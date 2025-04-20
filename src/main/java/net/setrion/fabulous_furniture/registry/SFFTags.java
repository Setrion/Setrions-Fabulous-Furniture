package net.setrion.fabulous_furniture.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.setrion.fabulous_furniture.FabulousFurniture;

public class SFFTags {

    public static class Items {

    }

    public static class Blocks {
        public static final TagKey<Block> CRATES = createBlockKey(FabulousFurniture.prefix("crates"));
        public static final TagKey<Block> FRIDGES = createBlockKey(FabulousFurniture.prefix("fridges"));
        public static final TagKey<Block> COPPER_FRIDGES = createBlockKey(FabulousFurniture.prefix("copper_fridges"));
        public static final TagKey<Block> FLOWER_BOX_PLACABLES = createBlockKey(FabulousFurniture.prefix("flower_box_placables"));
    }

    public static TagKey<Item> createItemKey(ResourceLocation name) {
        return TagKey.create(Registries.ITEM, name);
    }

    public static TagKey<Block> createBlockKey(ResourceLocation name) {
        return TagKey.create(Registries.BLOCK, name);
    }
}