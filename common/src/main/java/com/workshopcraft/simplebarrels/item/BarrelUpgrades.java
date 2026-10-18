package com.workshopcraft.simplebarrels.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.workshopcraft.simplebarrels.block.BarrelBlockEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record BarrelUpgrades(boolean comparator, boolean frame, int capacity) {
    public static final BarrelUpgrades NONE = new BarrelUpgrades(false, false, BarrelBlockEntity.BASE_CAPACITY);

    public static final Codec<BarrelUpgrades> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.BOOL.optionalFieldOf("comparator", false).forGetter(BarrelUpgrades::comparator),
            Codec.BOOL.optionalFieldOf("frame", false).forGetter(BarrelUpgrades::frame),
            Codec.INT.optionalFieldOf("capacity", BarrelBlockEntity.BASE_CAPACITY).forGetter(BarrelUpgrades::capacity)
    ).apply(i, BarrelUpgrades::new));

    public static final StreamCodec<ByteBuf, BarrelUpgrades> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, BarrelUpgrades::comparator,
            ByteBufCodecs.BOOL, BarrelUpgrades::frame,
            ByteBufCodecs.VAR_INT, BarrelUpgrades::capacity,
            BarrelUpgrades::new);

    public boolean isNone() { return equals(NONE); }
}
