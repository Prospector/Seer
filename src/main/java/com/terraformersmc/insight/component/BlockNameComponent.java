package com.terraformersmc.insight.component;

import com.mojang.serialization.Codec;
import com.terraformersmc.insight.api.component.InsightComponentType;
import com.terraformersmc.insight.api.component.context.BlockComponentContext;

import net.minecraft.text.Text;

public class BlockNameComponent extends CachedTextComponent<BlockComponentContext> {
	public static final Codec<BlockNameComponent> CODEC = Codec.unit(BlockNameComponent::new);

	@Override
	public InsightComponentType<BlockNameComponent> getType() {
		return InsightBlockComponents.BLOCK_NAME;
	}

	@Override
	protected Text getUncachedText(BlockComponentContext context) {
		return context.getState().getBlock().getName();
	}
}
