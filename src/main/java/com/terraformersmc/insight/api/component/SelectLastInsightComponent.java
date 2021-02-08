package com.terraformersmc.insight.api.component;

import com.mojang.serialization.Codec;
import com.terraformersmc.insight.api.component.config.ChildrenComponentConfig;
import com.terraformersmc.insight.api.component.context.ComponentContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;

public class SelectLastInsightComponent extends InsightComponent<ChildrenComponentConfig, ComponentContext> {

	public SelectLastInsightComponent(Codec<ChildrenComponentConfig> codec) {
		super(codec);
	}

	@Override
	public int getWidth(ChildrenComponentConfig config, ComponentContext context) {
		ConfiguredInsightComponent<?, ?, ?> lastValid = getLastValid(config, context);
		return lastValid != null ? lastValid.getWidthTypeless(context) : 0;
	}

	@Override
	public int getHeight(ChildrenComponentConfig config, ComponentContext context) {
		ConfiguredInsightComponent<?, ?, ?> lastValid = getLastValid(config, context);
		return lastValid != null ? lastValid.getHeightTypeless(context) : 0;
	}

	@Override
	public void render(ChildrenComponentConfig config, ComponentContext context, MatrixStack matrixStack, float tickDelta, int x, int y, MinecraftClient client) {
		ConfiguredInsightComponent<?, ?, ?> lastValid = getLastValid(config, context);
		if (lastValid != null) {
			lastValid.renderTypeless(context, matrixStack, tickDelta, x, y, client);
		}
	}

	private ConfiguredInsightComponent<?, ?, ?> getLastValid(ChildrenComponentConfig config, ComponentContext context) {
		List<ConfiguredInsightComponent<?, ?, ?>> children = config.getChildren();
		for (int i = children.size() - 1; i >= 0; i--) {
			ConfiguredInsightComponent<?, ?, ?> component = children.get(i);
			if (component.getWidthTypeless(context) > 0 && component.getHeightTypeless(context) > 0) {
				return component;
			}
		}
		return null;
	}
}
