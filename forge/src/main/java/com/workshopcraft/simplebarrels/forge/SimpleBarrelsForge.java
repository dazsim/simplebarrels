package com.workshopcraft.simplebarrels.forge;

import com.workshopcraft.simplebarrels.SimpleBarrels;
import com.workshopcraft.simplebarrels.block.BarrelBlockEntity;
import com.workshopcraft.simplebarrels.client.BarrelRenderer;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.RegisterEvent;

@Mod(SimpleBarrels.MOD_ID)
public class SimpleBarrelsForge {
    private static final net.minecraft.resources.ResourceLocation CAP_ID = SimpleBarrels.id("barrel_items");

    public SimpleBarrelsForge() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(SimpleBarrelsForge::onRegister);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modBus.addListener((EntityRenderersEvent.RegisterRenderers e) -> e.registerBlockEntityRenderer(ModContent.BARREL_BE, BarrelRenderer::new));
        }
        MinecraftForge.EVENT_BUS.addGenericListener(BlockEntity.class, SimpleBarrelsForge::onAttachCaps);
    }

    private static void onRegister(RegisterEvent e) {
        e.register(Registries.BLOCK, h -> ModContent.registerBlocks(h::register));
        e.register(Registries.ITEM, h -> ModContent.registerItems(h::register));
        e.register(Registries.BLOCK_ENTITY_TYPE, h -> ModContent.registerBlockEntities(h::register));
        e.register(Registries.CREATIVE_MODE_TAB, h -> ModContent.registerCreativeTabs(h::register));
    }

    private static void onAttachCaps(AttachCapabilitiesEvent<BlockEntity> e) {
        if (!(e.getObject() instanceof BarrelBlockEntity be)) return;
        LazyOptional<IItemHandler> handler = LazyOptional.of(() -> BarrelItemHandler.of(be));
        e.addCapability(CAP_ID, new ICapabilityProvider() {
            @Override
            public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
                return ForgeCapabilities.ITEM_HANDLER.orEmpty(cap, handler);
            }
        });
        e.addListener(handler::invalidate);
    }
}
