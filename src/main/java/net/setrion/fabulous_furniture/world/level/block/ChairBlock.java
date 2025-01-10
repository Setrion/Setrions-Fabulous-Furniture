package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChairBlock extends Block {

    public static final EnumProperty<Direction> FACING;
    protected static final VoxelShape CHAIR_BASE;
    protected static final VoxelShape CHAIR_NORTH;
    protected static final VoxelShape CHAIR_EAST;
    protected static final VoxelShape CHAIR_SOUTH;
    protected static final VoxelShape CHAIR_WEST;

    public ChairBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return switch (direction) {
            default:
            case NORTH: yield Shapes.or(CHAIR_BASE, CHAIR_NORTH);
            case EAST: yield Shapes.or(CHAIR_BASE, CHAIR_EAST);
            case SOUTH: yield Shapes.or(CHAIR_BASE, CHAIR_SOUTH);
            case WEST: yield Shapes.or(CHAIR_BASE, CHAIR_WEST);
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return rotate(blockState, mirror.getRotation(blockState.getValue(FACING)));
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;

        CHAIR_BASE = Shapes.or(Block.box(3, 0, 3, 5, 7, 5), Block.box(11, 0, 3, 13, 7, 5), Block.box(3, 0, 11, 5, 7, 13), Block.box(11, 0, 11, 13, 7, 13), Block.box(2, 7, 2, 14, 9, 14));
        CHAIR_NORTH = Block.box(2, 9, 12, 14, 20, 14);
        CHAIR_EAST = Block.box(2, 9, 2, 4, 20, 14);
        CHAIR_SOUTH = Block.box(2, 9, 2, 14, 20, 4);
        CHAIR_WEST = Block.box(12, 9, 2, 14, 20, 14);
    }
}