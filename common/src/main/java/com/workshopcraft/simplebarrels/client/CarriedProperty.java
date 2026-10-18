package com.workshopcraft.simplebarrels.client;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.workshopcraft.simplebarrels.SimpleBarrels;
import com.workshopcraft.simplebarrels.item.CarriedBlock;
import com.workshopcraft.simplebarrels.registry.BarrelWood;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.Nullable;

// picks the dolly model by what it carries, see tools/gen_resources.py
public record CarriedProperty() implements SelectItemModelProperty<String> {
    public static final Identifier ID = SimpleBarrels.id("carried");
    public static final SelectItemModelProperty.Type<CarriedProperty, String> TYPE =
            SelectItemModelProperty.Type.create(MapCodec.unit(new CarriedProperty()), Codec.STRING);

    @Override
    public @Nullable String get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner, int seed, ItemDisplayContext ctx) {
        CarriedBlock c = stack.get(ModContent.CARRIED_BLOCK);
        return c == null ? null : key(c.block());
    }

    static @Nullable String key(Block block) {
        for (BarrelWood wood : BarrelWood.values()) {
            if (ModContent.BARRELS.get(wood) == block) return wood.woodName;
        }
        if (block == Blocks.CHEST) return "chest";
        if (block == Blocks.TRAPPED_CHEST) return "trapped_chest";
        if (block == Blocks.ENDER_CHEST) return "ender_chest";
        if (block == ModContent.MODDED_BARREL) return "modded";
        return null;
    }

    @Override
    public SelectItemModelProperty.Type<CarriedProperty, String> type() { return TYPE; }

    @Override
    public Codec<String> valueCodec() { return Codec.STRING; }
}
