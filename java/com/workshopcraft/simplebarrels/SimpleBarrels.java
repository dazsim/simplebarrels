package com.workshopcraft.simplebarrels;

import com.workshopcraft.simplebarrels.blocks.BlockBarrel;
import com.workshopcraft.simplebarrels.handlers.BarrelHandler;
//import com.workshopcraft.simplebarrels.network.BarrelSyncClient;
//import com.workshopcraft.simplebarrels.network.BarrelSyncServer;
import com.workshopcraft.simplebarrels.proxy.barrelcommonproxy;
import com.workshopcraft.simplebarrels.tiles.TileEntityBarrel;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = SimpleBarrels.MODID, version = SimpleBarrels.VERSION)
public class SimpleBarrels
{
    public static final String MODID = "simplebarrels";
    public static final String VERSION = "1.2";
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
        
        
        proxy.preInit(event);
        
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
        //System.out.println("STUFF REGISTERD?? YARP");
        //MinecraftForge.EVENT_BUS.register(new BarrelHandler());
        FMLCommonHandler.instance().bus().register(new BarrelHandler());
        
        /*GameRegistry.addRecipe(new ItemStack(blockBarrel,1), new Object[]{
				"WCW",
				"WRW",
				"WFW",
				'W',Blocks.LOG,'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME
		});*/
        
        ItemStack i = new ItemStack(oakBarrel,1);
        GameRegistry.addRecipe(i, new Object[]{
				"WIW",
				"FCR",
				"WIW",
				'W',new ItemStack(Blocks.PLANKS,1,0),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
		});
        //i = new ItemStack(,1);
        
        GameRegistry.addRecipe(new ItemStack(spruceBarrel,1), new Object[]{
				"WIW",
				"FCR",
				"WIW",
				'W',new ItemStack(Blocks.PLANKS,1,1),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
		});
        GameRegistry.addRecipe(new ItemStack(birchBarrel,1), new Object[]{
				"WIW",
				"FCR",
				"WIW",
				'W',new ItemStack(Blocks.PLANKS,1,2),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
		});
        GameRegistry.addRecipe(new ItemStack(jungleBarrel,1), new Object[]{
				"WIW",
				"FCR",
				"WIW",
				'W',new ItemStack(Blocks.PLANKS,1,3),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
		});
        GameRegistry.addRecipe(new ItemStack(acaciaBarrel,1), new Object[]{
				"WIW",
				"FCR",
				"WIW",
				'W',new ItemStack(Blocks.PLANKS,1,4),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
		});
        GameRegistry.addRecipe(new ItemStack(darkoakBarrel,1), new Object[]{
				"WIW",
				"FCR",
				"WIW",
				'W',new ItemStack(Blocks.PLANKS,1,5),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
		});
        /*
         * FORESTRY
         */
        if (Loader.isModLoaded("forestry"))
        {
        	
        	Item tempPlank= Item.REGISTRY.getObject(new ResourceLocation("forestry:planks.0"));
        	Item tempPlank1= Item.REGISTRY.getObject(new ResourceLocation("forestry:planks.1"));
        	//Block tempPlank2 = Block.getBlockFromName("forestry:planks.acacia");
        	
        	
        	GameRegistry.addRecipe(new ItemStack(forestryacaciaBarrel,1), new Object[]{
				"WIW",
				"FCR",
				"WIW",
				'W',new ItemStack(tempPlank ,1,2),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
			 
        	
        	GameRegistry.addRecipe(new ItemStack(forestrybalsaBarrel,1), new Object[]{
				"WIW",
				"FCR",
				"WIW",
				'W',new ItemStack(tempPlank ,1,11),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
			
        	
        	GameRegistry.addRecipe(new ItemStack(forestrybaobabBarrel,1), new Object[]{
				"WIW",
				"FCR",
				"WIW",
				'W',new ItemStack(tempPlank ,1,6),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
			
        	
        	GameRegistry.addRecipe(new ItemStack(forestrycherryBarrel,1), new Object[]{
				"WIW",
				"FCR",
				"WIW",
				'W',new ItemStack(tempPlank ,1,15),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
			
        	
        	GameRegistry.addRecipe(new ItemStack(forestrychestnutBarrel,1), new Object[]{
				"WIW",
				"FCR",
				"WIW",
				'W',new ItemStack(tempPlank ,1,4),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
			
        	
        	GameRegistry.addRecipe(new ItemStack(forestrycitrusBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank1 ,1,7),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
			
        	
        	GameRegistry.addRecipe(new ItemStack(forestrycocoboloBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank1 ,1,11),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
        	
        	GameRegistry.addRecipe(new ItemStack(forestryebonyBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank ,1,9),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
        	
        	GameRegistry.addRecipe(new ItemStack(forestrygiganteumBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank1 ,1,8),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
        	
        	GameRegistry.addRecipe(new ItemStack(forestrygreenheartBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank ,1,14),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
        	
        	GameRegistry.addRecipe(new ItemStack(forestryipeBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank1 ,1,9),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
        	
        	GameRegistry.addRecipe(new ItemStack(forestrykapokBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank ,1,8),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
        	
        	GameRegistry.addRecipe(new ItemStack(forestrylarchBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank ,1,0),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
        	
        	GameRegistry.addRecipe(new ItemStack(forestrylimeBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank ,1,3),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
        	
        	GameRegistry.addRecipe(new ItemStack(forestrymahoeBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank1 ,1,0),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
        	GameRegistry.addRecipe(new ItemStack(forestrymahoganyBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank ,1,10),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
        	GameRegistry.addRecipe(new ItemStack(forestrymapleBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank1 ,1,6),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
        	GameRegistry.addRecipe(new ItemStack(forestrypadaukBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank1 ,1,10),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
        	GameRegistry.addRecipe(new ItemStack(forestrypalmBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank1 ,1,2),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
        	GameRegistry.addRecipe(new ItemStack(forestrypapayaBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank1 ,1,3),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
        	GameRegistry.addRecipe(new ItemStack(forestrypineBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank1 ,1,4),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
			
        	GameRegistry.addRecipe(new ItemStack(forestryplumBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank1 ,1,5),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
			
        	GameRegistry.addRecipe(new ItemStack(forestrypoplarBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank1 ,1,1),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
			//broken but appears correct???
        	GameRegistry.addRecipe(new ItemStack(forestrysequoiaBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank ,1,7),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
			
        	GameRegistry.addRecipe(new ItemStack(forestryteakBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank ,1,1),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
			
        	GameRegistry.addRecipe(new ItemStack(forestrywalnutBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank ,1,13),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
			
        	GameRegistry.addRecipe(new ItemStack(forestrywengeBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank ,1,5),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
			
        	GameRegistry.addRecipe(new ItemStack(forestrywillowBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank ,1,12),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
			
        	GameRegistry.addRecipe(new ItemStack(forestryzebrawoodBarrel,1), new Object[]{
				"WIW","FCR","WIW",'W',new ItemStack(tempPlank1 ,1,12),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
			});
			
			
			

        }
        proxy.init(event);
        
       
        
    }
}
