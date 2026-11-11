package net.vvxzv.tfccc;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = TFCCrudeCrucible.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue IS_ALLOW_HEATING = BUILDER.comment(" ", "Is allow vessel heating in charcoal forge", "是否允许小缸在木炭炉中加热").define("isAllowHeating", false);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean isAllowHeating;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        isAllowHeating = IS_ALLOW_HEATING.get();
    }
}
