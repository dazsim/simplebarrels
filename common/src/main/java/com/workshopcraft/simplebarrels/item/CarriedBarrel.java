package com.workshopcraft.simplebarrels.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public record CarriedBarrel(Block block, ItemStack item, int count, BarrelUpgrades upgrades) {
    public static final Codec<CarriedBarrel> CODEC = RecordCodecBuilder.create(i -> i.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(CarriedBarrel::block),
            ItemStack.OPTIONAL_CODEC.optionalFieldOf("item", ItemStack.EMPTY).forGetter(CarriedBarrel::item),
            Codec.INT.optionalFieldOf("count", 0).forGetter(CarriedBarrel::count),
            BarrelUpgrades.CODEC.optionalFieldOf("upgrades", BarrelUpgrades.NONE).forGetter(CarriedBarrel::upgrades)
    ).apply(i, CarriedBarrel::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, CarriedBarrel> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC);

    // ItemStack has no value equality, components need it
    @Override
    public boolean equals(Object o) {
        return o instanceof CarriedBarrel c && c.block == block && c.count == count && c.upgrades.equals(upgrades)
                && ItemStack.isSameItemSameComponents(c.item, item);
    }

    @Override
    public int hashCode() {
        return block.hashCode() * 31 + count;
    }
}
