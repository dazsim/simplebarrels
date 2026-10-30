package com.workshopcraft.simplebarrels.block;

import com.workshopcraft.simplebarrels.item.BarrelUpgrades;
import com.workshopcraft.simplebarrels.item.FluidContents;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

// one fluid in droplets, 81 to the mB, so both loaders map onto it exactly
public class FluidBarrelBlockEntity extends BlockEntity {
    public static final long MB = 81, BUCKET = 1000 * MB;

    private Fluid fluid = Fluids.EMPTY;
    private long amount;
    private boolean showCount;

    public Object platformStorage;

    public FluidBarrelBlockEntity(BlockPos pos, BlockState state) {
        super(ModContent.FLUID_BARREL_BE, pos, state);
    }

    public static Fluid still(Fluid f) {
        return f instanceof FlowingFluid flowing ? flowing.getSource() : f;
    }

    public static Component fluidName(Fluid f) {
        return f.defaultFluidState().createLegacyBlock().getBlock().getName();
    }

    public Fluid getFluid() { return fluid; }
    public long getAmount() { return amount; }
    public boolean isEmpty() { return amount <= 0; }
    public boolean showsCount() { return showCount; }

    public long getCapacity() {
        return getBlockState().getBlock() instanceof FluidBarrelBlock b ? b.tier.buckets * BUCKET : 16 * BUCKET;
    }

    public boolean addCounter() {
        if (showCount) return false;
        showCount = true;
        changed();
        return true;
    }

    public long room(Fluid f) {
        if (f == Fluids.EMPTY) return 0;
        return isEmpty() || still(f) == fluid ? getCapacity() - amount : 0;
    }

    // sets the contents without telling anyone, callers commit with changed()
    public void setRaw(Fluid f, long a) {
        if (a <= 0 || f == Fluids.EMPTY) {
            fluid = Fluids.EMPTY;
            amount = 0;
        } else {
            fluid = still(f);
            amount = Math.min(a, getCapacity());
        }
    }

    public void changed() {
        setChanged();
        if (level != null && !level.isClientSide()) {
            BlockState s = getBlockState();
            level.sendBlockUpdated(worldPosition, s, s, Block.UPDATE_CLIENTS);
        }
    }

    private void read(String id, long a) {
        Identifier key = Identifier.tryParse(id);
        setRaw(key == null ? Fluids.EMPTY : BuiltInRegistries.FLUID.getValue(key), a);
    }

    @Override
    protected void saveAdditional(ValueOutput out) {
        super.saveAdditional(out);
        if (!isEmpty()) {
            out.putString("Fluid", BuiltInRegistries.FLUID.getKey(fluid).toString());
            out.putLong("Amount", amount);
        }
        if (showCount) out.putBoolean("ShowCount", true);
    }

    @Override
    protected void loadAdditional(ValueInput in) {
        super.loadAdditional(in);
        showCount = in.getBooleanOr("ShowCount", false);
        read(in.getStringOr("Fluid", ""), in.getLongOr("Amount", 0));
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder b) {
        super.collectImplicitComponents(b);
        if (!isEmpty()) b.set(ModContent.FLUID, new FluidContents(fluid, amount));
        if (showCount) b.set(ModContent.UPGRADES, new BarrelUpgrades(true, false, BarrelBlockEntity.BASE_CAPACITY));
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter in) {
        super.applyImplicitComponents(in);
        FluidContents c = in.get(ModContent.FLUID);
        if (c != null) setRaw(c.fluid(), c.amount());
        showCount = in.getOrDefault(ModContent.UPGRADES, BarrelUpgrades.NONE).showCount();
    }

    @Override
    public void removeComponentsFromTag(ValueOutput out) {
        super.removeComponentsFromTag(out);
        out.discard("Fluid");
        out.discard("Amount");
        out.discard("ShowCount");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveCustomOnly(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
