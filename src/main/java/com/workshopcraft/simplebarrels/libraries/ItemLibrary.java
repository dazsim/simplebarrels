package com.workshopcraft.simplebarrels.libraries;

import javax.annotation.Nonnull;

import com.workshopcraft.simplebarrels.Reference;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@SuppressWarnings("ALL")
@ObjectHolder(Reference.MOD_ID)
public class ItemLibrary {
	@Nonnull
	public static final ItemBlock barrel;
	
	static {
		barrel = null;
	}
}
