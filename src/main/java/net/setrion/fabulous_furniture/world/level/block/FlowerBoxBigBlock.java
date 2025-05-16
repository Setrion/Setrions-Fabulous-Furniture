package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.setrion.fabulous_furniture.util.VoxelShapeUtils;
import net.setrion.fabulous_furniture.world.level.block.entity.FlowerBoxBlockEntity;
import org.jetbrains.annotations.Nullable;

public class FlowerBoxBigBlock extends FlowerBoxBlock {

    private static final VoxelShape VOXELSHAPE_BOTTOM;
    private static final VoxelShape VOXELSHAPE_TOP;

    public FlowerBoxBigBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        VoxelShape shape = state.getValue(HALF) == Half.BOTTOM ? VOXELSHAPE_BOTTOM : VOXELSHAPE_TOP;
        return switch (direction) {
            case EAST:
                yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.EAST, shape);
            case SOUTH:
                yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.SOUTH, shape);
            case WEST:
                yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.WEST, shape);
            case NORTH: default:
                yield shape;
        };
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FlowerBoxBlockEntity(4, blockPos, blockState);
    }

    static {
        VOXELSHAPE_BOTTOM = Block.box(0, 0, 0, 16, 6, 16);
        VOXELSHAPE_TOP = Block.box(0, 10, 0, 16, 16, 16);
    }
}