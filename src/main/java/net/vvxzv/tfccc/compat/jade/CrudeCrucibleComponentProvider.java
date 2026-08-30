package net.vvxzv.tfccc.compat.jade;

import net.dries007.tfc.config.TFCConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.vvxzv.tfccc.TFCCrudeCrucible;
import net.vvxzv.tfccc.common.block.entity.CrudeCrucibleBlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum CrudeCrucibleComponentProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        BlockEntity blockEntity = blockAccessor.getBlockEntity();
        if(blockEntity instanceof CrudeCrucibleBlockEntity crudeCrucible) {
            float temperature = crudeCrucible.getVesselTemperature();
            if(temperature > 2) {
                iTooltip.add(TFCConfig.CLIENT.heatTooltipStyle.get().formatColored((int)temperature));
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(TFCCrudeCrucible.MODID, "crude_crucible");
    }
}
