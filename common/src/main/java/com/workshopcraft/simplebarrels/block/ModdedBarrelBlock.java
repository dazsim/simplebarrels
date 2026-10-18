package com.workshopcraft.simplebarrels.block;

import com.mojang.serialization.MapCodec;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

// one block for every modded wood, the planks id lives in the block entity
public class ModdedBarrelBlock extends BarrelBlock {
    public static final MapCodec<ModdedBarrelBlock> CODEC = simpleCodec(ModdedBarrelBlock::new);
    public static final Identifier DEFAULT_WOOD = Identifier.withDefaultNamespace("oak_planks");

    public ModdedBarrelBlock(Properties props) {
        super(props);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() { return CODEC; }

    // drawn by ModdedBarrelModel
    @Override
    protected RenderShape getRenderShape(BlockState state) { return RenderShape.INVISIBLE; }

    @Override
    protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        ItemStack s = super.getCloneItemStack(level, pos, state, includeData);
        if (level.getBlockEntity(pos) instanceof BarrelBlockEntity be && be.getWood() != null) s.set(ModContent.WOOD, be.getWood());
        return s;
    }

    public static ItemStack stack(Identifier wood) {
        ItemStack s = new ItemStack(ModContent.MODDED_BARREL);
        s.set(ModContent.WOOD, wood);
        return s;
    }

    public static Component name(Identifier wood) {
        if (wood == null || wood.getPath().isEmpty()) wood = DEFAULT_WOOD;
        String w = wood.getPath().replace("_planks", "");
        String fallback = Character.toUpperCase(w.charAt(0)) + w.substring(1).replace('_', ' ');
        return Component.translatable("block.simplebarrels.modded_barrel.named",
                Component.translatableWithFallback("wood.simplebarrels." + wood.getNamespace() + "." + w, fallback));
    }

    public static Component modName(Identifier wood) {
        return Component.translatableWithFallback("mod.simplebarrels." + wood.getNamespace(), wood.getNamespace());
    }
}
