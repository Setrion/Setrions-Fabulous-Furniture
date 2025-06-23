package net.setrion.fabulous_furniture.world.level.block.entity;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.setrion.fabulous_furniture.registry.SFFBlockEntityTypes;
import net.setrion.fabulous_furniture.world.level.block.FlowerBoxBigBlock;
import net.setrion.fabulous_furniture.world.level.block.FlowerBoxInnerCornerBlock;
import net.setrion.fabulous_furniture.world.level.block.FlowerBoxOuterCornerBlock;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public class FlowerBoxBlockEntity extends RandomizableContainerBlockEntity {

    protected NonNullList<ItemStack> flowers;
    protected int slots;
    private static final Logger LOGGER = LogUtils.getLogger();

    public FlowerBoxBlockEntity(BlockPos pos, BlockState state) {
        this(state.getBlock() instanceof FlowerBoxOuterCornerBlock ? 3 : state.getBlock() instanceof FlowerBoxBigBlock ? 4 : state.getBlock() instanceof FlowerBoxInnerCornerBlock ? 1 : 2, pos, state);
    }

    public FlowerBoxBlockEntity(int slots, BlockPos pos, BlockState blockState) {
        super(SFFBlockEntityTypes.FLOWER_BOX.get(), pos, blockState);
        this.slots = slots;
        flowers = NonNullList.withSize(slots, ItemStack.EMPTY);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        flowers = NonNullList.withSize(slots, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, flowers);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, flowers);
    }

    @Override
    protected Component getDefaultName() {
        return Component.empty();
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return flowers;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        flowers = nonNullList;
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return null;
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        try (ProblemReporter.ScopedCollector problemreporter$scopedcollector = new ProblemReporter.ScopedCollector(this.problemPath(), LOGGER)) {
            TagValueOutput tagvalueoutput = TagValueOutput.createWithContext(problemreporter$scopedcollector, provider);
            ContainerHelper.saveAllItems(tagvalueoutput, this.flowers, true);
            return tagvalueoutput.buildResult();
        }
    }

    public boolean placeFlower(ServerLevel level, @Nullable LivingEntity entity, int slot, ItemStack stack) {
        if (flowers.size() >= slot+1) {
            ItemStack itemstack = flowers.get(slot);
            if (itemstack.isEmpty()) {
                flowers.set(slot, stack.consumeAndReturn(1, entity));
                level.gameEvent(GameEvent.BLOCK_CHANGE, getBlockPos(), GameEvent.Context.of(entity, getBlockState()));
                markUpdated();
                return true;
            }
        }
        return false;
    }

    public ItemStack takeFlower(ServerLevel level, int slot, @Nullable LivingEntity entity) {
        if(flowers.size() >= slot+1) {
            ItemStack itemstack = flowers.get(slot);
            if (!itemstack.isEmpty()) {
                flowers.set(slot, ItemStack.EMPTY);
                level.gameEvent(GameEvent.BLOCK_CHANGE, getBlockPos(), GameEvent.Context.of(entity, getBlockState()));
                markUpdated();
                return itemstack;
            }
        }

        return ItemStack.EMPTY;
    }

    private void markUpdated() {
        setChanged();
        if(this.level != null) this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }

    public NonNullList<ItemStack> getFlowers() {
        return flowers;
    }

    @Override
    public int getContainerSize() {
        return slots;
    }
}