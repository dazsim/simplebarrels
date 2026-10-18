package com.workshopcraft.simplebarrels.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public class ModdedBarrelItemRenderer implements SpecialModelRenderer<Identifier> {
    @Override
    public @Nullable Identifier extractArgument(ItemStack stack) {
        return stack.get(ModContent.WOOD);
    }

    @Override
    public void submit(@Nullable Identifier wood, PoseStack pose, SubmitNodeCollector out, int light, int overlay, boolean foil, int outline) {
        out.submitCustomGeometry(pose, Sheets.cutoutBlockItemSheet(),
                (p, vc) -> ModdedBarrelModel.draw(wood, Direction.EAST, p, vc, light, overlay));
    }

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
        output.accept(new Vector3f(0, 0, 0));
        output.accept(new Vector3f(1, 1, 1));
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked<Identifier> {
        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(new Unbaked());

        @Override
        public SpecialModelRenderer<Identifier> bake(SpecialModelRenderer.BakingContext ctx) { return new ModdedBarrelItemRenderer(); }

        @Override
        public MapCodec<Unbaked> type() { return MAP_CODEC; }
    }
}
