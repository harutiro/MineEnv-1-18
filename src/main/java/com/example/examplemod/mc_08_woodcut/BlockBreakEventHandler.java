package com.example.examplemod.mc_08_woodcut;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BlockBreakEventHandler {
    private static final int MAX_COUNT = 3;
    private static final int MAX_HEIGHT = 30;

    //ブロックを壊そうとした時に呼び出してくれる
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();

        // プレイヤーがいない場合は何もしない
        if (player == null) {
            return;
        }

        // プレイヤーが持っているアイテムが斧でない場合は何もしない
        Item item = player.getMainHandItem().getItem();
        if (item != Items.WOODEN_AXE &&
                item != Items.STONE_AXE &&
                item != Items.IRON_AXE &&
                item != Items.GOLDEN_AXE &&
                item != Items.DIAMOND_AXE
        ) {
            return;
        }

        // 壊そうとするブロックが原木でない場合は何もしない
        Block clickedBlock = event.getState().getBlock();
        if (clickedBlock != Blocks.OAK_LOG
                && clickedBlock != Blocks.SPRUCE_LOG
                && clickedBlock != Blocks.BIRCH_LOG
                && clickedBlock != Blocks.JUNGLE_LOG
                && clickedBlock != Blocks.ACACIA_LOG
                && clickedBlock != Blocks.DARK_OAK_LOG
        ) {
            return;
        }


        breakBlock((Level) event.getWorld(), event.getPos());


        // 今やろうとしている処理をやらないようにする
        // ブロックを壊さないようにする
        event.setCanceled(true);
    }

    private void breakBlock(Level level, BlockPos pos) {
        for (int y = 0; y < MAX_HEIGHT; y++) {
            for (int x = -MAX_COUNT; x <= MAX_COUNT; x++) {
                for (int z = -MAX_COUNT; z <= MAX_COUNT; z++) {
                    destroy(level, pos.offset(x, y, z));
                }
            }
        }
    }

    private void destroy(Level level, BlockPos pos) {
        //壊そうとするブロックの座標と種類を取得
        BlockState blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        // 壊そうとしているブロックが原木でない場合は何もしない
        if (block != Blocks.OAK_LOG &&
                block != Blocks.SPRUCE_LOG &&
                block != Blocks.BIRCH_LOG &&
                block != Blocks.JUNGLE_LOG &&
                block != Blocks.ACACIA_LOG &&
                block != Blocks.DARK_OAK_LOG &&
                block != Blocks.OAK_LEAVES &&
                block != Blocks.SPRUCE_LEAVES &&
                block != Blocks.BIRCH_LEAVES &&
                block != Blocks.JUNGLE_LEAVES &&
                block != Blocks.ACACIA_LEAVES &&
                block != Blocks.DARK_OAK_LEAVES
        ) {
            return;
        }

        // 壊したブロックが木谷はだったら、ブロックをドロップさせる
        Block.dropResources(blockState, level, pos);
        // 壊したブロックを空気にする
        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
    }
}
