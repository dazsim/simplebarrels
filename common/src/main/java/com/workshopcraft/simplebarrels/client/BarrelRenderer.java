package com.workshopcraft.simplebarrels.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.workshopcraft.simplebarrels.block.BarrelBlock;
import com.workshopcraft.simplebarrels.block.BarrelBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import org.joml.Matrix4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;

public class BarrelRenderer implements BlockEntityRenderer<BarrelBlockEntity> {
    private static final int PLATE_COLOUR = 0xFFE9DFC4;
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
            if (be.showsItem()) {
                pose.pushPose();
                pose.translate(0, 0.56, 0);
                pose.mulPose(Axis.YP.rotationDegrees(180));
                pose.scale(0.4F, 0.4F, 0.01F);
                Minecraft.getInstance().getItemRenderer().renderStatic(be.getTemplate(), ItemDisplayContext.FIXED,
                        faceLight, OverlayTexture.NO_OVERLAY, pose, buffers, be.getLevel(), 0);
                pose.popPose();
            }
            if (be.showsCount()) {
                drawPlate(pose, buffers, 0.34F, faceLight);
                drawText(pose, buffers, String.valueOf(be.getCount()), 0, 0.34F, 0xFF2B2418, faceLight, true);
            }
        }
        int tier = be.compressorTier();
        if (tier > 0) drawText(pose, buffers, String.valueOf(tier), -0.3F, 0.82F, 0xFFFFFF00, faceLight, false);
        pose.popPose();
    }

    // paper back plate, sized for 65536, both windings so culling never hides it
    private void drawPlate(PoseStack pose, MultiBufferSource buffers, float y, int light) {
        pose.pushPose();
        pose.translate(0, y, 0.001);
        pose.scale(TEXT_SCALE, -TEXT_SCALE, TEXT_SCALE);
        float half = font.width("65536") / 2F + 2;
        float x0 = -half, x1 = half, y0 = -1.5F, y1 = font.lineHeight - 0.5F;
        Matrix4f m = pose.last().pose();
        VertexConsumer vc = buffers.getBuffer(RenderType.textBackground());
        vc.vertex(m, x0, y0, 0).color(PLATE_COLOUR).uv2(light).endVertex();
        vc.vertex(m, x0, y1, 0).color(PLATE_COLOUR).uv2(light).endVertex();
        vc.vertex(m, x1, y1, 0).color(PLATE_COLOUR).uv2(light).endVertex();
        vc.vertex(m, x1, y0, 0).color(PLATE_COLOUR).uv2(light).endVertex();
        vc.vertex(m, x1, y0, 0).color(PLATE_COLOUR).uv2(light).endVertex();
        vc.vertex(m, x1, y1, 0).color(PLATE_COLOUR).uv2(light).endVertex();
        vc.vertex(m, x0, y1, 0).color(PLATE_COLOUR).uv2(light).endVertex();
        vc.vertex(m, x0, y0, 0).color(PLATE_COLOUR).uv2(light).endVertex();
        pose.popPose();
    }

    private void drawText(PoseStack pose, MultiBufferSource buffers, String s, float x, float y, int color, int light, boolean centre) {
        pose.pushPose();
        pose.translate(x, y, 0.003);
        pose.scale(TEXT_SCALE, -TEXT_SCALE, TEXT_SCALE);
        float dx = centre ? -font.width(s) / 2F : 0;
        font.drawInBatch(s, dx, 0, color, false, pose.last().pose(), buffers, Font.DisplayMode.NORMAL, 0, light);
        pose.popPose();
    }
}
