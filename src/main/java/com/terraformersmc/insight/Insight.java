package com.terraformersmc.insight;

import com.terraformersmc.insight.component.InsightBlockComponents;
import com.terraformersmc.insight.config.InsightConfigManager;
import com.terraformersmc.insight.event.InsightEventHandler;

import net.fabricmc.api.ModInitializer;

public class Insight implements ModInitializer {

	public static final String MOD_ID = "insight";

	private static void register() {
		InsightEventHandler.register();
		InsightBlockComponents.register();
	}

	@Override
	public void onInitialize() {
		InsightConfigManager.initializeConfig();
		register();
	}

}
