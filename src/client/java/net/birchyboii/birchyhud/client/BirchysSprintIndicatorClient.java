package net.birchyboii.birchyhud.client;

import net.birchyboii.birchyhud.BirchysSprintIndicator;
import net.birchyboii.birchyhud.client.config.ModConfig;
import net.birchyboii.birchyhud.client.config.SprintIndicatorPositionsOption;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.joml.Matrix3x2fStack;
import org.jspecify.annotations.Nullable;

public class BirchysSprintIndicatorClient implements ClientModInitializer {


	@Override
	public void onInitializeClient() {


		HudElementRegistry.attachElementBefore(VanillaHudElements.CROSSHAIR, Identifier.fromNamespaceAndPath(BirchysSprintIndicator.MOD_ID, "sprint"),
				this::renderSprintHud);


		ModConfig.load();
	}

	public void renderSprintHud(GuiGraphicsExtractor context, DeltaTracker tickCounter) {
		Minecraft client = Minecraft.getInstance();

		if (client.player == null || client.level == null) return;

		boolean isSprinting = client.player.isSprinting();
		boolean isPressingCtrl = client.player.input.keyPresses.sprint();
		boolean isToggleSprint = client.options.toggleSprint().get();

		double scaleFactor = client.getWindow().getGuiScale();
		int rawX = ModConfig.INSTANCE.getX();
		int rawY = ModConfig.INSTANCE.getY();

		int x = (int) (rawX / scaleFactor);
		int y = (int) (rawY / scaleFactor);


		if (ModConfig.INSTANCE.enableSprintIndicators) {

			String mainMessage = isSprinting ? ModConfig.INSTANCE.sprintIndicatorTextSprinting : "";
			String displayMessage = ModConfig.INSTANCE.sprintIndicatorTextNotSprinting;


			int mainColor = ModConfig.INSTANCE.sprintIndicatorColorSprinting;
			int secondaryColor = 0xFFFFFFFF;
			float scale = ModConfig.INSTANCE.fontSize;


			if (isSprinting) {
				if (isToggleSprint && isPressingCtrl) {
					displayMessage = ModConfig.INSTANCE.sprintIndicatorTextSprintingToggled;
					secondaryColor = ModConfig.INSTANCE.sprintIndicatorColorSprintingToggled;
				} else if (isToggleSprint) {
					displayMessage = ModConfig.INSTANCE.sprintIndicatorTextSprintingToggledKeyHeld;
					secondaryColor = ModConfig.INSTANCE.sprintIndicatorColorSprintingToggledKeyHeld;
				} else if (isPressingCtrl) {
					displayMessage = ModConfig.INSTANCE.sprintIndicatorTextSprintingKeyHeld;
					secondaryColor = ModConfig.INSTANCE.sprintIndicatorColorSprintingKeyHeld;
				} else {
					displayMessage = ModConfig.INSTANCE.sprintIndicatorTextSprintingKeyNotHeld;
					secondaryColor = ModConfig.INSTANCE.sprintIndicatorColorSprintingKeyNotHeld;
				}
			}
			int messageWidth = Mth.absMax(client.font.width(mainMessage), client.font.width(displayMessage));

			if (ModConfig.INSTANCE.sprintIndicatorPositionsOption == SprintIndicatorPositionsOption.TOP_RIGHT ||
					ModConfig.INSTANCE.sprintIndicatorPositionsOption == SprintIndicatorPositionsOption.BOTTOM_RIGHT) {
				x -= messageWidth;
			} if (ModConfig.INSTANCE.sprintIndicatorPositionsOption == SprintIndicatorPositionsOption.BOTTOM_LEFT ||
					ModConfig.INSTANCE.sprintIndicatorPositionsOption == SprintIndicatorPositionsOption.BOTTOM_RIGHT) {
				y -= client.font.lineHeight * 2;
			} if (ModConfig.INSTANCE.sprintIndicatorPositionsOption == SprintIndicatorPositionsOption.CENTER) {
				x -= (messageWidth / 2);
				y -= (client.font.lineHeight / 2) - client.font.lineHeight;
			}

			float inverseScale = 1.0f / scale;
			int finalX = (int) (x * inverseScale);
			int finalY = (int) (y * inverseScale);

			drawStringWithCustomFontSize(context, client.font, mainMessage,
					finalX, finalY, mainColor, true, scale);
			drawStringWithCustomFontSize(context, client.font, displayMessage,
					finalX,finalY + (int) (client.font.lineHeight * scale), secondaryColor, true, scale);
		}
	}

	public static void drawStringWithCustomFontSize(GuiGraphicsExtractor graphics, Font font, @Nullable String text,
													int x, int y, int color, boolean drawShadow, float scale) {
		Matrix3x2fStack matrices = graphics.pose();

		matrices.pushMatrix();
		matrices.scale(scale);

		graphics.text(font, text, x, y, color, drawShadow);

		matrices.popMatrix();

	}


}