package com.workshopcraft.simplebarrels.registry;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public enum FluidTier {
    COPPER("copper", 16, MapColor.COLOR_ORANGE, SoundType.COPPER),
    IRON("iron", 64, MapColor.METAL, SoundType.METAL),
    STEEL("steel", 256, MapColor.COLOR_GRAY, SoundType.METAL);

    public final String name;
    public final int buckets;
    public final MapColor mapColor;
    public final SoundType sound;

    FluidTier(String name, int buckets, MapColor mapColor, SoundType sound) {
        this.name = name;
        this.buckets = buckets;
        this.mapColor = mapColor;
        this.sound = sound;
    }

    public String blockId() {
        return name + "_fluid_barrel";
    }
}
