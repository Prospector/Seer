package com.terraformersmc.insight.api.component.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.terraformersmc.insight.api.component.ConfiguredInsightComponent;
import net.minecraft.util.Identifier;

public class ConfiguredInsightComponentRegistry {
	private final BiMap<Identifier, ConfiguredInsightComponent<?, ?, ?>> components = HashBiMap.create();

	public void register(Identifier id, ConfiguredInsightComponent<?, ?, ?> component) {
		components.put(id, component);
	}

	public ConfiguredInsightComponent<?, ?, ?> get(Identifier id) {
		return components.get(id);
	}

	public Identifier getId(ConfiguredInsightComponent<?, ?, ?> component) {
		return components.inverse().get(component);
	}
}
