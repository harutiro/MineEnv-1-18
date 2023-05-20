package com.example.examplemod.test;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class ItemTestSword extends SwordItem {
    public ItemTestSword() {
        super(
                Tiers.NETHERITE,
                10,
                10f,
                new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)
        );
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {

        LargeFireball largefireball = new LargeFireball(
                entity.level,
                entity,
                entity.getViewVector(1).x,
                entity.getViewVector(1).y,
                entity.getViewVector(1).z,
                10
        );

        largefireball.setPos(
                entity.position().x,
                entity.position().y,
                entity.position().z
        );

        entity.level.addFreshEntity(largefireball);

        System.out.println("ItemTestSword.onEntitySwing");

        return super.onEntitySwing(stack, entity);
    }

}
