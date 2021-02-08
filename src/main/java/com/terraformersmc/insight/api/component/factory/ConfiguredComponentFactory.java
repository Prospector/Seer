package com.terraformersmc.insight.api.component.factory;

import com.mojang.serialization.Codec;
import com.terraformersmc.insight.api.InsightRegistries;
import com.terraformersmc.insight.api.component.ConfiguredInsightComponent;
import com.terraformersmc.insight.api.component.context.BlockComponentContext;
import com.terraformersmc.insight.api.component.context.ComponentContext;

public interface ConfiguredComponentFactory<X extends ComponentContext> {
	Codec<ConfiguredComponentFactory<BlockComponentContext>> BLOCK_CONFIGURED_COMPONENT_FACTORY_CODEC = InsightRegistries.BLOCK_CONFIGURED_COMPONENTS;

	ConfiguredInsightComponent<?, ?, ?> create(X context);
}
