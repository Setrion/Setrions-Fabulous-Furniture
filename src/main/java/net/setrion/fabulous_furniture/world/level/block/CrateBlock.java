package net.setrion.fabulous_furniture.world.level.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.setrion.fabulous_furniture.registry.SFFStats;
import net.setrion.fabulous_furniture.world.level.block.entity.CrateBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrateBlock extends BaseEntityBlock implements BlockTagSupplier {

    public static final MapCodec<CrateBlock> CODEC = simpleCodec(CrateBlock::new);

    public CrateBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CrateBlockEntity(blockPos, blockState);
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState p_393681_, ServerLevel p_394632_, BlockPos p_394133_, boolean p_394282_) {
        Containers.updateNeighboursAfterDestroy(p_393681_, p_394632_, p_394133_);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (level instanceof ServerLevel serverlevel) {
            MenuProvider menuprovider = this.getMenuProvider(blockState, level, blockPos);
            if (menuprovider != null) {
                player.openMenu(menuprovider);
                player.awardStat(Stats.CUSTOM.get(SFFStats.OPEN_CRATE.get()));
                PiglinAi.angerNearbyPiglins(serverlevel, player, true);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public List<TagKey<Block>> getTags() {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }
}