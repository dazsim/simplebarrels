package com.workshopcraft.simplebarrels.item;

import com.workshopcraft.simplebarrels.block.ModdedBarrelBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class ModdedBarrelItem extends BlockItem {
    public ModdedBarrelItem(Block block, Properties props) {
        super(block, props);
    }

    @Override
    public Component getName(ItemStack stack) {
        return ModdedBarrelBlock.name(ModdedBarrelBlock.woodOf(stack));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> lines, TooltipFlag flag) {
        ResourceLocation wood = ModdedBarrelBlock.woodOf(stack);
        if (wood == null) return;
        Component mod = ModdedBarrelBlock.modName(wood);
        if (!BuiltInRegistries.BLOCK.containsKey(wood)) mod = Component.translatable("tooltip.simplebarrels.missing_mod", mod);
        lines.add(mod.copy().withStyle(ChatFormatting.GRAY));
    }
}
