package com.workshopcraft.simplebarrels.proxy;

import com.workshopcraft.simplebarrels.SimpleBarrels;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

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
        
	}
	
	

}
