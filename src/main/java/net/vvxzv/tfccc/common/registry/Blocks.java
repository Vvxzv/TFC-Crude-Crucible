package net.vvxzv.tfccc.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.vvxzv.tfccc.TFCCrudeCrucible;
import net.vvxzv.tfccc.common.block.CrudeCrucibleBlock;

public class Blocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, TFCCrudeCrucible.MODID);

    public static final DeferredHolder<Block, CrudeCrucibleBlock> CRUDE_CRUCIBLE = BLOCKS.register("crude_crucible", CrudeCrucibleBlock::new);
}
