package net.birchyboii.birchyhud.client;


import net.birchyboii.birchyhud.BirchysSprintIndicator;
import net.birchyboii.birchyhud.client.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

public class BirchysSprintIndicatorClient implements ClientModInitializer {


	@Override
	public void onInitializeClient() {


			HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, Identifier.fromNamespaceAndPath(BirchysSprintIndicator.MOD_ID, "sprint"), (context, tickCounter) -> {
				Minecraft client = Minecraft.getInstance();

				if (client.player == null || client.level == null) return;
				if (client.options.hideGui) return;

				boolean isSprinting = client.player.isSprinting();
				boolean isPressingCtrl = client.player.input.keyPresses.sprint();
				boolean isToggleSprint = client.options.toggleSprint().get();


				if (ModConfig.INSTANCE.enableSprintIndicators) {

					String mainMessage = isSprinting ? ModConfig.INSTANCE.sprintIndicatorTextSprinting : "";
					String displayMessage = ModConfig.INSTANCE.sprintIndicatorTextNotSprinting;

					int mainColor = ModConfig.INSTANCE.sprintIndicatorColorSprinting;
					int secondaryColor = 0xFFFFFFFF;;

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


					context.drawString(client.font, mainMessage, 10, 10, mainColor, true);
					context.drawString(client.font, displayMessage, 10, 10 + client.font.lineHeight, secondaryColor, true);
				}
			});


		ModConfig.load();
	}

}