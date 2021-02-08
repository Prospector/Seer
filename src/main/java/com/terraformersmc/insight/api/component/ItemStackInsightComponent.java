package com.terraformersmc.insight.api.component;

import com.mojang.serialization.Codec;
import com.terraformersmc.insight.api.component.config.ItemStackComponentConfig;
import com.terraformersmc.insight.api.component.context.ComponentContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class ItemStackInsightComponent extends InsightComponent<ItemStackComponentConfig, ComponentContext> {

	public ItemStackInsightComponent(Codec<ItemStackComponentConfig> codec) {
		super(codec);
	}

	@Override
	public int getWidth(ItemStackComponentConfig config, ComponentContext context) {
		return config.getStack().isEmpty() ? 0 : 16;
	}

	@Override
	public int getHeight(ItemStackComponentConfig config, ComponentContext context) {
		return config.getStack().isEmpty() ? 0 : 16;
	}

	@Override
	public void render(ItemStackComponentConfig config, ComponentContext context, MatrixStack matrixStack, float tickDelta, int x, int y, MinecraftClient client) {
		client.getItemRenderer().renderInGui(config.getStack(), x, y);
	}
}
