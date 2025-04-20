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
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.setrion.fabulous_furniture.FabulousFurniture;
import net.setrion.fabulous_furniture.world.level.block.*;
import net.setrion.fabulous_furniture.world.level.block.state.properties.LampPart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class SFFBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FabulousFurniture.MOD_ID);

    public static final DeferredBlock<CarpentryTableBlock> CARPENTRY_TABLE;

    public static final List<WoodType> WOOD_TYPES = new ArrayList<>();
    public static final Map<Block, String> STONE_MATERIALS = new HashMap<>();
    public static final Map<Block, DyeColor> WOOL_COLORS = new HashMap<>();
    public static final Map<Block, String> METALS = new HashMap<>();
    public static final Map<Block, String> TABLEWARE_MATERIALS = new HashMap<>();

    static {
        WOOD_TYPES.add(0, WoodType.OAK);
        WOOD_TYPES.add(1, WoodType.SPRUCE);
        WOOD_TYPES.add(2, WoodType.BIRCH);
        WOOD_TYPES.add(3, WoodType.ACACIA);
        WOOD_TYPES.add(4, WoodType.CHERRY);
        WOOD_TYPES.add(5, WoodType.JUNGLE);
        WOOD_TYPES.add(6, WoodType.DARK_OAK);
        WOOD_TYPES.add(7, WoodType.PALE_OAK);
        WOOD_TYPES.add(8, WoodType.CRIMSON);
        WOOD_TYPES.add(9, WoodType.WARPED);
        WOOD_TYPES.add(10, WoodType.MANGROVE);
        WOOD_TYPES.add(11, WoodType.BAMBOO);

        STONE_MATERIALS.put(Blocks.COBBLESTONE, "");
        STONE_MATERIALS.put(Blocks.COBBLED_DEEPSLATE, "");
        STONE_MATERIALS.put(Blocks.ANDESITE, "");
        STONE_MATERIALS.put(Blocks.DIORITE, "");
        STONE_MATERIALS.put(Blocks.GRANITE, "");
        STONE_MATERIALS.put(Blocks.STONE, "");
        STONE_MATERIALS.put(Blocks.POLISHED_GRANITE, "");
        STONE_MATERIALS.put(Blocks.POLISHED_DIORITE, "");
        STONE_MATERIALS.put(Blocks.POLISHED_ANDESITE, "");
        STONE_MATERIALS.put(Blocks.POLISHED_DEEPSLATE, "");
        STONE_MATERIALS.put(Blocks.POLISHED_TUFF, "");
        STONE_MATERIALS.put(Blocks.SANDSTONE, "_top");
        STONE_MATERIALS.put(Blocks.RED_SANDSTONE, "_top");
        STONE_MATERIALS.put(Blocks.POLISHED_BLACKSTONE, "");
        STONE_MATERIALS.put(Blocks.GILDED_BLACKSTONE, "");
        STONE_MATERIALS.put(Blocks.SMOOTH_BASALT, "");
        STONE_MATERIALS.put(Blocks.QUARTZ_BLOCK, "_top");
        STONE_MATERIALS.put(Blocks.CALCITE, "");

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

        METALS.put(Blocks.COPPER_BLOCK, "copper");
        METALS.put(Blocks.IRON_BLOCK, "iron");
        METALS.put(Blocks.GOLD_BLOCK, "gold");
        METALS.put(Blocks.NETHERITE_BLOCK, "netherite");

        TABLEWARE_MATERIALS.put(Blocks.QUARTZ_BLOCK, "_top");
        TABLEWARE_MATERIALS.put(Blocks.TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.WHITE_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.LIGHT_GRAY_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.GRAY_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.BLACK_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.BROWN_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.RED_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.ORANGE_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.YELLOW_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.LIME_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.GREEN_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.CYAN_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.LIGHT_BLUE_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.BLUE_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.PURPLE_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.MAGENTA_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.PINK_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.WHITE_GLAZED_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.GRAY_GLAZED_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.BLACK_GLAZED_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.BROWN_GLAZED_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.RED_GLAZED_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.ORANGE_GLAZED_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.YELLOW_GLAZED_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.LIME_GLAZED_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.GREEN_GLAZED_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.CYAN_GLAZED_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.BLUE_GLAZED_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.PURPLE_GLAZED_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.MAGENTA_GLAZED_TERRACOTTA, "");
        TABLEWARE_MATERIALS.put(Blocks.PINK_GLAZED_TERRACOTTA, "");

        CARPENTRY_TABLE = registerBlockWithItem("carpentry_table", CarpentryTableBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE));
        for (WoodType type : WOOD_TYPES) {
            String log_suffix;
            if (type == WoodType.CRIMSON || type == WoodType.WARPED) {
                log_suffix = "_stem";
            } else if (type == WoodType.BAMBOO) {
                log_suffix = "_block";
            } else {
                log_suffix = "_log";
            }
            registerBlockWithItem(type.name()+"_crate", CrateBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            STONE_MATERIALS.forEach(((block, suffix) -> {
                String top_name = block.getDescriptionId().replaceFirst("block.minecraft.", "").replaceFirst("quartz_block", "quartz");
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter", KitchenCounterBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter_shelf", properties -> new KitchenCounterContainerBaseBlock(properties, false, false), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter_door", KitchenCounterOpenableHingeContainerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter_small_drawer", properties -> new KitchenCounterOpenableContainerBlock(properties, false), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter_big_drawer", properties -> new KitchenCounterOpenableContainerBlock(properties, false), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter_sink", KitchenCounterSinkBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).randomTicks().pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_counter_smoker", KitchenCounterSmokerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_cabinet_door", properties -> new KitchenCabinetOpenableContainerBlock(properties, false), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_cabinet_glass_door", properties -> new KitchenCabinetOpenableContainerBlock(properties, false), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_cabinet_sideways_door", KitchenCabinetOpenableHingeContainerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+"_"+top_name+"_kitchen_cabinet_sideways_glass_door", KitchenCabinetOpenableHingeContainerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));

                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_counter", KitchenCounterBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_shelf", properties -> new KitchenCounterContainerBaseBlock(properties, false, false), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_door", KitchenCounterOpenableHingeContainerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_small_drawer", properties -> new KitchenCounterOpenableContainerBlock(properties, false), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_big_drawer", properties -> new KitchenCounterOpenableContainerBlock(properties, false), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_sink", KitchenCounterSinkBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).randomTicks().pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_counter_smoker", KitchenCounterSmokerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_door", properties -> new KitchenCabinetOpenableContainerBlock(properties, false), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_glass_door", properties -> new KitchenCabinetOpenableContainerBlock(properties, false), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_door", KitchenCabinetOpenableHingeContainerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem(type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_glass_door", KitchenCabinetOpenableHingeContainerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));

                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter", KitchenCounterBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_shelf", properties -> new KitchenCounterContainerBaseBlock(properties, false, false), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_door", KitchenCounterOpenableHingeContainerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_small_drawer", properties -> new KitchenCounterOpenableContainerBlock(properties, false), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_big_drawer", properties -> new KitchenCounterOpenableContainerBlock(properties, false), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_sink", KitchenCounterSinkBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).randomTicks().pushReaction(PushReaction.BLOCK));
                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_counter_smoker", KitchenCounterSmokerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_door", properties -> new KitchenCabinetOpenableContainerBlock(properties, false), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_glass_door", properties -> new KitchenCabinetOpenableContainerBlock(properties, false), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_door", KitchenCabinetOpenableHingeContainerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
                registerBlockWithItem("stripped_"+type.name()+log_suffix+"_"+top_name+"_kitchen_cabinet_sideways_glass_door", KitchenCabinetOpenableHingeContainerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));

                registerBlockWithItem(type.name()+"_"+top_name+"_knife_block", properties -> new RotatableBlock(properties, Block.box(4, 0, 4, 12, 14, 12)), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            }));

            registerBlockWithItem(type.name()+"_kitchen_cabinet", KitchenCabinetBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem(type.name()+"_kitchen_cabinet_shelf", properties -> new KitchenCabinetContainerBaseBlock(properties, false, false), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
            registerBlockWithItem(type.name()+"_kitchen_shelf", KitchenShelfBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));

            registerBlockWithItem(type.name()+log_suffix+"_kitchen_cabinet", KitchenCabinetBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem(type.name()+log_suffix+"_kitchen_cabinet_shelf", properties -> new KitchenCabinetContainerBaseBlock(properties, false, false), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
            registerBlockWithItem(type.name()+log_suffix+"_kitchen_shelf", KitchenShelfBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));

            registerBlockWithItem("stripped_"+type.name()+log_suffix+"_kitchen_cabinet", KitchenCabinetBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem("stripped_"+type.name()+log_suffix+"_kitchen_cabinet_shelf", properties -> new KitchenCabinetContainerBaseBlock(properties, false, false), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));
            registerBlockWithItem("stripped_"+type.name()+log_suffix+"_kitchen_shelf", KitchenShelfBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).pushReaction(PushReaction.BLOCK));

            registerBlockWithItem(type.name()+"_flower_box", FlowerBoxBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem(type.name()+log_suffix+"_flower_box", FlowerBoxBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem("stripped_"+type.name()+log_suffix+"_flower_box", FlowerBoxBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));

            registerBlockWithItem(type.name()+"_flower_box_corner", FlowerBoxCornerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem(type.name()+log_suffix+"_flower_box_corner", FlowerBoxCornerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem("stripped_"+type.name()+log_suffix+"_flower_box_corner", FlowerBoxCornerBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));

            registerBlockWithItem(type.name()+"_trash_bin", TrashBinBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem(type.name()+log_suffix+"_trash_bin", TrashBinBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem("stripped_"+type.name()+log_suffix+"_trash_bin", TrashBinBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));

            WOOL_COLORS.forEach(((block, color) -> {
                registerBlockWithItem(color.getName()+"_"+type.name()+"_chair", ChairBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
                registerBlockWithItem(color.getName()+"_"+type.name()+log_suffix+"_chair", ChairBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
                registerBlockWithItem(color.getName()+"_stripped_"+type.name()+log_suffix+"_chair", ChairBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
                registerBlockWithItem(color.getName()+"_"+type.name()+"_lamp", LampBlock::new, () -> lampProperties(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
                registerBlockWithItem(color.getName()+"_"+type.name()+"_bed", WoodenBedBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).noOcclusion());
                registerBlockWithItem(color.getName()+"_"+type.name()+log_suffix+"_bed", WoodenBedBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).noOcclusion());
                registerBlockWithItem(color.getName()+"_stripped_"+type.name()+log_suffix+"_bed", WoodenBedBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))).noOcclusion());
            }));

            registerBlockWithItem(type.name()+"_table", TableBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem(type.name()+log_suffix+"_table", TableBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem("stripped_"+type.name()+log_suffix+"_table", TableBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));

            registerBlockWithItem(type.name()+"_bedside_table", BedsideTableBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem(type.name()+log_suffix+"_bedside_table", BedsideTableBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem("stripped_"+type.name()+log_suffix+"_bedside_table", BedsideTableBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));

            registerBlockWithItem(type.name()+"_closet", ClosetBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem(type.name()+log_suffix+"_closet", ClosetBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));
            registerBlockWithItem("stripped_"+type.name()+log_suffix+"_closet", ClosetBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name()+"_planks"))));

            METALS.forEach((metal, name) -> {
                if (metal != Blocks.COPPER_BLOCK) {
                    registerBlockWithItem(type.name() + "_" + name + "_bench", BenchBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem(type.name() + log_suffix + "_" + name + "_bench", BenchBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem("stripped_" + type.name() + log_suffix + "_" + name + "_bench", BenchBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                } else {
                    registerBlockWithItem(type.name() + "_waxed_" + name + "_bench", BenchBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem(type.name() + log_suffix + "_waxed_" + name + "_bench", BenchBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem("stripped_" + type.name() + log_suffix + "_waxed_" + name + "_bench", BenchBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem(type.name() + "_waxed_exposed_" + name + "_bench", BenchBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem(type.name() + log_suffix + "_waxed_exposed_" + name + "_bench", BenchBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem("stripped_" + type.name() + log_suffix + "_waxed_exposed_" + name + "_bench", BenchBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem(type.name() + "_waxed_weathered_" + name + "_bench", BenchBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem(type.name() + log_suffix + "_waxed_weathered_" + name + "_bench", BenchBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem("stripped_" + type.name() + log_suffix + "_waxed_weathered_" + name + "_bench", BenchBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem(type.name() + "_waxed_oxidized_" + name + "_bench", BenchBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem(type.name() + log_suffix + "_waxed_oxidized_" + name + "_bench", BenchBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem("stripped_" + type.name() + log_suffix + "_waxed_oxidized_" + name + "_bench", BenchBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());

                    registerBlockWithItem(type.name() + "_" +  name + "_bench", properties -> new WeatheringBenchBlock(WeatheringCopper.WeatherState.UNAFFECTED, properties), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem(type.name() + log_suffix + "_" + name + "_bench", properties -> new WeatheringBenchBlock(WeatheringCopper.WeatherState.UNAFFECTED, properties), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem("stripped_" + type.name() + log_suffix + "_" + name + "_bench", properties -> new WeatheringBenchBlock(WeatheringCopper.WeatherState.UNAFFECTED, properties), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem(type.name() + "_exposed_" + name + "_bench", properties -> new WeatheringBenchBlock(WeatheringCopper.WeatherState.EXPOSED, properties), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem(type.name() + log_suffix + "_exposed_" + name + "_bench", properties -> new WeatheringBenchBlock(WeatheringCopper.WeatherState.EXPOSED, properties), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem("stripped_" + type.name() + log_suffix + "_exposed_" + name + "_bench", properties -> new WeatheringBenchBlock(WeatheringCopper.WeatherState.EXPOSED, properties), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem(type.name() + "_weathered_" + name + "_bench", properties -> new WeatheringBenchBlock(WeatheringCopper.WeatherState.WEATHERED, properties), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem(type.name() + log_suffix + "_weathered_" + name + "_bench", properties -> new WeatheringBenchBlock(WeatheringCopper.WeatherState.WEATHERED, properties), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem("stripped_" + type.name() + log_suffix + "_weathered_" + name + "_bench", properties -> new WeatheringBenchBlock(WeatheringCopper.WeatherState.WEATHERED, properties), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem(type.name() + "_oxidized_" + name + "_bench", properties -> new WeatheringBenchBlock(WeatheringCopper.WeatherState.OXIDIZED, properties), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem(type.name() + log_suffix + "_oxidized_" + name + "_bench", properties -> new WeatheringBenchBlock(WeatheringCopper.WeatherState.OXIDIZED, properties), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                    registerBlockWithItem("stripped_" + type.name() + log_suffix + "_oxidized_" + name + "_bench", properties -> new WeatheringBenchBlock(WeatheringCopper.WeatherState.OXIDIZED, properties), () -> BlockBehaviour.Properties.ofFullCopy(BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(type.name() + "_planks"))).noOcclusion());
                }
            });
        }

        STONE_MATERIALS.forEach(((block, suffix) -> {
            String top_name = block.getDescriptionId().replaceFirst("block.minecraft.", "").replaceFirst("quartz_block", "quartz");
            System.out.println(top_name);
            registerBlockWithItem(top_name+"_birdbath", BirdbathBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(block));
        }));

        TABLEWARE_MATERIALS.forEach((block, suffix) -> {
            String top_name = block.getDescriptionId().replaceFirst("block.minecraft.", "").replaceFirst("quartz_block", "quartz");
            registerBlockWithItem(top_name+"_tableware", TablewareBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(block));
        });

        WOOL_COLORS.forEach((block, color) -> METALS.forEach((metal, name) -> registerBlockWithItem(color+"_"+name+"_curtains", CurtainBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(block).noOcclusion())));

        METALS.forEach((metal, name) -> registerBlockWithItem(name+"_toaster", properties -> new RotatableBlock(properties, Block.box(2, 0, 2, 14, 4, 14)), () -> BlockBehaviour.Properties.ofFullCopy(metal)));
    }

    public static final DeferredBlock<Block> IRON_FRIDGE = registerBlockWithItem("iron_fridge", FridgeBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).pushReaction(PushReaction.BLOCK));
    public static final DeferredBlock<Block> GOLD_FRIDGE = registerBlockWithItem("gold_fridge", FridgeBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).pushReaction(PushReaction.BLOCK));
    public static final DeferredBlock<Block> NETHERITE_FRIDGE = registerBlockWithItem("netherite_fridge", FridgeBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK).pushReaction(PushReaction.BLOCK));
    public static final DeferredBlock<Block> COPPER_FRIDGE = registerBlockWithItem("copper_fridge", properties -> new WeatheringKitchenFridgeBlock(WeatheringCopper.WeatherState.UNAFFECTED, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).pushReaction(PushReaction.BLOCK));
    public static final DeferredBlock<Block> EXPOSED_COPPER_FRIDGE = registerBlockWithItem("exposed_copper_fridge", properties -> new WeatheringKitchenFridgeBlock(WeatheringCopper.WeatherState.WEATHERED, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.EXPOSED_COPPER).pushReaction(PushReaction.BLOCK));
    public static final DeferredBlock<Block> WEATHERED_COPPER_FRIDGE = registerBlockWithItem("weathered_copper_fridge", properties -> new WeatheringKitchenFridgeBlock(WeatheringCopper.WeatherState.EXPOSED, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WEATHERED_COPPER).pushReaction(PushReaction.BLOCK));
    public static final DeferredBlock<Block> OXIDIZED_COPPER_FRIDGE = registerBlockWithItem("oxidized_copper_fridge", properties -> new WeatheringKitchenFridgeBlock(WeatheringCopper.WeatherState.OXIDIZED, properties), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OXIDIZED_COPPER).pushReaction(PushReaction.BLOCK));
    public static final DeferredBlock<Block> WAXED_COPPER_FRIDGE = registerBlockWithItem("waxed_copper_fridge", FridgeBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WAXED_COPPER_BLOCK).pushReaction(PushReaction.BLOCK));
    public static final DeferredBlock<Block> WAXED_EXPOSED_COPPER_FRIDGE = registerBlockWithItem("waxed_exposed_copper_fridge", FridgeBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WAXED_EXPOSED_COPPER).pushReaction(PushReaction.BLOCK));
    public static final DeferredBlock<Block> WAXED_WEATHERED_COPPER_FRIDGE = registerBlockWithItem("waxed_weathered_copper_fridge", FridgeBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WAXED_WEATHERED_COPPER).pushReaction(PushReaction.BLOCK));
    public static final DeferredBlock<Block> WAXED_OXIDIZED_COPPER_FRIDGE = registerBlockWithItem("waxed_oxidized_copper_fridge", FridgeBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WAXED_OXIDIZED_COPPER).pushReaction(PushReaction.BLOCK));

    public static final DeferredBlock<Block> WHITE_LIGHT_GRAY_KITCHEN_TILES = registerBlockWithItem("white_light_gray_kitchen_tiles", KitchenTileBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_GRAY_KITCHEN_TILES = registerBlockWithItem("white_gray_kitchen_tiles", KitchenTileBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_BLACK_KITCHEN_TILES = registerBlockWithItem("white_black_kitchen_tiles", KitchenTileBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_BROWN_KITCHEN_TILES = registerBlockWithItem("white_brown_kitchen_tiles", KitchenTileBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_RED_KITCHEN_TILES = registerBlockWithItem("white_red_kitchen_tiles", KitchenTileBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_ORANGE_KITCHEN_TILES = registerBlockWithItem("white_orange_kitchen_tiles", KitchenTileBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_YELLOW_KITCHEN_TILES = registerBlockWithItem("white_yellow_kitchen_tiles", KitchenTileBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_LIME_KITCHEN_TILES = registerBlockWithItem("white_lime_kitchen_tiles", KitchenTileBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_GREEN_KITCHEN_TILES = registerBlockWithItem("white_green_kitchen_tiles", KitchenTileBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_CYAN_KITCHEN_TILES = registerBlockWithItem("white_cyan_kitchen_tiles", KitchenTileBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_LIGHT_BLUE_KITCHEN_TILES = registerBlockWithItem("white_light_blue_kitchen_tiles", KitchenTileBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_BLUE_KITCHEN_TILES = registerBlockWithItem("white_blue_kitchen_tiles", KitchenTileBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_PURPLE_KITCHEN_TILES = registerBlockWithItem("white_purple_kitchen_tiles", KitchenTileBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_MAGENTA_KITCHEN_TILES = registerBlockWithItem("white_magenta_kitchen_tiles", KitchenTileBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));
    public static final DeferredBlock<Block> WHITE_PINK_KITCHEN_TILES = registerBlockWithItem("white_pink_kitchen_tiles", KitchenTileBlock::new, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE));

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