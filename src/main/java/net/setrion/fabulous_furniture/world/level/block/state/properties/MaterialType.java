package net.setrion.fabulous_furniture.world.level.block.state.properties;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;
import java.util.stream.Stream;

public record MaterialType(String name, Item item) {
    private static final Map<String, MaterialType> TYPES = new Object2ObjectArrayMap();
    public static final Codec<MaterialType> CODEC;
    public static final MaterialType OAK;
    public static final MaterialType SPRUCE;
    public static final MaterialType BIRCH;
    public static final MaterialType ACACIA;
    public static final MaterialType CHERRY;
    public static final MaterialType JUNGLE;
    public static final MaterialType DARK_OAK;
    public static final MaterialType PALE_OAK;
    public static final MaterialType CRIMSON;
    public static final MaterialType WARPED;
    public static final MaterialType MANGROVE;
    public static final MaterialType BAMBOO;
    public static final MaterialType WOOL;
    public static final MaterialType CONCRETE;
    public static final MaterialType GRANITE;
    public static final MaterialType DIORITE;
    public static final MaterialType ANDESITE;
    public static final MaterialType DEEPSLATE;
    public static final MaterialType TUFF;
    public static final MaterialType SANDSTONE;
    public static final MaterialType RED_SANDSTONE;
    public static final MaterialType BLACKSTONE;
    public static final MaterialType GILDED_BLACKSTONE;
    public static final MaterialType BASALT;
    public static final MaterialType QUARTZ;
    public static final MaterialType CALCITE;
    public static final MaterialType IRON;
    public static final MaterialType COPPER;
    public static final MaterialType GOLD;
    public static final MaterialType NETHERITE;

    public MaterialType(String name, Item item) {
        this.name = name;
        this.item = item;
    }

    public static MaterialType register(MaterialType type) {
        TYPES.put(type.name(), type);
        return type;
    }

    public static Stream<MaterialType> values() {
        return TYPES.values().stream();
    }

    public String name() {
        return name;
    }

    public Item item() {
        return item;
    }

    public Component getTranslatedName() {
        return Component.translatable("material."+name+".name");
    }

    static {
        CODEC = Codec.stringResolver(MaterialType::name, TYPES::get);
        OAK = register(new MaterialType("oak", Blocks.OAK_PLANKS.asItem()));
        SPRUCE = register(new MaterialType("spruce", Blocks.SPRUCE_PLANKS.asItem()));
        BIRCH = register(new MaterialType("birch", Blocks.BIRCH_PLANKS.asItem()));
        ACACIA = register(new MaterialType("acacia", Blocks.ACACIA_PLANKS.asItem()));
        CHERRY = register(new MaterialType("cherry", Blocks.CHERRY_PLANKS.asItem()));
        JUNGLE = register(new MaterialType("jungle", Blocks.JUNGLE_PLANKS.asItem()));
        DARK_OAK = register(new MaterialType("dark_oak", Blocks.DARK_OAK_PLANKS.asItem()));
        PALE_OAK = register(new MaterialType("pale_oak", Blocks.PALE_OAK_PLANKS.asItem()));
        CRIMSON = register(new MaterialType("crimson", Blocks.CRIMSON_PLANKS.asItem()));
        WARPED = register(new MaterialType("warped", Blocks.WARPED_PLANKS.asItem()));
        MANGROVE = register(new MaterialType("mangrove", Blocks.MANGROVE_PLANKS.asItem()));
        BAMBOO = register(new MaterialType("bamboo", Blocks.BAMBOO_PLANKS.asItem()));
        WOOL = register(new MaterialType("wool", Blocks.WHITE_WOOL.asItem()));
        CONCRETE = register(new MaterialType("concrete", Blocks.WHITE_CONCRETE.asItem()));
        GRANITE = register(new MaterialType("granite", Blocks.POLISHED_GRANITE.asItem()));
        DIORITE = register(new MaterialType("diorite", Blocks.POLISHED_DIORITE.asItem()));
        ANDESITE = register(new MaterialType("andesite", Blocks.POLISHED_ANDESITE.asItem()));
        DEEPSLATE = register(new MaterialType("deepslate", Blocks.POLISHED_DEEPSLATE.asItem()));
        TUFF = register(new MaterialType("tuff", Blocks.POLISHED_TUFF.asItem()));
        SANDSTONE = register(new MaterialType("sandstone", Blocks.SANDSTONE.asItem()));
        RED_SANDSTONE = register(new MaterialType("red_sandstone", Blocks.RED_SANDSTONE.asItem()));
        BLACKSTONE = register(new MaterialType("blackstone", Blocks.POLISHED_BLACKSTONE.asItem()));
        GILDED_BLACKSTONE = register(new MaterialType("gilded_blackstone", Blocks.GILDED_BLACKSTONE.asItem()));
        BASALT = register(new MaterialType("basalt", Blocks.SMOOTH_BASALT.asItem()));
        QUARTZ = register(new MaterialType("quartz", Blocks.QUARTZ_BLOCK.asItem()));
        CALCITE = register(new MaterialType("calcite", Blocks.CALCITE.asItem()));
        IRON = register(new MaterialType("iron", Blocks.IRON_BLOCK.asItem()));
        COPPER = register(new MaterialType("copper", Blocks.COPPER_BLOCK.asItem()));
        GOLD = register(new MaterialType("gold", Blocks.GOLD_BLOCK.asItem()));
        NETHERITE = register(new MaterialType("netherite", Blocks.NETHERITE_BLOCK.asItem()));
    }
}