package com.example.examplemod.ramen_mod.block;

import com.example.examplemod.ramen_mod.item.ItemNoodle;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jline.utils.Log;

import java.util.Objects;
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

        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            // 色を変更して設定
            pLevel.setBlockAndUpdate(pPos, pState.setValue(WATER, !water));

            return InteractionResult.CONSUME;
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

            if (item instanceof ItemNoodle) {
                // アイテムがItemNoodleである場合の処理
                // ここに処理を追加してください

                System.out.println("ItemNoodle");

            }
        }
    }
}
