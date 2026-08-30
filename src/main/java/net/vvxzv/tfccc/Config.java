package net.vvxzv.tfccc;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = TFCCrudeCrucible.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.DoubleValue TEMPERATURE_DIFFERENCE_GROWTH_RATE = BUILDER.comment(" ", "Temperature difference growth rate. If the value is higher, the temperature of the crude crucible being heated will increase faster according to the temperature difference", "温差增长率。若该数值越高，被加热的简陋坩埚的温度会根据温差增长速度越快。").defineInRange("temperatureDifferenceGrowthRate", 0.005, 0, 1);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static double temperatureDifferenceGrowthRate;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        temperatureDifferenceGrowthRate = TEMPERATURE_DIFFERENCE_GROWTH_RATE.get();
    }
}
