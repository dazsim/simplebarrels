package com.workshopcraft.simplebarrels.libraries;

import javax.annotation.Nonnull;

import com.workshopcraft.simplebarrels.Reference;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@SuppressWarnings("ALL")
@ObjectHolder(Reference.MOD_ID)
public class ItemLibrary {
	@Nonnull
	public static final ItemBlock barrel;
	
	
	@Nonnull
	public static final Item itemdolly;
	@Nonnull
	public static final Item itemupgradecomparator;
	@Nonnull
	public static final Item itemupgrademark1compressor;
	@Nonnull
	public static final Item itemupgrademark2compressor;
	@Nonnull
	public static final Item itemupgrademark3compressor;
	@Nonnull
	public static final Item itemupgrademark4compressor;
	@Nonnull
	public static final Item itemupgradeitemframe;
	static {
		barrel = null;
		itemdolly = null;
		itemupgradecomparator = null;
		itemupgrademark1compressor = null;
		itemupgrademark2compressor = null;
		itemupgrademark3compressor = null;
		itemupgrademark4compressor = null;
		itemupgradeitemframe = null;
	}
}
