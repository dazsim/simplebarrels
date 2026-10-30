package com.workshopcraft.simplebarrels.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.material.Fluid;

// fluid kept on a broken fluid barrel, amount in droplets (81 per mB)
public record FluidContents(Fluid fluid, long amount) {
    public static final Codec<FluidContents> CODEC = RecordCodecBuilder.create(i -> i.group(
            BuiltInRegistries.FLUID.byNameCodec().fieldOf("fluid").forGetter(FluidContents::fluid),
            Codec.LONG.fieldOf("amount").forGetter(FluidContents::amount)
    ).apply(i, FluidContents::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, FluidContents> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.registry(Registries.FLUID), FluidContents::fluid,
            ByteBufCodecs.VAR_LONG.<RegistryFriendlyByteBuf>cast(), FluidContents::amount,
            FluidContents::new);
}
