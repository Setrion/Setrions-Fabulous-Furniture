package net.setrion.fabulous_furniture.data;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.setrion.fabulous_furniture.FabulousFurniture;
import net.setrion.fabulous_furniture.registry.SFFStats;
import net.setrion.fabulous_furniture.world.level.block.BlockTagSupplier;
import net.setrion.fabulous_furniture.world.level.block.state.properties.FurnitureCategory;
import net.setrion.fabulous_furniture.world.level.block.state.properties.MaterialType;

import static net.setrion.fabulous_furniture.registry.SFFBlocks.*;

public class LangGenerator extends LanguageProvider {

    public LangGenerator(PackOutput output) {
        super(output, FabulousFurniture.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        MaterialType.values().toList().forEach(material -> add(material.getTranslatableName(), createTranslatedName(material.name())));

        FurnitureCategory.values().toList().forEach(category -> add(category.getTranslatableName(), createTranslatedName(category.name())));

        add("itemGroup.fabulous_furniture.main", "Setrion's Fabulous Furniture");

        add("carpentry_table.amount", "Amount: ");
        add("carpentry_table.ingredients", "Ingredients:");
        add("carpentry_table.categories", "Categories");
        add("carpentry_table.materials", "Materials");

        add("container.carpentry_table", "Carpentry");
        add("container.crate", "Crate");
        add("container.kitchen_storage", "Kitchen Storage");
        add("container.fridge", "Fridge");
        add("container.bedside_table", "Bedside Table");
        add("container.closet", "Closet");

        add("trash_bin.warning", "Interacting with a Trash Bin will delete the Item you're holding!");

        translateCurtains();
        translateTableware();
        translateToasters();
        translateBirdbaths();

        translateStats();
        WOOD_TYPES.forEach(this::translateWoodenFurniture);

        add(CARPENTRY_TABLE.asItem(), "Carpentry Table");

        add(COPPER_FRIDGE.asItem(), "Copper Fridge");
        add(EXPOSED_COPPER_FRIDGE.asItem(), "Exposed Copper Fridge");
        add(WEATHERED_COPPER_FRIDGE.asItem(), "Weathered Copper Fridge");
        add(OXIDIZED_COPPER_FRIDGE.asItem(), "Oxidized Copper Fridge");
        add(WAXED_COPPER_FRIDGE.asItem(), "Waxed Copper Fridge");
        add(WAXED_EXPOSED_COPPER_FRIDGE.asItem(), "Waxed Exposed Copper Fridge");
        add(WAXED_WEATHERED_COPPER_FRIDGE.asItem(), "Waxed Weathered Copper Fridge");
        add(WAXED_OXIDIZED_COPPER_FRIDGE.asItem(), "Waxed Oxidized Copper Fridge");
        add(IRON_FRIDGE.asItem(), "Iron Fridge");
        add(GOLD_FRIDGE.asItem(), "Gold Fridge");
        add(NETHERITE_FRIDGE.asItem(), "Netherite Fridge");

        add(WHITE_LIGHT_GRAY_KITCHEN_TILES.asItem(), "Light Gray Kitchen Tiles");
        add(WHITE_GRAY_KITCHEN_TILES.asItem(), "Gray Kitchen Tiles");
        add(WHITE_BLACK_KITCHEN_TILES.asItem(), "Black Kitchen Tiles");
        add(WHITE_BROWN_KITCHEN_TILES.asItem(), "Brown Kitchen Tiles");
        add(WHITE_RED_KITCHEN_TILES.asItem(), "Red Kitchen Tiles");
        add(WHITE_ORANGE_KITCHEN_TILES.asItem(), "Orange Kitchen Tiles");
        add(WHITE_YELLOW_KITCHEN_TILES.asItem(), "Yellow Kitchen Tiles");
        add(WHITE_LIME_KITCHEN_TILES.asItem(), "Lime Kitchen Tiles");
        add(WHITE_GREEN_KITCHEN_TILES.asItem(), "Green Kitchen Tiles");
        add(WHITE_CYAN_KITCHEN_TILES.asItem(), "Cyan Kitchen Tiles");
        add(WHITE_LIGHT_BLUE_KITCHEN_TILES.asItem(), "Light Blue Kitchen Tiles");
        add(WHITE_BLUE_KITCHEN_TILES.asItem(), "Blue Kitchen Tiles");
        add(WHITE_PURPLE_KITCHEN_TILES.asItem(), "Purple Kitchen Tiles");
        add(WHITE_MAGENTA_KITCHEN_TILES.asItem(), "Magenta Kitchen Tiles");
        add(WHITE_PINK_KITCHEN_TILES.asItem(), "Pink Kitchen Tiles");

        add(OAK_HEDGE.asItem(), "Oak Hedge");
        add(SPRUCE_HEDGE.asItem(), "Spruce Hedge");
        add(BIRCH_HEDGE.asItem(), "Birch Hedge");
        add(JUNGLE_HEDGE.asItem(), "Jungle Hedge");
        add(ACACIA_HEDGE.asItem(), "Acacia Hedge");
        add(CHERRY_HEDGE.asItem(), "Cherry Hedge");
        add(DARK_OAK_HEDGE.asItem(), "Dark Oak Hedge");
        add(PALE_OAK_HEDGE.asItem(), "Pale Oak Hedge");
        add(MANGROVE_HEDGE.asItem(), "Mangrove Hedge");
        add(AZALEA_HEDGE.asItem(), "Azalea Hedge");
        add(FLOWERING_AZALEA_HEDGE.asItem(), "Flowering Azalea Hedge");
    }

    private void translateStats() {
        add("stat."+SFFStats.OPEN_CRATE.get().toLanguageKey(), "Crates Opened");
        add("stat."+SFFStats.OPEN_KITCHEN_COUNTER.get().toLanguageKey(), "Kitchen Counters Opened");
        add("stat."+SFFStats.OPEN_FRIDGE.get().toLanguageKey(), "Fridges Opened");
        add("stat."+SFFStats.OPEN_BEDSIDE_TABLE.get().toLanguageKey(), "Bedside Tables Opened");
        add("stat."+SFFStats.OPEN_CLOSET.get().toLanguageKey(), "Closets Opened");
        add("stat."+SFFStats.ACTIVATE_SINK.get().toLanguageKey(), "Sinks Activated");
        add("stat."+SFFStats.TAKE_WATER_FROM_SINK.get().toLanguageKey(), "Water Taken from Sink");
        add("stat."+SFFStats.INTERACT_WITH_FLOWER_BOX.get().toLanguageKey(), "Interactions with Flower Boxes");
        add("stat."+SFFStats.INTERACT_WITH_LAMP.get().toLanguageKey(), "Interactions with Lamps");
        add("stat."+SFFStats.THROW_AWAY_ITEM.get().toLanguageKey(), "Items Thrown away");
        add("stat."+SFFStats.SIT_ON_CHAIR.get().toLanguageKey(), "Times Sat on a Chair");
        add("stat."+SFFStats.SIT_ON_BENCH.get().toLanguageKey(), "Times Sat on a Bench");
    }

    private void translateBirdbaths() {
        STONE_MATERIALS.forEach((block, name) -> {
            String top_name = block.getDescriptionId().replaceFirst("block.minecraft.", "").replaceFirst("quartz_block", "quartz");
            translate(top_name+"_birdbath");
        });
    }

    private void translateCurtains() {
        WOOL_COLORS.forEach((wool, color) -> METALS.forEach((rod, name) -> translate(color+"_"+name+"_curtains")));
    }

    protected void translateTableware() {
        TABLEWARE_MATERIALS.forEach((block, suffix) -> {
            String top_name = block.getDescriptionId().replaceFirst("block.minecraft.", "").replaceFirst("quartz_block", "quartz");
            translate(top_name+"_tableware");
        });
    }

    protected void translateToasters() {
        METALS.forEach((metal, name) -> translate(name+"_toaster"));
    }

    private void translateWoodenFurniture(WoodType type) {
        Block planks = getBlockFromResourceLocation(ResourceLocation.parse(type.name()+"_planks"));
        String log_suffix;
        if (type == WoodType.CRIMSON || type == WoodType.WARPED) {
            log_suffix = "_stem";
        } else if (type == WoodType.BAMBOO) {
            log_suffix = "_block";
        } else {
            log_suffix = "_log";
        }
        translate(type.name()+"_crate");
        STONE_MATERIALS.forEach(((block, s) -> {
            String top_name = block.getDescriptionId().replaceFirst("block.minecraft.", "").replaceFirst("quartz_block", "quartz");
            translate(type.name()+"_"+top_name+"_kitchen_counter");
            translate(type.name()+"_"+top_name+"_kitchen_counter_shelf");
            translate(type.name()+"_"+top_name+"_kitchen_counter_door");
            translate(type.name()+"_"+top_name+"_kitchen_counter_small_drawer");
            translate(type.name()+"_"+top_name+"_kitchen_counter_big_drawer");
            translate(type.name()+"_"+top_name+"_kitchen_counter_sink");
            translate(type.name()+"_"+top_name+"_kitchen_counter_smoker");
            translate(type.name()+"_"+top_name+"_kitchen_cabinet_door");
            translate(type.name()+"_"+top_name+"_kitchen_cabinet_glass_door");
            translate(type.name()+"_"+top_name+"_kitchen_cabinet_sideways_door");
            translate(type.name()+"_"+top_name+"_kitchen_cabinet_sideways_glass_door");

            translate(type.name()+log_suffix+"_"+top_name+"_kitchen_counter");
            translate(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_shelf");
            translate(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_door");
            translate(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_small_drawer");
            translate(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_big_drawer");
            translate(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_sink");
            translate(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_smoker");
            translate(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_door");
            translate(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_glass_door");
            translate(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_door");
            translate(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_glass_door");

            translate("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter");
            translate("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_shelf");
            translate("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_door");
            translate("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_small_drawer");
            translate("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_big_drawer");
            translate("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_sink");
            translate("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_smoker");
            translate("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_door");
            translate("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_glass_door");
            translate("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_door");
            translate("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_glass_door");

            translate(type.name()+"_"+top_name+"_knife_block");
        }));

        translate(type.name()+"_kitchen_cabinet");
        translate(type.name()+"_kitchen_cabinet_shelf");
        translate(type.name()+"_kitchen_shelf");

        translate(type.name()+log_suffix+"_kitchen_cabinet");
        translate(type.name()+log_suffix+"_kitchen_cabinet_shelf");
        translate(type.name()+log_suffix+"_kitchen_shelf");

        translate("stripped_"+type.name()+log_suffix+"_kitchen_cabinet");
        translate("stripped_"+type.name()+log_suffix+"_kitchen_cabinet_shelf");
        translate("stripped_"+type.name()+log_suffix+"_kitchen_shelf");

        translate(type.name()+"_table");
        translate(type.name()+log_suffix+"_table");
        translate("stripped_"+type.name()+log_suffix+"_table");

        translate(type.name()+"_bedside_table");
        translate(type.name()+log_suffix+"_bedside_table");
        translate("stripped_"+type.name()+log_suffix+"_bedside_table");

        translate(type.name()+"_closet");
        translate(type.name()+log_suffix+"_closet");
        translate("stripped_"+type.name()+log_suffix+"_closet");

        translate(type.name()+"_flower_box");
        translate(type.name()+log_suffix+"_flower_box");
        translate("stripped_"+type.name()+log_suffix+"_flower_box");

        translate(type.name()+"_flower_box_inner_corner");
        translate(type.name()+log_suffix+"_flower_box_inner_corner");
        translate("stripped_"+type.name()+log_suffix+"_flower_box_inner_corner");

        translate(type.name()+"_flower_box_outer_corner");
        translate(type.name()+log_suffix+"_flower_box_outer_corner");
        translate("stripped_"+type.name()+log_suffix+"_flower_box_outer_corner");

        translate(type.name()+"_flower_box_big");
        translate(type.name()+log_suffix+"_flower_box_big");
        translate("stripped_"+type.name()+log_suffix+"_flower_box_big");

        translate(type.name()+"_trash_bin");
        translate(type.name()+log_suffix+"_trash_bin");
        translate("stripped_"+type.name()+log_suffix+"_trash_bin");

        for (WoodType type2 : WOOD_TYPES) {
            String log_suffix2;
            if (type2 == WoodType.CRIMSON || type2 == WoodType.WARPED) {
                log_suffix2 = "_stem";
            } else if (type2 == WoodType.BAMBOO) {
                log_suffix2 = "_block";
            } else {
                log_suffix2 = "_log";
            }

            translate(type.name()+"_"+type2.name()+"_birdhouse");
            translate(type.name()+log_suffix+"_"+type2.name()+"_birdhouse");
            translate("stripped_"+type.name()+log_suffix+"_"+type2.name()+"_birdhouse");
            translate(type.name()+"_"+type2.name()+log_suffix2+"_birdhouse");
            translate(type.name()+log_suffix+"_"+type2.name()+log_suffix2+"_birdhouse");
            translate("stripped_"+type.name()+log_suffix+"_"+type2.name()+log_suffix2+"_birdhouse");
            translate(type.name()+"_stripped_"+type2.name()+log_suffix2+"_birdhouse");
            translate(type.name()+log_suffix+"_stripped_"+type2.name()+log_suffix2+"_birdhouse");
            translate("stripped_"+type.name()+log_suffix+"_stripped_"+type2.name()+log_suffix2+"_birdhouse");

            translate("hanging_"+type.name()+"_"+type2.name()+"_birdhouse");
            translate("hanging_"+type.name()+log_suffix+"_"+type2.name()+"_birdhouse");
            translate("hanging_stripped_"+type.name()+log_suffix+"_"+type2.name()+"_birdhouse");
            translate("hanging_"+type.name()+"_"+type2.name()+log_suffix2+"_birdhouse");
            translate("hanging_"+type.name()+log_suffix+"_"+type2.name()+log_suffix2+"_birdhouse");
            translate("hanging_stripped_"+type.name()+log_suffix+"_"+type2.name()+log_suffix2+"_birdhouse");
            translate("hanging_"+type.name()+"_stripped_"+type2.name()+log_suffix2+"_birdhouse");
            translate("hanging_"+type.name()+log_suffix+"_stripped_"+type2.name()+log_suffix2+"_birdhouse");
            translate("hanging_stripped_"+type.name()+log_suffix+"_stripped_"+type2.name()+log_suffix2+"_birdhouse");
        }

        METALS.forEach((metal, name) -> {
            if (metal != Blocks.COPPER_BLOCK) {
                translate(type.name() + "_" + name + "_bench");
                translate(type.name() + log_suffix + "_" + name + "_bench");
                translate("stripped_" + type.name() + log_suffix + "_" + name + "_bench");
            } else {
                translate(type.name() + "_waxed_" + name + "_bench");
                translate(type.name() + log_suffix + "_waxed_" + name + "_bench");
                translate("stripped_" + type.name() + log_suffix + "_waxed_" + name + "_bench");
                translate(type.name() + "_waxed_exposed_" + name + "_bench");
                translate(type.name() + log_suffix + "_waxed_exposed_" + name + "_bench");
                translate("stripped_" + type.name() + log_suffix + "_waxed_exposed_" + name + "_bench");
                translate(type.name() + "_waxed_weathered_" + name + "_bench");
                translate(type.name() + log_suffix + "_waxed_weathered_" + name + "_bench");
                translate("stripped_" + type.name() + log_suffix + "_waxed_weathered_" + name + "_bench");
                translate(type.name() + "_waxed_oxidized_" + name + "_bench");
                translate(type.name() + log_suffix + "_waxed_oxidized_" + name + "_bench");
                translate("stripped_" + type.name() + log_suffix + "_waxed_oxidized_" + name + "_bench");

                translate(type.name() + "_" +  name + "_bench");
                translate(type.name() + log_suffix + "_" + name + "_bench");
                translate("stripped_" + type.name() + log_suffix + "_" + name + "_bench");
                translate(type.name() + "_exposed_" + name + "_bench");
                translate(type.name() + log_suffix + "_exposed_" + name + "_bench");
                translate("stripped_" + type.name() + log_suffix + "_exposed_" + name + "_bench");
                translate(type.name() + "_weathered_" + name + "_bench");
                translate(type.name() + log_suffix + "_weathered_" + name + "_bench");
                translate("stripped_" + type.name() + log_suffix + "_weathered_" + name + "_bench");
                translate(type.name() + "_oxidized_" + name + "_bench");
                translate(type.name() + log_suffix + "_oxidized_" + name + "_bench");
                translate("stripped_" + type.name() + log_suffix + "_oxidized_" + name + "_bench");
            }
        });

        WOOL_COLORS.forEach((block, color) -> BlockFamilies.getAllFamilies().toList().forEach(blockFamily -> {
            if (blockFamily.getBaseBlock() == planks) {
                translate(color+"_"+type.name()+"_chair");
                translate(color+"_"+type.name()+log_suffix+"_chair");
                translate(color+"_stripped_"+type.name()+log_suffix+"_chair");
                translate(color+"_"+type.name()+"_lamp");
                translate(color+"_"+type.name()+"_bed");
                translate(color+"_"+type.name()+log_suffix+"_bed");
                translate(color+"_stripped_"+type.name()+log_suffix+"_bed");
            }
        }));
    }

    private void translate(String name) {
        Block block = getBlockFromResourceLocation(FabulousFurniture.prefix(name));
        String wip = "";
        if (block instanceof BlockTagSupplier sup) {
            wip = sup.isWIP() ? "§4[Work in Progress]§r" : "";
        }
        add(block.asItem(), createTranslatedName(name) + " " + wip);
    }

    private String createTranslatedName(String name) {
        StringBuilder s = new StringBuilder();
        String[] split = name.split("_");
        for (String a : split) {
            a = a.replace('_', ' ');
            s.append(' ').append(a.substring(0, 1).toUpperCase()).append(a.substring(1));
        }
        return s.toString().replaceFirst(" ", "");
    }

    private Block getBlockFromResourceLocation(ResourceLocation location) {
        return BuiltInRegistries.BLOCK.getValue(location);
    }
}