package net.setrion.fabulous_furniture.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.setrion.fabulous_furniture.FabulousFurniture;
import net.setrion.fabulous_furniture.world.item.crafting.CarpentryTableRecipe;

public class SFFRecipeTypes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, FabulousFurniture.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<CarpentryTableRecipe>> CARPENTRY_TABLE_RECIPE = RECIPE_TYPES.register("carpentry_table_recipe", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(FabulousFurniture.MOD_ID, "carpentry_table_recipe")));

}