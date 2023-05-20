package com.example.examplemod.mc_11_footprints_sand;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;

import java.util.Random;

public class BlockFootprintsSand extends Block {

    // テクスチャだけ違うといったブロックを作る時は、ブロックのステータスを変えることによって区別をする
    private static final IntegerProperty COLOR = IntegerProperty.create("color", 0, 4);

    public BlockFootprintsSand() {
        super(Block.Properties.of(Material.STONE).strength(10f));
        this.registerDefaultState(this.stateDefinition.any().setValue(COLOR, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(COLOR);
    }

    // プレイヤーが上に乗った時に呼ばれる
    //    ブロックを触れた時に4に変更する
    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        super.stepOn(pLevel, pPos, pState, pEntity);

        // プレイヤーの時だけ処理をする
        if (!(pEntity instanceof Player)) {
            return;
        }

        //指定した座標の位置情報を取得するコード
        BlockState blockColorState = pLevel.getBlockState(pPos);

        //指定した座標の一ブロックのBlockStateを取得することができるコード
        // 4じゃない時に4にする
        if (blockColorState.getValue(COLOR) != 4) {
            // ブロックを触れた時に4に変更する
            pLevel.setBlockAndUpdate(pPos, blockColorState.setValue(COLOR, 4));
            // 5tick後に呼び出す
            pLevel.scheduleTick(pPos, this, 5);
        }
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        super.tick(pState, pLevel, pPos, pRandom);

        // ブロックの色を変更する
        int blockColorState = pState.getValue(COLOR);
        blockColorState--;

        pLevel.setBlockAndUpdate(pPos, pState.setValue(COLOR, blockColorState));

        if (blockColorState != 0) {
            // 5tick後に呼び出す
            // 0になるまで繰り返す
            pLevel.scheduleTick(pPos, this, 5);
        }

    }
}
