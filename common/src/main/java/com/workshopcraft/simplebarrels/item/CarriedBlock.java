package com.workshopcraft.simplebarrels.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.block.Block;

// block on the dolly plus its saved block entity data
public record CarriedBlock(Block block, CompoundTag data) {
    public static final Codec<CarriedBlock> CODEC = RecordCodecBuilder.create(i -> i.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(CarriedBlock::block),
            CompoundTag.CODEC.optionalFieldOf("data", new CompoundTag()).forGetter(CarriedBlock::data)
    ).apply(i, CarriedBlock::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, CarriedBlock> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.registry(net.minecraft.core.registries.Registries.BLOCK), CarriedBlock::block,
            ByteBufCodecs.COMPOUND_TAG, CarriedBlock::data,
            CarriedBlock::new);
}
