package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;

import java.util.List;
import java.util.stream.Stream;

public class TablewareBlock extends RotatableBlock {

    public TablewareBlock(Properties properties) {
        super(properties, Stream.of(
                Block.box(7, 0.01, 3, 13, 0.5, 9),
                Block.box(5, 0.01, 3, 7, 0.5, 9),
                Block.box(13, 0.01, 3, 15, 0.5, 9),
                Block.box(7, 0.01, 1, 13, 0.5, 3),
                Block.box(7, 0.01, 9, 13, 0.5, 11),
                Block.box(1, 0, 10, 4, 1, 13),
                Block.box(2, 0.5, 8, 3, 1.5, 10),
                Block.box(2, 2.5, 8, 3, 3.5, 10),
                Block.box(2, 1.5, 8, 3, 2.5, 9),
                Block.box(1, 1, 10, 2, 4, 13),
                Block.box(3, 1, 10, 4, 4, 13),
                Block.box(2, 1, 12, 3, 4, 13),
                Block.box(2, 1, 10, 3, 4, 11),
                Block.box(1.5, 1.5, 10.5, 3.5, 3.5, 12.5)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get());
    }

    @Override
    public List<TagKey<Block>> getTags() {
        return List.of(BlockTags.MINEABLE_WITH_PICKAXE);
    }
}
