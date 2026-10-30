package com.workshopcraft.simplebarrels.item;

import com.workshopcraft.simplebarrels.block.FluidBarrelBlock;
import com.workshopcraft.simplebarrels.block.FluidBarrelBlockEntity;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class FluidBarrelItem extends BlockItem {
    private final FluidBarrelBlock barrel;

    public FluidBarrelItem(FluidBarrelBlock block, Properties props) {
        super(block, props);
        this.barrel = block;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, TooltipDisplay display, Consumer<Component> lines, TooltipFlag flag) {
        FluidContents c = stack.get(ModContent.FLUID);
        int cap = barrel.tier.buckets * 1000;
        if (c == null || c.amount() <= 0) {
            lines.accept(Component.translatable("tooltip.simplebarrels.fluid_barrel_empty", barrel.tier.buckets).withStyle(ChatFormatting.GRAY));
            return;
        }
        lines.accept(Component.translatable("tooltip.simplebarrels.fluid_barrel", FluidBarrelBlockEntity.fluidName(c.fluid()),
                c.amount() / FluidBarrelBlockEntity.MB, cap).withStyle(ChatFormatting.GRAY));
    }
}
