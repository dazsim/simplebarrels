package com.workshopcraft.simplebarrels.handlers;

import com.workshopcraft.simplebarrels.tiles.TileEntityBarrel;

import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BarrelHandler {

	
	
	public void updateBarrel(TileEntityBarrel t)
	{
		NBTTagCompound compound = new NBTTagCompound();
		t.writeToNBT(compound );
		t.markDirty();
		t.getWorld().notifyBlockUpdate(t.getPos(), t.getWorld().getBlockState(t.getPos()), t.getWorld().getBlockState(t.getPos()), 3);
		
	}
	
	public BlockPos sideToOffset(PlayerInteractEvent event)
	{
		EnumFacing f = event.getFace();
		if (f == null)
		{
			return null;
		}
		if (f == EnumFacing.NORTH)
		{
			return new BlockPos(0,0,-1.0);
		}
		if (f == EnumFacing.WEST)
		{
			return new BlockPos(-1.0,0,0);
		}
		if (f == EnumFacing.SOUTH)
		{
			return new BlockPos(0,0,1.0);
		}
		if (f == EnumFacing.EAST)
		{
			return new BlockPos(1.0,0,0);
		}
		
		
		
		if (f == EnumFacing.DOWN)
		{
			return new BlockPos(0,-1.0,0);
		}
		if (f == EnumFacing.UP)
		{
			return new BlockPos(0,1.0,0);
		}
		return null;
	}
	
	@SubscribeEvent
	public void LeftClickBlock(PlayerInteractEvent event)
	{
		int dx,dy,dz;
		
		//this is for left click.
		if (!event.getWorld().isRemote)
		{
		TileEntity te = (event.getWorld().getTileEntity(event.getPos()));
		
		if (event instanceof LeftClickBlock)
		{
			
			if (te instanceof TileEntityBarrel)
			{
				
				TileEntityBarrel te2 = (TileEntityBarrel) te;
				
				if (te2.itemHandler.extractItem(0, 1, true) == null)
				{
					//do nothing
					
					return;
				
				} else
				if (event.getEntityPlayer().isSneaking())
				{
					if (te2.itemHandler.count>0)
					{
						/*** PULL ONE ITEM FROM BARREL ***/
						
						ItemStack istack = te2.itemHandler.extractItem(0, 1, false);
						BlockPos o = sideToOffset(event);
						
						InventoryHelper.spawnItemStack(event.getWorld(), event.getPos().getX()+o.getX(), event.getPos().getY()+o.getY(), event.getPos().getZ()+o.getZ(),istack);
					
						updateBarrel(te2);
						event.setCanceled(true);
						return;
					}
				} else 
				{
					/*** PULL A STACK FROM THE BARREL ***/
					ItemStack istack = te2.itemHandler.extractItem(0, te2.itemHandler.extractItem(0, 1, true).getMaxStackSize(), false);
					BlockPos o = sideToOffset(event);
					InventoryHelper.spawnItemStack(event.getWorld(), event.getPos().getX()+o.getX(), event.getPos().getY()+o.getY(), event.getPos().getZ()+o.getZ(),istack);
					updateBarrel(te2);
					event.setCanceled(true);
					return;
				}
				
			}
		}
	}}
	
}
