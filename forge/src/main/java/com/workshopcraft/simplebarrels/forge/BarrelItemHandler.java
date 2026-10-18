package com.workshopcraft.simplebarrels.forge;

import com.workshopcraft.simplebarrels.block.BarrelBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public final class BarrelItemHandler implements IItemHandler {
    private final BarrelBlockEntity be;

    private BarrelItemHandler(BarrelBlockEntity be) { this.be = be; }

    public static BarrelItemHandler of(BarrelBlockEntity be) {
        if (be.platformStorage instanceof BarrelItemHandler existing) return existing;
        BarrelItemHandler created = new BarrelItemHandler(be);
        be.platformStorage = created;
        return created;
    }

    @Override public int getSlots() { return 2; }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot != 0 || be.isEmpty()) return ItemStack.EMPTY;
        return be.getTemplate().copyWithCount(Math.min(be.getCount(), be.getTemplate().getMaxStackSize()));
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        int n = be.insert(stack, stack.getCount(), simulate);
        return n == 0 ? stack : stack.copyWithCount(stack.getCount() - n);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (slot != 0 || be.isEmpty()) return ItemStack.EMPTY;
        return be.extract(Math.min(amount, be.getTemplate().getMaxStackSize()), simulate);
    }

    @Override
    public int getSlotLimit(int slot) { return 64; }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) { return be.canAccept(stack); }
}
