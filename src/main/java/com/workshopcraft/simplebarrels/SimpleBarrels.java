package com.workshopcraft.simplebarrels;

import java.util.List;

import com.workshopcraft.simplebarrels.api.barrelFactory;
import com.workshopcraft.simplebarrels.blocks.BlockBarrel;
import com.workshopcraft.simplebarrels.handlers.BarrelHandler;
import com.workshopcraft.simplebarrels.items.ItemUpgradeComparator;
import com.workshopcraft.simplebarrels.items.ItemUpgradeItemFrame;
//import com.workshopcraft.simplebarrels.network.BarrelSyncClient;
//import com.workshopcraft.simplebarrels.network.BarrelSyncServer;
import com.workshopcraft.simplebarrels.proxy.barrelcommonproxy;
import com.workshopcraft.simplebarrels.tiles.TileEntityBarrel;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid = SimpleBarrels.MODID, version = SimpleBarrels.VERSION)
public class SimpleBarrels
{
    public static final String MODID = "simplebarrels";
    public static final String VERSION = "1.25h";
    
    public static ItemUpgradeComparator upgradeComparator;
    public static ItemUpgradeItemFrame upgradeItemFrame;
    public static SimpleBarrelsTab tabSimpleBarrels;
    
    
    /*
     * 1.3 BARRELS
     */
    public static List<BlockBarrel> barrels;
    public static barrelFactory bFactory;
    /*
     * VANILLA
     */
    public static BlockBarrel blockBarrel;
    public static BlockBarrel oakBarrel;
    public static BlockBarrel spruceBarrel;
    public static BlockBarrel birchBarrel;
    public static BlockBarrel jungleBarrel;
    public static BlockBarrel acaciaBarrel;
    public static BlockBarrel darkoakBarrel;
    /*
     * FORESTRY
     */
    
    /*
     * BIOMES O' PLENTY
     */
    
    
    /*
     * NATURA
     */
    
    
    /*
     * THAUMCRAFT
     */
    
    
    //public static SimpleNetworkWrapper BarrelNet= NetworkRegistry.INSTANCE.newSimpleChannel("barrelnet");
    //public static SimpleNetworkWrapper BarrelNet2= NetworkRegistry.INSTANCE.newSimpleChannel("barrelnet2");
    
    @SidedProxy(clientSide="com.workshopcraft.simplebarrels.proxy.clientbarrel",serverSide="com.workshopcraft.simplebarrels.proxy.serverbarrel")
    public static barrelcommonproxy proxy;
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
    	//blockBarrel = new BlockBarrel();
        //blockBarrel.setUnlocalizedName("blockbarrel");
    	tabSimpleBarrels = new SimpleBarrelsTab("tabSimpleBarrelsTab");
    	upgradeComparator = new ItemUpgradeComparator("upgradecomparator");
    	upgradeItemFrame = new ItemUpgradeItemFrame("upgradeitemframe");
    	
    	/*
    	oakBarrel = new BlockBarrel("oakbarrel");
        //oakBarrel.setUnlocalizedName("oakbarrel");
    	
        spruceBarrel = new BlockBarrel("sprucebarrel");
        //spruceBarrel.setUnlocalizedName("sprucebarrel");
        birchBarrel = new BlockBarrel("birchbarrel");
        //birchBarrel.setUnlocalizedName("birchbarrel");
        jungleBarrel = new BlockBarrel("junglebarrel");
        //jungleBarrel.setUnlocalizedName("junglebarrel");
        acaciaBarrel = new BlockBarrel("acaciabarrel");
        //acaciaBarrel.setUnlocalizedName("acaciabarrel");
        darkoakBarrel = new BlockBarrel("darkoakbarrel");
        //darkoakBarrel.setUnlocalizedName("darkoakbarrel");        
        */
        bFactory = new barrelFactory();
        proxy.preInit(event);
        
    }
    public void addBarrel(BlockBarrel barrel,Boolean f, Boolean c,ItemStack plank)
    {
    	ItemStack i = new ItemStack(barrel,1);
        NBTTagCompound n = new NBTTagCompound();
        
        n.setBoolean("frame", f);
        n.setBoolean("comp", c);
        i.setTagCompound(n);
        
        
        if (!f && !c)
        {
        	
        	
        	GameRegistry.addRecipe(new ShapedOreRecipe(i, new Object[]{
					"WIW",
					"FCR",
					"WIW",
					'W',plank,'C',"chestWood",'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			}));
        } else if (f && !c)
        {
        	GameRegistry.addRecipe(new ShapedOreRecipe(i, new Object[]{
					"WIW",
					" CR",
					"WIW",
					'W',plank,'C',"chestWood",'R',Items.COMPARATOR, 'I',Items.IRON_INGOT
			}));
        } else if (!f && c)
        {
        	GameRegistry.addRecipe(new ShapedOreRecipe(i, new Object[]{
					"WIW",
					"FC ",
					"WIW",
					'W',plank,'C',"chestWood",'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			}));
        } else if (f && c)
        {
        	GameRegistry.addRecipe(new ShapedOreRecipe(i, new Object[]{
					"WIW",
					" C ",
					"WIW",
					'W',plank,'C',"chestWood", 'I',Items.IRON_INGOT
			}));
        }
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        GameRegistry.registerTileEntity(TileEntityBarrel.class, "tile_entity_barrel");
        //GameRegistry.registerBlock(blockBarrel , "blockbarrel");
        //GameRegistry.registerBlock(oakBarrel , "oakbarrel");
        bFactory.init();
        /*
        GameRegistry.register(oakBarrel);
        GameRegistry.register(new ItemBlock(oakBarrel), oakBarrel.getRegistryName());
        GameRegistry.register(spruceBarrel);
        GameRegistry.register(new ItemBlock(spruceBarrel), spruceBarrel.getRegistryName());
        GameRegistry.register(birchBarrel);
        GameRegistry.register(new ItemBlock(birchBarrel), birchBarrel.getRegistryName());
        GameRegistry.register(jungleBarrel);
        GameRegistry.register(new ItemBlock(jungleBarrel), jungleBarrel.getRegistryName());
        GameRegistry.register(acaciaBarrel);
        GameRegistry.register(new ItemBlock(acaciaBarrel), acaciaBarrel.getRegistryName());
        GameRegistry.register(darkoakBarrel);
        GameRegistry.register(new ItemBlock(darkoakBarrel), darkoakBarrel.getRegistryName());
        */
        //GameRegistry.registerBlock(spruceBarrel , "sprucebarrel");
        //GameRegistry.registerBlock(birchBarrel , "birchbarrel");
       // GameRegistry.registerBlock(jungleBarrel , "junglebarrel");
       // GameRegistry.registerBlock(acaciaBarrel , "acaciabarrel");
       // GameRegistry.registerBlock(darkoakBarrel , "darkoakbarrel");
        /*
         * FORESTRY
         */
        
        
        GameRegistry.addRecipe(new ItemStack(this.upgradeComparator,1),new Object[] {
        		" A ",
        		"ABA",
        		" A ",
        		'A',Items.STICK,'B',Items.COMPARATOR
        });
        GameRegistry.addRecipe(new ItemStack(this.upgradeItemFrame,1),new Object[] {
        		" A ",
        		"ABA",
        		" A ",
        		'A',Items.STICK,'B',Items.ITEM_FRAME
        });
       
        FMLCommonHandler.instance().bus().register(new BarrelHandler());
        
        
        /*
        Item mcPlank= Item.REGISTRY.getObject(new ResourceLocation("minecraft:planks"));
        
        addBarrel(oakBarrel,false, false, new ItemStack(mcPlank,1,0));
        addBarrel(oakBarrel,true, false,new ItemStack(mcPlank,1,0));
        addBarrel(oakBarrel,false, true,new ItemStack(mcPlank,1,0));
        addBarrel(oakBarrel,true, true,new ItemStack(mcPlank,1,0));
        
        addBarrel(spruceBarrel,false, false,new ItemStack(Blocks.PLANKS,1,1));
        addBarrel(spruceBarrel,true, false,new ItemStack(Blocks.PLANKS,1,1));
        addBarrel(spruceBarrel,false, true,new ItemStack(Blocks.PLANKS,1,1));
        addBarrel(spruceBarrel,true, true,new ItemStack(Blocks.PLANKS,1,1));
        
        addBarrel(birchBarrel,false, false,new ItemStack(Blocks.PLANKS,1,2));
        addBarrel(birchBarrel,true, false,new ItemStack(Blocks.PLANKS,1,2));
        addBarrel(birchBarrel,false, true,new ItemStack(Blocks.PLANKS,1,2));
        addBarrel(birchBarrel,true, true,new ItemStack(Blocks.PLANKS,1,2));
        
        addBarrel(jungleBarrel,false, false,new ItemStack(Blocks.PLANKS,1,3));
        addBarrel(jungleBarrel,true, false,new ItemStack(Blocks.PLANKS,1,3));
        addBarrel(jungleBarrel,false, true,new ItemStack(Blocks.PLANKS,1,3));
        addBarrel(jungleBarrel,true, true,new ItemStack(Blocks.PLANKS,1,3));
        
        addBarrel(acaciaBarrel,false, false,new ItemStack(Blocks.PLANKS,1,4));
        addBarrel(acaciaBarrel,true, false,new ItemStack(Blocks.PLANKS,1,4));
        addBarrel(acaciaBarrel,false, true,new ItemStack(Blocks.PLANKS,1,4));
        addBarrel(acaciaBarrel,true, true,new ItemStack(Blocks.PLANKS,1,4));
        
        addBarrel(darkoakBarrel,false, false,new ItemStack(Blocks.PLANKS,1,5));
        addBarrel(darkoakBarrel,true, false,new ItemStack(Blocks.PLANKS,1,5));
        addBarrel(darkoakBarrel,false, true,new ItemStack(Blocks.PLANKS,1,5));
        addBarrel(darkoakBarrel,true, true,new ItemStack(Blocks.PLANKS,1,5));
        */
        
        /*
         * FORESTRY
         */
        /*
         * FORESTRY
         */
        
        proxy.init(event);
        
       
        
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	
    	
    }
}
