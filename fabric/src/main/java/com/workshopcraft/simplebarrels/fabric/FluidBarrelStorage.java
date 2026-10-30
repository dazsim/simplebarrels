package com.workshopcraft.simplebarrels.fabric;

import com.workshopcraft.simplebarrels.block.FluidBarrelBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.level.material.Fluid;

// droplets on both sides, so no rounding
public final class FluidBarrelStorage extends SnapshotParticipant<FluidBarrelStorage.Snapshot> implements SingleSlotStorage<FluidVariant> {
    record Snapshot(Fluid fluid, long amount) {}

    private final FluidBarrelBlockEntity be;

    private FluidBarrelStorage(FluidBarrelBlockEntity be) { this.be = be; }

    public static FluidBarrelStorage of(FluidBarrelBlockEntity be) {
        if (be.platformStorage instanceof FluidBarrelStorage existing) return existing;
        FluidBarrelStorage created = new FluidBarrelStorage(be);
        be.platformStorage = created;
        return created;
    }

    @Override
    public long insert(FluidVariant resource, long maxAmount, TransactionContext tx) {
        StoragePreconditions.notBlankNotNegative(resource, maxAmount);
        if (!resource.componentsMatch(DataComponentPatch.EMPTY)) return 0;
        long n = Math.min(maxAmount, be.room(resource.getFluid()));
        if (n <= 0) return 0;
        updateSnapshots(tx);
        be.setRaw(resource.getFluid(), be.getAmount() + n);
        return n;
    }

    @Override
    public long extract(FluidVariant resource, long maxAmount, TransactionContext tx) {
        StoragePreconditions.notBlankNotNegative(resource, maxAmount);
        if (be.isEmpty() || !resource.componentsMatch(DataComponentPatch.EMPTY)) return 0;
        if (FluidBarrelBlockEntity.still(resource.getFluid()) != be.getFluid()) return 0;
        long n = Math.min(maxAmount, be.getAmount());
        if (n <= 0) return 0;
        updateSnapshots(tx);
        be.setRaw(be.getFluid(), be.getAmount() - n);
        return n;
    }

    @Override public boolean isResourceBlank() { return be.isEmpty(); }
    @Override public FluidVariant getResource() { return be.isEmpty() ? FluidVariant.blank() : FluidVariant.of(be.getFluid()); }
    @Override public long getAmount() { return be.getAmount(); }
    @Override public long getCapacity() { return be.getCapacity(); }

    @Override protected Snapshot createSnapshot() { return new Snapshot(be.getFluid(), be.getAmount()); }
    @Override protected void readSnapshot(Snapshot s) { be.setRaw(s.fluid(), s.amount()); }
    @Override protected void onFinalCommit() { be.changed(); }
}
