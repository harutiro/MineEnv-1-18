package com.example.examplemod.mc_05_mysword;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;

public class ItemMySword extends SwordItem {
    public ItemMySword() {
        super(Tiers.IRON,
                3,
                -2.4f,
                (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)
        );
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player) {
            // スロー効果を与える
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1200, 0));

            // 燃やす
            target.setRemainingFireTicks(1200);

            // 上に打ち上げる
            target.setPos(target.position().x, target.position().y + 10, target.position().z);

            // 切った周りにガラスを貼る
            // 周りに貼ってあげる処理をしてあげよう。
            BlockPos pos = new BlockPos(target.getX(), target.getY(), target.getZ());
            target.level.setBlockAndUpdate(pos, Blocks.GLASS.defaultBlockState());
        }

        return super.hurtEnemy(stack, target, attacker);
    }
}
