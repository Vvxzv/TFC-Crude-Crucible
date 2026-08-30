package net.vvxzv.tfccc;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.vvxzv.tfccc.common.registry.BlockEntities;
import net.vvxzv.tfccc.common.registry.Blocks;

@Mod(TFCCrudeCrucible.MODID)
public class TFCCrudeCrucible {

    public static final String MODID = "tfc_crude_crucible";

    @SuppressWarnings("removal")
    public TFCCrudeCrucible() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        Blocks.BLOCKS.register(modEventBus);
        BlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);

        ForgeEventHandler.init();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
