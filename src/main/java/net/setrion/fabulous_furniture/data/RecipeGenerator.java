package net.setrion.fabulous_furniture.data;

import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.setrion.fabulous_furniture.FabulousFurniture;
import net.setrion.fabulous_furniture.registry.SFFBlocks;
import net.setrion.fabulous_furniture.world.item.crafting.CarpentryTableRecipe;
import net.setrion.fabulous_furniture.world.level.block.state.properties.FurnitureCategory;
import net.setrion.fabulous_furniture.world.level.block.state.properties.MaterialType;

import javax.annotation.Nullable;

import static net.minecraft.world.level.block.Blocks.*;
import static net.setrion.fabulous_furniture.registry.SFFBlocks.*;

public class RecipeGenerator extends RecipeProvider {
    private final HolderLookup.RegistryLookup<Item> items;

    public RecipeGenerator(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
        this.items = provider.lookupOrThrow(Registries.ITEM);
    }

    @Override
    public void buildRecipes() {
        ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, CARPENTRY_TABLE.get()).define('#', ItemTags.PLANKS).define('X', Items.COPPER_INGOT).pattern("XX").pattern("##").pattern("##").unlockedBy("has_copper", this.has(Items.COPPER_INGOT)).save(this.output);
        createFridgeRecipes();
        createCurtainRecipes();
        createKitchenTileRecipes();
        createWoodenFurnitureRecipes(WoodType.OAK, MaterialType.OAK);
        createWoodenFurnitureRecipes(WoodType.SPRUCE, MaterialType.SPRUCE);
        createWoodenFurnitureRecipes(WoodType.BIRCH, MaterialType.BIRCH);
        createWoodenFurnitureRecipes(WoodType.JUNGLE, MaterialType.JUNGLE);
        createWoodenFurnitureRecipes(WoodType.ACACIA, MaterialType.ACACIA);
        createWoodenFurnitureRecipes(WoodType.DARK_OAK, MaterialType.DARK_OAK);
        createWoodenFurnitureRecipes(WoodType.PALE_OAK, MaterialType.PALE_OAK);
        createWoodenFurnitureRecipes(WoodType.CHERRY, MaterialType.CHERRY);
        createWoodenFurnitureRecipes(WoodType.MANGROVE, MaterialType.MANGROVE);
        createWoodenFurnitureRecipes(WoodType.BAMBOO, MaterialType.BAMBOO);
        createWoodenFurnitureRecipes(WoodType.CRIMSON, MaterialType.CRIMSON);
        createWoodenFurnitureRecipes(WoodType.WARPED, MaterialType.WARPED);
    }

    private void createFridgeRecipes() {
        carpentryTableCrafting(SFFBlocks.IRON_FRIDGE.get(), 1, FurnitureCategory.FRIDGES, MaterialType.IRON, new ItemStack(Blocks.IRON_BLOCK, 2), new ItemStack(Blocks.IRON_BARS, 3));
        carpentryTableCrafting(SFFBlocks.COPPER_FRIDGE.get(), 1, FurnitureCategory.FRIDGES, MaterialType.COPPER, new ItemStack(Blocks.COPPER_BLOCK, 2), new ItemStack(Blocks.IRON_BARS, 3));
        carpentryTableCrafting(SFFBlocks.EXPOSED_COPPER_FRIDGE.get(), 1, FurnitureCategory.FRIDGES, MaterialType.COPPER, new ItemStack(Blocks.EXPOSED_COPPER, 2), new ItemStack(Blocks.IRON_BARS, 3));
        carpentryTableCrafting(SFFBlocks.WEATHERED_COPPER_FRIDGE.get(), 1, FurnitureCategory.FRIDGES, MaterialType.COPPER, new ItemStack(Blocks.WEATHERED_COPPER, 2), new ItemStack(Blocks.IRON_BARS, 3));
        carpentryTableCrafting(SFFBlocks.OXIDIZED_COPPER_FRIDGE.get(), 1, FurnitureCategory.FRIDGES, MaterialType.COPPER, new ItemStack(Blocks.OXIDIZED_COPPER, 2), new ItemStack(Blocks.IRON_BARS, 3));
        carpentryTableCrafting(SFFBlocks.WAXED_COPPER_FRIDGE.get(), 1, FurnitureCategory.FRIDGES, MaterialType.COPPER, new ItemStack(Blocks.WAXED_COPPER_BLOCK, 2), new ItemStack(Blocks.IRON_BARS, 3));
        carpentryTableCrafting(SFFBlocks.WAXED_EXPOSED_COPPER_FRIDGE.get(), 1, FurnitureCategory.FRIDGES, MaterialType.COPPER, new ItemStack(Blocks.WAXED_EXPOSED_COPPER, 2), new ItemStack(Blocks.IRON_BARS, 3));
        carpentryTableCrafting(SFFBlocks.WAXED_WEATHERED_COPPER_FRIDGE.get(), 1, FurnitureCategory.FRIDGES, MaterialType.COPPER, new ItemStack(Blocks.WAXED_WEATHERED_COPPER, 2), new ItemStack(Blocks.IRON_BARS, 3));
        carpentryTableCrafting(SFFBlocks.WAXED_OXIDIZED_COPPER_FRIDGE.get(), 1, FurnitureCategory.FRIDGES, MaterialType.COPPER, new ItemStack(Blocks.WAXED_OXIDIZED_COPPER, 2), new ItemStack(Blocks.IRON_BARS, 3));
        carpentryTableCrafting(SFFBlocks.GOLD_FRIDGE.get(), 1, FurnitureCategory.FRIDGES, MaterialType.GOLD, new ItemStack(Blocks.GOLD_BLOCK, 2), new ItemStack(Blocks.IRON_BARS, 3));
        carpentryTableCrafting(SFFBlocks.NETHERITE_FRIDGE.get(), 1, FurnitureCategory.FRIDGES, MaterialType.NETHERITE, new ItemStack(Blocks.NETHERITE_BLOCK, 2), new ItemStack(Blocks.IRON_BARS, 3));
    }

    private void createCurtainRecipes() {
        WOOL_COLORS.forEach((wool, color) -> CURTAIN_RODS.forEach((rod, name) -> carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_"+name+"_curtains")), 6, FurnitureCategory.CURTAINS, MaterialType.WOOL, getMaterialTypeFromTop(rod), new ItemStack(wool, 1), new ItemStack(rod, 1))));
    }

    private void createKitchenTileRecipes() {
        carpentryTableCrafting(SFFBlocks.WHITE_LIGHT_GRAY_KITCHEN_TILES.get(), 8, FurnitureCategory.FLOOR, MaterialType.CONCRETE, new ItemStack(Blocks.WHITE_CONCRETE, 4), new ItemStack(Blocks.LIGHT_GRAY_CONCRETE, 4));
        carpentryTableCrafting(SFFBlocks.WHITE_GRAY_KITCHEN_TILES.get(), 8, FurnitureCategory.FLOOR, MaterialType.CONCRETE, new ItemStack(Blocks.WHITE_CONCRETE, 4), new ItemStack(Blocks.GRAY_CONCRETE, 4));
        carpentryTableCrafting(SFFBlocks.WHITE_BLACK_KITCHEN_TILES.get(), 8, FurnitureCategory.FLOOR, MaterialType.CONCRETE, new ItemStack(Blocks.WHITE_CONCRETE, 4), new ItemStack(Blocks.BLACK_CONCRETE, 4));
        carpentryTableCrafting(SFFBlocks.WHITE_BROWN_KITCHEN_TILES.get(), 8, FurnitureCategory.FLOOR, MaterialType.CONCRETE, new ItemStack(Blocks.WHITE_CONCRETE, 4), new ItemStack(Blocks.BROWN_CONCRETE, 4));
        carpentryTableCrafting(SFFBlocks.WHITE_RED_KITCHEN_TILES.get(), 8, FurnitureCategory.FLOOR, MaterialType.CONCRETE, new ItemStack(Blocks.WHITE_CONCRETE, 4), new ItemStack(Blocks.RED_CONCRETE, 4));
        carpentryTableCrafting(SFFBlocks.WHITE_ORANGE_KITCHEN_TILES.get(), 8, FurnitureCategory.FLOOR, MaterialType.CONCRETE, new ItemStack(Blocks.WHITE_CONCRETE, 4), new ItemStack(Blocks.ORANGE_CONCRETE, 4));
        carpentryTableCrafting(SFFBlocks.WHITE_YELLOW_KITCHEN_TILES.get(), 8, FurnitureCategory.FLOOR, MaterialType.CONCRETE, new ItemStack(Blocks.WHITE_CONCRETE, 4), new ItemStack(Blocks.YELLOW_CONCRETE, 4));
        carpentryTableCrafting(SFFBlocks.WHITE_LIME_KITCHEN_TILES.get(), 8, FurnitureCategory.FLOOR, MaterialType.CONCRETE, new ItemStack(Blocks.WHITE_CONCRETE, 4), new ItemStack(Blocks.LIME_CONCRETE, 4));
        carpentryTableCrafting(SFFBlocks.WHITE_GREEN_KITCHEN_TILES.get(), 8, FurnitureCategory.FLOOR, MaterialType.CONCRETE, new ItemStack(Blocks.WHITE_CONCRETE, 4), new ItemStack(Blocks.GREEN_CONCRETE, 4));
        carpentryTableCrafting(SFFBlocks.WHITE_CYAN_KITCHEN_TILES.get(), 8, FurnitureCategory.FLOOR, MaterialType.CONCRETE, new ItemStack(Blocks.WHITE_CONCRETE, 4), new ItemStack(Blocks.CYAN_CONCRETE, 4));
        carpentryTableCrafting(SFFBlocks.WHITE_LIGHT_BLUE_KITCHEN_TILES.get(), 8, FurnitureCategory.FLOOR, MaterialType.CONCRETE, new ItemStack(Blocks.WHITE_CONCRETE, 4), new ItemStack(Blocks.LIGHT_BLUE_CONCRETE, 4));
        carpentryTableCrafting(SFFBlocks.WHITE_BLUE_KITCHEN_TILES.get(), 8, FurnitureCategory.FLOOR, MaterialType.CONCRETE, new ItemStack(Blocks.WHITE_CONCRETE, 4), new ItemStack(Blocks.BLUE_CONCRETE, 4));
        carpentryTableCrafting(SFFBlocks.WHITE_PURPLE_KITCHEN_TILES.get(), 8, FurnitureCategory.FLOOR, MaterialType.CONCRETE, new ItemStack(Blocks.WHITE_CONCRETE, 4), new ItemStack(Blocks.PURPLE_CONCRETE, 4));
        carpentryTableCrafting(SFFBlocks.WHITE_MAGENTA_KITCHEN_TILES.get(), 8, FurnitureCategory.FLOOR, MaterialType.CONCRETE, new ItemStack(Blocks.WHITE_CONCRETE, 4), new ItemStack(Blocks.MAGENTA_CONCRETE, 4));
        carpentryTableCrafting(SFFBlocks.WHITE_PINK_KITCHEN_TILES.get(), 8, FurnitureCategory.FLOOR, MaterialType.CONCRETE, new ItemStack(Blocks.WHITE_CONCRETE, 4), new ItemStack(Blocks.PINK_CONCRETE, 4));
    }

    private void createWoodenFurnitureRecipes(WoodType type, MaterialType materialType) {
        Block planks = getBlockFromResourceLocation(ResourceLocation.parse(type.name()+"_planks"));
        Block log;
        Block strippedLog;
        String log_suffix;
        if (type == WoodType.CRIMSON || type == WoodType.WARPED) {
            log = getBlockFromResourceLocation(ResourceLocation.parse(type.name()+"_stem"));
            strippedLog = getBlockFromResourceLocation(ResourceLocation.parse("stripped_"+type.name()+"_stem"));
            log_suffix = "_stem";
        } else if (type == WoodType.BAMBOO) {
            log = getBlockFromResourceLocation(ResourceLocation.parse(type.name()+"_block"));
            strippedLog = getBlockFromResourceLocation(ResourceLocation.parse("stripped_"+type.name()+"_block"));
            log_suffix = "_block";
        } else {
            log = getBlockFromResourceLocation(ResourceLocation.parse(type.name()+"_log"));
            strippedLog = getBlockFromResourceLocation(ResourceLocation.parse("stripped_"+type.name()+"_log"));
            log_suffix = "_log";
        }
        carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_crate")), 2, FurnitureCategory.CRATES, materialType, new ItemStack(planks, 2), new ItemStack(log, 2));

        COUNTER_TOPS.forEach(((block, s) -> {
            String top_name = block.getDescriptionId().replaceFirst("block.minecraft.", "").replaceFirst("quartz_block", "quartz");
            MaterialType additional = getMaterialTypeFromTop(block);
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter")), 4, FurnitureCategory.KITCHEN_COUNTERS, materialType, additional, new ItemStack(planks, 4), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_shelf")), 4, FurnitureCategory.KITCHEN_COUNTERS, materialType, additional, new ItemStack(planks, 4), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_door")), 4, FurnitureCategory.KITCHEN_COUNTERS, materialType, additional, new ItemStack(planks, 4), new ItemStack(log, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_small_drawer")), 4, FurnitureCategory.KITCHEN_COUNTERS, materialType, additional, new ItemStack(planks, 4), new ItemStack(log, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_big_drawer")), 4, FurnitureCategory.KITCHEN_COUNTERS, materialType, additional, new ItemStack(planks, 4), new ItemStack(log, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_sink")), 4, FurnitureCategory.KITCHEN_COUNTERS, materialType, additional, new ItemStack(planks, 4), new ItemStack(Items.WATER_BUCKET, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_smoker")), 4, FurnitureCategory.KITCHEN_COUNTERS, materialType, additional, new ItemStack(planks, 1), new ItemStack(Blocks.SMOKER, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_cabinet_door")), 4, FurnitureCategory.KITCHEN_CABINETS, materialType, additional, new ItemStack(planks, 4), new ItemStack(log, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_cabinet_glass_door")), 4, FurnitureCategory.KITCHEN_CABINETS, materialType, additional, new ItemStack(planks, 4), new ItemStack(log, 1), new ItemStack(GLASS_PANE, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_cabinet_sideways_door")), 4, FurnitureCategory.KITCHEN_CABINETS, materialType, additional, new ItemStack(planks, 4), new ItemStack(log, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_cabinet_sideways_glass_door")), 4, FurnitureCategory.KITCHEN_CABINETS, materialType, additional, new ItemStack(planks, 4), new ItemStack(log, 1), new ItemStack(GLASS_PANE, 1), new ItemStack(block, 1));

            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_counter")), 4, FurnitureCategory.KITCHEN_COUNTERS, materialType, additional, new ItemStack(log, 4), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_shelf")), 4, FurnitureCategory.KITCHEN_COUNTERS, materialType, additional, new ItemStack(log, 4), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_door")), 4, FurnitureCategory.KITCHEN_COUNTERS, materialType, additional, new ItemStack(log, 4), new ItemStack(strippedLog, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_small_drawer")), 4, FurnitureCategory.KITCHEN_COUNTERS, materialType, additional, new ItemStack(log, 4), new ItemStack(strippedLog, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_big_drawer")), 4, FurnitureCategory.KITCHEN_COUNTERS, materialType, additional, new ItemStack(log, 4), new ItemStack(strippedLog, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_sink")), 4, FurnitureCategory.KITCHEN_COUNTERS, materialType, additional, new ItemStack(log, 4), new ItemStack(Items.WATER_BUCKET, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_smoker")), 4, FurnitureCategory.KITCHEN_COUNTERS, materialType, additional, new ItemStack(log, 1), new ItemStack(Blocks.SMOKER, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_door")), 4, FurnitureCategory.KITCHEN_CABINETS, materialType, additional, new ItemStack(log, 4), new ItemStack(strippedLog, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_glass_door")), 4, FurnitureCategory.KITCHEN_CABINETS, materialType, additional, new ItemStack(log, 4), new ItemStack(strippedLog, 1), new ItemStack(GLASS_PANE, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_door")), 4, FurnitureCategory.KITCHEN_CABINETS, materialType, additional, new ItemStack(log, 4), new ItemStack(strippedLog, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_glass_door")), 4, FurnitureCategory.KITCHEN_CABINETS, materialType, additional, new ItemStack(log, 4), new ItemStack(strippedLog, 1), new ItemStack(GLASS_PANE, 1), new ItemStack(block, 1));

            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter")), 4, FurnitureCategory.KITCHEN_COUNTERS, materialType, additional, new ItemStack(strippedLog, 4), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_shelf")), 4, FurnitureCategory.KITCHEN_COUNTERS, materialType, additional, new ItemStack(strippedLog, 4), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_door")), 4, FurnitureCategory.KITCHEN_COUNTERS, materialType, additional, new ItemStack(strippedLog, 4), new ItemStack(log, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_small_drawer")), 4, FurnitureCategory.KITCHEN_COUNTERS, materialType, additional, new ItemStack(strippedLog, 4), new ItemStack(log, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_big_drawer")), 4, FurnitureCategory.KITCHEN_COUNTERS, materialType, new ItemStack(strippedLog, 4), new ItemStack(log, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_sink")), 4, FurnitureCategory.KITCHEN_COUNTERS, materialType, additional, new ItemStack(strippedLog, 4), new ItemStack(Items.WATER_BUCKET, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_smoker")), 4, FurnitureCategory.KITCHEN_COUNTERS, materialType, additional, new ItemStack(strippedLog, 1), new ItemStack(Blocks.SMOKER, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_door")), 4, FurnitureCategory.KITCHEN_CABINETS, materialType, additional, new ItemStack(strippedLog, 4), new ItemStack(log, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_glass_door")), 4, FurnitureCategory.KITCHEN_CABINETS, materialType, additional, new ItemStack(strippedLog, 4), new ItemStack(log, 1), new ItemStack(GLASS_PANE, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_door")), 4, FurnitureCategory.KITCHEN_CABINETS, materialType, additional, new ItemStack(strippedLog, 4), new ItemStack(log, 1), new ItemStack(block, 1));
            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_glass_door")), 4, FurnitureCategory.KITCHEN_CABINETS, materialType, additional, new ItemStack(strippedLog, 4), new ItemStack(log, 1), new ItemStack(GLASS_PANE, 1), new ItemStack(block, 1));

            carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_knife_block")), 2, FurnitureCategory.KITCHEN_MISC, materialType, additional, new ItemStack(planks, 1), new ItemStack(block, 1), new ItemStack(Items.IRON_NUGGET, 10));
        }));

        carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_kitchen_cabinet")), 4, FurnitureCategory.KITCHEN_CABINETS, materialType, new ItemStack(planks, 4));
        carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_kitchen_cabinet_shelf")), 4, FurnitureCategory.KITCHEN_CABINETS, materialType, new ItemStack(planks, 4));
        carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_kitchen_shelf")), 4, FurnitureCategory.KITCHEN_SHELFS, materialType, new ItemStack(planks, 1), new ItemStack(CHAIN, 1));

        carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_kitchen_cabinet")), 4, FurnitureCategory.KITCHEN_CABINETS, materialType, new ItemStack(log, 4));
        carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_kitchen_cabinet_shelf")), 4, FurnitureCategory.KITCHEN_CABINETS, materialType, new ItemStack(log, 4));
        carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_kitchen_shelf")), 4, FurnitureCategory.KITCHEN_SHELFS, materialType, new ItemStack(log, 1), new ItemStack(CHAIN, 1));

        carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_kitchen_cabinet")), 4, FurnitureCategory.KITCHEN_CABINETS, materialType, new ItemStack(strippedLog, 4));
        carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_kitchen_cabinet_shelf")), 4, FurnitureCategory.KITCHEN_CABINETS, materialType, new ItemStack(strippedLog, 4));
        carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_kitchen_shelf")), 4, FurnitureCategory.KITCHEN_SHELFS, materialType, new ItemStack(strippedLog, 1), new ItemStack(CHAIN, 1));

        carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_table")), 2, FurnitureCategory.TABLES, materialType, new ItemStack(planks, 1));
        carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_table")), 2, FurnitureCategory.TABLES, materialType, new ItemStack(log, 1));
        carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_table")), 2, FurnitureCategory.TABLES, materialType, new ItemStack(strippedLog, 1));

        carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_bedside_table")), 2, FurnitureCategory.TABLES, materialType, new ItemStack(planks, 2), new ItemStack(log, 1));
        carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_bedside_table")), 2, FurnitureCategory.TABLES, materialType, new ItemStack(log, 2), new ItemStack(strippedLog, 1));
        carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_bedside_table")), 2, FurnitureCategory.TABLES, materialType, new ItemStack(strippedLog, 2), new ItemStack(log, 1));

        carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_closet")), 1, FurnitureCategory.TABLES, materialType, new ItemStack(planks, 2), new ItemStack(log, 1));
        carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_closet")), 1, FurnitureCategory.TABLES, materialType, new ItemStack(log, 2), new ItemStack(strippedLog, 1));
        carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_closet")), 1, FurnitureCategory.TABLES, materialType, new ItemStack(strippedLog, 2), new ItemStack(log, 1));

        WOOL_COLORS.forEach((block, color) -> BlockFamilies.getAllFamilies().toList().forEach(blockFamily -> {
            if (blockFamily.getBaseBlock() == planks) {
                carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_"+type.name()+"_chair")), 2, FurnitureCategory.CHAIRS, materialType, new ItemStack(planks, 1), new ItemStack(blockFamily.get(BlockFamily.Variant.TRAPDOOR), 2), new ItemStack(block, 1));
                carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_"+type.name()+log_suffix+"_chair")), 2, FurnitureCategory.CHAIRS, materialType, new ItemStack(log, 1), new ItemStack(blockFamily.get(BlockFamily.Variant.TRAPDOOR), 2), new ItemStack(block, 1));
                carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_stripped_"+type.name()+log_suffix+"_chair")), 2, FurnitureCategory.CHAIRS, materialType, new ItemStack(strippedLog, 1), new ItemStack(blockFamily.get(BlockFamily.Variant.TRAPDOOR), 2), new ItemStack(block, 1));

                carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_"+type.name()+"_lamp")), 4, FurnitureCategory.LAMPS, materialType, new ItemStack(planks, 1), new ItemStack(log, 1), new ItemStack(strippedLog, 1), new ItemStack(REDSTONE_LAMP, 1), new ItemStack(block, 1));

                carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_"+type.name()+"_bed")), 1, FurnitureCategory.BEDS, materialType, new ItemStack(planks, 1), new ItemStack(blockFamily.get(BlockFamily.Variant.TRAPDOOR), 1), new ItemStack(block, 1));
                carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_"+type.name()+log_suffix+"_bed")), 1, FurnitureCategory.BEDS, materialType, new ItemStack(log, 1), new ItemStack(blockFamily.get(BlockFamily.Variant.TRAPDOOR), 1), new ItemStack(block, 1));
                carpentryTableCrafting(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_stripped_"+type.name()+log_suffix+"_bed")), 1, FurnitureCategory.BEDS, materialType, new ItemStack(strippedLog, 1), new ItemStack(blockFamily.get(BlockFamily.Variant.TRAPDOOR), 1), new ItemStack(block, 1));
            }
        }));
    }

    private MaterialType getMaterialTypeFromTop(Block block) {
        if (block == IRON_BLOCK) {
            return MaterialType.IRON;
        } else if (block == COPPER_BLOCK) {
            return MaterialType.COPPER;
        } else if (block == GOLD_BLOCK) {
            return MaterialType.GOLD;
        } else if (block == NETHERITE_BLOCK) {
            return MaterialType.NETHERITE;
        } else if (block == POLISHED_GRANITE) {
            return MaterialType.GRANITE;
        } else if (block == POLISHED_DIORITE) {
            return MaterialType.DIORITE;
        } else if (block == POLISHED_ANDESITE) {
            return MaterialType.ANDESITE;
        } else if (block == POLISHED_DEEPSLATE) {
            return MaterialType.DEEPSLATE;
        } else if (block == POLISHED_TUFF) {
            return MaterialType.TUFF;
        } else if (block == SANDSTONE) {
            return MaterialType.SANDSTONE;
        } else if (block == RED_SANDSTONE) {
            return MaterialType.RED_SANDSTONE;
        } else if (block == POLISHED_BLACKSTONE) {
            return MaterialType.BLACKSTONE;
        } else if (block == GILDED_BLACKSTONE) {
            return MaterialType.GILDED_BLACKSTONE;
        } else if (block == SMOOTH_BASALT) {
            return MaterialType.BASALT;
        } else if (block == QUARTZ_BLOCK) {
            return MaterialType.QUARTZ;
        } else {
            return MaterialType.CALCITE;
        }
    }

    private void carpentryTableCrafting(ItemLike result, int count, FurnitureCategory category, MaterialType main, ItemStack... ingredients) {
        String resultName = BuiltInRegistries.ITEM.getKey(result.asItem()).getPath();
        this.carpentryTableCrafting(resultName, result, count, category, main, null, ingredients);
    }

    private void carpentryTableCrafting(ItemLike result, int count, FurnitureCategory category, MaterialType main, @Nullable MaterialType additional, ItemStack... ingredients) {
        String resultName = BuiltInRegistries.ITEM.getKey(result.asItem()).getPath();
        this.carpentryTableCrafting(resultName, result, count, category, main, additional, ingredients);
    }

    private void carpentryTableCrafting(String name, ItemLike result, int count, FurnitureCategory category, MaterialType main, @Nullable MaterialType additional, ItemStack ... ingredients) {
        CarpentryTableRecipe.Builder builder = CarpentryTableRecipe.builder(result, count, category);
        for(ItemStack stack : ingredients) {
            builder.requiresMaterial(stack);
            builder.unlockedBy("has_"+BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath(), has(MinMaxBounds.Ints.atLeast(stack.getCount()), stack.getItem()));
        }
        builder.containsMaterial(main);
        if (additional != null) {
            builder.containsMaterial(additional);
        }
        ResourceKey<Recipe<?>> key = ResourceKey.create(Registries.RECIPE, FabulousFurniture.prefix("carpentry/" + name));
        builder.save(this.output, key);
    }

    private Block getBlockFromResourceLocation(ResourceLocation location) {
        return BuiltInRegistries.BLOCK.getValue(location);
    }
}