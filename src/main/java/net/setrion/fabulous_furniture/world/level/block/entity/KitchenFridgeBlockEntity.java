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
                if (state.getBlock() instanceof KitchenFridgeBlock) {
                    KitchenFridgeBlockEntity.this.playSound(SoundEvents.IRON_DOOR_OPEN);
                    KitchenFridgeBlockEntity.this.updateBlockState(state, true);
                }
            }

            protected void onClose(Level level, BlockPos pos, BlockState state) {
                if (state.getBlock() instanceof KitchenFridgeBlock) {
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
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(tag)) {
            ContainerHelper.loadAllItems(tag, this.items, provider);
        }

    }

    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, this.items, provider);
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
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    void updateBlockState(BlockState state, boolean open) {
        if (state.getValue(KitchenFridgeBlock.HALF) == DoubleBlockHalf.LOWER) {
            this.level.setBlock(this.getBlockPos(), state.setValue(KitchenFridgeBlock.OPEN, open), 3);
            this.level.setBlock(this.getBlockPos().above(), state.setValue(KitchenFridgeBlock.OPEN, open).setValue(KitchenFridgeBlock.HALF, DoubleBlockHalf.UPPER), 3);
        } else {
            this.level.setBlock(this.getBlockPos(), state.setValue(KitchenFridgeBlock.OPEN, open), 3);
            this.level.setBlock(this.getBlockPos().below(), state.setValue(KitchenFridgeBlock.OPEN, open).setValue(KitchenFridgeBlock.HALF, DoubleBlockHalf.LOWER), 3);
        }
    }

    void playSound(SoundEvent sound) {
        double d0 = (double)this.worldPosition.getX() + 0.5 / 2.0;
        double d1 = (double)this.worldPosition.getY() + 0.5 / 2.0;
        double d2 = (double)this.worldPosition.getZ() + 0.5 / 2.0;
        this.level.playSound(null, d0, d1, d2, sound, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }
}