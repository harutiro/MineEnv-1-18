package com.example.examplemod.mc_09_redstone;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import org.lwjgl.system.CallbackI;

import java.util.Random;

public class BlockRedstoneClock extends Block {

    public static final BooleanProperty REDSTONE_CLOCK = BooleanProperty.create("redstone_clock");

    public BlockRedstoneClock() {
        super(Block.Properties.of(Material.STONE).strength(10f));
        this.registerDefaultState(this.stateDefinition.any().setValue(REDSTONE_CLOCK, true));
    }

    //パワーを出力できる武録かどうか設定をする。
    @Override
    public boolean isSignalSource(BlockState pState) {
        return true;
    }

    // 出力するパワーの強さを設定する。


    @Override
    public int getSignal(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pDirection) {
        boolean flag = pState.getValue(REDSTONE_CLOCK);
        if (flag) {
            return 15;
        } else {
            return 0;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(REDSTONE_CLOCK);
    }

    //ブロックが設置されたときに呼ばれるメソッド
    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
        //30tick後にtickメソッドを呼び出す
        pLevel.scheduleTick(pPos, this, 30);
    }

    //1.5秒ごとに呼ばれるメソッド
    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        boolean flag = pLevel.hasNeighborSignal(pPos);
        if (flag) {
            pLevel.setBlock(pPos, pState.setValue(REDSTONE_CLOCK, false), 3);
        } else {
            pLevel.setBlock(pPos, pState.setValue(REDSTONE_CLOCK, true), 3);
        }
        pLevel.scheduleTick(pPos, pLevel.getBlockState(pPos).getBlock(), 30);
    }
}
