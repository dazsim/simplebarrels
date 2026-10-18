package com.workshopcraft.simplebarrels.registry;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public enum BarrelWood {
    OAK("oak", MapColor.WOOD, SoundType.WOOD),
    SPRUCE("spruce", MapColor.PODZOL, SoundType.WOOD),
    BIRCH("birch", MapColor.SAND, SoundType.WOOD),
    JUNGLE("jungle", MapColor.DIRT, SoundType.WOOD),
    ACACIA("acacia", MapColor.COLOR_ORANGE, SoundType.WOOD),
    DARK_OAK("dark_oak", MapColor.COLOR_BROWN, SoundType.WOOD),
    MANGROVE("mangrove", MapColor.COLOR_RED, SoundType.WOOD),
    CHERRY("cherry", MapColor.TERRACOTTA_WHITE, SoundType.CHERRY_WOOD),
    BAMBOO("bamboo", MapColor.COLOR_YELLOW, SoundType.BAMBOO_WOOD),
    CRIMSON("crimson", MapColor.CRIMSON_STEM, SoundType.NETHER_WOOD),
    WARPED("warped", MapColor.WARPED_STEM, SoundType.NETHER_WOOD);

    public final String woodName;
    public final MapColor mapColor;
    public final SoundType sound;

    BarrelWood(String woodName, MapColor mapColor, SoundType sound) {
        this.woodName = woodName;
        this.mapColor = mapColor;
        this.sound = sound;
    }

    public String blockId() {
        return woodName + "_barrel";
    }
}
