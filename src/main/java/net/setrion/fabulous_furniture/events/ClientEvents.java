package net.setrion.fabulous_furniture.events;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.setrion.fabulous_furniture.FabulousFurniture;
import net.setrion.fabulous_furniture.client.renderer.blockentity.FlowerBoxRenderer;
import net.setrion.fabulous_furniture.client.renderer.entity.SeatRenderer;
import net.setrion.fabulous_furniture.registry.SFFBlockEntityTypes;
import net.setrion.fabulous_furniture.registry.SFFEntityTypes;

@EventBusSubscriber(modid = FabulousFurniture.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(SFFEntityTypes.SEAT.get(), SeatRenderer::new);
        event.registerBlockEntityRenderer(SFFBlockEntityTypes.FLOWER_BOX.get(), FlowerBoxRenderer::new);
    }
}