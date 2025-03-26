package net.setrion.fabulous_furniture.world.level.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.setrion.fabulous_furniture.world.level.block.state.properties.ShelfShape;

public class KitchenShelfBlock extends KitchenCabinetContainerBaseBlock {

    public static final MapCodec<KitchenShelfBlock> CODEC = simpleCodec(KitchenShelfBlock::new);

    public static final EnumProperty<Direction> FACING;
    public static final EnumProperty<ShelfShape> SHAPE;

    protected static final VoxelShape COUNTER_NORTH;
    protected static final VoxelShape COUNTER_EAST;
    protected static final VoxelShape COUNTER_SOUTH;
    protected static final VoxelShape COUNTER_WEST;


    public KitchenShelfBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return switch (direction) {
            default:
            case NORTH: yield COUNTER_NORTH;
            case EAST: yield COUNTER_EAST;
            case SOUTH: yield COUNTER_SOUTH;
            case WEST: yield COUNTER_WEST;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SHAPE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockState state = this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
        BlockState state1 = blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos().relative(state.getValue(FACING).getClockWise()));
        BlockState state2 = blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos().relative(state.getValue(FACING).getClockWise().getOpposite()));
        if (isSameShelf(state, state1) && isSameShelf(state, state2)) {
            return state.setValue(SHAPE, ShelfShape.MIDDLE);
        } else if (isSameShelf(state, state1)) {
            return state.setValue(SHAPE, ShelfShape.RIGHT);
        } else if (isSameShelf(state, state2)) {
            return state.setValue(SHAPE, ShelfShape.LEFT);
        } else {
            return state.setValue(SHAPE, ShelfShape.SINGLE);
        }
    }

    public boolean isSameShelf(BlockState state, BlockState state2) {
        if (state2.getBlock() instanceof KitchenShelfBlock) {
            return state.getValue(FACING).equals(state2.getValue(FACING));
        }
        return false;
    }

    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos blockPos, Direction direction, BlockPos newPos, BlockState newState, RandomSource random) {
        if (direction == Direction.UP && !this.canSurvive(state, level, blockPos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            BlockState state1 = level.getBlockState(blockPos.relative(state.getValue(FACING).getClockWise()));
            BlockState state2 = level.getBlockState(blockPos.relative(state.getValue(FACING).getClockWise().getOpposite()));
            if (isSameShelf(state, state1) && isSameShelf(state, state2)) {
                return state.setValue(SHAPE, ShelfShape.MIDDLE);
            } else if (isSameShelf(state, state1)) {
                return state.setValue(SHAPE, ShelfShape.RIGHT);
            } else if (isSameShelf(state, state2)) {
                return state.setValue(SHAPE, ShelfShape.LEFT);
            } else {
                return state.setValue(SHAPE, ShelfShape.SINGLE);
            }
        }
    }

    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos blockPos) {
        BlockPos above = blockPos.above();
        BlockPos below = blockPos.below();
        BlockState aboveState = level.getBlockState(above);
        BlockState belowState = level.getBlockState(below);
        return aboveState.isFaceSturdy(level, above, Direction.DOWN) || belowState.isFaceSturdy(level, below, Direction.UP) || (aboveState.getBlock() instanceof KitchenShelfBlock && aboveState.getValue(FACING) == state.getValue(FACING));
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return rotate(blockState, mirror.getRotation(blockState.getValue(FACING)));
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState p_393681_, ServerLevel p_394632_, BlockPos p_394133_, boolean p_394282_) {
        Containers.updateNeighboursAfterDestroy(p_393681_, p_394632_, p_394133_);
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        SHAPE = EnumProperty.create("shape", ShelfShape.class);

        COUNTER_NORTH = Shapes.or(Block.box(0, 0, 5, 16, 1, 16), Block.box(0, 8, 5, 16, 9, 16));
        COUNTER_EAST = Shapes.or(Block.box(0, 0, 0, 11, 1, 16), Block.box(0, 8, 0, 11, 9, 16));
        COUNTER_SOUTH = Shapes.or(Block.box(0, 0, 0, 16, 1, 11), Block.box(0, 8, 0, 16, 9, 11));
        COUNTER_WEST = Shapes.or(Block.box(5, 0, 0, 16, 1, 16), Block.box(5, 8, 0, 16, 9, 16));
    }
}