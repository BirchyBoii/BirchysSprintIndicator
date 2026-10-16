package net.birchyboii.birchyhud.client;


import net.birchyboii.birchyhud.BirchysSprintIndicator;
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

			String mainMessage = isSprinting ? "Sprinting:" : "";
			String displayMessage = "";
			if (isSprinting) {
				if (isToggleSprint && isPressingCtrl) {
					displayMessage = "(Toggled)";
				} else if (isToggleSprint) {
					displayMessage = "(Toggled, Key Held)";
				} else if (isPressingCtrl) {
					displayMessage = "(Key Held)";
				} else {
					displayMessage = "(Key Not Held)";
				}
			}


			context.drawString(client.font, mainMessage, 10, 10, 0xFFFFFFFF, true);
			context.drawString(client.font, displayMessage, 10, 10 + client.font.lineHeight, 0xFF999999, true);
		});

	}

}