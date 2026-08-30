package net.vvxzv.tfccc.common.registry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.vvxzv.tfccc.TFCCrudeCrucible;
import net.vvxzv.tfccc.common.block.entity.CrudeCrucibleBlockEntity;

public class BlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TFCCrudeCrucible.MODID);

    public static final RegistryObject<BlockEntityType<CrudeCrucibleBlockEntity>> CRUDE_CRUCIBLE = BLOCK_ENTITY_TYPES.register("crude_crucible", () -> BlockEntityType.Builder.of(CrudeCrucibleBlockEntity::new, Blocks.CRUDE_CRUCIBLE.get()).build(null));
}
