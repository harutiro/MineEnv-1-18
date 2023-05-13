package com.example.examplemod.mc_10_snowball_fight;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class EntityMySnowball extends Snowball {

    //Entityに阿多田時のダメージ量
    private static final float DAMAGE = 2.0f;

    public EntityMySnowball(EntityType<? extends Snowball> entityTypeIn, Level level) {
        super(entityTypeIn, level);
    }

    public EntityMySnowball(Level level, Player player) {
        super(level, player);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        if (result.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) result;


            switch (blockHitResult.getDirection()) {
                case EAST, WEST, NORTH, SOUTH -> { // 東西南北の面に当たった場合
                    Block block = level.getBlockState(blockHitResult.getBlockPos()).getBlock();
                    if (block == Blocks.SNOW_BLOCK) {
                        // ブロックを破壊
                        level.setBlockAndUpdate(blockHitResult.getBlockPos(), Blocks.AIR.defaultBlockState());
                    }
                }
            }


        } else if (result.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityRayTraceResult = (EntityHitResult) result;
            entityRayTraceResult.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), DAMAGE);
        }
    }
}
