package net.setrion.fabulous_furniture.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.setrion.fabulous_furniture.FabulousFurniture;
import net.setrion.fabulous_furniture.world.item.crafting.CarpentryTableRecipe;

public class SFFRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, FabulousFurniture.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CarpentryTableRecipe>> CARPENTRY_TABLE_RECIPE = RECIPE_SERIALIZERS.register("carpentry_table", CarpentryTableRecipe.Serializer::new);

}