package com.workshopcraft.simplebarrels.api;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.workshopcraft.simplebarrels.SimpleBarrels;
import com.workshopcraft.simplebarrels.blocks.BlockBarrel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;


public class barrelFactory {
	
	List<barrelData> barrelList;
	
	public barrelFactory()
	{
		// TODO: This public constructor is used to create and register all the barrels in 
		// assets/simplebarrels/factory/barrels.json
			/*List<String> barreljson;*/
			
			
			
				///MDKExample/src/main/resources/assets/simplebarrels/factory/barrels.json
				
				//File barrelFile = new File(barrelFactory.class.getResource("assets/simplebarrels/factory/barrels.json").getPath());
				//File barrelFile = new File("/assets/simplebarrels/factory/barrels.json");
				//if (!barrelFile.exists())
				//{
					Gson gson=new Gson();
					JsonElement json;
					try {
						//json = gson.fromJson(new FileReader(barrelFile),JsonElement.class);
						//InputStreamReader(JsonElement.class.getResourceAsStream("/assets/simplebarrels/factory/barrels.json"));
						json = gson.fromJson(new InputStreamReader(JsonElement.class.getResourceAsStream("/barrels.json")),JsonElement.class);
						barrelData barrel;
						if ((json.isJsonObject()) && (json.getAsJsonObject().has("barrels")))
						{
							JsonElement marker = json.getAsJsonObject().get("barrels");
							
						
							if (marker.isJsonArray())
							{
								JsonArray jarr = marker.getAsJsonArray();
								Type barrelType = new TypeToken<List<barrelData>>(){}.getType();
								int jarrSize = jarr.size();
								int j=0;
								JsonElement bData;
								String tmpBarrelName = "";
								String tmpBarrelSourceBlock = "";
								int tmpBarrelSourceMeta = 0;
								String tmpBarrelDependency = "";
								barrelList = new ArrayList<barrelData>();
								while (j<jarrSize)
								{
								 bData = jarr.get(j);
								 tmpBarrelName = bData.getAsJsonObject().get("unlocalizedname").getAsString();
								 
								 tmpBarrelSourceBlock = bData.getAsJsonObject().get("sourceblock").getAsString();
								 tmpBarrelSourceMeta = Integer.parseInt(bData.getAsJsonObject().get("sourcemeta").getAsString());
								 tmpBarrelDependency = bData.getAsJsonObject().get("dependency").getAsString();
								 barrelList.add(new barrelData(tmpBarrelName,tmpBarrelSourceBlock,tmpBarrelSourceMeta,tmpBarrelDependency));
								 j++;
								}
								//we have loaded our data into an array of barrelData.
								barrelData tmp;
								int i = 0;
								System.out.println(barrelList.size());
								SimpleBarrels.barrels = new ArrayList<BlockBarrel>();
								BlockBarrel bBtmp;
								while( i<barrelList.size())
								{
									
									bBtmp = new BlockBarrel(barrelList.get(i).unlocalizedName);
									if (checkDependency(barrelList.get(i).dependency))
									{
										SimpleBarrels.barrels.add(bBtmp);
									}
									//System.out.println("name: "+barrelList.get(i).unlocalizedName);
									//System.out.println("sourceBlock: "+barrelList.get(i).sourceBlock);
									//System.out.println("sourceMeta: "+barrelList.get(i).sourceMeta);
									//System.out.println("-------------------------------");
									i++;
									
								}
							} else
							{
								System.out.println("NOT an array of JsonStuffz");
							}
						}
						String result = gson.toJson(json);
					} catch (JsonSyntaxException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						System.out.println("JSon SYNTAX ERROR");
					} catch (JsonIOException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						System.out.println("JSon IOException");
					} /*catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}*/
		
		
	}
	public Boolean checkDependency(String dependency)
	{
		if (dependency.equals("")) return true; //if its empty we dont call isModLoaded()
		if (Loader.isModLoaded(dependency))
		{
			return true;
		} else
		{
			return false;
		}
	}
	public barrelFactory(JsonObject barrelList)
	{
		//barrelList.get
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
	
	public BlockBarrel getBarrelFromUnlocalizedName(String name)
	{
		int i = 0;
		BlockBarrel bBtmp;
		while( i<SimpleBarrels.barrels.size())
		{
			//bBtmp = new BlockBarrel(barrelList.get(i).unlocalizedName);
			bBtmp = SimpleBarrels.barrels.get(i);
			if (bBtmp.getUnlocalizedName().equals(name))
			{
				return bBtmp;
			}
		}
		return null;
	}
	
	public void init()
	{
		int i = 0;
		BlockBarrel bBtmp;
		while( i<SimpleBarrels.barrels.size())
		{
			//bBtmp = new BlockBarrel(barrelList.get(i).unlocalizedName);
			bBtmp = SimpleBarrels.barrels.get(i);
			if (checkDependency(barrelList.get(i).dependency))
			{
				GameRegistry.register(bBtmp);
		        GameRegistry.register(new ItemBlock(bBtmp), bBtmp.getRegistryName());
		        Item mcPlank= Item.REGISTRY.getObject(new ResourceLocation(barrelList.get(i).sourceBlock));
		        
		        addBarrel(bBtmp,false, false, new ItemStack(mcPlank,1,barrelList.get(i).sourceMeta));
		        addBarrel(bBtmp,true, false,new ItemStack(mcPlank,1,barrelList.get(i).sourceMeta));
		        addBarrel(bBtmp,false, true,new ItemStack(mcPlank,1,barrelList.get(i).sourceMeta));
		        addBarrel(bBtmp,true, true,new ItemStack(mcPlank,1,barrelList.get(i).sourceMeta));
				i++;
			}
		}
	}
	
	public void clientInit()
	{
		
		int i = 0;
		BlockBarrel bBtmp;
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		while( i<SimpleBarrels.barrels.size())
		{
			//bBtmp = new BlockBarrel(barrelList.get(i).unlocalizedName);
			bBtmp = SimpleBarrels.barrels.get(i);
			if (checkDependency(barrelList.get(i).dependency))
			{
		        //System.out.println(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(bBtmp).getUnlocalizedName().substring(5));
		        renderItem.getItemModelMesher().register(Item.getItemFromBlock(bBtmp), 0, new ModelResourceLocation(SimpleBarrels.MODID + ":" + Item.getItemFromBlock(bBtmp).getUnlocalizedName().substring(5),"inventory"));
				i++;
			}
		}
	}
	
}
