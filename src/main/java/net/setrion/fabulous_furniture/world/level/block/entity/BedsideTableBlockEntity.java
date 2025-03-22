package net.setrion.fabulous_furniture.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.BlockState;
import net.setrion.fabulous_furniture.registry.SFFBlockEntityTypes;

public class BedsideTableBlockEntity extends SFFBaseContainerBlockEntity {

    public BedsideTableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SFFBlockEntityTypes.BEDSIDE_TABLE.get(), true, "container.bedside_table", 3, SoundEvents.BARREL_OPEN, SoundEvents.BARREL_CLOSE, blockPos, blockState);
    }
}