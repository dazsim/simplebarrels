package com.workshopcraft.simplebarrels.block;

import com.mojang.serialization.MapCodec;
import com.workshopcraft.simplebarrels.item.DollyItem;
import com.workshopcraft.simplebarrels.item.UpgradeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class BarrelBlock extends BaseEntityBlock {
    public static final MapCodec<BarrelBlock> CODEC = simpleCodec(BarrelBlock::new);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public BarrelBlock(Properties props) {
        super(props);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() { return CODEC; }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> b) { b.add(FACING); }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    protected BlockState rotate(BlockState s, Rotation r) { return s.setValue(FACING, r.rotate(s.getValue(FACING))); }

    @Override
    protected BlockState mirror(BlockState s, Mirror m) { return s.rotate(m.getRotation(s.getValue(FACING))); }

    @Override
    protected RenderShape getRenderShape(BlockState state) { return RenderShape.MODEL; }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) { return new BarrelBlockEntity(pos, state); }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack held, BlockState state, Level level, BlockPos pos, Player player,
                                              InteractionHand hand, BlockHitResult hit) {
        if (held.getItem() instanceof DollyItem || held.getItem() instanceof UpgradeItem) return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        if (!(level.getBlockEntity(pos) instanceof BarrelBlockEntity barrel)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if (!barrel.canAccept(held)) {

            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        if (!level.isClientSide) {
            int moved = barrel.insert(held, held.getCount(), false);
            held.shrink(moved);
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected void attack(BlockState state, Level level, BlockPos pos, Player player) {
        if (level.isClientSide || !(level.getBlockEntity(pos) instanceof BarrelBlockEntity barrel) || barrel.isEmpty()) return;
        int amount = player.isShiftKeyDown() ? 1 : barrel.getTemplate().getMaxStackSize();
        ItemStack out = barrel.extract(amount, false);
        if (out.isEmpty()) return;
        Vec3 c = Vec3.atCenterOf(pos);
        Vec3 eye = player.getEyePosition();
        Direction side = Direction.getNearest(eye.x - c.x, eye.y - c.y, eye.z - c.z);
        Vec3 at = c.add(side.getStepX() * 0.75, side.getStepY() * 0.75 - 0.125, side.getStepZ() * 0.75);
        ItemEntity e = new ItemEntity(level, at.x, at.y, at.z, out);
        e.setDeltaMovement(side.getStepX() * 0.05, 0.05, side.getStepZ() * 0.05);
        e.setPickUpDelay(0);
        level.addFreshEntity(e);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof BarrelBlockEntity barrel) {
            while (!barrel.isEmpty()) {
                ItemStack s = barrel.extract(barrel.getTemplate().getMaxStackSize(), false);
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), s);
            }
        }
        super.onRemove(state, level, pos, newState, moved);
    }

}
