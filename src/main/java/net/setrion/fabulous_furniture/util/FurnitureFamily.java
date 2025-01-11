package net.setrion.fabulous_furniture.util;

import com.google.common.collect.Maps;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;

public class FurnitureFamily {
    private final Block basePlanks;
    private final Block baseLog;
    private final Block baseStrippedLog;
    final Map<Variant, Map<Block, Block>> variants = Maps.newHashMap();

    FurnitureFamily(Block basePlanks, Block baseLog, Block baseStrippedLog) {
        this.basePlanks = basePlanks;
        this.baseLog = baseLog;
        this.baseStrippedLog = baseStrippedLog;
    }

    public Block getBasePlanks() {
        return basePlanks;
    }

    public Block getBaseLog() {
        return baseLog;
    }

    public Block getBaseStrippedLog() {
        return baseStrippedLog;
    }

    public Map<Variant, Map<Block, Block>> getVariants() {
        return this.variants;
    }

    public Map<Block, Block> get(Variant variant) {
        return this.variants.get(variant);
    }

    public static enum Variant {
        CRATE("crate"),
        KITCHEN_COUNTER("kitchen_counter"),
        KITCHEN_COUNTER_INNER_CORNER("kitchen_counter_inner_corner"),
        KITCHEN_COUNTER_OUTER_CORNER("kitchen_counter_outer_corner"),
        KITCHEN_COUNTER_OPEN("kitchen_counter_open"),
        KITCHEN_COUNTER_DOOR("kitchen_counter_door"),
        KITCHEN_COUNTER_GLASS_DOOR("kitchen_counter_glass_door"),
        KITCHEN_COUNTER_SMALL_DRAWER("kitchen_counter_small_drawer"),
        KITCHEN_COUNTER_BIG_DRAWER("kitchen_counter_big_drawer"),
        KITCHEN_COUNTER_SINK("kitchen_counter_sink"),
        KITCHEN_COUNTER_SMOKER("kitchen_counter_smoker"),
        KITCHEN_CABINET("kitchen_cabinet"),
        KITCHEN_CABINET_INNER_CORNER("kitchen_cabinet_inner_corner"),
        KITCHEN_CABINET_OUTER_CORNER("kitchen_cabinet_outer_corner"),
        KITCHEN_CABINET_OPEN("kitchen_cabinet_open"),
        KITCHEN_CABINET_SHELF("kitchen_cabinet_shelf"),
        KITCHEN_CABINET_DOOR("kitchen_cabinet_door"),
        KITCHEN_CABINET_GLASS_DOOR("kitchen_cabinet_glass_door"),
        KITCHEN_CABINET_SIDEWAYS_DOOR("kitchen_cabinet_sideways_door"),
        KITCHEN_CABINET_SIDEWAYS_GLASS_DOOR("kitchen_cabinet_sideways_glass_door"),
        KNIFE_BLOCK("knife_block"),
        CHAIR("chair"),
        TABLE("table"),
        TABLE_LAMP("table_lamp");

        private Variant(String variantName) {
        }
    }

    public static class Builder {
        private final FurnitureFamily family;

        public Builder(Block basePlanks, Block baseLog, Block baseStrippedLog) {
            this.family = new FurnitureFamily(basePlanks, baseLog, baseStrippedLog);
        }

        public FurnitureFamily getFamily() {
            return this.family;
        }

        public FurnitureFamily.Builder crate(Block block) {
            Map<Block, Block> map = getOrCreateMap(Variant.CRATE);
            map.put(Blocks.AIR, block);
            this.family.variants.put(Variant.CRATE, map);
            return this;
        }

        public FurnitureFamily.Builder kitchenCounter(Block block, Block top) {
            Map<Block, Block> map = getOrCreateMap(Variant.KITCHEN_COUNTER);
            map.put(top, block);
            this.family.variants.put(Variant.KITCHEN_COUNTER, map);
            return this;
        }

        public FurnitureFamily.Builder kitchenCounterInnerCorner(Block block, Block top) {
            Map<Block, Block> map = getOrCreateMap(Variant.KITCHEN_COUNTER_INNER_CORNER);
            map.put(top, block);
            this.family.variants.put(Variant.KITCHEN_COUNTER_INNER_CORNER, map);
            return this;
        }

        public FurnitureFamily.Builder kitchenCounterOuterCorner(Block block, Block top) {
            Map<Block, Block> map = getOrCreateMap(Variant.KITCHEN_COUNTER_OUTER_CORNER);
            map.put(top, block);
            this.family.variants.put(Variant.KITCHEN_COUNTER_OUTER_CORNER, map);
            return this;
        }

        public FurnitureFamily.Builder kitchenCounterOpen(Block block, Block top) {
            Map<Block, Block> map = getOrCreateMap(Variant.KITCHEN_COUNTER_OPEN);
            map.put(top, block);
            this.family.variants.put(Variant.KITCHEN_COUNTER_OPEN, map);
            return this;
        }

        public FurnitureFamily.Builder kitchenCounterDoor(Block block, Block top) {
            Map<Block, Block> map = getOrCreateMap(Variant.KITCHEN_COUNTER_DOOR);
            map.put(top, block);
            this.family.variants.put(Variant.KITCHEN_COUNTER_DOOR, map);
            return this;
        }

        public FurnitureFamily.Builder kitchenCounterGlassDoor(Block block, Block top) {
            Map<Block, Block> map = getOrCreateMap(Variant.KITCHEN_COUNTER_GLASS_DOOR);
            map.put(top, block);
            this.family.variants.put(Variant.KITCHEN_COUNTER_GLASS_DOOR, map);
            return this;
        }

        public FurnitureFamily.Builder kitchenCounterSmallDrawer(Block block, Block top) {
            Map<Block, Block> map = getOrCreateMap(Variant.KITCHEN_COUNTER_SMALL_DRAWER);
            map.put(top, block);
            this.family.variants.put(Variant.KITCHEN_COUNTER_SMALL_DRAWER, map);
            return this;
        }

        public FurnitureFamily.Builder kitchenCounterBigDrawer(Block block, Block top) {
            Map<Block, Block> map = getOrCreateMap(Variant.KITCHEN_COUNTER_BIG_DRAWER);
            map.put(top, block);
            this.family.variants.put(Variant.KITCHEN_COUNTER_BIG_DRAWER, map);
            return this;
        }

        public FurnitureFamily.Builder kitchenCounterSink(Block block, Block top) {
            Map<Block, Block> map = getOrCreateMap(Variant.KITCHEN_COUNTER_SINK);
            map.put(top, block);
            this.family.variants.put(Variant.KITCHEN_COUNTER_SINK, map);
            return this;
        }

        public FurnitureFamily.Builder kitchenCounterSmoker(Block block, Block top) {
            Map<Block, Block> map = getOrCreateMap(Variant.KITCHEN_COUNTER_SMOKER);
            map.put(top, block);
            this.family.variants.put(Variant.KITCHEN_COUNTER_SMOKER, map);
            return this;
        }

        public FurnitureFamily.Builder kitchenCabinet(Block block) {
            Map<Block, Block> map = getOrCreateMap(Variant.KITCHEN_CABINET);
            map.put(Blocks.AIR, block);
            this.family.variants.put(Variant.KITCHEN_CABINET, map);
            return this;
        }

        public FurnitureFamily.Builder kitchenCabinetInnerCorner(Block block) {
            Map<Block, Block> map = getOrCreateMap(Variant.KITCHEN_CABINET_INNER_CORNER);
            map.put(Blocks.AIR, block);
            this.family.variants.put(Variant.KITCHEN_CABINET_INNER_CORNER, map);
            return this;
        }

        public FurnitureFamily.Builder kitchenCabinetOuterCorner(Block block) {
            Map<Block, Block> map = getOrCreateMap(Variant.KITCHEN_CABINET_OUTER_CORNER);
            map.put(Blocks.AIR, block);
            this.family.variants.put(Variant.KITCHEN_CABINET_OUTER_CORNER, map);
            return this;
        }

        public FurnitureFamily.Builder kitchenCabinetOpen(Block block) {
            Map<Block, Block> map = getOrCreateMap(Variant.KITCHEN_CABINET_OPEN);
            map.put(Blocks.AIR, block);
            this.family.variants.put(Variant.KITCHEN_CABINET_OPEN, map);
            return this;
        }

        public FurnitureFamily.Builder kitchenCabinetShelf(Block block) {
            Map<Block, Block> map = getOrCreateMap(Variant.KITCHEN_CABINET_SHELF);
            map.put(Blocks.AIR, block);
            this.family.variants.put(Variant.KITCHEN_CABINET_SHELF, map);
            return this;
        }

        public FurnitureFamily.Builder kitchenCabinetDoor(Block block, Block handle) {
            Map<Block, Block> map = getOrCreateMap(Variant.KITCHEN_CABINET_DOOR);
            map.put(handle, block);
            this.family.variants.put(Variant.KITCHEN_CABINET_DOOR, map);
            return this;
        }

        public FurnitureFamily.Builder kitchenCabinetGlassDoor(Block block, Block handle) {
            Map<Block, Block> map = getOrCreateMap(Variant.KITCHEN_CABINET_GLASS_DOOR);
            map.put(handle, block);
            this.family.variants.put(Variant.KITCHEN_CABINET_GLASS_DOOR, map);
            return this;
        }

        public FurnitureFamily.Builder kitchenCabinetSidewaysDoor(Block block, Block handle) {
            Map<Block, Block> map = getOrCreateMap(Variant.KITCHEN_CABINET_SIDEWAYS_DOOR);
            map.put(handle, block);
            this.family.variants.put(Variant.KITCHEN_CABINET_SIDEWAYS_DOOR, map);
            return this;
        }

        public FurnitureFamily.Builder kitchenCabinetSidewaysGlassDoor(Block block, Block handle) {
            Map<Block, Block> map = getOrCreateMap(Variant.KITCHEN_CABINET_SIDEWAYS_GLASS_DOOR);
            map.put(handle, block);
            this.family.variants.put(Variant.KITCHEN_CABINET_SIDEWAYS_GLASS_DOOR, map);
            return this;
        }

        public FurnitureFamily.Builder knifeBlock(Block block, Block top) {
            Map<Block, Block> map = getOrCreateMap(Variant.KNIFE_BLOCK);
            map.put(top, block);
            this.family.variants.put(Variant.KNIFE_BLOCK, map);
            return this;
        }

        public FurnitureFamily.Builder chair(Block block, Block wool) {
            Map<Block, Block> map = getOrCreateMap(Variant.CHAIR);
            map.put(wool, block);
            this.family.variants.put(Variant.CHAIR, map);
            return this;
        }

        public FurnitureFamily.Builder table(Block block) {
            Map<Block, Block> map = getOrCreateMap(Variant.TABLE);
            map.put(Blocks.AIR, block);
            this.family.variants.put(Variant.TABLE, map);
            return this;
        }

        public FurnitureFamily.Builder table_lamp(Block block, Block wool) {
            Map<Block, Block> map = getOrCreateMap(Variant.TABLE_LAMP);
            map.put(wool, block);
            this.family.variants.put(Variant.TABLE_LAMP, map);
            return this;
        }

        private Map<Block, Block> getOrCreateMap(Variant variant) {
            Map<Block, Block> map;
            if (family.get(variant) != null) {
                map = family.get(variant);
            } else {
                map = Maps.newHashMap();
            }
            return map;
        }
    }
}