package com.workshopcraft.simplebarrels.fabric;

import com.workshopcraft.simplebarrels.registry.ModContent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;

public class SimpleBarrelsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ModContent.registerAllDirect();
        ItemStorage.SIDED.registerForBlockEntity((be, side) -> BarrelStorage.of(be), ModContent.BARREL_BE);
    }
}
