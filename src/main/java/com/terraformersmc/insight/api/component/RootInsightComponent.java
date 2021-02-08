package com.terraformersmc.insight.api.component;

import com.mojang.serialization.Codec;
import com.terraformersmc.insight.api.component.config.ChildrenComponentConfig;
import com.terraformersmc.insight.api.component.config.RootComponentConfig;
import com.terraformersmc.insight.api.component.context.ComponentContext;
import com.terraformersmc.insight.config.InsightConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class RootInsightComponent extends InsightComponent<RootComponentConfig, ComponentContext> {
	public RootInsightComponent(Codec<RootComponentConfig> codec) {
		super(codec);
	}

	@Override
	public int getWidth(RootComponentConfig config, ComponentContext context) {
		int width = 0;
		int leftWidth = config.getIcon().getWidth(context);
		int rightWidth = Math.max(Math.max(config.getHeader().getWidth(context), config.getBody().getWidth(context)), config.getFooter().getWidth(context));
		width += leftWidth;
		width += rightWidth;
		if (leftWidth > 0 && rightWidth > 0) {
			width += InsightConfig.innerPadding;
		}
		if (width > 0) {
			width += InsightConfig.outerPadding * 2;
		}
		return width;
	}

	@Override
	public int getHeight(RootComponentConfig config, ComponentContext context) {
		int height = 0;
		int leftHeight = config.getIcon().getHeight(context);
		int rightHeight = config.getHeader().getHeight(context) + config.getBody().getHeight(context) + config.getFooter().getHeight(context);
		height += Math.max(leftHeight, rightHeight);
		if (height > 0) {
			height += InsightConfig.outerPadding * 2;
		}
		return height;
	}

	@Override
	public void render(RootComponentConfig config, ComponentContext context, MatrixStack matrixStack, float tickDelta, int x, int y, MinecraftClient client) {
		ConfiguredInsightComponent<ComponentContext, ChildrenComponentConfig, SelectLastInsightComponent> icon = config.getIcon();
		icon.renderTypeless(context, matrixStack, tickDelta, x, y, client);
		int iconWidth = icon.getWidthTypeless(context);
		if (iconWidth > 0) {
			x += iconWidth;
			x += InsightConfig.innerPadding;
		}
		int headerHeight = config.getHeader().getHeightTypeless(context);
		int bodyHeight = config.getBody().getHeightTypeless(context);
		int footerHeight = config.getFooter().getHeightTypeless(context);
		if (headerHeight > 0) {
			config.getHeader().renderTypeless(context, matrixStack, tickDelta, x, y, client);
			y += headerHeight;
			if (bodyHeight > 0 || footerHeight > 0) {
				y += InsightConfig.innerPadding;
			}
		}
		if (bodyHeight > 0) {
			config.getBody().renderTypeless(context, matrixStack, tickDelta, x, y, client);
			y += bodyHeight;
			if (footerHeight > 0) {
				y += InsightConfig.innerPadding;
			}
		}
		if (footerHeight > 0) {
			config.getFooter().renderTypeless(context, matrixStack, tickDelta, x, y, client);
		}
	}
}
