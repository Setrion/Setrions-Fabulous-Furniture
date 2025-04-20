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
import net.setrion.fabulous_furniture.world.level.block.*;

public class KitchenFridgeBlockEntity extends SFFBaseContainerBlockEntity {

    public KitchenFridgeBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SFFBlockEntityTypes.FRIDGE.get(), true, "container.fridge", 6, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_DOOR_CLOSE, blockPos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        if (getBlockState().getValue(FridgeBlock.HALF).equals(DoubleBlockHalf.LOWER)) {
            items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
            if (!this.tryLoadLootTable(tag)) {
                ContainerHelper.loadAllItems(tag, items, provider);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        if (getBlockState().getValue(FridgeBlock.HALF).equals(DoubleBlockHalf.LOWER)) {
            if (!this.trySaveLootTable(tag)) {
                ContainerHelper.saveAllItems(tag, items, provider);
            }
        }
    }

    @Override
    void updateBlockState(BlockState state, boolean open) {
        if (level != null) {
            if (state.getValue(FridgeBlock.HALF) == DoubleBlockHalf.LOWER) {
                level.setBlock(getBlockPos(), state.setValue(FridgeBlock.OPEN, open), 3);
                level.setBlock(getBlockPos().above(), state.setValue(FridgeBlock.OPEN, open).setValue(FridgeBlock.HALF, DoubleBlockHalf.UPPER), 3);
            } else {
                level.setBlock(getBlockPos(), state.setValue(FridgeBlock.OPEN, open), 3);
                level.setBlock(getBlockPos().below(), state.setValue(FridgeBlock.OPEN, open).setValue(FridgeBlock.HALF, DoubleBlockHalf.LOWER), 3);
            }
        }
    }
}