package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Map;

public class HedgeBlock extends WallBlock implements BlockTagSupplier, ItemModelSupplier {

    private static final VoxelShape TEST_SHAPE_POST;
    private static final Map<Direction, VoxelShape> TEST_SHAPES_WALL;

    public HedgeBlock(Properties properties) {
        super(properties);
    }

    private static boolean isConnected(BlockState state, Property<WallSide> heightProperty) {
        return state.getValue(heightProperty) != WallSide.NONE;
    }

    private static boolean isCovered(VoxelShape firstShape, VoxelShape secondShape) {
        return !Shapes.joinIsNotEmpty(secondShape, firstShape, BooleanOp.ONLY_FIRST);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelReader levelreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.east();
        BlockPos blockpos3 = blockpos.south();
        BlockPos blockpos4 = blockpos.west();
        BlockPos blockpos5 = blockpos.above();
        BlockState blockstate = levelreader.getBlockState(blockpos1);
        BlockState blockstate1 = levelreader.getBlockState(blockpos2);
        BlockState blockstate2 = levelreader.getBlockState(blockpos3);
        BlockState blockstate3 = levelreader.getBlockState(blockpos4);
        BlockState blockstate4 = levelreader.getBlockState(blockpos5);
        boolean flag = this.connectsTo(blockstate, blockstate.isFaceSturdy(levelreader, blockpos1, Direction.SOUTH), Direction.SOUTH);
        boolean flag1 = this.connectsTo(blockstate1, blockstate1.isFaceSturdy(levelreader, blockpos2, Direction.WEST), Direction.WEST);
        boolean flag2 = this.connectsTo(blockstate2, blockstate2.isFaceSturdy(levelreader, blockpos3, Direction.NORTH), Direction.NORTH);
        boolean flag3 = this.connectsTo(blockstate3, blockstate3.isFaceSturdy(levelreader, blockpos4, Direction.EAST), Direction.EAST);
        BlockState blockstate5 = this.defaultBlockState().setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
        return this.updateShape(levelreader, blockstate5, blockpos5, blockstate4, flag, flag1, flag2, flag3);
    }

    private BlockState topUpdate(LevelReader level, BlockState state, BlockPos pos, BlockState secondState) {
        boolean flag = isConnected(state, NORTH);
        boolean flag1 = isConnected(state, EAST);
        boolean flag2 = isConnected(state, SOUTH);
        boolean flag3 = isConnected(state, WEST);
        return this.updateShape(level, state, pos, secondState, flag, flag1, flag2, flag3);
    }

    private BlockState sideUpdate(LevelReader level, BlockPos firstPos, BlockState firstState, BlockPos secondPos, BlockState secondState, Direction dir) {
        Direction direction = dir.getOpposite();
        boolean flag = dir == Direction.NORTH ? this.connectsTo(secondState, secondState.isFaceSturdy(level, secondPos, direction), direction) : isConnected(firstState, NORTH);
        boolean flag1 = dir == Direction.EAST ? this.connectsTo(secondState, secondState.isFaceSturdy(level, secondPos, direction), direction) : isConnected(firstState, EAST);
        boolean flag2 = dir == Direction.SOUTH ? this.connectsTo(secondState, secondState.isFaceSturdy(level, secondPos, direction), direction) : isConnected(firstState, SOUTH);
        boolean flag3 = dir == Direction.WEST ? this.connectsTo(secondState, secondState.isFaceSturdy(level, secondPos, direction), direction) : isConnected(firstState, WEST);
        BlockPos blockpos = firstPos.above();
        BlockState blockstate = level.getBlockState(blockpos);
        return this.updateShape(level, firstState, blockpos, blockstate, flag, flag1, flag2, flag3);
    }

    private boolean shouldRaisePost(BlockState state, BlockState neighbour, VoxelShape shape) {
        boolean flag = neighbour.getBlock() instanceof WallBlock && neighbour.getValue(UP);
        if (flag) {
            return true;
        } else {
            WallSide wallside = state.getValue(NORTH);
            WallSide wallside1 = state.getValue(SOUTH);
            WallSide wallside2 = state.getValue(EAST);
            WallSide wallside3 = state.getValue(WEST);
            boolean flag1 = wallside1 == WallSide.NONE;
            boolean flag2 = wallside3 == WallSide.NONE;
            boolean flag3 = wallside2 == WallSide.NONE;
            boolean flag4 = wallside == WallSide.NONE;
            boolean flag5 = flag4 && flag1 && flag2 && flag3 || flag4 != flag1 || flag2 != flag3;
            if (flag5) {
                return true;
            } else {
                boolean flag6 = wallside == WallSide.TALL && wallside1 == WallSide.TALL || wallside2 == WallSide.TALL && wallside3 == WallSide.TALL;
                return flag6 ? false : neighbour.is(BlockTags.WALL_POST_OVERRIDE) || isCovered(shape, TEST_SHAPE_POST);
            }
        }
    }

    private BlockState updateSides(BlockState state, boolean northConnection, boolean eastConnection, boolean southConnection, boolean westConnection, VoxelShape wallShape) {
        return state.setValue(NORTH, this.makeWallState(northConnection, wallShape, TEST_SHAPES_WALL.get(Direction.NORTH))).setValue(EAST, this.makeWallState(eastConnection, wallShape, TEST_SHAPES_WALL.get(Direction.EAST))).setValue(SOUTH, this.makeWallState(southConnection, wallShape, TEST_SHAPES_WALL.get(Direction.SOUTH))).setValue(WEST, this.makeWallState(westConnection, wallShape, TEST_SHAPES_WALL.get(Direction.WEST)));
    }

    private WallSide makeWallState(boolean allowConnection, VoxelShape shape, VoxelShape neighbourShape) {
        if (allowConnection) {
            return isCovered(shape, neighbourShape) ? WallSide.TALL : WallSide.LOW;
        } else {
            return WallSide.NONE;
        }
    }

    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if ((Boolean)state.getValue(WATERLOGGED)) {
            tickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        if (direction == Direction.DOWN) {
            return super.updateShape(state, level, tickAccess, pos, direction, neighborPos, neighborState, random);
        } else {
            return direction == Direction.UP ? this.topUpdate(level, state, neighborPos, neighborState) : this.sideUpdate(level, pos, state, neighborPos, neighborState, direction);
        }
    }

    private BlockState updateShape(LevelReader level, BlockState state, BlockPos pos, BlockState neighbour, boolean northConnection, boolean eastConnection, boolean southConnection, boolean westConnection) {
        VoxelShape voxelshape = neighbour.getCollisionShape(level, pos).getFaceShape(Direction.DOWN);
        BlockState blockstate = this.updateSides(state, northConnection, eastConnection, southConnection, westConnection, voxelshape);
        return blockstate.setValue(UP, this.shouldRaisePost(blockstate, neighbour, voxelshape));
    }

    private boolean connectsTo(BlockState state, boolean sideSolid, Direction direction) {
        Block block = state.getBlock();
        boolean flag = block instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(state, direction);
        return state.is(BlockTags.WALLS) || state.getBlock() instanceof HedgeBlock || state.getBlock() instanceof LeavesBlock || !isExceptionForConnection(state) && sideSolid || block instanceof IronBarsBlock || flag;
    }

    @Override
    public List<TagKey<Block>> getTags() {
        return List.of(BlockTags.MINEABLE_WITH_HOE);
    }

    static {
        TEST_SHAPE_POST = Block.column(2.0F, 0.0F, 16.0F);
        TEST_SHAPES_WALL = Shapes.rotateHorizontal(Block.boxZ(2.0F, 16.0F, 0.0F, 9.0F));
    }

    @Override
    public String getItemModelSuffix() {
        return "";
    }

    @Override
    public boolean hasSeparateModel() {
        return false;
    }
}