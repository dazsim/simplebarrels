package com.workshopcraft.simplebarrels.neoforge;

import com.workshopcraft.simplebarrels.SimpleBarrels;
import com.workshopcraft.simplebarrels.client.BarrelRenderer;
import com.workshopcraft.simplebarrels.client.CarriedProperty;
import com.workshopcraft.simplebarrels.client.DollyLoadRenderer;
import com.workshopcraft.simplebarrels.client.ModdedBarrelItemRenderer;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterSelectItemModelPropertyEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;

@Mod(value = SimpleBarrels.MOD_ID, dist = Dist.CLIENT)
public class SimpleBarrelsNeoForgeClient {
    public SimpleBarrelsNeoForgeClient(IEventBus modBus) {
        modBus.addListener((EntityRenderersEvent.RegisterRenderers e) -> e.registerBlockEntityRenderer(ModContent.BARREL_BE, BarrelRenderer::new));
        modBus.addListener((RegisterSpecialModelRendererEvent e) -> {
            e.register(SimpleBarrels.id("dolly_load"), DollyLoadRenderer.Unbaked.MAP_CODEC);
            e.register(SimpleBarrels.id("modded_barrel"), ModdedBarrelItemRenderer.Unbaked.MAP_CODEC);
        });
        modBus.addListener((RegisterSelectItemModelPropertyEvent e) -> e.register(CarriedProperty.ID, CarriedProperty.TYPE));
    }
}
