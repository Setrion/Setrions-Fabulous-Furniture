package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class TablewareBlock extends RotatableBlock {

    public TablewareBlock(Properties properties) {
        super(properties, Block.box(2, 0, 2, 14, 4, 14));
    }

    @Override
    public List<TagKey<Block>> getTags() {
        return List.of(BlockTags.MINEABLE_WITH_PICKAXE);
    }
}
