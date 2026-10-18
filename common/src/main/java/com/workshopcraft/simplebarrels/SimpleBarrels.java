package com.workshopcraft.simplebarrels;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SimpleBarrels {
    public static final String MOD_ID = "simplebarrels";
    public static final String MOD_NAME = "Simple Barrels";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    private SimpleBarrels() {}

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
