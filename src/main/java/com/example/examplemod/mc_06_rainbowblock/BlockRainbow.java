package com.example.examplemod.mc_06_rainbowblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;

public class BlockRainbow extends Block {

    public static final IntegerProperty COLOR = IntegerProperty.create("color", 0, 6);

    public BlockRainbow() {
        // このブロックの性質を設定する
        //最後はブロックの強度
        super(Block.Properties.of(Material.STONE).strength(10f));
        // ブロックの状態を設定する
        this.registerDefaultState(this.stateDefinition.any().setValue(COLOR, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        // ブロックの状態を定義する
        builder.add(COLOR);
    }

    @Override
    public void attack(BlockState state, Level level, BlockPos pos, Player player) {
        int color = state.getValue(COLOR); // 今の色を取得

        // 色を変更
        color++;
        if (color >= 7) {
            color = 0;
        }

        // 色を変更して設定
        level.setBlockAndUpdate(pos, state.setValue(COLOR, color));
    }
}
