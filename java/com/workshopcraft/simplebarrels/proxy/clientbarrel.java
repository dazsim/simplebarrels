package com.workshopcraft.simplebarrels.proxy;

import com.workshopcraft.simplebarrels.SimpleBarrels;
import com.workshopcraft.simplebarrels.items.ItemUpgradeComparator;
import com.workshopcraft.simplebarrels.items.ItemUpgradeItemFrame;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

public class clientbarrel extends barrelcommonproxy {
	
	@Override
	public void init(FMLInitializationEvent e) {
		SimpleBarrels.oakBarrel.initModel();
		
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        //renderItem.getItemModelMesher().register(Item.getItemFromBlock(blockBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(blockBarrel).getUnlocalizedName().substring(5),"inventory"));
        renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.oakBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.oakBarrel).getUnlocalizedName().substring(5),"inventory"));
        renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.spruceBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.spruceBarrel).getUnlocalizedName().substring(5),"inventory"));
        renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.birchBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.birchBarrel).getUnlocalizedName().substring(5),"inventory"));
        renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.jungleBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.jungleBarrel).getUnlocalizedName().substring(5),"inventory"));
        renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.acaciaBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.acaciaBarrel).getUnlocalizedName().substring(5),"inventory"));
        renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.darkoakBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.darkoakBarrel).getUnlocalizedName().substring(5),"inventory"));
        
        
        renderItem.getItemModelMesher().register((Item)SimpleBarrels.upgradeComparator, 0,new ModelResourceLocation(SimpleBarrels.MODID+ ":"+((ItemUpgradeComparator) SimpleBarrels.upgradeComparator).getName(),"inventory"));
        renderItem.getItemModelMesher().register((Item)SimpleBarrels.upgradeItemFrame, 0,new ModelResourceLocation(SimpleBarrels.MODID+ ":"+((ItemUpgradeItemFrame) SimpleBarrels.upgradeItemFrame).getName(),"inventory"));
        if (Loader.isModLoaded("forestry"))
        {
			//RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
			
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestryacaciaBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestryacaciaBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrybalsaBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrybalsaBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrybaobabBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrybaobabBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrycherryBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrycherryBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrychestnutBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrychestnutBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrycitrusBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrycitrusBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrycocoboloBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrycocoboloBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestryebonyBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestryebonyBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrygiganteumBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrygiganteumBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrygreenheartBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrygreenheartBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestryipeBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestryipeBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrykapokBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrykapokBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrylarchBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrylarchBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrylimeBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrylimeBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrymahoeBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrymahoeBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrymahoganyBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrymahoganyBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrymapleBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrymapleBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrypadaukBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrypadaukBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrypalmBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrypalmBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrypapayaBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrypapayaBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrypineBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrypineBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestryplumBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestryplumBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrypoplarBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrypoplarBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrysequoiaBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrysequoiaBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestryteakBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestryteakBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrywalnutBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrywalnutBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrywengeBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrywengeBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestrywillowBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestrywillowBarrel).getUnlocalizedName().substring(5),"inventory"));
        	renderItem.getItemModelMesher().register(Item.getItemFromBlock(SimpleBarrels.forestryzebrawoodBarrel), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(SimpleBarrels.forestryzebrawoodBarrel).getUnlocalizedName().substring(5),"inventory"));
        	
        }
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}

}
