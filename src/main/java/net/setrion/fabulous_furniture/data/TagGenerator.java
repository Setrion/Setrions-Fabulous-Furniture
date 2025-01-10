package net.setrion.fabulous_furniture.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.setrion.fabulous_furniture.FabulousFurniture;
import net.setrion.fabulous_furniture.registry.SFFBlocks;
import net.setrion.fabulous_furniture.registry.SFFTags;

import java.util.concurrent.CompletableFuture;

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
            tag(SFFTags.Blocks.CRATES).add(
                    SFFBlocks.OAK_CRATE.get(),
                    SFFBlocks.SPRUCE_CRATE.get(),
                    SFFBlocks.BIRCH_CRATE.get(),
                    SFFBlocks.JUNGLE_CRATE.get(),
                    SFFBlocks.ACACIA_CRATE.get(),
                    SFFBlocks.CHERRY_CRATE.get(),
                    SFFBlocks.DARK_OAK_CRATE.get(),
                    SFFBlocks.PALE_OAK_CRATE.get(),
                    SFFBlocks.BAMBOO_CRATE.get(),
                    SFFBlocks.MANGROVE_CRATE.get(),
                    SFFBlocks.CRIMSON_CRATE.get(),
                    SFFBlocks.WARPED_CRATE.get()
            );

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
        }
    }
}