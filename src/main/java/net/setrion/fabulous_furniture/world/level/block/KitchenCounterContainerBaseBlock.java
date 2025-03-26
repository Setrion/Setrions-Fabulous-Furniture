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
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.setrion.fabulous_furniture.registry.SFFStats;
import net.setrion.fabulous_furniture.world.level.block.entity.KitchenStorageBaseBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class KitchenCounterContainerBaseBlock extends BaseEntityBlock implements BlockTagSupplier {

    public static final MapCodec<KitchenCounterContainerBaseBlock> CODEC = simpleCodec(KitchenCounterContainerBaseBlock::new);

    public static final EnumProperty<Direction> FACING;
    public static final BooleanProperty OPEN;
    public static final EnumProperty<DoorHingeSide> HINGE;
    protected static final VoxelShape COUNTER_NORTH;
    protected static final VoxelShape COUNTER_EAST;
    protected static final VoxelShape COUNTER_SOUTH;
    protected static final VoxelShape COUNTER_WEST;
    protected static final VoxelShape TOP;
    private boolean canBeOpened;
    private boolean hasHinge;

    protected KitchenCounterContainerBaseBlock(Properties properties) {
        this(properties, false, false);
    }

    public KitchenCounterContainerBaseBlock(Properties properties, boolean canBeOpened, boolean hasHinge) {
        super(properties);
        this.canBeOpened = canBeOpened;
        this.hasHinge = hasHinge;
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
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockState state = defaultBlockState();
        if (canBeOpened) {
            state = state.setValue(OPEN, false);
        }
        if (hasHinge) {
            state = state.setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite()).setValue(HINGE, this.getHinge(blockPlaceContext));
        } else {
            state = state.setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
        }
        return state;
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

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return rotate(blockState, mirror.getRotation(blockState.getValue(FACING)));
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState p_393681_, ServerLevel p_394632_, BlockPos p_394133_, boolean p_394282_) {
        Containers.updateNeighboursAfterDestroy(p_393681_, p_394632_, p_394133_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new KitchenStorageBaseBlockEntity(canBeOpened, blockPos, blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (level instanceof ServerLevel serverlevel) {
            if (canBeOpened && blockState.hasProperty(OPEN) && player.isShiftKeyDown()) {
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

    @Override
    public List<TagKey<Block>> getTags() {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        OPEN = BlockStateProperties.OPEN;
        HINGE = BlockStateProperties.DOOR_HINGE;

        COUNTER_NORTH = Block.box(0, 0, 2, 16, 14, 16);
        COUNTER_EAST = Block.box(0, 0, 0, 14, 14, 16);
        COUNTER_SOUTH = Block.box(0, 0, 0, 16, 14, 14);
        COUNTER_WEST = Block.box(2, 0, 0, 16, 14, 16);
        TOP = Block.box(0, 14, 0, 16, 16, 16);
    }
}