package com.workshopcraft.simplebarrels.item;

import com.workshopcraft.simplebarrels.block.BarrelBlockEntity;
import com.workshopcraft.simplebarrels.registry.ModContent;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.List;

public class DollyItem extends Item {
    public DollyItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level level = ctx.getLevel();
        ItemStack dolly = ctx.getItemInHand();
        CarriedBarrel carried = dolly.get(ModContent.CARRIED_BARREL);
        if (carried == null) {
            if (ctx.getPlayer() == null || !ctx.getPlayer().isShiftKeyDown()) return InteractionResult.PASS;
            if (!(level.getBlockEntity(ctx.getClickedPos()) instanceof BarrelBlockEntity barrel)) return InteractionResult.PASS;
            if (level.isClientSide) return InteractionResult.SUCCESS;
            dolly.set(ModContent.CARRIED_BARREL, new CarriedBarrel(barrel.getBlockState().getBlock(),
                    barrel.getTemplate().copy(), barrel.getCount(), barrel.getUpgrades()));
            barrel.setContentsRaw(ItemStack.EMPTY, 0);
            level.removeBlock(ctx.getClickedPos(), false);
            return InteractionResult.CONSUME;
        }
        BlockPlaceContext place = new BlockPlaceContext(ctx);
        if (!place.canPlace()) return InteractionResult.FAIL;
        BlockPos pos = place.getClickedPos();
        BlockState state = carried.block().getStateForPlacement(place);
        if (state == null || !state.canSurvive(level, pos)) return InteractionResult.FAIL;
        if (level.isClientSide) return InteractionResult.SUCCESS;
        if (!level.setBlock(pos, state, Block.UPDATE_ALL)) return InteractionResult.FAIL;
        if (level.getBlockEntity(pos) instanceof BarrelBlockEntity barrel) {
            barrel.setUpgrades(carried.upgrades());
            barrel.setContentsRaw(carried.item(), carried.count());
            barrel.changed();
        }
        level.gameEvent(ctx.getPlayer(), GameEvent.BLOCK_PLACE, pos);
        dolly.remove(ModContent.CARRIED_BARREL);
        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> lines, TooltipFlag flag) {
        CarriedBarrel c = stack.get(ModContent.CARRIED_BARREL);
        if (c == null) return;
        lines.add(c.block().getName());
        if (!c.item().isEmpty()) lines.add(Component.literal(c.count() + " x ").append(c.item().getHoverName()));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.has(ModContent.CARRIED_BARREL);
    }

}
