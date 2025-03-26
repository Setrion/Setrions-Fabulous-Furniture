package net.setrion.fabulous_furniture.world.level.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.setrion.fabulous_furniture.registry.SFFBlocks;
import net.setrion.fabulous_furniture.registry.SFFStats;
import net.setrion.fabulous_furniture.registry.SFFTags;
import net.setrion.fabulous_furniture.world.level.block.entity.KitchenFridgeBlockEntity;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiConsumer;

public class FridgeBlock extends BaseEntityBlock implements BlockTagSupplier {

    public static final MapCodec<FridgeBlock> CODEC = simpleCodec(FridgeBlock::new);

    public static final EnumProperty<Direction> FACING;
    public static final BooleanProperty OPEN;
    public static final EnumProperty<DoorHingeSide> HINGE;
    public static final EnumProperty<DoubleBlockHalf> HALF;

    protected static final VoxelShape FRIDGE_NORTH;
    protected static final VoxelShape FRIDGE_EAST;
    protected static final VoxelShape FRIDGE_SOUTH;
    protected static final VoxelShape FRIDGE_WEST;

    public FridgeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false));
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return switch (direction) {
            default:
            case NORTH: yield FRIDGE_NORTH;
            case EAST: yield FRIDGE_EAST;
            case SOUTH: yield FRIDGE_SOUTH;
            case WEST: yield FRIDGE_WEST;
        };
    }

    protected BlockState updateShape(BlockState blockState, LevelReader p_374501_, ScheduledTickAccess p_374380_, BlockPos p_52800_, Direction p_52797_, BlockPos p_52801_, BlockState p_52798_, RandomSource p_374395_) {
        DoubleBlockHalf doubleblockhalf = blockState.getValue(HALF);
        if (p_52797_.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (p_52797_ == Direction.UP)) {
            return p_52798_.getBlock() instanceof FridgeBlock && p_52798_.getValue(HALF) != doubleblockhalf ? p_52798_.setValue(HALF, doubleblockhalf) : Blocks.AIR.defaultBlockState();
        } else {
            return doubleblockhalf == DoubleBlockHalf.LOWER && p_52797_ == Direction.DOWN && !blockState.canSurvive(p_374501_, p_52800_) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, p_374501_, p_374380_, p_52800_, p_52797_, p_52801_, p_52798_, p_374395_);
        }
    }

    protected void onExplosionHit(BlockState blockState, ServerLevel level, BlockPos blockPos, Explosion explosion, BiConsumer<ItemStack, BlockPos> consumer) {
        if (explosion.canTriggerBlocks() && blockState.getValue(HALF) == DoubleBlockHalf.LOWER) {
            this.setOpen(null, level, blockState, blockPos, !this.isOpen(blockState));
        }

        super.onExplosionHit(blockState, level, blockPos, explosion, consumer);
    }

    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = level.getBlockState(blockpos);
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(level, blockpos, Direction.UP) : blockstate.is(this);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();
        if (blockpos.getY() < level.getMaxY() && level.getBlockState(blockpos.above()).canBeReplaced(context)) {
            boolean flag = level.hasNeighborSignal(blockpos) || level.hasNeighborSignal(blockpos.above());
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(HINGE, this.getHinge(context)).setValue(OPEN, flag).setValue(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    private DoorHingeSide getHinge(BlockPlaceContext context) {
        BlockGetter blockgetter = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction direction = context.getHorizontalDirection();
        BlockPos blockpos1 = blockpos.above();
        Direction direction1 = direction.getCounterClockWise();
        BlockPos blockpos2 = blockpos.relative(direction1);
        BlockState blockstate = blockgetter.getBlockState(blockpos2);
        BlockPos blockpos3 = blockpos1.relative(direction1);
        BlockState blockstate1 = blockgetter.getBlockState(blockpos3);
        Direction direction2 = direction.getClockWise();
        BlockPos blockpos4 = blockpos.relative(direction2);
        BlockState blockstate2 = blockgetter.getBlockState(blockpos4);
        BlockPos blockpos5 = blockpos1.relative(direction2);
        BlockState blockstate3 = blockgetter.getBlockState(blockpos5);
        int i = (blockstate.isCollisionShapeFullBlock(blockgetter, blockpos2) ? -1 : 0) + (blockstate1.isCollisionShapeFullBlock(blockgetter, blockpos3) ? -1 : 0) + (blockstate2.isCollisionShapeFullBlock(blockgetter, blockpos4) ? 1 : 0) + (blockstate3.isCollisionShapeFullBlock(blockgetter, blockpos5) ? 1 : 0);
        boolean flag = blockstate.getBlock() instanceof KitchenCounterContainerBaseBlock;
        boolean flag1 = blockstate2.getBlock() instanceof KitchenCounterContainerBaseBlock;
        if ((!flag || flag1) && i <= 0) {
            if ((!flag1 || flag) && i == 0) {
                int j = direction.getStepX();
                int k = direction.getStepZ();
                Vec3 vec3 = context.getClickLocation();
                double d0 = vec3.x - (double)blockpos.getX();
                double d1 = vec3.z - (double)blockpos.getZ();
                return j < 0 && d1 < 0.5 || j > 0 && d1 > 0.5 || k < 0 && d0 > 0.5 || k > 0 && d0 < 0.5 ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT;
            } else {
                return DoorHingeSide.LEFT;
            }
        } else {
            return DoorHingeSide.RIGHT;
        }
    }

    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, FACING, OPEN, HINGE);
    }

    private void playSound(@Nullable Entity source, BlockState state, Level level, BlockPos pos, boolean isOpening) {
        SoundEvent open = state.is(SFFTags.Blocks.COPPER_FRIDGES) ? SoundEvents.COPPER_DOOR_OPEN : SoundEvents.IRON_DOOR_OPEN;
        SoundEvent close = state.is(SFFTags.Blocks.COPPER_FRIDGES) ? SoundEvents.COPPER_DOOR_CLOSE : SoundEvents.IRON_DOOR_CLOSE;
        level.playSound(source, pos, isOpening ? open : close, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (level instanceof ServerLevel serverlevel) {
            if (player.isShiftKeyDown()) {
                blockState = blockState.cycle(OPEN);
                System.out.println(SFFBlocks.BLOCKS.getEntries().size());
                level.setBlock(blockPos, blockState, 10);
                this.playSound(null, blockState, level, blockPos, blockState.getValue(OPEN));
                level.gameEvent(player, this.isOpen(blockState) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, blockPos);
                return InteractionResult.SUCCESS;
            } else {
                if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
                    blockPos = blockPos.below();
                }
                MenuProvider menuprovider = this.getMenuProvider(blockState, level, blockPos);
                if (menuprovider != null) {
                    player.openMenu(menuprovider);
                    player.awardStat(Stats.CUSTOM.get(SFFStats.OPEN_FRIDGE.get()));
                    PiglinAi.angerNearbyPiglins(serverlevel, player, true);
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    public boolean isOpen(BlockState state) {
        return state.getValue(OPEN);
    }

    public void setOpen(@Nullable Entity entity, Level level, BlockState state, BlockPos pos, boolean open) {
        if (state.is(this) && state.getValue(OPEN) != open) {
            level.setBlock(pos, state.setValue(OPEN, open), 10);
            this.playSound(entity, state, level, pos, open);
            level.gameEvent(entity, open ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        }
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new KitchenFridgeBlockEntity(blockPos, blockState);
    }

    @Override
    public List<TagKey<Block>> getTags() {
        return List.of(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_IRON_TOOL);
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        OPEN = BlockStateProperties.OPEN;
        HINGE = BlockStateProperties.DOOR_HINGE;
        HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
        FRIDGE_NORTH = Block.box(0, 0, 2, 16, 16, 16);
        FRIDGE_EAST = Block.box(0, 0, 0, 14, 16, 16);
        FRIDGE_SOUTH = Block.box(0, 0, 0, 16, 16, 14);
        FRIDGE_WEST = Block.box(2, 0, 0, 16, 16, 16);
    }
}