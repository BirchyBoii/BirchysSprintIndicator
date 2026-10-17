package net.birchyboii.birchyhud.client.config;

import net.minecraft.client.Minecraft;

import java.util.function.IntSupplier;

public enum SprintIndicatorPositionsOption {
    TOP_LEFT(
            () -> 10,
            () -> 10
    ),
    TOP_RIGHT(
            () -> Minecraft.getInstance().getWindow().getScreenWidth() - 10,
            () -> 10
    ),

    BOTTOM_RIGHT(
            () -> Minecraft.getInstance().getWindow().getScreenWidth() - 10,
            () -> Minecraft.getInstance().getWindow().getScreenHeight() - 10
    ),

    BOTTOM_LEFT(
            () -> 10,
            () -> Minecraft.getInstance().getWindow().getScreenHeight() - 10
    ),

    CENTER(
            () -> (Minecraft.getInstance().getWindow().getScreenWidth() / 2),
            () -> (Minecraft.getInstance().getWindow().getScreenHeight() / 2)
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
