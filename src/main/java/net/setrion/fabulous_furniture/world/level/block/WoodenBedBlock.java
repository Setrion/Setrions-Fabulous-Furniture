package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.setrion.fabulous_furniture.world.level.block.state.properties.BedShape;

import javax.annotation.Nullable;
import java.util.List;

public class WoodenBedBlock extends Block {

    public static final EnumProperty<BedPart> PART;
    public static final EnumProperty<BedShape> SHAPE;
    public static final EnumProperty<Direction> FACING;
    public static final BooleanProperty OCCUPIED;

    public WoodenBedBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, BedPart.FOOT).setValue(OCCUPIED, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART, SHAPE, OCCUPIED);
    }

    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult result) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS_SERVER;
        } else {
            if (state.getValue(PART) != BedPart.HEAD) {
                pos = pos.relative(state.getValue(FACING));
                state = level.getBlockState(pos);
                if (!state.is(this)) {
                    return InteractionResult.CONSUME;
                }
            }

            if (!canSetSpawn(level)) {
                level.removeBlock(pos, false);
                BlockPos blockpos = pos.relative(state.getValue(FACING).getOpposite());
                if (level.getBlockState(blockpos).is(this)) {
                    level.removeBlock(blockpos, false);
                }

                Vec3 vec3 = pos.getCenter();
                level.explode(null, level.damageSources().badRespawnPointExplosion(vec3), null, vec3, 5.0F, true, Level.ExplosionInteraction.BLOCK);
                return InteractionResult.SUCCESS_SERVER;
            } else if (state.getValue(OCCUPIED)) {
                if (!this.kickVillagerOutOfBed(level, pos)) {
                    player.displayClientMessage(Component.translatable("block.minecraft.bed.occupied"), true);
                }

                return InteractionResult.SUCCESS_SERVER;
            } else {
                player.startSleepInBed(pos).ifLeft((problem) -> {
                    if (problem.getMessage() != null) {
                        player.displayClientMessage(problem.getMessage(), true);
                    }
                });
                return InteractionResult.SUCCESS_SERVER;
            }
        }
    }

    @Override
    public boolean isBed(BlockState state, BlockGetter level, BlockPos pos, LivingEntity sleeper) {
        return true;
    }

    public static boolean canSetSpawn(Level level) {
        return level.dimensionType().bedWorks();
    }

    private boolean kickVillagerOutOfBed(Level level, BlockPos pos) {
        List<Villager> list = level.getEntitiesOfClass(Villager.class, new AABB(pos), LivingEntity::isSleeping);
        if (list.isEmpty()) {
            return false;
        } else {
            list.get(0).stopSleeping();
            return true;
        }
    }

    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float amount) {
        super.fallOn(level, state, pos, entity, amount * 0.5F);
    }

    public void updateEntityMovementAfterFallOn(BlockGetter getter, Entity entity) {
        if (entity.isSuppressingBounce()) {
            super.updateEntityMovementAfterFallOn(getter, entity);
        } else {
            this.bounceUp(entity);
        }

    }

    private void bounceUp(Entity entity) {
        Vec3 vec3 = entity.getDeltaMovement();
        if (vec3.y < 0.0) {
            double d0 = entity instanceof LivingEntity ? 1.0 : 0.8;
            entity.setDeltaMovement(vec3.x, -vec3.y * 0.6600000262260437 * d0, vec3.z);
        }
    }

    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        if (direction != getNeighbourDirection(state.getValue(PART), state.getValue(FACING))) {
            return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighbourPos, neighbourState, random);
        } else {
            return neighbourState.is(this) && neighbourState.getValue(PART) != state.getValue(PART) ? state.setValue(OCCUPIED, neighbourState.getValue(OCCUPIED)) : Blocks.AIR.defaultBlockState();
        }
    }

    private static Direction getNeighbourDirection(BedPart part, Direction direction) {
        return part == BedPart.FOOT ? direction : direction.getOpposite();
    }

    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative()) {
            BedPart bedpart = state.getValue(PART);
            if (bedpart == BedPart.FOOT) {
                BlockPos blockpos = pos.relative(getNeighbourDirection(bedpart, state.getValue(FACING)));
                BlockState blockstate = level.getBlockState(blockpos);
                if (blockstate.is(this) && blockstate.getValue(PART) == BedPart.HEAD) {
                    level.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                    level.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
                }
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection();
        BlockPos blockpos = context.getClickedPos();
        BlockPos blockpos1 = blockpos.relative(direction);
        Level level = context.getLevel();
        BlockState state = defaultBlockState().setValue(FACING, direction);
        BlockState leftState = level.getBlockState(blockpos.relative(direction.getClockWise()));
        BlockState rightState = level.getBlockState(blockpos.relative(direction.getCounterClockWise()));
        if (leftState.is(this) && leftState.getValue(PART) == defaultBlockState().getValue(PART) && rightState.is(this) && rightState.getValue(PART) == defaultBlockState().getValue(PART)) {
            state = state.setValue(SHAPE, BedShape.MIDDLE);
        } else if (leftState.is(this) && leftState.getValue(PART) == defaultBlockState().getValue(PART)) {
            state = state.setValue(SHAPE, BedShape.LEFT);
        } else if (rightState.is(this) && rightState.getValue(PART) == defaultBlockState().getValue(PART)) {
            state = state.setValue(SHAPE, BedShape.RIGHT);
        } else {
            state = state.setValue(SHAPE, BedShape.SINGLE);
        }
        return level.getBlockState(blockpos1).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(blockpos1) ? state : null;
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = getConnectedDirection(state).getOpposite();
        return switch (direction) {
            case NORTH -> Block.box(0, 4, 0, 16, 8, 16);
            case SOUTH -> Block.box(0, 4, 0, 16, 8, 16);
            case WEST -> Block.box(0, 4, 0, 16, 8, 16);
            default -> Block.box(0, 4, 0, 16, 8, 16);
        };
    }

    public static Direction getConnectedDirection(BlockState state) {
        Direction direction = state.getValue(FACING);
        return state.getValue(PART) == BedPart.HEAD ? direction.getOpposite() : direction;
    }

    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide) {
            BlockPos blockpos = pos.relative(state.getValue(FACING));
            level.setBlock(blockpos, state.setValue(PART, BedPart.HEAD), 3);
            level.blockUpdated(pos, Blocks.AIR);
            state.updateNeighbourShapes(level, pos, 3);
        }
    }

    protected long getSeed(BlockState state, BlockPos pos) {
        BlockPos blockpos = pos.relative(state.getValue(FACING), state.getValue(PART) == BedPart.HEAD ? 0 : 1);
        return Mth.getSeed(blockpos.getX(), pos.getY(), blockpos.getZ());
    }

    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    static {
        PART = BlockStateProperties.BED_PART;
        SHAPE = EnumProperty.create("shape", BedShape.class);
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        OCCUPIED = BedBlock.OCCUPIED;
    }
}