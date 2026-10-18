package com.workshopcraft.simplebarrels.item;

import com.workshopcraft.simplebarrels.block.ModdedBarrelBlock;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class ModdedBarrelItem extends BlockItem {
    public ModdedBarrelItem(Block block, Properties props) {
        super(block, props);
    }

    @Override
    public Component getName(ItemStack stack) {
        return ModdedBarrelBlock.name(stack.get(ModContent.WOOD));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, TooltipDisplay display, Consumer<Component> lines, TooltipFlag flag) {
        Identifier wood = stack.get(ModContent.WOOD);
        if (wood == null) return;
        Component mod = ModdedBarrelBlock.modName(wood);
        if (!BuiltInRegistries.BLOCK.containsKey(wood)) mod = Component.translatable("tooltip.simplebarrels.missing_mod", mod);
        lines.accept(mod.copy().withStyle(ChatFormatting.GRAY));
    }
}
