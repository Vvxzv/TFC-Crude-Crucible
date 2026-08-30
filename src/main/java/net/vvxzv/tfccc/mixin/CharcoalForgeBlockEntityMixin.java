package net.vvxzv.tfccc.mixin;

import net.dries007.tfc.common.blockentities.CharcoalForgeBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.vvxzv.tfccc.common.block.entity.CrudeCrucibleBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CharcoalForgeBlockEntity.class)
public class CharcoalForgeBlockEntityMixin {

    @Inject(method = "isItemValid", at = @At(value = "RETURN", ordinal = 1), cancellable = true, remap = false)
    private void isItemValid(int slot, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        boolean returnValue = cir.getReturnValue();
        cir.setReturnValue(returnValue && !CrudeCrucibleBlockEntity.isAllowItem(stack));
    }
}
