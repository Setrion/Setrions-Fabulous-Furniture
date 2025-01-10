package net.setrion.fabulous_furniture.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.setrion.fabulous_furniture.FabulousFurniture;

import java.util.Collection;
import java.util.stream.Collectors;

public class SFFCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FabulousFurniture.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = CREATIVE_MODE_TABS.register("main", () -> new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 1)
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS).icon(() -> new ItemStack(SFFBlocks.OAK_CRATE.asItem()))
            .title(Component.translatable("itemGroup.fabulous_furniture.main"))
            .displayItems((parameters, output) -> {
                Collection<Block> blocks = SFFBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toList());
                blocks.forEach(output::accept);
            }).build());
}