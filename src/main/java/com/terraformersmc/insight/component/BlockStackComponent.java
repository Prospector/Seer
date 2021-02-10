package com.terraformersmc.insight.component;

import com.mojang.serialization.Codec;
import com.terraformersmc.insight.api.component.InsightComponentType;
import com.terraformersmc.insight.api.component.context.BlockComponentContext;

import net.minecraft.item.ItemStack;

public class BlockStackComponent extends AbstractItemStackComponent<BlockComponentContext> {
	public static final Codec<BlockStackComponent> CODEC = Codec.unit(BlockStackComponent::new);

	@Override
	public InsightComponentType<BlockStackComponent> getType() {
		return InsightBlockComponents.BLOCK_STACK;
	}

	@Override
	protected ItemStack getStack(BlockComponentContext context) {
		return context.getState().getBlock().asItem().getDefaultStack();
	}
}
