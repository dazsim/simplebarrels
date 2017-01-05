package com.workshopcraft.simplebarrels.items;

import com.workshopcraft.simplebarrels.SimpleBarrels;
import com.workshopcraft.simplebarrels.blocks.BlockBarrel;
import com.workshopcraft.simplebarrels.tiles.TileEntityBarrel;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemUpgradeMark3Compressor extends Item{

	private String name="";
	
	public ItemUpgradeMark3Compressor()
	{
		if (name.equals(""))
		{
			// do nothing
		} else
		{
			GameRegistry.registerItem(this, name);
		}
		setUnlocalizedName(name);
		setMaxStackSize(64);
		setCreativeTab(SimpleBarrels.tabSimpleBarrels);
		
		
	}
	public ItemUpgradeMark3Compressor(String uname)
	{
		super();
		GameRegistry.registerItem(this,uname);
		name = uname;
		this.setUnlocalizedName(name);
		setCreativeTab(SimpleBarrels.tabSimpleBarrels);
	}
	
	public String getName()
	{
		return name;
		//this.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ)
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack,EntityPlayer playerIn,World worldIn,BlockPos pos,EnumHand hand,EnumFacing facing,float hitX,float hitY,float hitZ)
	{
		if (!worldIn.isRemote)
		{
			if (playerIn.isSneaking())
			{
				TileEntity t = worldIn.getTileEntity(pos);
				if (t!=null)
				{
					if ((TileEntityBarrel)t instanceof TileEntityBarrel)
					{
						TileEntityBarrel t2 = (TileEntityBarrel) t;
						if (t2.itemHandler.size==16384)
						{
							t2.itemHandler.size = 32768;
							stack.stackSize-=1;
							Block b = (t2.getBlockType());
							
							((BlockBarrel) b).updateBarrel((TileEntityBarrel)worldIn.getTileEntity(pos));
							
							return EnumActionResult.SUCCESS;
						}
					}
				}
			}
		}
		return EnumActionResult.FAIL;
	}
	
}
