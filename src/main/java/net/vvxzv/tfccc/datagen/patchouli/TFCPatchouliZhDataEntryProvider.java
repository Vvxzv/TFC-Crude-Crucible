package net.vvxzv.tfccc.datagen.patchouli;

import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.ChatFormatting;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TFCPatchouliZhDataEntryProvider extends PatchouliDataProvider{
    private final String lang;

    public TFCPatchouliZhDataEntryProvider(PackOutput pOutput, String lang) {
        super(pOutput, "field_guide", lang, true);
        this.lang = lang;
    }

    @Override
    protected void buildData(Consumer<FinishedPatchouliData> data) {
        data.accept(
                new PatchouliDataBuilder(ResourceLocation.fromNamespaceAndPath("tfc", "getting_started"))
                        .addEntry(
                                new PatchouliDataBuilder.EntriesBuilder(
                                        ResourceLocation.parse("crude_crucible"),
                                        "簡陋坩堝",
                                        TFCItems.VESSEL.get()
                                )
                                        .addTextPage(
                                                PatchouliDataBuilder.Text.of()
                                                        .add("通過手持")
                                                        .addThing("小缸")
                                                        .addChatFormatting("潜行+右鍵", ChatFormatting.GOLD)
                                                        .add("放置在")
                                                        .addThing("木炭爐")
                                                        .add("上方，可以獲得很大程度上的加熱速度。")
                                        )
                        )
        );
    }

    @Override
    public @NotNull String getName() {
        return "tfc_patchouli/" + this.lang;
    }
}
