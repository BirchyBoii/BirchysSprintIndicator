package net.birchyboii.birchyhud.client.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
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

        return builder.build();
    }
}
