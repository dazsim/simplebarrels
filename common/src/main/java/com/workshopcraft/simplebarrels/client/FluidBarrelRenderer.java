package com.workshopcraft.simplebarrels.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.workshopcraft.simplebarrels.block.FluidBarrelBlock;
import com.workshopcraft.simplebarrels.block.FluidBarrelBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class FluidBarrelRenderer implements BlockEntityRenderer<FluidBarrelBlockEntity, FluidBarrelRenderer.State> {
    private static final int PLATE_COLOUR = 0xFFE9DFC4;
    private static final float TEXT_SCALE = 0.015625F * 0.6666667F;
    // glass in the front texture, in block pixels from the face centre and floor
    private static final float WIN_X = 3, WIN_Y0 = 4, WIN_H = 8, FACE = 7;
    private final Font font;

    public static class State extends BlockEntityRenderState {
        Direction facing = Direction.NORTH;
        int faceLight;
        TextureAtlasSprite sprite;
        int tint;
        boolean glow;
        float fill;
        String count;
    }

    public FluidBarrelRenderer(BlockEntityRendererProvider.Context ctx) {
        this.font = ctx.font();
    }

    @Override
    public State createRenderState() { return new State(); }

    @Override
    public void extractRenderState(FluidBarrelBlockEntity be, State s, float partialTick, Vec3 camera, ModelFeatureRenderer.CrumblingOverlay breaking) {
        BlockEntityRenderer.super.extractRenderState(be, s, partialTick, camera, breaking);
        s.facing = be.getBlockState().getValue(FluidBarrelBlock.FACING);
        s.faceLight = be.getLevel() == null ? s.lightCoords : LevelRenderer.getLightCoords(be.getLevel(), be.getBlockPos().relative(s.facing));
        s.sprite = null;
        s.count = null;
        if (be.isEmpty()) return;
        Fluid f = be.getFluid();
        FluidModel model = Minecraft.getInstance().getModelManager().getFluidStateModelSet().get(f.defaultFluidState());
        s.sprite = model.stillMaterial().sprite();
        BlockTintSource tint = model.tintSource();
        ClientLevel level = Minecraft.getInstance().level;
        s.tint = tint == null || level == null ? 0xFFFFFFFF
                : tint.colorInWorld(f.defaultFluidState().createLegacyBlock(), level, be.getBlockPos()) | 0xFF000000;
        s.glow = f.defaultFluidState().createLegacyBlock().getLightEmission() > 0;
        s.fill = Math.min(1F, Math.max(1F / WIN_H / 2, be.getAmount() / (float) be.getCapacity()));
        if (be.showsCount()) s.count = String.valueOf(be.getAmount() / FluidBarrelBlockEntity.MB);
    }

    @Override
    public void submit(State s, PoseStack pose, SubmitNodeCollector out, CameraRenderState camera) {
        pose.pushPose();
        pose.translate(0.5, 0, 0.5);
        pose.mulPose(Axis.YP.rotationDegrees(-s.facing.toYRot()));
        if (s.sprite != null) drawFluid(pose, out, s);
        pose.translate(0, 0, FACE / 16F);
        if (s.count != null) {
            drawPlate(pose, out, s.count, 0.34F, s.faceLight);
            drawText(pose, out, s.count, 0, 0.34F, 0xFF2B2418, s.faceLight);
        }
        pose.popPose();
    }

    private void drawFluid(PoseStack pose, SubmitNodeCollector out, State s) {
        TextureAtlasSprite sp = s.sprite;
        int light = s.glow ? LightCoordsUtil.FULL_BRIGHT : s.faceLight;
        int tint = s.tint;
        float h = WIN_H * s.fill;
        float x0 = -WIN_X / 16F, x1 = WIN_X / 16F, y0 = WIN_Y0 / 16F, y1 = (WIN_Y0 + h) / 16F, z = FACE / 16F + 0.002F;
        float u0 = sp.getU((8 - WIN_X) / 16F), u1 = sp.getU((8 + WIN_X) / 16F);
        float v0 = sp.getV((16 - h) / 16F), v1 = sp.getV(1F);
        out.submitCustomGeometry(pose, Sheets.translucentBlockSheet(), (p, vc) -> {
            Matrix4f m = p.pose();
            Vector3f n = p.transformNormal(0, 0, 1, new Vector3f());
            Vector3f q = new Vector3f();
            float[][] corners = {{x0, y0, u0, v1}, {x1, y0, u1, v1}, {x1, y1, u1, v0}, {x0, y1, u0, v0}};
            for (float[] c : corners) {
                m.transformPosition(c[0], c[1], z, q);
                vc.addVertex(q.x(), q.y(), q.z(), tint, c[2], c[3], OverlayTexture.NO_OVERLAY, light, n.x(), n.y(), n.z());
            }
        });
    }

    private void drawPlate(PoseStack pose, SubmitNodeCollector out, String text, float y, int light) {
        pose.pushPose();
        pose.translate(0, y, 0.004);
        pose.scale(TEXT_SCALE, -TEXT_SCALE, TEXT_SCALE);
        float half = Math.max(font.width("65536"), font.width(text)) / 2F + 2;
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

    private void drawText(PoseStack pose, SubmitNodeCollector out, String s, float x, float y, int colour, int light) {
        pose.pushPose();
        pose.translate(x, y, 0.006);
        pose.scale(TEXT_SCALE, -TEXT_SCALE, TEXT_SCALE);
        out.submitText(pose, -font.width(s) / 2F, 0, FormattedCharSequence.forward(s, Style.EMPTY), false, Font.DisplayMode.NORMAL, light, colour, 0, 0);
        pose.popPose();
    }
}
