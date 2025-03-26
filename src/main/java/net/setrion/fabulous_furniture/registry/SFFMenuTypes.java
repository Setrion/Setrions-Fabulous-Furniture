package net.setrion.fabulous_furniture.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.setrion.fabulous_furniture.FabulousFurniture;
import net.setrion.fabulous_furniture.world.inventory.CarpentryTableMenu;

public class SFFMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, FabulousFurniture.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<CarpentryTableMenu>> CARPENTRY_TABLE = MENU_TYPES.register("carpentry_table", () -> new MenuType<>(CarpentryTableMenu::new, FeatureFlags.DEFAULT_FLAGS));

}