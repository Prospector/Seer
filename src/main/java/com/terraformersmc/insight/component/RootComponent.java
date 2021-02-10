package com.terraformersmc.insight.component;

import com.mojang.serialization.Codec;
import com.terraformersmc.insight.api.component.InsightComponent;
import com.terraformersmc.insight.api.component.InsightComponentType;
import com.terraformersmc.insight.api.component.context.BlockComponentContext;

import net.minecraft.client.util.math.MatrixStack;

public class RootComponent extends InsightComponent<BlockComponentContext> {
	protected static final Codec<RootComponent> CODEC = Codec.unit(RootComponent::new);

	@Override
	public InsightComponentType<RootComponent> getType() {
		return InsightBlockComponents.ROOT;
	}

	@Override
	public int getWidth(BlockComponentContext context) {
		return 64;
	}

	@Override
	public int getHeight(BlockComponentContext context) {
		return 32;
	}

	@Override
	public void render(BlockComponentContext context, MatrixStack matrices, float tickDelta, int x, int y) {
		return;
	}
}
