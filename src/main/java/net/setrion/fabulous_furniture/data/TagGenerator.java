package net.setrion.fabulous_furniture.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.setrion.fabulous_furniture.FabulousFurniture;
import net.setrion.fabulous_furniture.registry.SFFBlocks;
import net.setrion.fabulous_furniture.registry.SFFTags;
import net.setrion.fabulous_furniture.world.level.block.BlockTagSupplier;

import java.util.concurrent.CompletableFuture;

import static net.setrion.fabulous_furniture.registry.SFFBlocks.WOOD_TYPES;

public class TagGenerator {

    public static class Items extends ItemTagsProvider {

        public Items(PackOutput output, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> blockProvider) {
            super(output, future, blockProvider, FabulousFurniture.MOD_ID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {

        }
    }

    public static class Blocks extends BlockTagsProvider {

        public Blocks(PackOutput output, CompletableFuture<HolderLookup.Provider> future) {
            super(output, future, FabulousFurniture.MOD_ID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            for (WoodType type : WOOD_TYPES) {
                tag(SFFTags.Blocks.CRATES).add(BuiltInRegistries.BLOCK.getValue(FabulousFurniture.prefix(type.name()+"_crate")));
            }

            SFFBlocks.BLOCKS.getEntries().stream().filter(entry -> entry.getId().getNamespace().equals(FabulousFurniture.MOD_ID)).forEach(entry -> {
                Block block = entry.get();
                if(block instanceof BlockTagSupplier supplier) {
                    supplier.getTags().forEach(key -> this.tag(key).add(block));
                } else {
                    throw new IllegalArgumentException("Block doesn't implement BlockTagSupplier: " + entry.getId());
                }
            });

            tag(SFFTags.Blocks.COPPER_FRIDGES).add(
                    SFFBlocks.COPPER_FRIDGE.get(),
                    SFFBlocks.EXPOSED_COPPER_FRIDGE.get(),
                    SFFBlocks.WEATHERED_COPPER_FRIDGE.get(),
                    SFFBlocks.OXIDIZED_COPPER_FRIDGE.get(),
                    SFFBlocks.WAXED_COPPER_FRIDGE.get(),
                    SFFBlocks.WAXED_EXPOSED_COPPER_FRIDGE.get(),
                    SFFBlocks.WAXED_WEATHERED_COPPER_FRIDGE.get(),
                    SFFBlocks.WAXED_OXIDIZED_COPPER_FRIDGE.get()
            );

            tag(SFFTags.Blocks.FRIDGES).add(
                    SFFBlocks.IRON_FRIDGE.get(),
                    SFFBlocks.GOLD_FRIDGE.get(),
                    SFFBlocks.NETHERITE_FRIDGE.get()
            ).addTag(SFFTags.Blocks.COPPER_FRIDGES);

            tag(SFFTags.Blocks.FLOWER_BOX_PLACABLES).add(
                    net.minecraft.world.level.block.Blocks.OAK_SAPLING,
                    net.minecraft.world.level.block.Blocks.SPRUCE_SAPLING,
                    net.minecraft.world.level.block.Blocks.BIRCH_SAPLING,
                    net.minecraft.world.level.block.Blocks.JUNGLE_SAPLING,
                    net.minecraft.world.level.block.Blocks.ACACIA_SAPLING,
                    net.minecraft.world.level.block.Blocks.DARK_OAK_SAPLING,
                    net.minecraft.world.level.block.Blocks.MANGROVE_PROPAGULE,
                    net.minecraft.world.level.block.Blocks.CHERRY_SAPLING,
                    net.minecraft.world.level.block.Blocks.PALE_OAK_SAPLING,
                    net.minecraft.world.level.block.Blocks.BROWN_MUSHROOM,
                    net.minecraft.world.level.block.Blocks.RED_MUSHROOM,
                    net.minecraft.world.level.block.Blocks.CRIMSON_FUNGUS,
                    net.minecraft.world.level.block.Blocks.WARPED_FUNGUS,
                    net.minecraft.world.level.block.Blocks.SHORT_GRASS,
                    net.minecraft.world.level.block.Blocks.FERN,
                    net.minecraft.world.level.block.Blocks.SHORT_DRY_GRASS,
                    net.minecraft.world.level.block.Blocks.BUSH,
                    net.minecraft.world.level.block.Blocks.DEAD_BUSH,
                    net.minecraft.world.level.block.Blocks.DANDELION,
                    net.minecraft.world.level.block.Blocks.POPPY,
                    net.minecraft.world.level.block.Blocks.BLUE_ORCHID,
                    net.minecraft.world.level.block.Blocks.ALLIUM,
                    net.minecraft.world.level.block.Blocks.AZURE_BLUET,
                    net.minecraft.world.level.block.Blocks.RED_TULIP,
                    net.minecraft.world.level.block.Blocks.ORANGE_TULIP,
                    net.minecraft.world.level.block.Blocks.WHITE_TULIP,
                    net.minecraft.world.level.block.Blocks.PINK_TULIP,
                    net.minecraft.world.level.block.Blocks.OXEYE_DAISY,
                    net.minecraft.world.level.block.Blocks.CORNFLOWER,
                    net.minecraft.world.level.block.Blocks.LILY_OF_THE_VALLEY,
                    net.minecraft.world.level.block.Blocks.TORCHFLOWER,
                    net.minecraft.world.level.block.Blocks.CACTUS_FLOWER,
                    net.minecraft.world.level.block.Blocks.CLOSED_EYEBLOSSOM,
                    net.minecraft.world.level.block.Blocks.OPEN_EYEBLOSSOM,
                    net.minecraft.world.level.block.Blocks.WITHER_ROSE,
                    net.minecraft.world.level.block.Blocks.BAMBOO,
                    net.minecraft.world.level.block.Blocks.CRIMSON_ROOTS,
                    net.minecraft.world.level.block.Blocks.WARPED_ROOTS,
                    net.minecraft.world.level.block.Blocks.NETHER_SPROUTS,
                    net.minecraft.world.level.block.Blocks.TALL_DRY_GRASS
            );
        }
    }
}