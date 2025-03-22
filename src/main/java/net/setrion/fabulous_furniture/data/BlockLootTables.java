package net.setrion.fabulous_furniture.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.setrion.fabulous_furniture.FabulousFurniture;
import net.setrion.fabulous_furniture.registry.SFFBlocks;
import net.setrion.fabulous_furniture.world.level.block.ClosetBlock;
import net.setrion.fabulous_furniture.world.level.block.FridgeBlock;
import net.setrion.fabulous_furniture.world.level.block.WoodenBedBlock;

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
        WoodType.values().toList().forEach(this::generateWoodenFurniture);
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
        WOOL_COLORS.forEach((wool, color) -> CURTAIN_RODS.forEach((rod, name) -> dropSelf(getBlockFromResourceLocation(FabulousFurniture.prefix(color+"_"+name+"_curtains")))));
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

        COUNTER_TOPS.forEach(((block, s) -> {
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

    private Block getBlockFromResourceLocation(ResourceLocation location) {
        return BuiltInRegistries.BLOCK.getValue(location);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return SFFBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::value).collect(Collectors.toList());
    }
}