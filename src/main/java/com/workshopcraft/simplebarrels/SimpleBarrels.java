package com.workshopcraft.simplebarrels;

import java.util.List;

import com.workshopcraft.simplebarrels.api.barrelFactory;
import com.workshopcraft.simplebarrels.blocks.BlockBarrel;
import com.workshopcraft.simplebarrels.handlers.BarrelHandler;
import com.workshopcraft.simplebarrels.items.ItemUpgradeComparator;
import com.workshopcraft.simplebarrels.items.ItemUpgradeItemFrame;
import com.workshopcraft.simplebarrels.items.ItemUpgradeMark1Compressor;
import com.workshopcraft.simplebarrels.items.ItemUpgradeMark2Compressor;
import com.workshopcraft.simplebarrels.items.ItemUpgradeMark3Compressor;
import com.workshopcraft.simplebarrels.items.ItemUpgradeMark4Compressor;
//import com.workshopcraft.simplebarrels.network.BarrelSyncClient;
//import com.workshopcraft.simplebarrels.network.BarrelSyncServer;
import com.workshopcraft.simplebarrels.proxy.barrelcommonproxy;
import com.workshopcraft.simplebarrels.tiles.TileEntityBarrel;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
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
    public static final String VERSION = "1.26";
    
    public static ItemUpgradeComparator upgradeComparator;
    public static ItemUpgradeItemFrame upgradeItemFrame;
    public static ItemUpgradeMark1Compressor upgradeMark1Compressor;
    public static ItemUpgradeMark2Compressor upgradeMark2Compressor;
    public static ItemUpgradeMark3Compressor upgradeMark3Compressor;
    public static ItemUpgradeMark4Compressor upgradeMark4Compressor;
    
    
    
    
    public static SimpleBarrelsTab tabSimpleBarrels;
    
    
    /*
     * 1.3 BARRELS
     */
    public static List<BlockBarrel> barrels;
    public static barrelFactory bFactory;
    /*
     * VANILLA
     */
    
    
   
    
    @SidedProxy(clientSide="com.workshopcraft.simplebarrels.proxy.clientbarrel",serverSide="com.workshopcraft.simplebarrels.proxy.serverbarrel")
    public static barrelcommonproxy proxy;
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
    		tabSimpleBarrels = new SimpleBarrelsTab("tabSimpleBarrelsTab");
    	upgradeComparator = new ItemUpgradeComparator("upgradecomparator");
    	upgradeItemFrame = new ItemUpgradeItemFrame("upgradeitemframe");
    	upgradeMark1Compressor = new ItemUpgradeMark1Compressor("upgrademark1compressor");
    	upgradeMark2Compressor = new ItemUpgradeMark2Compressor("upgrademark2compressor");
    	upgradeMark3Compressor = new ItemUpgradeMark3Compressor("upgrademark3compressor");
    	upgradeMark4Compressor = new ItemUpgradeMark4Compressor("upgrademark4compressor");
    	
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
        GameRegistry.registerTileEntity(TileEntityBarrel.class, "tile_entity_barrel");
        bFactory.init();
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
        GameRegistry.addRecipe(new ItemStack(this.upgradeMark1Compressor,1),new Object[] {
        		" S ",
        		"SPS",
        		" S ",
        		'S',Items.STICK,'B',Blocks.PISTON
        });
        GameRegistry.addRecipe(new ItemStack(this.upgradeMark2Compressor,1),new Object[] {
        		" S ",
        		"SPS",
        		" S ",
        		'S',this.upgradeMark1Compressor,'B',Items.IRON_INGOT
        });
        GameRegistry.addRecipe(new ItemStack(this.upgradeMark3Compressor,1),new Object[] {
        		" S ",
        		"SPS",
        		" S ",
        		'S',this.upgradeMark2Compressor,'B',Items.GOLD_INGOT
        });
        GameRegistry.addRecipe(new ItemStack(this.upgradeMark4Compressor,1),new Object[] {
        		" S ",
        		"SPS",
        		" S ",
        		'S',this.upgradeMark3Compressor,'B',Items.DIAMOND
        });
        FMLCommonHandler.instance().bus().register(new BarrelHandler());
        
        
        
        proxy.init(event);
        
       
        
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	
    	
    }
}
