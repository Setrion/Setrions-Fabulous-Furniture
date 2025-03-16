package net.setrion.fabulous_furniture.data;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SmokerBlock;
import net.minecraft.world.level.block.state.properties.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.setrion.fabulous_furniture.FabulousFurniture;
import net.setrion.fabulous_furniture.world.level.block.*;
import net.setrion.fabulous_furniture.world.level.block.state.properties.*;

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
            if (!(block instanceof TableBlock || block instanceof KitchenCabinetShelfBlock || block instanceof CurtainBlock || block instanceof WoodenBedBlock)) {
                registerBasicBlockModel(blockModels.itemModelOutput, block);
            } else {
                if (block instanceof CurtainBlock) {
                    registerBasicBlockModel(blockModels.itemModelOutput, block, "_small_single_open");
                } else if (!(block instanceof WoodenBedBlock)) {
                    registerBasicBlockModel(blockModels.itemModelOutput, block, "_single");
                }
            }
        }));
    }

    private void createCurtains(BlockModelGenerators blockModels) {
        WOOL_COLORS.forEach((wool, color) -> {
            CURTAIN_RODS.forEach((rod, name) -> {
                createModel(blockModels, createCurtainBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_"+name+"_curtains")), TextureMapping.getBlockTexture(wool), TextureMapping.getBlockTexture(rod), blockModels));
            });
        });
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
        createModel(blockModels, createCrate(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_crate")), planks, log, blockModels));
        COUNTER_TOPS.forEach(((block, s) -> {
            String top_name = block.getDescriptionId().replaceFirst("block.minecraft.", "").replaceFirst("quartz_block", "quartz");
            createModel(blockModels, createKitchenCounter(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter")), planks, TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCounterShelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_shelf")), planks, TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCounterDoor(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_door")), planks, TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(log), blockModels));
            createModel(blockModels, createKitchenCounterSmallDrawer(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_small_drawer")), planks, TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(log), blockModels));
            createModel(blockModels, createKitchenCounterBigDrawer(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_big_drawer")), planks, TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(log), blockModels));
            createModel(blockModels, createKitchenCounterSink(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_sink")), planks, TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCounterSmoker(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_smoker")), planks, TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(SMOKER, "_front"), TextureMapping.getBlockTexture(SMOKER, "_front_on"), blockModels));
            createModel(blockModels, createKitchenCabinetDoor(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_cabinet_door")), planks, TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCabinetGlassDoor(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_cabinet_glass_door")), planks, TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCabinetSidewaysDoor(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_cabinet_sideways_door")), planks, TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCabinetSidewaysGlassDoor(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_cabinet_sideways_glass_door")), planks, TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(block, s), blockModels));

            createModel(blockModels, createKitchenCounter(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_counter")), log, TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCounterShelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_shelf")), log, TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCounterDoor(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_door")), log, TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(strippedLog), blockModels));
            createModel(blockModels, createKitchenCounterSmallDrawer(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_small_drawer")), log, TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(strippedLog), blockModels));
            createModel(blockModels, createKitchenCounterBigDrawer(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_big_drawer")), log, TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(strippedLog), blockModels));
            createModel(blockModels, createKitchenCounterSink(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_sink")), log, TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCounterSmoker(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_smoker")), log, TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(SMOKER, "_front"), TextureMapping.getBlockTexture(SMOKER, "_front_on"), blockModels));
            createModel(blockModels, createKitchenCabinetDoor(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_door")), log, TextureMapping.getBlockTexture(strippedLog), TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCabinetGlassDoor(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_glass_door")), log, TextureMapping.getBlockTexture(strippedLog), TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCabinetSidewaysDoor(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_door")), log, TextureMapping.getBlockTexture(strippedLog), TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCabinetSidewaysGlassDoor(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_glass_door")), log, TextureMapping.getBlockTexture(strippedLog), TextureMapping.getBlockTexture(block, s), blockModels));

            createModel(blockModels, createKitchenCounter(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter")), strippedLog, TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCounterShelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_shelf")), strippedLog, TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCounterDoor(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_door")), strippedLog, TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(log), blockModels));
            createModel(blockModels, createKitchenCounterSmallDrawer(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_small_drawer")), strippedLog, TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(log), blockModels));
            createModel(blockModels, createKitchenCounterBigDrawer(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_big_drawer")), strippedLog, TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(log), blockModels));
            createModel(blockModels, createKitchenCounterSink(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_sink")), strippedLog, TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCounterSmoker(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_smoker")), strippedLog, TextureMapping.getBlockTexture(block, s), TextureMapping.getBlockTexture(SMOKER, "_front"), TextureMapping.getBlockTexture(SMOKER, "_front_on"), blockModels));
            createModel(blockModels, createKitchenCabinetDoor(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_door")), strippedLog, TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCabinetGlassDoor(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_glass_door")), strippedLog, TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCabinetSidewaysDoor(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_door")), strippedLog, TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(block, s), blockModels));
            createModel(blockModels, createKitchenCabinetSidewaysGlassDoor(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_glass_door")), strippedLog, TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(block, s), blockModels));

            createModel(blockModels, createKnifeBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_knife_block")), planks, TextureMapping.getBlockTexture(block, s), blockModels));
        }));

        createModel(blockModels, createKitchenCabinet(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_kitchen_cabinet")), planks, blockModels));
        createModel(blockModels, createKitchenCabinetShelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_kitchen_cabinet_shelf")), planks, blockModels));
        createModel(blockModels, createKitchenShelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_kitchen_shelf")), planks, TextureMapping.getBlockTexture(CHAIN), blockModels));

        createModel(blockModels, createKitchenCabinet(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_kitchen_cabinet")), log, blockModels));
        createModel(blockModels, createKitchenCabinetShelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_kitchen_cabinet_shelf")), log, blockModels));
        createModel(blockModels, createKitchenShelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_kitchen_shelf")), log, TextureMapping.getBlockTexture(CHAIN), blockModels));

        createModel(blockModels, createKitchenCabinet(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_kitchen_cabinet")), strippedLog, blockModels));
        createModel(blockModels, createKitchenCabinetShelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_kitchen_cabinet_shelf")), strippedLog, blockModels));
        createModel(blockModels, createKitchenShelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_kitchen_shelf")), strippedLog, TextureMapping.getBlockTexture(CHAIN), blockModels));

        createModel(blockModels, createTableBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_table")), planks, blockModels));
        createModel(blockModels, createTableBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_table")), log, blockModels));
        createModel(blockModels, createTableBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_table")), strippedLog, blockModels));

        WOOL_COLORS.forEach((block, color) -> BlockFamilies.getAllFamilies().toList().forEach(blockFamily -> {
            if (blockFamily.getBaseBlock() == planks) {
                createModel(blockModels, createChairBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_"+type.name()+"_chair")), planks, TextureMapping.getBlockTexture(blockFamily.get(BlockFamily.Variant.TRAPDOOR)), TextureMapping.getBlockTexture(block), blockModels));
                createModel(blockModels, createChairBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_"+type.name()+log_suffix+"_chair")), log, TextureMapping.getBlockTexture(blockFamily.get(BlockFamily.Variant.TRAPDOOR)), TextureMapping.getBlockTexture(block), blockModels));
                createModel(blockModels, createChairBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_stripped_"+type.name()+log_suffix+"_chair")), strippedLog, TextureMapping.getBlockTexture(blockFamily.get(BlockFamily.Variant.TRAPDOOR)), TextureMapping.getBlockTexture(block), blockModels));
                createModel(blockModels, createLampBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_"+type.name()+"_lamp")), planks, TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(log, "_top"), TextureMapping.getBlockTexture(strippedLog), TextureMapping.getBlockTexture(REDSTONE_LAMP), TextureMapping.getBlockTexture(REDSTONE_LAMP, "_on"), TextureMapping.getBlockTexture(block), blockModels));
                createModel(blockModels, createWoodenBedBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_"+type.name()+"_bed")), TextureMapping.getBlockTexture(planks), TextureMapping.getBlockTexture(block), TextureMapping.getBlockTexture(blockFamily.get(BlockFamily.Variant.TRAPDOOR)), blockModels));
                createModel(blockModels, createWoodenBedBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_"+type.name()+log_suffix+"_bed")), TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(block), TextureMapping.getBlockTexture(blockFamily.get(BlockFamily.Variant.TRAPDOOR)), blockModels));
                createModel(blockModels, createWoodenBedBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_stripped_"+type.name()+log_suffix+"_bed")), TextureMapping.getBlockTexture(strippedLog), TextureMapping.getBlockTexture(block), TextureMapping.getBlockTexture(blockFamily.get(BlockFamily.Variant.TRAPDOOR)), blockModels));
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
        ResourceLocation straightModel = ModelTemplates.KITCHEN_COUNTER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top), blockModels.modelOutput);
        ResourceLocation innerCornerModel = ModelTemplates.KITCHEN_COUNTER_INNER_CORNER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top), blockModels.modelOutput);
        ResourceLocation outerCornerModel = ModelTemplates.KITCHEN_COUNTER_OUTER_CORNER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top), blockModels.modelOutput);

        return MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, KitchenCounterBlock.SHAPE)
                .select(Direction.EAST, CounterShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, straightModel).with(VariantProperties.Y_ROT, Rotation.R180).with(VariantProperties.UV_LOCK, true))
                .select(Direction.WEST, CounterShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, straightModel))
                .select(Direction.SOUTH, CounterShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, straightModel).with(VariantProperties.Y_ROT, Rotation.R270).with(VariantProperties.UV_LOCK, true))
                .select(Direction.NORTH, CounterShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, straightModel).with(VariantProperties.Y_ROT, Rotation.R90).with(VariantProperties.UV_LOCK, true))
                .select(Direction.EAST, CounterShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, outerCornerModel).with(VariantProperties.Y_ROT, Rotation.R180).with(VariantProperties.UV_LOCK, true))
                .select(Direction.WEST, CounterShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, outerCornerModel))
                .select(Direction.SOUTH, CounterShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, outerCornerModel).with(VariantProperties.Y_ROT, Rotation.R270).with(VariantProperties.UV_LOCK, true))
                .select(Direction.NORTH, CounterShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, outerCornerModel).with(VariantProperties.Y_ROT, Rotation.R90).with(VariantProperties.UV_LOCK, true))
                .select(Direction.EAST, CounterShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, outerCornerModel).with(VariantProperties.Y_ROT, Rotation.R90).with(VariantProperties.UV_LOCK, true))
                .select(Direction.WEST, CounterShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, outerCornerModel).with(VariantProperties.Y_ROT, Rotation.R270).with(VariantProperties.UV_LOCK, true))
                .select(Direction.SOUTH, CounterShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, outerCornerModel).with(VariantProperties.Y_ROT, Rotation.R180).with(VariantProperties.UV_LOCK, true))
                .select(Direction.NORTH, CounterShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, outerCornerModel))
                .select(Direction.EAST, CounterShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, innerCornerModel).with(VariantProperties.Y_ROT, Rotation.R180).with(VariantProperties.UV_LOCK, true))
                .select(Direction.WEST, CounterShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, innerCornerModel))
                .select(Direction.SOUTH, CounterShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, innerCornerModel).with(VariantProperties.Y_ROT, Rotation.R270).with(VariantProperties.UV_LOCK, true))
                .select(Direction.NORTH, CounterShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, innerCornerModel).with(VariantProperties.Y_ROT, Rotation.R90).with(VariantProperties.UV_LOCK, true))
                .select(Direction.EAST, CounterShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, innerCornerModel).with(VariantProperties.Y_ROT, Rotation.R90).with(VariantProperties.UV_LOCK, true))
                .select(Direction.WEST, CounterShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, innerCornerModel).with(VariantProperties.Y_ROT, Rotation.R270).with(VariantProperties.UV_LOCK, true))
                .select(Direction.SOUTH, CounterShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, innerCornerModel).with(VariantProperties.Y_ROT, Rotation.R180).with(VariantProperties.UV_LOCK, true))
                .select(Direction.NORTH, CounterShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, innerCornerModel)));
    }

    private MultiVariantGenerator createKitchenCounterShelf(Block block, Block counter, ResourceLocation top, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.KITCHEN_COUNTER_SHELF.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, model)).with(createUVLockedHorizontalFacingDispatch());
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
        ).with(createUVLockedHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCounterSmallDrawer(Block block, Block counter, ResourceLocation top, ResourceLocation door, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door);
        ResourceLocation closed = ModelTemplates.KITCHEN_COUNTER_SMALL_DRAWER.create(block, mapping, blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.KITCHEN_COUNTER_SMALL_DRAWER_OPEN.create(block, mapping, blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, closed)).with(
                PropertyDispatch.property(KitchenCounterContainerDrawerBlock.OPEN)
                        .select(false, Variant.variant().with(MODEL, closed))
                        .select(true, Variant.variant().with(MODEL, open))
        ).with(createUVLockedHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCounterBigDrawer(Block block, Block counter, ResourceLocation top, ResourceLocation door, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door);
        ResourceLocation closed = ModelTemplates.KITCHEN_COUNTER_BIG_DRAWER.create(block, mapping, blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.KITCHEN_COUNTER_BIG_DRAWER_OPEN.create(block, mapping, blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, closed)).with(
                PropertyDispatch.property(KitchenCounterContainerDrawerBlock.OPEN)
                        .select(false, Variant.variant().with(MODEL, closed))
                        .select(true, Variant.variant().with(MODEL, open))
        ).with(createUVLockedHorizontalFacingDispatch());
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
        ).with(createUVLockedHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCounterSmoker(Block block, Block counter, ResourceLocation top, ResourceLocation furnace, ResourceLocation furnace_lit, BlockModelGenerators blockModels) {
        ResourceLocation unlit = ModelTemplates.KITCHEN_COUNTER_SMOKER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.FURNACE, furnace), blockModels.modelOutput);
        ResourceLocation lit = ModelTemplates.KITCHEN_COUNTER_SMOKER_LIT.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.FURNACE, furnace_lit), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, unlit)).with(
                PropertyDispatch.property(SmokerBlock.LIT)
                        .select(false, Variant.variant().with(MODEL, unlit))
                        .select(true, Variant.variant().with(MODEL, lit))
        ).with(createUVLockedHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCabinet(Block block, Block cabinet, BlockModelGenerators blockModels) {
        ResourceLocation straightModel = ModelTemplates.KITCHEN_CABINET.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)), blockModels.modelOutput);
        ResourceLocation innerCornerModel = ModelTemplates.KITCHEN_CABINET_INNER_CORNER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)), blockModels.modelOutput);
        ResourceLocation outerCornerModel = ModelTemplates.KITCHEN_CABINET_OUTER_CORNER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)), blockModels.modelOutput);

        return MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, KitchenCabinetBlock.SHAPE)
                .select(Direction.EAST, CounterShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, straightModel).with(VariantProperties.Y_ROT, Rotation.R180).with(VariantProperties.UV_LOCK, true))
                .select(Direction.WEST, CounterShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, straightModel))
                .select(Direction.SOUTH, CounterShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, straightModel).with(VariantProperties.Y_ROT, Rotation.R270).with(VariantProperties.UV_LOCK, true))
                .select(Direction.NORTH, CounterShape.STRAIGHT, Variant.variant().with(VariantProperties.MODEL, straightModel).with(VariantProperties.Y_ROT, Rotation.R90).with(VariantProperties.UV_LOCK, true))
                .select(Direction.EAST, CounterShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, outerCornerModel).with(VariantProperties.Y_ROT, Rotation.R180).with(VariantProperties.UV_LOCK, true))
                .select(Direction.WEST, CounterShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, outerCornerModel))
                .select(Direction.SOUTH, CounterShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, outerCornerModel).with(VariantProperties.Y_ROT, Rotation.R270).with(VariantProperties.UV_LOCK, true))
                .select(Direction.NORTH, CounterShape.OUTER_RIGHT, Variant.variant().with(VariantProperties.MODEL, outerCornerModel).with(VariantProperties.Y_ROT, Rotation.R90).with(VariantProperties.UV_LOCK, true))
                .select(Direction.EAST, CounterShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, outerCornerModel).with(VariantProperties.Y_ROT, Rotation.R90).with(VariantProperties.UV_LOCK, true))
                .select(Direction.WEST, CounterShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, outerCornerModel).with(VariantProperties.Y_ROT, Rotation.R270).with(VariantProperties.UV_LOCK, true))
                .select(Direction.SOUTH, CounterShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, outerCornerModel).with(VariantProperties.Y_ROT, Rotation.R180).with(VariantProperties.UV_LOCK, true))
                .select(Direction.NORTH, CounterShape.OUTER_LEFT, Variant.variant().with(VariantProperties.MODEL, outerCornerModel))
                .select(Direction.EAST, CounterShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, innerCornerModel).with(VariantProperties.Y_ROT, Rotation.R180).with(VariantProperties.UV_LOCK, true))
                .select(Direction.WEST, CounterShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, innerCornerModel))
                .select(Direction.SOUTH, CounterShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, innerCornerModel).with(VariantProperties.Y_ROT, Rotation.R270).with(VariantProperties.UV_LOCK, true))
                .select(Direction.NORTH, CounterShape.INNER_RIGHT, Variant.variant().with(VariantProperties.MODEL, innerCornerModel).with(VariantProperties.Y_ROT, Rotation.R90).with(VariantProperties.UV_LOCK, true))
                .select(Direction.EAST, CounterShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, innerCornerModel).with(VariantProperties.Y_ROT, Rotation.R90).with(VariantProperties.UV_LOCK, true))
                .select(Direction.WEST, CounterShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, innerCornerModel).with(VariantProperties.Y_ROT, Rotation.R270).with(VariantProperties.UV_LOCK, true))
                .select(Direction.SOUTH, CounterShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, innerCornerModel).with(VariantProperties.Y_ROT, Rotation.R180).with(VariantProperties.UV_LOCK, true))
                .select(Direction.NORTH, CounterShape.INNER_LEFT, Variant.variant().with(VariantProperties.MODEL, innerCornerModel)));
    }

    private MultiVariantGenerator createKitchenCabinetShelf(Block block, Block cabinet, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.KITCHEN_CABINET_SHELF.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, model)).with(createUVLockedHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCabinetDoor(Block block, Block cabinet, ResourceLocation door, ResourceLocation handle, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle);
        ResourceLocation closed = ModelTemplates.KITCHEN_CABINET_DOOR.create(block, mapping, blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.KITCHEN_CABINET_DOOR_OPEN.create(block, mapping, blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, closed)).with(
                PropertyDispatch.property(KitchenCabinetContainerDoorBlock.OPEN)
                        .select(false, Variant.variant().with(MODEL, closed))
                        .select(true, Variant.variant().with(MODEL, open))
        ).with(createUVLockedHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenCabinetGlassDoor(Block block, Block cabinet, ResourceLocation door, ResourceLocation handle, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle).put(TextureSlots.GLASS, TextureMapping.getBlockTexture(GLASS));
        ResourceLocation closed = ModelTemplates.KITCHEN_CABINET_GLASS_DOOR.create(block, mapping, blockModels.modelOutput);
        ResourceLocation open = ModelTemplates.KITCHEN_CABINET_GLASS_DOOR_OPEN.create(block, mapping, blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, closed)).with(
                PropertyDispatch.property(KitchenCabinetContainerDoorBlock.OPEN)
                        .select(false, Variant.variant().with(MODEL, closed))
                        .select(true, Variant.variant().with(MODEL, open))
        ).with(createUVLockedHorizontalFacingDispatch());
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
        ).with(createUVLockedHorizontalFacingDispatch());
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
        ).with(createUVLockedHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKitchenShelf(Block block, Block planks, ResourceLocation chain, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.CHAIN, chain);
        ResourceLocation single = ModelTemplates.KITCHEN_SHELF_SINGLE.create(block, mapping, blockModels.modelOutput);
        ResourceLocation left = ModelTemplates.KITCHEN_SHELF_LEFT.create(block, mapping, blockModels.modelOutput);
        ResourceLocation right = ModelTemplates.KITCHEN_SHELF_RIGHT.create(block, mapping, blockModels.modelOutput);
        ResourceLocation middle = ModelTemplates.KITCHEN_SHELF_MIDDLE.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, single)).with(
                PropertyDispatch.property(KitchenCabinetShelfBlock.SHAPE)
                        .select(ShelfShape.SINGLE, Variant.variant().with(MODEL, single))
                        .select(ShelfShape.LEFT, Variant.variant().with(MODEL, left))
                        .select(ShelfShape.RIGHT, Variant.variant().with(MODEL, right))
                        .select(ShelfShape.MIDDLE, Variant.variant().with(MODEL, middle))
        ).with(createUVLockedHorizontalFacingDispatch());
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
        ).with(createUVLockedHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createKnifeBlock(Block block, Block blockTexture, ResourceLocation stand, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.KNIFE_BLOCK.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(blockTexture)).put(TextureSlots.BLOCK, TextureMapping.getBlockTexture(blockTexture)).put(TextureSlots.STAND, stand).put(TextureSlots.HANDLE, TextureMapping.getBlockTexture(ANVIL)).put(TextureSlots.KNIFE, TextureMapping.getBlockTexture(IRON_BLOCK)), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, model)).with(createUVLockedHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createChairBlock(Block block, Block chair, ResourceLocation decoration, ResourceLocation wool, BlockModelGenerators blockModels) {
        ResourceLocation model = ModelTemplates.CHAIR.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(chair)).put(TextureSlots.CHAIR, TextureMapping.getBlockTexture(chair)).put(TextureSlots.DECORATION, decoration).put(TextureSlots.WOOL, wool), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, model)).with(createUVLockedHorizontalFacingDispatch());
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
        ).with(createUVLockedHorizontalFacingDispatch());
    }

    private MultiVariantGenerator createLampBlock(Block block, Block planks, ResourceLocation log, ResourceLocation log_top, ResourceLocation stripped_log, ResourceLocation lamp, ResourceLocation lamp_on, ResourceLocation wool, BlockModelGenerators blockModels) {
        ResourceLocation model_single = ModelTemplates.LAMP_SINGLE.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.LOG, log).put(TextureSlots.LOG_TOP, log_top).put(TextureSlots.STRIPPED_LOG, stripped_log).put(TextureSlots.LAMP, lamp).put(TextureSlots.WOOL, wool), blockModels.modelOutput);
        ResourceLocation model_single_on = ModelTemplates.LAMP_SINGLE_ON.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.LOG, log).put(TextureSlots.LOG_TOP, log_top).put(TextureSlots.STRIPPED_LOG, stripped_log).put(TextureSlots.LAMP, lamp_on).put(TextureSlots.WOOL, wool), blockModels.modelOutput);
        ResourceLocation model_top = ModelTemplates.LAMP_TOP.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.LOG, log).put(TextureSlots.LAMP, lamp).put(TextureSlots.WOOL, wool), blockModels.modelOutput);
        ResourceLocation model_top_on = ModelTemplates.LAMP_TOP_ON.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.LOG, log).put(TextureSlots.LAMP, lamp_on).put(TextureSlots.WOOL, wool), blockModels.modelOutput);
        ResourceLocation model_middle = ModelTemplates.LAMP_MIDDLE.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.LOG, log), blockModels.modelOutput);
        ResourceLocation model_bottom = ModelTemplates.LAMP_BOTTOM.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.LOG, log).put(TextureSlots.LOG_TOP, log_top).put(TextureSlots.STRIPPED_LOG, stripped_log), blockModels.modelOutput);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, model_single)).with(
                PropertyDispatch.properties(LampBlock.ON, LampBlock.PART)
                        .select(true, LampPart.SINGLE, Variant.variant().with(MODEL, model_single_on))
                        .select(false, LampPart.SINGLE, Variant.variant().with(MODEL, model_single))
                        .select(true, LampPart.TOP, Variant.variant().with(MODEL, model_top_on))
                        .select(false, LampPart.TOP, Variant.variant().with(MODEL, model_top))
                        .select(true, LampPart.MIDDLE, Variant.variant().with(MODEL, model_middle))
                        .select(false, LampPart.MIDDLE, Variant.variant().with(MODEL, model_middle))
                        .select(true, LampPart.BOTTOM, Variant.variant().with(MODEL, model_bottom))
                        .select(false, LampPart.BOTTOM, Variant.variant().with(MODEL, model_bottom))
        );
    }

    private MultiVariantGenerator createWoodenBedBlock(Block block, ResourceLocation wood, ResourceLocation wool, ResourceLocation decoration, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, wood).put(TextureSlots.WOOD, wood).put(TextureSlots.WOOL, wool).put(TextureSlots.DECORATION, decoration);
        ResourceLocation head_single = ModelTemplates.WOODEN_BED_HEAD_SINGLE.create(block, mapping, blockModels.modelOutput);
        ResourceLocation head_left = ModelTemplates.WOODEN_BED_HEAD_LEFT.create(block, mapping, blockModels.modelOutput);
        ResourceLocation head_right = ModelTemplates.WOODEN_BED_HEAD_RIGHT.create(block, mapping, blockModels.modelOutput);
        ResourceLocation head_middle = ModelTemplates.WOODEN_BED_HEAD_MIDDLE.create(block, mapping, blockModels.modelOutput);
        ResourceLocation foot_single = ModelTemplates.WOODEN_BED_FOOT_SINGLE.create(block, mapping, blockModels.modelOutput);
        ResourceLocation foot_left = ModelTemplates.WOODEN_BED_FOOT_LEFT.create(block, mapping, blockModels.modelOutput);
        ResourceLocation foot_right = ModelTemplates.WOODEN_BED_FOOT_RIGHT.create(block, mapping, blockModels.modelOutput);
        ResourceLocation foot_middle = ModelTemplates.WOODEN_BED_FOOT_MIDDLE.create(block, mapping, blockModels.modelOutput);
        ResourceLocation item = ModelTemplates.WOODEN_BED_ITEM.create(block, mapping, blockModels.modelOutput);
        blockModels.registerSimpleItemModel(block, item);
        return MultiVariantGenerator.multiVariant(block, Variant.variant().with(MODEL, head_single)).with(
                PropertyDispatch.properties(WoodenBedBlock.PART, WoodenBedBlock.SHAPE, WoodenBedBlock.OCCUPIED)
                        .select(BedPart.HEAD, BedShape.SINGLE, false, Variant.variant().with(MODEL, head_single))
                        .select(BedPart.HEAD, BedShape.SINGLE, true, Variant.variant().with(MODEL, head_single))
                        .select(BedPart.HEAD, BedShape.LEFT, false, Variant.variant().with(MODEL, head_left))
                        .select(BedPart.HEAD, BedShape.LEFT, true, Variant.variant().with(MODEL, head_left))
                        .select(BedPart.HEAD, BedShape.RIGHT, false, Variant.variant().with(MODEL, head_right))
                        .select(BedPart.HEAD, BedShape.RIGHT, true, Variant.variant().with(MODEL, head_right))
                        .select(BedPart.HEAD, BedShape.MIDDLE, false, Variant.variant().with(MODEL, head_middle))
                        .select(BedPart.HEAD, BedShape.MIDDLE, true, Variant.variant().with(MODEL, head_middle))
                        .select(BedPart.FOOT, BedShape.SINGLE, false, Variant.variant().with(MODEL, foot_single))
                        .select(BedPart.FOOT, BedShape.SINGLE, true, Variant.variant().with(MODEL, foot_single))
                        .select(BedPart.FOOT, BedShape.LEFT, false, Variant.variant().with(MODEL, foot_left))
                        .select(BedPart.FOOT, BedShape.LEFT, true, Variant.variant().with(MODEL, foot_left))
                        .select(BedPart.FOOT, BedShape.RIGHT, false, Variant.variant().with(MODEL, foot_right))
                        .select(BedPart.FOOT, BedShape.RIGHT, true, Variant.variant().with(MODEL, foot_right))
                        .select(BedPart.FOOT, BedShape.MIDDLE, false, Variant.variant().with(MODEL, foot_middle))
                        .select(BedPart.FOOT, BedShape.MIDDLE, true, Variant.variant().with(MODEL, foot_middle))
        ).with(createBedFacingDispatch());
    }

    public static PropertyDispatch createUVLockedHorizontalFacingDispatch() {
        return PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING).select(Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, Rotation.R270).with(VariantProperties.UV_LOCK, true)).select(Direction.NORTH, Variant.variant());
    }

    public static PropertyDispatch createBedFacingDispatch() {
        return PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING).select(Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, Rotation.R270).with(VariantProperties.UV_LOCK, true)).select(Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, Rotation.R90).with(VariantProperties.UV_LOCK, true)).select(Direction.NORTH, Variant.variant().with(VariantProperties.Y_ROT, Rotation.R180).with(VariantProperties.UV_LOCK, true)).select(Direction.SOUTH, Variant.variant());
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
        public static final TextureSlot WOOD = TextureSlot.create("wood");
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
        public static final ModelTemplate KITCHEN_COUNTER_INNER_CORNER = getTemplate("kitchen_counter_inner_corner_template", Optional.of("_inner"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP);
        public static final ModelTemplate KITCHEN_COUNTER_OUTER_CORNER = getTemplate("kitchen_counter_outer_corner_template", Optional.of("_outer"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP);
        public static final ModelTemplate KITCHEN_COUNTER_SHELF = getTemplate("kitchen_counter_shelf_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP);
        public static final ModelTemplate KITCHEN_COUNTER_DOOR = getTemplate("kitchen_counter_door_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);
        public static final ModelTemplate KITCHEN_COUNTER_DOOR_OPEN = getTemplate("kitchen_counter_door_open_template", Optional.of("_open"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);
        public static final ModelTemplate KITCHEN_COUNTER_DOOR_MIRRORED = getTemplate("kitchen_counter_door_mirrored_template", Optional.of("_mirrored"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);
        public static final ModelTemplate KITCHEN_COUNTER_DOOR_MIRRORED_OPEN = getTemplate("kitchen_counter_door_mirrored_open_template", Optional.of("_mirrored_open"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);
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
        public static final ModelTemplate KITCHEN_CABINET_INNER_CORNER = getTemplate("kitchen_cabinet_inner_corner_template", Optional.of("_inner"), TextureSlot.PARTICLE, TextureSlots.CABINET);
        public static final ModelTemplate KITCHEN_CABINET_OUTER_CORNER = getTemplate("kitchen_cabinet_outer_corner_template", Optional.of("_outer"), TextureSlot.PARTICLE, TextureSlots.CABINET);
        public static final ModelTemplate KITCHEN_CABINET_SHELF = getTemplate("kitchen_cabinet_shelf_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.CABINET);
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
        public static final ModelTemplate KITCHEN_SHELF_SINGLE = getTemplate("kitchen_shelf_single_template", Optional.of("_single"), TextureSlot.PARTICLE, TextureSlots.PLANKS, TextureSlots.CHAIN);
        public static final ModelTemplate KITCHEN_SHELF_RIGHT = getTemplate("kitchen_shelf_right_template", Optional.of("_right"), TextureSlot.PARTICLE, TextureSlots.PLANKS, TextureSlots.CHAIN);
        public static final ModelTemplate KITCHEN_SHELF_LEFT = getTemplate("kitchen_shelf_left_template", Optional.of("_left"), TextureSlot.PARTICLE, TextureSlots.PLANKS, TextureSlots.CHAIN);
        public static final ModelTemplate KITCHEN_SHELF_MIDDLE = getTemplate("kitchen_shelf_middle_template", Optional.of("_middle"), TextureSlot.PARTICLE, TextureSlots.PLANKS);

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

        public static final ModelTemplate LAMP_SINGLE = getTemplate("lamp_single_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.LOG, TextureSlots.STRIPPED_LOG, TextureSlots.LOG_TOP, TextureSlots.PLANKS, TextureSlots.WOOL, TextureSlots.LAMP);
        public static final ModelTemplate LAMP_SINGLE_ON = getTemplate("lamp_single_template", Optional.of("_on"), TextureSlot.PARTICLE, TextureSlots.LOG, TextureSlots.STRIPPED_LOG, TextureSlots.LOG_TOP, TextureSlots.PLANKS, TextureSlots.WOOL, TextureSlots.LAMP);
        public static final ModelTemplate LAMP_TOP = getTemplate("lamp_top_template", Optional.of("_top"), TextureSlot.PARTICLE, TextureSlots.LOG, TextureSlots.PLANKS, TextureSlots.WOOL, TextureSlots.LAMP);
        public static final ModelTemplate LAMP_TOP_ON = getTemplate("lamp_top_template", Optional.of("top_on"), TextureSlot.PARTICLE, TextureSlots.LOG, TextureSlots.PLANKS, TextureSlots.WOOL, TextureSlots.LAMP);
        public static final ModelTemplate LAMP_MIDDLE = getTemplate("lamp_middle_template", Optional.of("_middle"), TextureSlot.PARTICLE, TextureSlots.LOG);
        public static final ModelTemplate LAMP_BOTTOM = getTemplate("lamp_bottom_template", Optional.of("_bottom"), TextureSlot.PARTICLE, TextureSlots.LOG, TextureSlots.STRIPPED_LOG, TextureSlots.LOG_TOP);

        public static final ModelTemplate WOODEN_BED_HEAD_SINGLE = getTemplate("wooden_bed_head_single_template", Optional.of("_head_single"), TextureSlot.PARTICLE, TextureSlots.WOOD, TextureSlots.WOOL, TextureSlots.DECORATION);
        public static final ModelTemplate WOODEN_BED_HEAD_LEFT = getTemplate("wooden_bed_head_left_template", Optional.of("_head_left"), TextureSlot.PARTICLE, TextureSlots.WOOD, TextureSlots.WOOL, TextureSlots.DECORATION);
        public static final ModelTemplate WOODEN_BED_HEAD_RIGHT = getTemplate("wooden_bed_head_right_template", Optional.of("_head_right"), TextureSlot.PARTICLE, TextureSlots.WOOD, TextureSlots.WOOL, TextureSlots.DECORATION);
        public static final ModelTemplate WOODEN_BED_HEAD_MIDDLE = getTemplate("wooden_bed_head_middle_template", Optional.of("_head_middle"), TextureSlot.PARTICLE, TextureSlots.WOOD, TextureSlots.WOOL, TextureSlots.DECORATION);
        public static final ModelTemplate WOODEN_BED_FOOT_SINGLE = getTemplate("wooden_bed_foot_single_template", Optional.of("_foot_single"), TextureSlot.PARTICLE, TextureSlots.WOOD, TextureSlots.WOOL, TextureSlots.DECORATION);
        public static final ModelTemplate WOODEN_BED_FOOT_LEFT = getTemplate("wooden_bed_foot_left_template", Optional.of("_foot_left"), TextureSlot.PARTICLE, TextureSlots.WOOD, TextureSlots.WOOL, TextureSlots.DECORATION);
        public static final ModelTemplate WOODEN_BED_FOOT_RIGHT = getTemplate("wooden_bed_foot_right_template", Optional.of("_foot_right"), TextureSlot.PARTICLE, TextureSlots.WOOD, TextureSlots.WOOL, TextureSlots.DECORATION);
        public static final ModelTemplate WOODEN_BED_FOOT_MIDDLE = getTemplate("wooden_bed_foot_middle_template", Optional.of("_foot_middle"), TextureSlot.PARTICLE, TextureSlots.WOOD, TextureSlots.WOOL, TextureSlots.DECORATION);
        public static final ModelTemplate WOODEN_BED_ITEM = getTemplate("wooden_bed_item_template", Optional.of("_item"), TextureSlot.PARTICLE, TextureSlots.WOOD, TextureSlots.WOOL, TextureSlots.DECORATION);

        private static ModelTemplate getTemplate(String name, Optional<String> suffix, TextureSlot... slots) {
            return new ModelTemplate(Optional.of(FabulousFurniture.prefix(name).withPrefix("block/template/")), suffix, slots);
        }
    }
}