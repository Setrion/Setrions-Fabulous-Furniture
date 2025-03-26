package net.setrion.fabulous_furniture.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.setrion.fabulous_furniture.FabulousFurniture;
import net.setrion.fabulous_furniture.world.level.entity.Seat;

public class SFFEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, FabulousFurniture.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<Seat>> SEAT = ENTITY_TYPES.register("seat", () -> EntityType.Builder.of(Seat::new, MobCategory.MISC).sized(0, 0).build(entityId("seat")));

    private static ResourceKey<EntityType<?>> entityId(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, FabulousFurniture.prefix(name));
    }
}