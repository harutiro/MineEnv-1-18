package com.example.examplemod.ramen_mod.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class ItemRamen extends Item {
    public ItemRamen() {
        super(new Item.Properties().food(new FoodProperties.Builder()
                .nutrition(10) // 空有服の回復量
                .saturationMod(1f) // 空腹になるまでの時間
                .build()
        ).tab(CreativeModeTab.TAB_FOOD));
    }
}
