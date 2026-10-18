package com.workshopcraft.simplebarrels.fabric;

import com.workshopcraft.simplebarrels.block.BarrelBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import net.minecraft.world.item.ItemStack;

public final class BarrelStorage extends SnapshotParticipant<BarrelStorage.Snapshot> implements SingleSlotStorage<ItemVariant> {
    record Snapshot(ItemStack template, int count) {}

    private final BarrelBlockEntity be;

    private BarrelStorage(BarrelBlockEntity be) { this.be = be; }

    public static BarrelStorage of(BarrelBlockEntity be) {
        if (be.platformStorage instanceof BarrelStorage existing) return existing;
        BarrelStorage created = new BarrelStorage(be);
        be.platformStorage = created;
        return created;
    }

    @Override
    public long insert(ItemVariant resource, long maxAmount, TransactionContext tx) {
        StoragePreconditions.notBlankNotNegative(resource, maxAmount);
        ItemStack stack = resource.toStack(1);
        long n = Math.min(maxAmount, be.spaceFor(stack));
        if (n <= 0) return 0;
        updateSnapshots(tx);
        be.setContentsRaw(stack, (be.isEmpty() ? 0 : be.getCount()) + (int) n);
        return n;
    }

    @Override
    public long extract(ItemVariant resource, long maxAmount, TransactionContext tx) {
        StoragePreconditions.notBlankNotNegative(resource, maxAmount);
        if (be.isEmpty() || !resource.matches(be.getTemplate())) return 0;
        long n = Math.min(maxAmount, be.getCount());
        if (n <= 0) return 0;
        updateSnapshots(tx);
        be.setContentsRaw(be.getTemplate(), be.getCount() - (int) n);
        return n;
    }

    @Override public boolean isResourceBlank() { return be.isEmpty(); }
    @Override public ItemVariant getResource() { return be.isEmpty() ? ItemVariant.blank() : ItemVariant.of(be.getTemplate()); }
    @Override public long getAmount() { return be.isEmpty() ? 0 : be.getCount(); }
    @Override public long getCapacity() { return be.getCapacity(); }

    @Override protected Snapshot createSnapshot() { return new Snapshot(be.getTemplate().copy(), be.getCount()); }
    @Override protected void readSnapshot(Snapshot s) { be.setContentsRaw(s.template(), s.count()); }
    @Override protected void onFinalCommit() { be.changed(); }
}
