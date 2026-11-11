package net.vvxzv.tfccc.common.block.entity;

import net.dries007.tfc.common.blockentities.IHeatable;
import net.dries007.tfc.common.blockentities.TFCBlockEntity;
import net.dries007.tfc.common.component.heat.HeatCapability;
import net.dries007.tfc.common.component.heat.IHeat;
import net.dries007.tfc.common.component.heat.IHeatConsumer;
import net.dries007.tfc.util.calendar.ICalendarTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.vvxzv.tfccc.Config;
import net.vvxzv.tfccc.common.registry.BlockEntities;
import org.jetbrains.annotations.NotNull;

public class CrudeCrucibleBlockEntity extends TFCBlockEntity implements IHeatable, IHeatConsumer, ICalendarTickable {
    private ItemStack stack;
    private float temperature;
    private long lastCalendarUpdateTick;
    private int targetTemperatureStabilityTicks;

    public CrudeCrucibleBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntities.CRUDE_CRUCIBLE.get(), pos, state);
    }

    protected CrudeCrucibleBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.stack = ItemStack.EMPTY;
        this.temperature = 0;
        this.lastCalendarUpdateTick = Integer.MIN_VALUE;
    }

    @SuppressWarnings("unused")
    public static void serverTick(Level level, BlockPos pos, BlockState state, CrudeCrucibleBlockEntity crudeCrucible) {
        crudeCrucible.checkForCalendarUpdate();

        if (crudeCrucible.targetTemperatureStabilityTicks > 0) {
            crudeCrucible.targetTemperatureStabilityTicks--;
        }

        if (crudeCrucible.temperature > 0.0F && crudeCrucible.targetTemperatureStabilityTicks == 0) {
            crudeCrucible.temperature = HeatCapability.adjustTempTowards(crudeCrucible.temperature, 0.0F);
        }

        IHeat heat = HeatCapability.get(crudeCrucible.getStack());
        if(heat != null) {
            HeatCapability.addTemp(heat, crudeCrucible.temperature, 6);
            crudeCrucible.markForSync();
        }
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        super.saveAdditional(tag, provider);
        if (!this.stack.isEmpty()) {
            tag.put("item", this.stack.save(provider));
        }
        tag.putFloat("temperature", this.temperature);
        tag.putLong("lastCalendarUpdateTick", this.lastCalendarUpdateTick);
        tag.putInt("targetTemperatureStabilityTicks", this.targetTemperatureStabilityTicks);
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        super.loadAdditional(tag, provider);
        this.stack = ItemStack.parseOptional(provider, tag.getCompound("item"));
        this.temperature = tag.getFloat("temperature");
        this.lastCalendarUpdateTick = tag.getLong("lastCalendarUpdateTick");
        this.targetTemperatureStabilityTicks = tag.getInt("targetTemperatureStabilityTicks");
    }

    public static boolean isAllowItem(ItemStack stack) {
        return stack.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath("tfc", "fired_vessels")));
    }

    public void setStack(ItemStack stack) {
        if(isAllowItem(stack)) {
            this.stack = stack.copyWithCount(1);
        } else {
            this.stack = ItemStack.EMPTY;
        }
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public float getVesselTemperature() {
        IHeat heat = HeatCapability.get(this.stack);
        if(heat != null) {
            return heat.getTemperature();
        }

        return 0;
    }

    @Override
    public float getTemperature() {
        return this.temperature;
    }

    @Override
    public void setTemperature(float value) {
        this.temperature = value;
        this.targetTemperatureStabilityTicks = 5;
        this.markForSync();
    }

    @Override
    public void onCalendarUpdate(long ticks) {
        assert this.level != null;

        this.temperature = HeatCapability.adjustTempTowards(this.temperature, 0, (float)ticks);
    }

    @Override
    public long getLastCalendarUpdateTick() {
        return this.lastCalendarUpdateTick;
    }

    @Override
    public void setLastCalendarUpdateTick(long ticks) {
        this.lastCalendarUpdateTick = ticks;
    }
}
