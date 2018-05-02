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

public class ItemUpgradeMark1Compressor extends Item{

	private String name="";
	
	public ItemUpgradeMark1Compressor()
	{
		super();
		setMaxStackSize(64);
	
	}
	
	public ItemUpgradeMark1Compressor(String name)
	{
		super();
		setMaxStackSize(64);
	
	}
	
	public String getName()
	{
		return name;
		//this.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ)
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn,World worldIn,BlockPos pos,EnumHand hand,EnumFacing facing,float hitX,float hitY,float hitZ)
	{
		ItemStack stack = playerIn.getHeldItem(hand);
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
						if (t2.itemHandler.size==4096)
						{
							t2.itemHandler.size = 8192;
							stack.shrink(1);
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
