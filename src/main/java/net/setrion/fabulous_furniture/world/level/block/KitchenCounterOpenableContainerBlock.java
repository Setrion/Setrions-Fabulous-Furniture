package net.setrion.fabulous_furniture.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.setrion.fabulous_furniture.registry.SFFStats;

public class KitchenCounterOpenableContainerBlock extends KitchenCounterContainerBaseBlock {

    public KitchenCounterOpenableContainerBlock(Properties properties, boolean hasHinge) {
        super(properties, true, hasHinge);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (level instanceof ServerLevel serverlevel) {
            if (blockState.hasProperty(OPEN) && player.isShiftKeyDown()) {
                if (blockState.getValue(OPEN)) {
                    level.setBlock(blockPos, blockState.setValue(OPEN, !blockState.getValue(OPEN)), 3);
                    level.playSound(null, blockPos, SoundEvents.BARREL_CLOSE, SoundSource.BLOCKS);
                } else {
                    level.setBlock(blockPos, blockState.setValue(OPEN, !blockState.getValue(OPEN)), 3);
                    level.playSound(null, blockPos, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS);
                }
            } else {
                MenuProvider menuprovider = this.getMenuProvider(blockState, level, blockPos);
                if (menuprovider != null) {
                    player.openMenu(menuprovider);
                    player.awardStat(Stats.CUSTOM.get(SFFStats.OPEN_KITCHEN_COUNTER.get()));
                    PiglinAi.angerNearbyPiglins(serverlevel, player, true);
                }
            }
        }

        return InteractionResult.SUCCESS;
    }
}