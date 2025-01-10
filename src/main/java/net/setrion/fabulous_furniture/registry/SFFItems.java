package net.setrion.fabulous_furniture.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.setrion.fabulous_furniture.FabulousFurniture;

import java.util.function.Function;

public class SFFItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FabulousFurniture.MOD_ID);

    public static <T extends Item> DeferredItem<T> registerItem(String name, Function<Item.Properties, T> item, Item.Properties properties) {
        return ITEMS.register(name, () -> item.apply(properties.setId(ResourceKey.create(Registries.ITEM, FabulousFurniture.prefix(name)))));
    }
}