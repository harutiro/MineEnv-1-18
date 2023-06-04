package com.example.examplemod.ramen_mod.block;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.ramen_mod.item.ItemDryNoodle;
import com.example.examplemod.ramen_mod.item.ItemNoodle;
import com.example.examplemod.ramen_mod.item.ItemWaterNoodle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jline.utils.Log;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class BlockSundo extends Block {

    public static final BooleanProperty WATER = BooleanProperty.create("water");

    public BlockSundo() {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(10f));

        // ブロックの状態を設定する
        this.registerDefaultState(this.stateDefinition.any().setValue(WATER, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        // ブロックの状態を定義する
        builder.add(WATER);
    }

    @Override
    public void attack(BlockState state, Level level, BlockPos pos, Player player) {


    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        boolean water = pState.getValue(WATER); // 今の色を取得
        ItemStack heldItem = pPlayer.getItemInHand(pHand);

        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            if (heldItem.getItem() == Items.WATER_BUCKET) {
                // 水バケツを持っている場合はブロックの状態を切り替える
                pLevel.setBlockAndUpdate(pPos, pState.setValue(WATER, !water));

                // 空バケツに変更する
                if (!pPlayer.isCreative()) {
                    pPlayer.setItemInHand(pHand, new ItemStack(Items.BUCKET));
                }

                return InteractionResult.CONSUME;
            } else if (heldItem.getItem() == Items.BUCKET) {
                // 水バケツを持っている場合はブロックの状態を切り替える
                pLevel.setBlockAndUpdate(pPos, pState.setValue(WATER, !water));

                // 空バケツに変更する
                if (!pPlayer.isCreative()) {
                    pPlayer.setItemInHand(pHand, new ItemStack(Items.WATER_BUCKET));
                }

                return InteractionResult.CONSUME;
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    // プレイヤーが上に乗った時に呼ばれる
    //    ブロックを触れた時に4に変更する
    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        super.stepOn(pLevel, pPos, pState, pEntity);

        // プレイヤーの時だけ処理をする
        if (pEntity instanceof ItemEntity) {
            ItemEntity itemEntity = (ItemEntity) pEntity;
            ItemStack itemStack = itemEntity.getItem();
            Item item = itemStack.getItem();

            if (item instanceof ItemDryNoodle && pState.getValue(WATER)) {
                // アイテムがItemNoodleでなおかつ、水が張っている時のみ処理をする
                // ここに処理を追加してください

                System.out.println("ItemNoodle");

                // アイテムがItemDryNoodleである場合の処理
                if (!pLevel.isClientSide) {
                    // サーバーサイドのみで実行する

                    // 30tick後に土ブロックにEntityを変えるタスクをスケジュールする
                    pLevel.scheduleTick(pPos, pState.getBlock(), 30);

                    // ここで、置かれたアイテムを消去する。
                    itemEntity.remove(Entity.RemovalReason.DISCARDED); // アイテムを削除する
                }

            }
        }
    }

    //1.5秒ごとに呼ばれるメソッド
    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        // 30tick後に実行される処理
        if (!pLevel.isClientSide) {
            // サーバーサイドのみで実行する

            // 土ブロックのEntityを生成する
            ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, pLevel);
            itemEntity.setItem(new ItemStack(ExampleMod.ITEM_WATER_NOODLE));
            itemEntity.setPos(pPos.getX() + 0.5, pPos.getY() + 1.0, pPos.getZ() + 0.5);
            pLevel.addFreshEntity(itemEntity);

            // 経験値回収の音を再生する
            pLevel.playSound(null, pPos, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.RECORDS, 1.0f, 1.0f);
        }
    }
}
