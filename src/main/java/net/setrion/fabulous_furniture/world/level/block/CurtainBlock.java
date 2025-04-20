package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.setrion.fabulous_furniture.util.VoxelShapeUtils;
import net.setrion.fabulous_furniture.world.level.block.state.properties.CurtainShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CurtainBlock extends Block implements BlockTagSupplier, ItemModelSupplier {

    public static final EnumProperty<CurtainShape> CURTAIN_SHAPE;
    public static final EnumProperty<Direction> FACING;
    public static final BooleanProperty LEFT;
    public static final BooleanProperty RIGHT;
    public static final BooleanProperty OPEN;

    public static final VoxelShape VOXELSHAPE;
    public static final VoxelShape VOXELSHAPE_COLLISION;

    public CurtainBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(LEFT, Boolean.FALSE)
                        .setValue(RIGHT, Boolean.FALSE)
                        .setValue(OPEN, Boolean.FALSE)
        );
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getHorizontalDirection().getOpposite();
        BlockState defaultState = defaultBlockState().setValue(FACING, facing);
        BlockState aboveState = context.getLevel().getBlockState(context.getClickedPos().above());
        BlockState belowState = context.getLevel().getBlockState(context.getClickedPos().below());
        BlockState leftState = context.getLevel().getBlockState(context.getClickedPos().relative(facing.getClockWise()));
        BlockState rightState = context.getLevel().getBlockState(context.getClickedPos().relative(facing.getClockWise().getOpposite()));
        if (isSameCurtains(defaultState, aboveState) && aboveState.getValue(CURTAIN_SHAPE) != CurtainShape.BOTTOM) {
            defaultState = defaultState.setValue(CURTAIN_SHAPE, CurtainShape.BOTTOM);
        } else if (isSameCurtains(defaultState, belowState) && belowState.getValue(CURTAIN_SHAPE) != CurtainShape.TOP) {
            defaultState = defaultState.setValue(CURTAIN_SHAPE, CurtainShape.TOP);
        } else {
            defaultState = defaultState.setValue(CURTAIN_SHAPE, CurtainShape.SINGLE);
        }
        if (rightState.getBlock().equals(this) && defaultState.getValue(CURTAIN_SHAPE) == rightState.getValue(CURTAIN_SHAPE)) {
            defaultState = defaultState.setValue(LEFT, isSameCurtains(defaultState, rightState));
        }
        if (leftState.getBlock().equals(this) && defaultState.getValue(CURTAIN_SHAPE) == leftState.getValue(CURTAIN_SHAPE)) {
            defaultState = defaultState.setValue(RIGHT, isSameCurtains(defaultState, leftState));
        }
        return defaultState;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        Direction facing = state.getValue(FACING);
        BlockState defaultState = defaultBlockState().setValue(FACING, facing).setValue(OPEN, state.getValue(OPEN));
        if (neighborState.getBlock().equals(this) && isSameCurtains(state, neighborState)) {
            if (state.getValue(CURTAIN_SHAPE) == CurtainShape.SINGLE && (neighborState.getValue(CURTAIN_SHAPE) != CurtainShape.TOP && neighborState.getValue(CURTAIN_SHAPE) != CurtainShape.BOTTOM)) {
                defaultState = defaultState.setValue(OPEN, neighborState.getValue(OPEN));
            } else if ((state.getValue(CURTAIN_SHAPE) == CurtainShape.TOP || state.getValue(CURTAIN_SHAPE) == CurtainShape.BOTTOM) && (neighborState.getValue(CURTAIN_SHAPE) == CurtainShape.TOP || neighborState.getValue(CURTAIN_SHAPE) == CurtainShape.BOTTOM)) {
                defaultState = defaultState.setValue(OPEN, neighborState.getValue(OPEN));
            }
        }
        BlockState aboveState = level.getBlockState(pos.above());
        BlockState belowState = level.getBlockState(pos.below());
        BlockState leftState = level.getBlockState(pos.relative(facing.getClockWise()));
        BlockState rightState = level.getBlockState(pos.relative(facing.getClockWise().getOpposite()));
        if (isSameCurtains(defaultState, aboveState) && aboveState.getValue(CURTAIN_SHAPE) != CurtainShape.BOTTOM) {
            defaultState = defaultState.setValue(CURTAIN_SHAPE, CurtainShape.BOTTOM);
        } else if (isSameCurtains(defaultState, belowState) && belowState.getValue(CURTAIN_SHAPE) != CurtainShape.TOP) {
            defaultState = defaultState.setValue(CURTAIN_SHAPE, CurtainShape.TOP);
        } else {
            defaultState = defaultState.setValue(CURTAIN_SHAPE, CurtainShape.SINGLE);
        }
        defaultState = defaultState.setValue(LEFT, isSameCurtains(defaultState, rightState) && defaultState.getValue(CURTAIN_SHAPE).equals(rightState.getValue(CURTAIN_SHAPE)));
        defaultState = defaultState.setValue(RIGHT, isSameCurtains(defaultState, leftState) && defaultState.getValue(CURTAIN_SHAPE).equals(leftState.getValue(CURTAIN_SHAPE)));
        return defaultState;
    }

    private boolean isSameCurtains(BlockState state, BlockState state2) {
        return state2.getBlock().equals(state.getBlock()) && state2.getValue(FACING).equals(state.getValue(FACING));
    }

    @Override
    protected int getLightBlock(BlockState blockState) {
        return blockState.getValue(OPEN) ? blockState.getLightBlock() : 15;
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return switch (direction) {
            case EAST:
                yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.EAST, VOXELSHAPE);
            case SOUTH:
                yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.SOUTH, VOXELSHAPE);
            case WEST:
                yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.WEST, VOXELSHAPE);
            case NORTH: default:
                yield VOXELSHAPE;
        };
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (level instanceof ServerLevel) {
            level.setBlock(blockPos, blockState.setValue(OPEN, !blockState.getValue(OPEN)), UPDATE_ALL);
            level.playSound(null, blockPos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        if (state.getValue(CURTAIN_SHAPE) != CurtainShape.BOTTOM) {
            return switch (direction) {
                case EAST:
                    yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.EAST, VOXELSHAPE_COLLISION);
                case SOUTH:
                    yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.SOUTH, VOXELSHAPE_COLLISION);
                case WEST:
                    yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.WEST, VOXELSHAPE_COLLISION);
                case NORTH: default:
                    yield VOXELSHAPE_COLLISION;
            };
        } else {
            return Block.box(0, 0, 0, 0, 0, 0);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CURTAIN_SHAPE, FACING, LEFT, RIGHT, OPEN);
    }

    @Override
    public List<TagKey<Block>> getTags() {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    static {
        CURTAIN_SHAPE = EnumProperty.create("shape", CurtainShape.class);
        FACING = HorizontalDirectionalBlock.FACING;
        LEFT = BooleanProperty.create("left");
        RIGHT = BooleanProperty.create("right");
        OPEN = BooleanProperty.create("open");

        VOXELSHAPE = Block.box(0, 0, 15, 16, 16, 16);
        VOXELSHAPE_COLLISION = Block.box(0, 13, 15, 16, 15, 16);
    }

    @Override
    public String getItemModelSuffix() {
        return "_small_single_open";
    }

    @Override
    public boolean hasSeparateModel() {
        return true;
    }
}