package net.vvxzv.tfccc.common.block;

import net.dries007.tfc.common.blocks.EntityBlockExtension;
import net.dries007.tfc.common.blocks.ExtendedBlock;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.vvxzv.tfccc.common.block.entity.CrudeCrucibleBlockEntity;
import net.vvxzv.tfccc.common.registry.BlockEntities;
import org.jetbrains.annotations.NotNull;

public class CrudeCrucibleBlock extends ExtendedBlock implements EntityBlockExtension {
    private static final VoxelShape SHAPE = Shapes.or(
            box(6.5, 6, 6.5, 9.5, 7, 9.5),
            box(5.5, 7, 5.5, 10.5, 8, 10.5),
            box(6.5, 0.1, 6.5, 9.5, 1.1, 9.5),
            box(5.5, 1, 5.5, 10.5, 6, 10.5)
    );

    public CrudeCrucibleBlock() {
        super(ExtendedProperties.of(MapColor.CLAY).strength(0.5F).noOcclusion().blockEntity(BlockEntities.CRUDE_CRUCIBLE).serverTicks(CrudeCrucibleBlockEntity::serverTick));
    }

    @Override
    public @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
        if(hand != InteractionHand.MAIN_HAND) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if(level.getBlockEntity(pos) instanceof CrudeCrucibleBlockEntity crudeCrucible) {
            ItemStack vessel = crudeCrucible.getStack();
            if(stack.isEmpty()) {
                player.setItemInHand(hand, vessel);
            } else {
                if(!player.addItem(vessel)) {
                    Helpers.spawnItem(level, pos, vessel);
                }
            }
            crudeCrucible.setStack(ItemStack.EMPTY);
            level.removeBlock(pos, true);
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public boolean canSurvive(@NotNull BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos pos = pPos.below();
        BlockState state = pLevel.getBlockState(pos);
        if(state.is(TFCBlocks.CHARCOAL_FORGE.get())) {
            return true;
        }

        return state.isFaceSturdy(pLevel, pos, Direction.UP);
    }

    @Override
    public void neighborChanged(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Block neighborBlock, @NotNull BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);

        if (level.isClientSide()) {
            return;
        }

        if (neighborPos.equals(pos.below())) {
            if (!state.canSurvive(level, pos)) {
                level.destroyBlock(pos, true);
            }
        }
    }


    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(@NotNull BlockState state, @NotNull HitResult target, LevelReader level, @NotNull BlockPos pos, @NotNull Player player) {
        if(level.getBlockEntity(pos) instanceof CrudeCrucibleBlockEntity crudeCrucible) {
            return crudeCrucible.getStack().copy();
        }

        return new ItemStack(TFCItems.VESSEL.get());
    }

    @Override
    public boolean onDestroyedByPlayer(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, boolean willHarvest, @NotNull FluidState fluid) {
        if(level.getBlockEntity(pos) instanceof CrudeCrucibleBlockEntity crudeCrucible) {
            if(player.isCreative()) {
                crudeCrucible.setStack(ItemStack.EMPTY);
            }
        }

        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    public void onRemove(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
        if(level.getBlockEntity(pos) instanceof CrudeCrucibleBlockEntity crudeCrucible) {
            Helpers.spawnItem(level, pos, crudeCrucible.getStack());
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }
}
