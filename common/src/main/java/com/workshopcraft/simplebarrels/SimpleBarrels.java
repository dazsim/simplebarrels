package com.workshopcraft.simplebarrels;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class SimpleBarrels {
    public static final String MOD_ID = "simplebarrels";
    public static final TagKey<Item> DOLLY = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("workshopcore", "dolly"));

    private SimpleBarrels() {}

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
