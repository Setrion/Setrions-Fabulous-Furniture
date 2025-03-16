package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.network.PacketDistributor;
import net.setrion.fabulous_furniture.network.CarpentryRecipes;
import net.setrion.fabulous_furniture.registry.SFFRecipeTypes;
import net.setrion.fabulous_furniture.world.inventory.CarpentryTableMenu;
import net.setrion.fabulous_furniture.world.item.crafting.CarpentryTableRecipe;
import net.setrion.fabulous_furniture.world.level.entity.Seat;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ChairBlock extends Block {

    public static final EnumProperty<Direction> FACING;
    protected static final VoxelShape CHAIR_BASE;
    protected static final VoxelShape CHAIR_NORTH;
    protected static final VoxelShape CHAIR_EAST;
    protected static final VoxelShape CHAIR_SOUTH;
    protected static final VoxelShape CHAIR_WEST;

    private static final Component CONTAINER_TITLE = Component.literal("Carpentry");

    public ChairBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return switch (direction) {
            default:
            case NORTH: yield Shapes.or(CHAIR_BASE, CHAIR_NORTH);
            case EAST: yield Shapes.or(CHAIR_BASE, CHAIR_EAST);
            case SOUTH: yield Shapes.or(CHAIR_BASE, CHAIR_SOUTH);
            case WEST: yield Shapes.or(CHAIR_BASE, CHAIR_WEST);
        };
    }

    /*@Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(Seat.sit(player, pos, 9.5*0.0625F, state.getValue(FACING))) {
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }*/

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            if (level instanceof ServerLevel serverLevel) {
                List<RecipeHolder<CarpentryTableRecipe>> recipesForInput = new ArrayList<>();
                serverLevel.recipeAccess().getRecipes().stream().forEach(recipe -> {
                    if (recipe.value().getType() == SFFRecipeTypes.CARPENTRY_TABLE_RECIPE.get()) {
                        recipesForInput.add((RecipeHolder<CarpentryTableRecipe>) recipe);
                    }
                });
                PacketDistributor.sendToPlayer((ServerPlayer) player, new CarpentryRecipes(recipesForInput));
                player.openMenu(state.getMenuProvider(level, pos));
            }
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((id, inventory, player) -> new CarpentryTableMenu(id, inventory, ContainerLevelAccess.create(level, pos)), CONTAINER_TITLE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
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

    static {
        FACING = HorizontalDirectionalBlock.FACING;

        CHAIR_BASE = Shapes.or(Block.box(3, 0, 3, 5, 7, 5), Block.box(11, 0, 3, 13, 7, 5), Block.box(3, 0, 11, 5, 7, 13), Block.box(11, 0, 11, 13, 7, 13), Block.box(2, 7, 2, 14, 9, 14));
        CHAIR_NORTH = Block.box(2, 9, 12, 14, 20, 14);
        CHAIR_EAST = Block.box(2, 9, 2, 4, 20, 14);
        CHAIR_SOUTH = Block.box(2, 9, 2, 14, 20, 4);
        CHAIR_WEST = Block.box(12, 9, 2, 14, 20, 14);
    }
}