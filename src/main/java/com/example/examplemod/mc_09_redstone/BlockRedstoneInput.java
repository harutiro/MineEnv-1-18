package com.example.examplemod.mc_09_redstone;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BlockRedstoneInput extends Block {

    public static final IntegerProperty POWER = IntegerProperty.create("power", 0, 4);

    public BlockRedstoneInput() {
        super(Block.Properties.of(Material.STONE).strength(10f));
        this.registerDefaultState(this.stateDefinition.any().setValue(POWER, 0));
    }

    @Override
    public void onProjectileHit(Level pLevel, BlockState pState, BlockHitResult pHit, Projectile pProjectile) {
        pLevel.scheduleTick(pHit.getBlockPos(), pState.getBlock(), 20);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(POWER);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        int power = pContext.getLevel().getBestNeighborSignal(pContext.getClickedPos());
        return this.defaultBlockState().setValue(POWER, power / 4);
    }

    // 周辺で何か変化があった時に呼び出されて実行されるプログラム
    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (pLevel.isClientSide) {
            return;
        }

        // 0か0じゃないかで変わる
        if (pLevel.hasNeighborSignal(pPos)) {
            int power = pLevel.getBestNeighborSignal(pPos);
            // 入力のレベルに応じてパワーの値を変化させる。ここの値でテクスチャを変化させる。
            pLevel.setBlockAndUpdate(pPos, pState.setValue(POWER, power / 4));
            pLevel.scheduleTick(pPos, this, 4);
            System.out.println("Start Input");
            System.out.println("power = " + power);
        } else {
            System.out.println("Stop Input");
        }
    }
}
