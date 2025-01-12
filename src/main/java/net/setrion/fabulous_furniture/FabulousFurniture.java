package net.setrion.fabulous_furniture;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.setrion.fabulous_furniture.events.RegistryEvents;
import net.setrion.fabulous_furniture.registry.SFFBlockEntityTypes;
import net.setrion.fabulous_furniture.registry.SFFBlocks;
import net.setrion.fabulous_furniture.world.level.block.*;

@Mod(FabulousFurniture.MOD_ID)
public class FabulousFurniture {
    public static final String MOD_ID = "fabulous_furniture";

    public FabulousFurniture(IEventBus modEventBus) {
        RegistryEvents.registerEverything(modEventBus);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addBlockEntityTypes);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    public void addBlockEntityTypes(BlockEntityTypeAddBlocksEvent event) {
        for (DeferredHolder<Block, ? extends Block> block : SFFBlocks.BLOCKS.getEntries()) {
            if (block.get() instanceof KitchenCounterSmokerBlock) {
                event.modify(BlockEntityType.SMOKER, block.get());
            }
        }
        for (DeferredHolder<Block, ? extends Block> block : SFFBlocks.BLOCKS.getEntries()) {
            if (block.get() instanceof CrateBlock) {
                event.modify(SFFBlockEntityTypes.CRATE.get(), block.get());
            }
        }
        for (DeferredHolder<Block, ? extends Block> block : SFFBlocks.BLOCKS.getEntries()) {
            if (block.get() instanceof KitchenCounterContainerBlock || block.get() instanceof KitchenCounterContainerDoorBlock || block.get() instanceof KitchenCounterContainerDrawerBlock || block.get() instanceof KitchenCabinetContainerBlock || block.get() instanceof KitchenCabinetContainerDoorBlock || block.get() instanceof KitchenCabinetContainerSidewaysDoorBlock || block.get() instanceof KitchenCabinetShelfBlock) {
                event.modify(SFFBlockEntityTypes.KITCHEN_COUNTER.get(), block.get());
            }
        }
    }

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
