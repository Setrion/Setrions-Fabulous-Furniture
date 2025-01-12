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
import net.setrion.fabulous_furniture.registry.SFFBlockEntityTypes;
import net.setrion.fabulous_furniture.world.level.block.*;

public class KitchenCounterBlockEntity extends RandomizableContainerBlockEntity {

    private NonNullList<ItemStack> items;
    private final ContainerOpenersCounter openersCounter;

    public KitchenCounterBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SFFBlockEntityTypes.KITCHEN_COUNTER.get(), blockPos, blockState);
        this.items = NonNullList.withSize(27, ItemStack.EMPTY);
        this.openersCounter = new ContainerOpenersCounter() {
            protected void onOpen(Level level, BlockPos pos, BlockState state) {
                if ((state.getBlock() instanceof KitchenCounterContainerDoorBlock || state.getBlock() instanceof KitchenCounterContainerDrawerBlock || state.getBlock() instanceof KitchenCabinetContainerDoorBlock || state.getBlock() instanceof KitchenCabinetContainerSidewaysDoorBlock) && !state.getValue(KitchenCounterContainerDoorBlock.OPEN)) {
                    KitchenCounterBlockEntity.this.playSound(SoundEvents.BARREL_OPEN);
                    KitchenCounterBlockEntity.this.updateBlockState(state, true);
                }
            }

            protected void onClose(Level level, BlockPos pos, BlockState state) {
                if ((state.getBlock() instanceof KitchenCounterContainerDoorBlock || state.getBlock() instanceof KitchenCounterContainerDrawerBlock || state.getBlock() instanceof KitchenCabinetContainerDoorBlock || state.getBlock() instanceof KitchenCabinetContainerSidewaysDoorBlock) && state.getValue(KitchenCounterContainerDoorBlock.OPEN)) {
                    KitchenCounterBlockEntity.this.playSound(SoundEvents.BARREL_CLOSE);
                    KitchenCounterBlockEntity.this.updateBlockState(state, false);
                }
            }

            protected void openerCountChanged(Level level, BlockPos blockPos, BlockState blockState, int oldCount, int newCount) {
            }

            protected boolean isOwnContainer(Player player) {
                if (player.containerMenu instanceof ChestMenu) {
                    Container container = ((ChestMenu)player.containerMenu).getContainer();
                    return container == KitchenCounterBlockEntity.this;
                } else {
                    return false;
                }
            }
        };
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.kitchen_counter");
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
        return ChestMenu.threeRows(i, inventory, this);
    }

    @Override
    public int getContainerSize() {
        return 27;
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
        this.level.setBlock(this.getBlockPos(), state.setValue(KitchenCounterContainerDoorBlock.OPEN, open), 3);
    }

    void playSound(SoundEvent sound) {
        double d0 = (double)this.worldPosition.getX() + 0.5 / 2.0;
        double d1 = (double)this.worldPosition.getY() + 0.5 / 2.0;
        double d2 = (double)this.worldPosition.getZ() + 0.5 / 2.0;
        this.level.playSound(null, d0, d1, d2, sound, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }
}