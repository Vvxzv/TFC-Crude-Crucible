package net.vvxzv.tfccc.common.block.entity;

import net.dries007.tfc.common.blockentities.TFCBlockEntity;
import net.dries007.tfc.common.capabilities.SidedHandler;
import net.dries007.tfc.common.capabilities.heat.HeatCapability;
import net.dries007.tfc.common.capabilities.heat.IHeat;
import net.dries007.tfc.common.capabilities.heat.IHeatBlock;
import net.dries007.tfc.util.calendar.ICalendarTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.vvxzv.tfccc.Config;
import net.vvxzv.tfccc.common.registry.BlockEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CrudeCrucibleBlockEntity extends TFCBlockEntity implements IHeatBlock, ICalendarTickable {
    private ItemStack stack;
    private float temperature;
    private long lastCalendarUpdateTick;
    private int targetTemperatureStabilityTicks;
    private final SidedHandler.Noop<IHeatBlock> sidedHeat;

    public CrudeCrucibleBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntities.CRUDE_CRUCIBLE.get(), pos, state);
    }

    protected CrudeCrucibleBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.stack = ItemStack.EMPTY;
        this.temperature = 0;
        this.lastCalendarUpdateTick = Integer.MIN_VALUE;
        this.sidedHeat = new SidedHandler.Noop<>(this);
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
    public void loadAdditional(@NotNull CompoundTag nbt) {
        super.loadAdditional(nbt);
        this.stack = ItemStack.of(nbt.getCompound("item"));
        this.temperature = nbt.getFloat("temperature");
        this.lastCalendarUpdateTick = nbt.getLong("lastCalendarUpdateTick");
        this.targetTemperatureStabilityTicks = nbt.getInt("targetTemperatureStabilityTicks");
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("item", this.stack.save(new CompoundTag()));
        nbt.putFloat("temperature", this.temperature);
        nbt.putLong("lastCalendarUpdateTick", this.lastCalendarUpdateTick);
        nbt.putInt("targetTemperatureStabilityTicks", this.targetTemperatureStabilityTicks);
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

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == HeatCapability.BLOCK_CAPABILITY ? this.sidedHeat.getSidedHandler(side).cast() : super.getCapability(cap, side);
    }
}
