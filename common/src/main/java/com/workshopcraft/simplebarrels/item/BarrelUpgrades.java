package com.workshopcraft.simplebarrels.item;

import com.workshopcraft.simplebarrels.block.BarrelBlockEntity;
import net.minecraft.nbt.CompoundTag;

public record BarrelUpgrades(boolean showCount, boolean showItem, int capacity) {
    public static final BarrelUpgrades NONE = new BarrelUpgrades(false, false, BarrelBlockEntity.BASE_CAPACITY);

    public void save(CompoundTag tag) {
        if (showCount) tag.putBoolean("ShowCount", true);
        if (showItem) tag.putBoolean("ShowItem", true);
        if (capacity != BarrelBlockEntity.BASE_CAPACITY) tag.putInt("Capacity", capacity);
    }

    public static BarrelUpgrades load(CompoundTag tag) {
        int cap = tag.contains("Capacity") ? tag.getInt("Capacity") : BarrelBlockEntity.BASE_CAPACITY;
        return new BarrelUpgrades(tag.getBoolean("ShowCount"), tag.getBoolean("ShowItem"), cap);
    }
}
