package net.setrion.fabulous_furniture.world.level.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.setrion.fabulous_furniture.registry.SFFStats;
import net.setrion.fabulous_furniture.world.level.block.entity.KitchenCounterBlockEntity;
import org.jetbrains.annotations.Nullable;

public class KitchenCabinetContainerBlock extends BaseEntityBlock {

    public static final MapCodec<KitchenCabinetContainerBlock> CODEC = simpleCodec(KitchenCabinetContainerBlock::new);

    public static final EnumProperty<Direction> FACING;
    protected static final VoxelShape COUNTER_NORTH;
    protected static final VoxelShape COUNTER_EAST;
    protected static final VoxelShape COUNTER_SOUTH;
    protected static final VoxelShape COUNTER_WEST;


    public KitchenCabinetContainerBlock(Properties properties) {
        super(properties);
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

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new KitchenCounterBlockEntity(blockPos, blockState);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        Containers.dropContentsOnDestroy(state, newState, level, pos);
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (level instanceof ServerLevel serverlevel) {
            MenuProvider menuprovider = this.getMenuProvider(blockState, level, blockPos);
            if (menuprovider != null) {
                player.openMenu(menuprovider);
                player.awardStat(Stats.CUSTOM.get(SFFStats.OPEN_KITCHEN_COUNTER.get()));
                PiglinAi.angerNearbyPiglins(serverlevel, player, true);
            }
        }

        return InteractionResult.SUCCESS;
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;

        COUNTER_NORTH = Shapes.or(Block.box(0, 0, 6, 16, 2, 16), Block.box(0, 2, 4, 16, 4, 16), Block.box(0, 4, 2, 16, 14, 16));
        COUNTER_EAST = Shapes.or(Block.box(0, 0, 0, 10, 2, 16), Block.box(0, 2, 0, 12, 4, 16), Block.box(0, 4, 0, 14, 14, 16));
        COUNTER_SOUTH = Shapes.or(Block.box(0, 0, 0, 16, 2, 10), Block.box(0, 2, 0, 16, 4, 12), Block.box(0, 4, 0, 16, 14, 14));
        COUNTER_WEST = Shapes.or(Block.box(6, 0, 0, 16, 2, 16), Block.box(4, 2, 0, 16, 4, 16), Block.box(2, 4, 0, 16, 14, 16));
    }
}