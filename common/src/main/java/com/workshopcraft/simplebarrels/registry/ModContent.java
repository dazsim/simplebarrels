package com.workshopcraft.simplebarrels.registry;

import com.workshopcraft.simplebarrels.SimpleBarrels;
import com.workshopcraft.simplebarrels.block.BarrelBlock;
import com.workshopcraft.simplebarrels.block.BarrelBlockEntity;
import com.workshopcraft.simplebarrels.block.ModdedBarrelBlock;
import com.workshopcraft.simplebarrels.item.DollyItem;
import com.workshopcraft.simplebarrels.item.ModdedBarrelItem;
import com.workshopcraft.simplebarrels.item.NbtShapedRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import com.workshopcraft.simplebarrels.item.UpgradeItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public final class ModContent {
    public static final Map<BarrelWood, BarrelBlock> BARRELS = new EnumMap<>(BarrelWood.class);
    public static ModdedBarrelBlock MODDED_BARREL;
    public static ModdedBarrelItem MODDED_BARREL_ITEM;
    public static BlockEntityType<BarrelBlockEntity> BARREL_BE;
    public static final NbtShapedRecipe.Serializer NBT_SHAPED = new NbtShapedRecipe.Serializer();
    public static CreativeModeTab TAB;
    public static DollyItem DOLLY;
    public static java.util.function.Function<Item.Properties, DollyItem> dollyFactory = DollyItem::new;
    public static BiFunction<Block, Item.Properties, ModdedBarrelItem> moddedItemFactory = ModdedBarrelItem::new;
    public static final List<Item> UPGRADES_ITEMS = new ArrayList<>();

    private ModContent() {}

    public static void registerBlocks(BiConsumer<ResourceLocation, Block> r) {
        for (BarrelWood wood : BarrelWood.values()) {
            BarrelBlock block = new BarrelBlock(props(wood));
            BARRELS.put(wood, block);
            r.accept(SimpleBarrels.id(wood.blockId()), block);
        }
        MODDED_BARREL = new ModdedBarrelBlock(props(BarrelWood.OAK));
        r.accept(SimpleBarrels.id("modded_barrel"), MODDED_BARREL);
    }

    private static BlockBehaviour.Properties props(BarrelWood wood) {
        return BlockBehaviour.Properties.of()
                .mapColor(wood.mapColor)
                .sound(wood.sound)
                .strength(2.5F)
                .ignitedByLava()
                .pushReaction(PushReaction.BLOCK)
                .noOcclusion();
    }

    public static void registerItems(BiConsumer<ResourceLocation, Item> r) {
        BARRELS.forEach((wood, block) -> r.accept(SimpleBarrels.id(wood.blockId()), new BlockItem(block, new Item.Properties())));
        MODDED_BARREL_ITEM = moddedItemFactory.apply(MODDED_BARREL, new Item.Properties());
        r.accept(SimpleBarrels.id("modded_barrel"), MODDED_BARREL_ITEM);
        DOLLY = dollyFactory.apply(new Item.Properties().stacksTo(1));
        r.accept(SimpleBarrels.id("dolly"), DOLLY);
        String[] names = {"comparator_upgrade", "item_frame_upgrade", "mark1_compressor", "mark2_compressor", "mark3_compressor", "mark4_compressor"};
        for (UpgradeItem.Kind k : UpgradeItem.Kind.values()) {
            UpgradeItem item = new UpgradeItem(k, new Item.Properties());
            UPGRADES_ITEMS.add(item);
            r.accept(SimpleBarrels.id(names[k.ordinal()]), item);
        }
    }

    public static void registerBlockEntities(BiConsumer<ResourceLocation, BlockEntityType<?>> r) {
        BARREL_BE = BlockEntityType.Builder.of(BarrelBlockEntity::new, allBarrels()).build(null);
        r.accept(SimpleBarrels.id("barrel"), BARREL_BE);
    }

    private static Block[] allBarrels() {
        List<Block> all = new ArrayList<>(BARRELS.values());
        all.add(MODDED_BARREL);
        return all.toArray(new Block[0]);
    }

    public static void registerRecipeSerializers(BiConsumer<ResourceLocation, RecipeSerializer<?>> r) {
        r.accept(SimpleBarrels.id("shaped_nbt"), NBT_SHAPED);
    }

    public static void registerCreativeTabs(BiConsumer<ResourceLocation, CreativeModeTab> r) {
        TAB = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                .title(Component.translatable("itemGroup." + SimpleBarrels.MOD_ID))
                .icon(() -> new ItemStack(BARRELS.get(BarrelWood.OAK)))
                .displayItems((params, out) -> {
                    BARRELS.values().forEach(out::accept);
                    for (String p : ModdedWoods.PLANKS) {
                        ResourceLocation wood = new ResourceLocation(p);
                        if (BuiltInRegistries.BLOCK.containsKey(wood)) out.accept(ModdedBarrelBlock.stack(wood));
                    }
                    out.accept(DOLLY);
                    UPGRADES_ITEMS.forEach(out::accept);
                })
                .build();
        r.accept(SimpleBarrels.id("main"), TAB);
    }

    public static <T> BiConsumer<ResourceLocation, T> direct(Registry<? super T> registry) {
        return (id, value) -> Registry.register(registry, id, value);
    }

    public static void registerAllDirect() {
        registerBlocks(direct(BuiltInRegistries.BLOCK));
        registerItems(direct(BuiltInRegistries.ITEM));
        registerBlockEntities(direct(BuiltInRegistries.BLOCK_ENTITY_TYPE));
        registerRecipeSerializers(direct(BuiltInRegistries.RECIPE_SERIALIZER));
        registerCreativeTabs(direct(BuiltInRegistries.CREATIVE_MODE_TAB));
    }
}
