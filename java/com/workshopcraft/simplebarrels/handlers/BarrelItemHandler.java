package com.workshopcraft.simplebarrels.handlers;

import com.workshopcraft.simplebarrels.blocks.BlockBarrel;
import com.workshopcraft.simplebarrels.tiles.TileEntityBarrel;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;

public class BarrelItemHandler implements IItemHandler{
	
	public ItemStack[] barrelContents = new ItemStack[1];
	public TileEntityBarrel t;
	public int count = 0;
	public int size = 4096;
	@Override
	public int getSlots() 
	{
		int a = size;
		if (barrelContents[0]!=null)
		{
			a =(int) (a / this.barrelContents[0].getMaxStackSize());
		}
		else
		{
			a = a / 64;
		}
		return a;
	}

	@Override
	public ItemStack getStackInSlot(int slot) 
	{
		if (this.barrelContents[0]!= null)
		{
		if (slot < (int)(count/this.barrelContents[0].getMaxStackSize()))
		{
			ItemStack i = new ItemStack(this.barrelContents[0].getItem(),64,this.barrelContents[0].getItemDamage());
    		i.setTagCompound(barrelContents[0].getTagCompound()); 
    		return i;
		} else if (slot == (int)(count/this.barrelContents[0].getMaxStackSize()))
		{
			int j = (int)(count%this.barrelContents[0].getMaxStackSize());
			ItemStack i = new ItemStack(this.barrelContents[0].getItem(),j,this.barrelContents[0].getItemDamage());
    		i.setTagCompound(barrelContents[0].getTagCompound()); 
    		
    		return i;
		} else
		{
			return null;
		}
		} else
		{
			return null;
		}
		
		
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) 
	{
		
		
		if (count == 0)
		{
			if (!simulate)
			{
			this.barrelContents[0]=stack;
			
				count = stack.stackSize;
				BlockBarrel.updateBarrel(t);
			}
			return null;
		}
		if (count == size)
		{
			//barrel is full
			return stack;
		}
		if (this.barrelContents[0].isItemEqual(stack))
		{
			NBTTagCompound t1 = this.barrelContents[0].getTagCompound();
			Boolean match = false;
			if (t1!=null)
			{
				if (stack !=null)
				{
					if (stack.getTagCompound()!=null)
					{
						if (t1.equals(stack.getTagCompound()))
						{
							match = true;
						}
					}
				}
				
			
			}
			if ((t1==null) && (stack.getTagCompound()==null))
			{
				match = true;
			}
			if (match)
			{
				if (stack.stackSize+count<=size)
				{
					//input stack will not fill barrel
					if (!simulate)
					{
						count+=stack.stackSize;
						BlockBarrel.updateBarrel(t);
					}
					return null;
				} else if (stack.stackSize+count>size)
				{
					//input stack will fill barrel
					if (!simulate)
					{
						int a = size - count;
						count = size;
						stack.stackSize -= a;
						BlockBarrel.updateBarrel(t);
					}
					return stack;
			
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		// we ignore slot as internal to the barrel we dont actually care
		int a = 0;
		if (count == 0)
		{
			return null;
		}
		else
		{
			if (simulate)
			{
				
				if (count >= amount)
				{
					a = amount;
				} else
				{
					a = count;
				}
				ItemStack i = new ItemStack(this.barrelContents[0].getItem(),a,this.barrelContents[0].getItemDamage());
	    		i.setTagCompound(barrelContents[0].getTagCompound());
	    		return i;
			} else
			{
				if (count >= amount)
				{
					a = amount;
				} else
				{
					a = count;
				}
				ItemStack i = new ItemStack(this.barrelContents[0].getItem(),a,this.barrelContents[0].getItemDamage());
	    		i.setTagCompound(barrelContents[0].getTagCompound());
	    		count -= a;
	    		if (count == 0)
	    		{
	    			this.barrelContents[0]=null;
	    		}
	    		BlockBarrel.updateBarrel(t);
	    		return i;
			}
		}
		
	}
	
	
}
