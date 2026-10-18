package com.workshopcraft.simplebarrels.neoforge;

import com.workshopcraft.simplebarrels.client.BarrelRenderer;
import com.workshopcraft.simplebarrels.client.DollyProperty;
import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public final class SimpleBarrelsNeoForgeClient {
    private SimpleBarrelsNeoForgeClient() {}

    static void init(IEventBus modBus) {
        modBus.addListener((EntityRenderersEvent.RegisterRenderers e) -> e.registerBlockEntityRenderer(ModContent.BARREL_BE, BarrelRenderer::new));
        modBus.addListener((FMLClientSetupEvent e) -> e.enqueueWork(() ->
                ItemProperties.register(ModContent.DOLLY, DollyProperty.ID, (stack, level, entity, seed) -> DollyProperty.get(stack))));
    }
}
