package net.setrion.fabulous_furniture.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Oxidizable;
import net.neoforged.neoforge.registries.datamaps.builtin.Waxable;
import net.setrion.fabulous_furniture.registry.SFFBlocks;

import java.util.concurrent.CompletableFuture;

public class DataMapGenerator extends DataMapProvider {

    public DataMapGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        builder(NeoForgeDataMaps.OXIDIZABLES)
                .add(SFFBlocks.COPPER_FRIDGE.getKey(), new Oxidizable(SFFBlocks.EXPOSED_COPPER_FRIDGE.get()), false)
                .add(SFFBlocks.EXPOSED_COPPER_FRIDGE.getKey(), new Oxidizable(SFFBlocks.WEATHERED_COPPER_FRIDGE.get()), false)
                .add(SFFBlocks.WEATHERED_COPPER_FRIDGE.getKey(), new Oxidizable(SFFBlocks.OXIDIZED_COPPER_FRIDGE.get()), false);

        builder(NeoForgeDataMaps.WAXABLES)
                .add(SFFBlocks.COPPER_FRIDGE.getKey(), new Waxable(SFFBlocks.WAXED_COPPER_FRIDGE.get()), false)
                .add(SFFBlocks.EXPOSED_COPPER_FRIDGE.getKey(), new Waxable(SFFBlocks.WAXED_EXPOSED_COPPER_FRIDGE.get()), false)
                .add(SFFBlocks.WEATHERED_COPPER_FRIDGE.getKey(), new Waxable(SFFBlocks.WAXED_WEATHERED_COPPER_FRIDGE.get()), false)
                .add(SFFBlocks.OXIDIZED_COPPER_FRIDGE.getKey(), new Waxable(SFFBlocks.WAXED_OXIDIZED_COPPER_FRIDGE.get()), false);
    }
}