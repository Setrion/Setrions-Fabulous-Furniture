package net.setrion.fabulous_furniture.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.setrion.fabulous_furniture.registry.SFFBlockEntityTypes;
import net.setrion.fabulous_furniture.world.level.block.KitchenCabinetOpenableContainerBlock;

public class KitchenStorageBaseBlockEntity extends SFFBaseContainerBlockEntity {

    public KitchenStorageBaseBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(blockState.hasProperty(KitchenCabinetOpenableContainerBlock.OPEN), blockPos, blockState);
    }

    public KitchenStorageBaseBlockEntity(boolean canBeOpened, BlockPos blockPos, BlockState blockState) {
        super(SFFBlockEntityTypes.KITCHEN_STORAGE.get(), canBeOpened, "container.kitchen_storage", 3, SoundEvents.BARREL_OPEN, SoundEvents.BARREL_CLOSE, blockPos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(tag)) {
            ContainerHelper.loadAllItems(tag, items, provider);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, items, provider);
        }
    }
}