package com.workshopcraft.simplebarrels.item;

import com.workshopcraft.simplebarrels.block.BarrelBlock;
import com.workshopcraft.simplebarrels.block.BarrelBlockEntity;
import com.workshopcraft.simplebarrels.block.ModdedBarrelBlock;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
    private static final String TAG = "CarriedBlock";

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
        CompoundTag carried = dolly.getTagElement(TAG);
        if (carried == null) {
            if (ctx.getPlayer() == null || !ctx.getPlayer().isShiftKeyDown()) return InteractionResult.PASS;
            BlockPos pos = ctx.getClickedPos();
            BlockState state = level.getBlockState(pos);
            if (!canCarry(state.getBlock())) return InteractionResult.PASS;
            if (level.isClientSide) return InteractionResult.SUCCESS;
            BlockEntity be = level.getBlockEntity(pos);
            CompoundTag data = new CompoundTag();
            if (be != null && state.getBlock() != Blocks.ENDER_CHEST) {
                data = be.saveWithoutMetadata();
                // empty it first so nothing spills when the block goes
                if (be instanceof BarrelBlockEntity barrel) barrel.setContentsRaw(ItemStack.EMPTY, 0);
                else if (be instanceof Container c) c.clearContent();
            }
            CompoundTag tag = new CompoundTag();
            tag.putString("Block", BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString());
            tag.put("Data", data);
            dolly.getOrCreateTag().put(TAG, tag);
            level.removeBlock(pos, false);
            return InteractionResult.CONSUME;
        }
        Block block = blockOf(carried);
        if (block == Blocks.AIR) return InteractionResult.FAIL;
        CompoundTag data = carried.getCompound("Data");
        BlockPlaceContext place = new BlockPlaceContext(ctx);
        if (!place.canPlace()) return InteractionResult.FAIL;
        BlockPos pos = place.getClickedPos();
        BlockState state = block.getStateForPlacement(place);
        if (state == null || !state.canSurvive(level, pos)) return InteractionResult.FAIL;
        if (level.isClientSide) return InteractionResult.SUCCESS;
        if (!level.setBlock(pos, state, Block.UPDATE_ALL)) return InteractionResult.FAIL;
        BlockEntity be = level.getBlockEntity(pos);
        if (be != null && !data.isEmpty()) {
            be.load(data);
            be.setChanged();
            if (be instanceof BarrelBlockEntity barrel) barrel.changed();
        }
        level.gameEvent(ctx.getPlayer(), GameEvent.BLOCK_PLACE, pos);
        dolly.removeTagKey(TAG);
        return InteractionResult.CONSUME;
    }

    private static Block blockOf(CompoundTag carried) {
        return BuiltInRegistries.BLOCK.get(new ResourceLocation(carried.getString("Block")));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> lines, TooltipFlag flag) {
        CompoundTag c = stack.getTagElement(TAG);
        if (c == null) return;
        lines.add(blockOf(c) == ModContent.MODDED_BARREL ? ModdedBarrelBlock.name(ResourceLocation.tryParse(c.getCompound("Data").getString("Wood"))) : blockOf(c).getName());
        CompoundTag d = c.getCompound("Data");
        if (d.contains("Item")) {
            ItemStack item = ItemStack.of(d.getCompound("Item"));
            if (!item.isEmpty()) lines.add(Component.literal(d.getInt("Count") + " x ").append(item.getHoverName()));
        }
    }

}
