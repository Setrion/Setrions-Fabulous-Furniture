package net.setrion.fabulous_furniture.world.level.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.setrion.fabulous_furniture.world.level.block.entity.KitchenFridgeBlockEntity;

public class WeatheringKitchenFridgeBlock extends FridgeBlock implements WeatheringCopper {

    public static final MapCodec<WeatheringKitchenFridgeBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(WeatherState.CODEC.fieldOf("weathering_state").forGetter(WeatheringKitchenFridgeBlock::getAge), propertiesCodec()).apply(instance, WeatheringKitchenFridgeBlock::new));
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
            changeOverTime(state, level, pos, random);
        }
    }

    @Override
    public void changeOverTime(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextFloat() < 0.05688889F) {
            this.getNextState(state, level, pos, random).ifPresent((p_153039_) -> {
                BlockEntity be = level.getBlockEntity(pos);
                if (be instanceof KitchenFridgeBlockEntity fridge) {
                    NonNullList<ItemStack> items = NonNullList.create();
                    items.addAll(fridge.getItems());
                    fridge.getItems().clear();
                    level.setBlockAndUpdate(pos, p_153039_);
                    KitchenFridgeBlockEntity fridgeBE = new KitchenFridgeBlockEntity(pos, p_153039_);
                    fridgeBE.setItems(items);
                    level.setBlockEntity(fridgeBE);
                }
            });
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
