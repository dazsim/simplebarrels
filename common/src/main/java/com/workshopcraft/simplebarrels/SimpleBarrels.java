package com.workshopcraft.simplebarrels;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SimpleBarrels {
    public static final String MOD_ID = "simplebarrels";
    public static final String MOD_NAME = "Simple Barrels";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final TagKey<Item> DOLLY = TagKey.create(Registries.ITEM, new ResourceLocation("workshopcore", "dolly"));

    private SimpleBarrels() {}

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
