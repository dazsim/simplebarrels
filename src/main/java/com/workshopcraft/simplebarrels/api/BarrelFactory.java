package com.workshopcraft.simplebarrels.api;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class BarrelFactory {

    private List<BarrelData> barrelList = new ArrayList<BarrelData>();

    public BarrelFactory() {
        // initialize the barrels list
        SimpleBarrels.barrels = new ArrayList<BlockBarrel>();

        List<JsonObject> configEntries = BarrelJsonLoader.load();

        for (JsonObject configJson : configEntries) {
            generateBarrelsFromJsonObject(configJson);
        }
    }

    private void generateBarrelsFromJsonObject(JsonObject configJson) {
        String modId = "";
        String resourceDomain = "minecraft";

        if (configJson.has("mod_id")) {
            modId = configJson.get("mod_id").getAsString();
        }

        if (configJson.has("resource_domain")) {
            resourceDomain = configJson.get("resource_domain").getAsString();
        }

        if (!checkDependency(modId)) {
            // The required mod defined in the configuration file IS NOT loaded.
            // Skip this group of barrels
            return;
        }

        if (!configJson.has("barrels") || !configJson.get("barrels").isJsonArray()) {
            // Config doesn't have a "barrels" entry, or that entry is not a JsonArray
            return;
        }

        JsonArray barrels = configJson.get("barrels").getAsJsonArray();

        for (JsonElement barrel : barrels) {
            BarrelData barrelData = new BarrelData(modId, resourceDomain, barrel.getAsJsonObject());

            if (!blockStateModelExists(barrelData.getUnlocalizedName())) {
                // The block state model file for this barrel does not exist. Do not register it.
                // Probably caused by a barrel imported from our barrels JSON files that doesn't have
                // an associated model file.
                continue;
            }

            barrelList.add(barrelData);

            // Create BlockBarrels and add to SimpleBarrels.barrels
            BlockBarrel blockBarrel = new BlockBarrel(barrelData.getUnlocalizedName());
            SimpleBarrels.barrels.add(blockBarrel);
        }
    }

    private Boolean checkDependency(String mod_dependency) {
        return mod_dependency.equals("") || Loader.isModLoaded(mod_dependency); // if it's empty we don't call isModLoaded()
    }

    private void addBarrelRecipe(BlockBarrel barrel, Boolean withItemFrame, Boolean withComparator, ItemStack plank) {
        ItemStack stack = new ItemStack(barrel, 1);
        NBTTagCompound tagCompound = new NBTTagCompound();

        tagCompound.setBoolean("frame", withItemFrame);
        tagCompound.setBoolean("comp", withComparator);

        stack.setTagCompound(tagCompound);

        char itemFrameSlot = (withItemFrame ? 'F' : ' ');
        char comparatorSlot = (withComparator ? 'R' : ' ');

        GameRegistry.addRecipe(new ShapedOreRecipe(
                stack,
                "WIW", (itemFrameSlot + "C" + comparatorSlot), "WIW",
                'W', plank,
                'I', Items.IRON_INGOT,
                'R', Items.COMPARATOR,
                'C', "chestWood",
                'F', Items.ITEM_FRAME
        ));
    }

    private Boolean blockStateModelExists(String uname) {
        if (uname.substring(0, 5).equals("tile.")) {
            uname = uname.substring(5);
        }
        InputStream resource = JsonElement.class.getResourceAsStream("/assets/simplebarrels/blockstates/" + uname + ".json");

        return resource != null;
    }

    /***/
    public BlockBarrel getBarrelFromUnlocalizedName(String name) {
        for (BlockBarrel barrel : SimpleBarrels.barrels) {
            if (barrel.getUnlocalizedName().equals(name)) {
                return barrel;
            }
        }
        return null;
    }

    /***/
    public void init() {
        for (BarrelData barrelData : barrelList) {

            BlockBarrel barrelBlock = getBarrelFromUnlocalizedName("tile." + barrelData.getUnlocalizedName());

            if (barrelBlock == null) {
                // No BlockBarrel found for the given BarrelData
                continue;
            }

            // Register BlockBarrels
            GameRegistry.register(barrelBlock);
            GameRegistry.register(new ItemBlock(barrelBlock), barrelBlock.getRegistryName());

            Item barrelPlankItem = Item.REGISTRY.getObject(new ResourceLocation(barrelData.getFullResourcePath()));
            int resourceIndex = barrelData.getResourceIndex();

            addBarrelRecipe(barrelBlock, false, false, new ItemStack(barrelPlankItem, 1, resourceIndex));
            addBarrelRecipe(barrelBlock, true, false, new ItemStack(barrelPlankItem, 1, resourceIndex));
            addBarrelRecipe(barrelBlock, false, true, new ItemStack(barrelPlankItem, 1, resourceIndex));
            addBarrelRecipe(barrelBlock, true, true, new ItemStack(barrelPlankItem, 1, resourceIndex));
        }
    }

    @SideOnly(Side.CLIENT)
    public void clientInit() {
        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

        for (BlockBarrel barrel : SimpleBarrels.barrels) {
            Item blockItem = Item.getItemFromBlock(barrel);
            String resourceDomain = SimpleBarrels.MODID + ":" + blockItem.getUnlocalizedName().substring(5);

            ModelResourceLocation loc = new ModelResourceLocation(resourceDomain, "inventory");

            renderItem.getItemModelMesher().register(blockItem, 0, loc);
        }

    }

}
