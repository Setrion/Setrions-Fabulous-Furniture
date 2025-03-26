package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class KitchenCounterSmokerBlock extends SmokerBlock implements BlockTagSupplier {

    protected static final VoxelShape COUNTER_NORTH;
    protected static final VoxelShape COUNTER_EAST;
    protected static final VoxelShape COUNTER_SOUTH;
    protected static final VoxelShape COUNTER_WEST;
    protected static final VoxelShape TOP;

    public KitchenCounterSmokerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
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
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        if (blockState.getValue(LIT)) {
            double d0 = blockPos.getX() + 0.5;
            double d1 = blockPos.getY();
            double d2 = blockPos.getZ() + 0.5;
            if (randomSource.nextDouble() < 0.1) {
                level.playLocalSound(d0, d1, d2, SoundEvents.SMOKER_SMOKE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }
        }
    }

    @Override
    public List<TagKey<Block>> getTags() {
        return List.of(BlockTags.MINEABLE_WITH_AXE, BlockTags.MINEABLE_WITH_PICKAXE);
    }

    static {
        COUNTER_NORTH = Block.box(0, 0, 2, 16, 14, 16);
        COUNTER_EAST = Block.box(0, 0, 0, 14, 14, 16);
        COUNTER_SOUTH = Block.box(0, 0, 0, 16, 14, 14);
        COUNTER_WEST = Block.box(2, 0, 0, 16, 14, 16);
        TOP = Block.box(0, 14, 0, 16, 16, 16);
    }
}