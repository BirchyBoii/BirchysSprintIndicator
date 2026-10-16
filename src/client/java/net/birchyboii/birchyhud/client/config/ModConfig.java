package net.birchyboii.birchyhud.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("sprint-indicators-config.json")
            .toFile();

    public boolean enableSprintIndicators = true;

    public String sprintIndicatorTextSprinting = "Sprinting:";
    public int sprintIndicatorColorSprinting = 0xFFFFFFFF;

    public String sprintIndicatorTextSprintingToggled = "(Toggled)";
    public int sprintIndicatorColorSprintingToggled = 0xFF999999;

    public String sprintIndicatorTextSprintingToggledKeyHeld = "(Toggled, Key Held)";
    public int sprintIndicatorColorSprintingToggledKeyHeld = 0xFF999999;

    public String sprintIndicatorTextSprintingKeyHeld = "(Key Held)";
    public int sprintIndicatorColorSprintingKeyHeld = 0xFF999999;

    public String sprintIndicatorTextSprintingKeyNotHeld = "(Key Not Held)";
    public int sprintIndicatorColorSprintingKeyNotHeld = 0xFF999999;

    public String sprintIndicatorTextNotSprinting = "";
    public int sprintIndicatorColorNotSprinting = 0x00000000;


    public static ModConfig INSTANCE = new ModConfig();

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(INSTANCE, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                INSTANCE = GSON.fromJson(reader, ModConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            save();
        }
    }
}