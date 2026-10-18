package com.workshopcraft.simplebarrels.neoforge;

import com.workshopcraft.simplebarrels.SimpleBarrels;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(SimpleBarrels.MOD_ID)
public class SimpleBarrelsNeoForge {
    public SimpleBarrelsNeoForge(IEventBus modBus) {
        modBus.addListener(SimpleBarrelsNeoForge::onRegister);
        modBus.addListener(SimpleBarrelsNeoForge::onCapabilities);
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
    }
}
