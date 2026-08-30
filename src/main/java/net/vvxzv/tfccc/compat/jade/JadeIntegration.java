package net.vvxzv.tfccc.compat.jade;

import net.vvxzv.tfccc.common.block.CrudeCrucibleBlock;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadeIntegration implements IWailaPlugin {

    @Override
    public void registerClient(IWailaClientRegistration reg) {
        reg.registerBlockComponent(CrudeCrucibleComponentProvider.INSTANCE, CrudeCrucibleBlock.class);
    }
}
