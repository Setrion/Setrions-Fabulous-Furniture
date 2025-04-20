package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.List;

public class HammockPostBlock extends Block implements BlockTagSupplier {

    public static final EnumProperty<Direction> FACING;

    public HammockPostBlock(Properties properties) {
        super(properties);
    }

    @Override
    public List<TagKey<Block>> getTags() {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
    }
}