package com.workshopcraft.simplebarrels;

import com.workshopcraft.simplebarrels.blocks.BlockBarrel;
import com.workshopcraft.simplebarrels.handlers.BarrelHandler;
//import com.workshopcraft.simplebarrels.network.BarrelSyncClient;
//import com.workshopcraft.simplebarrels.network.BarrelSyncServer;
import com.workshopcraft.simplebarrels.proxy.barrelcommonproxy;
import com.workshopcraft.simplebarrels.tiles.TileEntityBarrel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = SimpleBarrels.MODID, version = SimpleBarrels.VERSION)
public class SimpleBarrels
{
    public static final String MODID = "simplebarrels";
    public static final String VERSION = "1.0a";
    public static BlockBarrel blockBarrel;
    public static BlockBarrel oakBarrel;
    public static BlockBarrel spruceBarrel;
    public static BlockBarrel birchBarrel;
    public static BlockBarrel jungleBarrel;
    public static BlockBarrel acaciaBarrel;
    public static BlockBarrel darkoakBarrel;
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
        
        
        
        proxy.preInit(event);
        //BarrelNet.registerMessage(BarrelSyncClient.Handler.class, BarrelSyncClient.class,0,Side.CLIENT);
        //BarrelNet2.registerMessage(BarrelSyncServer.Handler.class, BarrelSyncServer.class,1,Side.SERVER);
        
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
        //System.out.println("STUFF REGISTERD?? YARP");
        //MinecraftForge.EVENT_BUS.register(new BarrelHandler());
        FMLCommonHandler.instance().bus().register(new BarrelHandler());
        
        /*GameRegistry.addRecipe(new ItemStack(blockBarrel,1), new Object[]{
				"WCW",
				"WRW",
				"WFW",
				'W',Blocks.LOG,'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME
		});*/
        
        
        GameRegistry.addRecipe(new ItemStack(oakBarrel,1), new Object[]{
				"WIW",
				"FCR",
				"WIW",
				'W',new ItemStack(Blocks.PLANKS,1,0),'C',Blocks.CHEST,'R',Items.COMPARATOR,'F',Items.ITEM_FRAME, 'I',Items.IRON_INGOT
		});
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
        
        
        proxy.init(event);
        
       
        
    }
}
