package com.workshopcraft.simplebarrels.blocks;

import java.util.ArrayList;
import java.util.Random;

import javax.annotation.Nullable;

import com.workshopcraft.simplebarrels.SimpleBarrels;
import com.workshopcraft.simplebarrels.tesr.TESRBlockBarrel;
import com.workshopcraft.simplebarrels.tiles.TileEntityBarrel;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBarrel extends BlockContainer{

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public BlockBarrel() {
		
		super(Material.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        setCreativeTab(SimpleBarrels.tabSimpleBarrels);
        setHardness(2.0f);
        setResistance(6.0f);
        setHarvestLevel("Axe", 0);
        
	}
	
	public BlockBarrel(String uname)
	{
		super(Material.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        setCreativeTab(SimpleBarrels.tabSimpleBarrels);
        setHardness(2.0f);
        setResistance(6.0f);
        setHarvestLevel("Axe", 0);
        setUnlocalizedName(uname);
        setRegistryName(uname);
       
	}
	
	
	
	 @SideOnly(Side.CLIENT)
	    public void initModel() {
		 
	        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	        // Bind our TESR to our tile entity
	        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBarrel.class, new TESRBlockBarrel());
	    }
	
	
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return true;
    }
    
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
    
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
        
    }
    @Override
    public void onBlockPlacedBy(World worldIn,BlockPos pos,IBlockState state,EntityLivingBase placer,ItemStack stack)
    {
    	//grab settings from itemstack
    	if (worldIn == null)
    	{
    		System.out.println("EMPTY WORLD");
    		return;
    	}
    	if (pos == null)
    	{
    		System.out.println("EMPTY POS");
    		return;
    	}
    	if (stack == null)
    	{
    		System.out.println("EMPTY STACK");
    		return;
    	}
    	Boolean c,f;
    	int s;
    	NBTTagCompound n = new NBTTagCompound();
    	n = stack.getTagCompound();
    	if (n !=null)
    	{
    	
    		c = n.getBoolean("comp");
    		f = n.getBoolean("frame");
    		s = n.getInteger("size");
	    	TileEntityBarrel t;
	    	TileEntity t1;
	    	
	    	t1 = (worldIn.getTileEntity(pos));
	    	if (t1 == null)
	    	{
	    	
	    	return;
	    	}
	    	if (t1 instanceof TileEntityBarrel)
	    	{
	    		t = (TileEntityBarrel)t1;
	    	} else
	    	{
	    		return;
	    	}
	    	if (t!=null)
			{
				t.init(c,f);
				t.itemHandler.size = s;
				this.updateBarrel((TileEntityBarrel)worldIn.getTileEntity(pos));
			}
		}
    
    }
   
    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
	{
    	TileEntity tileentity = worldIn.getTileEntity(pos);
         
        
        ItemStack istack;
        //check instance of barrel tile entity
        if (tileentity instanceof TileEntityBarrel)
        {
        	TileEntityBarrel te2 = (TileEntityBarrel)tileentity;
        	if (te2.itemHandler.extractItem(0, 1, true)!=null)
        	//if (te2.itemHandler.barrelContents[0]!=null)
        	{
           
        	istack = new ItemStack(te2.itemHandler.barrelContents.getItem(),te2.itemHandler.count,te2.itemHandler.barrelContents.getItemDamage());
			istack.setTagCompound(te2.itemHandler.barrelContents.getTagCompound());
			if (!worldIn.isRemote)
        	{
				InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY()+1.0, pos.getZ(),istack);
        	}
			
        	
        	}
        	
        	istack = new ItemStack(this,1,0);
        	NBTTagCompound n = new NBTTagCompound();
        	n.setBoolean("comp", te2.comp);
        	n.setBoolean("frame", te2.frame);
        	n.setInteger("size", te2.itemHandler.size);
        	istack.setTagCompound(n);
        	
        	if (!worldIn.isRemote)
        	{
        		InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY()+1.0, pos.getZ(),istack);
        	}
        }
	}
    
   
  
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityBarrel();
    }
    
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }
    
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
    
    public static void updateBarrel(TileEntityBarrel t)
	{
		NBTTagCompound compound = new NBTTagCompound();
		t.writeToNBT(compound );
		t.markDirty();
		t.getWorld().notifyBlockUpdate(t.getPos(), t.getWorld().getBlockState(t.getPos()), t.getWorld().getBlockState(t.getPos()), 3);
		
	}
    
    
    
    
    
    @Override
    public ArrayList<ItemStack> getDrops(IBlockAccess world,BlockPos pos, IBlockState state, int fortune)
    {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		TileEntity t = world.getTileEntity(pos);
		if (t instanceof TileEntityBarrel)
		{
			TileEntityBarrel tile = (TileEntityBarrel)t;
			ItemStack stack = new ItemStack(this,1,0);
        	stack.setTagCompound(tile.getTileData());
        	
			items.add(stack);
			if (tile.itemHandler !=null)
			{
				while (tile.itemHandler.extractItem(0, 1, true)!=null)
				{
					items.add(tile.itemHandler.extractItem(0, 64, false));
				}
				
			}
		}
	
			return items;
    	
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	
    		
    		if (!worldIn.isRemote)
    		{
    			
				TileEntity te = (worldIn.getTileEntity(pos));
				if (te instanceof TileEntityBarrel)
				{
					TileEntityBarrel te2 = (TileEntityBarrel) te;
					//System.out.println("size:"+te2.itemHandler.size);
					//System.out.println("stored:"+te2.itemHandler.count);
					
					if (!(te2.itemHandler.extractItem(0, 1, true) instanceof ItemStack)) //If barrel is holding nothing
					{
						// BARREL EMPTY
						if (heldItem != null) //place whatever is in hand in barrel
						{
							//HAND EMPTY
							NBTTagCompound n = new NBTTagCompound();
							
							ItemStack insertStack = new ItemStack(heldItem.getItem(),heldItem.stackSize,heldItem.getMetadata());
							insertStack.setTagCompound(n);
							te2.itemHandler.insertItem(0, insertStack ,false);
							
							
							playerIn.inventory.mainInventory[playerIn.inventory.currentItem].stackSize=0;
							playerIn.inventory.inventoryChanged=true;
							
							this.updateBarrel(te2);
							
							return true;
							
						}
					} else { 
						//CHECK FOR AND ATTEMPT HASHMAPPED HERE
						
						if (heldItem!=null) {
							//HAND NOT EMPTY
    						if (te2.itemHandler.extractItem(0, 1, true).isItemEqual(heldItem)) {
    							//ITEM IN HAND MATCHES BARREL CONTENTS
    							
    							NBTTagCompound t1 = te2.itemHandler.extractItem(0, 1, true).getTagCompound();
    							Boolean match = false;
    							if (heldItem.getTagCompound()!=null) { //make sure we dont pass null
    								if (t1!=null){
    									
    									if (t1.equals(heldItem.getTagCompound())) { //compare t1 tags with held item tags
    										match = true; //match!
    									}
    								}
									
								}
    							if (t1==null)
    							{
    								System.out.println("null on T1");
    							} else
    							{
    								System.out.println(t1.toString());
    							}
    							
    							if (((t1==null)|| (t1.toString().equals("{}")))  &&  (heldItem.getTagCompound()==null)) { //also match if netiher have tags
    								
	    								match = true;
	    								
	    						}
    							
    							if (match) {
    								
    								if (te2.itemHandler.count < te2.itemHandler.size) {
    									//before we insert. update HASHMAPED
    									//attempts to insert held item into barrel.
    									int incResult = te2.itemHandler.incItems(heldItem.stackSize);
										
										
										
										playerIn.inventory.mainInventory[playerIn.inventory.currentItem].stackSize=incResult;
										playerIn.inventory.inventoryChanged=true;
										this.updateBarrel(te2);
										return true;
    									
    									
    								}
    							}
    						} else { return true; }
							
						} else { return true; }
					} 
				}
    		}
    		return true;
    	}
    		
    }

