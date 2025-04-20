package net.setrion.fabulous_furniture.world.level.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
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
import net.setrion.fabulous_furniture.util.VoxelShapeUtils;
import net.setrion.fabulous_furniture.world.level.block.entity.BedsideTableBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BedsideTableBlock extends BaseEntityBlock implements BlockTagSupplier {

    public static final EnumProperty<Direction> FACING;
    public static final BooleanProperty OPEN;
    protected static final VoxelShape SHAPE;

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
        return switch (direction) {
            case EAST:
                yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.EAST, SHAPE);
            case SOUTH:
                yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.SOUTH, SHAPE);
            case WEST:
                yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.WEST, SHAPE);
            case NORTH: default:
                yield SHAPE;
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
    protected void affectNeighborsAfterRemoval(BlockState p_393681_, ServerLevel p_394632_, BlockPos p_394133_, boolean p_394282_) {
        Containers.updateNeighboursAfterDestroy(p_393681_, p_394632_, p_394133_);
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
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        OPEN = BlockStateProperties.OPEN;

        SHAPE = Shapes.or(Block.box(1, 0, 1, 15, 14, 16), Block.box(0, 14, 0, 16, 16, 16));
    }

    @Override
    public List<TagKey<Block>> getTags() {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }
}