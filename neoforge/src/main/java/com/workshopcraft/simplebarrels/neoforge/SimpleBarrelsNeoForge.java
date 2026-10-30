package com.workshopcraft.simplebarrels.neoforge;

import com.workshopcraft.simplebarrels.SimpleBarrels;
import com.workshopcraft.simplebarrels.block.FluidBarrelBlock;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.fluid.FluidUtil;

@Mod(SimpleBarrels.MOD_ID)
public class SimpleBarrelsNeoForge {
    public SimpleBarrelsNeoForge(IEventBus modBus) {
        modBus.addListener(SimpleBarrelsNeoForge::onRegister);
        modBus.addListener(SimpleBarrelsNeoForge::onCapabilities);
        FluidBarrelBlock.containers = new FluidBarrelBlock.Containers() {
            @Override
            public boolean isContainer(ItemStack stack) {
                return !stack.isEmpty() && ItemAccess.forStack(stack).getCapability(Capabilities.Fluid.ITEM) != null;
            }

            @Override
            public boolean interact(Level level, BlockPos pos, Player player, InteractionHand hand) {
                return FluidUtil.interactWithFluidHandler(player, hand, level, pos, null, null);
            }
        };
    }

    private static void onRegister(RegisterEvent e) {
        e.register(Registries.DATA_COMPONENT_TYPE, h -> ModContent.registerDataComponents(h::register));
        e.register(Registries.BLOCK, h -> ModContent.registerBlocks(h::register));
        e.register(Registries.ITEM, h -> ModContent.registerItems(h::register));
        e.register(Registries.BLOCK_ENTITY_TYPE, h -> ModContent.registerBlockEntities(h::register));
        e.register(Registries.CREATIVE_MODE_TAB, h -> ModContent.registerCreativeTabs(h::register));
    }

    private static void onCapabilities(RegisterCapabilitiesEvent e) {
        e.registerBlockEntity(Capabilities.Item.BLOCK, ModContent.BARREL_BE, (be, side) -> BarrelItemHandler.of(be));
        e.registerBlockEntity(Capabilities.Fluid.BLOCK, ModContent.FLUID_BARREL_BE, (be, side) -> FluidBarrelHandler.of(be));
    }
}
