package com.workshopcraft.simplebarrels.client;

import com.workshopcraft.simplebarrels.SimpleBarrels;
import com.workshopcraft.simplebarrels.item.CarriedBarrel;
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
        CarriedBarrel c = stack.get(ModContent.CARRIED_BARREL);
        return c == null ? 0 : value(c.block());
    }

    static float value(Block block) {
        for (BarrelWood wood : BarrelWood.values()) {
            if (ModContent.BARRELS.get(wood) == block) return (wood.ordinal() + 1) / 16F;
        }
        return 0;
    }
}
