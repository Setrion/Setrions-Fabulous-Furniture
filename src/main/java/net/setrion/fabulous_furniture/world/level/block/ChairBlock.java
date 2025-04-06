package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.setrion.fabulous_furniture.util.VoxelShapeUtils;
import net.setrion.fabulous_furniture.world.level.entity.Seat;

import java.util.List;

public class ChairBlock extends Block implements BlockTagSupplier {

    public static final EnumProperty<Direction> FACING;
    protected static final VoxelShape CHAIR_SHAPE;

    public ChairBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return switch (direction) {
            case NORTH: yield CHAIR_SHAPE;
            case EAST: yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.EAST, CHAIR_SHAPE);
            case SOUTH: yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.SOUTH, CHAIR_SHAPE);
            case WEST:
            default: yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.WEST, CHAIR_SHAPE);
        };
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(Seat.sit(player, pos, 9.5*0.0625F, state.getValue(FACING))) {
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
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

    @Override
    public List<TagKey<Block>> getTags() {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;

        CHAIR_SHAPE = Shapes.or(Block.box(3, 0, 3, 5, 7, 5), Block.box(11, 0, 3, 13, 7, 5), Block.box(3, 0, 11, 5, 7, 13), Block.box(11, 0, 11, 13, 7, 13), Block.box(2, 7, 2, 14, 9, 14), Block.box(2, 9, 12, 14, 20, 14));
    }
}