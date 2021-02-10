package com.terraformersmc.insight.event;

import com.terraformersmc.insight.gui.hud.InsightHud;
import com.terraformersmc.insight.gui.screen.InsightPlacementScreen;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.hit.HitResult;

public class InsightEventHandler {
	public static void register() {
		HudRenderCallback.EVENT.register(InsightEventHandler::onRenderHud);
	}

	public static void onRenderHud(MatrixStack matrixStack, float tickDelta) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (InsightHud.instance == null) {
			InsightHud.instance = new InsightHud(client);
		}

		if (!client.options.debugEnabled && !(client.currentScreen instanceof InsightPlacementScreen)) {
			HitResult crosshairTarget = client.crosshairTarget;
			if (crosshairTarget != null && crosshairTarget.getType() != HitResult.Type.MISS) {
				InsightHud.instance.render(crosshairTarget, matrixStack, tickDelta);
			}
		}
	}
}
