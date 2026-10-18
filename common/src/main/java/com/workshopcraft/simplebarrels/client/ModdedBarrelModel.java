package com.workshopcraft.simplebarrels.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.workshopcraft.simplebarrels.block.BarrelBlock;
import com.workshopcraft.simplebarrels.block.ModdedBarrelBlock;
import com.workshopcraft.simplebarrels.registry.BarrelWood;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.builders.UVPair;
import net.minecraft.client.renderer.block.BlockStateModelSet;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// oak barrel quads drawn with the planks texture swapped for the modded wood
public final class ModdedBarrelModel {
    private static final Map<Direction, List<BakedQuad>> QUADS = new EnumMap<>(Direction.class);
    private static final Map<Identifier, TextureAtlasSprite> SPRITES = new HashMap<>();
    private static BlockStateModelSet cachedFrom;
    private static TextureAtlasSprite oakPlanks;

    private ModdedBarrelModel() {}

    public static void draw(Identifier wood, Direction facing, PoseStack.Pose pose, VertexConsumer vc, int light, int overlay) {
        refresh();
        TextureAtlasSprite to = sprite(wood == null ? ModdedBarrelBlock.DEFAULT_WOOD : wood);
        Vector3f pos = new Vector3f();
        Vector3f normal = new Vector3f();
        for (BakedQuad q : quads(facing)) {
            boolean swap = q.materialInfo().sprite() == oakPlanks && to != oakPlanks;
            float shade = q.materialInfo().shade() ? shade(q.direction()) : 1F;
            int c = (int) (shade * 255);
            int colour = 0xFF000000 | c << 16 | c << 8 | c;
            pose.transformNormal(q.direction().getUnitVec3f(), normal);
            for (int i = 0; i < 4; i++) {
                Vector3fc p = q.position(i);
                pose.pose().transformPosition(p, pos);
                float u = UVPair.unpackU(q.packedUV(i));
                float v = UVPair.unpackV(q.packedUV(i));
                if (swap) {
                    u = to.getU((u - oakPlanks.getU0()) / (oakPlanks.getU1() - oakPlanks.getU0()));
                    v = to.getV((v - oakPlanks.getV0()) / (oakPlanks.getV1() - oakPlanks.getV0()));
                }
                vc.addVertex(pos.x(), pos.y(), pos.z(), colour, u, v, overlay, light, normal.x(), normal.y(), normal.z());
            }
        }
    }

    private static float shade(Direction d) {
        return switch (d) {
            case DOWN -> 0.5F;
            case UP -> 1F;
            case NORTH, SOUTH -> 0.8F;
            case WEST, EAST -> 0.6F;
        };
    }

    // models are rebuilt on resource reload, drop the cache when that happens
    private static void refresh() {
        BlockStateModelSet set = Minecraft.getInstance().getModelManager().getBlockStateModelSet();
        if (set == cachedFrom) return;
        cachedFrom = set;
        QUADS.clear();
        SPRITES.clear();
        oakPlanks = set.getParticleMaterial(ModContent.BARRELS.get(BarrelWood.OAK).defaultBlockState()).sprite();
    }

    private static List<BakedQuad> quads(Direction facing) {
        return QUADS.computeIfAbsent(facing, f -> {
            BlockState oak = ModContent.BARRELS.get(BarrelWood.OAK).defaultBlockState().setValue(BarrelBlock.FACING, f);
            BlockStateModel model = cachedFrom.get(oak);
            List<BlockStateModelPart> parts = new ArrayList<>();
            model.collectParts(RandomSource.create(42), parts);
            List<BakedQuad> out = new ArrayList<>();
            for (BlockStateModelPart part : parts) {
                for (Direction d : Direction.values()) out.addAll(part.getQuads(d));
                out.addAll(part.getQuads(null));
            }
            return out;
        });
    }

    private static TextureAtlasSprite sprite(Identifier wood) {
        return SPRITES.computeIfAbsent(wood, w -> BuiltInRegistries.BLOCK.getOptional(w)
                .map(b -> cachedFrom.getParticleMaterial(b.defaultBlockState()).sprite())
                .orElse(oakPlanks));
    }
}
