package com.workshopcraft.simplebarrels.block;

import com.workshopcraft.simplebarrels.item.BarrelUpgrades;
import com.workshopcraft.simplebarrels.item.UpgradeItem;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class BarrelBlockEntity extends BlockEntity {
    public static final int BASE_CAPACITY = 4096;

    private ItemStack template = ItemStack.EMPTY;
    private int count = 0;
    private int capacity = BASE_CAPACITY;
    // set by the upgrades or the crafting variants
    private boolean showCount = false;
    private boolean showItem = false;
    // planks id, modded barrel only
    private Identifier wood;

    public Object platformStorage;

    public BarrelBlockEntity(BlockPos pos, BlockState state) {
        super(ModContent.BARREL_BE, pos, state);
    }

    public ItemStack getTemplate() { return template; }
    public int getCount() { return count; }
    public int getCapacity() { return capacity; }
    public boolean showsCount() { return showCount; }
    public boolean showsItem() { return showItem; }
    public Identifier getWood() { return wood; }

    // 0 for none, 1 to 4 for compressor marks
    public int compressorTier() {
        int t = 0;
        for (int c = capacity; c > BASE_CAPACITY; c >>= 1) t++;
        return t;
    }

    public BarrelUpgrades getUpgrades() { return new BarrelUpgrades(showCount, showItem, capacity); }

    public void setUpgrades(BarrelUpgrades u) {
        showCount = u.showCount();
        showItem = u.showItem();
        capacity = Math.max(BASE_CAPACITY, u.capacity());
    }

    // same rules as the old upgrade items, compressors must go in order
    public boolean applyUpgrade(UpgradeItem.Kind kind) {
        switch (kind) {
            case COUNTER -> { if (showCount) return false; showCount = true; }
            case FRAME -> { if (showItem) return false; showItem = true; }
            default -> {
                int from = BASE_CAPACITY << (kind.ordinal() - UpgradeItem.Kind.MK1.ordinal());
                if (capacity != from) return false;
                capacity = from * 2;
            }
        }
        changed();
        return true;
    }

    public boolean isEmpty() { return count <= 0 || template.isEmpty(); }

    public boolean canAccept(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return isEmpty() || ItemStack.isSameItemSameComponents(template, stack);
    }

    public int spaceFor(ItemStack stack) {
        return canAccept(stack) ? capacity - (isEmpty() ? 0 : count) : 0;
    }

    public int insert(ItemStack stack, int amount, boolean simulate) {
        int n = Math.min(Math.min(amount, stack.getCount()), spaceFor(stack));
        if (n <= 0) return 0;
        if (!simulate) {
            if (isEmpty()) {
                template = stack.copyWithCount(1);
                count = 0;
            }
            count += n;
            changed();
        }
        return n;
    }

    public ItemStack extract(int amount, boolean simulate) {
        if (isEmpty() || amount <= 0) return ItemStack.EMPTY;
        int n = Math.min(amount, count);
        ItemStack out = template.copyWithCount(n);
        if (!simulate) {
            count -= n;
            if (count <= 0) {
                count = 0;
                template = ItemStack.EMPTY;
            }
            changed();
        }
        return out;
    }

    public void setContentsRaw(ItemStack template, int count) {
        this.template = count > 0 ? template.copyWithCount(1) : ItemStack.EMPTY;
        this.count = Math.max(0, count);
    }

    public void changed() {
        setChanged();
        if (level != null && !level.isClientSide()) {
            BlockState s = getBlockState();
            level.sendBlockUpdated(worldPosition, s, s, Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        if (level == null) return;
        while (!isEmpty()) {
            ItemStack s = extract(template.getMaxStackSize(), false);
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), s);
        }
    }

    @Override
    protected void saveAdditional(ValueOutput out) {
        super.saveAdditional(out);
        if (!isEmpty()) {
            out.store("Item", ItemStack.CODEC, template);
            out.putInt("Count", count);
        }
        if (capacity != BASE_CAPACITY) out.putInt("Capacity", capacity);
        if (showCount) out.putBoolean("ShowCount", true);
        if (showItem) out.putBoolean("ShowItem", true);
        if (wood != null) out.store("Wood", Identifier.CODEC, wood);
    }

    @Override
    protected void loadAdditional(ValueInput in) {
        super.loadAdditional(in);
        capacity = in.getIntOr("Capacity", BASE_CAPACITY);
        showCount = in.getBooleanOr("ShowCount", false);
        showItem = in.getBooleanOr("ShowItem", false);
        wood = in.read("Wood", Identifier.CODEC).orElse(null);
        ItemStack loaded = in.read("Item", ItemStack.CODEC).orElse(ItemStack.EMPTY);
        setContentsRaw(loaded, loaded.isEmpty() ? 0 : in.getIntOr("Count", 0));
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder b) {
        super.collectImplicitComponents(b);
        if (!getUpgrades().isNone()) b.set(ModContent.UPGRADES, getUpgrades());
        if (wood != null) b.set(ModContent.WOOD, wood);
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter in) {
        super.applyImplicitComponents(in);
        setUpgrades(in.getOrDefault(ModContent.UPGRADES, BarrelUpgrades.NONE));
        wood = in.get(ModContent.WOOD);
    }

    @Override
    public void removeComponentsFromTag(ValueOutput out) {
        super.removeComponentsFromTag(out);
        out.discard("Capacity");
        out.discard("ShowCount");
        out.discard("ShowItem");
        out.discard("Wood");
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
