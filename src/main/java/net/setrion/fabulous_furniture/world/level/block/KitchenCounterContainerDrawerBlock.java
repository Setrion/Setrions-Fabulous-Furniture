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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.setrion.fabulous_furniture.registry.SFFStats;
import net.setrion.fabulous_furniture.world.level.block.entity.KitchenCounterBlockEntity;
import org.jetbrains.annotations.Nullable;

public class KitchenCounterContainerDrawerBlock extends KitchenCounterContainerBlock {

    public static final MapCodec<KitchenCounterContainerDrawerBlock> CODEC = simpleCodec(KitchenCounterContainerDrawerBlock::new);

    public static final EnumProperty<Direction> FACING;
    public static final BooleanProperty OPEN;
    protected static final VoxelShape COUNTER_NORTH;
    protected static final VoxelShape COUNTER_EAST;
    protected static final VoxelShape COUNTER_SOUTH;
    protected static final VoxelShape COUNTER_WEST;
    protected static final VoxelShape TOP;


    public KitchenCounterContainerDrawerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return switch (direction) {
            default:
            case NORTH: yield Shapes.or(TOP, COUNTER_NORTH);
            case EAST: yield Shapes.or(TOP, COUNTER_EAST);
            case SOUTH: yield Shapes.or(TOP, COUNTER_SOUTH);
            case WEST: yield Shapes.or(TOP, COUNTER_WEST);
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite()).setValue(OPEN, false);
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

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (level instanceof ServerLevel serverlevel) {
            if (player.isShiftKeyDown()) {
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

        COUNTER_NORTH = Block.box(0, 0, 2, 16, 14, 16);
        COUNTER_EAST = Block.box(0, 0, 0, 14, 14, 16);
        COUNTER_SOUTH = Block.box(0, 0, 0, 16, 14, 14);
        COUNTER_WEST = Block.box(2, 0, 0, 16, 14, 16);
        TOP = Block.box(0, 14, 0, 16, 16, 16);
    }
}