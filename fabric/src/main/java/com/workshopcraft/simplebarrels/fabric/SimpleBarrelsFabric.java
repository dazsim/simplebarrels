package com.workshopcraft.simplebarrels.fabric;

import com.workshopcraft.simplebarrels.block.FluidBarrelBlock;
import com.workshopcraft.simplebarrels.block.FluidBarrelBlockEntity;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorageUtil;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SimpleBarrelsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ModContent.registerAllDirect();
        ItemStorage.SIDED.registerForBlockEntity((be, side) -> BarrelStorage.of(be), ModContent.BARREL_BE);
        FluidStorage.SIDED.registerForBlockEntity((be, side) -> FluidBarrelStorage.of(be), ModContent.FLUID_BARREL_BE);
        FluidBarrelBlock.containers = new FluidBarrelBlock.Containers() {
            @Override
            public boolean isContainer(ItemStack stack) {
                return !stack.isEmpty() && ContainerItemContext.withConstant(stack).find(FluidStorage.ITEM) != null;
            }

            @Override
            public boolean interact(Level level, BlockPos pos, Player player, InteractionHand hand) {
                return level.getBlockEntity(pos) instanceof FluidBarrelBlockEntity be
                        && FluidStorageUtil.interactWithFluidStorage(FluidBarrelStorage.of(be), player, hand);
            }
        };
    }
}
