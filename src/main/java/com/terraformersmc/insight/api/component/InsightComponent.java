package com.terraformersmc.insight.api.component;

import com.terraformersmc.insight.api.component.context.ComponentContext;

import net.minecraft.client.util.math.MatrixStack;

public abstract class InsightComponent<C extends ComponentContext> {
	public abstract InsightComponentType<? extends InsightComponent<C>> getType();

	public abstract int getWidth(C context);
	public abstract int getHeight(C context);

	public abstract void render(C context, MatrixStack matrices, float tickDelta, int x, int y);
}
