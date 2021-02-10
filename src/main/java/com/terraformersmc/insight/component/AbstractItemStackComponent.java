package com.terraformersmc.insight.component;

import com.terraformersmc.insight.api.component.InsightComponent;
import com.terraformersmc.insight.api.component.context.ComponentContext;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public abstract class AbstractItemStackComponent<C extends ComponentContext> extends InsightComponent<C> {
	protected abstract ItemStack getStack(C context);

	@Override
	public int getWidth(C context) {
		return this.getStack(context).isEmpty() ? 0 : 16;
	}

	@Override
	public int getHeight(C context) {
		return this.getStack(context).isEmpty() ? 0 : 16;
	}

	@Override
	public void render(C context, MatrixStack matrices, float tickDelta, int x, int y) {
		context.getClient().getItemRenderer().renderInGui(this.getStack(context), x, y);
	}
}
