package net.setrion.fabulous_furniture.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.setrion.fabulous_furniture.FabulousFurniture;
import net.setrion.fabulous_furniture.world.level.block.*;
import net.setrion.fabulous_furniture.world.level.block.state.properties.LampPart;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class SFFBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FabulousFurniture.MOD_ID);

    public static final Map<Block, String> COUNTER_TOPS = new HashMap<>(12);
    public static final Map<Block, DyeColor> WOOL_COLORS = new HashMap<>(12);
    public static final Map<Block, String> CURTAIN_RODS = new HashMap<>(12);

    static {
        COUNTER_TOPS.put(Blocks.POLISHED_GRANITE, "");
        COUNTER_TOPS.put(Blocks.POLISHED_DIORITE, "");
        COUNTER_TOPS.put(Blocks.POLISHED_ANDESITE, "");
        COUNTER_TOPS.put(Blocks.POLISHED_DEEPSLATE, "");
        COUNTER_TOPS.put(Blocks.POLISHED_TUFF, "");
        COUNTER_TOPS.put(Blocks.SANDSTONE, "_top");
        COUNTER_TOPS.put(Blocks.RED_SANDSTONE, "_top");
        COUNTER_TOPS.put(Blocks.POLISHED_BLACKSTONE, "");
        COUNTER_TOPS.put(Blocks.GILDED_BLACKSTONE, "");
        COUNTER_TOPS.put(Blocks.SMOOTH_BASALT, "");
        COUNTER_TOPS.put(Blocks.QUARTZ_BLOCK, "_top");
        COUNTER_TOPS.put(Blocks.CALCITE, "");

        WOOL_COLORS.put(Blocks.WHITE_WOOL, DyeColor.WHITE);
        WOOL_COLORS.put(Blocks.LIGHT_GRAY_WOOL, DyeColor.LIGHT_GRAY);
        WOOL_COLORS.put(Blocks.GRAY_WOOL, DyeColor.GRAY);
        WOOL_COLORS.put(Blocks.BLACK_WOOL, DyeColor.BLACK);
        WOOL_COLORS.put(Blocks.BROWN_WOOL, DyeColor.BROWN);
        WOOL_COLORS.put(Blocks.RED_WOOL, DyeColor.RED);
        WOOL_COLORS.put(Blocks.ORANGE_WOOL, DyeColor.ORANGE);
        WOOL_COLORS.put(Blocks.YELLOW_WOOL, DyeColor.YELLOW);
        WOOL_COLORS.put(Blocks.LIME_WOOL, DyeColor.LIME);
        WOOL_COLORS.put(Blocks.GREEN_WOOL, DyeColor.GREEN);
        WOOL_COLORS.put(Blocks.CYAN_WOOL, DyeColor.CYAN);
        WOOL_COLORS.put(Blocks.LIGHT_BLUE_WOOL, DyeColor.LIGHT_BLUE);
        WOOL_COLORS.put(Blocks.BLUE_WOOL, DyeColor.BLUE);
        WOOL_COLORS.put(Blocks.PURPLE_WOOL, DyeColor.PURPLE);
        WOOL_COLORS.put(Blocks.MAGENTA_WOOL, DyeColor.MAGENTA);
        WOOL_COLORS.put(Blocks.PINK_WOOL, DyeColor.PINK);

        CURTAIN_RODS.put(Blocks.COPPER_BLOCK, "copper");
        CURTAIN_RODS.put(Blocks.IRON_BLOCK, "iron");
        CURTAIN_RODS.put(Blocks.GOLD_BLOCK, "gold");
        CURTAIN_RODS.put(Blocks.NETHERITE_BLOCK, "netherite");
        for (WoodType type : WoodType.values().toList()) {
            String log_suffix;
            if (type == WoodType.CRIMSON || type == WoodType.WARPED) {
                log_suffix = "_stem";
            } else if (type == WoodType.BAMBOO) {
                log_suffix = "_block";
            } else {
                log_suffix = "_log";
            }
            registerBlockWithItem(type.name()+"_crate", CrateBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            COUNTER_TOPS.forEach(((block, suffix) -> {
                String top_name = block.getDescriptionId().replaceFirst("block.minecraft.", "").replaceFirst("quartz_block", "quartz");
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter", KitchenCounterBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter_shelf", KitchenCounterContainerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter_door", KitchenCounterContainerDoorBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter_small_drawer", KitchenCounterContainerDrawerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter_big_drawer", KitchenCounterContainerDrawerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter_sink", KitchenCounterSinkBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).randomTicks().pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter_smoker", KitchenCounterSmokerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_cabinet_door", KitchenCabinetContainerDoorBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_cabinet_glass_door", KitchenCabinetContainerDoorBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_cabinet_sideways_door", KitchenCabinetContainerSidewaysDoorBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_cabinet_sideways_glass_door", KitchenCabinetContainerSidewaysDoorBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));

                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_counter", KitchenCounterBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_shelf", KitchenCounterContainerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_door", KitchenCounterContainerDoorBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_small_drawer", KitchenCounterContainerDrawerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_big_drawer", KitchenCounterContainerDrawerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_sink", KitchenCounterSinkBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).randomTicks().pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_smoker", KitchenCounterSmokerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_door", KitchenCabinetContainerDoorBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_glass_door", KitchenCabinetContainerDoorBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_door", KitchenCabinetContainerSidewaysDoorBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_glass_door", KitchenCabinetContainerSidewaysDoorBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));

                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter", KitchenCounterBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_shelf", KitchenCounterContainerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_door", KitchenCounterContainerDoorBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_small_drawer", KitchenCounterContainerDrawerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_big_drawer", KitchenCounterContainerDrawerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_sink", KitchenCounterSinkBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).randomTicks().pushReaction(PushReaction.BLOCK));
                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_smoker", KitchenCounterSmokerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_door", KitchenCabinetContainerDoorBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_glass_door", KitchenCabinetContainerDoorBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_door", KitchenCabinetContainerSidewaysDoorBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_glass_door", KitchenCabinetContainerSidewaysDoorBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));

                registerBlockWithItem(type.name()+"_"+top_name+"_knife_block", properties -> new RotatableBlock(properties, new VoxelShape[] {Block.box(4, 0, 4, 12, 14, 12), Block.box(4, 0, 4, 12, 14, 12), Block.box(4, 0, 4, 12, 14, 12), Block.box(4, 0, 4, 12, 14, 12)}), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            }));

            registerBlockWithItem(type.name()+"_kitchen_cabinet", KitchenCabinetBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem(type.name()+"_kitchen_cabinet_shelf", KitchenCabinetContainerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
            registerBlockWithItem(type.name()+"_kitchen_shelf", KitchenCabinetShelfBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));

            registerBlockWithItem(type.name()+log_suffix+"_kitchen_cabinet", KitchenCabinetBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem(type.name()+log_suffix+"_kitchen_cabinet_shelf", KitchenCabinetContainerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
            registerBlockWithItem(type.name()+log_suffix+"_kitchen_shelf", KitchenCabinetShelfBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));

            registerBlockWithItem("stripped_"+type.name()+log_suffix+"_kitchen_cabinet", KitchenCabinetBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem("stripped_"+type.name()+log_suffix+"_kitchen_cabinet_shelf", KitchenCabinetContainerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
            registerBlockWithItem("stripped_"+type.name()+log_suffix+"_kitchen_shelf", KitchenCabinetShelfBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
            WOOL_COLORS.forEach(((block, color) -> {
                registerBlockWithItem(color.getName()+"_"+type.name()+"_chair", ChairBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
                registerBlockWithItem(color.getName()+"_"+type.name()+log_suffix+"_chair", ChairBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
                registerBlockWithItem(color.getName()+"_stripped_"+type.name()+log_suffix+"_chair", ChairBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
                registerBlockWithItem(color.getName()+"_"+type.name()+"_lamp", LampBlock::new, () -> lampProperties(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
                registerBlockWithItem(color.getName()+"_"+type.name()+"_bed", WoodenBedBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
                registerBlockWithItem(color.getName()+"_"+type.name()+log_suffix+"_bed", WoodenBedBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
                registerBlockWithItem(color.getName()+"_stripped_"+type.name()+log_suffix+"_bed", WoodenBedBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            }));
            registerBlockWithItem(type.name()+"_table", TableBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem(type.name()+log_suffix+"_table", TableBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem("stripped_"+type.name()+log_suffix+"_table", TableBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
        }


        WOOL_COLORS.forEach((block, color) -> {
            CURTAIN_RODS.forEach((rod, name) -> {
                registerBlockWithItem(color+"_"+name+"_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(block).noOcclusion());
            });
        });
    }

    public static final DeferredBlock<Block> IRON_FRIDGE = registerBlockWithItem("iron_fridge", KitchenFridgeBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).pushReaction(PushReaction.BLOCK));
    public static final DeferredBlock<Block> GOLD_FRIDGE = registerBlockWithItem("gold_fridge", KitchenFridgeBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).pushReaction(PushReaction.BLOCK));
    public static final DeferredBlock<Block> NETHERITE_FRIDGE = registerBlockWithItem("netherite_fridge", KitchenFridgeBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK).pushReaction(PushReaction.BLOCK));
    public static final DeferredBlock<Block> COPPER_FRIDGE = registerBlockWithItem("copper_fridge", properties -> new WeatheringKitchenFridgeBlock(WeatheringCopper.WeatherState.UNAFFECTED, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).pushReaction(PushReaction.BLOCK));
    public static final DeferredBlock<Block> EXPOSED_COPPER_FRIDGE = registerBlockWithItem("exposed_copper_fridge", properties -> new WeatheringKitchenFridgeBlock(WeatheringCopper.WeatherState.WEATHERED, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.EXPOSED_COPPER).pushReaction(PushReaction.BLOCK));
    public static final DeferredBlock<Block> WEATHERED_COPPER_FRIDGE = registerBlockWithItem("weathered_copper_fridge", properties -> new WeatheringKitchenFridgeBlock(WeatheringCopper.WeatherState.EXPOSED, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WEATHERED_COPPER).pushReaction(PushReaction.BLOCK));
    public static final DeferredBlock<Block> OXIDIZED_COPPER_FRIDGE = registerBlockWithItem("oxidized_copper_fridge", properties -> new WeatheringKitchenFridgeBlock(WeatheringCopper.WeatherState.OXIDIZED, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OXIDIZED_COPPER).pushReaction(PushReaction.BLOCK));
    public static final DeferredBlock<Block> WAXED_COPPER_FRIDGE = registerBlockWithItem("waxed_copper_fridge", KitchenFridgeBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WAXED_COPPER_BLOCK).pushReaction(PushReaction.BLOCK));
    public static final DeferredBlock<Block> WAXED_EXPOSED_COPPER_FRIDGE = registerBlockWithItem("waxed_exposed_copper_fridge", KitchenFridgeBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WAXED_EXPOSED_COPPER).pushReaction(PushReaction.BLOCK));
    public static final DeferredBlock<Block> WAXED_WEATHERED_COPPER_FRIDGE = registerBlockWithItem("waxed_weathered_copper_fridge", KitchenFridgeBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WAXED_WEATHERED_COPPER).pushReaction(PushReaction.BLOCK));
    public static final DeferredBlock<Block> WAXED_OXIDIZED_COPPER_FRIDGE = registerBlockWithItem("waxed_oxidized_copper_fridge", KitchenFridgeBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WAXED_OXIDIZED_COPPER).pushReaction(PushReaction.BLOCK));

    public static final DeferredBlock<Block> WHITE_LIGHT_GRAY_KITCHEN_TILES = registerBlockWithItem("white_light_gray_kitchen_tiles", Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_GRAY_KITCHEN_TILES = registerBlockWithItem("white_gray_kitchen_tiles", Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_BLACK_KITCHEN_TILES = registerBlockWithItem("white_black_kitchen_tiles", Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_BROWN_KITCHEN_TILES = registerBlockWithItem("white_brown_kitchen_tiles", Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_RED_KITCHEN_TILES = registerBlockWithItem("white_red_kitchen_tiles", Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_ORANGE_KITCHEN_TILES = registerBlockWithItem("white_orange_kitchen_tiles", Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_YELLOW_KITCHEN_TILES = registerBlockWithItem("white_yellow_kitchen_tiles", Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_LIME_KITCHEN_TILES = registerBlockWithItem("white_lime_kitchen_tiles", Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_GREEN_KITCHEN_TILES = registerBlockWithItem("white_green_kitchen_tiles", Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_CYAN_KITCHEN_TILES = registerBlockWithItem("white_cyan_kitchen_tiles", Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_LIGHT_BLUE_KITCHEN_TILES = registerBlockWithItem("white_light_blue_kitchen_tiles", Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_BLUE_KITCHEN_TILES = registerBlockWithItem("white_blue_kitchen_tiles", Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_PURPLE_KITCHEN_TILES = registerBlockWithItem("white_purple_kitchen_tiles", Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_MAGENTA_KITCHEN_TILES = registerBlockWithItem("white_magenta_kitchen_tiles", Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_PINK_KITCHEN_TILES = registerBlockWithItem("white_pink_kitchen_tiles", Block::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));

    private static BlockBehaviour.Properties lampProperties(Block block) {
        return BlockBehaviour.Properties.ofFullCopy(block).lightLevel((state) -> state.getValue(LampBlock.ON) && (state.getValue(LampBlock.PART) == LampPart.TOP || state.getValue(LampBlock.PART) == LampPart.SINGLE) ? 15 : 0);
    }
    
    public static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> block, Supplier<BlockBehaviour.Properties> properties) {
        return BLOCKS.register(name, () -> block.apply(properties.get().setId(ResourceKey.create(Registries.BLOCK, FabulousFurniture.prefix(name)))));
    }

    public static <T extends Block> DeferredBlock<T> registerBlockWithItem(String name, Function<BlockBehaviour.Properties, T> block, Supplier<BlockBehaviour.Properties> properties) {
        DeferredBlock<T> deferredBlock = BLOCKS.register(name, () -> block.apply(properties.get().setId(ResourceKey.create(Registries.BLOCK, FabulousFurniture.prefix(name)))));
        SFFItems.registerItem(name, itemProps -> new BlockItem(deferredBlock.get(), itemProps), new Item.Properties());
        return deferredBlock;
    }
}