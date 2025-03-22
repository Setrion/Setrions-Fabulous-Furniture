package net.setrion.fabulous_furniture.world.level.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.setrion.fabulous_furniture.registry.SFFBlocks;
import net.setrion.fabulous_furniture.registry.SFFStats;
import net.setrion.fabulous_furniture.util.VoxelShapeUtils;
import net.setrion.fabulous_furniture.world.level.block.entity.ClosetBlockEntity;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;

public class ClosetBlock extends BaseEntityBlock {

    public static final MapCodec<ClosetBlock> CODEC = simpleCodec(ClosetBlock::new);

    public static final EnumProperty<Direction> FACING;
    public static final BooleanProperty OPEN;
    public static final EnumProperty<DoubleBlockHalf> HALF;

    protected static final VoxelShape BOTTOM_OPEN;
    protected static final VoxelShape BOTTOM_CLOSED;
    protected static final VoxelShape TOP_OPEN;
    protected static final VoxelShape TOP_CLOSED;

    public ClosetBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false));
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        VoxelShape shape = BOTTOM_CLOSED;
        if (state.getValue(OPEN) && state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            shape = TOP_OPEN;
        } else if (!state.getValue(OPEN) && state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            shape = TOP_CLOSED;
        } else if (state.getValue(OPEN) && state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            shape = BOTTOM_OPEN;
        }
        return switch (direction) {
            default:
            case NORTH: yield shape;
            case EAST: yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.EAST, shape);
            case SOUTH: yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.SOUTH, shape);
            case WEST: yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.WEST, shape);
        };
    }

    protected BlockState updateShape(BlockState blockState, LevelReader level, ScheduledTickAccess tickAccess, BlockPos blockPos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        DoubleBlockHalf doubleblockhalf = blockState.getValue(HALF);
        if (direction.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            return neighborState.getBlock() instanceof ClosetBlock && neighborState.getValue(HALF) != doubleblockhalf ? neighborState.setValue(HALF, doubleblockhalf) : Blocks.AIR.defaultBlockState();
        } else {
            return doubleblockhalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !blockState.canSurvive(level, blockPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, level, tickAccess, blockPos, direction, neighborPos, neighborState, random);
        }
    }

    protected void onExplosionHit(BlockState blockState, ServerLevel level, BlockPos blockPos, Explosion explosion, BiConsumer<ItemStack, BlockPos> consumer) {
        if (explosion.canTriggerBlocks() && blockState.getValue(HALF) == DoubleBlockHalf.LOWER) {
            this.setOpen(null, level, blockState, blockPos, !this.isOpen(blockState));
        }

        super.onExplosionHit(blockState, level, blockPos, explosion, consumer);
    }

    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = level.getBlockState(blockpos);
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(level, blockpos, Direction.UP) : blockstate.is(this);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();
        if (blockpos.getY() < level.getMaxY() && level.getBlockState(blockpos.above()).canBeReplaced(context)) {
            boolean flag = level.hasNeighborSignal(blockpos) || level.hasNeighborSignal(blockpos.above());
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(OPEN, flag).setValue(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, FACING, OPEN);
    }

    private void playSound(@Nullable Entity source, BlockState state, Level level, BlockPos pos, boolean isOpening) {
        SoundEvent open = SoundEvents.WOODEN_DOOR_OPEN;
        SoundEvent close = SoundEvents.WOODEN_DOOR_CLOSE;
        level.playSound(source, pos, isOpening ? open : close, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (level instanceof ServerLevel serverlevel) {
            if (player.isShiftKeyDown()) {
                blockState = blockState.cycle(OPEN);
                System.out.println(SFFBlocks.BLOCKS.getEntries().size());
                level.setBlock(blockPos, blockState, 10);
                this.playSound(null, blockState, level, blockPos, blockState.getValue(OPEN));
                level.gameEvent(player, this.isOpen(blockState) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, blockPos);
                return InteractionResult.SUCCESS;
            } else {
                if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
                    blockPos = blockPos.below();
                }
                MenuProvider menuprovider = this.getMenuProvider(blockState, level, blockPos);
                if (menuprovider != null) {
                    player.openMenu(menuprovider);
                    player.awardStat(Stats.CUSTOM.get(SFFStats.OPEN_FRIDGE.get()));
                    PiglinAi.angerNearbyPiglins(serverlevel, player, true);
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    public boolean isOpen(BlockState state) {
        return state.getValue(OPEN);
    }

    public void setOpen(@Nullable Entity entity, Level level, BlockState state, BlockPos pos, boolean open) {
        if (state.is(this) && state.getValue(OPEN) != open) {
            level.setBlock(pos, state.setValue(OPEN, open), 10);
            this.playSound(entity, state, level, pos, open);
            level.gameEvent(entity, open ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        }
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        OPEN = BlockStateProperties.OPEN;
        HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
        BOTTOM_OPEN = Block.box(0, 0, 2, 16, 16, 16);
        BOTTOM_CLOSED = Block.box(0, 0, 0, 14, 16, 16);
        TOP_OPEN = Block.box(0, 0, 0, 16, 16, 14);
        TOP_CLOSED = Block.box(2, 0, 0, 16, 16, 16);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ClosetBlockEntity(blockPos, blockState);
    }
}