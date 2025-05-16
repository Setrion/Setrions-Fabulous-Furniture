package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class BirdbathBlock extends Block implements BlockTagSupplier {

    public BirdbathBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.or(Block.box(4, 0, 4, 12, 2, 12), Block.box(6, 2, 6, 10, 4, 10), Block.box(7, 4, 7, 9, 12, 9), Block.box(3, 12, 3, 13, 14, 13), Block.box(2, 14, 2, 14, 16, 14));
    }

    @Override
    public List<TagKey<Block>> getTags() {
        return List.of(BlockTags.MINEABLE_WITH_PICKAXE);
    }
}