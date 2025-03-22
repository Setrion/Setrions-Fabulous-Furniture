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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.setrion.fabulous_furniture.registry.SFFBlockEntityTypes;
import net.setrion.fabulous_furniture.world.level.block.KitchenCounterContainerBaseBlock;

public class SFFBaseContainerBlockEntity extends RandomizableContainerBlockEntity {

    protected NonNullList<ItemStack> items;
    private final boolean canBeOpened;
    private String title;
    private int size;
    private final ContainerOpenersCounter openersCounter;

    public SFFBaseContainerBlockEntity(BlockPos pos, BlockState state) {
        this(SFFBlockEntityTypes.SFF_CONTAINER.get(), false, "", 0, SoundEvents.BARREL_OPEN, SoundEvents.BARREL_CLOSE, pos, state);
    }

    public SFFBaseContainerBlockEntity(BlockEntityType<?> type, boolean canBeOpened, String title, int size, SoundEvent open, SoundEvent close, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.items = NonNullList.withSize(size*9, ItemStack.EMPTY);
        this.canBeOpened = canBeOpened;
        this.title = title;
        this.size = size;
        this.openersCounter = new ContainerOpenersCounter() {
            protected void onOpen(Level level, BlockPos pos, BlockState state) {
                if (canBeOpened && !state.getValue(KitchenCounterContainerBaseBlock.OPEN)) { //if not open
                    SFFBaseContainerBlockEntity.this.playSound(open);
                    SFFBaseContainerBlockEntity.this.updateBlockState(state, true);
                } else if (!canBeOpened) {
                    SFFBaseContainerBlockEntity.this.playSound(open);
                }
            }

            protected void onClose(Level level, BlockPos pos, BlockState state) {
                if (canBeOpened && state.getValue(KitchenCounterContainerBaseBlock.OPEN)) { //if already open
                    SFFBaseContainerBlockEntity.this.playSound(close);
                    SFFBaseContainerBlockEntity.this.updateBlockState(state, false);
                } else if (!canBeOpened) {
                    SFFBaseContainerBlockEntity.this.playSound(close);
                }
            }

            protected void openerCountChanged(Level level, BlockPos blockPos, BlockState blockState, int oldCount, int newCount) {
                SFFBaseContainerBlockEntity.this.signalOpenCount(level, blockPos, blockState, oldCount, newCount);
            }

            protected boolean isOwnContainer(Player player) {
                if (player.containerMenu instanceof ChestMenu) {
                    Container container = ((ChestMenu)player.containerMenu).getContainer();
                    return container == SFFBaseContainerBlockEntity.this;
                } else {
                    return false;
                }
            }
        };
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable(title);
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
        if (size == 3) return ChestMenu.threeRows(i, inventory, this);
        else if (size == 6) return ChestMenu.sixRows(i, inventory, this);
        else return null;
    }

    @Override
    public int getContainerSize() {
        return size*9;
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

    public static int getOpenCount(BlockGetter level, BlockPos pos) {
        BlockState blockstate = level.getBlockState(pos);
        if (blockstate.hasBlockEntity()) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof SFFBaseContainerBlockEntity) {
                return ((SFFBaseContainerBlockEntity)blockentity).openersCounter.getOpenerCount();
            }
        }

        return 0;
    }

    void updateBlockState(BlockState state, boolean open) {
        this.level.setBlock(this.getBlockPos(), state.setValue(KitchenCounterContainerBaseBlock.OPEN, open), 3);
    }

    void playSound(SoundEvent sound) {
        double d0 = worldPosition.getX() + 0.5 / 2.0;
        double d1 = worldPosition.getY() + 0.5 / 2.0;
        double d2 = worldPosition.getZ() + 0.5 / 2.0;
        level.playSound(null, d0, d1, d2, sound, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
    }

    protected void signalOpenCount(Level level, BlockPos pos, BlockState state, int eventId, int eventParam) {
        Block block = state.getBlock();
        level.blockEvent(pos, block, 1, eventParam);
    }
}