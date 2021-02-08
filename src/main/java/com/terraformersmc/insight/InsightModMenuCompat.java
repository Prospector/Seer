package com.terraformersmc.insight;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.terraformersmc.insight.gui.screen.InsightPlacementScreen;

public class InsightModMenuCompat implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return InsightPlacementScreen::new;
	}
}
