package com.workshopcraft.simplebarrels.item;

import com.workshopcraft.simplebarrels.block.BarrelBlock;
import com.workshopcraft.simplebarrels.block.BarrelBlockEntity;
import com.workshopcraft.simplebarrels.block.ModdedBarrelBlock;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.List;

public class DollyItem extends Item {
    public DollyItem(Properties props) {
        super(props);
    }

    public static boolean canCarry(Block b) {
        return b instanceof BarrelBlock || b == Blocks.CHEST || b == Blocks.TRAPPED_CHEST || b == Blocks.ENDER_CHEST;
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level level = ctx.getLevel();
        ItemStack dolly = ctx.getItemInHand();
        CarriedBlock carried = dolly.get(ModContent.CARRIED_BLOCK);
        if (carried == null) {
            if (ctx.getPlayer() == null || !ctx.getPlayer().isShiftKeyDown()) return InteractionResult.PASS;
            BlockPos pos = ctx.getClickedPos();
            BlockState state = level.getBlockState(pos);
            if (!canCarry(state.getBlock())) return InteractionResult.PASS;
            if (level.isClientSide) return InteractionResult.SUCCESS;
            BlockEntity be = level.getBlockEntity(pos);
            CompoundTag data = new CompoundTag();
            if (be != null && state.getBlock() != Blocks.ENDER_CHEST) {
                data = be.saveWithoutMetadata(level.registryAccess());
                // empty it first so nothing spills when the block goes
                if (be instanceof BarrelBlockEntity barrel) barrel.setContentsRaw(ItemStack.EMPTY, 0);
                else if (be instanceof Container c) c.clearContent();
            }
            dolly.set(ModContent.CARRIED_BLOCK, new CarriedBlock(state.getBlock(), data));
            level.removeBlock(pos, false);
            return InteractionResult.CONSUME;
        }
        BlockPlaceContext place = new BlockPlaceContext(ctx);
        if (!place.canPlace()) return InteractionResult.FAIL;
        BlockPos pos = place.getClickedPos();
        BlockState state = carried.block().getStateForPlacement(place);
        if (state == null || !state.canSurvive(level, pos)) return InteractionResult.FAIL;
        if (level.isClientSide) return InteractionResult.SUCCESS;
        if (!level.setBlock(pos, state, Block.UPDATE_ALL)) return InteractionResult.FAIL;
        BlockEntity be = level.getBlockEntity(pos);
        if (be != null && !carried.data().isEmpty()) {
            be.loadWithComponents(carried.data(), level.registryAccess());
            be.setChanged();
            if (be instanceof BarrelBlockEntity barrel) barrel.changed();
        }
        level.gameEvent(ctx.getPlayer(), GameEvent.BLOCK_PLACE, pos);
        dolly.remove(ModContent.CARRIED_BLOCK);
        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> lines, TooltipFlag flag) {
        CarriedBlock c = stack.get(ModContent.CARRIED_BLOCK);
        if (c == null) return;
        lines.add(c.block() == ModContent.MODDED_BARREL ? ModdedBarrelBlock.name(ResourceLocation.tryParse(c.data().getString("Wood"))) : c.block().getName());
        CompoundTag d = c.data();
        if (d.contains("Item") && ctx.registries() != null) {
            ItemStack item = ItemStack.parseOptional(ctx.registries(), d.getCompound("Item"));
            if (!item.isEmpty()) lines.add(Component.literal(d.getInt("Count") + " x ").append(item.getHoverName()));
        }
    }

}
