package com.workshopcraft.simplebarrels.handlers;

import com.workshopcraft.simplebarrels.Reference.Blocks;
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
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBarrel.class, new TESRBlockBarrel());
	}
}
