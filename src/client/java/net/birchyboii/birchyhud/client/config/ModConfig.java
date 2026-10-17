package net.birchyboii.birchyhud.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.IntSupplier;

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

    public float fontSize = 1;

    public SprintIndicatorPositionsOption sprintIndicatorPositionsOption = SprintIndicatorPositionsOption.TOP_LEFT;
    public int customX = 10;
    public int customY = 10;

    public int getX() {
        return sprintIndicatorPositionsOption == SprintIndicatorPositionsOption.CUSTOM ? customX : sprintIndicatorPositionsOption.getX();
    }

    public int getY() {
        return sprintIndicatorPositionsOption == SprintIndicatorPositionsOption.CUSTOM ? customY : sprintIndicatorPositionsOption.getY();
    }


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

enum SprintIndicatorPositionsOption {
    TOP_LEFT(
            () -> 10,
            () -> 10
    ),
    TOP_RIGHT(
            () -> Minecraft.getInstance().getWindow().getGuiScaledWidth() - 10,
            () -> 10
    ),
    BOTTOM_RIGHT(
            () -> Minecraft.getInstance().getWindow().getGuiScaledWidth() - 10,
            () -> Minecraft.getInstance().getWindow().getGuiScaledHeight() - 10
    ),
    BOTTOM_LEFT(
            () -> 10,
            () -> Minecraft.getInstance().getWindow().getGuiScaledHeight() - 10
    ),
    CENTER(
            () -> Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2,
            () -> Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2
    ),
    CUSTOM(
            () -> -1,
            () -> -1
    );

    private final IntSupplier xSupplier;
    private final IntSupplier ySupplier;

    SprintIndicatorPositionsOption(IntSupplier xSupplier, IntSupplier ySupplier) {
        this.xSupplier = xSupplier;
        this.ySupplier = ySupplier;
    }

    public int getX() {
        return xSupplier.getAsInt();
    }

    public int getY() {
        return ySupplier.getAsInt();
    }
}
