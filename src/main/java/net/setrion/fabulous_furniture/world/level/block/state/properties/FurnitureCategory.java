package net.setrion.fabulous_furniture.world.level.block.state.properties;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.setrion.fabulous_furniture.FabulousFurniture;

import java.util.Map;
import java.util.stream.Stream;

public record FurnitureCategory(String name, Item item) {
    private static final Map<String, FurnitureCategory> TYPES = new Object2ObjectArrayMap();
    public static final Codec<FurnitureCategory> CODEC;

    public static final FurnitureCategory CRATES;
    public static final FurnitureCategory CURTAINS;
    public static final FurnitureCategory FLOOR;
    public static final FurnitureCategory FRIDGES;
    public static final FurnitureCategory KITCHEN_COUNTERS;
    public static final FurnitureCategory KITCHEN_CABINETS;
    public static final FurnitureCategory KITCHEN_MISC;
    public static final FurnitureCategory KITCHEN_SHELFS;
    public static final FurnitureCategory CHAIRS;
    public static final FurnitureCategory TABLES;
    public static final FurnitureCategory LAMPS;
    public static final FurnitureCategory BEDS;
    public static final FurnitureCategory BEDSIDE_TABLES;
    public static final FurnitureCategory CLOSETS;

    public FurnitureCategory(String name, Item item) {
        this.name = name;
        this.item = item;
    }

    public static FurnitureCategory register(FurnitureCategory type) {
        TYPES.put(type.name(), type);
        return type;
    }

    public static Stream<FurnitureCategory> values() {
        return TYPES.values().stream();
    }

    public String name() {
        return name;
    }

    public Item item() {
        return item;
    }

    public Component getTranslatedName() {
        return Component.translatable(getTranslatableName());
    }

    public String getTranslatableName() {
        return "category."+name+".name";
    }

    static {
        CODEC = Codec.stringResolver(FurnitureCategory::name, TYPES::get);
        CRATES = register(new FurnitureCategory("crates", BuiltInRegistries.BLOCK.getValue(FabulousFurniture.prefix("oak_crate")).asItem()));
        CURTAINS = register(new FurnitureCategory("curtains", BuiltInRegistries.BLOCK.getValue(FabulousFurniture.prefix("white_copper_curtains")).asItem()));
        FLOOR = register(new FurnitureCategory("floor", BuiltInRegistries.BLOCK.getValue(FabulousFurniture.prefix("white_light_gray_kitchen_tiles")).asItem()));
        FRIDGES = register(new FurnitureCategory("fridges", BuiltInRegistries.BLOCK.getValue(FabulousFurniture.prefix("iron_fridge")).asItem()));
        KITCHEN_COUNTERS = register(new FurnitureCategory("kitchen_counters", BuiltInRegistries.BLOCK.getValue(FabulousFurniture.prefix("oak_polished_tuff_kitchen_counter")).asItem()));
        KITCHEN_CABINETS = register(new FurnitureCategory("kitchen_cabinets", BuiltInRegistries.BLOCK.getValue(FabulousFurniture.prefix("oak_kitchen_cabinet")).asItem()));
        KITCHEN_MISC = register(new FurnitureCategory("kitchen_misc", BuiltInRegistries.BLOCK.getValue(FabulousFurniture.prefix("oak_polished_tuff_knife_block")).asItem()));
        KITCHEN_SHELFS = register(new FurnitureCategory("kitchen_shelfs", BuiltInRegistries.BLOCK.getValue(FabulousFurniture.prefix("oak_kitchen_shelf")).asItem()));
        CHAIRS = register(new FurnitureCategory("chairs", BuiltInRegistries.BLOCK.getValue(FabulousFurniture.prefix("white_oak_chair")).asItem()));
        TABLES = register(new FurnitureCategory("tables", BuiltInRegistries.BLOCK.getValue(FabulousFurniture.prefix("oak_table")).asItem()));
        LAMPS = register(new FurnitureCategory("lamps", BuiltInRegistries.BLOCK.getValue(FabulousFurniture.prefix("white_oak_lamp")).asItem()));
        BEDS = register(new FurnitureCategory("beds", BuiltInRegistries.BLOCK.getValue(FabulousFurniture.prefix("white_oak_bed")).asItem()));
        BEDSIDE_TABLES = register(new FurnitureCategory("bedside_tables", BuiltInRegistries.BLOCK.getValue(FabulousFurniture.prefix("oak_bedside_table")).asItem()));
        CLOSETS = register(new FurnitureCategory("closets", BuiltInRegistries.BLOCK.getValue(FabulousFurniture.prefix("oak_closet")).asItem()));
    }
}