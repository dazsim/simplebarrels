package com.workshopcraft.simplebarrels.fabric;

import com.workshopcraft.simplebarrels.client.BarrelRenderer;
import com.workshopcraft.simplebarrels.client.ModdedBarrelModel;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class SimpleBarrelsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRenderers.register(ModContent.BARREL_BE, BarrelRenderer::new);
        BuiltinItemRendererRegistry.INSTANCE.register(ModContent.MODDED_BARREL_ITEM, (stack, mode, pose, buffers, light, overlay) -> ModdedBarrelModel.renderItem(stack, pose, buffers, light, overlay));
    }
}
