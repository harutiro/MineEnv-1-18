package com.example.examplemod.mc_04_hipotion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ItemHiPotion extends Item {
    public ItemHiPotion() {
        super(new Item.Properties().food(new FoodProperties.Builder()
                .nutrition(1) // 空有服の回復量
                .saturationMod(0.5f) // 空腹になるまでの時間
                .alwaysEat() // 満腹の時でも食べれるようにする
                .build()
        ).tab(CreativeModeTab.TAB_FOOD));
    }

    //飲むアニメーションを設定
    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    // アイテムを光らせる方法
    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    // 食べた時の処理
    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity player) {
        MobEffect effect = MobEffects.MOVEMENT_SPEED; // 速度上昇
        int duration = 200; //継続時間
        int positionLevel = 0; // レベル
        int size = pStack.getCount(); // アイテムの個数


        if (size >= 20) {
            effect = MobEffects.POISON; // 毒
            duration = 600; //継続時間
            positionLevel = 2; // レベル
        } else if (size >= 15) {
            effect = MobEffects.REGENERATION; // 回復
            duration = 1200; //継続時間
            positionLevel = 1; // レベル
        } else if (size >= 10) {
            duration = 600; //継続時間
            positionLevel = 1; // レベル
        }

        // エフェクトを付与する
        player.addEffect(new MobEffectInstance(effect, duration, positionLevel));

        return super.finishUsingItem(pStack, pLevel, player);
    }
}
