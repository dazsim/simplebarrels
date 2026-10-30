package com.workshopcraft.simplebarrels.neoforge;

import com.workshopcraft.simplebarrels.block.FluidBarrelBlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

// one slot in mB over the barrel's droplets
public final class FluidBarrelHandler extends SnapshotJournal<FluidBarrelHandler.Snapshot> implements ResourceHandler<FluidResource> {
    record Snapshot(Fluid fluid, long amount) {}

    private static final long MB = FluidBarrelBlockEntity.MB;
    private final FluidBarrelBlockEntity be;

    private FluidBarrelHandler(FluidBarrelBlockEntity be) { this.be = be; }

    public static FluidBarrelHandler of(FluidBarrelBlockEntity be) {
        if (be.platformStorage instanceof FluidBarrelHandler existing) return existing;
        FluidBarrelHandler created = new FluidBarrelHandler(be);
        be.platformStorage = created;
        return created;
    }

    @Override public int size() { return 1; }

    @Override
    public FluidResource getResource(int index) {
        return index != 0 || be.isEmpty() ? FluidResource.EMPTY : FluidResource.of(be.getFluid());
    }

    @Override
    public long getAmountAsLong(int index) {
        return index != 0 ? 0 : be.getAmount() / MB;
    }

    @Override
    public long getCapacityAsLong(int index, FluidResource resource) {
        return index == 0 ? be.getCapacity() / MB : 0;
    }

    @Override
    public boolean isValid(int index, FluidResource resource) {
        return index == 0 && !resource.isEmpty() && resource.isComponentsPatchEmpty() && be.room(resource.getFluid()) > 0;
    }

    @Override
    public int insert(int index, FluidResource resource, int amount, TransactionContext tx) {
        if (amount <= 0 || index != 0 || resource.isEmpty() || !resource.isComponentsPatchEmpty()) return 0;
        int n = (int) Math.min(amount, be.room(resource.getFluid()) / MB);
        if (n <= 0) return 0;
        updateSnapshots(tx);
        be.setRaw(resource.getFluid(), be.getAmount() + n * MB);
        return n;
    }

    @Override
    public int extract(int index, FluidResource resource, int amount, TransactionContext tx) {
        if (amount <= 0 || index != 0 || resource.isEmpty() || !resource.isComponentsPatchEmpty() || be.isEmpty()) return 0;
        if (FluidBarrelBlockEntity.still(resource.getFluid()) != be.getFluid()) return 0;
        int n = (int) Math.min(amount, be.getAmount() / MB);
        if (n <= 0) return 0;
        updateSnapshots(tx);
        be.setRaw(be.getFluid(), be.getAmount() - n * MB);
        return n;
    }

    @Override protected Snapshot createSnapshot() { return new Snapshot(be.getFluid(), be.getAmount()); }
    @Override protected void revertToSnapshot(Snapshot s) { be.setRaw(s.fluid(), s.amount()); }
    @Override protected void onRootCommit(Snapshot original) { be.changed(); }
}
