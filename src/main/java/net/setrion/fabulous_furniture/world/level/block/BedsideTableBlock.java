package net.setrion.fabulous_furniture.world.level.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.setrion.fabulous_furniture.registry.SFFStats;
import net.setrion.fabulous_furniture.util.VoxelShapeUtils;
import net.setrion.fabulous_furniture.world.level.block.entity.BedsideTableBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class BedsideTableBlock extends BaseEntityBlock {

    public static final EnumProperty<Direction> FACING;
    public static final BooleanProperty OPEN;
    protected static final VoxelShape SHAPE_CLOSE;
    protected static final VoxelShape SHAPE_OPEN;

    public static final MapCodec<BedsideTableBlock> CODEC = simpleCodec(BedsideTableBlock::new);

    public BedsideTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BedsideTableBlockEntity(blockPos, blockState);
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        VoxelShape shape = SHAPE_CLOSE;
        if (state.getValue(OPEN)) {
            shape = SHAPE_OPEN;
        }
        return switch (direction) {
            default:
            case NORTH: yield shape;
            case EAST: yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.EAST, shape);
            case SOUTH: yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.SOUTH, shape);
            case WEST: yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.WEST, shape);
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite()).setValue(OPEN, false);
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        Containers.dropContentsOnDestroy(state, newState, level, pos);
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (level instanceof ServerLevel serverlevel) {
            if (blockState.hasProperty(OPEN) && player.isShiftKeyDown()) {
                if (blockState.getValue(OPEN)) {
                    level.setBlock(blockPos, blockState.setValue(OPEN, !blockState.getValue(OPEN)), 3);
                    level.playSound(null, blockPos, SoundEvents.BARREL_CLOSE, SoundSource.BLOCKS);
                } else {
                    level.setBlock(blockPos, blockState.setValue(OPEN, !blockState.getValue(OPEN)), 3);
                    level.playSound(null, blockPos, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS);
                }
            } else {
                MenuProvider menuprovider = this.getMenuProvider(blockState, level, blockPos);
                if (menuprovider != null) {
                    player.openMenu(menuprovider);
                    player.awardStat(Stats.CUSTOM.get(SFFStats.OPEN_KITCHEN_COUNTER.get()));
                    PiglinAi.angerNearbyPiglins(serverlevel, player, true);
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        OPEN = BlockStateProperties.OPEN;

        SHAPE_CLOSE = Stream.of(
                Block.box(1, 0, 2, 15, 14, 16),
                Block.box(0, 14, 0, 16, 16, 16),
                Block.box(6, 3.5, -1, 10, 4.5, 0),
                Block.box(1.5, 1.5, 0, 14.5, 6.5, 1),
                Block.box(6, 9.5, -1, 10, 10.5, 0),
                Block.box(1.5, 7.5, 0, 14.5, 12.5, 1),
                Block.box(1, 0, 1, 2, 14, 2),
                Block.box(14, 0, 1, 15, 14, 2),
                Block.box(2, 2, 1, 14, 14, 2)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        SHAPE_OPEN = Stream.of(
                Block.box(0, 14, 0, 16, 16, 16),
                Block.box(1, 0, 1, 2, 14, 16),
                Block.box(14, 0, 1, 15, 14, 16),
                Block.box(2, 0, 2, 14, 2, 15),
                Block.box(2, 0, 15, 14, 14, 16),
                Block.box(2, 6, 1, 14, 8, 15),
                Block.box(2, 12, 1, 14, 14, 15),
                Stream.of(
                        Block.box(6, 9.5, -9, 10, 10.5, -8),
                        Block.box(1.5, 7.5, -8, 14.5, 12.5, -7),
                        Block.box(2, 8, -7, 14, 8.25, 6),
                        Block.box(2, 8.25, -7, 2.25, 11.25, 6),
                        Block.box(13.75, 8.25, -7, 14, 11.25, 6),
                        Block.box(2.25, 8.25, 5.75, 13.75, 11.25, 6)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                Stream.of(
                        Block.box(6, 3.5, -4, 10, 4.5, -3),
                        Block.box(1.5, 1.5, -3, 14.5, 6.5, -2),
                        Block.box(2, 2, -2, 14, 2.25, 11),
                        Block.box(2, 2.25, -2, 2.25, 5.25, 11),
                        Block.box(13.75, 2.25, -2, 14, 5.25, 11),
                        Block.box(2.25, 2.25, 10.75, 13.75, 5.25, 11)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    }
}