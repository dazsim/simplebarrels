package com.workshopcraft.simplebarrels.handlers;

import com.workshopcraft.simplebarrels.Reference.Blocks;
import com.workshopcraft.simplebarrels.Reference.Items;
import com.workshopcraft.simplebarrels.libraries.ItemLibrary;
import com.workshopcraft.simplebarrels.tesr.TESRBlockBarrel;
import com.workshopcraft.simplebarrels.tiles.TileEntityBarrel;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(Side.CLIENT)
public class RenderingHandler {
	
	
	@SubscribeEvent
	public static void onRenderingReady(ModelRegistryEvent evt)
	{
		ModelLoader.setCustomModelResourceLocation(
				ItemLibrary.barrel,
				0,
				new ModelResourceLocation(Blocks.barrel, "inventory")
		);
		
		ModelLoader.setCustomModelResourceLocation(
				ItemLibrary.itemdolly,
				0,
				new ModelResourceLocation(Items.itemdolly, "inventory")
		);
		ModelLoader.setCustomModelResourceLocation(
				ItemLibrary.itemupgradecomparator,
				0,
				new ModelResourceLocation(Items.itemupgradecomparator, "inventory")
		);
		ModelLoader.setCustomModelResourceLocation(
				ItemLibrary.itemupgradeitemframe,
				0,
				new ModelResourceLocation(Items.itemupgradeitemframe, "inventory")
		);
		ModelLoader.setCustomModelResourceLocation(
				ItemLibrary.itemupgrademark1compressor,
				0,
				new ModelResourceLocation(Items.itemupgrademark1compressor, "inventory")
		);
		ModelLoader.setCustomModelResourceLocation(
				ItemLibrary.itemupgrademark2compressor,
				0,
				new ModelResourceLocation(Items.itemupgrademark2compressor, "inventory")
		);
		ModelLoader.setCustomModelResourceLocation(
				ItemLibrary.itemupgrademark3compressor,
				0,
				new ModelResourceLocation(Items.itemupgrademark3compressor, "inventory")
		);
		ModelLoader.setCustomModelResourceLocation(
				ItemLibrary.itemupgrademark4compressor,
				0,
				new ModelResourceLocation(Items.itemupgrademark4compressor, "inventory")
		);
		
		
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBarrel.class, new TESRBlockBarrel());
	}
}
