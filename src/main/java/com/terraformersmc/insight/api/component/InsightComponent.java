package com.terraformersmc.insight.api.component;

import com.mojang.serialization.Codec;
import com.terraformersmc.insight.api.component.config.InsightComponentConfig;
import com.terraformersmc.insight.api.component.context.ComponentContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public abstract class InsightComponent<C extends InsightComponentConfig, X extends ComponentContext> {

	private final Codec<ConfiguredInsightComponent<X, C, InsightComponent<C, X>>> codec;

	public InsightComponent(Codec<C> codec) {
		this.codec = codec.fieldOf("config").xmap((config) -> new ConfiguredInsightComponent<>(this, config), ConfiguredInsightComponent::getConfig).codec();
	}

	public abstract int getWidth(C config, X context);

	public abstract int getHeight(C config, X context);

	public final int getWidthTypeless(InsightComponentConfig config, ComponentContext context) {
		return this.getWidth((C) config, (X) context);
	}

	public final int getHeightTypeless(InsightComponentConfig config, ComponentContext context) {
		return this.getHeight((C) config, (X) context);
	}

	public abstract void render(C config, X context, MatrixStack matrixStack, float tickDelta, int x, int y, MinecraftClient client);

	public ConfiguredInsightComponent<X, C, InsightComponent<C, X>> configure(C config) {
		return new ConfiguredInsightComponent<>(this, config);
	}

	public Codec<ConfiguredInsightComponent<X, C, InsightComponent<C, X>>> getCodec() {
		return codec;
	}
}
