package com.workshopcraft.simplebarrels.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.workshopcraft.simplebarrels.block.BarrelBlock;
import com.workshopcraft.simplebarrels.block.ModdedBarrelBlock;
import com.workshopcraft.simplebarrels.registry.BarrelWood;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// oak barrel quads with the planks texture swapped for the modded wood
public final class ModdedBarrelModel {
    private static final Map<String, List<BakedQuad>> CACHE = new HashMap<>();
    private static BakedModel cachedFrom;

    private ModdedBarrelModel() {}

    public static void render(ResourceLocation wood, Direction facing, PoseStack pose, MultiBufferSource buffers, int light, int overlay) {
        VertexConsumer vc = buffers.getBuffer(Sheets.cutoutBlockSheet());
        for (BakedQuad q : quads(wood == null ? ModdedBarrelBlock.DEFAULT_WOOD : wood, facing)) {
            vc.putBulkData(pose.last(), q, 1, 1, 1, 1, light, overlay);
        }
    }

    public static void renderItem(ItemStack stack, PoseStack pose, MultiBufferSource buffers, int light, int overlay) {
        render(stack.get(ModContent.WOOD), Direction.EAST, pose, buffers, light, overlay);
    }

    private static List<BakedQuad> quads(ResourceLocation wood, Direction facing) {
        Minecraft mc = Minecraft.getInstance();
        BlockState oak = ModContent.BARRELS.get(BarrelWood.OAK).defaultBlockState().setValue(BarrelBlock.FACING, facing);
        BakedModel model = mc.getBlockRenderer().getBlockModel(oak);
        BakedModel probe = mc.getBlockRenderer().getBlockModel(ModContent.BARRELS.get(BarrelWood.OAK).defaultBlockState());
        if (probe != cachedFrom) {
            CACHE.clear();
            cachedFrom = probe;
        }
        return CACHE.computeIfAbsent(wood + "/" + facing, k -> {
            TextureAtlasSprite from = model.getParticleIcon();
            TextureAtlasSprite to = BuiltInRegistries.BLOCK.getOptional(wood)
                    .map(b -> mc.getBlockRenderer().getBlockModel(b.defaultBlockState()).getParticleIcon()).orElse(from);
            List<BakedQuad> out = new ArrayList<>();
            RandomSource rand = RandomSource.create(42);
            for (Direction d : Direction.values()) collect(model.getQuads(oak, d, rand), from, to, out);
            collect(model.getQuads(oak, null, rand), from, to, out);
            return out;
        });
    }

    private static void collect(List<BakedQuad> in, TextureAtlasSprite from, TextureAtlasSprite to, List<BakedQuad> out) {
        for (BakedQuad q : in) out.add(q.getSprite() == from && from != to ? remap(q, from, to) : q);
    }

    private static BakedQuad remap(BakedQuad q, TextureAtlasSprite from, TextureAtlasSprite to) {
        int[] v = q.getVertices().clone();
        int stride = v.length / 4;
        for (int i = 0; i < 4; i++) {
            int at = i * stride + 4;
            float u = (Float.intBitsToFloat(v[at]) - from.getU0()) / (from.getU1() - from.getU0());
            float w = (Float.intBitsToFloat(v[at + 1]) - from.getV0()) / (from.getV1() - from.getV0());
            v[at] = Float.floatToRawIntBits(to.getU0() + u * (to.getU1() - to.getU0()));
            v[at + 1] = Float.floatToRawIntBits(to.getV0() + w * (to.getV1() - to.getV0()));
        }
        return new BakedQuad(v, q.getTintIndex(), q.getDirection(), to, q.isShade());
    }
}
