package com.workshopcraft.simplebarrels.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.workshopcraft.simplebarrels.block.BarrelBlock;
import com.workshopcraft.simplebarrels.block.BarrelBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;

public class BarrelRenderer implements BlockEntityRenderer<BarrelBlockEntity> {
    private static final float TEXT_SCALE = 0.015625F * 0.6666667F;
    private final Font font;

    public BarrelRenderer(BlockEntityRendererProvider.Context ctx) {
        this.font = ctx.getFont();
    }

    @Override
    public void render(BarrelBlockEntity be, float partialTick, PoseStack pose, MultiBufferSource buffers, int light, int overlay) {
        if (be.getLevel() == null) return;
        Direction facing = be.getBlockState().getValue(BarrelBlock.FACING);
        int faceLight = LevelRenderer.getLightColor(be.getLevel(), be.getBlockPos().relative(facing));

        pose.pushPose();
        pose.translate(0.5, 0, 0.5);
        pose.mulPose(Axis.YP.rotationDegrees(-facing.toYRot()));
        pose.translate(0, 0, 0.485);

        if (!be.isEmpty()) {
            if (be.hasFrame()) {
                pose.pushPose();
                pose.translate(0, 0.56, 0);
                pose.mulPose(Axis.YP.rotationDegrees(180));
                pose.scale(0.4F, 0.4F, 0.01F);
                Minecraft.getInstance().getItemRenderer().renderStatic(be.getTemplate(), ItemDisplayContext.FIXED,
                        faceLight, OverlayTexture.NO_OVERLAY, pose, buffers, be.getLevel(), 0);
                pose.popPose();
            }
            drawText(pose, buffers, String.valueOf(be.getCount()), 0, 0.3F, 0xFF000000, faceLight, true);
        }
        int tier = be.compressorTier();
        if (tier > 0) drawText(pose, buffers, String.valueOf(tier), -0.3F, 0.82F, 0xFFFFFF00, faceLight, false);
        pose.popPose();
    }

    private void drawText(PoseStack pose, MultiBufferSource buffers, String s, float x, float y, int color, int light, boolean centre) {
        pose.pushPose();
        pose.translate(x, y, 0.002);
        pose.scale(TEXT_SCALE, -TEXT_SCALE, TEXT_SCALE);
        float dx = centre ? -font.width(s) / 2F : 0;
        font.drawInBatch(s, dx, 0, color, false, pose.last().pose(), buffers, Font.DisplayMode.NORMAL, 0, light);
        pose.popPose();
    }
}
