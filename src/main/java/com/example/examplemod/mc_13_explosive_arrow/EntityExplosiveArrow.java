package com.example.examplemod.mc_13_explosive_arrow;

import com.example.examplemod.ExampleMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class EntityExplosiveArrow extends AbstractArrow {

    public EntityExplosiveArrow(EntityType<? extends EntityExplosiveArrow> type, Level level) {
        super(type, level);
    }

    public EntityExplosiveArrow(Level level, LivingEntity entity) {
        super(ExampleMod.ENTITY_EXPLOSIVE_ARROW, entity, level);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Items.ARROW);
    }

    // 矢がEntityに当たった時のコード
    @Override
    protected void doPostHurtEffects(LivingEntity livingEntity) {
        if (!level.isClientSide) {
            // 爆発の半径
            float explosionRadius = 2;
            // 爆発を起こす座標や爆発の範囲を定義する
            level.explode(this, this.getX(), this.getY(), this.getZ(), explosionRadius, Explosion.BlockInteraction.DESTROY);
            //Entityまたはブロックを破壊するように定義する
            this.remove(RemovalReason.KILLED);
        }
    }

    // 矢がブロックに当たった時のコード
    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        if (!level.isClientSide) {
            float explosionRadius = 2;
            level.explode(this, this.getX(), this.getY(), this.getZ(), explosionRadius, Explosion.BlockInteraction.DESTROY);
            this.remove(RemovalReason.KILLED);
        }
    }


}
