package com.workshopcraft.simplebarrels.block;

import com.workshopcraft.simplebarrels.registry.ModContent;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

// one block for every modded wood, the planks id lives in the block entity
public class ModdedBarrelBlock extends BarrelBlock {
    public static final ResourceLocation DEFAULT_WOOD = new ResourceLocation("minecraft", "oak_planks");

    public ModdedBarrelBlock(Properties props) {
        super(props);
    }

    // drawn by ModdedBarrelModel
    @Override
    public RenderShape getRenderShape(BlockState state) { return RenderShape.ENTITYBLOCK_ANIMATED; }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        if (level.getBlockEntity(pos) instanceof BarrelBlockEntity be && be.getWood() != null) return stack(be.getWood());
        return super.getCloneItemStack(level, pos, state);
    }

    public static ItemStack stack(ResourceLocation wood) {
        ItemStack s = new ItemStack(ModContent.MODDED_BARREL);
        s.getOrCreateTagElement("BlockEntityTag").putString("Wood", wood.toString());
        return s;
    }

    public static ResourceLocation woodOf(ItemStack stack) {
        CompoundTag t = stack.getTagElement("BlockEntityTag");
        return t != null && t.contains("Wood") ? ResourceLocation.tryParse(t.getString("Wood")) : null;
    }

    public static Component name(ResourceLocation wood) {
        if (wood == null || wood.getPath().isEmpty()) wood = DEFAULT_WOOD;
        String w = wood.getPath().replace("_planks", "");
        String fallback = Character.toUpperCase(w.charAt(0)) + w.substring(1).replace('_', ' ');
        return Component.translatable("block.simplebarrels.modded_barrel.named",
                Component.translatableWithFallback("wood.simplebarrels." + wood.getNamespace() + "." + w, fallback));
    }

    public static Component modName(ResourceLocation wood) {
        return Component.translatableWithFallback("mod.simplebarrels." + wood.getNamespace(), wood.getNamespace());
    }
}
