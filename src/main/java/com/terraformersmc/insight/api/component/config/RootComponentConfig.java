package com.terraformersmc.insight.api.component.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.terraformersmc.insight.api.component.ColumnInsightComponent;
import com.terraformersmc.insight.api.component.ConfiguredInsightComponent;
import com.terraformersmc.insight.api.component.SelectLastInsightComponent;
import com.terraformersmc.insight.api.component.context.ComponentContext;

import java.util.ArrayList;
import java.util.List;

public class RootComponentConfig implements InsightComponentConfig {
	public static final Codec<RootComponentConfig> CODEC = RecordCodecBuilder.create(
			(instance) -> instance.group(
					ConfiguredInsightComponent.CODEC.fieldOf("icon").forGetter(RootComponentConfig::getIcon),
					ConfiguredInsightComponent.CODEC.fieldOf("header").forGetter(RootComponentConfig::getHeader),
					ConfiguredInsightComponent.CODEC.fieldOf("body").forGetter(RootComponentConfig::getBody),
					ConfiguredInsightComponent.CODEC.fieldOf("footer").forGetter(RootComponentConfig::getFooter)
			).apply(instance, RootComponentConfig::createTypeless));

	private final ConfiguredInsightComponent<ComponentContext, ChildrenComponentConfig, SelectLastInsightComponent> icon;
	private final ConfiguredInsightComponent<ComponentContext, ChildrenComponentConfig, ColumnInsightComponent> header;
	private final ConfiguredInsightComponent<ComponentContext, ChildrenComponentConfig, ColumnInsightComponent> body;
	private final ConfiguredInsightComponent<ComponentContext, ChildrenComponentConfig, ColumnInsightComponent> footer;
	private List<ConfiguredInsightComponent<?, ?, ?>> allChildren = null;

	public RootComponentConfig(
			ConfiguredInsightComponent<ComponentContext, ChildrenComponentConfig, SelectLastInsightComponent> icon,
			ConfiguredInsightComponent<ComponentContext, ChildrenComponentConfig, ColumnInsightComponent> header,
			ConfiguredInsightComponent<ComponentContext, ChildrenComponentConfig, ColumnInsightComponent> body,
			ConfiguredInsightComponent<ComponentContext, ChildrenComponentConfig, ColumnInsightComponent> footer
	) {
		this.icon = icon;
		this.header = header;
		this.body = body;
		this.footer = footer;
	}

	public static RootComponentConfig createTypeless(ConfiguredInsightComponent<?, ?, ?> icon, ConfiguredInsightComponent<?, ?, ?> header, ConfiguredInsightComponent<?, ?, ?> body, ConfiguredInsightComponent<?, ?, ?> footer) {
		return new RootComponentConfig(
				(ConfiguredInsightComponent<ComponentContext, ChildrenComponentConfig, SelectLastInsightComponent>) icon,
				(ConfiguredInsightComponent<ComponentContext, ChildrenComponentConfig, ColumnInsightComponent>) header,
				(ConfiguredInsightComponent<ComponentContext, ChildrenComponentConfig, ColumnInsightComponent>) body,
				(ConfiguredInsightComponent<ComponentContext, ChildrenComponentConfig, ColumnInsightComponent>) footer
		);
	}

	public ConfiguredInsightComponent<ComponentContext, ChildrenComponentConfig, SelectLastInsightComponent> getIcon() {
		return icon;
	}

	public ConfiguredInsightComponent<ComponentContext, ChildrenComponentConfig, ColumnInsightComponent> getHeader() {
		return header;
	}

	public ConfiguredInsightComponent<ComponentContext, ChildrenComponentConfig, ColumnInsightComponent> getBody() {
		return body;
	}

	public ConfiguredInsightComponent<ComponentContext, ChildrenComponentConfig, ColumnInsightComponent> getFooter() {
		return footer;
	}

	public List<ConfiguredInsightComponent<?, ?, ?>> getChildren() {
		if (allChildren == null) {
			allChildren = new ArrayList<>();
			allChildren.add(icon);
			allChildren.add(header);
			allChildren.add(body);
			allChildren.add(footer);
		}
		return allChildren;
	}
}
