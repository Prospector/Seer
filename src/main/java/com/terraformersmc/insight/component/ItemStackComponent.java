package com.terraformersmc.insight.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.terraformersmc.insight.api.component.InsightComponentType;
import com.terraformersmc.insight.api.component.context.BlockComponentContext;

import net.minecraft.item.ItemStack;

public class ItemStackComponent extends AbstractItemStackComponent<BlockComponentContext> {
	public static final Codec<ItemStackComponent> CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
			ItemStack.CODEC.fieldOf("stack").forGetter(stack -> stack.stack)
		).apply(instance, ItemStackComponent::new);
	});

	private final ItemStack stack;

	public ItemStackComponent(ItemStack stack) {
		this.stack = stack;
	}

	@Override
	public InsightComponentType<ItemStackComponent> getType() {
		return InsightBlockComponents.ITEM_STACK;
	}

	@Override
	protected ItemStack getStack(BlockComponentContext context) {
		return this.stack;
	}
}
