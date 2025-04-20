package net.setrion.fabulous_furniture.world.level.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.setrion.fabulous_furniture.registry.SFFStats;
import net.setrion.fabulous_furniture.registry.SFFTags;
import net.setrion.fabulous_furniture.util.VoxelShapeUtils;
import net.setrion.fabulous_furniture.world.level.block.entity.FlowerBoxBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FlowerBoxBlock extends BaseEntityBlock implements BlockTagSupplier, ItemModelSupplier {

    public static final MapCodec<FlowerBoxBlock> CODEC = simpleCodec(FlowerBoxBlock::new);

    private static final VoxelShape VOXELSHAPE_BOTTOM;
    private static final VoxelShape VOXELSHAPE_TOP;

    public static final EnumProperty<Direction> FACING;
    public static final EnumProperty<Half> HALF;

    public FlowerBoxBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING).getOpposite();
        VoxelShape shape = state.getValue(HALF) == Half.BOTTOM ? VOXELSHAPE_BOTTOM : VOXELSHAPE_TOP;
        return switch (direction) {
            case EAST:
                yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.EAST, shape);
            case SOUTH:
                yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.SOUTH, shape);
            case WEST:
                yield VoxelShapeUtils.rotateShapeAroundY(Direction.NORTH, Direction.WEST, shape);
            case NORTH: default:
                yield shape;
        };
    }

    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        BlockEntity be = level.getBlockEntity(pos);
        int slot = getClickedSlot(result, pos, state.getValue(FACING));
        if (be instanceof FlowerBoxBlockEntity flowerBox && result.getDirection() == Direction.UP) {
            ItemStack itemstack = player.getItemInHand(hand);
            if (level instanceof ServerLevel serverlevel) {
                if (!itemstack.isEmpty()) {
                    if (itemstack.getItem() instanceof BlockItem blockItem) {
                        if (blockItem.getBlock().defaultBlockState().is(SFFTags.Blocks.FLOWER_BOX_PLACABLES)) {
                            if (flowerBox.placeFlower(serverlevel, player, slot, itemstack)) {
                                player.awardStat(SFFStats.INTERACT_WITH_FLOWER_BOX.get());
                                return InteractionResult.SUCCESS_SERVER;
                            }
                        }
                    }
                } else {
                    return InteractionResult.TRY_WITH_EMPTY_HAND;
                }
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    public static int getClickedSlot(BlockHitResult hit, BlockPos blockPos, Direction facing) {
        Vec3 hitPos = hit.getLocation();
        double localX = hitPos.x - blockPos.getX();
        double localZ = hitPos.z - blockPos.getZ();

        double rotatedX, rotatedZ;

        switch (facing) {
            case SOUTH -> {
                rotatedX = 1.0 - localX;
                rotatedZ = 1.0 - localZ;
            }
            case WEST -> {
                rotatedX = 1.0 - localZ;
                rotatedZ = localX;
            }
            case EAST -> {
                rotatedX = localZ;
                rotatedZ = 1.0 - localX;
            }
            default -> {
                rotatedX = localX;
                rotatedZ = localZ;
            }
        }

        if (rotatedZ < 0.5) {
            if (rotatedX < 0.5) {
                return 2;
            } else {
                return 3;
            }
        } else {
            if (rotatedX < 0.5) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult result) {
        BlockEntity be = level.getBlockEntity(pos);
        int slot = getClickedSlot(result, pos, state.getValue(FACING));
        if (be instanceof FlowerBoxBlockEntity flowerBox && result.getDirection() == Direction.UP) {
            if (level instanceof ServerLevel serverlevel) {
                ItemStack flower = flowerBox.takeFlower(serverlevel, slot, player);
                if (!flower.isEmpty()) {
                    if (!player.addItem(flower)) {
                        player.drop(flower, false);
                    }
                    player.awardStat(SFFStats.INTERACT_WITH_FLOWER_BOX.get());
                    return InteractionResult.SUCCESS_SERVER;
                }
            }
        }
        return super.useWithoutItem(state, level, pos, player, result);
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean moved) {
        Containers.updateNeighboursAfterDestroy(state, level, pos);
    }

    @javax.annotation.Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(HALF, context.getClickLocation().y - (double)blockpos.getY() > (double)0.5F ? Half.TOP : Half.BOTTOM);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FlowerBoxBlockEntity(2, blockPos, blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF);
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        HALF = BlockStateProperties.HALF;
        VOXELSHAPE_BOTTOM = Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 8.0);
        VOXELSHAPE_TOP = Block.box(0.0, 10.0, 0.0, 16.0, 16.0, 8.0);
    }

    @Override
    public List<TagKey<Block>> getTags() {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    public String getItemModelSuffix() {
        return "_bottom";
    }

    @Override
    public boolean hasSeparateModel() {
        return true;
    }
}
