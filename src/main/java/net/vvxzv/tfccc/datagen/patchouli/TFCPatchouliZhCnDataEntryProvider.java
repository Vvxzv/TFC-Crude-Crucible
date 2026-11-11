package net.vvxzv.tfccc.datagen.patchouli;

import net.dries007.tfc.common.items.TFCItems;
import net.minecraft.ChatFormatting;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TFCPatchouliZhCnDataEntryProvider extends PatchouliDataProvider{
    public TFCPatchouliZhCnDataEntryProvider(PackOutput pOutput) {
        super(pOutput, "field_guide", "zh_cn", true);
    }

    @Override
    protected void buildData(Consumer<FinishedPatchouliData> data) {
        data.accept(
                new PatchouliDataBuilder(ResourceLocation.fromNamespaceAndPath("tfc", "getting_started"))
                        .addEntry(
                                new PatchouliDataBuilder.EntriesBuilder(
                                        ResourceLocation.parse("crude_crucible"),
                                        "简陋坩埚",
                                        TFCItems.VESSEL.get()
                                )
                                        .addTextPage(
                                                PatchouliDataBuilder.Text.of()
                                                        .add("通过手持")
                                                        .addThing("小缸")
                                                        .addChatFormatting("潜行+右键", ChatFormatting.GOLD)
                                                        .add("放置在")
                                                        .addThing("木炭炉")
                                                        .add("上方，可以获得很大程度上的加热速度。")
                                        )
                        )
        );
    }

    @Override
    public @NotNull String getName() {
        return "tfc_patchouli/zh_cn";
    }
}
