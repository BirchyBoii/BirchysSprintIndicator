package net.birchyboii.birchyhud.client.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ModConfigScreen implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ModConfigScreen::createConfigScreen;
    }

    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Sprint Indicator Config"));

        ConfigCategory general = builder.getOrCreateCategory(Component.literal("General"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        builder.setSavingRunnable(ModConfig::save);

        general.addEntry(entryBuilder.startBooleanToggle(Component.literal("Enable Sprint Indicator"), ModConfig.INSTANCE.enableSprintIndicators)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Enables Or Disables Sprint Indicators!"))
                .setSaveConsumer(newValue -> {
                    ModConfig.INSTANCE.enableSprintIndicators = newValue;
                }).build());

        general.addEntry(entryBuilder.startFloatField(Component.literal("Font Size"), ModConfig.INSTANCE.fontSize)
                .setDefaultValue(1f)
                .setMax(2f)
                .setMin(0.25f)
                .setTooltip(Component.literal("Set Font Size"))
                .setSaveConsumer(newValue -> {
                    ModConfig.INSTANCE.fontSize = newValue;
                }).build());

        SubCategoryBuilder sprintingTextsDropdown = entryBuilder.startSubCategory(Component.literal("Text Settings"));
        sprintingTextsDropdown.setExpanded(false);


        sprintingTextsDropdown.add(entryBuilder.startStrField(Component.literal("Sprinting Indicator"), ModConfig.INSTANCE.sprintIndicatorTextSprinting)
                .setDefaultValue("Sprinting:")
                .setSaveConsumer(newValue -> {
                    ModConfig.INSTANCE.sprintIndicatorTextSprinting = newValue;
                }).build());
        sprintingTextsDropdown.add(entryBuilder.startAlphaColorField(Component.literal("Sprinting Indicator Color"), ModConfig.INSTANCE.sprintIndicatorColorSprinting)
                .setDefaultValue(0xFFFFFFFF)
                .setSaveConsumer(newValue -> {
                    ModConfig.INSTANCE.sprintIndicatorColorSprinting = newValue;
                }).build());

        sprintingTextsDropdown.add(entryBuilder.startStrField(Component.literal("Toggled Indicator"), ModConfig.INSTANCE.sprintIndicatorTextSprintingToggled)
                .setDefaultValue("(Toggled)")
                .setSaveConsumer(newValue -> {
                    ModConfig.INSTANCE.sprintIndicatorTextSprintingToggled = newValue;
                }).build());
        sprintingTextsDropdown.add(entryBuilder.startAlphaColorField(Component.literal("Toggled Indicator Color"), ModConfig.INSTANCE.sprintIndicatorColorSprintingToggled)
                .setDefaultValue(0xFF999999)
                .setSaveConsumer(newValue -> {
                    ModConfig.INSTANCE.sprintIndicatorColorSprintingToggled = newValue;
                }).build());

        sprintingTextsDropdown.add(entryBuilder.startStrField(Component.literal("Toggled, Key Held Indicator"), ModConfig.INSTANCE.sprintIndicatorTextSprintingToggledKeyHeld)
                .setDefaultValue("(Toggled, Key Held)")
                .setSaveConsumer(newValue -> {
                    ModConfig.INSTANCE.sprintIndicatorTextSprintingToggledKeyHeld = newValue;
                }).build());
        sprintingTextsDropdown.add(entryBuilder.startAlphaColorField(Component.literal("Toggled, Key Held Indicator Color"), ModConfig.INSTANCE.sprintIndicatorColorSprintingToggledKeyHeld)
                .setDefaultValue(0xFF999999)
                .setSaveConsumer(newValue -> {
                    ModConfig.INSTANCE.sprintIndicatorColorSprintingToggledKeyHeld = newValue;
                }).build());

        sprintingTextsDropdown.add(entryBuilder.startStrField(Component.literal("Key Held Indicator"), ModConfig.INSTANCE.sprintIndicatorTextSprintingKeyHeld)
                .setDefaultValue("(Key Held)")
                .setSaveConsumer(newValue -> {
                    ModConfig.INSTANCE.sprintIndicatorTextSprintingKeyHeld = newValue;
                }).build());
        sprintingTextsDropdown.add(entryBuilder.startAlphaColorField(Component.literal("Key Held Indicator Color"), ModConfig.INSTANCE.sprintIndicatorColorSprintingKeyHeld)
                .setDefaultValue(0xFF999999)
                .setSaveConsumer(newValue -> {
                    ModConfig.INSTANCE.sprintIndicatorColorSprintingKeyHeld = newValue;
                }).build());

        sprintingTextsDropdown.add(entryBuilder.startStrField(Component.literal("Key Not Held Indicator"), ModConfig.INSTANCE.sprintIndicatorTextSprintingKeyNotHeld)
                .setDefaultValue("(Key Not Held)")
                .setSaveConsumer(newValue -> {
                    ModConfig.INSTANCE.sprintIndicatorTextSprintingKeyNotHeld = newValue;
                }).build());
        sprintingTextsDropdown.add(entryBuilder.startAlphaColorField(Component.literal("Key Not Held Indicator Color"), ModConfig.INSTANCE.sprintIndicatorColorSprintingKeyNotHeld)
                .setDefaultValue(0xFF999999)
                .setSaveConsumer(newValue -> {
                    ModConfig.INSTANCE.sprintIndicatorColorSprintingKeyNotHeld = newValue;
                }).build());

        sprintingTextsDropdown.add(entryBuilder.startStrField(Component.literal("Not Sprinting Indicator"), ModConfig.INSTANCE.sprintIndicatorTextNotSprinting)
                .setDefaultValue("")
                .setSaveConsumer(newValue -> {
                    ModConfig.INSTANCE.sprintIndicatorTextNotSprinting = newValue;
                }).build());
        sprintingTextsDropdown.add(entryBuilder.startAlphaColorField(Component.literal("Not Sprinting Indicator Color"), ModConfig.INSTANCE.sprintIndicatorColorNotSprinting)
                .setDefaultValue(0x00000000)
                .setSaveConsumer(newValue -> {
                    ModConfig.INSTANCE.sprintIndicatorColorNotSprinting = newValue;
                }).build());


        general.addEntry(sprintingTextsDropdown.build());

        var screenPositionEnumEntry = entryBuilder.startEnumSelector(Component.literal("Choose Screen Position"),
                SprintIndicatorPositionsOption.class, ModConfig.INSTANCE.sprintIndicatorPositionsOption)
                .setDefaultValue(SprintIndicatorPositionsOption.TOP_LEFT)
                .setEnumNameProvider(enumValue -> {
                    return switch ((SprintIndicatorPositionsOption) enumValue) {
                        case TOP_LEFT -> Component.literal("Top Left");
                        case TOP_RIGHT -> Component.literal("Top Right");
                        case BOTTOM_RIGHT -> Component.literal("Bottom Right");
                        case BOTTOM_LEFT -> Component.literal("Bottom Left");
                        case CENTER -> Component.literal("Center");
                        case CUSTOM -> Component.literal("Custom Position");
                    };
                })
                .setSaveConsumer(newValue -> {
                    ModConfig.INSTANCE.sprintIndicatorPositionsOption = newValue;
                }).build();
        general.addEntry(screenPositionEnumEntry);

        var customXEntry = entryBuilder.startIntField(Component.literal("Custom X Position " +
                                "( Max: " + Minecraft.getInstance().getWindow().getScreenWidth() + ", Min: 0 )"),
                        ModConfig.INSTANCE.customX)
                .setDefaultValue(10)
                .setMin(0)
                .setMax(Minecraft.getInstance().getWindow().getScreenWidth())
                .setSaveConsumer(newValue -> {
                    ModConfig.INSTANCE.customX = newValue;})
                .setRequirement(() -> screenPositionEnumEntry.getValue() == SprintIndicatorPositionsOption.CUSTOM)
                .setDisplayRequirement(() -> screenPositionEnumEntry.getValue() == SprintIndicatorPositionsOption.CUSTOM)
                .build();

        var customYEntry = entryBuilder.startIntField(Component.literal("Custom Y Position " +
                                "( Max: " + Minecraft.getInstance().getWindow().getScreenHeight() + ", Min: 0 )"),
                        ModConfig.INSTANCE.customY)
                .setDefaultValue(10)
                .setMin(0)
                .setMax(Minecraft.getInstance().getWindow().getScreenWidth())
                .setSaveConsumer(newValue -> {
                    ModConfig.INSTANCE.customY = newValue;})
                .setRequirement(() -> screenPositionEnumEntry.getValue() == SprintIndicatorPositionsOption.CUSTOM)
                .setDisplayRequirement(() -> screenPositionEnumEntry.getValue() == SprintIndicatorPositionsOption.CUSTOM)
                .build();

        general.addEntry(customXEntry);
        general.addEntry(customYEntry);


        return builder.build();
    }
}
