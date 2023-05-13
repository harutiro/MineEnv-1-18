package com.example.examplemod.mc_03_magicstick;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemMagicStick extends Item {

    public ItemMagicStick() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));

    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
//        int time = 5;
//        // 5秒間燃やす
//        pTarget.setSecondsOnFire(time);
        //pTarget これは叩いた相手

        // エンティティを取得する
        Level level = pTarget.level;

        // エンティティによって生成するものを変える
        LivingEntity entity;
        if (pTarget instanceof Villager) {
            entity = new Zombie(level);
        } else {
            entity = new Pig(EntityType.PIG, level);
        }

        // エンティティの位置を設定する
        BlockPos spawnPos = pTarget.blockPosition();
        entity.setPos(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());

        if (!pTarget.level.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) pTarget.level;
            // エンティティをスポーンさせる
            serverLevel.tryAddFreshEntityWithPassengers(entity);
            // エンティティを削除する
            serverLevel.removeEntity(pTarget);

        }

        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}
