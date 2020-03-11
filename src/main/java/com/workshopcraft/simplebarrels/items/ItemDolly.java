package com.workshopcraft.simplebarrels.items;

import com.workshopcraft.simplebarrels.SimpleBarrels;
import com.workshopcraft.simplebarrels.blocks.BlockBarrel;
import com.workshopcraft.simplebarrels.handlers.BarrelItemHandler;
import com.workshopcraft.simplebarrels.tiles.TileEntityBarrel;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemDolly extends Item{

	private String name="";
	private boolean hasBarrel = false;
	
	private NBTTagCompound barrelNBT;
	
	public ItemDolly()
	{
		setMaxStackSize(1);
		setCreativeTab(SimpleBarrels.tabSimpleBarrels);
	}
	public ItemDolly(String uname)
	{
		super();
		GameRegistry.registerItem(this,uname);
		name = uname;
		this.setUnlocalizedName(name);
		setMaxStackSize(1);
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
			if (barrelNBT==null)
			{
				barrelNBT = new NBTTagCompound();
			}
			stack.readFromNBT(barrelNBT);
			if (!barrelNBT.hasKey("hasbarrel"))
			{
				barrelNBT.setBoolean("hasbarrel", false);
			}
			if (playerIn.isSneaking())
			{
				
				System.out.println("sneaky sneaky");
				
				if (barrelNBT.getBoolean("hasbarrel")==false)
				{
					
					System.out.println("does not have a barrel");
					TileEntity t = worldIn.getTileEntity(pos);
					if (t!=null)
					{
						if (t instanceof TileEntityBarrel)
						{
							//GRAB BARREL HERE
							System.out.println("GRAB BARREL");
							TileEntityBarrel t2 = (TileEntityBarrel) t;
							
							
							if (barrelNBT == null)
								barrelNBT = new NBTTagCompound();
							
							
							hasBarrel = true;
							barrelNBT.setBoolean("hasBarrel", hasBarrel);
							barrelNBT.setBoolean("barrelcomp", t2.comp);
							barrelNBT.setBoolean("barrelframe", t2.frame);
							barrelNBT.setInteger("barrelmeta", t2.getBlockMetadata());
							barrelNBT.setString("barrelname", t2.getBlockType().getUnlocalizedName());
							NBTTagList nbttaglist = new NBTTagList();
							if (t2.itemHandler != null)
				            {
								System.out.println("has an itemHandler");
				            	if (t2.itemHandler.count>0)
				            	{
				            		if (t2.itemHandler.barrelContents != null)
				            		{
				            			System.out.println("has an item in barrel");
				            			NBTTagCompound nbttagcompound = new NBTTagCompound();
				            			nbttagcompound.setByte("Slot", (byte)0);
				            			t2.itemHandler.barrelContents.writeToNBT(nbttagcompound);
				            			barrelNBT.setTag("barrelnbt", nbttagcompound);
				            			 //barrelNBT.appendTag(nbttagcompound);
				            			
				            			//NBTTagCompound
				            			 
				            		}
				            	
				                    barrelNBT.setTag("Items", nbttaglist);
				                    
				                    barrelNBT.setInteger("count", t2.itemHandler.count);
				                    barrelNBT.setInteger("size", t2.itemHandler.size);
				                    //compound.setBoolean("isblock", this.itemIsBlock);
				                    if (t2.itemHandler.barrelContents!=null)
				                    {

				                    	barrelNBT.setInteger("state", t2.itemHandler.barrelContents.getItem().getMetadata(t2.itemHandler.barrelContents));
				                    }else
				                    {

				                    	barrelNBT.setInteger("state", 0);
				                    }
				            	}
				            }
							//Set block to air
							System.out.println("set block to air");
							//worldIn.getBlockState(pos).getBlock().breakBlock(worldIn,pos,worldIn.getBlockState(pos));
							//barrelNBT.setBoolean("hasbarrel", true);
							//stack.writeToNBT(barrelNBT);

							//worldIn.setBlockToAir(pos);

							System.out.println("Barrel Taken");
							return EnumActionResult.SUCCESS;
						}
					}
				}
			} else
			{
				//place barrel if able to do so if holding a barrel.
				
				if (barrelNBT!=null)
				{
					//if ()
					System.out.println("has a barrel");
					if (worldIn.getBlockState(pos.offset(facing)).getBlock().isAir(worldIn.getBlockState(pos.offset(facing)), worldIn, pos.offset(facing))) 
					{
						System.out.println("adjcent block is air");
						
						if (barrelNBT.getBoolean("hasbarrel") == true)
						{
							
						
						//set blockstate to barrel.
						if (SimpleBarrels.bFactory.getBarrelFromUnlocalizedName(barrelNBT.getString("barrelname")).getDefaultState()==null)
						{
							System.out.println("fuck");
						}
						worldIn.setBlockState(pos.offset(facing), SimpleBarrels.bFactory.getBarrelFromUnlocalizedName(barrelNBT.getString("name")).getDefaultState() );
						
						TileEntityBarrel tEB = (TileEntityBarrel)worldIn.getTileEntity(pos.offset(facing));
						//worldIn.setTileEntity(pos.offset(facing),tEB );
						tEB.itemHandler.size = barrelNBT.getInteger("size");
				        if (tEB.itemHandler.size == 0)
				        {
				        	tEB.itemHandler.size = 4096;
				        }
				        if (tEB.itemHandler.count>0)
				        {
				            NBTTagList nbttaglist = barrelNBT.getTagList("Items", 10);
				            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(0);
				            int j = nbttagcompound.getByte("Slot") & 255;
				            tEB.itemHandler.barrelContents = ItemStack.loadItemStackFromNBT(nbttagcompound);
				            //this.itemIsBlock = compound.getBoolean("isblock");        
				            tEB.itemHandler.barrelContents.setItemDamage(barrelNBT.getInteger("state"));
				        }
						//tEB.itemHandler = this.barrelItemHandler;
						tEB.comp = barrelNBT.getBoolean("barrelcomp");
						tEB.frame = barrelNBT.getBoolean("barrelframe");
						barrelNBT.setBoolean("hasbarrel", false);
						barrelNBT = null;
						stack.setTagCompound(barrelNBT);
						System.out.println("Barrel Replaced");
						return EnumActionResult.SUCCESS;
						}
					}
					
				} else
				{
					System.out.println("sorry, no data for you bro");
				}
			}
		}
		return EnumActionResult.FAIL;
	}
	
}
