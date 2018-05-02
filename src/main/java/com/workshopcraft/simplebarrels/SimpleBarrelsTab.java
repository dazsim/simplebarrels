package com.workshopcraft.simplebarrels;

import com.workshopcraft.simplebarrels.libraries.BlockLibrary;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class SimpleBarrelsTab extends CreativeTabs{

	private ItemStack tabIcon = null;
	
	SimpleBarrelsTab()
	{
		super(Reference.TabLebel);
	}
	
	@Override
	public ItemStack getTabIconItem() {
		if (tabIcon == null) {
			tabIcon = new ItemStack(BlockLibrary.barrel);
		}
		return tabIcon;
	}
}
