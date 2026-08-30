package net.vvxzv.tfccc.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.vvxzv.tfccc.TFCCrudeCrucible;
import net.vvxzv.tfccc.common.block.entity.CrudeCrucibleBlockEntity;

public class BlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TFCCrudeCrucible.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CrudeCrucibleBlockEntity>> CRUDE_CRUCIBLE = BLOCK_ENTITY_TYPES.register("crude_crucible", () -> BlockEntityType.Builder.of(CrudeCrucibleBlockEntity::new, Blocks.CRUDE_CRUCIBLE.get()).build(null));
}
