package net.vvxzv.tfccc;

import net.dries007.tfc.common.capabilities.BlockCapabilities;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.vvxzv.tfccc.common.registry.BlockEntities;
import net.vvxzv.tfccc.common.registry.Blocks;

@Mod(TFCCrudeCrucible.MODID)
public class TFCCrudeCrucible {
    public static final String MODID = "tfc_crude_crucible";

    public TFCCrudeCrucible(IEventBus modEventBus, ModContainer modContainer) {
        Blocks.BLOCKS.register(modEventBus);
        BlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);

        modEventBus.addListener(this::registerCapabilities);

        NeoForgeEventHandler.init();

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                BlockCapabilities.HEAT,
                BlockEntities.CRUDE_CRUCIBLE.get(),
                (blockEntity, direction) -> blockEntity
        );
    }
}
