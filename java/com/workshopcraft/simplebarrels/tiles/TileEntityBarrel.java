package com.workshopcraft.simplebarrels.tiles;

import javax.annotation.Nullable;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class TileEntityBarrel extends TileEntity implements ITickable, IInventory
{
    public ItemStack[] barrelContents = new ItemStack[1];
    public NBTTagCompound tags = new NBTTagCompound();
    public IBlockState state;
    public Integer size,count;
    public Boolean itemIsBlock=false;
    
    private int ticksSinceSync;
    private String customName;
    /** The number of players currently using this chest */
    public int numPlayersUsing;
    public int maxsize = 4096;
    public TileEntityBarrel()
    {
    	this.size = 4096;
    	
    	
    }
    @Override
   public void onLoad()
   {
	   if (FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT)
        {
        	
        	//SimpleBarrels.BarrelNet.sendTo(new BarrelSyncMsg(null,this.getPos().getX(),this.getPos().getY(),this.getPos().getZ()), );
    		
		   	
    		//SimpleBarrels.BarrelNet2.sendToServer(new BarrelSyncServer(Minecraft.getMinecraft().thePlayer.getName(),this.getPos().getX(),this.getPos().getY(),this.getPos().getZ()));
        }
   }
    

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return 1;
    }

    /**
     * Returns the stack in the given slot.
     */
    
    @Nullable
    public ItemStack getStackInSlot(int index)
    {
        //this.fillWithLoot((EntityPlayer)null);
    	if (this.barrelContents[0]!=null)
    	{
    		if (count>0)
    		{
    			int a = this.barrelContents[0].getMaxStackSize();//check if itemstack isnt a 64 stack(E.g. eggs)
    			if (count <a)
    			{
    				//count = 0;
    				ItemStack i = new ItemStack(this.barrelContents[0].getItem(),1,this.barrelContents[0].getItemDamage());
    				i.setTagCompound(barrelContents[0].getTagCompound());
    				return i; 
    		
    			} else
    			{
    				//count -= a;
    				ItemStack i = new ItemStack(this.barrelContents[0].getItem(),1,this.barrelContents[0].getItemDamage());
    				i.setTagCompound(barrelContents[0].getTagCompound());
    				return i;
    			} 
    		}
    	}
    	return null;
    }
    
    
    public boolean canInsertItem(int index,ItemStack stack,EnumFacing side)
    {
    	System.out.println("meh");
    	if (this.barrelContents[0]!= null)
		{
    	if (this.barrelContents[0].getItem().equals(stack.getItem()))
    	{
    		if (this.barrelContents[0].getTagCompound().equals(stack.getTagCompound()))
    		{
    			if (this.count < this.size)
    			{
    				System.out.println("This hopper can insert shit in me");
    				return true;
    			}
    			
    		}
    	}
		} else
		{
			if (this.count==0)
			{
			return true;
			}
		}
    	System.out.println("This hopper cant insert shit in me");
    	return false;
    }
    
    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    @Nullable
    public ItemStack decrStackSize(int index, int count)
    {
        
        //ItemStack itemstack = ItemStackHelper.getAndSplit(this.barrelContents, index, count);
    	ItemStack itemstack = null;
    	if (this.count >=count)
    	{
    		itemstack = new ItemStack(this.barrelContents[0].getItem(),count,this.barrelContents[0].getItemDamage());
    		itemstack.setTagCompound(barrelContents[0].getTagCompound());
    		this.count -= count;
    		
    	} else
    	{
    		itemstack = new ItemStack(this.barrelContents[0].getItem(),this.count,this.barrelContents[0].getItemDamage());
    		itemstack.setTagCompound(barrelContents[0].getTagCompound());
    		this.count = 0;
    		this.barrelContents[0]=null;
    	}
        if (itemstack != null)
        {
            this.markDirty();
        }

        return itemstack;
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    @Nullable
    public ItemStack removeStackFromSlot(int index)
    {
        
        //return ItemStackHelper.getAndRemove(this.barrelContents, index);
    	ItemStack itemstack = null;
    	if (this.count==0)
    	{
    		return null;
    	} else if (count<=this.barrelContents[0].getMaxStackSize())
    	{
    		itemstack = new ItemStack(barrelContents[0].getItem(),this.count,this.barrelContents[0].getItemDamage());
    		itemstack.setTagCompound(barrelContents[0].getTagCompound());
    		count = 0;
    		this.barrelContents[0]=null;
    		return itemstack;
    	} else
    	{
    		count = count - this.barrelContents[0].getMaxStackSize();
    		itemstack = new ItemStack(barrelContents[0].getItem(),this.barrelContents[0].getMaxStackSize(),this.barrelContents[0].getItemDamage());
    		itemstack.setTagCompound(barrelContents[0].getTagCompound());
    		return itemstack;
    	}
    	
    	
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int index, @Nullable ItemStack stack)
    {
        if ((this.barrelContents[0].isItemEqual(stack)) || (this.barrelContents[0]==null))
        {
        	
        	//this.barrelContents[0] = stack;
        	if ((this.count + stack.stackSize)< this.size)
        	{
        		this.count += stack.stackSize;
        		this.markDirty();
        		return;
        	} else if (this.count<this.size)
        	{
        		stack.stackSize = this.size-this.count;
        		this.count = this.size;
        		this.markDirty();
        		return;
        	}
        }
    }

    /**
     * Get the name of this object. For players this returns their username
     */
    public String getName()
    {
        return this.hasCustomName() ? this.customName : "container.chest";
    }

    /**
     * Returns true if this thing is named
     */
    public boolean hasCustomName()
    {
        return this.customName != null && !this.customName.isEmpty();
    }

    public void setCustomName(String name)
    {
        this.customName = name;
    }
    
    public boolean isBlock(ItemStack i)
    {
    	if (i.getItem() instanceof ItemBlock)
    	{
    		return true;
    	} else
    	{
    		return false;
    	}
    }

    public void readFromNBT(NBTTagCompound compound)
    {
    	
        super.readFromNBT(compound);
        this.count = compound.getInteger("count");
        if (compound.hasKey("CustomName", 8))
        {
            this.customName = compound.getString("CustomName");
        }
        if (count>0)
        {
        	this.barrelContents = new ItemStack[this.getSizeInventory()];
        	this.tags = compound.getCompoundTag("tags");
        	
        

        
            NBTTagList nbttaglist = compound.getTagList("Items", 10);

            
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(0);
            int j = nbttagcompound.getByte("Slot") & 255;

                
            this.barrelContents[0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
            this.itemIsBlock = compound.getBoolean("isblock");        
            this.barrelContents[0].setItemDamage(compound.getInteger("state"));
        }
        
    }



    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
    	
        super.writeToNBT(compound);
        	
        
            NBTTagList nbttaglist = new NBTTagList();
            if (count != null)
            {
            	if (count>0)
            	{
            		if (this.barrelContents[0] != null)
            		{
            			NBTTagCompound nbttagcompound = new NBTTagCompound();
            			nbttagcompound.setByte("Slot", (byte)0);
            			this.barrelContents[0].writeToNBT(nbttagcompound);
            			nbttaglist.appendTag(nbttagcompound);
            			
            			//NBTTagCompound
            			 
            		}
            		/*
            		if (this.tags!=null)
            		{
            			NBTTagList taglist2 = new NBTTagList();
            			taglist2.appendTag(this.tags);
            			compound.setTag("tags", taglist2);

            			compound.setTag("Items", nbttaglist);
            		}
            		*/
                    compound.setTag("Items", nbttaglist);

                    compound.setInteger("count", this.count);
                    compound.setBoolean("isblock", this.itemIsBlock);
                    if (this.barrelContents[0]!=null)
                    {
                    	compound.setInteger("state", this.barrelContents[0].getItem().getMetadata(this.barrelContents[0]));
                    }else
                    {
                    	compound.setInteger("state", 0);
                    }
            	}
            }

        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.customName);
        }
        return compound;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
     */
    public int getInventoryStackLimit()
    {
        return this.size;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public void updateContainingBlockInfo()
    {
        super.updateContainingBlockInfo();
        
        
    }

    
        

    

    

    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update()
    {
   
    	this.getWorld().notifyBlockUpdate(this.getPos(), this.getWorld().getBlockState(this.getPos()), this.getWorld().getBlockState(this.getPos()), 3);
    	
		
        
    }

    public boolean receiveClientEvent(int id, int type)
    {
        if (id == 1)
        {
            this.numPlayersUsing = type;
            return true;
        }
        else
        {
            return super.receiveClientEvent(id, type);
        }
    }

    public void openInventory(EntityPlayer player)
    {
        if (!player.isSpectator())
        {
            if (this.numPlayersUsing < 0)
            {
                this.numPlayersUsing = 0;
            }

            ++this.numPlayersUsing;
            this.worldObj.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
            this.worldObj.notifyNeighborsOfStateChange(this.pos, this.getBlockType());
            this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType());
        }
    }

    public ItemStack insertStack(IInventory inventoryIn,ItemStack stack,int c,EnumFacing side)
    {
    	
    	if (isItemValidForSlot(0,stack))
    	{
    		if (this.count+stack.stackSize<this.size)
    		{
    			this.count +=stack.stackSize;
    			return null;
    		} else if (this.count < this.size)
    		{
    			int ret = stack.stackSize+this.count-this.size;
    			this.count = this.size;
    			stack.stackSize= ret;
    			return stack;
    		}
    	}
    	return stack;
    }
    
    public void closeInventory(EntityPlayer player)
    {
        if (!player.isSpectator() && this.getBlockType() instanceof BlockChest)
        {
            --this.numPlayersUsing;
            this.worldObj.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
            this.worldObj.notifyNeighborsOfStateChange(this.pos, this.getBlockType());
            this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType());
        }
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
    	if (this.barrelContents[0]!=null)
    	{
    	if (this.barrelContents[0].equals(stack))
    	{
    		if (this.barrelContents[0].getTagCompound().equals(stack.getTagCompound()))
    		{
    		return true;
    		}
    	}
    	}
    	return false;
        
    }
    
    

    /**
     * invalidates a tile entity
     */
    public void invalidate()
    {
        super.invalidate();
        this.updateContainingBlockInfo();
        
    }

    
    
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        
        return new ContainerChest(playerInventory, this, playerIn);
    }

    public int getField(int id)
    {
        return 0;
    }

    public void setField(int id, int value)
    {
    }

    public int getFieldCount()
    {
        return 0;
    }

    public void clear()
    {
        

            this.barrelContents[0] = null;
            this.count = 0;
        
    }

    @Override
    @Nullable
    public final SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
    }

    @Override
    public final NBTTagCompound getUpdateTag()
    {
        NBTTagCompound tag = new NBTTagCompound();
        writeTileClientData(tag);
        
        return tag;
    }

    private void writeTileClientData(NBTTagCompound compound) 
    {
    	
		//this.writeToNBT(tag);
		NBTTagList nbttaglist = new NBTTagList();
        if (this.count!=null)
        {
        	if (this.count!=0)
        	{
        		if (this.barrelContents[0] != null)
        		{
        			NBTTagCompound nbttagcompound = new NBTTagCompound();
        			nbttagcompound.setByte("Slot", (byte)0);
        			this.barrelContents[0].writeToNBT(nbttagcompound);
        			nbttaglist.appendTag(nbttagcompound);
        		}
        		/*
        		if (this.tags!=null)
        		{
        			compound.setTag("Items", nbttaglist);
        			NBTTagList taglist2 = new NBTTagList();
        			taglist2.appendTag(this.tags);
        			compound.setTag("tags", taglist2);
        		}*/
        		compound.setTag("Items", nbttaglist);
        		compound.setInteger("count", count); 
                
                
        	       
                compound.setBoolean("isblock", this.itemIsBlock);
                if (this.barrelContents[0]!=null)
                {
                	compound.setInteger("state", this.barrelContents[0].getItem().getMetadata(this.barrelContents[0]));
                } else
                {
                	compound.setInteger("state", 0);
                }
        	}
        }
        
		
        if (this.hasCustomName())
        {
        	compound.setString("CustomName", this.customName);
        }
       
        
    
	}
    
    
	@Override
    public final void onDataPacket(NetworkManager m, SPacketUpdateTileEntity p)
    {
        	readTileClientData(p.getNbtCompound());
        	
        	
            IBlockState state = getBlockState();
            worldObj.notifyBlockUpdate(pos, state, state, 3); //TODO: Check if this works
             
    }



	private IBlockState getBlockState() {
		return this.getWorld().getBlockState(this.getPos());
		
	}
	private void readTileClientData(NBTTagCompound compound) {
		if (compound!=null)
    	{
    		
    	
		this.barrelContents = new ItemStack[this.getSizeInventory()];

        if (compound.hasKey("CustomName", 8))
        {
            this.customName = compound.getString("CustomName");
        }

        
            NBTTagList nbttaglist = compound.getTagList("Items", 10);
            
            
                NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(0);
                int j = nbttagcompound.getByte("Slot") & 255;

                
                    this.barrelContents[0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
                    this.count = compound.getInteger("count");
                    this.itemIsBlock = compound.getBoolean("isblock");      
                    int st = compound.getInteger("state");
                    /*
                    if (this.barrelContents[0]!=null)
                    {
                    	this.tags = compound.getCompoundTag("tags");
                    	this.barrelContents[0].setItemDamage(st);
                    	if (this.barrelContents[0].getItem() instanceof ItemBlock)
                        {
                        	//restore 
                        }
                    }*/
                    //this.barrelContents[0].getItem().setDamage(this.barrelContents[0], compound.getInteger("state")); 
    	} 
    		
                    
	}
	@Override
	public ITextComponent getDisplayName() {
		
		return null;
	}


	

}
