package net.setrion.fabulous_furniture;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.setrion.fabulous_furniture.client.gui.screens.inventory.CarpentryTableScreen;
import net.setrion.fabulous_furniture.events.RegistryEvents;
import net.setrion.fabulous_furniture.registry.SFFBlockEntityTypes;
import net.setrion.fabulous_furniture.registry.SFFBlocks;
import net.setrion.fabulous_furniture.registry.SFFMenuTypes;
import net.setrion.fabulous_furniture.world.level.block.*;

@Mod(FabulousFurniture.MOD_ID)
public class FabulousFurniture {
    public static final String MOD_ID = "fabulous_furniture";

    public FabulousFurniture(IEventBus modEventBus) {
        RegistryEvents.registerEverything(modEventBus);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientScreenSetup);
        modEventBus.addListener(this::addBlockEntityTypes);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    public void clientScreenSetup(RegisterMenuScreensEvent event) {
        event.register(SFFMenuTypes.CARPENTRY_TABLE.get(), CarpentryTableScreen::new);
    }

    public void addBlockEntityTypes(BlockEntityTypeAddBlocksEvent event) {
        for (DeferredHolder<Block, ? extends Block> block : SFFBlocks.BLOCKS.getEntries()) {
            if (block.get() instanceof KitchenCounterSmokerBlock) {
                event.modify(BlockEntityType.SMOKER, block.get());
            } else if (block.get() instanceof KitchenCounterContainerBaseBlock || block.get() instanceof KitchenCabinetContainerBaseBlock || block.get() instanceof KitchenShelfBlock) {
                event.modify(SFFBlockEntityTypes.KITCHEN_STORAGE.get(), block.get());
            } else if (block.get() instanceof CrateBlock) {
                event.modify(SFFBlockEntityTypes.CRATE.get(), block.get());
            } else if (block.get() instanceof BedsideTableBlock) {
                event.modify(SFFBlockEntityTypes.BEDSIDE_TABLE.get(), block.get());
            } else if (block.get() instanceof ClosetBlock) {
                event.modify(SFFBlockEntityTypes.CLOSET.get(), block.get());
            } else if (block.get() instanceof FlowerBoxBlock) {
                event.modify(SFFBlockEntityTypes.FLOWER_BOX.get(), block.get());
            }
        }
    }

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
