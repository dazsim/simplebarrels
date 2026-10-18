package com.workshopcraft.simplebarrels.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.workshopcraft.simplebarrels.block.BarrelBlock;
import com.workshopcraft.simplebarrels.block.BarrelBlockEntity;
import com.workshopcraft.simplebarrels.block.ModdedBarrelBlock;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class BarrelRenderer implements BlockEntityRenderer<BarrelBlockEntity, BarrelRenderer.State> {
    private static final int PLATE_COLOUR = 0xFFE9DFC4;
    private static final float TEXT_SCALE = 0.015625F * 0.6666667F;
    private final Font font;
    private final ItemModelResolver items;

    public static class State extends BlockEntityRenderState {
        Direction facing = Direction.NORTH;
        int faceLight;
        boolean modded;
        Identifier wood;
        final ItemStackRenderState item = new ItemStackRenderState();
        String count;
        int tier;
    }

    public BarrelRenderer(BlockEntityRendererProvider.Context ctx) {
        this.font = ctx.font();
        this.items = ctx.itemModelResolver();
    }

    @Override
    public State createRenderState() { return new State(); }

    @Override
    public void extractRenderState(BarrelBlockEntity be, State s, float partialTick, Vec3 camera, ModelFeatureRenderer.CrumblingOverlay breaking) {
        BlockEntityRenderer.super.extractRenderState(be, s, partialTick, camera, breaking);
        s.facing = be.getBlockState().getValue(BarrelBlock.FACING);
        s.faceLight = be.getLevel() == null ? s.lightCoords : LevelRenderer.getLightCoords(be.getLevel(), be.getBlockPos().relative(s.facing));
        s.modded = be.getBlockState().getBlock() instanceof ModdedBarrelBlock;
        s.wood = be.getWood();
        if (!be.isEmpty() && be.showsItem()) {
            items.updateForTopItem(s.item, be.getTemplate(), ItemDisplayContext.FIXED, be.getLevel(), null, (int) be.getBlockPos().asLong());
        } else {
            s.item.clear();
        }
        s.count = !be.isEmpty() && be.showsCount() ? String.valueOf(be.getCount()) : null;
        s.tier = be.compressorTier();
    }

    @Override
    public void submit(State s, PoseStack pose, SubmitNodeCollector out, CameraRenderState camera) {
        if (s.modded) {
            out.submitCustomGeometry(pose, Sheets.cutoutBlockSheet(),
                    (p, vc) -> ModdedBarrelModel.draw(s.wood, s.facing, p, vc, s.lightCoords, OverlayTexture.NO_OVERLAY));
        }
        pose.pushPose();
        pose.translate(0.5, 0, 0.5);
        pose.mulPose(Axis.YP.rotationDegrees(-s.facing.toYRot()));
        pose.translate(0, 0, 0.485);

        if (!s.item.isEmpty()) {
            pose.pushPose();
            pose.translate(0, 0.56, 0);
            pose.mulPose(Axis.YP.rotationDegrees(180));
            pose.scale(0.4F, 0.4F, 0.01F);
            s.item.submit(pose, out, s.faceLight, OverlayTexture.NO_OVERLAY, 0);
            pose.popPose();
        }
        if (s.count != null) {
            drawPlate(pose, out, 0.34F, s.faceLight);
            drawText(pose, out, s.count, 0, 0.34F, 0xFF2B2418, s.faceLight, true);
        }
        if (s.tier > 0) drawText(pose, out, String.valueOf(s.tier), -0.3F, 0.82F, 0xFFFFFF00, s.faceLight, false);
        pose.popPose();
    }

    // paper back plate, sized for 65536, both windings so culling never hides it
    private void drawPlate(PoseStack pose, SubmitNodeCollector out, float y, int light) {
        pose.pushPose();
        pose.translate(0, y, 0.001);
        pose.scale(TEXT_SCALE, -TEXT_SCALE, TEXT_SCALE);
        float half = font.width("65536") / 2F + 2;
        float x0 = -half, x1 = half, y0 = -1.5F, y1 = font.lineHeight - 0.5F;
        out.submitCustomGeometry(pose, RenderTypes.textBackground(), (p, vc) -> {
            Matrix4f m = p.pose();
            vc.addVertex(m, x0, y0, 0).setColor(PLATE_COLOUR).setLight(light);
            vc.addVertex(m, x0, y1, 0).setColor(PLATE_COLOUR).setLight(light);
            vc.addVertex(m, x1, y1, 0).setColor(PLATE_COLOUR).setLight(light);
            vc.addVertex(m, x1, y0, 0).setColor(PLATE_COLOUR).setLight(light);
            vc.addVertex(m, x1, y0, 0).setColor(PLATE_COLOUR).setLight(light);
            vc.addVertex(m, x1, y1, 0).setColor(PLATE_COLOUR).setLight(light);
            vc.addVertex(m, x0, y1, 0).setColor(PLATE_COLOUR).setLight(light);
            vc.addVertex(m, x0, y0, 0).setColor(PLATE_COLOUR).setLight(light);
        });
        pose.popPose();
    }

    private void drawText(PoseStack pose, SubmitNodeCollector out, String s, float x, float y, int colour, int light, boolean centre) {
        pose.pushPose();
        pose.translate(x, y, 0.003);
        pose.scale(TEXT_SCALE, -TEXT_SCALE, TEXT_SCALE);
        float dx = centre ? -font.width(s) / 2F : 0;
        out.submitText(pose, dx, 0, FormattedCharSequence.forward(s, Style.EMPTY), false, Font.DisplayMode.NORMAL, light, colour, 0, 0);
        pose.popPose();
    }
}
