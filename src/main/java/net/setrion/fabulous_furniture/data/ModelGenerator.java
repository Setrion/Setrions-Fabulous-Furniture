package net.setrion.fabulous_furniture.data;

import com.mojang.math.Quadrant;
import net.minecraft.client.data.models.*;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SmokerBlock;
import net.minecraft.world.level.block.state.properties.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.setrion.fabulous_furniture.FabulousFurniture;
import net.setrion.fabulous_furniture.world.level.block.*;
import net.setrion.fabulous_furniture.world.level.block.state.properties.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.minecraft.client.data.models.BlockModelGenerators.ROTATION_HORIZONTAL_FACING;
import static net.minecraft.client.data.models.BlockModelGenerators.ROTATION_HORIZONTAL_FACING_ALT;
import static net.minecraft.client.renderer.block.model.VariantMutator.*;
import static net.minecraft.world.level.block.Blocks.*;

import static net.setrion.fabulous_furniture.registry.SFFBlocks.*;

public class ModelGenerator extends ModelProvider {

    public ModelGenerator(PackOutput output) {
        super(output, FabulousFurniture.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        WOOD_TYPES.forEach(type -> generateWoodenFurniture(type, blockModels));
        createBirdbaths(blockModels);
        createCurtains(blockModels);
        createKitchenTiles(blockModels);

        blockModels.createCraftingTableLike(CARPENTRY_TABLE.get(), MANGROVE_PLANKS, TextureMapping::craftingTable);

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

        createTableware(blockModels);
        createToasters(blockModels);

        createHedgeBlock(OAK_HEDGE.get(), TextureMapping.getBlockTexture(OAK_LEAVES), blockModels, -12012264);
        createHedgeBlock(SPRUCE_HEDGE.get(), TextureMapping.getBlockTexture(SPRUCE_LEAVES), blockModels, -10380959);
        createHedgeBlock(BIRCH_HEDGE.get(), TextureMapping.getBlockTexture(BIRCH_LEAVES), blockModels, -8345771);
        createHedgeBlock(JUNGLE_HEDGE.get(), TextureMapping.getBlockTexture(JUNGLE_LEAVES), blockModels, -12012264);
        createHedgeBlock(ACACIA_HEDGE.get(), TextureMapping.getBlockTexture(ACACIA_LEAVES), blockModels, -12012264);
        createHedgeBlock(CHERRY_HEDGE.get(), TextureMapping.getBlockTexture(CHERRY_LEAVES), blockModels);
        createHedgeBlock(DARK_OAK_HEDGE.get(), TextureMapping.getBlockTexture(DARK_OAK_LEAVES), blockModels, -12012264);
        createHedgeBlock(PALE_OAK_HEDGE.get(), TextureMapping.getBlockTexture(PALE_OAK_LEAVES), blockModels);
        createHedgeBlock(MANGROVE_HEDGE.get(), TextureMapping.getBlockTexture(MANGROVE_LEAVES), blockModels, -7158200);
        createHedgeBlock(AZALEA_HEDGE.get(), TextureMapping.getBlockTexture(AZALEA_LEAVES), blockModels);
        createHedgeBlock(FLOWERING_AZALEA_HEDGE.get(), TextureMapping.getBlockTexture(FLOWERING_AZALEA_LEAVES), blockModels);

        Collection<Block> blocks = BLOCKS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toList());
        blocks.forEach((block -> {
            if (block instanceof ItemModelSupplier itemModel) {
                if (itemModel.hasSeparateModel()) {
                    registerBasicBlockModel(blockModels.itemModelOutput, block, itemModel.getItemModelSuffix());
                }
            } else if (block instanceof BirdbathBlock) {
                registerBirdbathModel(blockModels.itemModelOutput, block);
            } else {
                registerBasicBlockModel(blockModels.itemModelOutput, block);
            }
        }));
    }

    private void createCurtains(BlockModelGenerators blockModels) {
        WOOL_COLORS.forEach((wool, color) -> METALS.forEach((rod, name) -> createModel(blockModels, createCurtainBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_"+name+"_curtains")), TextureMapping.getBlockTexture(wool), TextureMapping.getBlockTexture(rod), blockModels))));
    }

    private void createBirdbaths(BlockModelGenerators blockModels) {
        STONE_MATERIALS.forEach((block, s) -> {
            String stone_name = block.getDescriptionId().replaceFirst("block.minecraft.", "").replaceFirst("quartz_block", "quartz");
            createModel(blockModels, createBirdbathBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(stone_name+"_birdbath")), TextureMapping.getBlockTexture(block, s), blockModels));
        });
    }

    private void createTableware(BlockModelGenerators blockModels) {
        TABLEWARE_MATERIALS.forEach((block, suffix) -> {
            String top_name = block.getDescriptionId().replaceFirst("block.minecraft.", "").replaceFirst("quartz_block", "quartz");
            createModel(blockModels, createTablewareBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(top_name+"_tableware")), TextureMapping.getBlockTexture(block, suffix), TextureMapping.getBlockTexture(ANCIENT_DEBRIS, "_top"), blockModels));
        });
    }

    private void createToasters(BlockModelGenerators blockModels) {
        METALS.forEach((metal, name) -> createModel(blockModels, createToasterBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(name+"_toaster")), metal, TextureMapping.getBlockTexture(ANVIL), blockModels)));
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
        STONE_MATERIALS.forEach(((block, s) -> {
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

        createModel(blockModels, createBedsideTableBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_bedside_table")), planks, TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(log), blockModels));
        createModel(blockModels, createBedsideTableBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_bedside_table")), log, TextureMapping.getBlockTexture(strippedLog), TextureMapping.getBlockTexture(strippedLog), blockModels));
        createModel(blockModels, createBedsideTableBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_bedside_table")), strippedLog, TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(log), blockModels));

        createModel(blockModels, createCloset(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_closet")), TextureMapping.getBlockTexture(planks), TextureMapping.getBlockTexture(log), blockModels));
        createModel(blockModels, createCloset(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_closet")), TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(strippedLog), blockModels));
        createModel(blockModels, createCloset(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_closet")), TextureMapping.getBlockTexture(strippedLog), TextureMapping.getBlockTexture(log), blockModels));

        createModel(blockModels, createFlowerBoxBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_flower_box")), TextureMapping.getBlockTexture(planks), TextureMapping.getBlockTexture(planks), blockModels));
        createModel(blockModels, createFlowerBoxBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_flower_box")), TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(log, "_top"), blockModels));
        createModel(blockModels, createFlowerBoxBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_flower_box")), TextureMapping.getBlockTexture(strippedLog), TextureMapping.getBlockTexture(strippedLog, "_top"), blockModels));

        createModel(blockModels, createFlowerBoxInnerCornerBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_flower_box_inner_corner")), TextureMapping.getBlockTexture(planks), TextureMapping.getBlockTexture(planks), blockModels));
        createModel(blockModels, createFlowerBoxInnerCornerBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_flower_box_inner_corner")), TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(log, "_top"), blockModels));
        createModel(blockModels, createFlowerBoxInnerCornerBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_flower_box_inner_corner")), TextureMapping.getBlockTexture(strippedLog), TextureMapping.getBlockTexture(strippedLog, "_top"), blockModels));

        createModel(blockModels, createFlowerBoxOuterCornerBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_flower_box_outer_corner")), TextureMapping.getBlockTexture(planks), TextureMapping.getBlockTexture(planks), blockModels));
        createModel(blockModels, createFlowerBoxOuterCornerBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_flower_box_outer_corner")), TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(log, "_top"), blockModels));
        createModel(blockModels, createFlowerBoxOuterCornerBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_flower_box_outer_corner")), TextureMapping.getBlockTexture(strippedLog), TextureMapping.getBlockTexture(strippedLog, "_top"), blockModels));

        createModel(blockModels, createFlowerBoxBigBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_flower_box_big")), TextureMapping.getBlockTexture(planks), TextureMapping.getBlockTexture(planks), blockModels));
        createModel(blockModels, createFlowerBoxBigBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_flower_box_big")), TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(log, "_top"), blockModels));
        createModel(blockModels, createFlowerBoxBigBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_flower_box_big")), TextureMapping.getBlockTexture(strippedLog), TextureMapping.getBlockTexture(strippedLog, "_top"), blockModels));

        createModel(blockModels, createTrashBinBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_trash_bin")), TextureMapping.getBlockTexture(planks), blockModels));
        createModel(blockModels, createTrashBinBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_trash_bin")), TextureMapping.getBlockTexture(log), blockModels));
        createModel(blockModels, createTrashBinBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_trash_bin")), TextureMapping.getBlockTexture(strippedLog), blockModels));

        for (WoodType type2 : WOOD_TYPES) {
            Block planks2 = getBlockFromResourceLocation(ResourceLocation.parse(type2.name()+"_planks"));
            Block log2;
            Block strippedLog2;
            String log_suffix2;
            if (type2 == WoodType.CRIMSON || type2 == WoodType.WARPED) {
                log2 = getBlockFromResourceLocation(ResourceLocation.parse(type2.name()+"_stem"));
                strippedLog2 = getBlockFromResourceLocation(ResourceLocation.parse("stripped_"+type2.name()+"_stem"));
                log_suffix2 = "_stem";
            } else if (type2 == WoodType.BAMBOO) {
                log2 = getBlockFromResourceLocation(ResourceLocation.parse(type2.name()+"_block"));
                strippedLog2 = getBlockFromResourceLocation(ResourceLocation.parse("stripped_"+type2.name()+"_block"));
                log_suffix2 = "_block";
            } else {
                log2 = getBlockFromResourceLocation(ResourceLocation.parse(type2.name()+"_log"));
                strippedLog2 = getBlockFromResourceLocation(ResourceLocation.parse("stripped_"+type2.name()+"_log"));
                log_suffix2 = "_log";
            }

            createModel(blockModels, createBirdhouseBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+type2.name()+"_birdhouse")), TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(log, "_top"), TextureMapping.getBlockTexture(planks), TextureMapping.getBlockTexture(planks2), blockModels));
            createModel(blockModels, createBirdhouseBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+type2.name()+"_birdhouse")), TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(log, "_top"), TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(planks2), blockModels));
            createModel(blockModels, createBirdhouseBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+type2.name()+"_birdhouse")), TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(log, "_top"), TextureMapping.getBlockTexture(strippedLog), TextureMapping.getBlockTexture(planks2), blockModels));
            createModel(blockModels, createBirdhouseBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+type2.name()+log_suffix2+"_birdhouse")), TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(log, "_top"), TextureMapping.getBlockTexture(planks), TextureMapping.getBlockTexture(log2), blockModels));
            createModel(blockModels, createBirdhouseBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+type2.name()+log_suffix2+"_birdhouse")), TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(log, "_top"), TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(log2), blockModels));
            createModel(blockModels, createBirdhouseBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+type2.name()+log_suffix2+"_birdhouse")), TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(log, "_top"), TextureMapping.getBlockTexture(strippedLog), TextureMapping.getBlockTexture(log2), blockModels));
            createModel(blockModels, createBirdhouseBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_stripped_"+type2.name()+log_suffix2+"_birdhouse")), TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(log, "_top"), TextureMapping.getBlockTexture(planks), TextureMapping.getBlockTexture(strippedLog2), blockModels));
            createModel(blockModels, createBirdhouseBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_stripped_"+type2.name()+log_suffix2+"_birdhouse")), TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(log, "_top"), TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(strippedLog2), blockModels));
            createModel(blockModels, createBirdhouseBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_stripped_"+type2.name()+log_suffix2+"_birdhouse")), TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(log, "_top"), TextureMapping.getBlockTexture(strippedLog), TextureMapping.getBlockTexture(strippedLog2), blockModels));

            createModel(blockModels, createHangingBirdhouseBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("hanging_"+type.name()+"_"+type2.name()+"_birdhouse")), TextureMapping.getBlockTexture(planks), TextureMapping.getBlockTexture(planks2), blockModels));
            createModel(blockModels, createHangingBirdhouseBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("hanging_"+type.name()+log_suffix+"_"+type2.name()+"_birdhouse")), TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(planks2), blockModels));
            createModel(blockModels, createHangingBirdhouseBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("hanging_stripped_"+type.name()+log_suffix+"_"+type2.name()+"_birdhouse")), TextureMapping.getBlockTexture(strippedLog), TextureMapping.getBlockTexture(planks2), blockModels));
            createModel(blockModels, createHangingBirdhouseBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("hanging_"+type.name()+"_"+type2.name()+log_suffix2+"_birdhouse")), TextureMapping.getBlockTexture(planks), TextureMapping.getBlockTexture(log2), blockModels));
            createModel(blockModels, createHangingBirdhouseBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("hanging_"+type.name()+log_suffix+"_"+type2.name()+log_suffix2+"_birdhouse")), TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(log2), blockModels));
            createModel(blockModels, createHangingBirdhouseBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("hanging_stripped_"+type.name()+log_suffix+"_"+type2.name()+log_suffix2+"_birdhouse")), TextureMapping.getBlockTexture(strippedLog), TextureMapping.getBlockTexture(log2), blockModels));
            createModel(blockModels, createHangingBirdhouseBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("hanging_"+type.name()+"_stripped_"+type2.name()+log_suffix2+"_birdhouse")), TextureMapping.getBlockTexture(planks), TextureMapping.getBlockTexture(strippedLog2), blockModels));
            createModel(blockModels, createHangingBirdhouseBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("hanging_"+type.name()+log_suffix+"_stripped_"+type2.name()+log_suffix2+"_birdhouse")), TextureMapping.getBlockTexture(log), TextureMapping.getBlockTexture(strippedLog2), blockModels));
            createModel(blockModels, createHangingBirdhouseBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("hanging_stripped_"+type.name()+log_suffix+"_stripped_"+type2.name()+log_suffix2+"_birdhouse")), TextureMapping.getBlockTexture(strippedLog), TextureMapping.getBlockTexture(strippedLog2), blockModels));
        }

        METALS.forEach((metal, name) -> {
            if (metal != Blocks.COPPER_BLOCK) {
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + "_" + name + "_bench")), planks, TextureMapping.getBlockTexture(metal), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + log_suffix + "_" + name + "_bench")), log, TextureMapping.getBlockTexture(metal), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_" + type.name() + log_suffix + "_" + name + "_bench")), strippedLog, TextureMapping.getBlockTexture(metal), blockModels));
            } else {
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + "_waxed_" + name + "_bench")), planks, TextureMapping.getBlockTexture(metal), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + log_suffix + "_waxed_" + name + "_bench")), log, TextureMapping.getBlockTexture(metal), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_" + type.name() + log_suffix + "_waxed_" + name + "_bench")), strippedLog, TextureMapping.getBlockTexture(metal), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + "_waxed_exposed_" + name + "_bench")), planks, TextureMapping.getBlockTexture(EXPOSED_COPPER), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + log_suffix + "_waxed_exposed_" + name + "_bench")), log, TextureMapping.getBlockTexture(EXPOSED_COPPER), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_" + type.name() + log_suffix + "_waxed_exposed_" + name + "_bench")), strippedLog, TextureMapping.getBlockTexture(EXPOSED_COPPER), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + "_waxed_weathered_" + name + "_bench")), planks, TextureMapping.getBlockTexture(WEATHERED_COPPER), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + log_suffix + "_waxed_weathered_" + name + "_bench")), log, TextureMapping.getBlockTexture(WEATHERED_COPPER), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_" + type.name() + log_suffix + "_waxed_weathered_" + name + "_bench")), strippedLog, TextureMapping.getBlockTexture(WEATHERED_COPPER), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + "_waxed_oxidized_" + name + "_bench")), planks, TextureMapping.getBlockTexture(OXIDIZED_COPPER), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + log_suffix + "_waxed_oxidized_" + name + "_bench")), log, TextureMapping.getBlockTexture(OXIDIZED_COPPER), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_" + type.name() + log_suffix + "_waxed_oxidized_" + name + "_bench")), strippedLog, TextureMapping.getBlockTexture(OXIDIZED_COPPER), blockModels));

                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + "_" +  name + "_bench")), planks, TextureMapping.getBlockTexture(metal), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + log_suffix + "_" + name + "_bench")), log, TextureMapping.getBlockTexture(metal), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_" + type.name() + log_suffix + "_" + name + "_bench")), strippedLog, TextureMapping.getBlockTexture(metal), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + "_exposed_" + name + "_bench")), planks, TextureMapping.getBlockTexture(EXPOSED_COPPER), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + log_suffix + "_exposed_" + name + "_bench")), log, TextureMapping.getBlockTexture(EXPOSED_COPPER), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_" + type.name() + log_suffix + "_exposed_" + name + "_bench")), strippedLog, TextureMapping.getBlockTexture(EXPOSED_COPPER), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + "_weathered_" + name + "_bench")), planks, TextureMapping.getBlockTexture(WEATHERED_COPPER), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + log_suffix + "_weathered_" + name + "_bench")), log, TextureMapping.getBlockTexture(WEATHERED_COPPER), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_" + type.name() + log_suffix + "_weathered_" + name + "_bench")), strippedLog, TextureMapping.getBlockTexture(WEATHERED_COPPER), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + "_oxidized_" + name + "_bench")), planks, TextureMapping.getBlockTexture(OXIDIZED_COPPER), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + log_suffix + "_oxidized_" + name + "_bench")), log, TextureMapping.getBlockTexture(OXIDIZED_COPPER), blockModels));
                createModel(blockModels, createBenchBlock(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_" + type.name() + log_suffix + "_oxidized_" + name + "_bench")), strippedLog, TextureMapping.getBlockTexture(OXIDIZED_COPPER), blockModels));
            }
        });

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
        MultiVariant variant = BlockModelGenerators.plainVariant(ModelTemplates.CRATE.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.LOG, TextureMapping.getBlockTexture(log)), blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block, variant.with(Y_ROT.withValue(Quadrant.R90)));
    }

    private MultiVariantGenerator createKitchenTiles(Block block, ResourceLocation tile_base, ResourceLocation tile_2, BlockModelGenerators blockModels) {
        return BlockModelGenerators.createSimpleBlock(block, BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_TILES.create(block, new TextureMapping().put(TextureSlot.PARTICLE, tile_base).put(TextureSlots.TILE_BASE, tile_base).put(TextureSlots.TILE_2, tile_2), blockModels.modelOutput)));
    }

    private MultiVariantGenerator createKitchenCounter(Block block, Block counter, ResourceLocation top, BlockModelGenerators blockModels) {
        MultiVariant straight = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top), blockModels.modelOutput));
        MultiVariant innerCorner = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER_INNER_CORNER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top), blockModels.modelOutput));
        MultiVariant outerCorner = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER_OUTER_CORNER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top), blockModels.modelOutput));

        return MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING, KitchenCounterBlock.SHAPE)
                .select(Direction.EAST, CounterShape.STRAIGHT, straight.with(Y_ROT.withValue(Quadrant.R180)).with(UV_LOCK.withValue(true)))
                .select(Direction.WEST, CounterShape.STRAIGHT, straight)
                .select(Direction.SOUTH, CounterShape.STRAIGHT, straight.with(Y_ROT.withValue(Quadrant.R270)).with(UV_LOCK.withValue(true)))
                .select(Direction.NORTH, CounterShape.STRAIGHT, straight.with(Y_ROT.withValue(Quadrant.R90)).with(UV_LOCK.withValue(true)))
                .select(Direction.EAST, CounterShape.OUTER_RIGHT, outerCorner.with(Y_ROT.withValue(Quadrant.R180)).with(UV_LOCK.withValue(true)))
                .select(Direction.WEST, CounterShape.OUTER_RIGHT, outerCorner)
                .select(Direction.SOUTH, CounterShape.OUTER_RIGHT, outerCorner.with(Y_ROT.withValue(Quadrant.R270)).with(UV_LOCK.withValue(true)))
                .select(Direction.NORTH, CounterShape.OUTER_RIGHT, outerCorner.with(Y_ROT.withValue(Quadrant.R90)).with(UV_LOCK.withValue(true)))
                .select(Direction.EAST, CounterShape.OUTER_LEFT, outerCorner.with(Y_ROT.withValue(Quadrant.R90)).with(UV_LOCK.withValue(true)))
                .select(Direction.WEST, CounterShape.OUTER_LEFT, outerCorner.with(Y_ROT.withValue(Quadrant.R270)).with(UV_LOCK.withValue(true)))
                .select(Direction.SOUTH, CounterShape.OUTER_LEFT, outerCorner.with(Y_ROT.withValue(Quadrant.R180)).with(UV_LOCK.withValue(true)))
                .select(Direction.NORTH, CounterShape.OUTER_LEFT, outerCorner)
                .select(Direction.EAST, CounterShape.INNER_RIGHT, innerCorner.with(Y_ROT.withValue(Quadrant.R180)).with(UV_LOCK.withValue(true)))
                .select(Direction.WEST, CounterShape.INNER_RIGHT, innerCorner)
                .select(Direction.SOUTH, CounterShape.INNER_RIGHT, innerCorner.with(Y_ROT.withValue(Quadrant.R270)).with(UV_LOCK.withValue(true)))
                .select(Direction.NORTH, CounterShape.INNER_RIGHT, innerCorner.with(Y_ROT.withValue(Quadrant.R90)).with(UV_LOCK.withValue(true)))
                .select(Direction.EAST, CounterShape.INNER_LEFT, innerCorner.with(Y_ROT.withValue(Quadrant.R90)).with(UV_LOCK.withValue(true)))
                .select(Direction.WEST, CounterShape.INNER_LEFT, innerCorner.with(Y_ROT.withValue(Quadrant.R270)).with(UV_LOCK.withValue(true)))
                .select(Direction.SOUTH, CounterShape.INNER_LEFT, innerCorner.with(Y_ROT.withValue(Quadrant.R180)).with(UV_LOCK.withValue(true)))
                .select(Direction.NORTH, CounterShape.INNER_LEFT, innerCorner));
    }

    private MultiVariantGenerator createKitchenCounterShelf(Block block, Block counter, ResourceLocation top, BlockModelGenerators blockModels) {
        MultiVariant variant = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER_SHELF.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top), blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block, variant).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createKitchenCounterDoor(Block block, Block counter, ResourceLocation top, ResourceLocation door, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door);
        MultiVariant closed = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER_DOOR.create(block, mapping, blockModels.modelOutput));
        MultiVariant open = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER_DOOR_OPEN.create(block, mapping, blockModels.modelOutput));
        MultiVariant closed_mirrored = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER_DOOR_MIRRORED.create(block, mapping, blockModels.modelOutput));
        MultiVariant open_mirrored = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER_DOOR_MIRRORED_OPEN.create(block, mapping, blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(KitchenCounterContainerBaseBlock.HINGE, KitchenCounterContainerBaseBlock.OPEN)
                        .select(DoorHingeSide.LEFT, false, closed_mirrored)
                        .select(DoorHingeSide.LEFT, true, open_mirrored)
                        .select(DoorHingeSide.RIGHT, false, closed)
                        .select(DoorHingeSide.RIGHT, true, open)
        ).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createKitchenCounterSmallDrawer(Block block, Block counter, ResourceLocation top, ResourceLocation door, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door);
        MultiVariant closed = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER_SMALL_DRAWER.create(block, mapping, blockModels.modelOutput));
        MultiVariant open = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER_SMALL_DRAWER_OPEN.create(block, mapping, blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(KitchenCounterContainerBaseBlock.OPEN)
                        .select(false, closed)
                        .select(true, open)
        ).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createKitchenCounterBigDrawer(Block block, Block counter, ResourceLocation top, ResourceLocation door, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door);
        MultiVariant closed = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER_BIG_DRAWER.create(block, mapping, blockModels.modelOutput));
        MultiVariant open = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER_BIG_DRAWER_OPEN.create(block, mapping, blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(KitchenCounterContainerBaseBlock.OPEN)
                        .select(false, closed)
                        .select(true, open)
        ).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createKitchenCounterSink(Block block, Block counter, ResourceLocation top, BlockModelGenerators blockModels) {
        TextureMapping baseMapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.FAUCET, TextureMapping.getBlockTexture(ANVIL));
        TextureMapping fillMapping = baseMapping.put(TextureSlots.WATER, TextureMapping.getBlockTexture(WATER).withSuffix("_still"));
        MultiVariant empty = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER_SINK_EMPTY.create(block, baseMapping, blockModels.modelOutput));
        MultiVariant empty_on = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER_SINK_EMPTY_ON.create(block, baseMapping.put(TextureSlots.STREAM, TextureMapping.getBlockTexture(WATER).withSuffix("_flow")), blockModels.modelOutput));
        MultiVariant level_1 = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER_SINK_LEVEL_1.create(block, fillMapping, blockModels.modelOutput));
        MultiVariant level_1_on = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER_SINK_LEVEL_1_ON.create(block, fillMapping.put(TextureSlots.STREAM, TextureMapping.getBlockTexture(WATER).withSuffix("_flow")), blockModels.modelOutput));
        MultiVariant level_2 = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER_SINK_LEVEL_2.create(block, fillMapping, blockModels.modelOutput));
        MultiVariant level_2_on = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER_SINK_LEVEL_2_ON.create(block, fillMapping.put(TextureSlots.STREAM, TextureMapping.getBlockTexture(WATER).withSuffix("_flow")), blockModels.modelOutput));
        MultiVariant level_3 = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER_SINK_LEVEL_3.create(block, fillMapping, blockModels.modelOutput));
        MultiVariant level_3_on = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER_SINK_LEVEL_3_ON.create(block, fillMapping.put(TextureSlots.STREAM, TextureMapping.getBlockTexture(WATER).withSuffix("_flow")), blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(KitchenCounterSinkBlock.ON, KitchenCounterSinkBlock.LEVEL)
                        .select(true, 0, empty_on)
                        .select(false, 0, empty)
                        .select(true, 1, level_1_on)
                        .select(false, 1, level_1)
                        .select(true, 2, level_2_on)
                        .select(false, 2, level_2)
                        .select(true, 3, level_3_on)
                        .select(false, 3, level_3)
        ).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createKitchenCounterSmoker(Block block, Block counter, ResourceLocation top, ResourceLocation furnace, ResourceLocation furnace_lit, BlockModelGenerators blockModels) {
        MultiVariant unlit = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER_SMOKER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.FURNACE, furnace), blockModels.modelOutput));
        MultiVariant lit = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_COUNTER_SMOKER_LIT.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.FURNACE, furnace_lit), blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(SmokerBlock.LIT)
                        .select(false, unlit)
                        .select(true, lit)
        ).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createKitchenCabinet(Block block, Block cabinet, BlockModelGenerators blockModels) {
        MultiVariant straight = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_CABINET.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)), blockModels.modelOutput));
        MultiVariant outerCorner = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_CABINET_INNER_CORNER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)), blockModels.modelOutput));
        MultiVariant innerCorner = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_CABINET_OUTER_CORNER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)), blockModels.modelOutput));

        return MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING, KitchenCabinetBlock.SHAPE)
                .select(Direction.EAST, CounterShape.STRAIGHT, straight.with(Y_ROT.withValue(Quadrant.R180)).with(UV_LOCK.withValue(true)))
                .select(Direction.WEST, CounterShape.STRAIGHT, straight)
                .select(Direction.SOUTH, CounterShape.STRAIGHT, straight.with(Y_ROT.withValue(Quadrant.R270)).with(UV_LOCK.withValue(true)))
                .select(Direction.NORTH, CounterShape.STRAIGHT, straight.with(Y_ROT.withValue(Quadrant.R90)).with(UV_LOCK.withValue(true)))
                .select(Direction.EAST, CounterShape.OUTER_RIGHT, outerCorner.with(Y_ROT.withValue(Quadrant.R180)).with(UV_LOCK.withValue(true)))
                .select(Direction.WEST, CounterShape.OUTER_RIGHT, outerCorner)
                .select(Direction.SOUTH, CounterShape.OUTER_RIGHT, outerCorner.with(Y_ROT.withValue(Quadrant.R270)).with(UV_LOCK.withValue(true)))
                .select(Direction.NORTH, CounterShape.OUTER_RIGHT, outerCorner.with(Y_ROT.withValue(Quadrant.R90)).with(UV_LOCK.withValue(true)))
                .select(Direction.EAST, CounterShape.OUTER_LEFT, outerCorner.with(Y_ROT.withValue(Quadrant.R90)).with(UV_LOCK.withValue(true)))
                .select(Direction.WEST, CounterShape.OUTER_LEFT, outerCorner.with(Y_ROT.withValue(Quadrant.R270)).with(UV_LOCK.withValue(true)))
                .select(Direction.SOUTH, CounterShape.OUTER_LEFT, outerCorner.with(Y_ROT.withValue(Quadrant.R180)).with(UV_LOCK.withValue(true)))
                .select(Direction.NORTH, CounterShape.OUTER_LEFT, outerCorner)
                .select(Direction.EAST, CounterShape.INNER_RIGHT, innerCorner.with(Y_ROT.withValue(Quadrant.R180)).with(UV_LOCK.withValue(true)))
                .select(Direction.WEST, CounterShape.INNER_RIGHT, innerCorner)
                .select(Direction.SOUTH, CounterShape.INNER_RIGHT, innerCorner.with(Y_ROT.withValue(Quadrant.R270)).with(UV_LOCK.withValue(true)))
                .select(Direction.NORTH, CounterShape.INNER_RIGHT, innerCorner.with(Y_ROT.withValue(Quadrant.R90)).with(UV_LOCK.withValue(true)))
                .select(Direction.EAST, CounterShape.INNER_LEFT, innerCorner.with(Y_ROT.withValue(Quadrant.R90)).with(UV_LOCK.withValue(true)))
                .select(Direction.WEST, CounterShape.INNER_LEFT, innerCorner.with(Y_ROT.withValue(Quadrant.R270)).with(UV_LOCK.withValue(true)))
                .select(Direction.SOUTH, CounterShape.INNER_LEFT, innerCorner.with(Y_ROT.withValue(Quadrant.R180)).with(UV_LOCK.withValue(true)))
                .select(Direction.NORTH, CounterShape.INNER_LEFT, innerCorner));
    }

    private MultiVariantGenerator createKitchenCabinetShelf(Block block, Block cabinet, BlockModelGenerators blockModels) {
        MultiVariant model = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_CABINET_SHELF.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)), blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block, model).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createKitchenCabinetDoor(Block block, Block cabinet, ResourceLocation door, ResourceLocation handle, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle);
        MultiVariant closed = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_CABINET_DOOR.create(block, mapping, blockModels.modelOutput));
        MultiVariant open = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_CABINET_DOOR_OPEN.create(block, mapping, blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(KitchenCounterContainerBaseBlock.OPEN)
                        .select(false, closed)
                        .select(true, open)
        ).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createKitchenCabinetGlassDoor(Block block, Block cabinet, ResourceLocation door, ResourceLocation handle, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle).put(TextureSlots.GLASS, TextureMapping.getBlockTexture(GLASS));
        MultiVariant closed = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_CABINET_GLASS_DOOR.create(block, mapping, blockModels.modelOutput));
        MultiVariant open = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_CABINET_GLASS_DOOR_OPEN.create(block, mapping, blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(KitchenCounterContainerBaseBlock.OPEN)
                        .select(false, closed)
                        .select(true, open)
        ).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createKitchenCabinetSidewaysDoor(Block block, Block cabinet, ResourceLocation door, ResourceLocation handle, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle);
        MultiVariant closed = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_CABINET_DOOR_SIDEWAYS.create(block, mapping, blockModels.modelOutput));
        MultiVariant open = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_CABINET_DOOR_SIDEWAYS_OPEN.create(block, mapping, blockModels.modelOutput));
        MultiVariant closed_mirrored = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_CABINET_DOOR_SIDEWAYS_MIRRORED.create(block, mapping, blockModels.modelOutput));
        MultiVariant open_mirrored = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_CABINET_DOOR_SIDEWAYS_MIRRORED_OPEN.create(block, mapping, blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(KitchenCounterContainerBaseBlock.HINGE, KitchenCounterContainerBaseBlock.OPEN)
                        .select(DoorHingeSide.LEFT, false, closed_mirrored)
                        .select(DoorHingeSide.LEFT, true, open_mirrored)
                        .select(DoorHingeSide.RIGHT, false, closed)
                        .select(DoorHingeSide.RIGHT, true, open)
        ).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createKitchenCabinetSidewaysGlassDoor(Block block, Block cabinet, ResourceLocation door, ResourceLocation handle, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.CABINET, TextureMapping.getBlockTexture(cabinet)).put(TextureSlots.DOOR, door).put(TextureSlots.HANDLE, handle).put(TextureSlots.GLASS, TextureMapping.getBlockTexture(GLASS));
        MultiVariant closed = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_CABINET_GLASS_DOOR_SIDEWAYS.create(block, mapping, blockModels.modelOutput));
        MultiVariant open = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_CABINET_GLASS_DOOR_SIDEWAYS_OPEN.create(block, mapping, blockModels.modelOutput));
        MultiVariant closed_mirrored = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_CABINET_GLASS_DOOR_SIDEWAYS_MIRRORED.create(block, mapping, blockModels.modelOutput));
        MultiVariant open_mirrored = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_CABINET_GLASS_DOOR_SIDEWAYS_MIRRORED_OPEN.create(block, mapping, blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(KitchenCounterContainerBaseBlock.HINGE, KitchenCounterContainerBaseBlock.OPEN)
                        .select(DoorHingeSide.LEFT, false, closed_mirrored)
                        .select(DoorHingeSide.LEFT, true, open_mirrored)
                        .select(DoorHingeSide.RIGHT, false, closed)
                        .select(DoorHingeSide.RIGHT, true, open)
        ).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createKitchenShelf(Block block, Block planks, ResourceLocation chain, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.CHAIN, chain);
        MultiVariant single = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_SHELF_SINGLE.create(block, mapping, blockModels.modelOutput));
        MultiVariant left = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_SHELF_LEFT.create(block, mapping, blockModels.modelOutput));
        MultiVariant right = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_SHELF_RIGHT.create(block, mapping, blockModels.modelOutput));
        MultiVariant middle = BlockModelGenerators.plainVariant(ModelTemplates.KITCHEN_SHELF_MIDDLE.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)), blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(KitchenShelfBlock.SHAPE)
                        .select(ShelfShape.SINGLE, single)
                        .select(ShelfShape.LEFT, left)
                        .select(ShelfShape.RIGHT, right)
                        .select(ShelfShape.MIDDLE, middle)
        ).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createFridge(Block block, ResourceLocation fridge, ResourceLocation bars, ResourceLocation handle, BlockModelGenerators blockModels) {
        TextureMapping mapping_bottom = new TextureMapping().put(TextureSlot.PARTICLE, fridge).put(TextureSlots.FRIDGE, fridge).put(TextureSlots.BARS, bars).put(TextureSlots.HANDLE, handle);
        TextureMapping mapping_top = new TextureMapping().put(TextureSlot.PARTICLE, fridge).put(TextureSlots.FRIDGE, fridge).put(TextureSlots.HANDLE, handle);
        MultiVariant bottom = BlockModelGenerators.plainVariant(ModelTemplates.FRIDGE_BOTTOM.create(block, mapping_bottom, blockModels.modelOutput));
        MultiVariant bottom_open = BlockModelGenerators.plainVariant(ModelTemplates.FRIDGE_BOTTOM_OPEN.create(block, mapping_bottom, blockModels.modelOutput));
        MultiVariant bottom_mirrored = BlockModelGenerators.plainVariant(ModelTemplates.FRIDGE_BOTTOM_MIRRORED.create(block, mapping_bottom, blockModels.modelOutput));
        MultiVariant bottom_mirrored_open = BlockModelGenerators.plainVariant(ModelTemplates.FRIDGE_BOTTOM_MIRRORED_OPEN.create(block, mapping_bottom, blockModels.modelOutput));
        MultiVariant top = BlockModelGenerators.plainVariant(ModelTemplates.FRIDGE_TOP.create(block, mapping_top, blockModels.modelOutput));
        MultiVariant top_open = BlockModelGenerators.plainVariant(ModelTemplates.FRIDGE_TOP_OPEN.create(block, mapping_top, blockModels.modelOutput));
        MultiVariant top_mirrored = BlockModelGenerators.plainVariant(ModelTemplates.FRIDGE_TOP_MIRRORED.create(block, mapping_top, blockModels.modelOutput));
        MultiVariant top_mirrored_open = BlockModelGenerators.plainVariant(ModelTemplates.FRIDGE_TOP_MIRRORED_OPEN.create(block, mapping_top, blockModels.modelOutput));
        ResourceLocation item = ModelTemplates.FRIDGE_INVENTORY.create(block, mapping_top, blockModels.modelOutput);
        blockModels.registerSimpleItemModel(block, item);
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(FridgeBlock.HALF, FridgeBlock.HINGE, FridgeBlock.OPEN)
                        .select(DoubleBlockHalf.UPPER, DoorHingeSide.LEFT, false, top_mirrored)
                        .select(DoubleBlockHalf.UPPER, DoorHingeSide.LEFT, true, top_mirrored_open)
                        .select(DoubleBlockHalf.UPPER, DoorHingeSide.RIGHT, false, top)
                        .select(DoubleBlockHalf.UPPER, DoorHingeSide.RIGHT, true, top_open)
                        .select(DoubleBlockHalf.LOWER, DoorHingeSide.LEFT, false, bottom_mirrored)
                        .select(DoubleBlockHalf.LOWER, DoorHingeSide.LEFT, true, bottom_mirrored_open)
                        .select(DoubleBlockHalf.LOWER, DoorHingeSide.RIGHT, false, bottom)
                        .select(DoubleBlockHalf.LOWER, DoorHingeSide.RIGHT, true, bottom_open)
        ).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createKnifeBlock(Block block, Block blockTexture, ResourceLocation stand, BlockModelGenerators blockModels) {
        MultiVariant model = BlockModelGenerators.plainVariant(ModelTemplates.KNIFE_BLOCK.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(blockTexture)).put(TextureSlots.BLOCK, TextureMapping.getBlockTexture(blockTexture)).put(TextureSlots.STAND, stand).put(TextureSlots.HANDLE, TextureMapping.getBlockTexture(ANVIL)).put(TextureSlots.KNIFE, TextureMapping.getBlockTexture(IRON_BLOCK)), blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block, model).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createTablewareBlock(Block block, ResourceLocation tableware, ResourceLocation liquid, BlockModelGenerators blockModels) {
        MultiVariant model = BlockModelGenerators.plainVariant(ModelTemplates.TABLEWARE.create(block, new TextureMapping().put(TextureSlot.PARTICLE, tableware).put(TextureSlots.TABLEWARE, tableware).put(TextureSlots.LIQUID, liquid), blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block, model).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createToasterBlock(Block block, Block blockTexture, ResourceLocation bottom, BlockModelGenerators blockModels) {
        MultiVariant model = BlockModelGenerators.plainVariant(ModelTemplates.TOASTER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(blockTexture)).put(TextureSlots.TOASTER, TextureMapping.getBlockTexture(blockTexture)).put(TextureSlot.BOTTOM, bottom), blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block, model).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createChairBlock(Block block, Block chair, ResourceLocation decoration, ResourceLocation wool, BlockModelGenerators blockModels) {
        MultiVariant model = BlockModelGenerators.plainVariant(ModelTemplates.CHAIR.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(chair)).put(TextureSlots.CHAIR, TextureMapping.getBlockTexture(chair)).put(TextureSlots.DECORATION, decoration).put(TextureSlots.WOOL, wool), blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block, model).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createTableBlock(Block block, Block planks, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks));
        MultiVariant single = BlockModelGenerators.plainVariant(ModelTemplates.TABLE_SINGLE.create(block, mapping, blockModels.modelOutput));
        MultiVariant middle = BlockModelGenerators.plainVariant(ModelTemplates.TABLE_MIDDLE.create(block, mapping, blockModels.modelOutput));
        MultiVariant north = BlockModelGenerators.plainVariant(ModelTemplates.TABLE_NORTH.create(block, mapping, blockModels.modelOutput));
        MultiVariant east = BlockModelGenerators.plainVariant(ModelTemplates.TABLE_EAST.create(block, mapping, blockModels.modelOutput));
        MultiVariant south = BlockModelGenerators.plainVariant(ModelTemplates.TABLE_SOUTH.create(block, mapping, blockModels.modelOutput));
        MultiVariant west = BlockModelGenerators.plainVariant(ModelTemplates.TABLE_WEST.create(block, mapping, blockModels.modelOutput));
        MultiVariant north_east = BlockModelGenerators.plainVariant(ModelTemplates.TABLE_NORTH_EAST.create(block, mapping, blockModels.modelOutput));
        MultiVariant north_west = BlockModelGenerators.plainVariant(ModelTemplates.TABLE_NORTH_WEST.create(block, mapping, blockModels.modelOutput));
        MultiVariant south_east = BlockModelGenerators.plainVariant(ModelTemplates.TABLE_SOUTH_EAST.create(block, mapping, blockModels.modelOutput));
        MultiVariant south_west = BlockModelGenerators.plainVariant(ModelTemplates.TABLE_SOUTH_WEST.create(block, mapping, blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(TableBlock.NORTH, TableBlock.EAST, TableBlock.SOUTH, TableBlock.WEST)
                        .select(false, false, false, false, single)
                        .select(true, true, true, true, middle)
                        .select(true, false, true, false, middle)
                        .select(false, true, false, true, middle)
                        .select(true, false, false, false, south)
                        .select(false, true, false, false, west)
                        .select(false, false, true, false, north)
                        .select(false, false, false, true, east)
                        .select(true, true, false, false, south_west)
                        .select(false, true, true, false, north_west)
                        .select(false, false, true, true, north_east)
                        .select(true, false, false, true, south_east)
                        .select(true, true, true, false, middle)
                        .select(false, true, true, true, middle)
                        .select(true, false, true, true, middle)
                        .select(true, true, false, true, middle)
        );
    }

    private MultiVariantGenerator createCurtainBlock(Block block, ResourceLocation wool, ResourceLocation curtain_rod, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, wool).put(TextureSlots.WOOL, wool).put(TextureSlots.CURTAIN_ROD, curtain_rod);
        MultiVariant small_closed = BlockModelGenerators.plainVariant(ModelTemplates.CURTAIN_SMALL_CLOSED.create(block, mapping, blockModels.modelOutput));
        MultiVariant small_single_open = BlockModelGenerators.plainVariant(ModelTemplates.CURTAIN_SMALL_SINGLE_OPEN.create(block, mapping, blockModels.modelOutput));
        MultiVariant small_left_open = BlockModelGenerators.plainVariant(ModelTemplates.CURTAIN_SMALL_LEFT_OPEN.create(block, mapping, blockModels.modelOutput));
        MultiVariant small_right_open = BlockModelGenerators.plainVariant(ModelTemplates.CURTAIN_SMALL_RIGHT_OPEN.create(block, mapping, blockModels.modelOutput));
        MultiVariant big_top_middle_open = BlockModelGenerators.plainVariant(ModelTemplates.CURTAIN_BIG_TOP_MIDDLE_OPEN.create(block, mapping, blockModels.modelOutput));
        MultiVariant big_top_closed = BlockModelGenerators.plainVariant(ModelTemplates.CURTAIN_BIG_TOP_CLOSED.create(block, mapping, blockModels.modelOutput));
        MultiVariant big_bottom_closed = BlockModelGenerators.plainVariant(ModelTemplates.CURTAIN_BIG_BOTTOM_CLOSED.create(block, mapping, blockModels.modelOutput));
        MultiVariant big_top_single_open = BlockModelGenerators.plainVariant(ModelTemplates.CURTAIN_BIG_TOP_SINGLE_OPEN.create(block, mapping, blockModels.modelOutput));
        MultiVariant big_top_right_open = BlockModelGenerators.plainVariant(ModelTemplates.CURTAIN_BIG_TOP_RIGHT_OPEN.create(block, mapping, blockModels.modelOutput));
        MultiVariant big_top_left_open = BlockModelGenerators.plainVariant(ModelTemplates.CURTAIN_BIG_TOP_LEFT_OPEN.create(block, mapping, blockModels.modelOutput));
        MultiVariant big_bottom_single_open = BlockModelGenerators.plainVariant(ModelTemplates.CURTAIN_BIG_BOTTOM_SINGLE_OPEN.create(block, mapping, blockModels.modelOutput));
        MultiVariant big_bottom_right_open = BlockModelGenerators.plainVariant(ModelTemplates.CURTAIN_BIG_BOTTOM_RIGHT_OPEN.create(block, mapping, blockModels.modelOutput));
        MultiVariant big_bottom_left_open = BlockModelGenerators.plainVariant(ModelTemplates.CURTAIN_BIG_BOTTOM_LEFT_OPEN.create(block, mapping, blockModels.modelOutput));
        MultiVariant big_bottom_middle_open = BlockModelGenerators.plainVariant(ModelTemplates.CURTAIN_BIG_BOTTOM_MIDDLE_OPEN.create(block, mapping, blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(CurtainBlock.CURTAIN_SHAPE, CurtainBlock.LEFT, CurtainBlock.RIGHT, CurtainBlock.OPEN)
                        .select(CurtainShape.SINGLE, false, false, false, small_closed)
                        .select(CurtainShape.SINGLE, true, false, false, small_closed)
                        .select(CurtainShape.SINGLE, false, true, false, small_closed)
                        .select(CurtainShape.SINGLE, true, true, false, small_closed)
                        .select(CurtainShape.SINGLE, false, false, true, small_single_open)
                        .select(CurtainShape.SINGLE, false, true, true, small_right_open)
                        .select(CurtainShape.SINGLE, true, false, true, small_left_open)
                        .select(CurtainShape.SINGLE, true, true, true, big_top_middle_open)

                        .select(CurtainShape.TOP, false, false, false, big_top_closed)
                        .select(CurtainShape.TOP, true, false, false, big_top_closed)
                        .select(CurtainShape.TOP, false, true, false, big_top_closed)
                        .select(CurtainShape.TOP, true, true, false, big_top_closed)
                        .select(CurtainShape.TOP, false, false, true, big_top_single_open)
                        .select(CurtainShape.TOP, true, false, true, big_top_left_open)
                        .select(CurtainShape.TOP, false, true, true, big_top_right_open)
                        .select(CurtainShape.TOP, true, true, true, big_top_middle_open)

                        .select(CurtainShape.BOTTOM, false, false, false, big_bottom_closed)
                        .select(CurtainShape.BOTTOM, true, false, false, big_bottom_closed)
                        .select(CurtainShape.BOTTOM, false, true, false, big_bottom_closed)
                        .select(CurtainShape.BOTTOM, true, true, false, big_bottom_closed)
                        .select(CurtainShape.BOTTOM, false, false, true, big_bottom_single_open)
                        .select(CurtainShape.BOTTOM, true, false, true, big_bottom_left_open)
                        .select(CurtainShape.BOTTOM, false, true, true, big_bottom_right_open)
                        .select(CurtainShape.BOTTOM, true, true, true, big_bottom_middle_open)
        ).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createBedsideTableBlock(Block block, Block counter, ResourceLocation top, ResourceLocation door, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(counter)).put(TextureSlots.COUNTER, TextureMapping.getBlockTexture(counter)).put(TextureSlots.TOP, top).put(TextureSlots.DOOR, door);
        MultiVariant closed = BlockModelGenerators.plainVariant(ModelTemplates.BEDSIDE_TABLE.create(block, mapping, blockModels.modelOutput));
        MultiVariant open = BlockModelGenerators.plainVariant(ModelTemplates.BEDSIDE_TABLE_OPEN.create(block, mapping, blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(BedsideTableBlock.OPEN)
                        .select(false, closed)
                        .select(true, open)
        ).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createCloset(Block block, ResourceLocation closet, ResourceLocation door, BlockModelGenerators blockModels) {
        TextureMapping mapping_bottom = new TextureMapping().put(TextureSlot.PARTICLE, closet).put(TextureSlots.CLOSET, closet).put(TextureSlots.DOOR, door);
        TextureMapping mapping_top = new TextureMapping().put(TextureSlot.PARTICLE, closet).put(TextureSlots.CLOSET, closet).put(TextureSlots.DOOR, door);
        MultiVariant bottom = BlockModelGenerators.plainVariant(ModelTemplates.CLOSET_BOTTOM.create(block, mapping_bottom, blockModels.modelOutput));
        MultiVariant bottom_open = BlockModelGenerators.plainVariant(ModelTemplates.CLOSET_BOTTOM_OPEN.create(block, mapping_bottom, blockModels.modelOutput));
        MultiVariant top = BlockModelGenerators.plainVariant(ModelTemplates.CLOSET_TOP.create(block, mapping_top, blockModels.modelOutput));
        MultiVariant top_open = BlockModelGenerators.plainVariant(ModelTemplates.CLOSET_TOP_OPEN.create(block, mapping_top, blockModels.modelOutput));
        ResourceLocation item = ModelTemplates.CLOSET_INVENTORY.create(block, mapping_top, blockModels.modelOutput);
        blockModels.registerSimpleItemModel(block, item);
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(ClosetBlock.HALF, ClosetBlock.OPEN)
                        .select(DoubleBlockHalf.UPPER, false, top)
                        .select(DoubleBlockHalf.UPPER, true, top_open)
                        .select(DoubleBlockHalf.LOWER, false, bottom)
                        .select(DoubleBlockHalf.LOWER, true, bottom_open)
        ).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createLampBlock(Block block, Block planks, ResourceLocation log, ResourceLocation log_top, ResourceLocation stripped_log, ResourceLocation lamp, ResourceLocation lamp_on, ResourceLocation wool, BlockModelGenerators blockModels) {
        MultiVariant model_single = BlockModelGenerators.plainVariant(ModelTemplates.LAMP_SINGLE.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.LOG, log).put(TextureSlots.LOG_TOP, log_top).put(TextureSlots.STRIPPED_LOG, stripped_log).put(TextureSlots.LAMP, lamp).put(TextureSlots.WOOL, wool), blockModels.modelOutput));
        MultiVariant model_single_on = BlockModelGenerators.plainVariant(ModelTemplates.LAMP_SINGLE_ON.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.LOG, log).put(TextureSlots.LOG_TOP, log_top).put(TextureSlots.STRIPPED_LOG, stripped_log).put(TextureSlots.LAMP, lamp_on).put(TextureSlots.WOOL, wool), blockModels.modelOutput));
        MultiVariant model_top = BlockModelGenerators.plainVariant(ModelTemplates.LAMP_TOP.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.LOG, log).put(TextureSlots.LAMP, lamp).put(TextureSlots.WOOL, wool), blockModels.modelOutput));
        MultiVariant model_top_on = BlockModelGenerators.plainVariant(ModelTemplates.LAMP_TOP_ON.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.LOG, log).put(TextureSlots.LAMP, lamp_on).put(TextureSlots.WOOL, wool), blockModels.modelOutput));
        MultiVariant model_middle = BlockModelGenerators.plainVariant(ModelTemplates.LAMP_MIDDLE.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.PLANKS, TextureMapping.getBlockTexture(planks)).put(TextureSlots.LOG, log), blockModels.modelOutput));
        MultiVariant model_bottom = BlockModelGenerators.plainVariant(ModelTemplates.LAMP_BOTTOM.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(planks)).put(TextureSlots.LOG, log).put(TextureSlots.LOG_TOP, log_top).put(TextureSlots.STRIPPED_LOG, stripped_log), blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(LampBlock.ON, LampBlock.PART)
                        .select(true, LampPart.SINGLE, model_single_on)
                        .select(false, LampPart.SINGLE, model_single)
                        .select(true, LampPart.TOP, model_top_on)
                        .select(false, LampPart.TOP, model_top)
                        .select(true, LampPart.MIDDLE, model_middle)
                        .select(false, LampPart.MIDDLE, model_middle)
                        .select(true, LampPart.BOTTOM, model_bottom)
                        .select(false, LampPart.BOTTOM, model_bottom)
        );
    }

    private MultiVariantGenerator createWoodenBedBlock(Block block, ResourceLocation wood, ResourceLocation wool, ResourceLocation decoration, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, wood).put(TextureSlots.WOOD, wood).put(TextureSlots.WOOL, wool).put(TextureSlots.DECORATION, decoration);
        MultiVariant head_single = BlockModelGenerators.plainVariant(ModelTemplates.WOODEN_BED_HEAD_SINGLE.create(block, mapping, blockModels.modelOutput));
        MultiVariant head_left = BlockModelGenerators.plainVariant(ModelTemplates.WOODEN_BED_HEAD_LEFT.create(block, mapping, blockModels.modelOutput));
        MultiVariant head_right = BlockModelGenerators.plainVariant(ModelTemplates.WOODEN_BED_HEAD_RIGHT.create(block, mapping, blockModels.modelOutput));
        MultiVariant head_middle = BlockModelGenerators.plainVariant(ModelTemplates.WOODEN_BED_HEAD_MIDDLE.create(block, mapping, blockModels.modelOutput));
        MultiVariant foot_single = BlockModelGenerators.plainVariant(ModelTemplates.WOODEN_BED_FOOT_SINGLE.create(block, mapping, blockModels.modelOutput));
        MultiVariant foot_left = BlockModelGenerators.plainVariant(ModelTemplates.WOODEN_BED_FOOT_LEFT.create(block, mapping, blockModels.modelOutput));
        MultiVariant foot_right = BlockModelGenerators.plainVariant(ModelTemplates.WOODEN_BED_FOOT_RIGHT.create(block, mapping, blockModels.modelOutput));
        MultiVariant foot_middle = BlockModelGenerators.plainVariant(ModelTemplates.WOODEN_BED_FOOT_MIDDLE.create(block, mapping, blockModels.modelOutput));
        ResourceLocation inventory = ModelTemplates.WOODEN_BED_INVENTORY.create(block, mapping, blockModels.modelOutput);
        blockModels.registerSimpleItemModel(block, inventory);
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(WoodenBedBlock.PART, WoodenBedBlock.SHAPE, WoodenBedBlock.OCCUPIED)
                        .select(BedPart.HEAD, BedShape.SINGLE, false, head_single)
                        .select(BedPart.HEAD, BedShape.SINGLE, true, head_single)
                        .select(BedPart.HEAD, BedShape.LEFT, false, head_left)
                        .select(BedPart.HEAD, BedShape.LEFT, true, head_left)
                        .select(BedPart.HEAD, BedShape.RIGHT, false, head_right)
                        .select(BedPart.HEAD, BedShape.RIGHT, true, head_right)
                        .select(BedPart.HEAD, BedShape.MIDDLE, false, head_middle)
                        .select(BedPart.HEAD, BedShape.MIDDLE, true, head_middle)
                        .select(BedPart.FOOT, BedShape.SINGLE, false, foot_single)
                        .select(BedPart.FOOT, BedShape.SINGLE, true, foot_single)
                        .select(BedPart.FOOT, BedShape.LEFT, false, foot_left)
                        .select(BedPart.FOOT, BedShape.LEFT, true, foot_left)
                        .select(BedPart.FOOT, BedShape.RIGHT, false, foot_right)
                        .select(BedPart.FOOT, BedShape.RIGHT, true, foot_right)
                        .select(BedPart.FOOT, BedShape.MIDDLE, false, foot_middle)
                        .select(BedPart.FOOT, BedShape.MIDDLE, true, foot_middle)
        ).with(ROTATION_HORIZONTAL_FACING_ALT);
    }

    private MultiVariantGenerator createBenchBlock(Block block, Block wood, ResourceLocation metal, BlockModelGenerators blockModels) {
        TextureMapping mapping = new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(wood)).put(TextureSlots.WOOD, TextureMapping.getBlockTexture(wood)).put(TextureSlots.METAL, metal);
        MultiVariant single = BlockModelGenerators.plainVariant(ModelTemplates.BENCH_SINGLE.create(block, mapping, blockModels.modelOutput));
        MultiVariant left = BlockModelGenerators.plainVariant(ModelTemplates.BENCH_LEFT.create(block, mapping, blockModels.modelOutput));
        MultiVariant right = BlockModelGenerators.plainVariant(ModelTemplates.BENCH_RIGHT.create(block, mapping, blockModels.modelOutput));
        MultiVariant middle = BlockModelGenerators.plainVariant(ModelTemplates.BENCH_MIDDLE.create(block, new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(wood)).put(TextureSlots.WOOD, TextureMapping.getBlockTexture(wood)), blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(BenchBlock.SHAPE)
                        .select(BenchShape.SINGLE, single)
                        .select(BenchShape.LEFT, left)
                        .select(BenchShape.RIGHT, right)
                        .select(BenchShape.MIDDLE, middle)
        ).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createFlowerBoxBlock(Block block, ResourceLocation wood, ResourceLocation wood_top, BlockModelGenerators blockModels) {
        MultiVariant top = BlockModelGenerators.plainVariant(ModelTemplates.FLOWER_BOX_TOP.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wood).put(TextureSlots.DIRT, TextureMapping.getBlockTexture(DIRT)).put(TextureSlots.WOOD, wood).put(TextureSlots.WOOD_TOP, wood_top), blockModels.modelOutput));
        MultiVariant bottom = BlockModelGenerators.plainVariant(ModelTemplates.FLOWER_BOX_BOTTOM.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wood).put(TextureSlots.DIRT, TextureMapping.getBlockTexture(DIRT)).put(TextureSlots.WOOD, wood).put(TextureSlots.WOOD_TOP, wood_top), blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(FlowerBoxBlock.HALF)
                        .select(Half.TOP, top)
                        .select(Half.BOTTOM, bottom)
        ).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createFlowerBoxInnerCornerBlock(Block block, ResourceLocation wood, ResourceLocation wood_top, BlockModelGenerators blockModels) {
        MultiVariant top = BlockModelGenerators.plainVariant(ModelTemplates.FLOWER_BOX_TOP_INNER_CORNER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wood).put(TextureSlots.DIRT, TextureMapping.getBlockTexture(DIRT)).put(TextureSlots.WOOD, wood).put(TextureSlots.WOOD_TOP, wood_top), blockModels.modelOutput));
        MultiVariant bottom = BlockModelGenerators.plainVariant(ModelTemplates.FLOWER_BOX_BOTTOM_INNER_CORNER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wood).put(TextureSlots.DIRT, TextureMapping.getBlockTexture(DIRT)).put(TextureSlots.WOOD, wood).put(TextureSlots.WOOD_TOP, wood_top), blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(FlowerBoxBlock.HALF)
                        .select(Half.TOP, top)
                        .select(Half.BOTTOM, bottom)
        ).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createFlowerBoxOuterCornerBlock(Block block, ResourceLocation wood, ResourceLocation wood_top, BlockModelGenerators blockModels) {
        MultiVariant top = BlockModelGenerators.plainVariant(ModelTemplates.FLOWER_BOX_TOP_OUTER_CORNER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wood).put(TextureSlots.DIRT, TextureMapping.getBlockTexture(DIRT)).put(TextureSlots.WOOD, wood).put(TextureSlots.WOOD_TOP, wood_top), blockModels.modelOutput));
        MultiVariant bottom = BlockModelGenerators.plainVariant(ModelTemplates.FLOWER_BOX_BOTTOM_OUTER_CORNER.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wood).put(TextureSlots.DIRT, TextureMapping.getBlockTexture(DIRT)).put(TextureSlots.WOOD, wood).put(TextureSlots.WOOD_TOP, wood_top), blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(FlowerBoxBlock.HALF)
                        .select(Half.TOP, top)
                        .select(Half.BOTTOM, bottom)
        ).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createFlowerBoxBigBlock(Block block, ResourceLocation wood, ResourceLocation wood_top, BlockModelGenerators blockModels) {
        MultiVariant top = BlockModelGenerators.plainVariant(ModelTemplates.FLOWER_BOX_TOP_BIG.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wood).put(TextureSlots.DIRT, TextureMapping.getBlockTexture(DIRT)).put(TextureSlots.WOOD, wood).put(TextureSlots.WOOD_TOP, wood_top), blockModels.modelOutput));
        MultiVariant bottom = BlockModelGenerators.plainVariant(ModelTemplates.FLOWER_BOX_BOTTOM_BIG.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wood).put(TextureSlots.DIRT, TextureMapping.getBlockTexture(DIRT)).put(TextureSlots.WOOD, wood).put(TextureSlots.WOOD_TOP, wood_top), blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(FlowerBoxBlock.HALF)
                        .select(Half.TOP, top)
                        .select(Half.BOTTOM, bottom)
        ).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createTrashBinBlock(Block block, ResourceLocation wood, BlockModelGenerators blockModels) {
        MultiVariant model = BlockModelGenerators.plainVariant(ModelTemplates.TRASH_BIN.create(block, new TextureMapping().put(TextureSlot.PARTICLE, wood).put(TextureSlots.METAL, TextureMapping.getBlockTexture(ANVIL)).put(TextureSlots.WOOD, wood), blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block, model);
    }

    private MultiVariantGenerator createBirdbathBlock(Block block, ResourceLocation stone, BlockModelGenerators blockModels) {
        MultiVariant model = BlockModelGenerators.plainVariant(ModelTemplates.BIRDBATH.create(block, new TextureMapping().put(TextureSlot.PARTICLE, stone).put(TextureSlots.STONE, stone).put(TextureSlots.WATER, TextureMapping.getBlockTexture(WATER).withSuffix("_still")), blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block, model);
    }

    private MultiVariantGenerator createBirdhouseBlock(Block block, ResourceLocation log, ResourceLocation log_top, ResourceLocation planks, ResourceLocation roof, BlockModelGenerators blockModels) {
        MultiVariant top = BlockModelGenerators.plainVariant(ModelTemplates.BIRDHOUSE_TOP.create(block, new TextureMapping().put(TextureSlot.PARTICLE, planks).put(TextureSlots.LOG, log).put(TextureSlots.PLANKS, planks).put(TextureSlots.ROOF, roof), blockModels.modelOutput));
        MultiVariant bottom = BlockModelGenerators.plainVariant(ModelTemplates.BIRDHOUSE_BOTTOM.create(block, new TextureMapping().put(TextureSlot.PARTICLE, log).put(TextureSlots.LOG, log).put(TextureSlots.LOG_TOP, log_top), blockModels.modelOutput));
        ResourceLocation inventory = ModelTemplates.BIRDHOUSE_INVENTORY.create(block, new TextureMapping().put(TextureSlots.LOG, log).put(TextureSlots.LOG_TOP, log_top).put(TextureSlots.PLANKS, planks).put(TextureSlots.ROOF, roof), blockModels.modelOutput);
        blockModels.registerSimpleItemModel(block, inventory);
        return MultiVariantGenerator.dispatch(block).with(
                PropertyDispatch.initial(BirdhouseBlock.HALF)
                        .select(DoubleBlockHalf.LOWER, bottom)
                        .select(DoubleBlockHalf.UPPER, top)
        ).with(ROTATION_HORIZONTAL_FACING);
    }

    private MultiVariantGenerator createHangingBirdhouseBlock(Block block, ResourceLocation planks, ResourceLocation roof, BlockModelGenerators blockModels) {
        MultiVariant model = BlockModelGenerators.plainVariant(ModelTemplates.HANGING_BIRDHOUSE.create(block, new TextureMapping().put(TextureSlot.PARTICLE, planks).put(TextureSlots.PLANKS, planks).put(TextureSlots.ROOF, roof), blockModels.modelOutput));
        return MultiVariantGenerator.dispatch(block, model).with(ROTATION_HORIZONTAL_FACING);
    }

    private void createHedgeBlock(Block block, ResourceLocation leaves, BlockModelGenerators blockModels) {
        createHedgeBlock(block, leaves, blockModels, 0);
    }

    private void createHedgeBlock(Block block, ResourceLocation leaves, BlockModelGenerators blockModels, int tint) {
        MultiVariant multivariant = BlockModelGenerators.plainVariant(
                ModelTemplates.HEDGE_POST.create(block, new TextureMapping().put(TextureSlot.PARTICLE, leaves).put(TextureSlot.WALL, leaves), blockModels.modelOutput)
        );
        MultiVariant multivariant1 = BlockModelGenerators.plainVariant(
                ModelTemplates.HEDGE_LOW_SIDE.create(block, new TextureMapping().put(TextureSlot.PARTICLE, leaves).put(TextureSlot.WALL, leaves), blockModels.modelOutput)
        );
        MultiVariant multivariant2 = BlockModelGenerators.plainVariant(
                ModelTemplates.HEDGE_TALL_SIDE.create(block, new TextureMapping().put(TextureSlot.PARTICLE, leaves).put(TextureSlot.WALL, leaves), blockModels.modelOutput)
        );
        blockModels.blockStateOutput.accept(BlockModelGenerators.createWall(block, multivariant, multivariant1, multivariant2));
        ResourceLocation inventory = ModelTemplates.HEDGE_INVENTORY.create(block, new TextureMapping().put(TextureSlot.PARTICLE, leaves).put(TextureSlot.WALL, leaves), blockModels.modelOutput);
        if (tint != 0) {
            blockModels.registerSimpleTintedItemModel(block, inventory, ItemModelUtils.constantTint(tint));
        } else {
            blockModels.registerSimpleItemModel(block, inventory);
        }
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

    private void registerBirdbathModel(ItemModelOutput output, Block block) {
        output.accept(block.asItem(), ItemModelUtils.tintedModel(ModelLocationUtils.getModelLocation(block), ItemModelUtils.constantTint(-1), ItemModelUtils.constantTint(4159204)));
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.listElements().filter((holder) -> false);
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return BuiltInRegistries.ITEM.listElements().filter((holder) -> false);
    }

    public static class TextureSlots {
        public static final TextureSlot BLOCK = TextureSlot.create("block");
        public static final TextureSlot STAND = TextureSlot.create("stand");
        public static final TextureSlot PLANKS = TextureSlot.create("planks");
        public static final TextureSlot DIRT = TextureSlot.create("dirt");
        public static final TextureSlot WOOD = TextureSlot.create("wood");
        public static final TextureSlot WOOD_TOP = TextureSlot.create("wood_top");
        public static final TextureSlot METAL = TextureSlot.create("metal");
        public static final TextureSlot STONE = TextureSlot.create("stone");
        public static final TextureSlot CHAIR = TextureSlot.create("chair");
        public static final TextureSlot ROOF = TextureSlot.create("roof");
        public static final TextureSlot LOG = TextureSlot.create("log");
        public static final TextureSlot LOG_TOP = TextureSlot.create("log_top");
        public static final TextureSlot STRIPPED_LOG = TextureSlot.create("stripped_log");
        public static final TextureSlot COUNTER = TextureSlot.create("counter");
        public static final TextureSlot CABINET = TextureSlot.create("cabinet");
        public static final TextureSlot FRIDGE = TextureSlot.create("fridge");
        public static final TextureSlot CLOSET = TextureSlot.create("closet");
        public static final TextureSlot TOP = TextureSlot.create("top");
        public static final TextureSlot WOOL = TextureSlot.create("wool");
        public static final TextureSlot CURTAIN_ROD = TextureSlot.create("curtain_rod");
        public static final TextureSlot LAMP = TextureSlot.create("lamp");
        public static final TextureSlot DECORATION = TextureSlot.create("decoration");
        public static final TextureSlot DOOR = TextureSlot.create("door");
        public static final TextureSlot BARS = TextureSlot.create("bars");
        public static final TextureSlot HANDLE = TextureSlot.create("handle");
        public static final TextureSlot KNIFE = TextureSlot.create("knife");
        public static final TextureSlot TABLEWARE = TextureSlot.create("tableware");
        public static final TextureSlot TOASTER = TextureSlot.create("toaster");
        public static final TextureSlot GLASS = TextureSlot.create("glass");
        public static final TextureSlot FAUCET = TextureSlot.create("faucet");
        public static final TextureSlot CHAIN = TextureSlot.create("chain");
        public static final TextureSlot WATER = TextureSlot.create("water");
        public static final TextureSlot LIQUID = TextureSlot.create("liquid");
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

        public static final ModelTemplate FRIDGE_BOTTOM = getTemplate("fridge_bottom_template", Optional.of("_bottom"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.BARS, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_BOTTOM_OPEN = getTemplate("fridge_bottom_open_template", Optional.of("_bottom_open"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.BARS, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_BOTTOM_MIRRORED = getTemplate("fridge_bottom_mirrored_template", Optional.of("_bottom_mirrored"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.BARS, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_BOTTOM_MIRRORED_OPEN = getTemplate("fridge_bottom_mirrored_open_template", Optional.of("_bottom_mirrored_open"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.BARS, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_TOP = getTemplate("fridge_top_template", Optional.of("_top"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_TOP_OPEN = getTemplate("fridge_top_open_template", Optional.of("_top_open"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_TOP_MIRRORED = getTemplate("fridge_top_mirrored_template", Optional.of("_top_mirrored"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_TOP_MIRRORED_OPEN = getTemplate("fridge_top_mirrored_open_template", Optional.of("_top_mirrored_open"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.HANDLE);
        public static final ModelTemplate FRIDGE_INVENTORY = getTemplate("fridge_inventory_template", Optional.of("_inventory"), TextureSlot.PARTICLE, TextureSlots.FRIDGE, TextureSlots.HANDLE);

        public static final ModelTemplate KNIFE_BLOCK = getTemplate("knife_block_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.BLOCK, TextureSlots.STAND, TextureSlots.HANDLE, TextureSlots.KNIFE);
        public static final ModelTemplate TABLEWARE = getTemplate("tableware_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.TABLEWARE, TextureSlots.LIQUID);
        public static final ModelTemplate TOASTER = getTemplate("toaster_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.TOASTER, TextureSlot.BOTTOM);
        public static final ModelTemplate TOASTER_ON = getTemplate("toaster_on_template", Optional.of("_on"), TextureSlot.PARTICLE, TextureSlots.TOASTER, TextureSlot.BOTTOM);

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

        public static final ModelTemplate BEDSIDE_TABLE = getTemplate("bedside_table_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);
        public static final ModelTemplate BEDSIDE_TABLE_OPEN = getTemplate("bedside_table_open_template", Optional.of("_open"), TextureSlot.PARTICLE, TextureSlots.COUNTER, TextureSlots.TOP, TextureSlots.DOOR);

        public static final ModelTemplate CLOSET_BOTTOM = getTemplate("closet_bottom_template", Optional.of("_bottom"), TextureSlot.PARTICLE, TextureSlots.CLOSET, TextureSlots.DOOR);
        public static final ModelTemplate CLOSET_BOTTOM_OPEN = getTemplate("closet_bottom_open_template", Optional.of("_bottom_open"), TextureSlot.PARTICLE, TextureSlots.CLOSET, TextureSlots.DOOR);
        public static final ModelTemplate CLOSET_TOP = getTemplate("closet_top_template", Optional.of("_top"), TextureSlot.PARTICLE, TextureSlots.CLOSET, TextureSlots.DOOR);
        public static final ModelTemplate CLOSET_TOP_OPEN = getTemplate("closet_top_open_template", Optional.of("_top_open"), TextureSlot.PARTICLE, TextureSlots.CLOSET, TextureSlots.DOOR);
        public static final ModelTemplate CLOSET_INVENTORY = getTemplate("closet_inventory_template", Optional.of("_inventory"), TextureSlot.PARTICLE, TextureSlots.CLOSET, TextureSlots.DOOR);

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
        public static final ModelTemplate WOODEN_BED_INVENTORY = getTemplate("wooden_bed_inventory_template", Optional.of("_inventory"), TextureSlot.PARTICLE, TextureSlots.WOOD, TextureSlots.WOOL, TextureSlots.DECORATION);

        public static final ModelTemplate BENCH_SINGLE = getTemplate("bench_single_template", Optional.of("_single"), TextureSlot.PARTICLE, TextureSlots.WOOD, TextureSlots.METAL);
        public static final ModelTemplate BENCH_RIGHT = getTemplate("bench_right_template", Optional.of("_right"), TextureSlot.PARTICLE, TextureSlots.WOOD, TextureSlots.METAL);
        public static final ModelTemplate BENCH_LEFT = getTemplate("bench_left_template", Optional.of("_left"), TextureSlot.PARTICLE, TextureSlots.WOOD, TextureSlots.METAL);
        public static final ModelTemplate BENCH_MIDDLE = getTemplate("bench_middle_template", Optional.of("_middle"), TextureSlot.PARTICLE, TextureSlots.WOOD);

        public static final ModelTemplate FLOWER_BOX_BOTTOM = getTemplate("flower_box_bottom_template", Optional.of("_bottom"), TextureSlot.PARTICLE, TextureSlots.DIRT, TextureSlots.WOOD, TextureSlots.WOOD_TOP);
        public static final ModelTemplate FLOWER_BOX_BOTTOM_INNER_CORNER = getTemplate("flower_box_bottom_inner_corner_template", Optional.of("_bottom"), TextureSlot.PARTICLE, TextureSlots.DIRT, TextureSlots.WOOD, TextureSlots.WOOD_TOP);
        public static final ModelTemplate FLOWER_BOX_BOTTOM_OUTER_CORNER = getTemplate("flower_box_bottom_outer_corner_template", Optional.of("_bottom"), TextureSlot.PARTICLE, TextureSlots.DIRT, TextureSlots.WOOD, TextureSlots.WOOD_TOP);
        public static final ModelTemplate FLOWER_BOX_BOTTOM_BIG = getTemplate("flower_box_bottom_big_template", Optional.of("_bottom"), TextureSlot.PARTICLE, TextureSlots.DIRT, TextureSlots.WOOD, TextureSlots.WOOD_TOP);
        public static final ModelTemplate FLOWER_BOX_TOP = getTemplate("flower_box_top_template", Optional.of("_top"), TextureSlot.PARTICLE, TextureSlots.DIRT, TextureSlots.WOOD, TextureSlots.WOOD_TOP);
        public static final ModelTemplate FLOWER_BOX_TOP_INNER_CORNER = getTemplate("flower_box_top_inner_corner_template", Optional.of("_top"), TextureSlot.PARTICLE, TextureSlots.DIRT, TextureSlots.WOOD, TextureSlots.WOOD_TOP);
        public static final ModelTemplate FLOWER_BOX_TOP_OUTER_CORNER = getTemplate("flower_box_top_outer_corner_template", Optional.of("_top"), TextureSlot.PARTICLE, TextureSlots.DIRT, TextureSlots.WOOD, TextureSlots.WOOD_TOP);
        public static final ModelTemplate FLOWER_BOX_TOP_BIG = getTemplate("flower_box_top_big_template", Optional.of("_top"), TextureSlot.PARTICLE, TextureSlots.DIRT, TextureSlots.WOOD, TextureSlots.WOOD_TOP);

        public static final ModelTemplate TRASH_BIN = getTemplate("trash_bin_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.METAL, TextureSlots.WOOD);
        public static final ModelTemplate BIRDBATH = getTemplate("birdbath_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.STONE, TextureSlots.WATER);

        public static final ModelTemplate HEDGE_POST = getTemplate("hedge_post_template", Optional.of("_post"), TextureSlot.WALL).extend().renderType("cutout_mipped").build();
        public static final ModelTemplate HEDGE_LOW_SIDE = getTemplate("hedge_side_template", Optional.of("_side"), TextureSlot.WALL).extend().renderType("cutout_mipped").build();
        public static final ModelTemplate HEDGE_TALL_SIDE = getTemplate("hedge_side_tall_template", Optional.of("_side_tall"), TextureSlot.WALL).extend().renderType("cutout_mipped").build();
        public static final ModelTemplate HEDGE_INVENTORY = getTemplate("hedge_inventory_template", Optional.of("_inventory"), TextureSlot.WALL);

        public static final ModelTemplate BIRDHOUSE_BOTTOM = getTemplate("birdhouse_bottom_template", Optional.of("_bottom"), TextureSlot.PARTICLE, TextureSlots.LOG, TextureSlots.LOG_TOP);
        public static final ModelTemplate BIRDHOUSE_TOP = getTemplate("birdhouse_top_template", Optional.of("_top"), TextureSlot.PARTICLE, TextureSlots.LOG, TextureSlots.PLANKS, TextureSlots.ROOF);
        public static final ModelTemplate BIRDHOUSE_INVENTORY = getTemplate("birdhouse_inventory_template", Optional.of("_inventory"), TextureSlots.LOG, TextureSlots.LOG_TOP, TextureSlots.PLANKS, TextureSlots.ROOF);

        public static final ModelTemplate HANGING_BIRDHOUSE = getTemplate("hanging_birdhouse_template", Optional.empty(), TextureSlot.PARTICLE, TextureSlots.PLANKS, TextureSlots.ROOF);

        private static ModelTemplate getTemplate(String name, Optional<String> suffix, TextureSlot... slots) {
            return new ModelTemplate(Optional.of(FabulousFurniture.prefix(name).withPrefix("block/template/")), suffix, slots);
        }
    }
}