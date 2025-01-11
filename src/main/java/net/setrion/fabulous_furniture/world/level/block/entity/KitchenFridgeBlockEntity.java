package net.setrion.fabulous_furniture.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.setrion.fabulous_furniture.registry.SFFBlockEntityTypes;
import net.setrion.fabulous_furniture.world.level.block.*;

public class KitchenFridgeBlockEntity extends RandomizableContainerBlockEntity {

    private NonNullList<ItemStack> items;
    private final ContainerOpenersCounter openersCounter;

    public KitchenFridgeBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SFFBlockEntityTypes.FRIDGE.get(), blockPos, blockState);
        this.items = NonNullList.withSize(54, ItemStack.EMPTY);
        this.openersCounter = new ContainerOpenersCounter() {
            protected void onOpen(Level level, BlockPos pos, BlockState state) {
                if (!state.getValue(KitchenFridgeBlock.OPEN) && state.getBlock() instanceof KitchenFridgeBlock) {
                    KitchenFridgeBlockEntity.this.playSound(SoundEvents.IRON_DOOR_OPEN);
                    KitchenFridgeBlockEntity.this.updateBlockState(state, true);
                }
            }

            protected void onClose(Level level, BlockPos pos, BlockState state) {
                if (state.getValue(KitchenFridgeBlock.OPEN) && state.getBlock() instanceof KitchenFridgeBlock) {
                    KitchenFridgeBlockEntity.this.playSound(SoundEvents.IRON_DOOR_CLOSE);
                    KitchenFridgeBlockEntity.this.updateBlockState(state, false);
                }
            }

            protected void openerCountChanged(Level level, BlockPos blockPos, BlockState blockState, int oldCount, int newCount) {
            }

            protected boolean isOwnContainer(Player player) {
                if (player.containerMenu instanceof ChestMenu) {
                    Container container = ((ChestMenu)player.containerMenu).getContainer();
                    return container == KitchenFridgeBlockEntity.this;
                } else {
                    return false;
                }
            }
        };
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.fridge");
    }

    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        if (getBlockState().getValue(KitchenFridgeBlock.HALF).equals(DoubleBlockHalf.LOWER)) {
            items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
            if (!this.tryLoadLootTable(tag)) {
                ContainerHelper.loadAllItems(tag, items, provider);
            }
        }
    }

    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        if (getBlockState().getValue(KitchenFridgeBlock.HALF).equals(DoubleBlockHalf.LOWER)) {
            if (!this.trySaveLootTable(tag)) {
                ContainerHelper.saveAllItems(tag, items, provider);
            }
        }
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return ChestMenu.sixRows(i, inventory, this);
    }

    @Override
    public int getContainerSize() {
        return 54;
    }

    public void startOpen(Player player) {
        if (!remove && !player.isSpectator()) {
            openersCounter.incrementOpeners(player, getLevel(), getBlockPos(), getBlockState());
        }
    }

    public void stopOpen(Player player) {
        if (!remove && !player.isSpectator()) {
            openersCounter.decrementOpeners(player, getLevel(), getBlockPos(), getBlockState());
        }
    }

    public void recheckOpen() {
        if (!remove) {
            openersCounter.recheckOpeners(getLevel(), getBlockPos(), getBlockState());
        }
    }

    void updateBlockState(BlockState state, boolean open) {
        if (state.getValue(KitchenFridgeBlock.HALF) == DoubleBlockHalf.LOWER) {
            level.setBlock(getBlockPos(), state.setValue(KitchenFridgeBlock.OPEN, open), 3);
            level.setBlock(getBlockPos().above(), state.setValue(KitchenFridgeBlock.OPEN, open).setValue(KitchenFridgeBlock.HALF, DoubleBlockHalf.UPPER), 3);
        } else {
            level.setBlock(getBlockPos(), state.setValue(KitchenFridgeBlock.OPEN, open), 3);
            level.setBlock(getBlockPos().below(), state.setValue(KitchenFridgeBlock.OPEN, open).setValue(KitchenFridgeBlock.HALF, DoubleBlockHalf.LOWER), 3);
        }
    }

    void playSound(SoundEvent sound) {
        double d0 = worldPosition.getX() + 0.5 / 2.0;
        double d1 = worldPosition.getY() + 0.5 / 2.0;
        double d2 = worldPosition.getZ() + 0.5 / 2.0;
        level.playSound(null, d0, d1, d2, sound, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
    }
}