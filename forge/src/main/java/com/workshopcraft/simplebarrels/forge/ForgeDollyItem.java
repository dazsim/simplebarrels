package com.workshopcraft.simplebarrels.forge;

import com.mojang.blaze3d.vertex.PoseStack;
import com.workshopcraft.simplebarrels.client.DollyChestRenderer;
import com.workshopcraft.simplebarrels.item.DollyItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class ForgeDollyItem extends DollyItem {
    public ForgeDollyItem(Properties props) {
        super(props);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private BlockEntityWithoutLevelRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    Minecraft mc = Minecraft.getInstance();
                    renderer = new BlockEntityWithoutLevelRenderer(mc.getBlockEntityRenderDispatcher(), mc.getEntityModels()) {
                        @Override
                        public void renderByItem(ItemStack stack, ItemDisplayContext ctx, PoseStack pose, MultiBufferSource buffers, int light, int overlay) {
                            DollyChestRenderer.render(stack, pose, buffers, light, overlay);
                        }
                    };
                }
                return renderer;
            }
        });
    }
}
