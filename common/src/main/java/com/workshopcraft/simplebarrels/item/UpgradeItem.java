package com.workshopcraft.simplebarrels.item;

import com.workshopcraft.simplebarrels.block.BarrelBlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class UpgradeItem extends Item {
    public enum Kind { COMPARATOR, FRAME, MK1, MK2, MK3, MK4 }

    private final Kind kind;

    public UpgradeItem(Kind kind, Properties props) {
        super(props);
        this.kind = kind;
    }

    public Kind kind() { return kind; }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        if (ctx.getPlayer() == null || !ctx.getPlayer().isShiftKeyDown()) return InteractionResult.PASS;
        if (!(ctx.getLevel().getBlockEntity(ctx.getClickedPos()) instanceof BarrelBlockEntity barrel)) return InteractionResult.PASS;
        if (ctx.getLevel().isClientSide) return InteractionResult.SUCCESS;
        if (!barrel.applyUpgrade(kind)) return InteractionResult.FAIL;
        if (!ctx.getPlayer().getAbilities().instabuild) ctx.getItemInHand().shrink(1);
        return InteractionResult.CONSUME;
    }
}
