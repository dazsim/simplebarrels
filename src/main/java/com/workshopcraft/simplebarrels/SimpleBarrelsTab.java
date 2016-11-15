package com.workshopcraft.simplebarrels;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class SimpleBarrelsTab extends CreativeTabs{

	
	public SimpleBarrelsTab(String tabName)
	{
		super(tabName);
	}
	
	@Override
	public Item getTabIconItem() {
		return Item.getItemFromBlock(SimpleBarrels.barrels.get(0));
	}
}
