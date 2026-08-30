package net.vvxzv.tfccc.common.registry;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vvxzv.tfccc.TFCCrudeCrucible;
import net.vvxzv.tfccc.common.block.CrudeCrucibleBlock;

public class Blocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TFCCrudeCrucible.MODID);

    public static final RegistryObject<Block> CRUDE_CRUCIBLE = BLOCKS.register("crude_crucible", CrudeCrucibleBlock::new);
}
