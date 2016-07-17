package com.workshopcraft.simplebarrels.tiles;

import javax.annotation.Nullable;

import com.workshopcraft.simplebarrels.handlers.BarrelItemHandler;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityBarrel extends TileEntity //implements ITickable
{
    //public ItemStack[] barrelContents = new ItemStack[1];
    public BarrelItemHandler itemHandler;
    //public NBTTagCompound tags = new NBTTagCompound();
    public IBlockState state;
    //public Integer size,count;
    public Boolean itemIsBlock=false;
    /** Determines if the check for adjacent chests has taken place. */
    /** The current angle of the lid (between 0 and 1) */
    private int ticksSinceSync;
    private String customName;
    /** The number of players currently using this chest */
    public int numPlayersUsing;
    //public int maxsize = 4096;
    public int ticks = 0;
    public TileEntityBarrel()
    {
    	itemHandler = new BarrelItemHandler();
    	
    	itemHandler.t = this;
    	
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
        itemHandler.count = compound.getInteger("count");
        if (compound.hasKey("CustomName", 8))
        {
            this.customName = compound.getString("CustomName");
        }
        
        
        if (itemHandler.count>0)
        {
        	itemHandler.barrelContents = new ItemStack[1];
        	//this.tags = compound.getCompoundTag("tags");
        	
        

        
            NBTTagList nbttaglist = compound.getTagList("Items", 10);

            
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(0);
            int j = nbttagcompound.getByte("Slot") & 255;

                
            itemHandler.barrelContents[0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
            //this.itemIsBlock = compound.getBoolean("isblock");        
            itemHandler.barrelContents[0].setItemDamage(compound.getInteger("state"));
        }
        
    }



    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
    	
        super.writeToNBT(compound);
        	
        
            NBTTagList nbttaglist = new NBTTagList();
            if (itemHandler != null)
            {
            	if (itemHandler.count>0)
            	{
            		if (itemHandler.barrelContents[0] != null)
            		{
            			NBTTagCompound nbttagcompound = new NBTTagCompound();
            			nbttagcompound.setByte("Slot", (byte)0);
            			itemHandler.barrelContents[0].writeToNBT(nbttagcompound);
            			nbttaglist.appendTag(nbttagcompound);
            			
            			//NBTTagCompound
            			 
            		}
            	
                    compound.setTag("Items", nbttaglist);

                    compound.setInteger("count", itemHandler.count);
                    //compound.setBoolean("isblock", this.itemIsBlock);
                    if (itemHandler.barrelContents[0]!=null)
                    {
                    	compound.setInteger("state", itemHandler.barrelContents[0].getItem().getMetadata(itemHandler.barrelContents[0]));
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
        return 65535;
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
    //@Override
    

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

    

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
    	if (itemHandler.barrelContents[0]!=null)
    	{
    	if (itemHandler.barrelContents[0].equals(stack))
    	{
    		if (itemHandler.barrelContents[0].getTagCompound().equals(stack.getTagCompound()))
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
        

    		itemHandler.barrelContents[0] = null;
    		itemHandler.count = 0;
        
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
        NBTTagCompound tag = super.getUpdateTag();
        if (tag == null)
        {
        	tag = new NBTTagCompound();
        }
        writeTileClientData(tag);
        
        return tag;
    }

    private void writeTileClientData(NBTTagCompound compound) 
    {
    	
		//this.writeToNBT(tag);
		NBTTagList nbttaglist = new NBTTagList();
        if (itemHandler!=null)
        {
        	if (itemHandler.count!=0)
        	{
        		if (itemHandler.barrelContents[0] != null)
        		{
        			NBTTagCompound nbttagcompound = new NBTTagCompound();
        			nbttagcompound.setByte("Slot", (byte)0);
        			itemHandler.barrelContents[0].writeToNBT(nbttagcompound);
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
        		compound.setInteger("count", itemHandler.count); 
                
                
        	       
                //compound.setBoolean("isblock", this.itemIsBlock);
                if (itemHandler.barrelContents[0]!=null)
                {
                	compound.setInteger("state", itemHandler.barrelContents[0].getItem().getMetadata(itemHandler.barrelContents[0]));
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
    		
    	
			itemHandler.barrelContents = new ItemStack[1];

        if (compound.hasKey("CustomName", 8))
        {
            this.customName = compound.getString("CustomName");
        }

        
            NBTTagList nbttaglist = compound.getTagList("Items", 10);
            
            
                NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(0);
                int j = nbttagcompound.getByte("Slot") & 255;

                
                	itemHandler.barrelContents[0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
                	itemHandler.count = compound.getInteger("count");
                    //this.itemIsBlock = compound.getBoolean("isblock");      
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
                    int s = 0;
                    boolean err = false;
                    try {
                    s = compound.getInteger("state");
                    }
                    catch (NullPointerException e) {
                    	 err = true;
                    } 
                    if (!err)
                    {
                    	if (itemHandler.barrelContents[0]!=null)
                    	{
                    		itemHandler.barrelContents[0].getItem().setDamage(itemHandler.barrelContents[0], s);
                    	}
                    }
    	} 
    		
                    
	}
	//@Override
	public ITextComponent getDisplayName() {
		
		return null;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capability, facing);
		
	}
	
	//@SupressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			//BarrelItemHandler handler = new BarrelItemHandler();
			//return (T) handler;
			return (T) itemHandler;
		}
		return super.getCapability(capability, facing);
		}
	}



