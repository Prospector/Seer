package com.terraformersmc.insight.api.component;

import com.mojang.serialization.Codec;
import com.terraformersmc.insight.api.component.config.TextComponentConfig;
import com.terraformersmc.insight.api.component.context.ComponentContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class TextInsightComponent extends InsightComponent<TextComponentConfig, ComponentContext> {

	public TextInsightComponent(Codec<TextComponentConfig> codec) {
		super(codec);
	}

	@Override
	public int getWidth(TextComponentConfig config, ComponentContext context) {
		return MinecraftClient.getInstance().textRenderer.getWidth(config.getText());
	}

	@Override
	public int getHeight(TextComponentConfig config, ComponentContext context) {
		return MinecraftClient.getInstance().textRenderer.fontHeight;
	}

	@Override
	public void render(TextComponentConfig config, ComponentContext context, MatrixStack matrixStack, float tickDelta, int x, int y, MinecraftClient client) {
		client.textRenderer.draw(matrixStack, config.getText(), x, y, 0xFFFFFF);
	}
}
