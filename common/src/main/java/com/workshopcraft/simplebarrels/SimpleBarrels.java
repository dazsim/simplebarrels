package com.workshopcraft.simplebarrels;

import net.minecraft.resources.Identifier;

public final class SimpleBarrels {
    public static final String MOD_ID = "simplebarrels";

    private SimpleBarrels() {}

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
