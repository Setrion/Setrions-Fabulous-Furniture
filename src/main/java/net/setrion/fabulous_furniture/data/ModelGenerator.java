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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SmokerBlock;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.setrion.fabulous_furniture.FabulousFurniture;
import net.setrion.fabulous_furniture.registry.SFFBlocks;
import net.setrion.fabulous_furniture.util.FurnitureFamilies;
import net.setrion.fabulous_furniture.util.FurnitureFamily;
import net.setrion.fabulous_furniture.world.level.block.*;
import net.setrion.fabulous_furniture.world.level.block.state.properties.CurtainShape;
import net.setrion.fabulous_furniture.world.level.block.state.properties.ShelfShape;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModelGenerator extends ModelProvider {

    /*
    helpermethods for generating models
    WoodenFurniture(Block planks, Block log)
    createChair(Block block, WoodenFurniture) -> planks = WoodenFurniture.getPlanks()
     */

    private final Map<Block, String> COUNTER_TOPS = new HashMap<>(12);
    private final Block[] WOOL_COLORS = new Block[] {Blocks.WHITE_WOOL, Blocks.LIGHT_GRAY_WOOL, Blocks.GRAY_WOOL, Blocks.BLACK_WOOL, Blocks.BROWN_WOOL, Blocks.RED_WOOL, Blocks.ORANGE_WOOL, Blocks.YELLOW_WOOL, Blocks.LIME_WOOL, Blocks.GREEN_WOOL, Blocks.CYAN_WOOL, Blocks.LIGHT_BLUE_WOOL, Blocks.BLUE_WOOL, Blocks.PURPLE_WOOL, Blocks.MAGENTA_WOOL, Blocks.PINK_WOOL};

    public ModelGenerator(PackOutput output) {
        super(output, FabulousFurniture.MOD_ID);
        COUNTER_TOPS.put(Blocks.POLISHED_GRANITE, "");
        COUNTER_TOPS.put(Blocks.POLISHED_DIORITE, "");
        COUNTER_TOPS.put(Blocks.POLISHED_ANDESITE, "");
        COUNTER_TOPS.put(Blocks.POLISHED_DEEPSLATE, "");
        COUNTER_TOPS.put(Blocks.POLISHED_TUFF, "");
        COUNTER_TOPS.put(Blocks.SANDSTONE, "_top");
        COUNTER_TOPS.put(Blocks.RED_SANDSTONE, "_top");
        COUNTER_TOPS.put(Blocks.POLISHED_BLACKSTONE, "");
        COUNTER_TOPS.put(Blocks.GILDED_BLACKSTONE, "");
        COUNTER_TOPS.put(Blocks.SMOOTH_BASALT, "");
        COUNTER_TOPS.put(Blocks.QUARTZ_BLOCK, "_top");
        COUNTER_TOPS.put(Blocks.CALCITE, "");
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        generateWoodenFurniture(FurnitureFamilies.OAK, blockModels);
        generateWoodenFurniture(FurnitureFamilies.SPRUCE, blockModels);
        generateWoodenFurniture(FurnitureFamilies.BIRCH, blockModels);
        generateWoodenFurniture(FurnitureFamilies.JUNGLE, blockModels);
        generateWoodenFurniture(FurnitureFamilies.ACACIA, blockModels);
        generateWoodenFurniture(FurnitureFamilies.CHERRY, blockModels);
        generateWoodenFurniture(FurnitureFamilies.DARK_OAK, blockModels);
        generateWoodenFurniture(FurnitureFamilies.PALE_OAK, blockModels);
        generateWoodenFurniture(FurnitureFamilies.BAMBOO, blockModels);
        generateWoodenFurniture(FurnitureFamilies.MANGROVE, blockModels);
        generateWoodenFurniture(FurnitureFamilies.CRIMSON, blockModels);
        generateWoodenFurniture(FurnitureFamilies.WARPED, blockModels);
        createCurtains(blockModels);
        createKitchenTiles(blockModels);

        blockModels.blockStateOutput.accept(createFridge(SFFBlocks.IRON_FRIDGE.get(), TextureMapping.getBlockTexture(Blocks.IRON_BLOCK), TextureMapping.getBlockTexture(Blocks.IRON_BARS), TextureMapping.getBlockTexture(Blocks.ANVIL), blockModels));
        blockModels.blockStateOutput.accept(createFridge(SFFBlocks.GOLD_FRIDGE.get(), TextureMapping.getBlockTexture(Blocks.GOLD_BLOCK), TextureMapping.getBlockTexture(Blocks.IRON_BARS), TextureMapping.getBlockTexture(Blocks.ANVIL), blockModels));
        blockModels.blockStateOutput.accept(createFridge(SFFBlocks.NETHERITE_FRIDGE.get(), TextureMapping.getBlockTexture(Blocks.NETHERITE_BLOCK), TextureMapping.getBlockTexture(Blocks.IRON_BARS), TextureMapping.getBlockTexture(Blocks.ANVIL), blockModels));
        blockModels.blockStateOutput.accept(createFridge(SFFBlocks.COPPER_FRIDGE.get(), TextureMapping.getBlockTexture(Blocks.COPPER_BLOCK), TextureMapping.getBlockTexture(Blocks.IRON_BARS), TextureMapping.getBlockTexture(Blocks.ANVIL), blockModels));
        blockModels.blockStateOutput.accept(createFridge(SFFBlocks.EXPOSED_COPPER_FRIDGE.get(), TextureMapping.getBlockTexture(Blocks.EXPOSED_COPPER), TextureMapping.getBlockTexture(Blocks.IRON_BARS), TextureMapping.getBlockTexture(Blocks.ANVIL), blockModels));
        blockModels.blockStateOutput.accept(createFridge(SFFBlocks.WEATHERED_COPPER_FRIDGE.get(), TextureMapping.getBlockTexture(Blocks.WEATHERED_COPPER), TextureMapping.getBlockTexture(Blocks.IRON_BARS), TextureMapping.getBlockTexture(Blocks.ANVIL), blockModels));
        blockModels.blockStateOutput.accept(createFridge(SFFBlocks.OXIDIZED_COPPER_FRIDGE.get(), TextureMapping.getBlockTexture(Blocks.OXIDIZED_COPPER), TextureMapping.getBlockTexture(Blocks.IRON_BARS), TextureMapping.getBlockTexture(Blocks.ANVIL), blockModels));
        blockModels.blockStateOutput.accept(createFridge(SFFBlocks.WAXED_COPPER_FRIDGE.get(), TextureMapping.getBlockTexture(Blocks.COPPER_BLOCK), TextureMapping.getBlockTexture(Blocks.IRON_BARS), TextureMapping.getBlockTexture(Blocks.ANVIL), blockModels));
        blockModels.blockStateOutput.accept(createFridge(SFFBlocks.WAXED_EXPOSED_COPPER_FRIDGE.get(), TextureMapping.getBlockTexture(Blocks.EXPOSED_COPPER), TextureMapping.getBlockTexture(Blocks.IRON_BARS), TextureMapping.getBlockTexture(Blocks.ANVIL), blockModels));
        blockModels.blockStateOutput.accept(createFridge(SFFBlocks.WAXED_WEATHERED_COPPER_FRIDGE.get(), TextureMapping.getBlockTexture(Blocks.WEATHERED_COPPER), TextureMapping.getBlockTexture(Blocks.IRON_BARS), TextureMapping.getBlockTexture(Blocks.ANVIL), blockModels));
        blockModels.blockStateOutput.accept(createFridge(SFFBlocks.WAXED_OXIDIZED_COPPER_FRIDGE.get(), TextureMapping.getBlockTexture(Blocks.OXIDIZED_COPPER), TextureMapping.getBlockTexture(Blocks.IRON_BARS), TextureMapping.getBlockTexture(Blocks.ANVIL), blockModels));

        Collection<Block> blocks = SFFBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toList());
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
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.WHITE_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.WHITE_WOOL), TextureMapping.getBlockTexture(Blocks.IRON_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.WHITE_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.WHITE_WOOL), TextureMapping.getBlockTexture(Blocks.GOLD_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.WHITE_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.WHITE_WOOL), TextureMapping.getBlockTexture(Blocks.NETHERITE_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.WHITE_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.WHITE_WOOL), TextureMapping.getBlockTexture(Blocks.COPPER_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.LIGHT_GRAY_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.LIGHT_GRAY_WOOL), TextureMapping.getBlockTexture(Blocks.IRON_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.LIGHT_GRAY_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.LIGHT_GRAY_WOOL), TextureMapping.getBlockTexture(Blocks.GOLD_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.LIGHT_GRAY_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.LIGHT_GRAY_WOOL), TextureMapping.getBlockTexture(Blocks.NETHERITE_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.LIGHT_GRAY_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.LIGHT_GRAY_WOOL), TextureMapping.getBlockTexture(Blocks.COPPER_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.GRAY_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.GRAY_WOOL), TextureMapping.getBlockTexture(Blocks.IRON_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.GRAY_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.GRAY_WOOL), TextureMapping.getBlockTexture(Blocks.GOLD_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.GRAY_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.GRAY_WOOL), TextureMapping.getBlockTexture(Blocks.NETHERITE_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.GRAY_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.GRAY_WOOL), TextureMapping.getBlockTexture(Blocks.COPPER_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.BLACK_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.BLACK_WOOL), TextureMapping.getBlockTexture(Blocks.IRON_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.BLACK_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.BLACK_WOOL), TextureMapping.getBlockTexture(Blocks.GOLD_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.BLACK_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.BLACK_WOOL), TextureMapping.getBlockTexture(Blocks.NETHERITE_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.BLACK_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.BLACK_WOOL), TextureMapping.getBlockTexture(Blocks.COPPER_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.BROWN_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.BROWN_WOOL), TextureMapping.getBlockTexture(Blocks.IRON_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.BROWN_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.BROWN_WOOL), TextureMapping.getBlockTexture(Blocks.GOLD_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.BROWN_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.BROWN_WOOL), TextureMapping.getBlockTexture(Blocks.NETHERITE_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.BROWN_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.BROWN_WOOL), TextureMapping.getBlockTexture(Blocks.COPPER_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.RED_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.RED_WOOL), TextureMapping.getBlockTexture(Blocks.IRON_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.RED_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.RED_WOOL), TextureMapping.getBlockTexture(Blocks.GOLD_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.RED_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.RED_WOOL), TextureMapping.getBlockTexture(Blocks.NETHERITE_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.RED_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.RED_WOOL), TextureMapping.getBlockTexture(Blocks.COPPER_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.ORANGE_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.ORANGE_WOOL), TextureMapping.getBlockTexture(Blocks.IRON_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.ORANGE_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.ORANGE_WOOL), TextureMapping.getBlockTexture(Blocks.GOLD_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.ORANGE_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.ORANGE_WOOL), TextureMapping.getBlockTexture(Blocks.NETHERITE_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.ORANGE_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.ORANGE_WOOL), TextureMapping.getBlockTexture(Blocks.COPPER_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.YELLOW_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.YELLOW_WOOL), TextureMapping.getBlockTexture(Blocks.IRON_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.YELLOW_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.YELLOW_WOOL), TextureMapping.getBlockTexture(Blocks.GOLD_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.YELLOW_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.YELLOW_WOOL), TextureMapping.getBlockTexture(Blocks.NETHERITE_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.YELLOW_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.YELLOW_WOOL), TextureMapping.getBlockTexture(Blocks.COPPER_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.LIME_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.LIME_WOOL), TextureMapping.getBlockTexture(Blocks.IRON_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.LIME_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.LIME_WOOL), TextureMapping.getBlockTexture(Blocks.GOLD_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.LIME_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.LIME_WOOL), TextureMapping.getBlockTexture(Blocks.NETHERITE_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.LIME_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.LIME_WOOL), TextureMapping.getBlockTexture(Blocks.COPPER_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.GREEN_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.GREEN_WOOL), TextureMapping.getBlockTexture(Blocks.IRON_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.GREEN_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.GREEN_WOOL), TextureMapping.getBlockTexture(Blocks.GOLD_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.GREEN_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.GREEN_WOOL), TextureMapping.getBlockTexture(Blocks.NETHERITE_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.GREEN_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.GREEN_WOOL), TextureMapping.getBlockTexture(Blocks.COPPER_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.CYAN_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.CYAN_WOOL), TextureMapping.getBlockTexture(Blocks.IRON_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.CYAN_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.CYAN_WOOL), TextureMapping.getBlockTexture(Blocks.GOLD_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.CYAN_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.CYAN_WOOL), TextureMapping.getBlockTexture(Blocks.NETHERITE_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.CYAN_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.CYAN_WOOL), TextureMapping.getBlockTexture(Blocks.COPPER_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.LIGHT_BLUE_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.LIGHT_BLUE_WOOL), TextureMapping.getBlockTexture(Blocks.IRON_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.LIGHT_BLUE_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.LIGHT_BLUE_WOOL), TextureMapping.getBlockTexture(Blocks.GOLD_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.LIGHT_BLUE_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.LIGHT_BLUE_WOOL), TextureMapping.getBlockTexture(Blocks.NETHERITE_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.LIGHT_BLUE_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.LIGHT_BLUE_WOOL), TextureMapping.getBlockTexture(Blocks.COPPER_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.BLUE_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.BLUE_WOOL), TextureMapping.getBlockTexture(Blocks.IRON_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.BLUE_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.BLUE_WOOL), TextureMapping.getBlockTexture(Blocks.GOLD_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.BLUE_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.BLUE_WOOL), TextureMapping.getBlockTexture(Blocks.NETHERITE_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.BLUE_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.BLUE_WOOL), TextureMapping.getBlockTexture(Blocks.COPPER_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.PURPLE_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.PURPLE_WOOL), TextureMapping.getBlockTexture(Blocks.IRON_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.PURPLE_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.PURPLE_WOOL), TextureMapping.getBlockTexture(Blocks.GOLD_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.PURPLE_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.PURPLE_WOOL), TextureMapping.getBlockTexture(Blocks.NETHERITE_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.PURPLE_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.PURPLE_WOOL), TextureMapping.getBlockTexture(Blocks.COPPER_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.MAGENTA_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.MAGENTA_WOOL), TextureMapping.getBlockTexture(Blocks.IRON_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.MAGENTA_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.MAGENTA_WOOL), TextureMapping.getBlockTexture(Blocks.GOLD_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.MAGENTA_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.MAGENTA_WOOL), TextureMapping.getBlockTexture(Blocks.NETHERITE_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.MAGENTA_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.MAGENTA_WOOL), TextureMapping.getBlockTexture(Blocks.COPPER_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.PINK_IRON_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.PINK_WOOL), TextureMapping.getBlockTexture(Blocks.IRON_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.PINK_GOLD_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.PINK_WOOL), TextureMapping.getBlockTexture(Blocks.GOLD_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.PINK_NETHERITE_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.PINK_WOOL), TextureMapping.getBlockTexture(Blocks.NETHERITE_BLOCK), blockModels));
        blockModels.blockStateOutput.accept(createCurtainBlock(SFFBlocks.PINK_COPPER_CURTAINS.get(), TextureMapping.getBlockTexture(Blocks.PINK_WOOL), TextureMapping.getBlockTexture(Blocks.COPPER_BLOCK), blockModels));
    }

    private void createKitchenTiles(BlockModelGenerators blockModels) {
        blockModels.blockStateOutput.accept(createKitchenTiles(SFFBlocks.WHITE_LIGHT_GRAY_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(Blocks.WHITE_CONCRETE), TextureMapping.getBlockTexture(Blocks.LIGHT_GRAY_CONCRETE), blockModels));
        blockModels.blockStateOutput.accept(createKitchenTiles(SFFBlocks.WHITE_GRAY_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(Blocks.WHITE_CONCRETE), TextureMapping.getBlockTexture(Blocks.GRAY_CONCRETE), blockModels));
        blockModels.blockStateOutput.accept(createKitchenTiles(SFFBlocks.WHITE_BLACK_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(Blocks.WHITE_CONCRETE), TextureMapping.getBlockTexture(Blocks.BLACK_CONCRETE), blockModels));
        blockModels.blockStateOutput.accept(createKitchenTiles(SFFBlocks.WHITE_BROWN_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(Blocks.WHITE_CONCRETE), TextureMapping.getBlockTexture(Blocks.BROWN_CONCRETE), blockModels));
        blockModels.blockStateOutput.accept(createKitchenTiles(SFFBlocks.WHITE_RED_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(Blocks.WHITE_CONCRETE), TextureMapping.getBlockTexture(Blocks.RED_CONCRETE), blockModels));
        blockModels.blockStateOutput.accept(createKitchenTiles(SFFBlocks.WHITE_ORANGE_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(Blocks.WHITE_CONCRETE), TextureMapping.getBlockTexture(Blocks.ORANGE_CONCRETE), blockModels));
        blockModels.blockStateOutput.accept(createKitchenTiles(SFFBlocks.WHITE_YELLOW_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(Blocks.WHITE_CONCRETE), TextureMapping.getBlockTexture(Blocks.YELLOW_CONCRETE), blockModels));
        blockModels.blockStateOutput.accept(createKitchenTiles(SFFBlocks.WHITE_LIME_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(Blocks.WHITE_CONCRETE), TextureMapping.getBlockTexture(Blocks.LIME_CONCRETE), blockModels));
        blockModels.blockStateOutput.accept(createKitchenTiles(SFFBlocks.WHITE_GREEN_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(Blocks.WHITE_CONCRETE), TextureMapping.getBlockTexture(Blocks.GREEN_CONCRETE), blockModels));
        blockModels.blockStateOutput.accept(createKitchenTiles(SFFBlocks.WHITE_CYAN_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(Blocks.WHITE_CONCRETE), TextureMapping.getBlockTexture(Blocks.CYAN_CONCRETE), blockModels));
        blockModels.blockStateOutput.accept(createKitchenTiles(SFFBlocks.WHITE_LIGHT_BLUE_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(Blocks.WHITE_CONCRETE), TextureMapping.getBlockTexture(Blocks.LIGHT_BLUE_CONCRETE), blockModels));
        blockModels.blockStateOutput.accept(createKitchenTiles(SFFBlocks.WHITE_BLUE_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(Blocks.WHITE_CONCRETE), TextureMapping.getBlockTexture(Blocks.BLUE_CONCRETE), blockModels));
        blockModels.blockStateOutput.accept(createKitchenTiles(SFFBlocks.WHITE_PURPLE_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(Blocks.WHITE_CONCRETE), TextureMapping.getBlockTexture(Blocks.PURPLE_CONCRETE), blockModels));
        blockModels.blockStateOutput.accept(createKitchenTiles(SFFBlocks.WHITE_MAGENTA_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(Blocks.WHITE_CONCRETE), TextureMapping.getBlockTexture(Blocks.MAGENTA_CONCRETE), blockModels));
        blockModels.blockStateOutput.accept(createKitchenTiles(SFFBlocks.WHITE_PINK_KITCHEN_TILES.get(), TextureMapping.getBlockTexture(Blocks.WHITE_CONCRETE), TextureMapping.getBlockTexture(Blocks.PINK_CONCRETE), blockModels));
    }

    private void generateWoodenFurniture(FurnitureFamily family, BlockModelGenerators blockModels) {
        blockModels.blockStateOutput.accept(createCrate(family.get(FurnitureFamily.Variant.CRATE).get(Blocks.AIR), family.getBasePlanks(), family.getBaseLog(), blockModels));
        COUNTER_TOPS.forEach(((block, s) -> {
            blockModels.blockStateOutput.accept(createKitchenCounter(family.get(FurnitureFamily.Variant.KITCHEN_COUNTER).get(block), family.getBasePlanks(), TextureMapping.getBlockTexture(block, s), blockModels));
            blockModels.blockStateOutput.accept(createKitchenCounterInnerCorner(family.get(FurnitureFamily.Variant.KITCHEN_COUNTER_INNER_CORNER).get(block), family.getBasePlanks(), TextureMapping.getBlockTexture(block, s), blockModels));
            blockModels.blockStateOutput.accept(createKitchenCounterOuterCorner(family.get(FurnitureFamily.Variant.KITCHEN_COUNTER_OUTER_CORNER).get(block), family.getBasePlanks(), TextureMapping.getBlockTexture(block, s), blockModels));
            blockModels.blockStateOutput.accept(createOpenKitchenCounter(family.get(FurnitureFamily.Variant.KITCHEN_COUNTER_OPEN).get(block), family.getBasePlanks(), TextureMapping.getBlockTexture(block, s), blockModels));
            blockModels.blockStateOutput.accept(createKitchenCounterDoor(family.get(FurnitureFamily.Variant.KITCHEN_COUNTER_DOOR).get(block), family.getBasePlanks(), TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(family.getBaseLog()), blockModels));
            blockModels.blockStateOutput.accept(createKitchenCounterGlassDoor(family.get(FurnitureFamily.Variant.KITCHEN_COUNTER_GLASS_DOOR).get(block), family.getBasePlanks(), TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(family.getBaseLog()), blockModels));
            blockModels.blockStateOutput.accept(createKitchenCounterSmallDrawer(family.get(FurnitureFamily.Variant.KITCHEN_COUNTER_SMALL_DRAWER).get(block), family.getBasePlanks(), TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(family.getBaseLog()), blockModels));
            blockModels.blockStateOutput.accept(createKitchenCounterBigDrawer(family.get(FurnitureFamily.Variant.KITCHEN_COUNTER_BIG_DRAWER).get(block), family.getBasePlanks(), TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(family.getBaseLog()), blockModels));
            blockModels.blockStateOutput.accept(createKitchenCounterSink(family.get(FurnitureFamily.Variant.KITCHEN_COUNTER_SINK).get(block), family.getBasePlanks(), TextureMapping.getBlockTexture(block, s), blockModels));
            blockModels.blockStateOutput.accept(createKitchenCounterSmoker(family.get(FurnitureFamily.Variant.KITCHEN_COUNTER_SMOKER).get(block), family.getBasePlanks(), TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(Blocks.SMOKER, "_front"), TextureMapping.getBlockTexture(Blocks.SMOKER, "_front_on"), blockModels));
            blockModels.blockStateOutput.accept(createKitchenCabinetDoor(family.get(FurnitureFamily.Variant.KITCHEN_CABINET_DOOR).get(block), family.getBasePlanks(), TextureMapping.getBlockTexture(family.getBaseLog()), TextureMapping.getBlockTexture(block, s), blockModels));
            blockModels.blockStateOutput.accept(createKitchenCabinetGlassDoor(family.get(FurnitureFamily.Variant.KITCHEN_CABINET_GLASS_DOOR).get(block), family.getBasePlanks(), TextureMapping.getBlockTexture(family.getBaseLog()), TextureMapping.getBlockTexture(block, s), blockModels));
            blockModels.blockStateOutput.accept(createKitchenCabinetSidewaysDoor(family.get(FurnitureFamily.Variant.KITCHEN_CABINET_SIDEWAYS_DOOR).get(block), family.getBasePlanks(), TextureMapping.getBlockTexture(family.getBaseLog()), TextureMapping.getBlockTexture(block, s), blockModels));
            blockModels.blockStateOutput.accept(createKitchenCabinetSidewaysGlassDoor(family.get(FurnitureFamily.Variant.KITCHEN_CABINET_SIDEWAYS_GLASS_DOOR).get(block), family.getBasePlanks(), TextureMapping.getBlockTexture(family.getBaseLog()), TextureMapping.getBlockTexture(block, s), blockModels));

            blockModels.blockStateOutput.accept(createKnifeBlock(family.get(FurnitureFamily.Variant.KNIFE_BLOCK).get(block), family.getBasePlanks(), TextureMapping.getBlockTexture(block, s), blockModels));
        }));

        blockModels.blockStateOutput.accept(createKitchenCabinet(family.get(FurnitureFamily.Variant.KITCHEN_CABINET).get(Blocks.AIR), family.getBasePlanks(), blockModels));
        blockModels.blockStateOutput.accept(createKitchenCabinetInnerCorner(family.get(FurnitureFamily.Variant.KITCHEN_CABINET_INNER_CORNER).get(Blocks.AIR), family.getBasePlanks(), blockModels));
        blockModels.blockStateOutput.accept(createKitchenCabinetOuterCorner(family.get(FurnitureFamily.Variant.KITCHEN_CABINET_OUTER_CORNER).get(Blocks.AIR), family.getBasePlanks(), blockModels));
        blockModels.blockStateOutput.accept(createOpenKitchenCabinet(family.get(FurnitureFamily.Variant.KITCHEN_CABINET_OPEN).get(Blocks.AIR), family.getBasePlanks(), blockModels));
        blockModels.blockStateOutput.accept(createKitchenCabinetShelf(family.get(FurnitureFamily.Variant.KITCHEN_CABINET_SHELF).get(Blocks.AIR), family.getBasePlanks(), TextureMapping.getBlockTexture(Blocks.CHAIN), blockModels));
        blockModels.blockStateOutput.accept(createTableBlock(family.get(FurnitureFamily.Variant.TABLE).get(Blocks.AIR), family.getBasePlanks(), blockModels));

        Arrays.stream(WOOL_COLORS).toList().forEach((block -> {
            BlockFamilies.getAllFamilies().toList().forEach(blockFamily -> {
                if (blockFamily.getBaseBlock() == family.getBasePlanks()) {
                    blockModels.blockStateOutput.accept(createChairBlock(family.get(FurnitureFamily.Variant.CHAIR).get(block), family.getBasePlanks(), TextureMapping.getBlockTexture(blockFamily.get(BlockFamily.Variant.TRAPDOOR)), TextureMapping.getBlockTexture(block), blockModels));

                }
            });
        }));
    }

    private MultiVariantGenerator createCrate(Block block, Block planks, Block log, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.CRATE.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.LOG, TextureMapping.getBlockTexture(log)), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model), Variant.variant().with(VariantProperties.MODEL, model).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90));
    }

    private MultiVariantGenerator createKitchenTiles(Block block, ResourceLocation tile_base, ResourceLocation tile_2, BlockModelGenerators blockModels) {
        return BlockModelGenerators.createSimpleBlock(block, ModelTemplates.KITCHEN_TILES.create(block, new TextureMapping().put(TextureSlot.PARTICLE, tile_base).put(TextureSlots.TILE_BASE, tile_base).put(TextureSlots.TILE_2, tile_2), blockModels.modelOutput));

    }

    private MultiVariantGenerator createKitchenCounter(Block block, Block counter, ResourceLocation top, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.KITCHEN_COUNTER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCounterInnerCorner(Block block, Block counter, ResourceLocation top, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.KITCHEN_COUNTER_INNER_CORNER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCounterOuterCorner(Block block, Block counter, ResourceLocation top, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.KITCHEN_COUNTER_OUTER_CORNER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createOpenKitchenCounter(Block block, Block counter, ResourceLocation top, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.KITCHEN_COUNTER_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCounterDoor(Block block, Block counter, ResourceLocation top, ResourceLocation door, BlockModelGenerators blockModels) {
        ResourceLocation closed = ModelTemplates.KITCHEN_COUNTER_DOOR.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door), blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.KITCHEN_COUNTER_DOOR_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door), blockModels.modelOutput);
        ResourceLocation closed_mirrored = ModelTemplates.KITCHEN_COUNTER_DOOR_MIRRORED.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door), blockModels.modelOutput);
        ResourceLocation open_mirrored = ModelTemplates.KITCHEN_COUNTER_DOOR_MIRRORED_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, closed)).with(
                PropertyDispatch.properties(KitchenCounterContainerDoorBlock.HINGE, KitchenCounterContainerDoorBlock.OPEN)
                        .select(DoorHingeSide.LEFT, false, Variant.variant().with(VariantProperties.MODEL, closed_mirrored))
                        .select(DoorHingeSide.LEFT, true, Variant.variant().with(VariantProperties.MODEL, open_mirrored))
                        .select(DoorHingeSide.RIGHT, false, Variant.variant().with(VariantProperties.MODEL, closed))
                        .select(DoorHingeSide.RIGHT, true, Variant.variant().with(VariantProperties.MODEL, open))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCounterGlassDoor(Block block, Block counter, ResourceLocation top, ResourceLocation door, BlockModelGenerators blockModels) {
        ResourceLocation closed = ModelTemplates.KITCHEN_COUNTER_GLASS_DOOR.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door).put(TextureSlots.GLASS, TextureMapping.getBlockTexture(Blocks.GLASS)), blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.KITCHEN_COUNTER_GLASS_DOOR_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door).put(TextureSlots.GLASS, TextureMapping.getBlockTexture(Blocks.GLASS)), blockModels.modelOutput);
        ResourceLocation closed_mirrored = ModelTemplates.KITCHEN_COUNTER_GLASS_DOOR_MIRRORED.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door).put(TextureSlots.GLASS, TextureMapping.getBlockTexture(Blocks.GLASS)), blockModels.modelOutput);
        ResourceLocation open_mirrored = ModelTemplates.KITCHEN_COUNTER_GLASS_DOOR_MIRRORED_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door).put(TextureSlots.GLASS, TextureMapping.getBlockTexture(Blocks.GLASS)), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, closed)).with(
                PropertyDispatch.properties(KitchenCounterContainerDoorBlock.HINGE, KitchenCounterContainerDoorBlock.OPEN)
                        .select(DoorHingeSide.LEFT, false, Variant.variant().with(VariantProperties.MODEL, closed_mirrored))
                        .select(DoorHingeSide.LEFT, true, Variant.variant().with(VariantProperties.MODEL, open_mirrored))
                        .select(DoorHingeSide.RIGHT, false, Variant.variant().with(VariantProperties.MODEL, closed))
                        .select(DoorHingeSide.RIGHT, true, Variant.variant().with(VariantProperties.MODEL, open))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCounterSmallDrawer(Block block, Block counter, ResourceLocation top, ResourceLocation door, BlockModelGenerators blockModels) {
        ResourceLocation closed = ModelTemplates.KITCHEN_COUNTER_SMALL_DRAWER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door), blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.KITCHEN_COUNTER_SMALL_DRAWER_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, closed)).with(
                PropertyDispatch.property(KitchenCounterContainerDrawerBlock.OPEN)
                        .select(false, Variant.variant().with(VariantProperties.MODEL, closed))
                        .select(true, Variant.variant().with(VariantProperties.MODEL, open))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCounterBigDrawer(Block block, Block counter, ResourceLocation top, ResourceLocation door, BlockModelGenerators blockModels) {
        ResourceLocation closed = ModelTemplates.KITCHEN_COUNTER_BIG_DRAWER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door), blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.KITCHEN_COUNTER_BIG_DRAWER_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, closed)).with(
                PropertyDispatch.property(KitchenCounterContainerDrawerBlock.OPEN)
                        .select(false, Variant.variant().with(VariantProperties.MODEL, closed))
                        .select(true, Variant.variant().with(VariantProperties.MODEL, open))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCounterSink(Block block, Block counter, ResourceLocation top, BlockModelGenerators blockModels) {
        ResourceLocation empty = ModelTemplates.KITCHEN_COUNTER_SINK_EMPTY.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.FAUCET, TextureMapping.getBlockTexture(Blocks.ANVIL)), blockModels.modelOutput);
        ResourceLocation empty_on = ModelTemplates.KITCHEN_COUNTER_SINK_EMPTY_ON.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.FAUCET, TextureMapping.getBlockTexture(Blocks.ANVIL)).put(TextureSlots.STREAM, TextureMapping.getBlockTexture(Blocks.WATER).withSuffix("_flow")), blockModels.modelOutput);
        ResourceLocation level_1 = ModelTemplates.KITCHEN_COUNTER_SINK_LEVEL_1.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.FAUCET, TextureMapping.getBlockTexture(Blocks.ANVIL)).put(TextureSlots.WATER, TextureMapping.getBlockTexture(Blocks.WATER).withSuffix("_still")), blockModels.modelOutput);
        ResourceLocation level_1_on = ModelTemplates.KITCHEN_COUNTER_SINK_LEVEL_1_ON.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.FAUCET, TextureMapping.getBlockTexture(Blocks.ANVIL)).put(TextureSlots.WATER, TextureMapping.getBlockTexture(Blocks.WATER).withSuffix("_still")).put(TextureSlots.STREAM, TextureMapping.getBlockTexture(Blocks.WATER).withSuffix("_flow")), blockModels.modelOutput);
        ResourceLocation level_2 = ModelTemplates.KITCHEN_COUNTER_SINK_LEVEL_2.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.FAUCET, TextureMapping.getBlockTexture(Blocks.ANVIL)).put(TextureSlots.WATER, TextureMapping.getBlockTexture(Blocks.WATER).withSuffix("_still")), blockModels.modelOutput);
        ResourceLocation level_2_on = ModelTemplates.KITCHEN_COUNTER_SINK_LEVEL_2_ON.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.FAUCET, TextureMapping.getBlockTexture(Blocks.ANVIL)).put(TextureSlots.WATER, TextureMapping.getBlockTexture(Blocks.WATER).withSuffix("_still")).put(TextureSlots.STREAM, TextureMapping.getBlockTexture(Blocks.WATER).withSuffix("_flow")), blockModels.modelOutput);
        ResourceLocation level_3 = ModelTemplates.KITCHEN_COUNTER_SINK_LEVEL_3.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.FAUCET, TextureMapping.getBlockTexture(Blocks.ANVIL)).put(TextureSlots.WATER, TextureMapping.getBlockTexture(Blocks.WATER).withSuffix("_still")), blockModels.modelOutput);
        ResourceLocation level_3_on = ModelTemplates.KITCHEN_COUNTER_SINK_LEVEL_3_ON.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.FAUCET, TextureMapping.getBlockTexture(Blocks.ANVIL)).put(TextureSlots.WATER, TextureMapping.getBlockTexture(Blocks.WATER).withSuffix("_still")).put(TextureSlots.STREAM, TextureMapping.getBlockTexture(Blocks.WATER).withSuffix("_flow")), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, empty)).with(
                PropertyDispatch.properties(KitchenCounterSinkBlock.ON, KitchenCounterSinkBlock.LEVEL)
                        .select(true, 0, Variant.variant().with(VariantProperties.MODEL, empty_on))
                        .select(false, 0, Variant.variant().with(VariantProperties.MODEL, empty))
                        .select(true, 1, Variant.variant().with(VariantProperties.MODEL, level_1_on))
                        .select(false, 1, Variant.variant().with(VariantProperties.MODEL, level_1))
                        .select(true, 2, Variant.variant().with(VariantProperties.MODEL, level_2_on))
                        .select(false, 2, Variant.variant().with(VariantProperties.MODEL, level_2))
                        .select(true, 3, Variant.variant().with(VariantProperties.MODEL, level_3_on))
                        .select(false, 3, Variant.variant().with(VariantProperties.MODEL, level_3))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCounterSmoker(Block block, Block counter, ResourceLocation top, ResourceLocation furnace, ResourceLocation furnace_lit, BlockModelGenerators blockModels) {
        ResourceLocation unlit = ModelTemplates.KITCHEN_COUNTER_SMOKER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.FURNACE, furnace), blockModels.modelOutput);
        ResourceLocation lit = ModelTemplates.KITCHEN_COUNTER_SMOKER_LIT.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.FURNACE, furnace_lit), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, unlit)).with(
                PropertyDispatch.property(SmokerBlock.LIT)
                        .select(false, Variant.variant().with(VariantProperties.MODEL, unlit))
                        .select(true, Variant.variant().with(VariantProperties.MODEL, lit))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCabinet(Block block, Block cabinet, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.KITCHEN_CABINET.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCabinetInnerCorner(Block block, Block cabinet, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.KITCHEN_CABINET_INNER_CORNER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCabinetOuterCorner(Block block, Block cabinet, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.KITCHEN_CABINET_OUTER_CORNER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createOpenKitchenCabinet(Block block, Block cabinet, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.KITCHEN_CABINET_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCabinetDoor(Block block, Block cabinet, ResourceLocation door, ResourceLocation handle, BlockModelGenerators blockModels) {
        ResourceLocation closed = ModelTemplates.KITCHEN_CABINET_DOOR.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle), blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.KITCHEN_CABINET_DOOR_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, closed)).with(
                PropertyDispatch.property(KitchenCabinetContainerDoorBlock.OPEN)
                        .select(false, Variant.variant().with(VariantProperties.MODEL, closed))
                        .select(true, Variant.variant().with(VariantProperties.MODEL, open))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCabinetGlassDoor(Block block, Block cabinet, ResourceLocation door, ResourceLocation handle, BlockModelGenerators blockModels) {
        ResourceLocation closed = ModelTemplates.KITCHEN_CABINET_GLASS_DOOR.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle).put(TextureSlots.GLASS, TextureMapping.getBlockTexture(Blocks.GLASS)), blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.KITCHEN_CABINET_GLASS_DOOR_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle).put(TextureSlots.GLASS, TextureMapping.getBlockTexture(Blocks.GLASS)), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, closed)).with(
                PropertyDispatch.property(KitchenCabinetContainerDoorBlock.OPEN)
                        .select(false, Variant.variant().with(VariantProperties.MODEL, closed))
                        .select(true, Variant.variant().with(VariantProperties.MODEL, open))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCabinetSidewaysDoor(Block block, Block cabinet, ResourceLocation door, ResourceLocation handle, BlockModelGenerators blockModels) {
        ResourceLocation closed = ModelTemplates.KITCHEN_CABINET_DOOR_SIDEWAYS.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle), blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.KITCHEN_CABINET_DOOR_SIDEWAYS_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle), blockModels.modelOutput);
        ResourceLocation closed_mirrored = ModelTemplates.KITCHEN_CABINET_DOOR_SIDEWAYS_MIRRORED.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle), blockModels.modelOutput);
        ResourceLocation open_mirrored = ModelTemplates.KITCHEN_CABINET_DOOR_SIDEWAYS_MIRRORED_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, closed)).with(
                PropertyDispatch.properties(KitchenCabinetContainerSidewaysDoorBlock.HINGE, KitchenCabinetContainerSidewaysDoorBlock.OPEN)
                        .select(DoorHingeSide.LEFT, false, Variant.variant().with(VariantProperties.MODEL, closed_mirrored))
                        .select(DoorHingeSide.LEFT, true, Variant.variant().with(VariantProperties.MODEL, open_mirrored))
                        .select(DoorHingeSide.RIGHT, false, Variant.variant().with(VariantProperties.MODEL, closed))
                        .select(DoorHingeSide.RIGHT, true, Variant.variant().with(VariantProperties.MODEL, open))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCabinetSidewaysGlassDoor(Block block, Block cabinet, ResourceLocation door, ResourceLocation handle, BlockModelGenerators blockModels) {
        ResourceLocation closed = ModelTemplates.KITCHEN_CABINET_GLASS_DOOR_SIDEWAYS.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle).put(TextureSlots.GLASS, TextureMapping.getBlockTexture(Blocks.GLASS)), blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.KITCHEN_CABINET_GLASS_DOOR_SIDEWAYS_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle).put(TextureSlots.GLASS, TextureMapping.getBlockTexture(Blocks.GLASS)), blockModels.modelOutput);
        ResourceLocation closed_mirrored = ModelTemplates.KITCHEN_CABINET_GLASS_DOOR_SIDEWAYS_MIRRORED.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle).put(TextureSlots.GLASS, TextureMapping.getBlockTexture(Blocks.GLASS)), blockModels.modelOutput);
        ResourceLocation open_mirrored = ModelTemplates.KITCHEN_CABINET_GLASS_DOOR_SIDEWAYS_MIRRORED_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle).put(TextureSlots.GLASS, TextureMapping.getBlockTexture(Blocks.GLASS)), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, closed)).with(
                PropertyDispatch.properties(KitchenCabinetContainerSidewaysDoorBlock.HINGE, KitchenCabinetContainerSidewaysDoorBlock.OPEN)
                        .select(DoorHingeSide.LEFT, false, Variant.variant().with(VariantProperties.MODEL, closed_mirrored))
                        .select(DoorHingeSide.LEFT, true, Variant.variant().with(VariantProperties.MODEL, open_mirrored))
                        .select(DoorHingeSide.RIGHT, false, Variant.variant().with(VariantProperties.MODEL, closed))
                        .select(DoorHingeSide.RIGHT, true, Variant.variant().with(VariantProperties.MODEL, open))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCabinetShelf(Block block, Block planks, ResourceLocation chain, BlockModelGenerators blockModels) {
        ResourceLocation single = ModelTemplates.KITCHEN_CABINET_SHELF_SINGLE.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.CHAIN, chain), blockModels.modelOutput);
        ResourceLocation left = ModelTemplates.KITCHEN_CABINET_SHELF_LEFT.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.CHAIN, chain), blockModels.modelOutput);
        ResourceLocation right = ModelTemplates.KITCHEN_CABINET_SHELF_RIGHT.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.CHAIN, chain), blockModels.modelOutput);
        ResourceLocation middle = ModelTemplates.KITCHEN_CABINET_SHELF_MIDDLE.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.CHAIN, chain), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, single)).with(
                PropertyDispatch.property(KitchenCabinetShelfBlock.SHAPE)
                        .select(ShelfShape.SINGLE, Variant.variant().with(VariantProperties.MODEL, single))
                        .select(ShelfShape.LEFT, Variant.variant().with(VariantProperties.MODEL, left))
                        .select(ShelfShape.RIGHT, Variant.variant().with(VariantProperties.MODEL, right))
                        .select(ShelfShape.MIDDLE, Variant.variant().with(VariantProperties.MODEL, middle))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createFridge(Block block, ResourceLocation fridge, ResourceLocation bars, ResourceLocation handle, BlockModelGenerators blockModels) {
        ResourceLocation bottom = ModelTemplates.FRIDGE_BOTTOM.create(block, new TextureMapping().put(TextureSlot.PARTICLE, fridge).put(TextureSlots.FRIDGE, fridge).put(TextureSlots.BARS, bars).put(TextureSlots.HANDLE, handle), blockModels.modelOutput);
        ResourceLocation bottom_open = ModelTemplates.FRIDGE_BOTTOM_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, fridge).put(TextureSlots.FRIDGE, fridge).put(TextureSlots.BARS, bars).put(TextureSlots.HANDLE, handle), blockModels.modelOutput);
        ResourceLocation bottom_mirrored = ModelTemplates.FRIDGE_BOTTOM_MIRRORED.create(block, new TextureMapping().put(TextureSlot.PARTICLE, fridge).put(TextureSlots.FRIDGE, fridge).put(TextureSlots.BARS, bars).put(TextureSlots.HANDLE, handle), blockModels.modelOutput);
        ResourceLocation bottom_mirrored_open = ModelTemplates.FRIDGE_BOTTOM_MIRRORED_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, fridge).put(TextureSlots.FRIDGE, fridge).put(TextureSlots.BARS, bars).put(TextureSlots.HANDLE, handle), blockModels.modelOutput);
        ResourceLocation top = ModelTemplates.FRIDGE_TOP.create(block, new TextureMapping().put(TextureSlot.PARTICLE, fridge).put(TextureSlots.FRIDGE, fridge).put(TextureSlots.HANDLE, handle), blockModels.modelOutput);
        ResourceLocation top_open = ModelTemplates.FRIDGE_TOP_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, fridge).put(TextureSlots.FRIDGE, fridge).put(TextureSlots.HANDLE, handle), blockModels.modelOutput);
        ResourceLocation top_mirrored = ModelTemplates.FRIDGE_TOP_MIRRORED.create(block, new TextureMapping().put(TextureSlot.PARTICLE, fridge).put(TextureSlots.FRIDGE, fridge).put(TextureSlots.HANDLE, handle), blockModels.modelOutput);
        ResourceLocation top_mirrored_open = ModelTemplates.FRIDGE_TOP_MIRRORED_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, fridge).put(TextureSlots.FRIDGE, fridge).put(TextureSlots.HANDLE, handle), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, bottom)).with(
                PropertyDispatch.properties(KitchenFridgeBlock.HALF, KitchenFridgeBlock.HINGE, KitchenFridgeBlock.OPEN)
                        .select(DoubleBlockHalf.UPPER, DoorHingeSide.LEFT, false, Variant.variant().with(VariantProperties.MODEL, top_mirrored))
                        .select(DoubleBlockHalf.UPPER, DoorHingeSide.LEFT, true, Variant.variant().with(VariantProperties.MODEL, top_mirrored_open))
                        .select(DoubleBlockHalf.UPPER, DoorHingeSide.RIGHT, false, Variant.variant().with(VariantProperties.MODEL, top))
                        .select(DoubleBlockHalf.UPPER, DoorHingeSide.RIGHT, true, Variant.variant().with(VariantProperties.MODEL, top_open))
                        .select(DoubleBlockHalf.LOWER, DoorHingeSide.LEFT, false, Variant.variant().with(VariantProperties.MODEL, bottom_mirrored))
                        .select(DoubleBlockHalf.LOWER, DoorHingeSide.LEFT, true, Variant.variant().with(VariantProperties.MODEL, bottom_mirrored_open))
                        .select(DoubleBlockHalf.LOWER, DoorHingeSide.RIGHT, false, Variant.variant().with(VariantProperties.MODEL, bottom))
                        .select(DoubleBlockHalf.LOWER, DoorHingeSide.RIGHT, true, Variant.variant().with(VariantProperties.MODEL, bottom_open))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKnifeBlock(Block block, Block blockTexture, ResourceLocation stand, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.KNIFE_BLOCK.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(blockTexture)).put(TextureSlots.BLOCK, TextureMapping.getBlockTexture(blockTexture)).put(TextureSlots.STAND, stand).put(TextureSlots.HANDLE, TextureMapping.getBlockTexture(Blocks.ANVIL)).put(TextureSlots.KNIFE, TextureMapping.getBlockTexture(Blocks.IRON_BLOCK)), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createChairBlock(Block block, Block chair, ResourceLocation decoration, ResourceLocation wool, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.CHAIR.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(chair)).put(TextureSlots.CHAIR, TextureMapping.getBlockTexture(chair)).put(TextureSlots.DECORATION, decoration).put(TextureSlots.WOOL, wool), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model)).with(BlockModelGenerators.createHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createTableBlock(Block block, Block planks, BlockModelGenerators blockModels) {
        ResourceLocation single = ModelTemplates.TABLE_SINGLE.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)), blockModels.modelOutput);
        ResourceLocation middle = ModelTemplates.TABLE_MIDDLE.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)), blockModels.modelOutput);
        ResourceLocation north = ModelTemplates.TABLE_NORTH.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)), blockModels.modelOutput);
        ResourceLocation east = ModelTemplates.TABLE_EAST.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)), blockModels.modelOutput);
        ResourceLocation south = ModelTemplates.TABLE_SOUTH.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)), blockModels.modelOutput);
        ResourceLocation west = ModelTemplates.TABLE_WEST.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)), blockModels.modelOutput);
        ResourceLocation north_east = ModelTemplates.TABLE_NORTH_EAST.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)), blockModels.modelOutput);
        ResourceLocation north_west = ModelTemplates.TABLE_NORTH_WEST.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)), blockModels.modelOutput);
        ResourceLocation south_east = ModelTemplates.TABLE_SOUTH_EAST.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)), blockModels.modelOutput);
        ResourceLocation south_west = ModelTemplates.TABLE_SOUTH_WEST.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, single)).with(
                PropertyDispatch.properties(TableBlock.NORTH, TableBlock.EAST, TableBlock.SOUTH, TableBlock.WEST)
                        .select(false, false, false, false, Variant.variant().with(VariantProperties.MODEL, single))
                        .select(true, true, true, true, Variant.variant().with(VariantProperties.MODEL, middle))
                        .select(true, false, true, false, Variant.variant().with(VariantProperties.MODEL, middle))
                        .select(false, true, false, true, Variant.variant().with(VariantProperties.MODEL, middle))
                        .select(true, false, false, false, Variant.variant().with(VariantProperties.MODEL, south))
                        .select(false, true, false, false, Variant.variant().with(VariantProperties.MODEL, west))
                        .select(false, false, true, false, Variant.variant().with(VariantProperties.MODEL, north))
                        .select(false, false, false, true, Variant.variant().with(VariantProperties.MODEL, east))
                        .select(true, true, false, false, Variant.variant().with(VariantProperties.MODEL, south_west))
                        .select(false, true, true, false, Variant.variant().with(VariantProperties.MODEL, north_west))
                        .select(false, false, true, true, Variant.variant().with(VariantProperties.MODEL, north_east))
                        .select(true, false, false, true, Variant.variant().with(VariantProperties.MODEL, south_east))
                        .select(true, true, true, false, Variant.variant().with(VariantProperties.MODEL, middle))
                        .select(false, true, true, true, Variant.variant().with(VariantProperties.MODEL, middle))
                        .select(true, false, true, true, Variant.variant().with(VariantProperties.MODEL, middle))
                        .select(true, true, false, true, Variant.variant().with(VariantProperties.MODEL, middle))
        );
    }

    private MultiVariantGenerator createCurtainBlock(Block block, ResourceLocation wool, ResourceLocation curtain_rod, BlockModelGenerators blockModels) {
        ResourceLocation small_closed = ModelTemplates.CURTAIN_SMALL_CLOSED.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wool).put(TextureSlots.WOOL, wool).put(TextureSlots.CURTAIN_ROD, curtain_rod), blockModels.modelOutput);
        ResourceLocation small_single_open = ModelTemplates.CURTAIN_SMALL_SINGLE_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wool).put(TextureSlots.WOOL, wool).put(TextureSlots.CURTAIN_ROD, curtain_rod), blockModels.modelOutput);
        ResourceLocation small_left_open = ModelTemplates.CURTAIN_SMALL_LEFT_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wool).put(TextureSlots.WOOL, wool).put(TextureSlots.CURTAIN_ROD, curtain_rod), blockModels.modelOutput);
        ResourceLocation small_right_open = ModelTemplates.CURTAIN_SMALL_RIGHT_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wool).put(TextureSlots.WOOL, wool).put(TextureSlots.CURTAIN_ROD, curtain_rod), blockModels.modelOutput);
        ResourceLocation big_top_middle_open = ModelTemplates.CURTAIN_BIG_TOP_MIDDLE_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wool).put(TextureSlots.WOOL, wool).put(TextureSlots.CURTAIN_ROD, curtain_rod), blockModels.modelOutput);
        ResourceLocation big_top_closed = ModelTemplates.CURTAIN_BIG_TOP_CLOSED.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wool).put(TextureSlots.WOOL, wool).put(TextureSlots.CURTAIN_ROD, curtain_rod), blockModels.modelOutput);
        ResourceLocation big_bottom_closed = ModelTemplates.CURTAIN_BIG_BOTTOM_CLOSED.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wool).put(TextureSlots.WOOL, wool).put(TextureSlots.CURTAIN_ROD, curtain_rod), blockModels.modelOutput);
        ResourceLocation big_top_single_open = ModelTemplates.CURTAIN_BIG_TOP_SINGLE_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wool).put(TextureSlots.WOOL, wool).put(TextureSlots.CURTAIN_ROD, curtain_rod), blockModels.modelOutput);
        ResourceLocation big_top_right_open = ModelTemplates.CURTAIN_BIG_TOP_RIGHT_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wool).put(TextureSlots.WOOL, wool).put(TextureSlots.CURTAIN_ROD, curtain_rod), blockModels.modelOutput);
        ResourceLocation big_top_left_open = ModelTemplates.CURTAIN_BIG_TOP_LEFT_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wool).put(TextureSlots.WOOL, wool).put(TextureSlots.CURTAIN_ROD, curtain_rod), blockModels.modelOutput);
        ResourceLocation big_bottom_single_open = ModelTemplates.CURTAIN_BIG_BOTTOM_SINGLE_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wool).put(TextureSlots.WOOL, wool).put(TextureSlots.CURTAIN_ROD, curtain_rod), blockModels.modelOutput);
        ResourceLocation big_bottom_right_open = ModelTemplates.CURTAIN_BIG_BOTTOM_RIGHT_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wool).put(TextureSlots.WOOL, wool).put(TextureSlots.CURTAIN_ROD, curtain_rod), blockModels.modelOutput);
        ResourceLocation big_bottom_left_open = ModelTemplates.CURTAIN_BIG_BOTTOM_LEFT_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wool).put(TextureSlots.WOOL, wool).put(TextureSlots.CURTAIN_ROD, curtain_rod), blockModels.modelOutput);
        ResourceLocation big_bottom_middle_open = ModelTemplates.CURTAIN_BIG_BOTTOM_MIDDLE_OPEN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wool).put(TextureSlots.WOOL, wool).put(TextureSlots.CURTAIN_ROD, curtain_rod), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, small_closed)).with(
                PropertyDispatch.properties(CurtainBlock.CURTAIN_SHAPE, CurtainBlock.LEFT, CurtainBlock.RIGHT, CurtainBlock.OPEN)
                        .select(CurtainShape.SINGLE, false, false, false, Variant.variant().with(VariantProperties.MODEL, small_closed))
                        .select(CurtainShape.SINGLE, true, false, false, Variant.variant().with(VariantProperties.MODEL, small_closed))
                        .select(CurtainShape.SINGLE, false, true, false, Variant.variant().with(VariantProperties.MODEL, small_closed))
                        .select(CurtainShape.SINGLE, true, true, false, Variant.variant().with(VariantProperties.MODEL, small_closed))
                        .select(CurtainShape.SINGLE, false, false, true, Variant.variant().with(VariantProperties.MODEL, small_single_open))
                        .select(CurtainShape.SINGLE, false, true, true, Variant.variant().with(VariantProperties.MODEL, small_right_open))
                        .select(CurtainShape.SINGLE, true, false, true, Variant.variant().with(VariantProperties.MODEL, small_left_open))
                        .select(CurtainShape.SINGLE, true, true, true, Variant.variant().with(VariantProperties.MODEL, big_top_middle_open))

                        .select(CurtainShape.TOP, false, false, false, Variant.variant().with(VariantProperties.MODEL, big_top_closed))
                        .select(CurtainShape.TOP, true, false, false, Variant.variant().with(VariantProperties.MODEL, big_top_closed))
                        .select(CurtainShape.TOP, false, true, false, Variant.variant().with(VariantProperties.MODEL, big_top_closed))
                        .select(CurtainShape.TOP, true, true, false, Variant.variant().with(VariantProperties.MODEL, big_top_closed))
                        .select(CurtainShape.TOP, false, false, true, Variant.variant().with(VariantProperties.MODEL, big_top_single_open))
                        .select(CurtainShape.TOP, true, false, true, Variant.variant().with(VariantProperties.MODEL, big_top_left_open))
                        .select(CurtainShape.TOP, false, true, true, Variant.variant().with(VariantProperties.MODEL, big_top_right_open))
                        .select(CurtainShape.TOP, true, true, true, Variant.variant().with(VariantProperties.MODEL, big_top_middle_open))

                        .select(CurtainShape.BOTTOM, false, false, false, Variant.variant().with(VariantProperties.MODEL, big_bottom_closed))
                        .select(CurtainShape.BOTTOM, true, false, false, Variant.variant().with(VariantProperties.MODEL, big_bottom_closed))
                        .select(CurtainShape.BOTTOM, false, true, false, Variant.variant().with(VariantProperties.MODEL, big_bottom_closed))
                        .select(CurtainShape.BOTTOM, true, true, false, Variant.variant().with(VariantProperties.MODEL, big_bottom_closed))
                        .select(CurtainShape.BOTTOM, false, false, true, Variant.variant().with(VariantProperties.MODEL, big_bottom_single_open))
                        .select(CurtainShape.BOTTOM, true, false, true, Variant.variant().with(VariantProperties.MODEL, big_bottom_left_open))
                        .select(CurtainShape.BOTTOM, false, true, true, Variant.variant().with(VariantProperties.MODEL, big_bottom_right_open))
                        .select(CurtainShape.BOTTOM, true, true, true, Variant.variant().with(VariantProperties.MODEL, big_bottom_middle_open))
        ).with(BlockModelGenerators.createHorizontalFacingDispatch());
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
        public static final TextureSlot COUNTER = TextureSlot.create("counter");
        public static final TextureSlot CABINET = TextureSlot.create("cabinet");
        public static final TextureSlot FRIDGE = TextureSlot.create("fridge");
        public static final TextureSlot TOP = TextureSlot.create("top");
        public static final TextureSlot WOOL = TextureSlot.create("wool");
        public static final TextureSlot CURTAIN_ROD = TextureSlot.create("curtain_rod");
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
        public static final ModelTemplate CRATE = new ModelTemplate(Optional.of(FabulousFurniture.prefix("crate_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.PLANKS, TextureSlots.LOG);
        public static final ModelTemplate KITCHEN_COUNTER = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP);
        public static final ModelTemplate KITCHEN_COUNTER_INNER_CORNER = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_inner_corner_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP);
        public static final ModelTemplate KITCHEN_COUNTER_OUTER_CORNER = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_outer_corner_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP);
        public static final ModelTemplate KITCHEN_COUNTER_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_open_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP);
        public static final ModelTemplate KITCHEN_COUNTER_DOOR = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_door_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);
        public static final ModelTemplate KITCHEN_COUNTER_DOOR_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_door_open_template").withPrefix("block/template/")), Optional.of("_open"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);
        public static final ModelTemplate KITCHEN_COUNTER_DOOR_MIRRORED = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_door_mirrored_template").withPrefix("block/template/")), Optional.of("_mirrored"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);
        public static final ModelTemplate KITCHEN_COUNTER_DOOR_MIRRORED_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_door_mirrored_open_template").withPrefix("block/template/")), Optional.of("_mirrored_open"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);
        public static final ModelTemplate KITCHEN_COUNTER_GLASS_DOOR = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_glass_door_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR, TextureSlots.GLASS);
        public static final ModelTemplate KITCHEN_COUNTER_GLASS_DOOR_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_glass_door_open_template").withPrefix("block/template/")), Optional.of("_open"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR, TextureSlots.GLASS);
        public static final ModelTemplate KITCHEN_COUNTER_GLASS_DOOR_MIRRORED = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_glass_door_mirrored_template").withPrefix("block/template/")), Optional.of("_mirrored"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR, TextureSlots.GLASS);
        public static final ModelTemplate KITCHEN_COUNTER_GLASS_DOOR_MIRRORED_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_glass_door_mirrored_open_template").withPrefix("block/template/")), Optional.of("_mirrored_open"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR, TextureSlots.GLASS);
        public static final ModelTemplate KITCHEN_COUNTER_SMALL_DRAWER = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_small_drawer_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);
        public static final ModelTemplate KITCHEN_COUNTER_SMALL_DRAWER_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_small_drawer_open_template").withPrefix("block/template/")), Optional.of("_open"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);
        public static final ModelTemplate KITCHEN_COUNTER_BIG_DRAWER = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_big_drawer_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);
        public static final ModelTemplate KITCHEN_COUNTER_BIG_DRAWER_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_big_drawer_open_template").withPrefix("block/template/")), Optional.of("_open"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);
        public static final ModelTemplate KITCHEN_COUNTER_SINK_EMPTY = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_sink_empty_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.FAUCET);
        public static final ModelTemplate KITCHEN_COUNTER_SINK_EMPTY_ON = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_sink_empty_on_template").withPrefix("block/template/")), Optional.of("_on"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.FAUCET, TextureSlots.STREAM);
        public static final ModelTemplate KITCHEN_COUNTER_SINK_LEVEL_1 = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_sink_level_1_template").withPrefix("block/template/")), Optional.of("_level_1"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.FAUCET, TextureSlots.WATER);
        public static final ModelTemplate KITCHEN_COUNTER_SINK_LEVEL_1_ON = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_sink_level_1_on_template").withPrefix("block/template/")), Optional.of("_level_1_on"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.FAUCET, TextureSlots.WATER, TextureSlots.STREAM);
        public static final ModelTemplate KITCHEN_COUNTER_SINK_LEVEL_2 = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_sink_level_2_template").withPrefix("block/template/")), Optional.of("_level_2"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.FAUCET, TextureSlots.WATER);
        public static final ModelTemplate KITCHEN_COUNTER_SINK_LEVEL_2_ON = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_sink_level_2_on_template").withPrefix("block/template/")), Optional.of("_level_2_on"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.FAUCET, TextureSlots.WATER, TextureSlots.STREAM);
        public static final ModelTemplate KITCHEN_COUNTER_SINK_LEVEL_3 = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_sink_level_3_template").withPrefix("block/template/")), Optional.of("_level_3"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.FAUCET, TextureSlots.WATER);
        public static final ModelTemplate KITCHEN_COUNTER_SINK_LEVEL_3_ON = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_sink_level_3_on_template").withPrefix("block/template/")), Optional.of("_level_3_on"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.FAUCET, TextureSlots.WATER, TextureSlots.STREAM);
        public static final ModelTemplate KITCHEN_COUNTER_SMOKER = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_smoker_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.FURNACE);
        public static final ModelTemplate KITCHEN_COUNTER_SMOKER_LIT = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_counter_smoker_lit_template").withPrefix("block/template/")), Optional.of("_lit"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.FURNACE);

        public static final ModelTemplate KITCHEN_CABINET = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_cabinet_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.CABINET);
        public static final ModelTemplate KITCHEN_CABINET_INNER_CORNER = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_cabinet_inner_corner_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.CABINET);
        public static final ModelTemplate KITCHEN_CABINET_OUTER_CORNER = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_cabinet_outer_corner_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.CABINET);
        public static final ModelTemplate KITCHEN_CABINET_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_cabinet_open_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.CABINET);
        public static final ModelTemplate KITCHEN_CABINET_DOOR = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_cabinet_door_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE);
        public static final ModelTemplate KITCHEN_CABINET_DOOR_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_cabinet_door_open_template").withPrefix("block/template/")), Optional.of("_open"), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE);
        public static final ModelTemplate KITCHEN_CABINET_GLASS_DOOR = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_cabinet_glass_door_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE, TextureSlots.GLASS);
        public static final ModelTemplate KITCHEN_CABINET_GLASS_DOOR_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_cabinet_glass_door_open_template").withPrefix("block/template/")), Optional.of("_open"), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE, TextureSlots.GLASS);
        public static final ModelTemplate KITCHEN_CABINET_DOOR_SIDEWAYS = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_cabinet_sideways_door_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE);
        public static final ModelTemplate KITCHEN_CABINET_DOOR_SIDEWAYS_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_cabinet_sideways_door_open_template").withPrefix("block/template/")), Optional.of("_open"), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE);
        public static final ModelTemplate KITCHEN_CABINET_DOOR_SIDEWAYS_MIRRORED = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_cabinet_sideways_door_mirrored_template").withPrefix("block/template/")), Optional.of("_mirrored"), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE);
        public static final ModelTemplate KITCHEN_CABINET_DOOR_SIDEWAYS_MIRRORED_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_cabinet_sideways_door_mirrored_open_template").withPrefix("block/template/")), Optional.of("_mirrored_open"), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE);
        public static final ModelTemplate KITCHEN_CABINET_GLASS_DOOR_SIDEWAYS = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_cabinet_sideways_glass_door_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE, TextureSlots.GLASS);
        public static final ModelTemplate KITCHEN_CABINET_GLASS_DOOR_SIDEWAYS_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_cabinet_sideways_glass_door_open_template").withPrefix("block/template/")), Optional.of("_open"), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE, TextureSlots.GLASS);
        public static final ModelTemplate KITCHEN_CABINET_GLASS_DOOR_SIDEWAYS_MIRRORED = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_cabinet_sideways_glass_door_mirrored_template").withPrefix("block/template/")), Optional.of("_mirrored"), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE, TextureSlots.GLASS);
        public static final ModelTemplate KITCHEN_CABINET_GLASS_DOOR_SIDEWAYS_MIRRORED_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_cabinet_sideways_glass_door_mirrored_open_template").withPrefix("block/template/")), Optional.of("_mirrored_open"), TextureSlot.PARTICLE, TextureSlots.CABINET, TextureSlots.DOOR, TextureSlots.HANDLE, TextureSlots.GLASS);
        public static final ModelTemplate KITCHEN_CABINET_SHELF_SINGLE = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_cabinet_shelf_single_template").withPrefix("block/template/")), Optional.of("_single"), TextureSlot.PARTICLE, TextureSlots.PLANKS, TextureSlots.CHAIN);
        public static final ModelTemplate KITCHEN_CABINET_SHELF_RIGHT = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_cabinet_shelf_right_template").withPrefix("block/template/")), Optional.of("_right"), TextureSlot.PARTICLE, TextureSlots.PLANKS, TextureSlots.CHAIN);
        public static final ModelTemplate KITCHEN_CABINET_SHELF_LEFT = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_cabinet_shelf_left_template").withPrefix("block/template/")), Optional.of("_left"), TextureSlot.PARTICLE, TextureSlots.PLANKS, TextureSlots.CHAIN);
        public static final ModelTemplate KITCHEN_CABINET_SHELF_MIDDLE = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_cabinet_shelf_middle_template").withPrefix("block/template/")), Optional.of("_middle"), TextureSlot.PARTICLE, TextureSlots.PLANKS);

        public static final ModelTemplate FRIDGE_BOTTOM = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_fridge_bottom_template").withPrefix("block/template/")), Optional.of("_bottom"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.BARS, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_BOTTOM_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_fridge_bottom_open_template").withPrefix("block/template/")), Optional.of("_bottom_open"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.BARS, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_BOTTOM_MIRRORED = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_fridge_bottom_mirrored_template").withPrefix("block/template/")), Optional.of("_bottom_mirrored"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.BARS, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_BOTTOM_MIRRORED_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_fridge_bottom_mirrored_open_template").withPrefix("block/template/")), Optional.of("_bottom_mirrored_open"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.BARS, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_TOP = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_fridge_top_template").withPrefix("block/template/")), Optional.of("_top"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_TOP_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_fridge_top_open_template").withPrefix("block/template/")), Optional.of("_top_open"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_TOP_MIRRORED = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_fridge_top_mirrored_template").withPrefix("block/template/")), Optional.of("_top_mirrored"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_TOP_MIRRORED_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_fridge_top_mirrored_open_template").withPrefix("block/template/")), Optional.of("_top_mirrored_open"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.HANDLE);

        public static final ModelTemplate KNIFE_BLOCK = new ModelTemplate(Optional.of(FabulousFurniture.prefix("knife_block_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.BLOCK, TextureSlots.STAND, TextureSlots.HANDLE, TextureSlots.KNIFE);

        public static final ModelTemplate CHAIR = new ModelTemplate(Optional.of(FabulousFurniture.prefix("wooden_chair_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.CHAIR, TextureSlots.DECORATION, TextureSlots.WOOL);
        public static final ModelTemplate TABLE_SINGLE = new ModelTemplate(Optional.of(FabulousFurniture.prefix("wooden_table_single_template").withPrefix("block/template/")), Optional.of("_single"), TextureSlot.PARTICLE, TextureSlots.PLANKS);
        public static final ModelTemplate TABLE_MIDDLE = new ModelTemplate(Optional.of(FabulousFurniture.prefix("wooden_table_middle_template").withPrefix("block/template/")), Optional.of("_middle"), TextureSlot.PARTICLE, TextureSlots.PLANKS);
        public static final ModelTemplate TABLE_NORTH = new ModelTemplate(Optional.of(FabulousFurniture.prefix("wooden_table_north_template").withPrefix("block/template/")), Optional.of("_north"), TextureSlot.PARTICLE, TextureSlots.PLANKS);
        public static final ModelTemplate TABLE_EAST = new ModelTemplate(Optional.of(FabulousFurniture.prefix("wooden_table_east_template").withPrefix("block/template/")), Optional.of("_east"), TextureSlot.PARTICLE, TextureSlots.PLANKS);
        public static final ModelTemplate TABLE_SOUTH = new ModelTemplate(Optional.of(FabulousFurniture.prefix("wooden_table_south_template").withPrefix("block/template/")), Optional.of("_south"), TextureSlot.PARTICLE, TextureSlots.PLANKS);
        public static final ModelTemplate TABLE_WEST = new ModelTemplate(Optional.of(FabulousFurniture.prefix("wooden_table_west_template").withPrefix("block/template/")), Optional.of("_west"), TextureSlot.PARTICLE, TextureSlots.PLANKS);
        public static final ModelTemplate TABLE_NORTH_EAST = new ModelTemplate(Optional.of(FabulousFurniture.prefix("wooden_table_north_east_template").withPrefix("block/template/")), Optional.of("_north_east"), TextureSlot.PARTICLE, TextureSlots.PLANKS);
        public static final ModelTemplate TABLE_NORTH_WEST = new ModelTemplate(Optional.of(FabulousFurniture.prefix("wooden_table_north_west_template").withPrefix("block/template/")), Optional.of("_north_west"), TextureSlot.PARTICLE, TextureSlots.PLANKS);
        public static final ModelTemplate TABLE_SOUTH_EAST = new ModelTemplate(Optional.of(FabulousFurniture.prefix("wooden_table_south_east_template").withPrefix("block/template/")), Optional.of("_south_east"), TextureSlot.PARTICLE, TextureSlots.PLANKS);
        public static final ModelTemplate TABLE_SOUTH_WEST = new ModelTemplate(Optional.of(FabulousFurniture.prefix("wooden_table_south_west_template").withPrefix("block/template/")), Optional.of("_south_west"), TextureSlot.PARTICLE, TextureSlots.PLANKS);

        public static final ModelTemplate CURTAIN_SMALL_CLOSED = new ModelTemplate(Optional.of(FabulousFurniture.prefix("curtains_small_closed_template").withPrefix("block/template/")), Optional.of("_small_closed"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_SMALL_SINGLE_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("curtains_small_single_open_template").withPrefix("block/template/")), Optional.of("_small_single_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_SMALL_LEFT_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("curtains_small_left_open_template").withPrefix("block/template/")), Optional.of("_small_left_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_SMALL_RIGHT_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("curtains_small_right_open_template").withPrefix("block/template/")), Optional.of("_small_right_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_BIG_BOTTOM_CLOSED = new ModelTemplate(Optional.of(FabulousFurniture.prefix("curtains_big_bottom_closed_template").withPrefix("block/template/")), Optional.of("_big_bottom_closed"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_BIG_TOP_CLOSED = new ModelTemplate(Optional.of(FabulousFurniture.prefix("curtains_big_top_closed_template").withPrefix("block/template/")), Optional.of("_big_top_closed"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_BIG_TOP_SINGLE_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("curtains_big_top_single_open_template").withPrefix("block/template/")), Optional.of("_big_top_single_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_BIG_BOTTOM_SINGLE_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("curtains_big_bottom_single_open_template").withPrefix("block/template/")), Optional.of("_big_bottom_single_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_BIG_BOTTOM_LEFT_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("curtains_big_bottom_left_open_template").withPrefix("block/template/")), Optional.of("_big_bottom_left_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_BIG_BOTTOM_RIGHT_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("curtains_big_bottom_right_open_template").withPrefix("block/template/")), Optional.of("_big_bottom_right_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_BIG_BOTTOM_MIDDLE_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("curtains_big_bottom_middle_open_template").withPrefix("block/template/")), Optional.of("_big_bottom_middle_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_BIG_TOP_LEFT_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("curtains_big_top_left_open_template").withPrefix("block/template/")), Optional.of("_big_top_left_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_BIG_TOP_RIGHT_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("curtains_big_top_right_open_template").withPrefix("block/template/")), Optional.of("_big_top_right_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);
        public static final ModelTemplate CURTAIN_BIG_TOP_MIDDLE_OPEN = new ModelTemplate(Optional.of(FabulousFurniture.prefix("curtains_big_top_middle_open_template").withPrefix("block/template/")), Optional.of("_big_top_middle_open"), TextureSlot.PARTICLE, TextureSlots.WOOL, TextureSlots.CURTAIN_ROD);

        public static final ModelTemplate KITCHEN_TILES = new ModelTemplate(Optional.of(FabulousFurniture.prefix("kitchen_tiles_template").withPrefix("block/template/")), Optional.empty(), TextureSlot.PARTICLE, TextureSlots.TILE_BASE, TextureSlots.TILE_2);
    }
}