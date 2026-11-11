package net.vvxzv.tfccc;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = TFCCrudeCrucible.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue IS_ALLOW_HEATING = BUILDER.comment(" ", "Is allow vessel heating in charcoal forge", "是否允许小缸在木炭炉中加热").define("isAllowHeating", false);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean isAllowHeating;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        isAllowHeating = IS_ALLOW_HEATING.get();
    }
}
