package com.example.examplemod.mc_01_myblock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class BlockMyBlock extends Block {
    public BlockMyBlock() {
        super(BlockBehaviour.Properties.of(Material.DIRT));
    }


}
