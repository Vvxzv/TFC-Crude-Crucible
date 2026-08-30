package net.vvxzv.tfccc;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.vvxzv.tfccc.common.block.entity.CrudeCrucibleBlockEntity;
import net.vvxzv.tfccc.common.registry.Blocks;

public class ForgeEventHandler {

    public static void init() {
        IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.addListener(ForgeEventHandler::placeCrudeCrucible);
    }

    public static void placeCrudeCrucible(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        Player player = event.getEntity();

        if (player == null || !player.isShiftKeyDown()) {
            return;
        }

        if (event.getHand() != InteractionHand.MAIN_HAND) {
            return;
        }

        Direction face = event.getFace();
        if (face == null) {
            return;
        }

        BlockPos clickPos = event.getPos();
        BlockState clickedState = level.getBlockState(clickPos);
        BlockPos placePos = clickedState.canBeReplaced() ? clickPos : clickPos.relative(face);

        ItemStack stack = player.getItemInHand(event.getHand());
        if(CrudeCrucibleBlockEntity.isAllowItem(stack)) {
            BlockState state = Blocks.CRUDE_CRUCIBLE.get().defaultBlockState();

            if (!level.isUnobstructed(state, placePos, CollisionContext.of(player))) {
                return;
            }

            if(!allowCrudeCruciblePlaceOn(level, placePos)) {
                return;
            }

            if (level.isClientSide()) {
                SoundType soundtype = state.getSoundType(level, placePos, player);
                level.playSound(
                        player,
                        placePos,
                        soundtype.getPlaceSound(),
                        SoundSource.BLOCKS,
                        (soundtype.getVolume() + 1.0F) / 2.0F,
                        soundtype.getPitch() * 0.8F
                );
                return;
            }

            level.setBlockAndUpdate(placePos, state);

            if(level.getBlockEntity(placePos) instanceof CrudeCrucibleBlockEntity crudeCrucible) {
                crudeCrucible.setStack(stack);
                if(!player.isCreative()) {
                    stack.shrink(1);
                }
            }

            event.setCanceled(true);
        }
    }

    private static boolean allowCrudeCruciblePlaceOn(Level level, BlockPos cruciblePos) {
        BlockPos pos = cruciblePos.below();
        BlockState state = level.getBlockState(pos);
        if(state.is(TFCBlocks.CHARCOAL_FORGE.get())) {
            return true;
        }

        return state.isFaceSturdy(level, pos, Direction.UP);
    }
}
