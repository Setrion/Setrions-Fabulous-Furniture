package net.setrion.fabulous_furniture.data;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SmokerBlock;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.setrion.fabulous_furniture.FabulousFurniture;
import net.setrion.fabulous_furniture.world.level.block.*;
import net.setrion.fabulous_furniture.world.level.block.state.properties.CurtainShape;
import net.setrion.fabulous_furniture.world.level.block.state.properties.ShelfShape;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.minecraft.client.data.models.blockstates.VariantProperties.*;
import static net.minecraft.world.level.block.Blocks.*;

import static net.setrion.fabulous_furniture.registry.SFFBlocks.*;

public class ModelGenerator extends ModelProvider {

    public ModelGenerator(PackOutput output) {
        super(output, FabulousFurniture.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        WoodType.values().toList().forEach(type -> {
            generateWoodenFurniture(type, blockModels);
        });
        createCurtains(blockModels);
        createKitchenTiles(blockModels);

        createModel(blockModels, createFridge(IRON_FRIDGE.get(), TextureMapping.getBlockTexture(IRON_BLOCK), TextureMapping.getBlockTexture(IRON_BARS), TextureMapping.getBlockTexture(ANVIL), blockModels));
        createModel(blockModels, createFridge(GOLD_FRIDGE.get(), TextureMapping.getBlockTexture(GOLD_BLOCK), TextureMapping.getBlockTexture(IRON_BARS), TextureMapping.getBlockTexture(ANVIL), blockModels));
        createModel(blockModels, createFridge(NETHERITE_FRIDGE.get(), TextureMapping.getBlockTexture(NETHERITE_BLOCK), TextureMapping.getBlockTexture(IRON_BARS), TextureMapping.getBlockTexture(ANVIL), blockModels));
        createModel(blockModels, createFridge(COPPER_FRIDGE.get(), TextureMapping.getBlockTexture(COPPER_BLOCK), TextureMapping.getBlockTexture(IRON_BARS), TextureMapping.getBlockTexture(ANVIL), blockModels));
        createModel(blockModels, createFridge(EXPOSED_COPPER_FRIDGE.get(), TextureMapping.getBlockTexture(EXPOSED_COPPER), TextureMapping.getBlockTexture(IRON_BARS), TextureMapping.getBlockTexture(ANVIL), blockModels));
        createModel(blockModels, createFridge(WEATHERED_COPPER_FRIDGE.get(), TextureMapping.getBlockTexture(WEATHERED_COPPER), TextureMapping.getBlockTexture(IRON_BARS), TextureMapping.getBlockTexture(ANVIL), blockModels));
        createModel(blockModels, createFridge(OXIDIZED_COPPER_FRIDGE.get(), TextureMapping.getBlockTexture(OXIDIZED_COPPER), TextureMapping.getBlockTexture(IRON_BARS), TextureMapping.getBlockTexture(ANVIL), blockModels));
        createModel(blockModels, createFridge(WAXED_COPPER_FRIDGE.get(), TextureMapping.getBlockTexture(COPPER_BLOCK), TextureMapping.getBlockTexture(IRON_BARS), TextureMapping.getBlockTexture(ANVIL), blockModels));
        createModel(blockModels, createFridge(WAXED_EXPOSED_COPPER_FRIDGE.get(), TextureMapping.getBlockTexture(EXPOSED_COPPER), TextureMapping.getBlockTexture(IRON_BARS), TextureMapping.getBlockTexture(ANVIL), blockModels));
        createModel(blockModels, createFridge(WAXED_WEATHERED_COPPER_FRIDGE.get(), TextureMapping.getBlockTexture(WEATHERED_COPPER), TextureMapping.getBlockTexture(IRON_BARS), TextureMapping.getBlockTexture(ANVIL), blockModels));
        createModel(blockModels, createFridge(WAXED_OXIDIZED_COPPER_FRIDGE.get(), TextureMapping.getBlockTexture(OXIDIZED_COPPER), TextureMapping.getBlockTexture(IRON_BARS), TextureMapping.getBlockTexture(ANVIL), blockModels));

        Collection<Block> blocks = BLOCKS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toList());
        blocks.forEach((block -> {
            if (!(block instanceof TableBlock || block instanceof KitchenCabinetShelfBlock || block instanceof CurtainBlock)) {
                registerBasicBlockModel(blockModels.itemModelOutput, block);
            } else {
                if (block instanceof CurtainBlock) {
                    registerBasicBlockModel(blockModels.itemModelOutput, block, "_small_single_open");
                } else {
                    registerBasicBlockModel(blockModels.itemModelOutput, block, "_single");
                }
            }
        }));
    }

    private void createCurtains(BlockModelGenerators blockModels) {
        createModel(blockModels, createCurtainBlock(WHITE_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(WHITE_WOOL), TextureMapping.getBlockTexture(IRON_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(WHITE_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(WHITE_WOOL), TextureMapping.getBlockTexture(GOLD_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(WHITE_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(WHITE_WOOL), TextureMapping.getBlockTexture(NETHERITE_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(WHITE_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(WHITE_WOOL), TextureMapping.getBlockTexture(COPPER_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(LIGHT_GRAY_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(LIGHT_GRAY_WOOL), TextureMapping.getBlockTexture(IRON_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(LIGHT_GRAY_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(LIGHT_GRAY_WOOL), TextureMapping.getBlockTexture(GOLD_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(LIGHT_GRAY_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(LIGHT_GRAY_WOOL), TextureMapping.getBlockTexture(NETHERITE_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(LIGHT_GRAY_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(LIGHT_GRAY_WOOL), TextureMapping.getBlockTexture(COPPER_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(GRAY_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(GRAY_WOOL), TextureMapping.getBlockTexture(IRON_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(GRAY_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(GRAY_WOOL), TextureMapping.getBlockTexture(GOLD_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(GRAY_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(GRAY_WOOL), TextureMapping.getBlockTexture(NETHERITE_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(GRAY_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(GRAY_WOOL), TextureMapping.getBlockTexture(COPPER_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(BLACK_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(BLACK_WOOL), TextureMapping.getBlockTexture(IRON_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(BLACK_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(BLACK_WOOL), TextureMapping.getBlockTexture(GOLD_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(BLACK_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(BLACK_WOOL), TextureMapping.getBlockTexture(NETHERITE_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(BLACK_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(BLACK_WOOL), TextureMapping.getBlockTexture(COPPER_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(BROWN_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(BROWN_WOOL), TextureMapping.getBlockTexture(IRON_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(BROWN_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(BROWN_WOOL), TextureMapping.getBlockTexture(GOLD_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(BROWN_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(BROWN_WOOL), TextureMapping.getBlockTexture(NETHERITE_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(BROWN_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(BROWN_WOOL), TextureMapping.getBlockTexture(COPPER_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(RED_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(RED_WOOL), TextureMapping.getBlockTexture(IRON_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(RED_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(RED_WOOL), TextureMapping.getBlockTexture(GOLD_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(RED_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(RED_WOOL), TextureMapping.getBlockTexture(NETHERITE_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(RED_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(RED_WOOL), TextureMapping.getBlockTexture(COPPER_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(ORANGE_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(ORANGE_WOOL), TextureMapping.getBlockTexture(IRON_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(ORANGE_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(ORANGE_WOOL), TextureMapping.getBlockTexture(GOLD_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(ORANGE_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(ORANGE_WOOL), TextureMapping.getBlockTexture(NETHERITE_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(ORANGE_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(ORANGE_WOOL), TextureMapping.getBlockTexture(COPPER_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(YELLOW_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(YELLOW_WOOL), TextureMapping.getBlockTexture(IRON_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(YELLOW_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(YELLOW_WOOL), TextureMapping.getBlockTexture(GOLD_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(YELLOW_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(YELLOW_WOOL), TextureMapping.getBlockTexture(NETHERITE_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(YELLOW_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(YELLOW_WOOL), TextureMapping.getBlockTexture(COPPER_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(LIME_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(LIME_WOOL), TextureMapping.getBlockTexture(IRON_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(LIME_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(LIME_WOOL), TextureMapping.getBlockTexture(GOLD_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(LIME_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(LIME_WOOL), TextureMapping.getBlockTexture(NETHERITE_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(LIME_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(LIME_WOOL), TextureMapping.getBlockTexture(COPPER_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(GREEN_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(GREEN_WOOL), TextureMapping.getBlockTexture(IRON_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(GREEN_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(GREEN_WOOL), TextureMapping.getBlockTexture(GOLD_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(GREEN_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(GREEN_WOOL), TextureMapping.getBlockTexture(NETHERITE_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(GREEN_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(GREEN_WOOL), TextureMapping.getBlockTexture(COPPER_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(CYAN_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(CYAN_WOOL), TextureMapping.getBlockTexture(IRON_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(CYAN_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(CYAN_WOOL), TextureMapping.getBlockTexture(GOLD_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(CYAN_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(CYAN_WOOL), TextureMapping.getBlockTexture(NETHERITE_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(CYAN_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(CYAN_WOOL), TextureMapping.getBlockTexture(COPPER_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(LIGHT_BLUE_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(LIGHT_BLUE_WOOL), TextureMapping.getBlockTexture(IRON_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(LIGHT_BLUE_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(LIGHT_BLUE_WOOL), TextureMapping.getBlockTexture(GOLD_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(LIGHT_BLUE_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(LIGHT_BLUE_WOOL), TextureMapping.getBlockTexture(NETHERITE_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(LIGHT_BLUE_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(LIGHT_BLUE_WOOL), TextureMapping.getBlockTexture(COPPER_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(BLUE_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(BLUE_WOOL), TextureMapping.getBlockTexture(IRON_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(BLUE_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(BLUE_WOOL), TextureMapping.getBlockTexture(GOLD_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(BLUE_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(BLUE_WOOL), TextureMapping.getBlockTexture(NETHERITE_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(BLUE_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(BLUE_WOOL), TextureMapping.getBlockTexture(COPPER_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(PURPLE_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(PURPLE_WOOL), TextureMapping.getBlockTexture(IRON_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(PURPLE_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(PURPLE_WOOL), TextureMapping.getBlockTexture(GOLD_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(PURPLE_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(PURPLE_WOOL), TextureMapping.getBlockTexture(NETHERITE_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(PURPLE_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(PURPLE_WOOL), TextureMapping.getBlockTexture(COPPER_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(MAGENTA_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(MAGENTA_WOOL), TextureMapping.getBlockTexture(IRON_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(MAGENTA_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(MAGENTA_WOOL), TextureMapping.getBlockTexture(GOLD_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(MAGENTA_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(MAGENTA_WOOL), TextureMapping.getBlockTexture(NETHERITE_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(MAGENTA_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(MAGENTA_WOOL), TextureMapping.getBlockTexture(COPPER_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(PINK_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(PINK_WOOL), TextureMapping.getBlockTexture(IRON_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(PINK_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(PINK_WOOL), TextureMapping.getBlockTexture(GOLD_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(PINK_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(PINK_WOOL), TextureMapping.getBlockTexture(NETHERITE_BLOCK), blockModels));
        createModel(blockModels, createCurtainBlock(PINK_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(PINK_WOOL), TextureMapping.getBlockTexture(COPPER_BLOCK), blockModels));
    }

    private void createKitchenTiles(BlockModelGenerators blockModels) {
        createModel(blockModels, createKitchenTiles(WHITE_LIGHT_GRAY_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(WHITE_CONCRETE), TextureMapping.getBlockTexture(LIGHT_GRAY_CONCRETE), blockModels));
        createModel(blockModels, createKitchenTiles(WHITE_GRAY_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(WHITE_CONCRETE), TextureMapping.getBlockTexture(GRAY_CONCRETE), blockModels));
        createModel(blockModels, createKitchenTiles(WHITE_BLACK_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(WHITE_CONCRETE), TextureMapping.getBlockTexture(BLACK_CONCRETE), blockModels));
        createModel(blockModels, createKitchenTiles(WHITE_BROWN_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(WHITE_CONCRETE), TextureMapping.getBlockTexture(BROWN_CONCRETE), blockModels));
        createModel(blockModels, createKitchenTiles(WHITE_RED_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(WHITE_CONCRETE), TextureMapping.getBlockTexture(RED_CONCRETE), blockModels));
        createModel(blockModels, createKitchenTiles(WHITE_ORANGE_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(WHITE_CONCRETE), TextureMapping.getBlockTexture(ORANGE_CONCRETE), blockModels));
        createModel(blockModels, createKitchenTiles(WHITE_YELLOW_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(WHITE_CONCRETE), TextureMapping.getBlockTexture(YELLOW_CONCRETE), blockModels));
        createModel(blockModels, createKitchenTiles(WHITE_LIME_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(WHITE_CONCRETE), TextureMapping.getBlockTexture(LIME_CONCRETE), blockModels));
        createModel(blockModels, createKitchenTiles(WHITE_GREEN_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(WHITE_CONCRETE), TextureMapping.getBlockTexture(GREEN_CONCRETE), blockModels));
        createModel(blockModels, createKitchenTiles(WHITE_CYAN_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(WHITE_CONCRETE), TextureMapping.getBlockTexture(CYAN_CONCRETE), blockModels));
        createModel(blockModels, createKitchenTiles(WHITE_LIGHT_BLUE_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(WHITE_CONCRETE), TextureMapping.getBlockTexture(LIGHT_BLUE_CONCRETE), blockModels));
        createModel(blockModels, createKitchenTiles(WHITE_BLUE_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(WHITE_CONCRETE), TextureMapping.getBlockTexture(BLUE_CONCRETE), blockModels));
        createModel(blockModels, createKitchenTiles(WHITE_PURPLE_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(WHITE_CONCRETE), TextureMapping.getBlockTexture(PURPLE_CONCRETE), blockModels));
        createModel(blockModels, createKitchenTiles(WHITE_MAGENTA_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(WHITE_CONCRETE), TextureMapping.getBlockTexture(MAGENTA_CONCRETE), blockModels));
        createModel(blockModels, createKitchenTiles(WHITE_PINK_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(WHITE_CONCRETE), TextureMapping.getBlockTexture(PINK_CONCRETE), blockModels));
    }

    private void generateWoodenFurniture(WoodType type, BlockModelGenerators blockModels) {
        Block planks = getBlockFromResourceLocation(ResourceLocation.parse(type.name()+"_planks"));
        Block log;
        Block strippedLog;
        if (type == WoodType.CRIMSON || type == WoodType.WARPED) {
            log = getBlockFromResourceLocation(ResourceLocation.parse(type.name()+"_stem"));
            strippedLog = getBlockFromResourceLocation(ResourceLocation.parse("stripped_"+type.name()+"_stem"));
        } else if (type == WoodType.BAMBOO) {
            log = getBlockFromResourceLocation(ResourceLocation.parse(type.name()+"_block"));
            strippedLog = getBlockFromResourceLocation(ResourceLocation.parse("stripped_"+type.name()+"_block"));
        } else {
            log = getBlockFromResourceLocation(ResourceLocation.parse(type.name()+"_log"));
            strippedLog = getBlockFromResourceLocation(ResourceLocation.parse("stripped_"+type.name()+"_log"));
        }
        createModel(blockModels, createCrate(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_crate")), planks, log, blockModels));
        COUNTER_TOPS.forEach(((block, s) -> {
            String top_name = block.getDescriptionId().replaceFirst("block.minecraft.", "").replaceFirst("quartz_block", "quartz");
            createModel(blockModels, createKitchenCounter(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter")), planks, TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCounterInnerCorner(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_inner_corner")), planks, TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCounterOuterCorner(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_outer_corner")), planks, TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createOpenKitchenCounter(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_open")), planks, TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCounterDoor(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_door")), planks, TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(log), blockModels));
            createModel(blockModels, createKitchenCounterGlassDoor(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_glass_door")), planks, TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(log), blockModels));
            createModel(blockModels, createKitchenCounterSmallDrawer(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_small_drawer")), planks, TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(log), blockModels));
            createModel(blockModels, createKitchenCounterBigDrawer(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_big_drawer")), planks, TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(log), blockModels));
            createModel(blockModels, createKitchenCounterSink(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_sink")), planks, TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCounterSmoker(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_smoker")), planks, TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(SMOKER, "_front"), TextureMapping.getBlockTexture(SMOKER, "_front_on"), blockModels));
            createModel(blockModels, createKitchenCabinetDoor(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_cabinet_door")), planks, TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCabinetGlassDoor(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_cabinet_glass_door")), planks, TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCabinetSidewaysDoor(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_cabinet_sideways_door")), planks, TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCabinetSidewaysGlassDoor(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_cabinet_sideways_glass_door")), planks, TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(block, s), blockModels));

            createModel(blockModels, createKnifeBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_knife_block")), planks, TextureMapping.getBlockTexture(block, s), blockModels));
        }));

        createModel(blockModels, createKitchenCabinet(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_kitchen_cabinet")), planks, blockModels));
        createModel(blockModels, createKitchenCabinetInnerCorner(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_kitchen_cabinet_inner_corner")), planks, blockModels));
        createModel(blockModels, createKitchenCabinetOuterCorner(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_kitchen_cabinet_outer_corner")), planks, blockModels));
        createModel(blockModels, createOpenKitchenCabinet(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_kitchen_cabinet_open")), planks, blockModels));
        createModel(blockModels, createKitchenCabinetShelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_kitchen_cabinet_shelf")), planks, TextureMapping.getBlockTexture(CHAIN), blockModels));
        createModel(blockModels, createTableBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_table")), planks, blockModels));

        WOOL_COLORS.forEach((block, color) -> BlockFamilies.getAllFamilies().toList().forEach(blockFamily -> {
            if (blockFamily.getBaseBlock() == planks) {
                String wool_name = block.getDescriptionId().replaceFirst("block.minecraft.", "");
                createModel(blockModels, createChairBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+wool_name+"_chair")), planks, TextureMapping.getBlockTexture(blockFamily.get(BlockFamily.Variant.TRAPDOOR)), TextureMapping.getBlockTexture(block), blockModels));
                createModel(blockModels, createTableLampBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+color+"_table_lamp")), planks, TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(log, "_top"), TextureMapping.getBlockTexture(strippedLog), TextureMapping.getBlockTexture(REDSTONE_LAMP), TextureMapping.getBlockTexture(REDSTONE_LAMP, "_on"), TextureMapping.getBlockTexture(block), blockModels));
            }
        }));
    }
    
    private Block getBlockFromResourceLocation(ResourceLocation location) {
        return BuiltInRegistries.BLOCK.getValue(location);
    }

    private MultiVariantGenerator createCrate(Block block, Block planks, Block log, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.CRATE.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.LOG, TextureMapping.getBlockTexture(log)), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, model), Variant.variant().with(MODEL, model).with(Y_ROT, Rotation.R90));
    }

    private MultiVariantGenerator createKitchenTiles(Block block, ResourceLocation tile_base, ResourceLocation tile_2, BlockModelGenerators blockModels) {
        return BlockModelGenerators.createSimpleBlock(block, ModelTemplates.KITCHEN_TILES.create(block, new TextureMapping().put(TextureSlot.PARTICLE, tile_base).put(TextureSlots.TILE_BASE, tile_base).put(TextureSlots.TILE_2, tile_2), blockModels.modelOutput));

    }

    private MultiVariantGenerator createKitchenCounter(Block block, Block counter, ResourceLocation top, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.KITCHEN_COUNTER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCounterInnerCorner(Block block, Block counter, ResourceLocation top, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.KITCHEN_COUNTER_INNER_CORNER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCounterOuterCorner(Block block, Block counter, ResourceLocation top, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.KITCHEN_COUNTER_OUTER_CORNER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createOpenKitchenCounter(Block block, Block counter, ResourceLocation top, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.KITCHEN_COUNTER_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCounterDoor(Block block, Block counter, ResourceLocation top, ResourceLocation door, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door);
        ResourceLocation closed = ModelTemplates.KITCHEN_COUNTER_DOOR.create(block, mapping, blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.KITCHEN_COUNTER_DOOR_OPEN.create(block, mapping, blockModels.modelOutput);
        ResourceLocation closed_mirrored = ModelTemplates.KITCHEN_COUNTER_DOOR_MIRRORED.create(block, mapping, blockModels.modelOutput);
        ResourceLocation open_mirrored = ModelTemplates.KITCHEN_COUNTER_DOOR_MIRRORED_OPEN.create(block, mapping, blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, closed)).with(
                PropertyDispatch.properties(KitchenCounterContainerDoorBlock.HINGE, KitchenCounterContainerDoorBlock.OPEN)
                        .select(DoorHingeSide.LEFT, false, Variant.variant().with(MODEL, closed_mirrored))
                        .select(DoorHingeSide.LEFT, true, Variant.variant().with(MODEL, open_mirrored))
                        .select(DoorHingeSide.RIGHT, false, Variant.variant().with(MODEL, closed))
                        .select(DoorHingeSide.RIGHT, true, Variant.variant().with(MODEL, open))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCounterGlassDoor(Block block, Block counter, ResourceLocation top, ResourceLocation door, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door).put(TextureSlots.GLASS, TextureMapping.getBlockTexture(GLASS));
        ResourceLocation closed = ModelTemplates.KITCHEN_COUNTER_GLASS_DOOR.create(block, mapping, blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.KITCHEN_COUNTER_GLASS_DOOR_OPEN.create(block, mapping, blockModels.modelOutput);
        ResourceLocation closed_mirrored = ModelTemplates.KITCHEN_COUNTER_GLASS_DOOR_MIRRORED.create(block, mapping, blockModels.modelOutput);
        ResourceLocation open_mirrored = ModelTemplates.KITCHEN_COUNTER_GLASS_DOOR_MIRRORED_OPEN.create(block, mapping, blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, closed)).with(
                PropertyDispatch.properties(KitchenCounterContainerDoorBlock.HINGE, KitchenCounterContainerDoorBlock.OPEN)
                        .select(DoorHingeSide.LEFT, false, Variant.variant().with(MODEL, closed_mirrored))
                        .select(DoorHingeSide.LEFT, true, Variant.variant().with(MODEL, open_mirrored))
                        .select(DoorHingeSide.RIGHT, false, Variant.variant().with(MODEL, closed))
                        .select(DoorHingeSide.RIGHT, true, Variant.variant().with(MODEL, open))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCounterSmallDrawer(Block block, Block counter, ResourceLocation top, ResourceLocation door, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door);
        ResourceLocation closed = ModelTemplates.KITCHEN_COUNTER_SMALL_DRAWER.create(block, mapping, blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.KITCHEN_COUNTER_SMALL_DRAWER_OPEN.create(block, mapping, blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, closed)).with(
                PropertyDispatch.property(KitchenCounterContainerDrawerBlock.OPEN)
                        .select(false, Variant.variant().with(MODEL, closed))
                        .select(true, Variant.variant().with(MODEL, open))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCounterBigDrawer(Block block, Block counter, ResourceLocation top, ResourceLocation door, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door);
        ResourceLocation closed = ModelTemplates.KITCHEN_COUNTER_BIG_DRAWER.create(block, mapping, blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.KITCHEN_COUNTER_BIG_DRAWER_OPEN.create(block, mapping, blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, closed)).with(
                PropertyDispatch.property(KitchenCounterContainerDrawerBlock.OPEN)
                        .select(false, Variant.variant().with(MODEL, closed))
                        .select(true, Variant.variant().with(MODEL, open))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCounterSink(Block block, Block counter, ResourceLocation top, BlockModelGenerators blockModels) {
        TextureMapping baseMapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.FAUCET, TextureMapping.getBlockTexture(ANVIL));
        TextureMapping fillMapping = baseMapping.put(TextureSlots.WATER, TextureMapping.getBlockTexture(WATER).withSuffix("_still"));
        ResourceLocation empty = ModelTemplates.KITCHEN_COUNTER_SINK_EMPTY.create(block, baseMapping, blockModels.modelOutput);
        ResourceLocation empty_on = ModelTemplates.KITCHEN_COUNTER_SINK_EMPTY_ON.create(block, baseMapping.put(TextureSlots.STREAM, TextureMapping.getBlockTexture(WATER).withSuffix("_flow")), blockModels.modelOutput);
        ResourceLocation level_1 = ModelTemplates.KITCHEN_COUNTER_SINK_LEVEL_1.create(block, fillMapping, blockModels.modelOutput);
        ResourceLocation level_1_on = ModelTemplates.KITCHEN_COUNTER_SINK_LEVEL_1_ON.create(block, fillMapping.put(TextureSlots.STREAM, TextureMapping.getBlockTexture(WATER).withSuffix("_flow")), blockModels.modelOutput);
        ResourceLocation level_2 = ModelTemplates.KITCHEN_COUNTER_SINK_LEVEL_2.create(block, fillMapping, blockModels.modelOutput);
        ResourceLocation level_2_on = ModelTemplates.KITCHEN_COUNTER_SINK_LEVEL_2_ON.create(block, fillMapping.put(TextureSlots.STREAM, TextureMapping.getBlockTexture(WATER).withSuffix("_flow")), blockModels.modelOutput);
        ResourceLocation level_3 = ModelTemplates.KITCHEN_COUNTER_SINK_LEVEL_3.create(block, fillMapping, blockModels.modelOutput);
        ResourceLocation level_3_on = ModelTemplates.KITCHEN_COUNTER_SINK_LEVEL_3_ON.create(block, fillMapping.put(TextureSlots.STREAM, TextureMapping.getBlockTexture(WATER).withSuffix("_flow")), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, empty)).with(
                PropertyDispatch.properties(KitchenCounterSinkBlock.ON, KitchenCounterSinkBlock.LEVEL)
                        .select(true, 0, Variant.variant().with(MODEL, empty_on))
                        .select(false, 0, Variant.variant().with(MODEL, empty))
                        .select(true, 1, Variant.variant().with(MODEL, level_1_on))
                        .select(false, 1, Variant.variant().with(MODEL, level_1))
                        .select(true, 2, Variant.variant().with(MODEL, level_2_on))
                        .select(false, 2, Variant.variant().with(MODEL, level_2))
                        .select(true, 3, Variant.variant().with(MODEL, level_3_on))
                        .select(false, 3, Variant.variant().with(MODEL, level_3))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCounterSmoker(Block block, Block counter, ResourceLocation top, ResourceLocation furnace, ResourceLocation furnace_lit, BlockModelGenerators blockModels) {
        ResourceLocation unlit = ModelTemplates.KITCHEN_COUNTER_SMOKER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.FURNACE, furnace), blockModels.modelOutput);
        ResourceLocation lit = ModelTemplates.KITCHEN_COUNTER_SMOKER_LIT.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.FURNACE, furnace_lit), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, unlit)).with(
                PropertyDispatch.property(SmokerBlock.LIT)
                        .select(false, Variant.variant().with(MODEL, unlit))
                        .select(true, Variant.variant().with(MODEL, lit))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCabinet(Block block, Block cabinet, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.KITCHEN_CABINET.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCabinetInnerCorner(Block block, Block cabinet, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.KITCHEN_CABINET_INNER_CORNER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCabinetOuterCorner(Block block, Block cabinet, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.KITCHEN_CABINET_OUTER_CORNER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createOpenKitchenCabinet(Block block, Block cabinet, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.KITCHEN_CABINET_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCabinetDoor(Block block, Block cabinet, ResourceLocation door, ResourceLocation handle, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle);
        ResourceLocation closed = ModelTemplates.KITCHEN_CABINET_DOOR.create(block, mapping, blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.KITCHEN_CABINET_DOOR_OPEN.create(block, mapping, blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, closed)).with(
                PropertyDispatch.property(KitchenCabinetContainerDoorBlock.OPEN)
                        .select(false, Variant.variant().with(MODEL, closed))
                        .select(true, Variant.variant().with(MODEL, open))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCabinetGlassDoor(Block block, Block cabinet, ResourceLocation door, ResourceLocation handle, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle).put(TextureSlots.GLASS, TextureMapping.getBlockTexture(GLASS));
        ResourceLocation closed = ModelTemplates.KITCHEN_CABINET_GLASS_DOOR.create(block, mapping, blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.KITCHEN_CABINET_GLASS_DOOR_OPEN.create(block, mapping, blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, closed)).with(
                PropertyDispatch.property(KitchenCabinetContainerDoorBlock.OPEN)
                        .select(false, Variant.variant().with(MODEL, closed))
                        .select(true, Variant.variant().with(MODEL, open))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCabinetSidewaysDoor(Block block, Block cabinet, ResourceLocation door, ResourceLocation handle, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle);
        ResourceLocation closed = ModelTemplates.KITCHEN_CABINET_DOOR_SIDEWAYS.create(block, mapping, blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.KITCHEN_CABINET_DOOR_SIDEWAYS_OPEN.create(block, mapping, blockModels.modelOutput);
        ResourceLocation closed_mirrored = ModelTemplates.KITCHEN_CABINET_DOOR_SIDEWAYS_MIRRORED.create(block, mapping, blockModels.modelOutput);
        ResourceLocation open_mirrored = ModelTemplates.KITCHEN_CABINET_DOOR_SIDEWAYS_MIRRORED_OPEN.create(block, mapping, blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, closed)).with(
                PropertyDispatch.properties(KitchenCabinetContainerSidewaysDoorBlock.HINGE, KitchenCabinetContainerSidewaysDoorBlock.OPEN)
                        .select(DoorHingeSide.LEFT, false, Variant.variant().with(MODEL, closed_mirrored))
                        .select(DoorHingeSide.LEFT, true, Variant.variant().with(MODEL, open_mirrored))
                        .select(DoorHingeSide.RIGHT, false, Variant.variant().with(MODEL, closed))
                        .select(DoorHingeSide.RIGHT, true, Variant.variant().with(MODEL, open))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCabinetSidewaysGlassDoor(Block block, Block cabinet, ResourceLocation door, ResourceLocation handle, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle).put(TextureSlots.GLASS, TextureMapping.getBlockTexture(GLASS));
        ResourceLocation closed = ModelTemplates.KITCHEN_CABINET_GLASS_DOOR_SIDEWAYS.create(block, mapping, blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.KITCHEN_CABINET_GLASS_DOOR_SIDEWAYS_OPEN.create(block, mapping, blockModels.modelOutput);
        ResourceLocation closed_mirrored = ModelTemplates.KITCHEN_CABINET_GLASS_DOOR_SIDEWAYS_MIRRORED.create(block, mapping, blockModels.modelOutput);
        ResourceLocation open_mirrored = ModelTemplates.KITCHEN_CABINET_GLASS_DOOR_SIDEWAYS_MIRRORED_OPEN.create(block, mapping, blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, closed)).with(
                PropertyDispatch.properties(KitchenCabinetContainerSidewaysDoorBlock.HINGE, KitchenCabinetContainerSidewaysDoorBlock.OPEN)
                        .select(DoorHingeSide.LEFT, false, Variant.variant().with(MODEL, closed_mirrored))
                        .select(DoorHingeSide.LEFT, true, Variant.variant().with(MODEL, open_mirrored))
                        .select(DoorHingeSide.RIGHT, false, Variant.variant().with(MODEL, closed))
                        .select(DoorHingeSide.RIGHT, true, Variant.variant().with(MODEL, open))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCabinetShelf(Block block, Block planks, ResourceLocation chain, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.CHAIN, chain);
        ResourceLocation single = ModelTemplates.KITCHEN_CABINET_SHELF_SINGLE.create(block, mapping, blockModels.modelOutput);
        ResourceLocation left = ModelTemplates.KITCHEN_CABINET_SHELF_LEFT.create(block, mapping, blockModels.modelOutput);
        ResourceLocation right = ModelTemplates.KITCHEN_CABINET_SHELF_RIGHT.create(block, mapping, blockModels.modelOutput);
        ResourceLocation middle = ModelTemplates.KITCHEN_CABINET_SHELF_MIDDLE.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, single)).with(
                PropertyDispatch.property(KitchenCabinetShelfBlock.SHAPE)
                        .select(ShelfShape.SINGLE, Variant.variant().with(MODEL, single))
                        .select(ShelfShape.LEFT, Variant.variant().with(MODEL, left))
                        .select(ShelfShape.RIGHT, Variant.variant().with(MODEL, right))
                        .select(ShelfShape.MIDDLE, Variant.variant().with(MODEL, middle))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createFridge(Block block, ResourceLocation fridge, ResourceLocation bars, ResourceLocation handle, BlockModelGenerators blockModels) {
        TextureMapping mapping_bottom = new TextureMapping().put(TextureSlot.PARTICLE, fridge).put(TextureSlots.FRIDGE, fridge).put(TextureSlots.BARS, bars).put(TextureSlots.HANDLE, handle);
        TextureMapping mapping_top = new TextureMapping().put(TextureSlot.PARTICLE, fridge).put(TextureSlots.FRIDGE, fridge).put(TextureSlots.HANDLE, handle);
        ResourceLocation bottom = ModelTemplates.FRIDGE_BOTTOM.create(block, mapping_bottom, blockModels.modelOutput);
        ResourceLocation bottom_open = ModelTemplates.FRIDGE_BOTTOM_OPEN.create(block, mapping_bottom, blockModels.modelOutput);
        ResourceLocation bottom_mirrored = ModelTemplates.FRIDGE_BOTTOM_MIRRORED.create(block, mapping_bottom, blockModels.modelOutput);
        ResourceLocation bottom_mirrored_open = ModelTemplates.FRIDGE_BOTTOM_MIRRORED_OPEN.create(block, mapping_bottom, blockModels.modelOutput);
        ResourceLocation top = ModelTemplates.FRIDGE_TOP.create(block, mapping_top, blockModels.modelOutput);
        ResourceLocation top_open = ModelTemplates.FRIDGE_TOP_OPEN.create(block, mapping_top, blockModels.modelOutput);
        ResourceLocation top_mirrored = ModelTemplates.FRIDGE_TOP_MIRRORED.create(block, mapping_top, blockModels.modelOutput);
        ResourceLocation top_mirrored_open = ModelTemplates.FRIDGE_TOP_MIRRORED_OPEN.create(block, mapping_top, blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, bottom)).with(
                PropertyDispatch.properties(KitchenFridgeBlock.HALF, KitchenFridgeBlock.HINGE, KitchenFridgeBlock.OPEN)
                        .select(DoubleBlockHalf.UPPER, DoorHingeSide.LEFT, false, Variant.variant().with(MODEL, top_mirrored))
                        .select(DoubleBlockHalf.UPPER, DoorHingeSide.LEFT, true, Variant.variant().with(MODEL, top_mirrored_open))
                        .select(DoubleBlockHalf.UPPER, DoorHingeSide.RIGHT, false, Variant.variant().with(MODEL, top))
                        .select(DoubleBlockHalf.UPPER, DoorHingeSide.RIGHT, true, Variant.variant().with(MODEL, top_open))
                        .select(DoubleBlockHalf.LOWER, DoorHingeSide.LEFT, false, Variant.variant().with(MODEL, bottom_mirrored))
                        .select(DoubleBlockHalf.LOWER, DoorHingeSide.LEFT, true, Variant.variant().with(MODEL, bottom_mirrored_open))
                        .select(DoubleBlockHalf.LOWER, DoorHingeSide.RIGHT, false, Variant.variant().with(MODEL, bottom))
                        .select(DoubleBlockHalf.LOWER, DoorHingeSide.RIGHT, true, Variant.variant().with(MODEL, bottom_open))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKnifeBlock(Block block, Block blockTexture, ResourceLocation stand, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.KNIFE_BLOCK.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(blockTexture)).put(TextureSlots.BLOCK, TextureMapping.getBlockTexture(blockTexture)).put(TextureSlots.STAND, stand).put(TextureSlots.HANDLE, TextureMapping.getBlockTexture(ANVIL)).put(TextureSlots.KNIFE, TextureMapping.getBlockTexture(IRON_BLOCK)), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createChairBlock(Block block, Block chair, ResourceLocation decoration, ResourceLocation wool, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.CHAIR.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(chair)).put(TextureSlots.CHAIR, TextureMapping.getBlockTexture(chair)).put(TextureSlots.DECORATION, decoration).put(TextureSlots.WOOL, wool), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createTableBlock(Block block, Block planks, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks));
        ResourceLocation single = ModelTemplates.TABLE_SINGLE.create(block, mapping, blockModels.modelOutput);
        ResourceLocation middle = ModelTemplates.TABLE_MIDDLE.create(block, mapping, blockModels.modelOutput);
        ResourceLocation north = ModelTemplates.TABLE_NORTH.create(block, mapping, blockModels.modelOutput);
        ResourceLocation east = ModelTemplates.TABLE_EAST.create(block, mapping, blockModels.modelOutput);
        ResourceLocation south = ModelTemplates.TABLE_SOUTH.create(block, mapping, blockModels.modelOutput);
        ResourceLocation west = ModelTemplates.TABLE_WEST.create(block, mapping, blockModels.modelOutput);
        ResourceLocation north_east = ModelTemplates.TABLE_NORTH_EAST.create(block, mapping, blockModels.modelOutput);
        ResourceLocation north_west = ModelTemplates.TABLE_NORTH_WEST.create(block, mapping, blockModels.modelOutput);
        ResourceLocation south_east = ModelTemplates.TABLE_SOUTH_EAST.create(block, mapping, blockModels.modelOutput);
        ResourceLocation south_west = ModelTemplates.TABLE_SOUTH_WEST.create(block, mapping, blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, single)).with(
                PropertyDispatch.properties(TableBlock.NORTH, TableBlock.EAST, TableBlock.SOUTH, TableBlock.WEST)
                        .select(false, false, false, false, Variant.variant().with(MODEL, single))
                        .select(true, true, true, true, Variant.variant().with(MODEL, middle))
                        .select(true, false, true, false, Variant.variant().with(MODEL, middle))
                        .select(false, true, false, true, Variant.variant().with(MODEL, middle))
                        .select(true, false, false, false, Variant.variant().with(MODEL, south))
                        .select(false, true, false, false, Variant.variant().with(MODEL, west))
                        .select(false, false, true, false, Variant.variant().with(MODEL, north))
                        .select(false, false, false, true, Variant.variant().with(MODEL, east))
                        .select(true, true, false, false, Variant.variant().with(MODEL, south_west))
                        .select(false, true, true, false, Variant.variant().with(MODEL, north_west))
                        .select(false, false, true, true, Variant.variant().with(MODEL, north_east))
                        .select(true, false, false, true, Variant.variant().with(MODEL, south_east))
                        .select(true, true, true, false, Variant.variant().with(MODEL, middle))
                        .select(false, true, true, true, Variant.variant().with(MODEL, middle))
                        .select(true, false, true, true, Variant.variant().with(MODEL, middle))
                        .select(true, true, false, true, Variant.variant().with(MODEL, middle))
        );
    }

    private MultiVariantGenerator createCurtainBlock(Block block, ResourceLocation wool, ResourceLocation curtain_rod, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, wool).put(TextureSlots.WOOL, wool).put(TextureSlots.CURTAIN_ROD, curtain_rod);
        ResourceLocation small_closed = ModelTemplates.CURTAIN_SMALL_CLOSED.create(block, mapping, blockModels.modelOutput);
        ResourceLocation small_single_open = ModelTemplates.CURTAIN_SMALL_SINGLE_OPEN.create(block, mapping, blockModels.modelOutput);
        ResourceLocation small_left_open = ModelTemplates.CURTAIN_SMALL_LEFT_OPEN.create(block, mapping, blockModels.modelOutput);
        ResourceLocation small_right_open = ModelTemplates.CURTAIN_SMALL_RIGHT_OPEN.create(block, mapping, blockModels.modelOutput);
        ResourceLocation big_top_middle_open = ModelTemplates.CURTAIN_BIG_TOP_MIDDLE_OPEN.create(block, mapping, blockModels.modelOutput);
        ResourceLocation big_top_closed = ModelTemplates.CURTAIN_BIG_TOP_CLOSED.create(block, mapping, blockModels.modelOutput);
        ResourceLocation big_bottom_closed = ModelTemplates.CURTAIN_BIG_BOTTOM_CLOSED.create(block, mapping, blockModels.modelOutput);
        ResourceLocation big_top_single_open = ModelTemplates.CURTAIN_BIG_TOP_SINGLE_OPEN.create(block, mapping, blockModels.modelOutput);
        ResourceLocation big_top_right_open = ModelTemplates.CURTAIN_BIG_TOP_RIGHT_OPEN.create(block, mapping, blockModels.modelOutput);
        ResourceLocation big_top_left_open = ModelTemplates.CURTAIN_BIG_TOP_LEFT_OPEN.create(block, mapping, blockModels.modelOutput);
        ResourceLocation big_bottom_single_open = ModelTemplates.CURTAIN_BIG_BOTTOM_SINGLE_OPEN.create(block, mapping, blockModels.modelOutput);
        ResourceLocation big_bottom_right_open = ModelTemplates.CURTAIN_BIG_BOTTOM_RIGHT_OPEN.create(block, mapping, blockModels.modelOutput);
        ResourceLocation big_bottom_left_open = ModelTemplates.CURTAIN_BIG_BOTTOM_LEFT_OPEN.create(block, mapping, blockModels.modelOutput);
        ResourceLocation big_bottom_middle_open = ModelTemplates.CURTAIN_BIG_BOTTOM_MIDDLE_OPEN.create(block, mapping, blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, small_closed)).with(
                PropertyDispatch.properties(CurtainBlock.CURTAIN_SHAPE, CurtainBlock.LEFT, CurtainBlock.RIGHT, CurtainBlock.OPEN)
                        .select(CurtainShape.SINGLE, false, false, false, Variant.variant().with(MODEL, small_closed))
                        .select(CurtainShape.SINGLE, true, false, false, Variant.variant().with(MODEL, small_closed))
                        .select(CurtainShape.SINGLE, false, true, false, Variant.variant().with(MODEL, small_closed))
                        .select(CurtainShape.SINGLE, true, true, false, Variant.variant().with(MODEL, small_closed))
                        .select(CurtainShape.SINGLE, false, false, true, Variant.variant().with(MODEL, small_single_open))
                        .select(CurtainShape.SINGLE, false, true, true, Variant.variant().with(MODEL, small_right_open))
                        .select(CurtainShape.SINGLE, true, false, true, Variant.variant().with(MODEL, small_left_open))
                        .select(CurtainShape.SINGLE, true, true, true, Variant.variant().with(MODEL, big_top_middle_open))

                        .select(CurtainShape.TOP, false, false, false, Variant.variant().with(MODEL, big_top_closed))
                        .select(CurtainShape.TOP, true, false, false, Variant.variant().with(MODEL, big_top_closed))
                        .select(CurtainShape.TOP, false, true, false, Variant.variant().with(MODEL, big_top_closed))
                        .select(CurtainShape.TOP, true, true, false, Variant.variant().with(MODEL, big_top_closed))
                        .select(CurtainShape.TOP, false, false, true, Variant.variant().with(MODEL, big_top_single_open))
                        .select(CurtainShape.TOP, true, false, true, Variant.variant().with(MODEL, big_top_left_open))
                        .select(CurtainShape.TOP, false, true, true, Variant.variant().with(MODEL, big_top_right_open))
                        .select(CurtainShape.TOP, true, true, true, Variant.variant().with(MODEL, big_top_middle_open))

                        .select(CurtainShape.BOTTOM, false, false, false, Variant.variant().with(MODEL, big_bottom_closed))
                        .select(CurtainShape.BOTTOM, true, false, false, Variant.variant().with(MODEL, big_bottom_closed))
                        .select(CurtainShape.BOTTOM, false, true, false, Variant.variant().with(MODEL, big_bottom_closed))
                        .select(CurtainShape.BOTTOM, true, true, false, Variant.variant().with(MODEL, big_bottom_closed))
                        .select(CurtainShape.BOTTOM, false, false, true, Variant.variant().with(MODEL, big_bottom_single_open))
                        .select(CurtainShape.BOTTOM, true, false, true, Variant.variant().with(MODEL, big_bottom_left_open))
                        .select(CurtainShape.BOTTOM, false, true, true, Variant.variant().with(MODEL, big_bottom_right_open))
                        .select(CurtainShape.BOTTOM, true, true, true, Variant.variant().with(MODEL, big_bottom_middle_open))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createTableLampBlock(Block block, Block planks, ResourceLocation log, ResourceLocation log_top, ResourceLocation stripped_log, ResourceLocation lamp, ResourceLocation lamp_on, ResourceLocation wool, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.TABLE_LAMP.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.LOG, log).put(TextureSlots.LOG_TOP, log_top).put(TextureSlots.STRIPPED_LOG, stripped_log).put(TextureSlots.LAMP, lamp).put(TextureSlots.WOOL, wool), blockModels.modelOutput);
        ResourceLocation model_on = ModelTemplates.TABLE_LAMP_ON.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.LOG, log).put(TextureSlots.LOG_TOP, log_top).put(TextureSlots.STRIPPED_LOG, stripped_log).put(TextureSlots.LAMP, lamp_on).put(TextureSlots.WOOL, wool), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, model)).with(
                PropertyDispatch.property(LampBlock.ON)
                        .select(true, Variant.variant().with(MODEL, model_on))
                        .select(false, Variant.variant().with(MODEL, model))
        );
    }

    private void createModel(BlockModelGenerators blockModels, MultiVariantGenerator generator) {
        blockModels.blockStateOutput.accept(generator);
    }

    private void registerBasicBlockModel(ItemModelOutput output, Block block, String suffix) {
        output.accept(block.asItem(), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(block).withSuffix(suffix)));
    }

    private void registerBasicBlockModel(ItemModelOutput output, Block block) {
        output.accept(block.asItem(), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(block)));
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.listElements().filter((holder) -> {
            return false;
        });
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return BuiltInRegistries.ITEM.listElements().filter((holder) -> {
            return false;
        });
    }

    public static class TextureSlots {
        public static final TextureSlot BLOCK = TextureSlot.create("block");
        public static final TextureSlot STAND = TextureSlot.create("stand");
        public static final TextureSlot PLANKS = TextureSlot.create("planks");
        public static final TextureSlot CHAIR = TextureSlot.create("chair");
        public static final TextureSlot LOG = TextureSlot.create("log");
        public static final TextureSlot LOG_TOP = TextureSlot.create("log_top");
        public static final TextureSlot STRIPPED_LOG = TextureSlot.create("stripped_log");
        public static final TextureSlot STRIPPED_LOG_TOP = TextureSlot.create("stripped_log_top");
        public static final TextureSlot COUNTER = TextureSlot.create("counter");
        public static final TextureSlot CABINET = TextureSlot.create("cabinet");
        public static final TextureSlot FRIDGE = TextureSlot.create("fridge");
        public static final TextureSlot TOP = TextureSlot.create("top");
        public static final TextureSlot WOOL = TextureSlot.create("wool");
        public static final TextureSlot CURTAIN_ROD = TextureSlot.create("curtain_rod");
        public static final TextureSlot LAMP = TextureSlot.create("lamp");
        public static final TextureSlot DECORATION = TextureSlot.create("decoration");
        public static final TextureSlot DOOR = TextureSlot.create("door");
        public static final TextureSlot BARS = TextureSlot.create("bars");
        public static final TextureSlot HANDLE = TextureSlot.create("handle");
        public static final TextureSlot KNIFE = TextureSlot.create("knife");
        public static final TextureSlot GLASS = TextureSlot.create("glass");
        public static final TextureSlot FAUCET = TextureSlot.create("faucet");
        public static final TextureSlot CHAIN = TextureSlot.create("chain");
        public static final TextureSlot WATER = TextureSlot.create("water");
        public static final TextureSlot STREAM = TextureSlot.create("stream");
        public static final TextureSlot FURNACE = TextureSlot.create("furnace");
        public static final TextureSlot TILE_BASE = TextureSlot.create("tile_base");
        public static final TextureSlot TILE_2 = TextureSlot.create("tile_2");
    }

    public static class ModelTemplates {
        public static final ModelTemplate CRATE = getTemplate("crate_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.PLANKS, TextureSlots.LOG);
        public static final ModelTemplate KITCHEN_COUNTER = getTemplate("kitchen_counter_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP);
        public static final ModelTemplate KITCHEN_COUNTER_INNER_CORNER = getTemplate("kitchen_counter_inner_corner_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP);
        public static final ModelTemplate KITCHEN_COUNTER_OUTER_CORNER = getTemplate("kitchen_counter_outer_corner_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP);
        public static final ModelTemplate KITCHEN_COUNTER_OPEN = getTemplate("kitchen_counter_open_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP);
        public static final ModelTemplate KITCHEN_COUNTER_DOOR = getTemplate("kitchen_counter_door_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);
        public static final ModelTemplate KITCHEN_COUNTER_DOOR_OPEN = getTemplate("kitchen_counter_door_open_template", Optional.of("_open"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);
        public static final ModelTemplate KITCHEN_COUNTER_DOOR_MIRRORED = getTemplate("kitchen_counter_door_mirrored_template", Optional.of("_mirrored"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);
        public static final ModelTemplate KITCHEN_COUNTER_DOOR_MIRRORED_OPEN = getTemplate("kitchen_counter_door_mirrored_open_template", Optional.of("_mirrored_open"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);
        public static final ModelTemplate KITCHEN_COUNTER_GLASS_DOOR = getTemplate("kitchen_counter_glass_door_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR, TextureSlots.GLASS);
        public static final ModelTemplate KITCHEN_COUNTER_GLASS_DOOR_OPEN = getTemplate("kitchen_counter_glass_door_open_template", Optional.of("_open"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR, TextureSlots.GLASS);
        public static final ModelTemplate KITCHEN_COUNTER_GLASS_DOOR_MIRRORED = getTemplate("kitchen_counter_glass_door_mirrored_template", Optional.of("_mirrored"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR, TextureSlots.GLASS);
        public static final ModelTemplate KITCHEN_COUNTER_GLASS_DOOR_MIRRORED_OPEN = getTemplate("kitchen_counter_glass_door_mirrored_open_template", Optional.of("_mirrored_open"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR, TextureSlots.GLASS);
        public static final ModelTemplate KITCHEN_COUNTER_SMALL_DRAWER = getTemplate("kitchen_counter_small_drawer_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);
        public static final ModelTemplate KITCHEN_COUNTER_SMALL_DRAWER_OPEN = getTemplate("kitchen_counter_small_drawer_open_template", Optional.of("_open"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);
        public static final ModelTemplate KITCHEN_COUNTER_BIG_DRAWER = getTemplate("kitchen_counter_big_drawer_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);
        public static final ModelTemplate KITCHEN_COUNTER_BIG_DRAWER_OPEN = getTemplate("kitchen_counter_big_drawer_open_template", Optional.of("_open"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);
        public static final ModelTemplate KITCHEN_COUNTER_SINK_EMPTY = getTemplate("kitchen_counter_sink_empty_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.FAUCET);
        public static final ModelTemplate KITCHEN_COUNTER_SINK_EMPTY_ON = getTemplate("kitchen_counter_sink_empty_on_template", Optional.of("_on"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.FAUCET, TextureSlots.STREAM);
        public static final ModelTemplate KITCHEN_COUNTER_SINK_LEVEL_1 = getTemplate("kitchen_counter_sink_level_1_template", Optional.of("_level_1"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.FAUCET, TextureSlots.WATER);
        public static final ModelTemplate KITCHEN_COUNTER_SINK_LEVEL_1_ON = getTemplate("kitchen_counter_sink_level_1_on_template", Optional.of("_level_1_on"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.FAUCET, TextureSlots.WATER, TextureSlots.STREAM);
        public static final ModelTemplate KITCHEN_COUNTER_SINK_LEVEL_2 = getTemplate("kitchen_counter_sink_level_2_template", Optional.of("_level_2"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.FAUCET, TextureSlots.WATER);
        public static final ModelTemplate KITCHEN_COUNTER_SINK_LEVEL_2_ON = getTemplate("kitchen_counter_sink_level_2_on_template", Optional.of("_level_2_on"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.FAUCET, TextureSlots.WATER, TextureSlots.STREAM);
        public static final ModelTemplate KITCHEN_COUNTER_SINK_LEVEL_3 = getTemplate("kitchen_counter_sink_level_3_template", Optional.of("_level_3"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.FAUCET, TextureSlots.WATER);
        public static final ModelTemplate KITCHEN_COUNTER_SINK_LEVEL_3_ON = getTemplate("kitchen_counter_sink_level_3_on_template", Optional.of("_level_3_on"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.FAUCET, TextureSlots.WATER, TextureSlots.STREAM);
        public static final ModelTemplate KITCHEN_COUNTER_SMOKER = getTemplate("kitchen_counter_smoker_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.FURNACE);
        public static final ModelTemplate KITCHEN_COUNTER_SMOKER_LIT = getTemplate("kitchen_counter_smoker_lit_template", Optional.of("_lit"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.FURNACE);

        public static final ModelTemplate KITCHEN_CABINET = getTemplate("kitchen_cabinet_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.CABINET);
        public static final ModelTemplate KITCHEN_CABINET_INNER_CORNER = getTemplate("kitchen_cabinet_inner_corner_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.CABINET);
        public static final ModelTemplate KITCHEN_CABINET_OUTER_CORNER = getTemplate("kitchen_cabinet_outer_corner_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.CABINET);
        public static final ModelTemplate KITCHEN_CABINET_OPEN = getTemplate("kitchen_cabinet_open_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.CABINET);
        public static final ModelTemplate KITCHEN_CABINET_DOOR = getTemplate("kitchen_cabinet_door_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE);
        public static final ModelTemplate KITCHEN_CABINET_DOOR_OPEN = getTemplate("kitchen_cabinet_door_open_template", Optional.of("_open"), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE);
        public static final ModelTemplate KITCHEN_CABINET_GLASS_DOOR = getTemplate("kitchen_cabinet_glass_door_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE, TextureSlots.GLASS);
        public static final ModelTemplate KITCHEN_CABINET_GLASS_DOOR_OPEN = getTemplate("kitchen_cabinet_glass_door_open_template", Optional.of("_open"), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE, TextureSlots.GLASS);
        public static final ModelTemplate KITCHEN_CABINET_DOOR_SIDEWAYS = getTemplate("kitchen_cabinet_sideways_door_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE);
        public static final ModelTemplate KITCHEN_CABINET_DOOR_SIDEWAYS_OPEN = getTemplate("kitchen_cabinet_sideways_door_open_template", Optional.of("_open"), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE);
        public static final ModelTemplate KITCHEN_CABINET_DOOR_SIDEWAYS_MIRRORED = getTemplate("kitchen_cabinet_sideways_door_mirrored_template", Optional.of("_mirrored"), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE);
        public static final ModelTemplate KITCHEN_CABINET_DOOR_SIDEWAYS_MIRRORED_OPEN = getTemplate("kitchen_cabinet_sideways_door_mirrored_open_template", Optional.of("_mirrored_open"), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE);
        public static final ModelTemplate KITCHEN_CABINET_GLASS_DOOR_SIDEWAYS = getTemplate("kitchen_cabinet_sideways_glass_door_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE, TextureSlots.GLASS);
        public static final ModelTemplate KITCHEN_CABINET_GLASS_DOOR_SIDEWAYS_OPEN = getTemplate("kitchen_cabinet_sideways_glass_door_open_template", Optional.of("_open"), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE, TextureSlots.GLASS);
        public static final ModelTemplate KITCHEN_CABINET_GLASS_DOOR_SIDEWAYS_MIRRORED = getTemplate("kitchen_cabinet_sideways_glass_door_mirrored_template", Optional.of("_mirrored"), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE, TextureSlots.GLASS);
        public static final ModelTemplate KITCHEN_CABINET_GLASS_DOOR_SIDEWAYS_MIRRORED_OPEN = getTemplate("kitchen_cabinet_sideways_glass_door_mirrored_open_template", Optional.of("_mirrored_open"), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE, TextureSlots.GLASS);
        public static final ModelTemplate KITCHEN_CABINET_SHELF_SINGLE = getTemplate("kitchen_cabinet_shelf_single_template", Optional.of("_single"), TextureSlot.PARTICLE, TextureSlots.PLANKS, TextureSlots.CHAIN);
        public static final ModelTemplate KITCHEN_CABINET_SHELF_RIGHT = getTemplate("kitchen_cabinet_shelf_right_template", Optional.of("_right"), TextureSlot.PARTICLE, TextureSlots.PLANKS, TextureSlots.CHAIN);
        public static final ModelTemplate KITCHEN_CABINET_SHELF_LEFT = getTemplate("kitchen_cabinet_shelf_left_template", Optional.of("_left"), TextureSlot.PARTICLE, TextureSlots.PLANKS, TextureSlots.CHAIN);
        public static final ModelTemplate KITCHEN_CABINET_SHELF_MIDDLE = getTemplate("kitchen_cabinet_shelf_middle_template", Optional.of("_middle"), TextureSlot.PARTICLE, TextureSlots.PLANKS);

        public static final ModelTemplate FRIDGE_BOTTOM = getTemplate("kitchen_fridge_bottom_template", Optional.of("_bottom"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.BARS, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_BOTTOM_OPEN = getTemplate("kitchen_fridge_bottom_open_template", Optional.of("_bottom_open"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.BARS, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_BOTTOM_MIRRORED = getTemplate("kitchen_fridge_bottom_mirrored_template", Optional.of("_bottom_mirrored"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.BARS, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_BOTTOM_MIRRORED_OPEN = getTemplate("kitchen_fridge_bottom_mirrored_open_template", Optional.of("_bottom_mirrored_open"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.BARS, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_TOP = getTemplate("kitchen_fridge_top_template", Optional.of("_top"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_TOP_OPEN = getTemplate("kitchen_fridge_top_open_template", Optional.of("_top_open"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_TOP_MIRRORED = getTemplate("kitchen_fridge_top_mirrored_template", Optional.of("_top_mirrored"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_TOP_MIRRORED_OPEN = getTemplate("kitchen_fridge_top_mirrored_open_template", Optional.of("_top_mirrored_open"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.HANDLE);

        public static final ModelTemplate KNIFE_BLOCK = getTemplate("knife_block_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.BLOCK, TextureSlots.STAND, TextureSlots.HANDLE, TextureSlots.KNIFE);

        public static final ModelTemplate CHAIR = getTemplate("wooden_chair_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.CHAIR, TextureSlots.DECORATION, TextureSlots.WOOL);
        public static final ModelTemplate TABLE_SINGLE = getTemplate("wooden_table_single_template", Optional.of("_single"), TextureSlot.PARTICLE, TextureSlots.PLANKS);
        public static final ModelTemplate TABLE_MIDDLE = getTemplate("wooden_table_middle_template", Optional.of("_middle"), TextureSlot.PARTICLE, TextureSlots.PLANKS);
        public static final ModelTemplate TABLE_NORTH = getTemplate("wooden_table_north_template", Optional.of("_north"), TextureSlot.PARTICLE, TextureSlots.PLANKS);
        public static final ModelTemplate TABLE_EAST = getTemplate("wooden_table_east_template", Optional.of("_east"), TextureSlot.PARTICLE, TextureSlots.PLANKS);
        public static final ModelTemplate TABLE_SOUTH = getTemplate("wooden_table_south_template", Optional.of("_south"), TextureSlot.PARTICLE, TextureSlots.PLANKS);
        public static final ModelTemplate TABLE_WEST = getTemplate("wooden_table_west_template", Optional.of("_west"), TextureSlot.PARTICLE, TextureSlots.PLANKS);
        public static final ModelTemplate TABLE_NORTH_EAST = getTemplate("wooden_table_north_east_template", Optional.of("_north_east"), TextureSlot.PARTICLE, TextureSlots.PLANKS);
        public static final ModelTemplate TABLE_NORTH_WEST = getTemplate("wooden_table_north_west_template", Optional.of("_north_west"), TextureSlot.PARTICLE, TextureSlots.PLANKS);
        public static final ModelTemplate TABLE_SOUTH_EAST = getTemplate("wooden_table_south_east_template", Optional.of("_south_east"), TextureSlot.PARTICLE, TextureSlots.PLANKS);
        public static final ModelTemplate TABLE_SOUTH_WEST = getTemplate("wooden_table_south_west_template", Optional.of("_south_west"), TextureSlot.PARTICLE, TextureSlots.PLANKS);

        public static final ModelTemplate CURTAIN_SMALL_CLOSED = getTemplate("curtains_small_closed_template", Optional.of("_small_closed"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_SMALL_SINGLE_OPEN = getTemplate("curtains_small_single_open_template", Optional.of("_small_single_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_SMALL_LEFT_OPEN = getTemplate("curtains_small_left_open_template", Optional.of("_small_left_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_SMALL_RIGHT_OPEN = getTemplate("curtains_small_right_open_template", Optional.of("_small_right_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_BIG_BOTTOM_CLOSED = getTemplate("curtains_big_bottom_closed_template", Optional.of("_big_bottom_closed"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_BIG_TOP_CLOSED = getTemplate("curtains_big_top_closed_template", Optional.of("_big_top_closed"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_BIG_TOP_SINGLE_OPEN = getTemplate("curtains_big_top_single_open_template", Optional.of("_big_top_single_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_BIG_BOTTOM_SINGLE_OPEN = getTemplate("curtains_big_bottom_single_open_template", Optional.of("_big_bottom_single_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_BIG_BOTTOM_LEFT_OPEN = getTemplate("curtains_big_bottom_left_open_template", Optional.of("_big_bottom_left_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_BIG_BOTTOM_RIGHT_OPEN = getTemplate("curtains_big_bottom_right_open_template", Optional.of("_big_bottom_right_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_BIG_BOTTOM_MIDDLE_OPEN = getTemplate("curtains_big_bottom_middle_open_template", Optional.of("_big_bottom_middle_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_BIG_TOP_LEFT_OPEN = getTemplate("curtains_big_top_left_open_template", Optional.of("_big_top_left_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_BIG_TOP_RIGHT_OPEN = getTemplate("curtains_big_top_right_open_template", Optional.of("_big_top_right_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_BIG_TOP_MIDDLE_OPEN = getTemplate("curtains_big_top_middle_open_template", Optional.of("_big_top_middle_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);

        public static final ModelTemplate KITCHEN_TILES = getTemplate("kitchen_tiles_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.TILE_BASE, TextureSlots.TILE_2);

        public static final ModelTemplate TABLE_LAMP = getTemplate("table_lamp_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.LOG, TextureSlots.STRIPPED_LOG, TextureSlots.LOG_TOP, TextureSlots.PLANKS, TextureSlots.WOOL, TextureSlots.LAMP);
        public static final ModelTemplate TABLE_LAMP_ON = getTemplate("table_lamp_template", Optional.of("_on"), TextureSlot.PARTICLE, TextureSlots.LOG, TextureSlots.STRIPPED_LOG, TextureSlots.LOG_TOP, TextureSlots.PLANKS, TextureSlots.WOOL, TextureSlots.LAMP);

        private static ModelTemplate getTemplate(String name, Optional<String> suffix, TextureSlot... slots) {
            return new ModelTemplate(Optional.of(FabulousFurniture.prefix(name).withPrefix("block/template/")), suffix, slots);
        }
    }
}