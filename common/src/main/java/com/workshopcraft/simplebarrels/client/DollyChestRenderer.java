package com.workshopcraft.simplebarrels.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.workshopcraft.simplebarrels.SimpleBarrels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.block.entity.TrappedChestBlockEntity;

// loaded dolly with a chest or modded barrel: dolly frame model plus the block on the plate
public final class DollyChestRenderer {
    private static ChestBlockEntity chest, trapped;
    private static EnderChestBlockEntity ender;

    private DollyChestRenderer() {}

    public static void render(ItemStack stack, PoseStack pose, MultiBufferSource buffers, int light, int overlay) {
        Minecraft mc = Minecraft.getInstance();
        BakedModel frame = mc.getModelManager().getModel(frameModel());
        mc.getBlockRenderer().getModelRenderer().renderModel(pose.last(), buffers.getBuffer(Sheets.cutoutBlockSheet()),
                null, frame, 1, 1, 1, light, OverlayTexture.NO_OVERLAY);

        CompoundTag carried = stack.getTagElement("CarriedBlock");
        if (carried != null && ModContent.MODDED_BARREL == BuiltInRegistries.BLOCK.get(new ResourceLocation(carried.getString("Block")))) {
            pose.pushPose();
            pose.translate(3.2 / 16, 1.6 / 16, 1.4 / 16);
            pose.scale(0.6F, 0.6F, 0.6F);
            ModdedBarrelModel.render(ResourceLocation.tryParse(carried.getCompound("Data").getString("Wood")), Direction.EAST, pose, buffers, light, overlay);
            pose.popPose();
            return;
        }
        BlockEntity be = chestFor(carried);
        if (be == null) return;
        pose.pushPose();
        pose.translate(3.2 / 16, 1.6 / 16, 1.4 / 16);
        pose.scale(0.6F, 0.6F, 0.6F);
        pose.translate(0.5, 0, 0.5);
        pose.mulPose(Axis.YP.rotationDegrees(180));
        pose.translate(-0.5, 0, -0.5);
        mc.getBlockEntityRenderDispatcher().renderItem(be, pose, buffers, light, overlay);
        pose.popPose();
    }

    static ModelResourceLocation frameModel() {
        return new ModelResourceLocation(SimpleBarrels.id("dolly"), "inventory");
    }

    private static BlockEntity chestFor(CompoundTag tag) {
        if (tag == null) return null;
        Block b = BuiltInRegistries.BLOCK.get(new ResourceLocation(tag.getString("Block")));
        if (b == Blocks.CHEST) {
            if (chest == null) chest = new ChestBlockEntity(BlockPos.ZERO, Blocks.CHEST.defaultBlockState());
            return chest;
        }
        if (b == Blocks.TRAPPED_CHEST) {
            if (trapped == null) trapped = new TrappedChestBlockEntity(BlockPos.ZERO, Blocks.TRAPPED_CHEST.defaultBlockState());
            return trapped;
        }
        if (b == Blocks.ENDER_CHEST) {
            if (ender == null) ender = new EnderChestBlockEntity(BlockPos.ZERO, Blocks.ENDER_CHEST.defaultBlockState());
            return ender;
        }
        return null;
    }
}
