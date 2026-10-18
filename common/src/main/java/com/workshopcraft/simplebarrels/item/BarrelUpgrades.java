package com.workshopcraft.simplebarrels.item;

import com.workshopcraft.simplebarrels.block.BarrelBlockEntity;
import net.minecraft.nbt.CompoundTag;

public record BarrelUpgrades(boolean hideCount, boolean hideItem, int capacity) {
    public static final BarrelUpgrades NONE = new BarrelUpgrades(false, false, BarrelBlockEntity.BASE_CAPACITY);

    public void save(CompoundTag tag) {
        if (hideCount) tag.putBoolean("HideCount", true);
        if (hideItem) tag.putBoolean("HideItem", true);
        if (capacity != BarrelBlockEntity.BASE_CAPACITY) tag.putInt("Capacity", capacity);
    }

    public static BarrelUpgrades load(CompoundTag tag) {
        int cap = tag.contains("Capacity") ? tag.getInt("Capacity") : BarrelBlockEntity.BASE_CAPACITY;
        return new BarrelUpgrades(tag.getBoolean("HideCount"), tag.getBoolean("HideItem"), cap);
    }
}
