package com.workshopcraft.simplebarrels.fabric;

import com.workshopcraft.simplebarrels.client.BarrelRenderer;
import com.workshopcraft.simplebarrels.client.DollyProperty;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class SimpleBarrelsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRenderers.register(ModContent.BARREL_BE, BarrelRenderer::new);
        FabricModelPredicateProviderRegistry.register(ModContent.DOLLY, DollyProperty.ID, (stack, level, entity, seed) -> DollyProperty.get(stack));
    }
}
