package net.setrion.fabulous_furniture.events;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.MainThreadPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.setrion.fabulous_furniture.data.*;
import net.setrion.fabulous_furniture.network.CarpentryRecipes;
import net.setrion.fabulous_furniture.registry.*;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber()
public class RegistryEvents {

    public static void registerEverything(IEventBus modEventBus) {
        SFFStats.STATS.register(modEventBus);
        SFFEntityTypes.ENTITY_TYPES.register(modEventBus);
        SFFCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        SFFBlocks.BLOCKS.register(modEventBus);
        SFFMenuTypes.MENU_TYPES.register(modEventBus);
        SFFRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        SFFRecipeTypes.RECIPE_TYPES.register(modEventBus);
        SFFBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
        SFFItems.ITEMS.register(modEventBus);
    }

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(
                CarpentryRecipes.TYPE,
                CarpentryRecipes.STREAM_CODEC,
                new MainThreadPayloadHandler<>(
                        CarpentryRecipes.ClientPayloadHandler::handleDataOnMain
                ),
                new MainThreadPayloadHandler<>(
                        CarpentryRecipes.ClientPayloadHandler::handleDataOnMain
                )
        );
    }

    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> newLookup = event.getLookupProvider();

        generator.addProvider(true, new DataMapGenerator(output, newLookup));
        TagGenerator.Blocks blockTags = new TagGenerator.Blocks(output, newLookup);
        generator.addProvider(true, new LootTableGenerator(output, newLookup));
        generator.addProvider(true, blockTags);
        generator.addProvider(true, new LangGenerator(output));
        generator.addProvider(true, new RecipeRunner(output, newLookup));
        generator.addProvider(true, new TagGenerator.Items(output, newLookup));
        generator.addProvider(true, new ModelGenerator(output));
    }

    @SubscribeEvent
    public static void gatherServerData(GatherDataEvent.Server event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> newLookup = event.getLookupProvider();

        generator.addProvider(true, new DataMapGenerator(output, newLookup));
        TagGenerator.Blocks blockTags = new TagGenerator.Blocks(output, newLookup);
        generator.addProvider(true, new LootTableGenerator(output, newLookup));
        generator.addProvider(true, new LangGenerator(output));
        generator.addProvider(true, blockTags);
        generator.addProvider(true, new RecipeRunner(output, newLookup));
        generator.addProvider(true, new TagGenerator.Items(output, newLookup));
    }
}