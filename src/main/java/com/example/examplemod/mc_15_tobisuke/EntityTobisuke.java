package com.example.examplemod.mc_15_tobisuke;

import com.example.examplemod.ExampleMod;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

// TamalAnimalを継承することで、懐く動物を作ることができる
public class EntityTobisuke extends TamableAnimal {

    private static final EntityDataAccessor<Float> DATA_HEALTH_ID = SynchedEntityData.defineId(EntityTobisuke.class, EntityDataSerializers.FLOAT);

    public EntityTobisuke(EntityType<? extends EntityTobisuke> entityType, Level level) {
        super(entityType, level);
        this.setTame(false);
    }

    // AIの指定
    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                // 動くスピード
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                // 最大体力
                .add(Attributes.MAX_HEALTH, 8.0D)
                // 攻撃力
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    protected void customServerAiStep() {
        this.entityData.set(DATA_HEALTH_ID, this.getHealth());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_HEALTH_ID, this.getHealth());
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mate) {
        EntityTobisuke entityTobisuke = ExampleMod.ENTITY_TOBISUKE.create(level);
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            entityTobisuke.setOwnerUUID(uuid);
            entityTobisuke.setTame(true);
        }
        return entityTobisuke;
    }

    // なつかせるアイテム
    @Override
    public boolean isFood(ItemStack stack) {
        return stack.getItem() == Items.APPLE;
    }

    // でスポーンの条件
    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return !isTame() && tickCount > 2400;
    }

    // プレイヤーにクリックされた時の動きを指定する
    @Override
    public InteractionResult mobInteract(Player playerIn, InteractionHand hand) {
        // プレイヤーがなつかせるアイテムを持っているかどうか判定する
        if (!isFood(playerIn.getMainHandItem())) {
            return InteractionResult.PASS;
        }

        if (this.level.isClientSide) {
            return InteractionResult.PASS;
        }

        // プレイヤーになついていないかを判定する
        if (isTame()) {
            this.setOrderedToSit(!this.isOrderedToSit());
        } else {
            if (!playerIn.isCreative()) {
                playerIn.getMainHandItem().setCount(playerIn.getMainHandItem().getCount() - 1);
            }

            // ここでTobisukeがなついた後の行動を決める
            // プレイヤーのあとをついてくるように指定する。
            this.setTame(true);
            this.setOrderedToSit(false);
            this.setHealth(20.0F);
            this.setOwnerUUID(playerIn.getUUID());
            this.spawnTamingParticles(true);
            this.level.broadcastEntityEvent(this, (byte) 7);

        }

        return InteractionResult.SUCCESS;
    }
}
