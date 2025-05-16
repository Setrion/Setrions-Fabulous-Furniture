package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.List;

public interface BlockTagSupplier {
    List<TagKey<Block>> getTags();

    default boolean isWIP() {
        return false;
    }
}