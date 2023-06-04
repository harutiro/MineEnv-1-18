package com.example.examplemod.ramen_mod.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ItemDryNoodle extends Item {
    public ItemDryNoodle() {
        super(new Item.Properties().food(new FoodProperties.Builder()
                .fast() // すごいスピードで食べれる。
                .nutrition(1) // 空有服の回復量
                .saturationMod(0.01f) // 空腹になるまでの時間
                .alwaysEat() // 満腹の時でも食べれるようにする
                .build()
        ).tab(CreativeModeTab.TAB_FOOD));
    }

    //飲むアニメーションを設定
    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.EAT;
    }

    // 食べた時の処理
    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity player) {
        return super.finishUsingItem(pStack, pLevel, player);
    }
}
