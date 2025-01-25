package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
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
import net.setrion.fabulous_furniture.util.VoxelShapeUtils;
import net.setrion.fabulous_furniture.world.level.block.state.properties.CounterShape;

public class KitchenCabinetBlock extends Block {

    public static final EnumProperty<Direction> FACING;
    public static final EnumProperty<CounterShape> SHAPE;
    protected static final VoxelShape CABINET_SHAPE;
    protected static final VoxelShape INNER_CABINET_SHAPE;
    protected static final VoxelShape OUTER_CABINET_SHAPE;

    public KitchenCabinetBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        CounterShape shape = state.getValue(SHAPE);
        if ((shape == CounterShape.INNER_LEFT && direction == Direction.NORTH) || (shape == CounterShape.INNER_RIGHT && direction == Direction.WEST)) {
            return INNER_CABINET_SHAPE;
        } else if ((shape == CounterShape.INNER_LEFT && direction == Direction.EAST) || (shape == CounterShape.INNER_RIGHT && direction == Direction.NORTH)) {
            return VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.EAST, INNER_CABINET_SHAPE);
        } else if ((shape == CounterShape.INNER_LEFT && direction == Direction.SOUTH) || (shape == CounterShape.INNER_RIGHT && direction == Direction.EAST)) {
            return VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.SOUTH, INNER_CABINET_SHAPE);
        } else if ((shape == CounterShape.INNER_LEFT && direction == Direction.WEST) || (shape == CounterShape.INNER_RIGHT && direction == Direction.SOUTH)) {
            return VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.WEST, INNER_CABINET_SHAPE);
        } else if ((shape == CounterShape.OUTER_LEFT && direction == Direction.NORTH) || (shape == CounterShape.OUTER_RIGHT && direction == Direction.WEST)) {
            return OUTER_CABINET_SHAPE;
        } else if ((shape == CounterShape.OUTER_LEFT && direction == Direction.EAST) || (shape == CounterShape.OUTER_RIGHT && direction == Direction.NORTH)) {
            return VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.EAST, OUTER_CABINET_SHAPE);
        } else if ((shape == CounterShape.OUTER_LEFT && direction == Direction.SOUTH) || (shape == CounterShape.OUTER_RIGHT && direction == Direction.EAST)) {
            return VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.SOUTH, OUTER_CABINET_SHAPE);
        } else if ((shape == CounterShape.OUTER_LEFT && direction == Direction.WEST) || (shape == CounterShape.OUTER_RIGHT && direction == Direction.SOUTH)) {
            return VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.WEST, OUTER_CABINET_SHAPE);
        }{
            return switch (direction) {
                default:
                case NORTH:
                    yield CABINET_SHAPE;
                case EAST:
                    yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.EAST, CABINET_SHAPE);
                case SOUTH:
                    yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.SOUTH, CABINET_SHAPE);
                case WEST:
                    yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.WEST, CABINET_SHAPE);
            };
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SHAPE);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
        return blockstate.setValue(SHAPE, getCabinetShape(blockstate, context.getLevel(), blockpos));
    }

    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        return direction.getAxis().isHorizontal() ? state.setValue(SHAPE, getCabinetShape(state, level, pos)) : super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    private static CounterShape getCabinetShape(BlockState state, BlockGetter level, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        BlockState blockstate = level.getBlockState(pos.relative(direction));
        if (isKitchenCounter(blockstate)) {
            Direction direction1 = blockstate.getValue(FACING);
            if (direction1.getAxis() != state.getValue(FACING).getAxis() && canTakeShape(state, level, pos, direction1.getOpposite())) {
                if (direction1 == direction.getCounterClockWise()) {
                    return CounterShape.INNER_LEFT;
                }

                return CounterShape.INNER_RIGHT;
            }
        }

        BlockState blockstate1 = level.getBlockState(pos.relative(direction.getOpposite()));
        if (isKitchenCounter(blockstate1)) {
            Direction direction2 = blockstate1.getValue(FACING);
            if (direction2.getAxis() != state.getValue(FACING).getAxis() && canTakeShape(state, level, pos, direction2)) {
                if (direction2 == direction.getCounterClockWise()) {
                    return CounterShape.OUTER_LEFT;
                }

                return CounterShape.OUTER_RIGHT;
            }
        }

        return CounterShape.STRAIGHT;
    }

    private static boolean canTakeShape(BlockState state, BlockGetter level, BlockPos pos, Direction face) {
        BlockState blockstate = level.getBlockState(pos.relative(face));
        return !isKitchenCounter(blockstate) || blockstate.getValue(FACING) != state.getValue(FACING);
    }

    public static boolean isKitchenCounter(BlockState state) {
        return state.getBlock() instanceof KitchenCabinetBlock || state.getBlock() instanceof KitchenCabinetContainerBlock;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    protected BlockState mirror(BlockState state, Mirror mirror) {
        Direction direction = state.getValue(FACING);
        CounterShape counterShape = state.getValue(SHAPE);
        switch (mirror) {
            case LEFT_RIGHT:
                if (direction.getAxis() == Direction.Axis.Z) {
                    return switch (counterShape) {
                        case INNER_LEFT ->
                                rotate(state, Rotation.CLOCKWISE_180).setValue(SHAPE, CounterShape.INNER_RIGHT);
                        case INNER_RIGHT ->
                                rotate(state, Rotation.CLOCKWISE_180).setValue(SHAPE, CounterShape.INNER_LEFT);
                        case OUTER_LEFT ->
                                rotate(state, Rotation.CLOCKWISE_180).setValue(SHAPE, CounterShape.OUTER_RIGHT);
                        case OUTER_RIGHT ->
                                rotate(state, Rotation.CLOCKWISE_180).setValue(SHAPE, CounterShape.OUTER_LEFT);
                        default -> rotate(state, Rotation.CLOCKWISE_180);
                    };
                }
                break;
            case FRONT_BACK:
                if (direction.getAxis() == Direction.Axis.X) {
                    return switch (counterShape) {
                        case INNER_LEFT ->
                                rotate(state, Rotation.CLOCKWISE_180).setValue(SHAPE, CounterShape.INNER_LEFT);
                        case INNER_RIGHT ->
                                rotate(state, Rotation.CLOCKWISE_180).setValue(SHAPE, CounterShape.INNER_RIGHT);
                        case OUTER_LEFT ->
                                rotate(state, Rotation.CLOCKWISE_180).setValue(SHAPE, CounterShape.OUTER_RIGHT);
                        case OUTER_RIGHT ->
                                rotate(state, Rotation.CLOCKWISE_180).setValue(SHAPE, CounterShape.OUTER_LEFT);
                        case STRAIGHT -> rotate(state, Rotation.CLOCKWISE_180);
                    };
                }
        }

        return super.mirror(state, mirror);
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        SHAPE = EnumProperty.create("shape", CounterShape.class);

        CABINET_SHAPE = Shapes.or(Block.box(0, 0, 6, 16, 2, 16), Block.box(0, 2, 4, 16, 4, 16), Block.box(0, 4, 2, 16, 14, 16));

        INNER_CABINET_SHAPE = Shapes.or(Block.box(6, 0, 0, 16, 2, 16), Block.box(0, 0, 6, 16, 2, 16), Block.box(4, 2, 0, 16, 4, 16), Block.box(0, 2, 4, 16, 4, 16), Block.box(2, 4, 0, 16, 14, 16), Block.box(0, 4, 2, 16, 14, 16));

        OUTER_CABINET_SHAPE = Shapes.or(Block.box(6, 0, 6, 16, 2, 16), Block.box(4, 2, 4, 16, 4, 16), Block.box(2, 4, 2, 16, 14, 16));

    }
}