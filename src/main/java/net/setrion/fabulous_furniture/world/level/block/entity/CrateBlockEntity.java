package net.setrion.fabulous_furniture.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.BlockState;
import net.setrion.fabulous_furniture.registry.SFFBlockEntityTypes;

public class CrateBlockEntity extends SFFBaseContainerBlockEntity {

    public CrateBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SFFBlockEntityTypes.CRATE.get(), false, "container.crate", 3, SoundEvents.BARREL_OPEN, SoundEvents.BARREL_CLOSE, blockPos, blockState);
    }
}