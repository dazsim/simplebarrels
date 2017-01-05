package com.workshopcraft.simplebarrels.proxy;

import com.workshopcraft.simplebarrels.SimpleBarrels;
import com.workshopcraft.simplebarrels.items.ItemUpgradeComparator;
import com.workshopcraft.simplebarrels.items.ItemUpgradeItemFrame;
import com.workshopcraft.simplebarrels.items.ItemUpgradeMark1Compressor;
import com.workshopcraft.simplebarrels.items.ItemUpgradeMark2Compressor;
import com.workshopcraft.simplebarrels.items.ItemUpgradeMark3Compressor;
import com.workshopcraft.simplebarrels.items.ItemUpgradeMark4Compressor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

public class clientbarrel extends barrelcommonproxy {
	
	@Override
	public void init(FMLInitializationEvent e) {
		//SimpleBarrels.oakBarrel.initModel();
		SimpleBarrels.barrels.get(0).initModel();
		
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		SimpleBarrels.bFactory.clientInit();
		/*
        //renderItem.getItemModelMesher().register(Item.getItemFromBlock(blockBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(blockBarrel).getUnlocalizedName().substring(5),"inventory"));
        renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.oakBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.oakBarrel).getUnlocalizedName().substring(5),"inventory"));
        renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.spruceBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.spruceBarrel).getUnlocalizedName().substring(5),"inventory"));
        renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.birchBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.birchBarrel).getUnlocalizedName().substring(5),"inventory"));
        renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.jungleBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.jungleBarrel).getUnlocalizedName().substring(5),"inventory"));
        renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.acaciaBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.acaciaBarrel).getUnlocalizedName().substring(5),"inventory"));
        renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.darkoakBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.darkoakBarrel).getUnlocalizedName().substring(5),"inventory"));
        */
        
        renderItem.getItemModelMesher().register((Item)SimpleBarrels.upgradeComparator, 0,new ModelResourceLocation(SimpleBarrels.MODID+ ":"+((ItemUpgradeComparator) SimpleBarrels.upgradeComparator).getName(),"inventory"));
        renderItem.getItemModelMesher().register((Item)SimpleBarrels.upgradeItemFrame, 0,new ModelResourceLocation(SimpleBarrels.MODID+ ":"+((ItemUpgradeItemFrame) SimpleBarrels.upgradeItemFrame).getName(),"inventory"));
        
        renderItem.getItemModelMesher().register((Item)SimpleBarrels.upgradeMark1Compressor, 0,new ModelResourceLocation(SimpleBarrels.MODID+ ":"+((ItemUpgradeMark1Compressor) SimpleBarrels.upgradeMark1Compressor).getName(),"inventory"));
        renderItem.getItemModelMesher().register((Item)SimpleBarrels.upgradeMark2Compressor, 0,new ModelResourceLocation(SimpleBarrels.MODID+ ":"+((ItemUpgradeMark2Compressor) SimpleBarrels.upgradeMark2Compressor).getName(),"inventory"));
        renderItem.getItemModelMesher().register((Item)SimpleBarrels.upgradeMark3Compressor, 0,new ModelResourceLocation(SimpleBarrels.MODID+ ":"+((ItemUpgradeMark3Compressor) SimpleBarrels.upgradeMark3Compressor).getName(),"inventory"));
        renderItem.getItemModelMesher().register((Item)SimpleBarrels.upgradeMark4Compressor, 0,new ModelResourceLocation(SimpleBarrels.MODID+ ":"+((ItemUpgradeMark4Compressor) SimpleBarrels.upgradeMark4Compressor).getName(),"inventory"));
        
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}

}
