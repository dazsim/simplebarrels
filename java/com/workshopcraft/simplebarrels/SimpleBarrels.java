package com.workshopcraft.simplebarrels;

import com.workshopcraft.simplebarrels.blocks.BlockBarrel;
import com.workshopcraft.simplebarrels.handlers.BarrelHandler;
import com.workshopcraft.simplebarrels.items.ItemUpgradeComparator;
import com.workshopcraft.simplebarrels.items.ItemUpgradeItemFrame;
//import com.workshopcraft.simplebarrels.network.BarrelSyncClient;
//import com.workshopcraft.simplebarrels.network.BarrelSyncServer;
import com.workshopcraft.simplebarrels.proxy.barrelcommonproxy;
import com.workshopcraft.simplebarrels.tiles.TileEntityBarrel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
    public static BlockBarrel forestryacaciaBarrel;
    public static BlockBarrel forestrybalsaBarrel;
    public static BlockBarrel forestrybaobabBarrel;
    public static BlockBarrel forestrycherryBarrel;
    public static BlockBarrel forestrychestnutBarrel;
    public static BlockBarrel forestrycitrusBarrel;
    public static BlockBarrel forestrycocoboloBarrel;
    public static BlockBarrel forestryebonyBarrel;
    public static BlockBarrel forestrygiganteumBarrel;
    public static BlockBarrel forestrygreenheartBarrel;
    public static BlockBarrel forestryipeBarrel;
    public static BlockBarrel forestrykapokBarrel;
    public static BlockBarrel forestrylarchBarrel;
    public static BlockBarrel forestrylimeBarrel;
    public static BlockBarrel forestrymahoeBarrel;
    public static BlockBarrel forestrymahoganyBarrel;
    public static BlockBarrel forestrymapleBarrel;
    public static BlockBarrel forestrypadaukBarrel;
    public static BlockBarrel forestrypalmBarrel;
    public static BlockBarrel forestrypapayaBarrel;
    public static BlockBarrel forestrypineBarrel;
    public static BlockBarrel forestryplumBarrel;
    public static BlockBarrel forestrypoplarBarrel;
    public static BlockBarrel forestrysequoiaBarrel;
    public static BlockBarrel forestryteakBarrel;
    public static BlockBarrel forestrywalnutBarrel;
    public static BlockBarrel forestrywengeBarrel;
    public static BlockBarrel forestrywillowBarrel;
    public static BlockBarrel forestryzebrawoodBarrel;
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
    	
    	
    	oakBarrel = new BlockBarrel();
        oakBarrel.setUnlocalizedName("oakbarrel");
        spruceBarrel = new BlockBarrel();
        spruceBarrel.setUnlocalizedName("sprucebarrel");
        birchBarrel = new BlockBarrel();
        birchBarrel.setUnlocalizedName("birchbarrel");
        jungleBarrel = new BlockBarrel();
        jungleBarrel.setUnlocalizedName("junglebarrel");
        acaciaBarrel = new BlockBarrel();
        acaciaBarrel.setUnlocalizedName("acaciabarrel");
        darkoakBarrel = new BlockBarrel();
        darkoakBarrel.setUnlocalizedName("darkoakbarrel");        
        
        
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
        GameRegistry.registerBlock(oakBarrel , "oakbarrel");
        GameRegistry.registerBlock(spruceBarrel , "sprucebarrel");
        GameRegistry.registerBlock(birchBarrel , "birchbarrel");
        GameRegistry.registerBlock(jungleBarrel , "junglebarrel");
        GameRegistry.registerBlock(acaciaBarrel , "acaciabarrel");
        GameRegistry.registerBlock(darkoakBarrel , "darkoakbarrel");
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
        
        
        
        
        addBarrel(oakBarrel,false, false,new ItemStack(Blocks.PLANKS,1,0));
        addBarrel(oakBarrel,true, false,new ItemStack(Blocks.PLANKS,1,0));
        addBarrel(oakBarrel,false, true,new ItemStack(Blocks.PLANKS,1,0));
        addBarrel(oakBarrel,true, true,new ItemStack(Blocks.PLANKS,1,0));
        
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
        
        
        /*
         * FORESTRY
         */
        /*
         * FORESTRY
         */
        if (Loader.isModLoaded("forestry"))
        {
        	forestryacaciaBarrel = new BlockBarrel();
        	forestryacaciaBarrel.setUnlocalizedName("forestryacaciabarrel"); 
	        forestrybalsaBarrel = new BlockBarrel();
	        forestrybalsaBarrel.setUnlocalizedName("forestrybalsabarrel");
	        forestrybaobabBarrel = new BlockBarrel();
	        forestrybaobabBarrel.setUnlocalizedName("forestrybaobabbarrel");
	        forestrycherryBarrel = new BlockBarrel();
	        forestrycherryBarrel.setUnlocalizedName("forestrycherrybarrel");
	        forestrychestnutBarrel = new BlockBarrel();
	        forestrychestnutBarrel.setUnlocalizedName("forestrychestnutbarrel");
	        forestrycitrusBarrel = new BlockBarrel(); 
	        forestrycitrusBarrel.setUnlocalizedName("forestrycitrusbarrel");
	        forestrycocoboloBarrel = new BlockBarrel();
	        forestrycocoboloBarrel.setUnlocalizedName("forestrycocobolobarrel");
	        forestryebonyBarrel = new BlockBarrel();
	        forestryebonyBarrel.setUnlocalizedName("forestryebonybarrel");
	        forestrygiganteumBarrel = new BlockBarrel();
	        forestrygiganteumBarrel.setUnlocalizedName("forestrygiganteumbarrel");
	        forestrygreenheartBarrel = new BlockBarrel();
	        forestrygreenheartBarrel.setUnlocalizedName("forestrygreenheartbarrel");
	        forestryipeBarrel = new BlockBarrel();
	        forestryipeBarrel.setUnlocalizedName("forestryipebarrel");
	        forestrykapokBarrel = new BlockBarrel();
	        forestrykapokBarrel.setUnlocalizedName("forestrykapokbarrel");
	        forestrylarchBarrel = new BlockBarrel();
	        forestrylarchBarrel.setUnlocalizedName("forestrylarchbarrel");
	        forestrylimeBarrel = new BlockBarrel();
	        forestrylimeBarrel.setUnlocalizedName("forestrylimebarrel");
	        forestrymahoeBarrel = new BlockBarrel();
	        forestrymahoeBarrel.setUnlocalizedName("forestrymahoebarrel");
	        forestrymahoganyBarrel = new BlockBarrel();
	        forestrymahoganyBarrel.setUnlocalizedName("forestrymahoganybarrel");
	        forestrymapleBarrel = new BlockBarrel();
	        forestrymapleBarrel.setUnlocalizedName("forestrymaplebarrel");
	        
	        forestrypadaukBarrel = new BlockBarrel();
	        forestrypadaukBarrel.setUnlocalizedName("forestrypadaukbarrel");
	        forestrypalmBarrel = new BlockBarrel();
	        forestrypalmBarrel.setUnlocalizedName("forestrypalmbarrel");
	        forestrypapayaBarrel = new BlockBarrel();
	        forestrypapayaBarrel.setUnlocalizedName("forestrypapayabarrel");
	        forestrypineBarrel = new BlockBarrel();
	        forestrypineBarrel.setUnlocalizedName("forestrypinebarrel");
	        forestryplumBarrel = new BlockBarrel();
	        forestryplumBarrel.setUnlocalizedName("forestryplumbarrel");
	        forestrypoplarBarrel = new BlockBarrel();
	        forestrypoplarBarrel.setUnlocalizedName("forestrypoplarbarrel");
	        forestrysequoiaBarrel = new BlockBarrel();
	        forestrysequoiaBarrel.setUnlocalizedName("forestrysequoiabarrel");
	        forestryteakBarrel = new BlockBarrel();
	        forestryteakBarrel.setUnlocalizedName("forestryteakbarrel");
	        forestrywalnutBarrel = new BlockBarrel();
	        forestrywalnutBarrel.setUnlocalizedName("forestrywalnutbarrel");
	        forestrywengeBarrel = new BlockBarrel();
	        forestrywengeBarrel.setUnlocalizedName("forestrywengebarrel");
	        forestrywillowBarrel = new BlockBarrel();
	        forestrywillowBarrel.setUnlocalizedName("forestrywillowbarrel");
	        forestryzebrawoodBarrel = new BlockBarrel();
	        forestryzebrawoodBarrel.setUnlocalizedName("forestryzebrawoodbarrel");
        }
    	if (Loader.isModLoaded("forestry"))
        {
        	GameRegistry.registerBlock(forestryacaciaBarrel,"forestryacaciabarrel");
        	GameRegistry.registerBlock(forestrybalsaBarrel,"forestrybalsabarrel");
        	GameRegistry.registerBlock(forestrybaobabBarrel,"forestrybaobabbarrel");
        	GameRegistry.registerBlock(forestrycherryBarrel,"forestrycherrybarrel");
        	GameRegistry.registerBlock(forestrychestnutBarrel,"forestrychestnutbarrel");
        	GameRegistry.registerBlock(forestrycitrusBarrel,"forestrycitrusbarrel");
        	GameRegistry.registerBlock(forestrycocoboloBarrel,"forestrycocobolobarrel");
        	GameRegistry.registerBlock(forestryebonyBarrel,"forestryebonybarrel");
        	GameRegistry.registerBlock(forestrygiganteumBarrel,"forestrygiganteumbarrel");
        	GameRegistry.registerBlock(forestrygreenheartBarrel,"forestrygreenheartbarrel");
        	GameRegistry.registerBlock(forestryipeBarrel,"forestryipebarrel");
        	GameRegistry.registerBlock(forestrykapokBarrel,"forestrykapokbarrel");
        	GameRegistry.registerBlock(forestrylarchBarrel,"forestrylarchbarrel");
        	GameRegistry.registerBlock(forestrylimeBarrel,"forestrylimebarrel");
        	GameRegistry.registerBlock(forestrymahoeBarrel,"forestrymahoebarrel");
        	GameRegistry.registerBlock(forestrymahoganyBarrel,"forestrymahoganybarrel");
        	GameRegistry.registerBlock(forestrymapleBarrel,"forestrymaplebarrel");
        	GameRegistry.registerBlock(forestrypadaukBarrel,"forestrypadaukbarrel");
        	GameRegistry.registerBlock(forestrypalmBarrel,"forestrypalmbarrel");
        	GameRegistry.registerBlock(forestrypapayaBarrel,"forestrypapayabarrel");
        	GameRegistry.registerBlock(forestrypineBarrel,"forestrypinebarrel");
        	GameRegistry.registerBlock(forestryplumBarrel,"forestryplumbarrel");
        	GameRegistry.registerBlock(forestrypoplarBarrel,"forestrypoplarbarrel");
        	GameRegistry.registerBlock(forestrysequoiaBarrel,"forestrysequoiabarrel");
        	GameRegistry.registerBlock(forestryteakBarrel,"forestryteakbarrel");
        	GameRegistry.registerBlock(forestrywalnutBarrel,"forestrywalnutbarrel");
        	GameRegistry.registerBlock(forestrywengeBarrel,"forestrywengebarrel");
        	GameRegistry.registerBlock(forestrywillowBarrel,"forestrywillowbarrel");
        	GameRegistry.registerBlock(forestryzebrawoodBarrel,"forestryzebrawoodbarrel");
        }
    	if (Loader.isModLoaded("forestry"))
        {
        	
        	Item tempPlank= Item.REGISTRY.getObject(new ResourceLocation("forestry:planks.0"));
        	Item tempPlank1= Item.REGISTRY.getObject(new ResourceLocation("forestry:planks.1"));
        	//Block tempPlank2 = Block.getBlockFromName("forestry:planks.cocobolo");
        	
        	addBarrel(forestryacaciaBarrel,false, false,new ItemStack(tempPlank ,1,2));
            addBarrel(forestryacaciaBarrel,false, true,new ItemStack(tempPlank ,1,2));
            addBarrel(forestryacaciaBarrel,true, false,new ItemStack(tempPlank ,1,2));
            addBarrel(forestryacaciaBarrel,true, true,new ItemStack(tempPlank ,1,2));
            
            addBarrel(forestrybalsaBarrel,false, false,new ItemStack(tempPlank ,1,11));
            addBarrel(forestrybalsaBarrel,false, true,new ItemStack(tempPlank ,1,11));
            addBarrel(forestrybalsaBarrel,true, false,new ItemStack(tempPlank ,1,11));
            addBarrel(forestrybalsaBarrel,true, true,new ItemStack(tempPlank ,1,11));
            
            addBarrel(forestrybaobabBarrel,false, false,new ItemStack(tempPlank ,1,6));
            addBarrel(forestrybaobabBarrel,false, true,new ItemStack(tempPlank ,1,6));
            addBarrel(forestrybaobabBarrel,true, false,new ItemStack(tempPlank ,1,6));
            addBarrel(forestrybaobabBarrel,true, true,new ItemStack(tempPlank ,1,6));
            
            addBarrel(forestrycherryBarrel,false, false,new ItemStack(tempPlank ,1,15));
            addBarrel(forestrycherryBarrel,false, true,new ItemStack(tempPlank ,1,15));
            addBarrel(forestrycherryBarrel,true, false,new ItemStack(tempPlank ,1,15));
            addBarrel(forestrycherryBarrel,true, true,new ItemStack(tempPlank ,1,15));
            
            addBarrel(forestrychestnutBarrel,false, false,new ItemStack(tempPlank ,1,4));
            addBarrel(forestrychestnutBarrel,false, true,new ItemStack(tempPlank ,1,4));
            addBarrel(forestrychestnutBarrel,true, false,new ItemStack(tempPlank ,1,4));
            addBarrel(forestrychestnutBarrel,true, true,new ItemStack(tempPlank ,1,4));
            
            addBarrel(forestrycitrusBarrel,false, false,new ItemStack(tempPlank1 ,1,7));
            addBarrel(forestrycitrusBarrel,false, true,new ItemStack(tempPlank1 ,1,7));
            addBarrel(forestrycitrusBarrel,true, false,new ItemStack(tempPlank1 ,1,7));
            addBarrel(forestrycitrusBarrel,true, true,new ItemStack(tempPlank1 ,1,7));
            
            addBarrel(forestrycocoboloBarrel,false, false,new ItemStack(tempPlank1 ,1,11));
            addBarrel(forestrycocoboloBarrel,false, true,new ItemStack(tempPlank1 ,1,11));
            addBarrel(forestrycocoboloBarrel,true, false,new ItemStack(tempPlank1 ,1,11));
            addBarrel(forestrycocoboloBarrel,true, true,new ItemStack(tempPlank1 ,1,11));
            
            addBarrel(forestryebonyBarrel,false, false,new ItemStack(tempPlank ,1,9));
            addBarrel(forestryebonyBarrel,false, true,new ItemStack(tempPlank ,1,9));
            addBarrel(forestryebonyBarrel,true, false,new ItemStack(tempPlank ,1,9));
            addBarrel(forestryebonyBarrel,true, true,new ItemStack(tempPlank ,1,9));
            
            addBarrel(forestrygiganteumBarrel,false, false,new ItemStack(tempPlank1 ,1,8));
            addBarrel(forestrygiganteumBarrel,false, true,new ItemStack(tempPlank1 ,1,8));
            addBarrel(forestrygiganteumBarrel,true, false,new ItemStack(tempPlank1 ,1,8));
            addBarrel(forestrygiganteumBarrel,true, true,new ItemStack(tempPlank1 ,1,8));
            
            addBarrel(forestrygreenheartBarrel,false, false,new ItemStack(tempPlank ,1,14));
            addBarrel(forestrygreenheartBarrel,false, true,new ItemStack(tempPlank ,1,14));
            addBarrel(forestrygreenheartBarrel,true, false,new ItemStack(tempPlank ,1,14));
            addBarrel(forestrygreenheartBarrel,true, true,new ItemStack(tempPlank ,1,14));
            
            addBarrel(forestryipeBarrel,false, false,new ItemStack(tempPlank1 ,1,9));
            addBarrel(forestryipeBarrel,false, true,new ItemStack(tempPlank1 ,1,9));
            addBarrel(forestryipeBarrel,true, false,new ItemStack(tempPlank1 ,1,9));
            addBarrel(forestryipeBarrel,true, true,new ItemStack(tempPlank1 ,1,9));
            
            addBarrel(forestrykapokBarrel,false, false,new ItemStack(tempPlank ,1,8));
            addBarrel(forestrykapokBarrel,false, true,new ItemStack(tempPlank ,1,8));
            addBarrel(forestrykapokBarrel,true, false,new ItemStack(tempPlank ,1,8));
            addBarrel(forestrykapokBarrel,true, true,new ItemStack(tempPlank ,1,8));
            
            addBarrel(forestrylarchBarrel,false, false,new ItemStack(tempPlank ,1,0));
            addBarrel(forestrylarchBarrel,false, true,new ItemStack(tempPlank ,1,0));
            addBarrel(forestrylarchBarrel,true, false,new ItemStack(tempPlank ,1,0));
            addBarrel(forestrylarchBarrel,true, true,new ItemStack(tempPlank ,1,0));
            
            addBarrel(forestrylimeBarrel,false, false,new ItemStack(tempPlank ,1,3));
            addBarrel(forestrylimeBarrel,false, true,new ItemStack(tempPlank ,1,3));
            addBarrel(forestrylimeBarrel,true, false,new ItemStack(tempPlank ,1,3));
            addBarrel(forestrylimeBarrel,true, true,new ItemStack(tempPlank ,1,3));
            
            addBarrel(forestrymahoeBarrel,false, false,new ItemStack(tempPlank1 ,1,0));
            addBarrel(forestrymahoeBarrel,false, true,new ItemStack(tempPlank1 ,1,0));
            addBarrel(forestrymahoeBarrel,true, false,new ItemStack(tempPlank1 ,1,0));
            addBarrel(forestrymahoeBarrel,true, true,new ItemStack(tempPlank1 ,1,0));
            
            addBarrel(forestrymahoganyBarrel,false, false,new ItemStack(tempPlank ,1,10));
            addBarrel(forestrymahoganyBarrel,false, true,new ItemStack(tempPlank ,1,10));
            addBarrel(forestrymahoganyBarrel,true, false,new ItemStack(tempPlank ,1,10));
            addBarrel(forestrymahoganyBarrel,true, true,new ItemStack(tempPlank ,1,10));
            
            addBarrel(forestrymapleBarrel,false, false,new ItemStack(tempPlank1 ,1,6));
            addBarrel(forestrymapleBarrel,false, true,new ItemStack(tempPlank1 ,1,6));
            addBarrel(forestrymapleBarrel,true, false,new ItemStack(tempPlank1 ,1,6));
            addBarrel(forestrymapleBarrel,true, true,new ItemStack(tempPlank1 ,1,6));
            
            addBarrel(forestrypadaukBarrel,false, false,new ItemStack(tempPlank1 ,1,10));
            addBarrel(forestrypadaukBarrel,false, true,new ItemStack(tempPlank1 ,1,10));
            addBarrel(forestrypadaukBarrel,true, false,new ItemStack(tempPlank1 ,1,10));
            addBarrel(forestrypadaukBarrel,true, true,new ItemStack(tempPlank1 ,1,10));
            
            addBarrel(forestrypalmBarrel,false, false,new ItemStack(tempPlank1 ,1,2));
            addBarrel(forestrypalmBarrel,false, true,new ItemStack(tempPlank1 ,1,2));
            addBarrel(forestrypalmBarrel,true, false,new ItemStack(tempPlank1 ,1,2));
            addBarrel(forestrypalmBarrel,true, true,new ItemStack(tempPlank1 ,1,2));
            
            addBarrel(forestrypapayaBarrel,false, false,new ItemStack(tempPlank1 ,1,3));
            addBarrel(forestrypapayaBarrel,false, true,new ItemStack(tempPlank1 ,1,3));
            addBarrel(forestrypapayaBarrel,true, false,new ItemStack(tempPlank1 ,1,3));
            addBarrel(forestrypapayaBarrel,true, true,new ItemStack(tempPlank1 ,1,3));
            
            addBarrel(forestrypineBarrel,false, false,new ItemStack(tempPlank1, 1,4));
            addBarrel(forestrypineBarrel,false, true,new ItemStack(tempPlank1, 1,4));
            addBarrel(forestrypineBarrel,true, false,new ItemStack(tempPlank1, 1,4));
            addBarrel(forestrypineBarrel,true, true,new ItemStack(tempPlank1, 1,4));
            
            addBarrel(forestryplumBarrel,false, false,new ItemStack(tempPlank1 ,1,5));
            addBarrel(forestryplumBarrel,false, true,new ItemStack(tempPlank1 ,1,5));
            addBarrel(forestryplumBarrel,true, false,new ItemStack(tempPlank1 ,1,5));
            addBarrel(forestryplumBarrel,true, true,new ItemStack(tempPlank1 ,1,5));
            
            addBarrel(forestrypoplarBarrel,false, false,new ItemStack(tempPlank1 ,1,1));
            addBarrel(forestrypoplarBarrel,false, true,new ItemStack(tempPlank1 ,1,1));
            addBarrel(forestrypoplarBarrel,true, false,new ItemStack(tempPlank1 ,1,1));
            addBarrel(forestrypoplarBarrel,true, true,new ItemStack(tempPlank1 ,1,1));
            
            addBarrel(forestrysequoiaBarrel,false, false,new ItemStack(tempPlank ,1,7));
            addBarrel(forestrysequoiaBarrel,false, true,new ItemStack(tempPlank ,1,7));
            addBarrel(forestrysequoiaBarrel,true, false,new ItemStack(tempPlank ,1,7));
            addBarrel(forestrysequoiaBarrel,true, true,new ItemStack(tempPlank ,1,7));
            
            addBarrel(forestryteakBarrel,false, false,new ItemStack(tempPlank ,1,1));
            addBarrel(forestryteakBarrel,false, true,new ItemStack(tempPlank ,1,1));
            addBarrel(forestryteakBarrel,true, false,new ItemStack(tempPlank ,1,1));
            addBarrel(forestryteakBarrel,true, true,new ItemStack(tempPlank ,1,1));
            
            addBarrel(forestrywalnutBarrel,false, false,new ItemStack(tempPlank ,1,13));
            addBarrel(forestrywalnutBarrel,false, true,new ItemStack(tempPlank ,1,13));
            addBarrel(forestrywalnutBarrel,true, false,new ItemStack(tempPlank ,1,13));
            addBarrel(forestrywalnutBarrel,true, true,new ItemStack(tempPlank ,1,13));
            
            addBarrel(forestrywengeBarrel,false, false,new ItemStack(tempPlank ,1,5));
            addBarrel(forestrywengeBarrel,false, true,new ItemStack(tempPlank ,1,5));
            addBarrel(forestrywengeBarrel,true, false,new ItemStack(tempPlank ,1,5));
            addBarrel(forestrywengeBarrel,true, true,new ItemStack(tempPlank ,1,5));
            
            addBarrel(forestrywillowBarrel,false, false,new ItemStack(tempPlank ,1,12));
            addBarrel(forestrywillowBarrel,false, true,new ItemStack(tempPlank ,1,12));
            addBarrel(forestrywillowBarrel,true, false,new ItemStack(tempPlank ,1,12));
            addBarrel(forestrywillowBarrel,true, true,new ItemStack(tempPlank ,1,12));
            
            addBarrel(forestryzebrawoodBarrel,false, false,new ItemStack(tempPlank1 ,1,12));
            addBarrel(forestryzebrawoodBarrel,false, true,new ItemStack(tempPlank1 ,1,12));
            addBarrel(forestryzebrawoodBarrel,true, false,new ItemStack(tempPlank1 ,1,12));
            addBarrel(forestryzebrawoodBarrel,true, true,new ItemStack(tempPlank1 ,1,12));
            
			
			
			

        }
        proxy.init(event);
        
       
        
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	
    	
    }
}
