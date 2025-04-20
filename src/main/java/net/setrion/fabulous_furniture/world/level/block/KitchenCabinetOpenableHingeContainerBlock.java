package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class KitchenCabinetOpenableHingeContainerBlock extends KitchenCabinetOpenableContainerBlock {

    public KitchenCabinetOpenableHingeContainerBlock(Properties properties) {
        super(properties, true);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, HINGE);
    }
}