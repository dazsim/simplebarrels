package com.workshopcraft.simplebarrels.block;

import com.workshopcraft.simplebarrels.registry.ModContent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BarrelBlockEntity extends BlockEntity {
    public static final int BASE_CAPACITY = 4096;

    private ItemStack template = ItemStack.EMPTY;
    private int count = 0;
    private int capacity = BASE_CAPACITY;

    public Object platformStorage;

    public BarrelBlockEntity(BlockPos pos, BlockState state) {
        super(ModContent.BARREL_BE, pos, state);
    }

    public ItemStack getTemplate() { return template; }
    public int getCount() { return count; }
    public int getCapacity() { return capacity; }
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
        if (level != null && !level.isClientSide) {
            BlockState s = getBlockState();
            level.sendBlockUpdated(worldPosition, s, s, Block.UPDATE_CLIENTS);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (!isEmpty()) {
            tag.put("Item", template.save(registries));
            tag.putInt("Count", count);
        }
        if (capacity != BASE_CAPACITY) tag.putInt("Capacity", capacity);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        capacity = tag.contains("Capacity") ? tag.getInt("Capacity") : BASE_CAPACITY;
        ItemStack loaded = tag.contains("Item") ? ItemStack.parseOptional(registries, tag.getCompound("Item")) : ItemStack.EMPTY;
        setContentsRaw(loaded, loaded.isEmpty() ? 0 : tag.getInt("Count"));
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
