package com.terraformersmc.insight.component;

import com.terraformersmc.insight.api.component.context.ComponentContext;

import net.minecraft.text.Text;

public abstract class CachedTextComponent<C extends ComponentContext> extends AbstractTextComponent<C> {
	private Text cachedText;

	@Override
	protected final Text getText(C context) {
		if (this.cachedText == null) {
			this.cachedText = this.getUncachedText(context);
		}
		return this.cachedText;
	}

	public final void clearTextCache() {
		this.cachedText = null;
	}

	protected abstract Text getUncachedText(C context);
}
