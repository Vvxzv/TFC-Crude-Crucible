package net.vvxzv.tfccc;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = TFCCrudeCrucible.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.DoubleValue TEMPERATURE_DIFFERENCE_GROWTH_RATE = BUILDER.comment(" ", "Temperature difference growth rate. If the value is higher, the temperature of the crude crucible being heated will increase faster according to the temperature difference", "温差增长率。若该数值越高，被加热的简陋坩埚的温度会根据温差增长速度越快。").defineInRange("temperatureDifferenceGrowthRate", 0.005, 0, 1);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static double temperatureDifferenceGrowthRate;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        temperatureDifferenceGrowthRate = TEMPERATURE_DIFFERENCE_GROWTH_RATE.get();
    }
}
