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

public class KitchenCounterInnerCornerBlock extends Block {

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    protected final VoxelShape COUNTER_NORTH = Shapes.or(Block.box(0, 0, 2, 16, 14, 16), Block.box(2, 0, 0, 16, 14, 2), Block.box(0, 14, 0, 16, 16, 16));
    protected final VoxelShape COUNTER_EAST = Shapes.or(Block.box(0, 0, 0, 14, 14, 16), Block.box(14, 0, 2, 16, 14, 16), Block.box(0, 14, 0, 16, 16, 16));
    protected final VoxelShape COUNTER_SOUTH = Shapes.or(Block.box(0, 0, 0, 16, 14, 14), Block.box(0, 0, 14, 14, 14, 16), Block.box(0, 14, 0, 16, 16, 16));
    protected final VoxelShape COUNTER_WEST = Shapes.or(Block.box(0, 0, 0, 16, 14, 14), Block.box(2, 0, 14, 16, 14, 16), Block.box(0, 14, 0, 16, 16, 16));


    public KitchenCounterInnerCornerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return switch (direction) {
            default:
            case NORTH: yield Shapes.or(COUNTER_NORTH);
            case EAST: yield Shapes.or(COUNTER_EAST);
            case SOUTH: yield Shapes.or(COUNTER_SOUTH);
            case WEST: yield Shapes.or(COUNTER_WEST);
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
}