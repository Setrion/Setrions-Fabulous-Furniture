package net.setrion.fabulous_furniture.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.setrion.fabulous_furniture.FabulousFurniture;
import net.setrion.fabulous_furniture.world.level.block.entity.CrateBlockEntity;
import net.setrion.fabulous_furniture.world.level.block.entity.KitchenCounterBlockEntity;
import net.setrion.fabulous_furniture.world.level.block.entity.KitchenFridgeBlockEntity;

public class SFFBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, FabulousFurniture.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CrateBlockEntity>> CRATE = BLOCK_ENTITY_TYPES.register("crate", () -> new BlockEntityType<>(CrateBlockEntity::new,
            SFFBlocks.OAK_CRATE.get(), SFFBlocks.SPRUCE_CRATE.get(), SFFBlocks.BIRCH_CRATE.get(), SFFBlocks.JUNGLE_CRATE.get(), SFFBlocks.ACACIA_CRATE.get(), SFFBlocks.CHERRY_CRATE.get(), SFFBlocks.DARK_OAK_CRATE.get(), SFFBlocks.PALE_OAK_CRATE.get(), SFFBlocks.BAMBOO_CRATE.get(), SFFBlocks.MANGROVE_CRATE.get(), SFFBlocks.CRIMSON_CRATE.get(), SFFBlocks.WARPED_CRATE.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<KitchenCounterBlockEntity>> KITCHEN_COUNTER = BLOCK_ENTITY_TYPES.register("kitchen_counter", () -> new BlockEntityType<>(KitchenCounterBlockEntity::new,
            SFFBlocks.OAK_POLISHED_TUFF_KITCHEN_COUNTER_DOOR.get(), SFFBlocks.OAK_POLISHED_TUFF_KITCHEN_COUNTER_OPEN.get(), SFFBlocks.OAK_POLISHED_TUFF_KITCHEN_COUNTER_GLASS_DOOR.get(), SFFBlocks.OAK_POLISHED_TUFF_KITCHEN_COUNTER_SMALL_DRAWER.get(), SFFBlocks.OAK_POLISHED_TUFF_KITCHEN_COUNTER_BIG_DRAWER.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<KitchenFridgeBlockEntity>> FRIDGE = BLOCK_ENTITY_TYPES.register("fridge", () -> new BlockEntityType<>(KitchenFridgeBlockEntity::new,
            SFFBlocks.IRON_FRIDGE.get(), SFFBlocks.GOLD_FRIDGE.get(), SFFBlocks.NETHERITE_FRIDGE.get(), SFFBlocks.COPPER_FRIDGE.get(), SFFBlocks.EXPOSED_COPPER_FRIDGE.get(), SFFBlocks.WEATHERED_COPPER_FRIDGE.get(), SFFBlocks.OXIDIZED_COPPER_FRIDGE.get(), SFFBlocks.WAXED_COPPER_FRIDGE.get(), SFFBlocks.WAXED_EXPOSED_COPPER_FRIDGE.get(), SFFBlocks.WAXED_WEATHERED_COPPER_FRIDGE.get(), SFFBlocks.WAXED_OXIDIZED_COPPER_FRIDGE.get()));
}