package net.setrion.fabulous_furniture.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.setrion.fabulous_furniture.registry.SFFBlockEntityTypes;
import net.setrion.fabulous_furniture.world.level.block.ClosetBlock;

public class ClosetBlockEntity extends SFFBaseContainerBlockEntity {

    public ClosetBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SFFBlockEntityTypes.CLOSET.get(), true, "container.closet", 6, SoundEvents.WOODEN_DOOR_OPEN, SoundEvents.WOODEN_DOOR_CLOSE, blockPos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        if (getBlockState().getValue(ClosetBlock.HALF).equals(DoubleBlockHalf.LOWER)) {
            items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
            if (!this.tryLoadLootTable(tag)) {
                ContainerHelper.loadAllItems(tag, items, provider);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        if (getBlockState().getValue(ClosetBlock.HALF).equals(DoubleBlockHalf.LOWER)) {
            if (!this.trySaveLootTable(tag)) {
                ContainerHelper.saveAllItems(tag, items, provider);
            }
        }
    }

    @Override
    void updateBlockState(BlockState state, boolean open) {
        if (state.getValue(ClosetBlock.HALF) == DoubleBlockHalf.LOWER) {
            level.setBlock(getBlockPos(), state.setValue(ClosetBlock.OPEN, open), 3);
            level.setBlock(getBlockPos().above(), state.setValue(ClosetBlock.OPEN, open).setValue(ClosetBlock.HALF, DoubleBlockHalf.UPPER), 3);
        } else {
            level.setBlock(getBlockPos(), state.setValue(ClosetBlock.OPEN, open), 3);
            level.setBlock(getBlockPos().below(), state.setValue(ClosetBlock.OPEN, open).setValue(ClosetBlock.HALF, DoubleBlockHalf.LOWER), 3);
        }
    }
}