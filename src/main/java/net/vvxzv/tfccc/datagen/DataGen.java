package net.vvxzv.tfccc.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.vvxzv.tfccc.TFCCrudeCrucible;
import net.vvxzv.tfccc.datagen.patchouli.TFCPatchouliDataEntryProvider;
import net.vvxzv.tfccc.datagen.patchouli.TFCPatchouliZhCnDataEntryProvider;
import net.vvxzv.tfccc.datagen.patchouli.TFCPatchouliZhDataEntryProvider;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = TFCCrudeCrucible.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(output, lookup, helper);
        gen.addProvider(event.includeServer(), blockTagsProvider);

        gen.addProvider(event.includeClient(), new TFCPatchouliDataEntryProvider(output));
        gen.addProvider(event.includeClient(), new TFCPatchouliZhCnDataEntryProvider(output));
        gen.addProvider(event.includeClient(), new TFCPatchouliZhDataEntryProvider(output, "zh_hk"));
        gen.addProvider(event.includeClient(), new TFCPatchouliZhDataEntryProvider(output, "zh_tw"));
    }
}
