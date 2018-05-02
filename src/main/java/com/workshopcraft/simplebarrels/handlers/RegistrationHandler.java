package com.workshopcraft.simplebarrels.handlers;

import com.workshopcraft.simplebarrels.Reference;
import com.workshopcraft.simplebarrels.Reference.Blocks;
import com.workshopcraft.simplebarrels.Reference.Items;
import com.workshopcraft.simplebarrels.blocks.BlockBarrel;
import com.workshopcraft.simplebarrels.items.ItemDolly;
import com.workshopcraft.simplebarrels.items.ItemUpgradeComparator;
import com.workshopcraft.simplebarrels.libraries.BlockLibrary;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@SuppressWarnings("SameParameterValue")
@EventBusSubscriber
public class RegistrationHandler {

	@SubscribeEvent
	public static void onRegisterBlocks(Register<Block> registryEvent) {
		//Register Blocks and TE here
		final IForgeRegistry<Block> registry = registryEvent.getRegistry();

		registerBlock(registry, new BlockBarrel(), Blocks.barrel);
	}
	
	@SubscribeEvent
	public static void onRegisterItems(Register<Item> registryEvent) {
		final IForgeRegistry<Item> registry = registryEvent.getRegistry();

		registerItemFromBlock(registry, BlockLibrary.barrel);
		//registerItemFromBlock(registry, BlockLibrary.controller);

		registerItem(registry,(Item)new ItemDolly(), Items.itemdolly,true);
		registerItem(registry,(Item)new ItemUpgradeComparator(), Items.itemupgradecomparator,true);
		//registerItem(registry, new ItemPunchCardBlank(), Items.punchcardblank, true);
		//registerItem(registry, new ItemPunchCardWritten(), Items.punchcardwritten, false);
		
	}
	
	private static void registerBlock(IForgeRegistry<Block> registry, Block block, ResourceLocation registryName)
	{
		registry.register(block
				.setRegistryName(registryName)
				.setCreativeTab(Reference.CreativeTab)
				.setUnlocalizedName(registryName.toString())
		);
	}

	private static void registerItem(IForgeRegistry<Item> registry, Item item, ResourceLocation registryName, boolean showInCreativeTab)
	{
		item.setRegistryName(registryName)
			.setUnlocalizedName(registryName.toString());

		if (showInCreativeTab) {
			item.setCreativeTab(Reference.CreativeTab);
		}

		registry.register(item);
	}

	private static void registerItemFromBlock(IForgeRegistry<Item> registry, Block block)
	{
		final ResourceLocation registryName = block.getRegistryName();
		assert registryName != null;

		registry.register(new ItemBlock(block)
			.setRegistryName(registryName)
			.setUnlocalizedName(registryName.toString()));
	}
}
