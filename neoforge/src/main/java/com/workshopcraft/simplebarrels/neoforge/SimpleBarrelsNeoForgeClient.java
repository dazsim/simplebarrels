package com.workshopcraft.simplebarrels.neoforge;

import com.workshopcraft.simplebarrels.client.BarrelRenderer;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public final class SimpleBarrelsNeoForgeClient {
    private SimpleBarrelsNeoForgeClient() {}

    static void init(IEventBus modBus) {
        modBus.addListener((EntityRenderersEvent.RegisterRenderers e) -> e.registerBlockEntityRenderer(ModContent.BARREL_BE, BarrelRenderer::new));
    }
}
