package com.terraformersmc.insight.component;

import com.terraformersmc.insight.api.component.InsightComponent;
import com.terraformersmc.insight.api.component.context.ComponentContext;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public abstract class AbstractTextComponent<C extends ComponentContext> extends InsightComponent<C> {
	protected abstract Text getText(C context);

	@Override
	public int getWidth(C context) {
		return context.getClient().textRenderer.getWidth(this.getText(context));
	}

	@Override
	public int getHeight(C context) {
		return context.getClient().textRenderer.fontHeight;
	}

	@Override
	public void render(C context, MatrixStack matrices, float tickDelta, int x, int y) {
		context.getClient().textRenderer.draw(matrices, this.getText(context), x, y, 0xFFFFFF);
	}
}
