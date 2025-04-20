package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.setrion.fabulous_furniture.world.level.block.state.properties.LampPart;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LampBlock extends Block implements BlockTagSupplier {

    public static final BooleanProperty ON;
    public static final EnumProperty<LampPart> PART;

    private static final VoxelShape VOXELSHAPE_SINGLE;
    private static final VoxelShape VOXELSHAPE_TOP;
    private static final VoxelShape VOXELSHAPE_MIDDLE;
    private static final VoxelShape VOXELSHAPE_BOTTOM;

    public LampBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(ON, false).setValue(PART, LampPart.SINGLE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ON, PART);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        LampPart direction = state.getValue(PART);
        return switch (direction) {
            case SINGLE:
                yield VOXELSHAPE_SINGLE;
            case TOP:
                yield VOXELSHAPE_TOP;
            case MIDDLE:
                yield VOXELSHAPE_MIDDLE;
            case BOTTOM:
                yield VOXELSHAPE_BOTTOM;
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = defaultBlockState();
        BlockState above = context.getLevel().getBlockState(context.getClickedPos().above());
        BlockState below = context.getLevel().getBlockState(context.getClickedPos().below());
        if (above.getBlock() == this && below.getBlock() == this) {
            return state.setValue(PART, LampPart.MIDDLE);
        } else if (above.getBlock() == this && below.getBlock() != this) {
            return state.setValue(PART, LampPart.BOTTOM);
        } else if (above.getBlock() != this && below.getBlock() == this) {
            return state.setValue(PART, LampPart.TOP);
        } else {
            return state.setValue(PART, LampPart.SINGLE);
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (direction == Direction.UP || direction == Direction.DOWN) {
            BlockState newState = state;
            BlockState above = level.getBlockState(pos.above());
            BlockState below = level.getBlockState(pos.below());
            if (above.getBlock() == this && below.getBlock() == this) {
                newState = newState.setValue(PART, LampPart.MIDDLE);
            } else if (above.getBlock() == this && below.getBlock() != this) {
                newState = newState.setValue(PART, LampPart.BOTTOM);
            } else if (above.getBlock() != this && below.getBlock() == this) {
                newState = newState.setValue(PART, LampPart.TOP);
            } else {
                newState = newState.setValue(PART, LampPart.SINGLE);
            }
            if (neighborState.hasProperty(ON)) {
                newState = newState.setValue(ON, neighborState.getValue(ON));
            }
            return newState;
        }
        return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.setBlock(pos, state.cycle(ON), 3)) {
            level.playSound(null, pos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public List<TagKey<Block>> getTags() {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    static {
        ON = BooleanProperty.create("on");
        PART = EnumProperty.create("part", LampPart.class);

        VOXELSHAPE_SINGLE = Block.box(3, 0, 3, 13, 14, 13);
        VOXELSHAPE_TOP = Shapes.or(Block.box(3, 5, 3, 13, 14, 13), Block.box(7, 0, 7, 9, 5, 9));
        VOXELSHAPE_MIDDLE = Block.box(7, 0, 7, 9, 16, 9);
        VOXELSHAPE_BOTTOM = Shapes.or(Block.box(4, 0, 4, 12, 2, 12), Block.box(7, 2, 7, 9, 16, 9));
    }
}