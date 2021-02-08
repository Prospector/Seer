package com.terraformersmc.insight.api.component.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;

public class ItemStackComponentConfig implements InsightComponentConfig {
	public static final Codec<ItemStackComponentConfig> CODEC = RecordCodecBuilder.create(
			(instance) -> instance.group(
					ItemStack.CODEC.fieldOf("stack").forGetter(ItemStackComponentConfig::getStack)
			).apply(instance, ItemStackComponentConfig::new));

	private final ItemStack stack;

	public ItemStackComponentConfig(ItemStack stack) {
		this.stack = stack;
	}

	public ItemStack getStack() {
		return this.stack;
	}
}
