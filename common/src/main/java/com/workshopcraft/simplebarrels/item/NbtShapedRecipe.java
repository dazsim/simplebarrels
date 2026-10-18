package com.workshopcraft.simplebarrels.item;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

// shaped recipe whose result keeps its nbt, vanilla fabric drops it
public class NbtShapedRecipe extends ShapedRecipe {
    public NbtShapedRecipe(ShapedRecipe base, ItemStack result) {
        super(base.getId(), base.getGroup(), base.category(), base.getWidth(), base.getHeight(), base.getIngredients(), result, base.showNotification());
    }

    @Override
    public RecipeSerializer<?> getSerializer() { return ModContent.NBT_SHAPED; }

    public static class Serializer implements RecipeSerializer<NbtShapedRecipe> {
        @Override
        public NbtShapedRecipe fromJson(ResourceLocation id, JsonObject json) {
            ShapedRecipe base = RecipeSerializer.SHAPED_RECIPE.fromJson(id, json);
            ItemStack result = base.getResultItem(null).copy();
            JsonObject r = GsonHelper.getAsJsonObject(json, "result");
            if (r.has("nbt") && JsonOps.INSTANCE.convertTo(NbtOps.INSTANCE, r.get("nbt")) instanceof CompoundTag tag) result.setTag(tag);
            return new NbtShapedRecipe(base, result);
        }

        @Override
        public NbtShapedRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            ShapedRecipe base = RecipeSerializer.SHAPED_RECIPE.fromNetwork(id, buf);
            return new NbtShapedRecipe(base, base.getResultItem(null));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, NbtShapedRecipe recipe) {
            RecipeSerializer.SHAPED_RECIPE.toNetwork(buf, recipe);
        }
    }
}
