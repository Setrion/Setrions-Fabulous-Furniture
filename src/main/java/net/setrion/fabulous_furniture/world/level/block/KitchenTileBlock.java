package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class KitchenTileBlock extends Block implements BlockTagSupplier {

    public KitchenTileBlock(Properties properties) {
        super(properties);
    }

    @Override
    public List<TagKey<Block>> getTags() {
        return List.of(BlockTags.MINEABLE_WITH_PICKAXE);
    }
}