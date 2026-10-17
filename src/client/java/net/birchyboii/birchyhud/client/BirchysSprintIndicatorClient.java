package net.birchyboii.birchyhud.client;


import net.birchyboii.birchyhud.BirchysSprintIndicator;
import net.birchyboii.birchyhud.client.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;
import org.joml.Matrix3x2fStack;
import org.jspecify.annotations.Nullable;

public class BirchysSprintIndicatorClient implements ClientModInitializer {


	@Override
	public void onInitializeClient() {


			HudElementRegistry.attachElementBefore(VanillaHudElements.CROSSHAIR, Identifier.fromNamespaceAndPath(BirchysSprintIndicator.MOD_ID, "sprint"),
					(context, tickCounter) -> {
				Minecraft client = Minecraft.getInstance();

				if (client.player == null || client.level == null) return;
				if (client.options.hideGui) return;

				boolean isSprinting = client.player.isSprinting();
				boolean isPressingCtrl = client.player.input.keyPresses.sprint();
				boolean isToggleSprint = client.options.toggleSprint().get();

				double scaleFactor = client.getWindow().getGuiScale();
				int rawX = ModConfig.INSTANCE.customX;
				int rawY = ModConfig.INSTANCE.customY;

				int x = (int) (rawX / scaleFactor);
				int y = (int) (rawY / scaleFactor);


				if (ModConfig.INSTANCE.enableSprintIndicators) {

					String mainMessage = isSprinting ? ModConfig.INSTANCE.sprintIndicatorTextSprinting : "";
					String displayMessage = ModConfig.INSTANCE.sprintIndicatorTextNotSprinting;

					int mainColor = ModConfig.INSTANCE.sprintIndicatorColorSprinting;
					int secondaryColor = 0xFFFFFFFF;;
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


					drawStringWithCustomFontSize(context, client.font, mainMessage,
							x, y, mainColor, true, scale);
					drawStringWithCustomFontSize(context, client.font, displayMessage,
							x,y + client.font.lineHeight, secondaryColor, true, scale);
				}
			});


		ModConfig.load();
	}

	public static void drawStringWithCustomFontSize(GuiGraphics graphics, Font font, @Nullable String text,
													int x, int y, int color, boolean drawShadow, float scale) {
			Matrix3x2fStack matrices = graphics.pose();

			matrices.pushMatrix();
			matrices.scale(scale);

			graphics.drawString(font, text, x, y, color, drawShadow);

			matrices.popMatrix();

	}

}