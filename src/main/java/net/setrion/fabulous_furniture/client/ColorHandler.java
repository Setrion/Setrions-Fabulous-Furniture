package net.setrion.fabulous_furniture.client;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.setrion.fabulous_furniture.FabulousFurniture;
import net.setrion.fabulous_furniture.registry.SFFBlocks;
import net.setrion.fabulous_furniture.world.level.block.KitchenCounterSinkBlock;

@EventBusSubscriber(modid = FabulousFurniture.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ColorHandler {

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        for (DeferredHolder<Block, ? extends Block> block : SFFBlocks.BLOCKS.getEntries()) {
            if (block.get() instanceof KitchenCounterSinkBlock) {
                event.register((state, getter, pos, tintIndex) -> tintIndex == 1 && getter != null && pos != null ? BiomeColors.getAverageWaterColor(getter, pos) : -1, block.get());
            }
        }
    }
}