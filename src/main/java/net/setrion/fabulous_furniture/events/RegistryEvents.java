package net.setrion.fabulous_furniture.events;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.setrion.fabulous_furniture.data.DataMapGenerator;
import net.setrion.fabulous_furniture.data.ModelGenerator;
import net.setrion.fabulous_furniture.data.TagGenerator;
import net.setrion.fabulous_furniture.registry.*;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus=EventBusSubscriber.Bus.MOD)
public class RegistryEvents {

    public static void registerEverything(IEventBus modEventBus) {
        SFFStats.STATS.register(modEventBus);
        SFFCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        SFFBlocks.BLOCKS.register(modEventBus);
        SFFBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
        SFFItems.ITEMS.register(modEventBus);
    }

    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> newLookup = event.getLookupProvider();

        generator.addProvider(true, new DataMapGenerator(output, newLookup));
        TagGenerator.Blocks blockTags = new TagGenerator.Blocks(output, newLookup);
        generator.addProvider(true, blockTags);
        generator.addProvider(true, new TagGenerator.Items(output, newLookup, blockTags.contentsGetter()));
        generator.addProvider(true, new ModelGenerator(output));
    }

    @SubscribeEvent
    public static void gatherServerData(GatherDataEvent.Server event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> newLookup = event.getLookupProvider();

        generator.addProvider(true, new DataMapGenerator(output, newLookup));
        TagGenerator.Blocks blockTags = new TagGenerator.Blocks(output, newLookup);
        generator.addProvider(true, blockTags);
        generator.addProvider(true, new TagGenerator.Items(output, newLookup, blockTags.contentsGetter()));
    }
}