package com.workshopcraft.simplebarrels.neoforge;

import com.workshopcraft.simplebarrels.client.BarrelRenderer;
import com.workshopcraft.simplebarrels.client.DollyChestRenderer;
import com.workshopcraft.simplebarrels.client.DollyProperty;
import com.workshopcraft.simplebarrels.client.ModdedBarrelModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public final class SimpleBarrelsNeoForgeClient {
    private SimpleBarrelsNeoForgeClient() {}

    interface ItemDraw {
        void render(ItemStack stack, PoseStack pose, MultiBufferSource buffers, int light, int overlay);
    }

    private static IClientItemExtensions extension(ItemDraw draw) {
        return new IClientItemExtensions() {
            private BlockEntityWithoutLevelRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    Minecraft mc = Minecraft.getInstance();
                    renderer = new BlockEntityWithoutLevelRenderer(mc.getBlockEntityRenderDispatcher(), mc.getEntityModels()) {
                        @Override
                        public void renderByItem(ItemStack stack, ItemDisplayContext ctx, PoseStack pose, MultiBufferSource buffers, int light, int overlay) {
                            draw.render(stack, pose, buffers, light, overlay);
                        }
                    };
                }
                return renderer;
            }
        };
    }

    static void init(IEventBus modBus) {
        modBus.addListener((RegisterClientExtensionsEvent e) -> {
            e.registerItem(extension(DollyChestRenderer::render), ModContent.DOLLY);
            e.registerItem(extension(ModdedBarrelModel::renderItem), ModContent.MODDED_BARREL_ITEM);
        });
        modBus.addListener((EntityRenderersEvent.RegisterRenderers e) -> e.registerBlockEntityRenderer(ModContent.BARREL_BE, BarrelRenderer::new));
        modBus.addListener((FMLClientSetupEvent e) -> e.enqueueWork(() ->
                ItemProperties.register(ModContent.DOLLY, DollyProperty.ID, (stack, level, entity, seed) -> DollyProperty.get(stack))));
    }
}
