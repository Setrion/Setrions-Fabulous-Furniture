package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

public class LampBlock extends Block {

    public static final BooleanProperty ON;
    public static final EnumProperty<LampPart> PART;

    VoxelShape SINGLE = Block.box(3, 0, 3, 13, 14, 13);
    VoxelShape TOP = Shapes.or(Block.box(3, 5, 3, 13, 14, 13), Block.box(7, 0, 7, 9, 5, 9));
    VoxelShape MIDDLE = Block.box(7, 0, 7, 9, 16, 9);
    VoxelShape BOTTOM = Shapes.or(Block.box(4, 0, 4, 12, 2, 12), Block.box(7, 2, 7, 9, 16, 9));

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
            case SINGLE: yield SINGLE;
            case TOP: yield TOP;
            case MIDDLE: yield MIDDLE;
            case BOTTOM: yield BOTTOM;
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
            BlockState above = level.getBlockState(pos.above());
            BlockState below = level.getBlockState(pos.below());
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
        return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (state.getValue(PART) == LampPart.SINGLE || state.getValue(PART) == LampPart.TOP) {
            level.setBlock(pos, state.cycle(ON), 3);
            return InteractionResult.SUCCESS;
        }
        //level.playSound(null, pos, SoundEvents.BARREL_CLOSE, SoundSource.BLOCKS);
        return InteractionResult.PASS;
    }

    static {
        ON = BooleanProperty.create("on");
        PART = EnumProperty.create("part", LampPart.class);
    }
}