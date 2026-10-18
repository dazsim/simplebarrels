package com.workshopcraft.simplebarrels.neoforge;

import com.workshopcraft.simplebarrels.block.BarrelBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.TransferPreconditions;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

public final class BarrelItemHandler extends SnapshotJournal<BarrelItemHandler.Snapshot> implements ResourceHandler<ItemResource> {
    record Snapshot(ItemStack template, int count) {}

    private final BarrelBlockEntity be;

    private BarrelItemHandler(BarrelBlockEntity be) { this.be = be; }

    public static BarrelItemHandler of(BarrelBlockEntity be) {
        if (be.platformStorage instanceof BarrelItemHandler existing) return existing;
        BarrelItemHandler created = new BarrelItemHandler(be);
        be.platformStorage = created;
        return created;
    }

    @Override public int size() { return 1; }

    @Override
    public ItemResource getResource(int index) {
        return index != 0 || be.isEmpty() ? ItemResource.EMPTY : ItemResource.of(be.getTemplate());
    }

    @Override
    public long getAmountAsLong(int index) {
        return index != 0 || be.isEmpty() ? 0 : be.getCount();
    }

    @Override
    public long getCapacityAsLong(int index, ItemResource resource) {
        return index == 0 ? be.getCapacity() : 0;
    }

    @Override
    public boolean isValid(int index, ItemResource resource) {
        return index == 0 && !resource.isEmpty() && be.canAccept(resource.toStack(1));
    }

    @Override
    public int insert(int index, ItemResource resource, int amount, TransactionContext tx) {
        TransferPreconditions.checkNonEmptyNonNegative(resource, amount);
        if (index != 0) return 0;
        ItemStack stack = resource.toStack(1);
        int n = Math.min(amount, be.spaceFor(stack));
        if (n <= 0) return 0;
        updateSnapshots(tx);
        be.setContentsRaw(stack, (be.isEmpty() ? 0 : be.getCount()) + n);
        return n;
    }

    @Override
    public int extract(int index, ItemResource resource, int amount, TransactionContext tx) {
        TransferPreconditions.checkNonEmptyNonNegative(resource, amount);
        if (index != 0 || be.isEmpty() || !resource.matches(be.getTemplate())) return 0;
        int n = Math.min(amount, be.getCount());
        if (n <= 0) return 0;
        updateSnapshots(tx);
        be.setContentsRaw(be.getTemplate(), be.getCount() - n);
        return n;
    }

    @Override protected Snapshot createSnapshot() { return new Snapshot(be.getTemplate().copy(), be.getCount()); }
    @Override protected void revertToSnapshot(Snapshot s) { be.setContentsRaw(s.template(), s.count()); }
    @Override protected void onRootCommit(Snapshot original) { be.changed(); }
}
