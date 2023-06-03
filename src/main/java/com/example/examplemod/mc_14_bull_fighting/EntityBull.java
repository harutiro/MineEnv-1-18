package com.example.examplemod.mc_14_bull_fighting;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

//AIの作成
public class EntityBull extends Animal {

    public EntityBull(EntityType<? extends Animal> entityTypeIn, Level level) {
        super(entityTypeIn, level);
    }

    @Override
    protected void registerGoals() {
        // 水に入った時に浮かぶ
        this.goalSelector.addGoal(0, new FloatGoal(this));
        // 攻撃する
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0d, false));
        // プレイヤーを見つける
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0f));
        // 水を避ける
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0d));
        // 周りを見渡す
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        // 攻撃対象の設定
        this.goalSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    // 能力の設定
    public static AttributeSupplier.Builder registerAttributes() {
        return Animal.createMobAttributes()
                // 動く速度
                .add(Attributes.MOVEMENT_SPEED, 0.2f)
                // 追いかける範囲
                .add(Attributes.FOLLOW_RANGE, 35.0d)
                // 最大HP
                .add(Attributes.MAX_HEALTH, 20.0d)
                // 防御力
                .add(Attributes.ARMOR, 2.0d)
                // 攻撃力
                .add(Attributes.ATTACK_DAMAGE, 2.0d);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.getTarget() == null) {
            // プレイヤーがいなかったら速さは0.2
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2f);
        } else {
            // プレイヤーがいたら速さは0.6
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.6f);
        }
    }

    // 子供を産まないように設定
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        return null;
    }

    // どの音を流すか
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.COW_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamegeSource) {
        return SoundEvents.COW_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.COW_DEATH;
    }

    // 足音の設定
    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.COW_STEP, 0.15f, 1.0f);
    }

    // 音量の設定
    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }

    // 目の高さの設定
    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return this.isBaby() ? sizeIn.height * 0.95f : sizeIn.height * 0.92f;
    }
}
