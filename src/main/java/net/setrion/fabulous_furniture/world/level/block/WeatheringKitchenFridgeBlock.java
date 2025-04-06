package net.setrion.fabulous_furniture.world.level.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class WeatheringKitchenFridgeBlock extends FridgeBlock implements WeatheringCopper {

    public static final MapCodec<WeatheringKitchenFridgeBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(WeatherState.CODEC.fieldOf("weathering_state").forGetter(WeatheringKitchenFridgeBlock::getAge), propertiesCodec()).apply(instance, WeatheringKitchenFridgeBlock::new);
    });
    private final WeatheringCopper.WeatherState weatherState;

    public MapCodec<WeatheringKitchenFridgeBlock> codec() {
        return CODEC;
    }

    public WeatheringKitchenFridgeBlock(WeatheringCopper.WeatherState weatherState, Properties properties) {
        super(properties);
        this.weatherState = weatherState;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(FridgeBlock.HALF) == DoubleBlockHalf.LOWER) {
            BlockEntity be = level.getBlockEntity(pos);
            getNextState(state, level, pos, random).ifPresent(bState -> {
                be.setBlockState(bState);
            });
            changeOverTime(state, level, pos, random);
            level.setBlockEntity(be);
        }
    }

    /*@Override
    public void changeOverTime(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextFloat() < 0.05688889F) {
            BlockEntity be = level.getBlockEntity(pos);
            getNextState(state, level, pos, random).ifPresent((p_153039_) -> {
                if (be instanceof KitchenFridgeBlockEntity fridge) {
                    System.out.println(fridge.countItem(Items.BEEF));
                    fridge.setBlockState(p_153039_);
                    be.setBlockState(p_153039_);
                    level.setBlockEntity(fridge);
                }
                level.setBlockAndUpdate(pos, p_153039_);
                System.out.println(be.getBlockState());
            });
        }
    }*/

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public WeatherState getAge() {
        return weatherState;
    }
}
