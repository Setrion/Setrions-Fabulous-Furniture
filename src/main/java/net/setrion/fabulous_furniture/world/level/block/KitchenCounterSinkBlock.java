package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.setrion.fabulous_furniture.registry.SFFStats;
import net.setrion.fabulous_furniture.util.VoxelShapeUtils;

import java.util.List;

public class KitchenCounterSinkBlock extends Block implements BlockTagSupplier {

    public static final EnumProperty<Direction> FACING;
    public static final IntegerProperty LEVEL;
    public static final BooleanProperty ON;
    protected static final VoxelShape VOXELSHAPE;


    public KitchenCounterSinkBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LEVEL, 0).setValue(ON, false));
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return switch (direction) {
            case EAST:
                yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.EAST, VOXELSHAPE);
            case SOUTH:
                yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.SOUTH, VOXELSHAPE);
            case WEST:
                yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.WEST, VOXELSHAPE);
            case NORTH: default:
                yield VOXELSHAPE;
        };
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(ON)) {
            Direction dir = state.getValue(FACING).getOpposite();
            double d0 = (double) pos.getX() + 0.5;
            double d1 = (double) pos.getY() + 1;
            double d2 = (double) pos.getZ() + 0.5;
            level.addParticle(ParticleTypes.FALLING_WATER, d0 + 0.175 * (double) dir.getStepX(), d1 + 0.22, d2 + 0.175 * (double) dir.getStepZ(), 0.0, 0.0, 0.0);
        }
        super.animateTick(state, level, pos, random);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(ON)) {
            if (random.nextInt(2) == 0) {
                int fill = state.getValue(LEVEL);
                if (fill < 3) {
                    level.setBlock(pos, state.setValue(LEVEL, fill + 1), 3);
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LEVEL, ON);
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
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level instanceof ServerLevel) {
            if (state.getValue(ON)) {
                level.setBlock(pos, state.setValue(ON, false), 3);
            } else {
                level.setBlock(pos, state.setValue(ON, true), 3);
            }
            player.awardStat(Stats.CUSTOM.get(SFFStats.ACTIVATE_KITCHEN_SINK.get()));
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level instanceof ServerLevel) {
            if (stack.getItem() == Items.BUCKET) {
                if (state.getValue(LEVEL) == 3) {
                    player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
                    level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS);
                    level.setBlock(pos, state.setValue(LEVEL, 0), 3);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public List<TagKey<Block>> getTags() {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        LEVEL = IntegerProperty.create("level", 0, 3);
        ON = BooleanProperty.create("on");

        VOXELSHAPE = Shapes.or(Block.box(0, 0, 2, 16, 14, 16), Block.box(0, 14, 0, 16, 16, 16));
    }
}