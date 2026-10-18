package com.workshopcraft.simplebarrels.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.workshopcraft.simplebarrels.item.CarriedBlock;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.ChestSpecialRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

// the block sitting on a loaded dolly: a chest or a modded barrel
public class DollyLoadRenderer implements SpecialModelRenderer<CarriedBlock> {
    private final ChestSpecialRenderer chest, trapped, ender;

    DollyLoadRenderer(ChestSpecialRenderer chest, ChestSpecialRenderer trapped, ChestSpecialRenderer ender) {
        this.chest = chest;
        this.trapped = trapped;
        this.ender = ender;
    }

    @Override
    public @Nullable CarriedBlock extractArgument(ItemStack stack) {
        return stack.get(ModContent.CARRIED_BLOCK);
    }

    @Override
    public void submit(@Nullable CarriedBlock c, PoseStack pose, SubmitNodeCollector out, int light, int overlay, boolean foil, int outline) {
        if (c == null) return;
        pose.pushPose();
        pose.translate(3.2 / 16, 1.6 / 16, 1.4 / 16);
        pose.scale(0.6F, 0.6F, 0.6F);
        if (c.block() == ModContent.MODDED_BARREL) {
            Identifier wood = Identifier.tryParse(c.data().getStringOr("Wood", ""));
            out.submitCustomGeometry(pose, Sheets.cutoutBlockSheet(),
                    (p, vc) -> ModdedBarrelModel.draw(wood, Direction.EAST, p, vc, light, overlay));
        } else {
            ChestSpecialRenderer r = c.block() == Blocks.CHEST ? chest : c.block() == Blocks.TRAPPED_CHEST ? trapped : c.block() == Blocks.ENDER_CHEST ? ender : null;
            if (r != null) r.submit(pose, out, light, overlay, false, outline);
        }
        pose.popPose();
    }

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
        output.accept(new Vector3f(3.2F / 16, 1.6F / 16, 1.4F / 16));
        output.accept(new Vector3f(3.2F / 16 + 0.6F, 1.6F / 16 + 0.6F, 1.4F / 16 + 0.6F));
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked<CarriedBlock> {
        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(new Unbaked());

        @Override
        public SpecialModelRenderer<CarriedBlock> bake(SpecialModelRenderer.BakingContext ctx) {
            return new DollyLoadRenderer(
                    new ChestSpecialRenderer.Unbaked(ChestSpecialRenderer.REGULAR.single(), ChestType.SINGLE).bake(ctx),
                    new ChestSpecialRenderer.Unbaked(ChestSpecialRenderer.TRAPPED.single(), ChestType.SINGLE).bake(ctx),
                    new ChestSpecialRenderer.Unbaked(ChestSpecialRenderer.ENDER_CHEST, ChestType.SINGLE).bake(ctx));
        }

        @Override
        public MapCodec<Unbaked> type() { return MAP_CODEC; }
    }
}
