package com.workshopcraft.simplebarrels.libraries;

import javax.annotation.Nonnull;

import com.workshopcraft.simplebarrels.Reference;
import com.workshopcraft.simplebarrels.blocks.BlockBarrel;

import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@SuppressWarnings("ALL")
@ObjectHolder(Reference.MOD_ID)
public class BlockLibrary {

	@Nonnull
	public static final BlockBarrel barrel;
	
	static {
			barrel = null;
	}
}
