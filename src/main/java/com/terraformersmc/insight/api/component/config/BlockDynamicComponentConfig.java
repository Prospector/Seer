package com.terraformersmc.insight.api.component.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.terraformersmc.insight.api.component.context.BlockComponentContext;
import com.terraformersmc.insight.api.component.factory.ConfiguredComponentFactory;

public class BlockDynamicComponentConfig implements InsightComponentConfig {
	public static final Codec<BlockDynamicComponentConfig> CODEC = RecordCodecBuilder.create(
			(instance) -> instance.group(
					ConfiguredComponentFactory.BLOCK_CONFIGURED_COMPONENT_FACTORY_CODEC.fieldOf("factory").forGetter(BlockDynamicComponentConfig::getFactory)
			).apply(instance, BlockDynamicComponentConfig::new));

	private final ConfiguredComponentFactory<BlockComponentContext> factory;

	public BlockDynamicComponentConfig(ConfiguredComponentFactory<BlockComponentContext> factory) {
		this.factory = factory;
	}

	public ConfiguredComponentFactory<BlockComponentContext> getFactory() {
		return this.factory;
	}
}
