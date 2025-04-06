package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.setrion.fabulous_furniture.util.VoxelShapeUtils;
import net.setrion.fabulous_furniture.world.level.block.state.properties.BenchShape;
import net.setrion.fabulous_furniture.world.level.entity.Seat;

import java.util.List;

public class BenchBlock extends Block implements BlockTagSupplier {

    public static final EnumProperty<Direction> FACING;
    public static final EnumProperty<BenchShape> SHAPE;

    private static final VoxelShape VOXELSHAPE_SINGLE;
    private static final VoxelShape VOXELSHAPE_LEFT;
    private static final VoxelShape VOXELSHAPE_RIGHT;
    private static final VoxelShape VOXELSHAPE_MIDDLE;

    public BenchBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockState state = this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
        BlockState state1 = blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos().relative(state.getValue(FACING).getClockWise()));
        BlockState state2 = blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos().relative(state.getValue(FACING).getClockWise().getOpposite()));
        if (isSameShelf(state, state1) && isSameShelf(state, state2)) {
            return state.setValue(SHAPE, BenchShape.MIDDLE);
        } else if (isSameShelf(state, state1)) {
            return state.setValue(SHAPE, BenchShape.RIGHT);
        } else if (isSameShelf(state, state2)) {
            return state.setValue(SHAPE, BenchShape.LEFT);
        } else {
            return state.setValue(SHAPE, BenchShape.SINGLE);
        }
    }

    public boolean isSameShelf(BlockState state, BlockState state2) {
        if (state2.getBlock() == this) {
            return state.getValue(FACING).equals(state2.getValue(FACING));
        }
        return false;
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        VoxelShape vShape = switch(state.getValue(SHAPE)) {
            case SINGLE -> VOXELSHAPE_SINGLE;
            case LEFT -> VOXELSHAPE_LEFT;
            case RIGHT -> VOXELSHAPE_RIGHT;
            case MIDDLE -> VOXELSHAPE_MIDDLE;
        };
        return switch (direction) {
            case NORTH: yield vShape;
            case EAST: yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.EAST, vShape);
            case SOUTH: yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.SOUTH, vShape);
            case WEST:
            default: yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.WEST, vShape);
        };
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(Seat.sit(player, pos, 6.75*0.0625F, state.getValue(FACING))) {
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SHAPE);
    }

    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos blockPos, Direction direction, BlockPos newPos, BlockState newState, RandomSource random) {
        BlockState state1 = level.getBlockState(blockPos.relative(state.getValue(FACING).getClockWise()));
        BlockState state2 = level.getBlockState(blockPos.relative(state.getValue(FACING).getClockWise().getOpposite()));
        if (isSameShelf(state, state1) && isSameShelf(state, state2)) {
            return state.setValue(SHAPE, BenchShape.MIDDLE);
        } else if (isSameShelf(state, state1)) {
            return state.setValue(SHAPE, BenchShape.RIGHT);
        } else if (isSameShelf(state, state2)) {
            return state.setValue(SHAPE, BenchShape.LEFT);
        } else {
            return state.setValue(SHAPE, BenchShape.SINGLE);
        }
    }

    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
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
    public List<TagKey<Block>> getTags() {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        SHAPE = EnumProperty.create("shape", BenchShape.class);

        VOXELSHAPE_MIDDLE = Shapes.or(Block.box(0, 0, 1, 16, 2, 3), Block.box(0, 11, 14, 16, 13, 16), Block.box(0, 13, 15, 16, 14, 17), Block.box(0, 9, 13, 16, 11, 15), Block.box(0, 7, 12, 16, 9, 14), Block.box(0, 2, 2, 16, 4, 4), Block.box(0, 4, 3, 16, 6, 5), Block.box(0, 5, 5, 16, 7, 13));
        VOXELSHAPE_LEFT = Shapes.or(VOXELSHAPE_MIDDLE, Block.box(13, 0, 12.75, 15, 6, 13.75));
        VOXELSHAPE_RIGHT = Shapes.or(VOXELSHAPE_MIDDLE, Block.box(1, 0, 12.75, 3, 6, 13.75));
        VOXELSHAPE_SINGLE = Shapes.or(VOXELSHAPE_MIDDLE, Block.box(13, 0, 12.75, 15, 6, 13.75), Block.box(1, 0, 12.75, 3, 6, 13.75));
    }
}