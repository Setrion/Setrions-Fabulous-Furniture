package net.setrion.fabulous_furniture.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.setrion.fabulous_furniture.FabulousFurniture;
import net.setrion.fabulous_furniture.registry.SFFBlocks;
import net.setrion.fabulous_furniture.world.level.block.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static net.setrion.fabulous_furniture.registry.SFFBlocks.*;

public class BlockLootTables extends BlockLootSubProvider {

    private final Set<Block> knownBlocks = new HashSet<>();

    public BlockLootTables(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void add(Block block, LootTable.Builder builder) {
        super.add(block, builder);
        knownBlocks.add(block);
    }

    @Override
    protected void generate() {
        dropSelf(CARPENTRY_TABLE.get());
        generateFridges();
        generateCurtains();
        generateKitchenTiles();
        generateFurniture();
        generateHedges();
        WOOD_TYPES.forEach(this::generateWoodenFurniture);
    }

    private void generateFridges() {
        add(IRON_FRIDGE.get(), block -> createSinglePropConditionTable(block, FridgeBlock.HALF, DoubleBlockHalf.LOWER));
        add(COPPER_FRIDGE.get(), block -> createSinglePropConditionTable(block, FridgeBlock.HALF, DoubleBlockHalf.LOWER));
        add(EXPOSED_COPPER_FRIDGE.get(), block -> createSinglePropConditionTable(block, FridgeBlock.HALF, DoubleBlockHalf.LOWER));
        add(WEATHERED_COPPER_FRIDGE.get(), block -> createSinglePropConditionTable(block, FridgeBlock.HALF, DoubleBlockHalf.LOWER));
        add(OXIDIZED_COPPER_FRIDGE.get(), block -> createSinglePropConditionTable(block, FridgeBlock.HALF, DoubleBlockHalf.LOWER));
        add(WAXED_COPPER_FRIDGE.get(), block -> createSinglePropConditionTable(block, FridgeBlock.HALF, DoubleBlockHalf.LOWER));
        add(WAXED_EXPOSED_COPPER_FRIDGE.get(), block -> createSinglePropConditionTable(block, FridgeBlock.HALF, DoubleBlockHalf.LOWER));
        add(WAXED_WEATHERED_COPPER_FRIDGE.get(), block -> createSinglePropConditionTable(block, FridgeBlock.HALF, DoubleBlockHalf.LOWER));
        add(WAXED_OXIDIZED_COPPER_FRIDGE.get(), block -> createSinglePropConditionTable(block, FridgeBlock.HALF, DoubleBlockHalf.LOWER));
        add(GOLD_FRIDGE.get(), block -> createSinglePropConditionTable(block, FridgeBlock.HALF, DoubleBlockHalf.LOWER));
        add(NETHERITE_FRIDGE.get(), block -> createSinglePropConditionTable(block, FridgeBlock.HALF, DoubleBlockHalf.LOWER));
    }

    private void generateCurtains() {
        WOOL_COLORS.forEach((wool, color) -> METALS.forEach((rod, name) -> dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_"+name+"_curtains")))));
    }

    private void generateKitchenTiles() {
        dropSelf(WHITE_LIGHT_GRAY_KITCHEN_TILES.get());
        dropSelf(WHITE_GRAY_KITCHEN_TILES.get());
        dropSelf(WHITE_BLACK_KITCHEN_TILES.get());
        dropSelf(WHITE_BROWN_KITCHEN_TILES.get());
        dropSelf(WHITE_ORANGE_KITCHEN_TILES.get());
        dropSelf(WHITE_RED_KITCHEN_TILES.get());
        dropSelf(WHITE_YELLOW_KITCHEN_TILES.get());
        dropSelf(WHITE_LIME_KITCHEN_TILES.get());
        dropSelf(WHITE_GREEN_KITCHEN_TILES.get());
        dropSelf(WHITE_CYAN_KITCHEN_TILES.get());
        dropSelf(WHITE_LIGHT_BLUE_KITCHEN_TILES.get());
        dropSelf(WHITE_BLUE_KITCHEN_TILES.get());
        dropSelf(WHITE_PURPLE_KITCHEN_TILES.get());
        dropSelf(WHITE_MAGENTA_KITCHEN_TILES.get());
        dropSelf(WHITE_PINK_KITCHEN_TILES.get());
    }

    private void generateFurniture() {
        STONE_MATERIALS.forEach((block, name) -> {
            String top_name = block.getDescriptionId().replaceFirst("block.minecraft.", "").replaceFirst("quartz_block", "quartz");
            dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(top_name+"_birdbath")));
        });

        TABLEWARE_MATERIALS.forEach((block, suffix) -> {
            String top_name = block.getDescriptionId().replaceFirst("block.minecraft.", "").replaceFirst("quartz_block", "quartz");
            dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(top_name+"_tableware")));
        });

        METALS.forEach((metal, name) -> dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(name+"_toaster"))));
    }

    private void generateWoodenFurniture(WoodType type) {
        Block planks = getBlockFromResourceLocation(ResourceLocation.parse(type.name()+"_planks"));
        String log_suffix;
        if (type == WoodType.CRIMSON || type == WoodType.WARPED) {
            log_suffix = "_stem";
        } else if (type == WoodType.BAMBOO) {
            log_suffix = "_block";
        } else {
            log_suffix = "_log";
        }
        add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_crate")), this::createNameableBlockEntityTable);

        STONE_MATERIALS.forEach(((block, s) -> {
            String top_name = block.getDescriptionId().replaceFirst("block.minecraft.", "").replaceFirst("quartz_block", "quartz");
            dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter")));
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_shelf")), this::createNameableBlockEntityTable);
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_door")), this::createNameableBlockEntityTable);
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_small_drawer")), this::createNameableBlockEntityTable);
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_big_drawer")), this::createNameableBlockEntityTable);
            dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_sink")));
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_counter_smoker")), this::createNameableBlockEntityTable);
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_cabinet_door")), this::createNameableBlockEntityTable);
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_cabinet_glass_door")), this::createNameableBlockEntityTable);
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_cabinet_sideways_door")), this::createNameableBlockEntityTable);
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_kitchen_cabinet_sideways_glass_door")), this::createNameableBlockEntityTable);

            dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_counter")));
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_shelf")), this::createNameableBlockEntityTable);
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_door")), this::createNameableBlockEntityTable);
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_small_drawer")), this::createNameableBlockEntityTable);
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_big_drawer")), this::createNameableBlockEntityTable);
            dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_sink")));
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_smoker")), this::createNameableBlockEntityTable);
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_door")), this::createNameableBlockEntityTable);
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_glass_door")), this::createNameableBlockEntityTable);
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_door")), this::createNameableBlockEntityTable);
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_glass_door")), this::createNameableBlockEntityTable);

            dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter")));
            add(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_shelf")), this::createNameableBlockEntityTable);
            add(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_door")), this::createNameableBlockEntityTable);
            add(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_small_drawer")), this::createNameableBlockEntityTable);
            add(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_big_drawer")), this::createNameableBlockEntityTable);
            dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_sink")));
            add(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_smoker")), this::createNameableBlockEntityTable);
            add(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_door")), this::createNameableBlockEntityTable);
            add(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_glass_door")), this::createNameableBlockEntityTable);
            add(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_door")), this::createNameableBlockEntityTable);
            add(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_glass_door")), this::createNameableBlockEntityTable);

            dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+top_name+"_knife_block")));
        }));

        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_kitchen_cabinet")));
        add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_kitchen_cabinet_shelf")), this::createNameableBlockEntityTable);
        add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_kitchen_shelf")), this::createNameableBlockEntityTable);

        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_kitchen_cabinet")));
        add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_kitchen_cabinet_shelf")), this::createNameableBlockEntityTable);
        add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_kitchen_shelf")), this::createNameableBlockEntityTable);

        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_kitchen_cabinet")));
        add(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_kitchen_cabinet_shelf")), this::createNameableBlockEntityTable);
        add(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_kitchen_shelf")), this::createNameableBlockEntityTable);

        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_table")));
        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_table")));
        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_table")));

        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_bedside_table")));
        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_bedside_table")));
        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_bedside_table")));

        add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_closet")), block -> createSinglePropConditionTable(block, ClosetBlock.HALF, DoubleBlockHalf.LOWER));
        add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_closet")), block -> createSinglePropConditionTable(block, ClosetBlock.HALF, DoubleBlockHalf.LOWER));
        add(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_closet")), block -> createSinglePropConditionTable(block, ClosetBlock.HALF, DoubleBlockHalf.LOWER));

        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_flower_box")));
        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_flower_box")));
        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_flower_box")));

        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_flower_box_inner_corner")));
        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_flower_box_inner_corner")));
        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_flower_box_inner_corner")));

        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_flower_box_outer_corner")));
        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_flower_box_outer_corner")));
        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_flower_box_outer_corner")));

        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_flower_box_big")));
        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_flower_box_big")));
        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_flower_box_big")));

        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_trash_bin")));
        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_trash_bin")));
        dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_trash_bin")));

        for (WoodType type2 : WOOD_TYPES) {
            String log_suffix2;
            if (type2 == WoodType.CRIMSON || type2 == WoodType.WARPED) {
                log_suffix2 = "_stem";
            } else if (type2 == WoodType.BAMBOO) {
                log_suffix2 = "_block";
            } else {
                log_suffix2 = "_log";
            }

            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+type2.name()+"_birdhouse")), block -> createSinglePropConditionTable(block, BirdhouseBlock.HALF, DoubleBlockHalf.LOWER));
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+type2.name()+"_birdhouse")), block -> createSinglePropConditionTable(block, BirdhouseBlock.HALF, DoubleBlockHalf.LOWER));
            add(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+type2.name()+"_birdhouse")), block -> createSinglePropConditionTable(block, BirdhouseBlock.HALF, DoubleBlockHalf.LOWER));
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_"+type2.name()+log_suffix2+"_birdhouse")), block -> createSinglePropConditionTable(block, BirdhouseBlock.HALF, DoubleBlockHalf.LOWER));
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_"+type2.name()+log_suffix2+"_birdhouse")), block -> createSinglePropConditionTable(block, BirdhouseBlock.HALF, DoubleBlockHalf.LOWER));
            add(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_"+type2.name()+log_suffix2+"_birdhouse")), block -> createSinglePropConditionTable(block, BirdhouseBlock.HALF, DoubleBlockHalf.LOWER));
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+"_stripped_"+type2.name()+log_suffix2+"_birdhouse")), block -> createSinglePropConditionTable(block, BirdhouseBlock.HALF, DoubleBlockHalf.LOWER));
            add(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name()+log_suffix+"_stripped_"+type2.name()+log_suffix2+"_birdhouse")), block -> createSinglePropConditionTable(block, BirdhouseBlock.HALF, DoubleBlockHalf.LOWER));
            add(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_"+type.name()+log_suffix+"_stripped_"+type2.name()+log_suffix2+"_birdhouse")), block -> createSinglePropConditionTable(block, BirdhouseBlock.HALF, DoubleBlockHalf.LOWER));

            dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("hanging_"+type.name()+"_"+type2.name()+"_birdhouse")));
            dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("hanging_"+type.name()+log_suffix+"_"+type2.name()+"_birdhouse")));
            dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("hanging_stripped_"+type.name()+log_suffix+"_"+type2.name()+"_birdhouse")));
            dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("hanging_"+type.name()+"_"+type2.name()+log_suffix2+"_birdhouse")));
            dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("hanging_"+type.name()+log_suffix+"_"+type2.name()+log_suffix2+"_birdhouse")));
            dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("hanging_stripped_"+type.name()+log_suffix+"_"+type2.name()+log_suffix2+"_birdhouse")));
            dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("hanging_"+type.name()+"_stripped_"+type2.name()+log_suffix2+"_birdhouse")));
            dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("hanging_"+type.name()+log_suffix+"_stripped_"+type2.name()+log_suffix2+"_birdhouse")));
            dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("hanging_stripped_"+type.name()+log_suffix+"_stripped_"+type2.name()+log_suffix2+"_birdhouse")));
        }

        METALS.forEach((metal, name) -> {
            if (metal != Blocks.COPPER_BLOCK) {
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + "_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + log_suffix + "_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_" + type.name() + log_suffix + "_" + name + "_bench")));
            } else {
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + "_waxed_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + log_suffix + "_waxed_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_" + type.name() + log_suffix + "_waxed_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + "_waxed_exposed_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + log_suffix + "_waxed_exposed_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_" + type.name() + log_suffix + "_waxed_exposed_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + "_waxed_weathered_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + log_suffix + "_waxed_weathered_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_" + type.name() + log_suffix + "_waxed_weathered_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + "_waxed_oxidized_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + log_suffix + "_waxed_oxidized_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_" + type.name() + log_suffix + "_waxed_oxidized_" + name + "_bench")));

                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + "_" +  name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + log_suffix + "_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_" + type.name() + log_suffix + "_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + "_exposed_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + log_suffix + "_exposed_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_" + type.name() + log_suffix + "_exposed_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + "_weathered_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + log_suffix + "_weathered_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_" + type.name() + log_suffix + "_weathered_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + "_oxidized_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(type.name() + log_suffix + "_oxidized_" + name + "_bench")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix("stripped_" + type.name() + log_suffix + "_oxidized_" + name + "_bench")));
            }
        });

        WOOL_COLORS.forEach((block, color) -> BlockFamilies.getAllFamilies().toList().forEach(blockFamily -> {
            if (blockFamily.getBaseBlock() == planks) {
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_"+type.name()+"_chair")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_"+type.name()+log_suffix+"_chair")));
                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_stripped_"+type.name()+log_suffix+"_chair")));

                dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_"+type.name()+"_lamp")));

                add(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_"+type.name()+"_bed")), loot -> createSinglePropConditionTable(loot, WoodenBedBlock.PART, BedPart.HEAD));
                add(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_"+type.name()+log_suffix+"_bed")), loot -> createSinglePropConditionTable(loot, WoodenBedBlock.PART, BedPart.HEAD));
                add(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_stripped_"+type.name()+log_suffix+"_bed")), loot -> createSinglePropConditionTable(loot, WoodenBedBlock.PART, BedPart.HEAD));
            }
        }));
    }

    private void generateHedges() {
        dropSelf(OAK_HEDGE.get());
        dropSelf(SPRUCE_HEDGE.get());
        dropSelf(BIRCH_HEDGE.get());
        dropSelf(JUNGLE_HEDGE.get());
        dropSelf(ACACIA_HEDGE.get());
        dropSelf(CHERRY_HEDGE.get());
        dropSelf(DARK_OAK_HEDGE.get());
        dropSelf(PALE_OAK_HEDGE.get());
        dropSelf(MANGROVE_HEDGE.get());
        dropSelf(AZALEA_HEDGE.get());
        dropSelf(FLOWERING_AZALEA_HEDGE.get());
    }

    private Block getBlockFromResourceLocation(ResourceLocation location) {
        return BuiltInRegistries.BLOCK.getValue(location);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return SFFBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::value).collect(Collectors.toList());
    }
}