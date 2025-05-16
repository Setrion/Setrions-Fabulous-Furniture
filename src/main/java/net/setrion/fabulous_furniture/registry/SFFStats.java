package net.setrion.fabulous_furniture.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.setrion.fabulous_furniture.FabulousFurniture;

import java.util.ArrayList;
import java.util.List;

public class SFFStats {

    public static final DeferredRegister<ResourceLocation> STATS = DeferredRegister.create(Registries.CUSTOM_STAT, FabulousFurniture.MOD_ID);

    private static final List<Runnable> STAT_SETUP = new ArrayList<>();

    public static final DeferredHolder<ResourceLocation, ResourceLocation> OPEN_CRATE = makeCustomStat("open_crate");
    public static final DeferredHolder<ResourceLocation, ResourceLocation> OPEN_KITCHEN_COUNTER = makeCustomStat("open_kitchen_counter");
    public static final DeferredHolder<ResourceLocation, ResourceLocation> OPEN_FRIDGE = makeCustomStat("open_fridge");
    public static final DeferredHolder<ResourceLocation, ResourceLocation> OPEN_BEDSIDE_TABLE = makeCustomStat("open_bedside_table");
    public static final DeferredHolder<ResourceLocation, ResourceLocation> OPEN_CLOSET = makeCustomStat("open_closet");

    public static final DeferredHolder<ResourceLocation, ResourceLocation> ACTIVATE_SINK = makeCustomStat("activate_sink");
    public static final DeferredHolder<ResourceLocation, ResourceLocation> TAKE_WATER_FROM_SINK = makeCustomStat("take_water_from_sink");

    public static final DeferredHolder<ResourceLocation, ResourceLocation> INTERACT_WITH_FLOWER_BOX = makeCustomStat("interact_with_flower_box");
    public static final DeferredHolder<ResourceLocation, ResourceLocation> INTERACT_WITH_LAMP = makeCustomStat("interact_with_lamp");

    public static final DeferredHolder<ResourceLocation, ResourceLocation> THROW_AWAY_ITEM = makeCustomStat("throw_away_item");

    public static final DeferredHolder<ResourceLocation, ResourceLocation> SIT_ON_CHAIR = makeCustomStat("sit_on_chair");
    public static final DeferredHolder<ResourceLocation, ResourceLocation> SIT_ON_BENCH = makeCustomStat("sit_on_bench");

    private static DeferredHolder<ResourceLocation, ResourceLocation> makeCustomStat(String key) {
        ResourceLocation resourcelocation = FabulousFurniture.prefix(key);
        STAT_SETUP.add(() -> Stats.CUSTOM.get(resourcelocation, StatFormatter.DEFAULT));
        return STATS.register(key, () -> resourcelocation);
    }

    public static void init() {
        STAT_SETUP.forEach(Runnable::run);
    }
}