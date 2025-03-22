package net.setrion.fabulous_furniture.world.level.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class KitchenCabinetContainerBaseBlock extends KitchenCounterContainerBaseBlock {

    public static final MapCodec<KitchenCabinetContainerBaseBlock> CODEC = simpleCodec(KitchenCabinetContainerBaseBlock::new);

    public static final EnumProperty<Direction> FACING;
    protected static final VoxelShape COUNTER_NORTH;
    protected static final VoxelShape COUNTER_EAST;
    protected static final VoxelShape COUNTER_SOUTH;
    protected static final VoxelShape COUNTER_WEST;

    private boolean canBeOpened;
    private boolean hasHinge;

    protected KitchenCabinetContainerBaseBlock(Properties properties) {
        this(properties, false, false);
    }

    public KitchenCabinetContainerBaseBlock(Properties properties, boolean canBeOpened, boolean hasHinge) {
        super(properties, canBeOpened, hasHinge);
        this.canBeOpened = canBeOpened;
        this.hasHinge = hasHinge;
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
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockState state = defaultBlockState();
        if (canBeOpened) {
            state = state.setValue(OPEN, false);
        }
        if (hasHinge) {
            state = state.setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite()).setValue(HINGE, this.getHinge(blockPlaceContext));
        } else {
            state = state.setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
        }
        return state;
    }

    private DoorHingeSide getHinge(BlockPlaceContext context) {
        BlockGetter blockgetter = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction direction = context.getHorizontalDirection();
        BlockPos blockpos1 = blockpos.above();
        Direction direction1 = direction.getCounterClockWise();
        BlockPos blockpos2 = blockpos.relative(direction1);
        BlockState blockstate = blockgetter.getBlockState(blockpos2);
        BlockPos blockpos3 = blockpos1.relative(direction1);
        BlockState blockstate1 = blockgetter.getBlockState(blockpos3);
        Direction direction2 = direction.getClockWise();
        BlockPos blockpos4 = blockpos.relative(direction2);
        BlockState blockstate2 = blockgetter.getBlockState(blockpos4);
        BlockPos blockpos5 = blockpos1.relative(direction2);
        BlockState blockstate3 = blockgetter.getBlockState(blockpos5);
        int i = (blockstate.isCollisionShapeFullBlock(blockgetter, blockpos2) ? -1 : 0) + (blockstate1.isCollisionShapeFullBlock(blockgetter, blockpos3) ? -1 : 0) + (blockstate2.isCollisionShapeFullBlock(blockgetter, blockpos4) ? 1 : 0) + (blockstate3.isCollisionShapeFullBlock(blockgetter, blockpos5) ? 1 : 0);
        boolean flag = blockstate.getBlock() instanceof KitchenCabinetContainerBaseBlock;
        boolean flag1 = blockstate2.getBlock() instanceof KitchenCabinetContainerBaseBlock;
        if ((!flag || flag1) && i <= 0) {
            if ((!flag1 || flag) && i == 0) {
                int j = direction.getStepX();
                int k = direction.getStepZ();
                Vec3 vec3 = context.getClickLocation();
                double d0 = vec3.x - (double)blockpos.getX();
                double d1 = vec3.z - (double)blockpos.getZ();
                return j < 0 && d1 < 0.5 || j > 0 && d1 > 0.5 || k < 0 && d0 > 0.5 || k > 0 && d0 < 0.5 ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT;
            } else {
                return DoorHingeSide.LEFT;
            }
        } else {
            return DoorHingeSide.RIGHT;
        }
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
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        Containers.dropContentsOnDestroy(state, newState, level, pos);
        super.onRemove(state, level, pos, newState, isMoving);
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;

        COUNTER_NORTH = Shapes.or(Block.box(0, 0, 6, 16, 2, 16), Block.box(0, 2, 4, 16, 4, 16), Block.box(0, 4, 2, 16, 14, 16));
        COUNTER_EAST = Shapes.or(Block.box(0, 0, 0, 10, 2, 16), Block.box(0, 2, 0, 12, 4, 16), Block.box(0, 4, 0, 14, 14, 16));
        COUNTER_SOUTH = Shapes.or(Block.box(0, 0, 0, 16, 2, 10), Block.box(0, 2, 0, 16, 4, 12), Block.box(0, 4, 0, 16, 14, 14));
        COUNTER_WEST = Shapes.or(Block.box(6, 0, 0, 16, 2, 16), Block.box(4, 2, 0, 16, 4, 16), Block.box(2, 4, 0, 16, 14, 16));
    }
}