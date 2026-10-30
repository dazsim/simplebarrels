package com.workshopcraft.simplebarrels.fabric;

import com.workshopcraft.simplebarrels.SimpleBarrels;
import com.workshopcraft.simplebarrels.client.BarrelRenderer;
import com.workshopcraft.simplebarrels.client.CarriedProperty;
import com.workshopcraft.simplebarrels.client.DollyLoadRenderer;
import com.workshopcraft.simplebarrels.client.FluidBarrelRenderer;
import com.workshopcraft.simplebarrels.client.ModdedBarrelItemRenderer;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperties;
import net.minecraft.client.renderer.special.SpecialModelRenderers;

public class SimpleBarrelsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRenderers.register(ModContent.BARREL_BE, BarrelRenderer::new);
        BlockEntityRenderers.register(ModContent.FLUID_BARREL_BE, FluidBarrelRenderer::new);
        SpecialModelRenderers.ID_MAPPER.put(SimpleBarrels.id("dolly_load"), DollyLoadRenderer.Unbaked.MAP_CODEC);
        SpecialModelRenderers.ID_MAPPER.put(SimpleBarrels.id("modded_barrel"), ModdedBarrelItemRenderer.Unbaked.MAP_CODEC);
        SelectItemModelProperties.ID_MAPPER.put(CarriedProperty.ID, CarriedProperty.TYPE);
    }
}
