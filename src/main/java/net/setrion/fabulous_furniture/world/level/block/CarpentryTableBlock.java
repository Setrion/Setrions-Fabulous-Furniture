package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.fml.ModList;
import net.neoforged.fml.VersionChecker;
import net.neoforged.neoforge.network.PacketDistributor;
import net.setrion.fabulous_furniture.FabulousFurniture;
import net.setrion.fabulous_furniture.network.CarpentryRecipes;
import net.setrion.fabulous_furniture.registry.SFFRecipeTypes;
import net.setrion.fabulous_furniture.world.inventory.CarpentryTableMenu;
import net.setrion.fabulous_furniture.world.item.crafting.CarpentryTableRecipe;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CarpentryTableBlock extends Block implements BlockTagSupplier {

    private static final Component CONTAINER_TITLE = Component.translatable("container.carpentry_table");

    public CarpentryTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            if (level instanceof ServerLevel serverLevel) {
                List<RecipeHolder<CarpentryTableRecipe>> recipesForInput = new ArrayList<>();
                serverLevel.recipeAccess().getRecipes().forEach(recipe -> {
                    if (recipe.value().getType() == SFFRecipeTypes.CARPENTRY_TABLE_RECIPE.get()) {
                        recipesForInput.add((RecipeHolder<CarpentryTableRecipe>) recipe);
                    }
                });
                PacketDistributor.sendToPlayer((ServerPlayer) player, new CarpentryRecipes(recipesForInput));
                player.openMenu(state.getMenuProvider(level, pos));
                System.out.println(ModList.get().getModContainerById(FabulousFurniture.MOD_ID).get().getModInfo().getUpdateURL());
                System.out.println(VersionChecker.getResult(ModList.get().getModContainerById(FabulousFurniture.MOD_ID).get().getModInfo()));
            }
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((id, inventory, player) -> new CarpentryTableMenu(id, inventory, ContainerLevelAccess.create(level, pos)), CONTAINER_TITLE);
    }

    @Override
    public List<TagKey<Block>> getTags() {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }
}