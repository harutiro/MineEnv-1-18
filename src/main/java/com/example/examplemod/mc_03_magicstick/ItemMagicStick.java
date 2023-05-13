package com.example.examplemod.mc_03_magicstick;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemMagicStick extends Item {

    public ItemMagicStick() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));

    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        int time = 5;
        // 5秒間燃やす
        pTarget.setSecondsOnFire(time);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}
