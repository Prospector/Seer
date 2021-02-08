package com.terraformersmc.insight.api.component;

import com.mojang.serialization.Codec;
import com.terraformersmc.insight.api.InsightRegistries;
import com.terraformersmc.insight.api.component.config.InsightComponentConfig;
import com.terraformersmc.insight.api.component.context.ComponentContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class ConfiguredInsightComponent<X extends ComponentContext, CC extends InsightComponentConfig, C extends InsightComponent<CC, X>> {

	public static final Codec<ConfiguredInsightComponent<?, ?, ?>> CODEC = InsightRegistries.COMPONENTS.dispatch(ConfiguredInsightComponent::getComponent, InsightComponent::getCodec);

	private final C component;
	private final CC config;

	public ConfiguredInsightComponent(C component, CC config) {
		this.component = component;
		this.config = config;
	}

	public C getComponent() {
		return component;
	}

	public CC getConfig() {
		return config;
	}

	public int getWidth(X context) {
		return component.getWidth(config, context);
	}

	public int getHeight(X context) {
		return component.getHeight(config, context);
	}

	public void render(X context, MatrixStack matrixStack, float tickDelta, int x, int y, MinecraftClient client) {
		component.render(config, context, matrixStack, tickDelta, x, y, client);
	}

	public int getWidthTypeless(ComponentContext context) {
		return getWidth((X) context);
	}

	public int getHeightTypeless(ComponentContext context) {
		return getHeight((X) context);
	}

	public void renderTypeless(ComponentContext context, MatrixStack matrixStack, float tickDelta, int x, int y, MinecraftClient client) {
		render((X) context, matrixStack, tickDelta, x, y, client);
	}
}
