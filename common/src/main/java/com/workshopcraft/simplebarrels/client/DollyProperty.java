package com.workshopcraft.simplebarrels.client;

import com.workshopcraft.simplebarrels.SimpleBarrels;
import com.workshopcraft.simplebarrels.item.CarriedBlock;
import net.minecraft.world.level.block.Blocks;
import com.workshopcraft.simplebarrels.registry.BarrelWood;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

// picks the dolly_<wood> model override, see tools/gen_resources.py
public final class DollyProperty {
    public static final ResourceLocation ID = SimpleBarrels.id("carried");

    private DollyProperty() {}

    public static float get(ItemStack stack) {
        CarriedBlock c = stack.get(ModContent.CARRIED_BLOCK);
        return c == null ? 0 : value(c.block());
    }

    static float value(Block block) {
        for (BarrelWood wood : BarrelWood.values()) {
            if (ModContent.BARRELS.get(wood) == block) return (wood.ordinal() + 1) / 16F;
        }
        if (block == Blocks.CHEST) return 12 / 16F;
        if (block == Blocks.TRAPPED_CHEST) return 13 / 16F;
        if (block == Blocks.ENDER_CHEST) return 14 / 16F;
        if (block == ModContent.MODDED_BARREL) return 15 / 16F;
        return 0;
    }
}
