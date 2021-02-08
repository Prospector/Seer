package com.terraformersmc.insight.api.component;

import com.mojang.serialization.Codec;
import com.terraformersmc.insight.api.component.config.ChildrenComponentConfig;
import com.terraformersmc.insight.api.component.context.ComponentContext;
import com.terraformersmc.insight.config.InsightConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;

public class ColumnInsightComponent extends InsightComponent<ChildrenComponentConfig, ComponentContext> {

	public ColumnInsightComponent(Codec<ChildrenComponentConfig> codec) {
		super(codec);
	}

	@Override
	public int getWidth(ChildrenComponentConfig config, ComponentContext context) {
		int width = 0;
		for (ConfiguredInsightComponent<?, ?, ?> child : config.getChildren()) {
			int childWidth = child.getWidthTypeless(context);
			if (isValid(child, context) && childWidth > width) {
				width = childWidth;
			}
		}
		return width;
	}

	@Override
	public int getHeight(ChildrenComponentConfig config, ComponentContext context) {
		int height = 0;
		for (ConfiguredInsightComponent<?, ?, ?> child : config.getChildren()) {
			if (isValid(child, context)) {
				height += child.getHeightTypeless(context);
			}
		}
		return height;
	}

	@Override
	public void render(ChildrenComponentConfig config, ComponentContext context, MatrixStack matrixStack, float tickDelta, int x, int y, MinecraftClient client) {
		List<ConfiguredInsightComponent<?, ?, ?>> children = config.getChildren();
		for (int i = 0; i < children.size(); i++) {
			ConfiguredInsightComponent<?, ?, ?> child = children.get(i);
			if (isValid(child, context)) {
				child.renderTypeless(context, matrixStack, tickDelta, x, y, client);
				if (i + 1 < children.size()) {
					y += InsightConfig.innerPadding;
				}
			}
		}
	}

	private boolean isValid(ConfiguredInsightComponent<?, ?, ?> component, ComponentContext context) {
		return component.getWidthTypeless(context) > 0 && component.getHeightTypeless(context) > 0;
	}
}
