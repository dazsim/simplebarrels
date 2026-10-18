package com.workshopcraft.simplebarrels.item;

import com.workshopcraft.simplebarrels.block.BarrelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.List;

public class DollyItem extends Item {
    private static final String TAG = "CarriedBarrel";

    public DollyItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level level = ctx.getLevel();
        ItemStack dolly = ctx.getItemInHand();
        CompoundTag carried = dolly.getTagElement(TAG);
        if (carried == null) {
            if (ctx.getPlayer() == null || !ctx.getPlayer().isShiftKeyDown()) return InteractionResult.PASS;
            if (!(level.getBlockEntity(ctx.getClickedPos()) instanceof BarrelBlockEntity barrel)) return InteractionResult.PASS;
            if (level.isClientSide) return InteractionResult.SUCCESS;
            CompoundTag tag = barrel.saveWithoutMetadata();
            tag.putString("Block", BuiltInRegistries.BLOCK.getKey(barrel.getBlockState().getBlock()).toString());
            dolly.getOrCreateTag().put(TAG, tag);
            barrel.setContentsRaw(ItemStack.EMPTY, 0);
            level.removeBlock(ctx.getClickedPos(), false);
            return InteractionResult.CONSUME;
        }
        Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(carried.getString("Block")));
        if (block == Blocks.AIR) return InteractionResult.FAIL;
        BlockPlaceContext place = new BlockPlaceContext(ctx);
        if (!place.canPlace()) return InteractionResult.FAIL;
        BlockPos pos = place.getClickedPos();
        BlockState state = block.getStateForPlacement(place);
        if (state == null || !state.canSurvive(level, pos)) return InteractionResult.FAIL;
        if (level.isClientSide) return InteractionResult.SUCCESS;
        if (!level.setBlock(pos, state, Block.UPDATE_ALL)) return InteractionResult.FAIL;
        if (level.getBlockEntity(pos) instanceof BarrelBlockEntity barrel) {
            barrel.load(carried);
            barrel.changed();
        }
        level.gameEvent(ctx.getPlayer(), GameEvent.BLOCK_PLACE, pos);
        dolly.removeTagKey(TAG);
        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> lines, TooltipFlag flag) {
        CompoundTag c = stack.getTagElement(TAG);
        if (c == null) return;
        lines.add(BuiltInRegistries.BLOCK.get(new ResourceLocation(c.getString("Block"))).getName());
        ItemStack item = ItemStack.of(c.getCompound("Item"));
        if (!item.isEmpty()) lines.add(Component.literal(c.getInt("Count") + " x ").append(item.getHoverName()));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.getTagElement(TAG) != null;
    }
}
