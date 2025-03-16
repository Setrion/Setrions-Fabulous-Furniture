package net.setrion.fabulous_furniture.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

public class RecipeRunner extends RecipeProvider.Runner {

    public RecipeRunner(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        return new RecipeGenerator(provider, output);
    }

    @Override
    public String getName() {
        return "Fabulous Furniture Recipes";
    }
}