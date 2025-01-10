package net.setrion.fabulous_furniture.world.level.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class WeatheringKitchenFridgeBlock extends KitchenFridgeBlock implements WeatheringCopper {

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
        if (state.getValue(KitchenFridgeBlock.HALF) == DoubleBlockHalf.LOWER) {
            changeOverTime(state, level, pos, random);
        }
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public WeatherState getAge() {
        return weatherState;
    }
}
