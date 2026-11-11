package net.vvxzv.tfccc.datagen.patchouli;

import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.ChatFormatting;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TFCPatchouliDataEntryProvider extends PatchouliDataProvider{
    public TFCPatchouliDataEntryProvider(PackOutput pOutput) {
        super(pOutput, "field_guide", "en_us", true);
    }

    @Override
    protected void buildData(Consumer<FinishedPatchouliData> data) {
        data.accept(
                new PatchouliDataBuilder(ResourceLocation.fromNamespaceAndPath("tfc", "getting_started"))
                        .addEntry(
                                new PatchouliDataBuilder.EntriesBuilder(
                                        ResourceLocation.parse("crude_crucible"),
                                        "Crude Crucible",
                                        TFCItems.VESSEL.get()
                                )
                                        .addTextPage(
                                                PatchouliDataBuilder.Text.of()
                                                        .add("By holding ")
                                                        .addThing("Vessel ")
                                                        .addChatFormatting("Shift + Right-Click ", ChatFormatting.GOLD)
                                                        .add("and placing it above ")
                                                        .addThing("Charcoal Forge, ")
                                                        .add("a high heating speed can be achieved.")
                                        )
                        )
        );
    }

    @Override
    public @NotNull String getName() {
        return "tfc_patchouli/en_us";
    }
}
