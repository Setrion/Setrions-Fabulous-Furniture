package net.setrion.fabulous_furniture.data;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.setrion.fabulous_furniture.FabulousFurniture;
import static net.setrion.fabulous_furniture.registry.SFFBlocks.*;

public class LangGenerator extends LanguageProvider {

    public LangGenerator(PackOutput output) {
        super(output, FabulousFurniture.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        translateCurtains();
        WoodType.values().toList().forEach(this::translateWoodenFurniture);

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
    }

    private void translateCurtains() {
        WOOL_COLORS.forEach((wool, color) -> {
            CURTAIN_RODS.forEach((rod, name) -> {
                translate(color+"_"+name+"_curtains");
            });
        });
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
        COUNTER_TOPS.forEach(((block, s) -> {
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
        add(getBlockFromResourceLocation(FabulousFurniture.prefix(name)).asItem(), createTranslatedName(name));
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