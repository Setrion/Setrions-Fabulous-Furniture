package net.setrion.fabulous_furniture.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class SFFBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FabulousFurniture.MOD_ID);

    public static final Map<Block, String> COUNTER_TOPS = new HashMap<>(12);
    public static final Map<Block, String> WOOL_COLORS = new HashMap<>(12);

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

        WOOL_COLORS.put(Blocks.WHITE_WOOL, "white");
        WOOL_COLORS.put(Blocks.LIGHT_GRAY_WOOL, "light_gray");
        WOOL_COLORS.put(Blocks.GRAY_WOOL, "gray");
        WOOL_COLORS.put(Blocks.BLACK_WOOL, "black");
        WOOL_COLORS.put(Blocks.BROWN_WOOL, "brown");
        WOOL_COLORS.put(Blocks.RED_WOOL, "red");
        WOOL_COLORS.put(Blocks.ORANGE_WOOL, "orange");
        WOOL_COLORS.put(Blocks.YELLOW_WOOL, "yellow");
        WOOL_COLORS.put(Blocks.LIME_WOOL, "lime");
        WOOL_COLORS.put(Blocks.GREEN_WOOL, "green");
        WOOL_COLORS.put(Blocks.CYAN_WOOL, "cyan");
        WOOL_COLORS.put(Blocks.LIGHT_BLUE_WOOL, "light_blue");
        WOOL_COLORS.put(Blocks.BLUE_WOOL, "blue");
        WOOL_COLORS.put(Blocks.PURPLE_WOOL, "purple");
        WOOL_COLORS.put(Blocks.MAGENTA_WOOL, "magenta");
        WOOL_COLORS.put(Blocks.PINK_WOOL, "pink");
        for (WoodType type : WoodType.values().toList()) {
            registerBlockWithItem(type.name()+"_crate", CrateBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            COUNTER_TOPS.forEach(((block, suffix) -> {
                String top_name = block.getDescriptionId().replaceFirst("block.minecraft.", "").replaceFirst("quartz_block", "quartz");
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter", KitchenCounterBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter_inner_corner", KitchenCounterInnerCornerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter_outer_corner", KitchenCounterOuterCornerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter_open", KitchenCounterContainerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter_door", KitchenCounterContainerDoorBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter_glass_door", KitchenCounterContainerDoorBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter_small_drawer", KitchenCounterContainerDrawerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter_big_drawer", KitchenCounterContainerDrawerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter_sink", KitchenCounterSinkBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).randomTicks().pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter_smoker", KitchenCounterSmokerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_cabinet_door", KitchenCabinetContainerDoorBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_cabinet_glass_door", KitchenCabinetContainerDoorBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_cabinet_sideways_door", KitchenCabinetContainerSidewaysDoorBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_cabinet_sideways_glass_door", KitchenCabinetContainerSidewaysDoorBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_knife_block", properties -> new RotatableBlock(properties, new VoxelShape[] {Block.box(4, 0, 4, 12, 14, 12), Block.box(4, 0, 4, 12, 14, 12), Block.box(4, 0, 4, 12, 14, 12), Block.box(4, 0, 4, 12, 14, 12)}), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            }));
            registerBlockWithItem(type.name()+"_kitchen_cabinet", KitchenCabinetBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem(type.name()+"_kitchen_cabinet_inner_corner", KitchenCabinetInnerCornerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem(type.name()+"_kitchen_cabinet_outer_corner", KitchenCabinetOuterCornerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem(type.name()+"_kitchen_cabinet_open", KitchenCabinetContainerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
            registerBlockWithItem(type.name()+"_kitchen_cabinet_shelf", KitchenCabinetShelfBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
            WOOL_COLORS.forEach(((block, color) -> {
                String wool_name = block.getDescriptionId().replaceFirst("block.minecraft.", "");
                registerBlockWithItem(type.name()+"_"+wool_name+"_chair", ChairBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
                registerBlockWithItem(type.name()+"_"+color+"_table_lamp", LampBlock::new, () -> lampProperties(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            }));
            registerBlockWithItem(type.name()+"_table", TableBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
        }
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

    public static final DeferredBlock<Block> WHITE_IRON_CURTAINS = registerBlockWithItem("white_iron_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL).noOcclusion());
    public static final DeferredBlock<Block> WHITE_GOLD_CURTAINS = registerBlockWithItem("white_gold_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL).noOcclusion());
    public static final DeferredBlock<Block> WHITE_NETHERITE_CURTAINS = registerBlockWithItem("white_netherite_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL).noOcclusion());
    public static final DeferredBlock<Block> WHITE_COPPER_CURTAINS = registerBlockWithItem("white_copper_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL).noOcclusion());
    public static final DeferredBlock<Block> LIGHT_GRAY_IRON_CURTAINS = registerBlockWithItem("light_gray_iron_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT_GRAY_WOOL).noOcclusion());
    public static final DeferredBlock<Block> LIGHT_GRAY_GOLD_CURTAINS = registerBlockWithItem("light_gray_gold_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT_GRAY_WOOL).noOcclusion());
    public static final DeferredBlock<Block> LIGHT_GRAY_NETHERITE_CURTAINS = registerBlockWithItem("light_gray_netherite_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT_GRAY_WOOL).noOcclusion());
    public static final DeferredBlock<Block> LIGHT_GRAY_COPPER_CURTAINS = registerBlockWithItem("light_gray_copper_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT_GRAY_WOOL).noOcclusion());
    public static final DeferredBlock<Block> GRAY_IRON_CURTAINS = registerBlockWithItem("gray_iron_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.GRAY_WOOL).noOcclusion());
    public static final DeferredBlock<Block> GRAY_GOLD_CURTAINS = registerBlockWithItem("gray_gold_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.GRAY_WOOL).noOcclusion());
    public static final DeferredBlock<Block> GRAY_NETHERITE_CURTAINS = registerBlockWithItem("gray_netherite_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.GRAY_WOOL).noOcclusion());
    public static final DeferredBlock<Block> GRAY_COPPER_CURTAINS = registerBlockWithItem("gray_copper_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.GRAY_WOOL).noOcclusion());
    public static final DeferredBlock<Block> BLACK_IRON_CURTAINS = registerBlockWithItem("black_iron_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BLACK_WOOL).noOcclusion());
    public static final DeferredBlock<Block> BLACK_GOLD_CURTAINS = registerBlockWithItem("black_gold_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BLACK_WOOL).noOcclusion());
    public static final DeferredBlock<Block> BLACK_NETHERITE_CURTAINS = registerBlockWithItem("black_netherite_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BLACK_WOOL).noOcclusion());
    public static final DeferredBlock<Block> BLACK_COPPER_CURTAINS = registerBlockWithItem("black_copper_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BLACK_WOOL).noOcclusion());
    public static final DeferredBlock<Block> BROWN_IRON_CURTAINS = registerBlockWithItem("brown_iron_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_WOOL).noOcclusion());
    public static final DeferredBlock<Block> BROWN_GOLD_CURTAINS = registerBlockWithItem("brown_gold_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_WOOL).noOcclusion());
    public static final DeferredBlock<Block> BROWN_NETHERITE_CURTAINS = registerBlockWithItem("brown_netherite_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_WOOL).noOcclusion());
    public static final DeferredBlock<Block> BROWN_COPPER_CURTAINS = registerBlockWithItem("brown_copper_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_WOOL).noOcclusion());
    public static final DeferredBlock<Block> RED_IRON_CURTAINS = registerBlockWithItem("red_iron_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_WOOL).noOcclusion());
    public static final DeferredBlock<Block> RED_GOLD_CURTAINS = registerBlockWithItem("red_gold_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_WOOL).noOcclusion());
    public static final DeferredBlock<Block> RED_NETHERITE_CURTAINS = registerBlockWithItem("red_netherite_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_WOOL).noOcclusion());
    public static final DeferredBlock<Block> RED_COPPER_CURTAINS = registerBlockWithItem("red_copper_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RED_WOOL).noOcclusion());
    public static final DeferredBlock<Block> ORANGE_IRON_CURTAINS = registerBlockWithItem("orange_iron_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.ORANGE_WOOL).noOcclusion());
    public static final DeferredBlock<Block> ORANGE_GOLD_CURTAINS = registerBlockWithItem("orange_gold_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.ORANGE_WOOL).noOcclusion());
    public static final DeferredBlock<Block> ORANGE_NETHERITE_CURTAINS = registerBlockWithItem("orange_netherite_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.ORANGE_WOOL).noOcclusion());
    public static final DeferredBlock<Block> ORANGE_COPPER_CURTAINS = registerBlockWithItem("orange_copper_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.ORANGE_WOOL).noOcclusion());
    public static final DeferredBlock<Block> YELLOW_IRON_CURTAINS = registerBlockWithItem("yellow_iron_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.YELLOW_WOOL).noOcclusion());
    public static final DeferredBlock<Block> YELLOW_GOLD_CURTAINS = registerBlockWithItem("yellow_gold_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.YELLOW_WOOL).noOcclusion());
    public static final DeferredBlock<Block> YELLOW_NETHERITE_CURTAINS = registerBlockWithItem("yellow_netherite_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.YELLOW_WOOL).noOcclusion());
    public static final DeferredBlock<Block> YELLOW_COPPER_CURTAINS = registerBlockWithItem("yellow_copper_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.YELLOW_WOOL).noOcclusion());
    public static final DeferredBlock<Block> LIME_IRON_CURTAINS = registerBlockWithItem("lime_iron_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.LIME_WOOL).noOcclusion());
    public static final DeferredBlock<Block> LIME_GOLD_CURTAINS = registerBlockWithItem("lime_gold_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.LIME_WOOL).noOcclusion());
    public static final DeferredBlock<Block> LIME_NETHERITE_CURTAINS = registerBlockWithItem("lime_netherite_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.LIME_WOOL).noOcclusion());
    public static final DeferredBlock<Block> LIME_COPPER_CURTAINS = registerBlockWithItem("lime_copper_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.LIME_WOOL).noOcclusion());
    public static final DeferredBlock<Block> GREEN_IRON_CURTAINS = registerBlockWithItem("green_iron_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.GREEN_WOOL).noOcclusion());
    public static final DeferredBlock<Block> GREEN_GOLD_CURTAINS = registerBlockWithItem("green_gold_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.GREEN_WOOL).noOcclusion());
    public static final DeferredBlock<Block> GREEN_NETHERITE_CURTAINS = registerBlockWithItem("green_netherite_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.GREEN_WOOL).noOcclusion());
    public static final DeferredBlock<Block> GREEN_COPPER_CURTAINS = registerBlockWithItem("green_copper_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.GREEN_WOOL).noOcclusion());
    public static final DeferredBlock<Block> CYAN_IRON_CURTAINS = registerBlockWithItem("cyan_iron_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.CYAN_WOOL).noOcclusion());
    public static final DeferredBlock<Block> CYAN_GOLD_CURTAINS = registerBlockWithItem("cyan_gold_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.CYAN_WOOL).noOcclusion());
    public static final DeferredBlock<Block> CYAN_NETHERITE_CURTAINS = registerBlockWithItem("cyan_netherite_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.CYAN_WOOL).noOcclusion());
    public static final DeferredBlock<Block> CYAN_COPPER_CURTAINS = registerBlockWithItem("cyan_copper_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.CYAN_WOOL).noOcclusion());
    public static final DeferredBlock<Block> LIGHT_BLUE_IRON_CURTAINS = registerBlockWithItem("light_blue_iron_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT_BLUE_WOOL).noOcclusion());
    public static final DeferredBlock<Block> LIGHT_BLUE_GOLD_CURTAINS = registerBlockWithItem("light_blue_gold_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT_BLUE_WOOL).noOcclusion());
    public static final DeferredBlock<Block> LIGHT_BLUE_NETHERITE_CURTAINS = registerBlockWithItem("light_blue_netherite_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT_BLUE_WOOL).noOcclusion());
    public static final DeferredBlock<Block> LIGHT_BLUE_COPPER_CURTAINS = registerBlockWithItem("light_blue_copper_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT_BLUE_WOOL).noOcclusion());
    public static final DeferredBlock<Block> BLUE_IRON_CURTAINS = registerBlockWithItem("blue_iron_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_WOOL).noOcclusion());
    public static final DeferredBlock<Block> BLUE_GOLD_CURTAINS = registerBlockWithItem("blue_gold_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_WOOL).noOcclusion());
    public static final DeferredBlock<Block> BLUE_NETHERITE_CURTAINS = registerBlockWithItem("blue_netherite_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_WOOL).noOcclusion());
    public static final DeferredBlock<Block> BLUE_COPPER_CURTAINS = registerBlockWithItem("blue_copper_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_WOOL).noOcclusion());
    public static final DeferredBlock<Block> PURPLE_IRON_CURTAINS = registerBlockWithItem("purple_iron_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.PURPLE_WOOL).noOcclusion());
    public static final DeferredBlock<Block> PURPLE_GOLD_CURTAINS = registerBlockWithItem("purple_gold_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.PURPLE_WOOL).noOcclusion());
    public static final DeferredBlock<Block> PURPLE_NETHERITE_CURTAINS = registerBlockWithItem("purple_netherite_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.PURPLE_WOOL).noOcclusion());
    public static final DeferredBlock<Block> PURPLE_COPPER_CURTAINS = registerBlockWithItem("purple_copper_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.PURPLE_WOOL).noOcclusion());
    public static final DeferredBlock<Block> MAGENTA_IRON_CURTAINS = registerBlockWithItem("magenta_iron_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MAGENTA_WOOL).noOcclusion());
    public static final DeferredBlock<Block> MAGENTA_GOLD_CURTAINS = registerBlockWithItem("magenta_gold_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MAGENTA_WOOL).noOcclusion());
    public static final DeferredBlock<Block> MAGENTA_NETHERITE_CURTAINS = registerBlockWithItem("magenta_netherite_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MAGENTA_WOOL).noOcclusion());
    public static final DeferredBlock<Block> MAGENTA_COPPER_CURTAINS = registerBlockWithItem("magenta_copper_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MAGENTA_WOOL).noOcclusion());
    public static final DeferredBlock<Block> PINK_IRON_CURTAINS = registerBlockWithItem("pink_iron_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_WOOL).noOcclusion());
    public static final DeferredBlock<Block> PINK_GOLD_CURTAINS = registerBlockWithItem("pink_gold_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_WOOL).noOcclusion());
    public static final DeferredBlock<Block> PINK_NETHERITE_CURTAINS = registerBlockWithItem("pink_netherite_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_WOOL).noOcclusion());
    public static final DeferredBlock<Block> PINK_COPPER_CURTAINS = registerBlockWithItem("pink_copper_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_WOOL).noOcclusion());

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
        return BlockBehaviour.Properties.ofFullCopy(block).lightLevel((state) -> state.getValue(LampBlock.ON) ? 15 : 0);
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