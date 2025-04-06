package net.setrion.fabulous_furniture.world.level.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

public class WeatheringBenchBlock extends BenchBlock implements WeatheringCopper {

    public static final MapCodec<WeatheringBenchBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(WeatherState.CODEC.fieldOf("weathering_state").forGetter(WeatheringBenchBlock::getAge), propertiesCodec()).apply(instance, WeatheringBenchBlock::new);
    });
    private final WeatheringCopper.WeatherState weatherState;

    public MapCodec<WeatheringBenchBlock> codec() {
        return CODEC;
    }

    public WeatheringBenchBlock(WeatheringCopper.WeatherState weatherState, Properties properties) {
        super(properties);
        this.weatherState = weatherState;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        changeOverTime(state, level, pos, random);
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