package com.workshopcraft.simplebarrels.registry;

import com.workshopcraft.simplebarrels.SimpleBarrels;
import com.workshopcraft.simplebarrels.block.BarrelBlock;
import com.workshopcraft.simplebarrels.block.BarrelBlockEntity;
import com.workshopcraft.simplebarrels.block.FluidBarrelBlock;
import com.workshopcraft.simplebarrels.block.FluidBarrelBlockEntity;
import com.workshopcraft.simplebarrels.block.ModdedBarrelBlock;
import com.workshopcraft.simplebarrels.item.BarrelUpgrades;
import com.workshopcraft.simplebarrels.item.CarriedBlock;
import com.workshopcraft.simplebarrels.item.DollyItem;
import com.workshopcraft.simplebarrels.item.FluidBarrelItem;
import com.workshopcraft.simplebarrels.item.FluidContents;
import com.workshopcraft.simplebarrels.item.ModdedBarrelItem;
import com.workshopcraft.simplebarrels.item.UpgradeItem;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public final class ModContent {
    public static final Map<BarrelWood, BarrelBlock> BARRELS = new EnumMap<>(BarrelWood.class);
    public static ModdedBarrelBlock MODDED_BARREL;
    public static ModdedBarrelItem MODDED_BARREL_ITEM;
    public static BlockEntityType<BarrelBlockEntity> BARREL_BE;
    public static final Map<FluidTier, FluidBarrelBlock> FLUID_BARRELS = new EnumMap<>(FluidTier.class);
    public static BlockEntityType<FluidBarrelBlockEntity> FLUID_BARREL_BE;
    public static DataComponentType<FluidContents> FLUID;
    public static CreativeModeTab TAB;
    public static DollyItem DOLLY;
    public static final List<Item> UPGRADES_ITEMS = new ArrayList<>();
    public static DataComponentType<BarrelUpgrades> UPGRADES;
    public static DataComponentType<CarriedBlock> CARRIED_BLOCK;
    public static DataComponentType<Identifier> WOOD;

    // only there when Simple Machines is
    private static final Identifier STEEL_INGOT = Identifier.fromNamespaceAndPath("simplemachines", "steel_ingot");

    private ModContent() {}

    public static void registerBlocks(BiConsumer<Identifier, Block> r) {
        for (BarrelWood wood : BarrelWood.values()) {
            Identifier id = SimpleBarrels.id(wood.blockId());
            BarrelBlock block = new BarrelBlock(props(wood, id));
            BARRELS.put(wood, block);
            r.accept(id, block);
        }
        Identifier id = SimpleBarrels.id("modded_barrel");
        MODDED_BARREL = new ModdedBarrelBlock(props(BarrelWood.OAK, id));
        r.accept(id, MODDED_BARREL);
        for (FluidTier tier : FluidTier.values()) {
            Identifier fid = SimpleBarrels.id(tier.blockId());
            FluidBarrelBlock block = new FluidBarrelBlock(tier, BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, fid))
                    .mapColor(tier.mapColor)
                    .sound(tier.sound)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .strength(3.0F, 6.0F)
                    .pushReaction(PushReaction.BLOCK)
                    .noOcclusion());
            FLUID_BARRELS.put(tier, block);
            r.accept(fid, block);
        }
    }

    private static BlockBehaviour.Properties props(BarrelWood wood, Identifier id) {
        return BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(Registries.BLOCK, id))
                .mapColor(wood.mapColor)
                .sound(wood.sound)
                .strength(2.5F)
                .ignitedByLava()
                .pushReaction(PushReaction.BLOCK)
                .noOcclusion();
    }

    private static Item.Properties item(Identifier id) {
        return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id));
    }

    public static void registerItems(BiConsumer<Identifier, Item> r) {
        BARRELS.forEach((wood, block) -> {
            Identifier id = SimpleBarrels.id(wood.blockId());
            r.accept(id, new BlockItem(block, item(id).useBlockDescriptionPrefix()));
        });
        Identifier modded = SimpleBarrels.id("modded_barrel");
        MODDED_BARREL_ITEM = new ModdedBarrelItem(MODDED_BARREL, item(modded).useBlockDescriptionPrefix());
        r.accept(modded, MODDED_BARREL_ITEM);
        FLUID_BARRELS.forEach((tier, block) -> {
            Identifier id = SimpleBarrels.id(tier.blockId());
            r.accept(id, new FluidBarrelItem(block, item(id).useBlockDescriptionPrefix()));
        });
        Identifier dolly = SimpleBarrels.id("dolly");
        DOLLY = new DollyItem(item(dolly).stacksTo(1));
        r.accept(dolly, DOLLY);
        String[] names = {"counter", "item_frame_upgrade", "mark1_compressor", "mark2_compressor", "mark3_compressor", "mark4_compressor"};
        for (UpgradeItem.Kind k : UpgradeItem.Kind.values()) {
            Identifier id = SimpleBarrels.id(names[k.ordinal()]);
            UpgradeItem upgrade = new UpgradeItem(k, item(id));
            UPGRADES_ITEMS.add(upgrade);
            r.accept(id, upgrade);
        }
    }

    public static void registerDataComponents(BiConsumer<Identifier, DataComponentType<?>> r) {
        UPGRADES = DataComponentType.<BarrelUpgrades>builder().persistent(BarrelUpgrades.CODEC).networkSynchronized(BarrelUpgrades.STREAM_CODEC).build();
        CARRIED_BLOCK = DataComponentType.<CarriedBlock>builder().persistent(CarriedBlock.CODEC).networkSynchronized(CarriedBlock.STREAM_CODEC).build();
        WOOD = DataComponentType.<Identifier>builder().persistent(Identifier.CODEC).networkSynchronized(Identifier.STREAM_CODEC).build();
        r.accept(SimpleBarrels.id("upgrades"), UPGRADES);
        r.accept(SimpleBarrels.id("carried_block"), CARRIED_BLOCK);
        r.accept(SimpleBarrels.id("wood"), WOOD);
        FLUID = DataComponentType.<FluidContents>builder().persistent(FluidContents.CODEC).networkSynchronized(FluidContents.STREAM_CODEC).build();
        r.accept(SimpleBarrels.id("fluid"), FLUID);
    }

    public static void registerBlockEntities(BiConsumer<Identifier, BlockEntityType<?>> r) {
        Set<Block> blocks = new HashSet<>(BARRELS.values());
        blocks.add(MODDED_BARREL);
        BARREL_BE = new BlockEntityType<>(BarrelBlockEntity::new, blocks);
        r.accept(SimpleBarrels.id("barrel"), BARREL_BE);
        FLUID_BARREL_BE = new BlockEntityType<>(FluidBarrelBlockEntity::new, new HashSet<>(FLUID_BARRELS.values()));
        r.accept(SimpleBarrels.id("fluid_barrel"), FLUID_BARREL_BE);
    }

    public static void registerCreativeTabs(BiConsumer<Identifier, CreativeModeTab> r) {
        TAB = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                .title(Component.translatable("itemGroup." + SimpleBarrels.MOD_ID))
                .icon(() -> new ItemStack(BARRELS.get(BarrelWood.OAK)))
                .displayItems((params, out) -> {
                    BARRELS.values().forEach(out::accept);
                    for (String p : ModdedWoods.PLANKS) {
                        Identifier wood = Identifier.parse(p);
                        if (BuiltInRegistries.BLOCK.containsKey(wood)) out.accept(ModdedBarrelBlock.stack(wood));
                    }
                    out.accept(FLUID_BARRELS.get(FluidTier.COPPER));
                    out.accept(FLUID_BARRELS.get(FluidTier.IRON));
                    if (BuiltInRegistries.ITEM.containsKey(STEEL_INGOT)) out.accept(FLUID_BARRELS.get(FluidTier.STEEL));
                    out.accept(DOLLY);
                    UPGRADES_ITEMS.forEach(out::accept);
                })
                .build();
        r.accept(SimpleBarrels.id("main"), TAB);
    }

    public static <T> BiConsumer<Identifier, T> direct(Registry<? super T> registry) {
        return (id, value) -> Registry.register(registry, id, value);
    }

    public static void registerAllDirect() {
        registerDataComponents(direct(BuiltInRegistries.DATA_COMPONENT_TYPE));
        registerBlocks(direct(BuiltInRegistries.BLOCK));
        registerItems(direct(BuiltInRegistries.ITEM));
        registerBlockEntities(direct(BuiltInRegistries.BLOCK_ENTITY_TYPE));
        registerCreativeTabs(direct(BuiltInRegistries.CREATIVE_MODE_TAB));
    }
}
