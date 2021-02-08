package com.terraformersmc.insight.api.component.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.terraformersmc.insight.api.component.ConfiguredInsightComponent;

import java.util.List;

public class ChildrenComponentConfig implements InsightComponentConfig {
	public static final Codec<ChildrenComponentConfig> CODEC = RecordCodecBuilder.create(
			(instance) -> instance.group(
					ConfiguredInsightComponent.CODEC.listOf().fieldOf("children").forGetter(ChildrenComponentConfig::getChildren)
			).apply(instance, ChildrenComponentConfig::new));

	private final List<ConfiguredInsightComponent<?, ?, ?>> children;

	public ChildrenComponentConfig(List<ConfiguredInsightComponent<?, ?, ?>> children) {
		this.children = children;
	}

	public List<ConfiguredInsightComponent<?, ?, ?>> getChildren() {
		return children;
	}
}
