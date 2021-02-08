package com.terraformersmc.insight.api.component.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.terraformersmc.insight.api.component.InsightComponent;
import net.minecraft.util.Identifier;

public class InsightComponentRegistry implements Codec<InsightComponent<?, ?>> {
	private final BiMap<Identifier, InsightComponent<?, ?>> components = HashBiMap.create();

	public void register(Identifier id, InsightComponent<?, ?> component) {
		components.put(id, component);
	}

	public InsightComponent<?, ?> get(Identifier id) {
		return components.get(id);
	}

	public Identifier getId(InsightComponent<?, ?> component) {
		return components.inverse().get(component);
	}

	@Override
	public <T> DataResult<Pair<InsightComponent<?, ?>, T>> decode(DynamicOps<T> ops, T input) {
		return Identifier.CODEC.decode(ops, input).flatMap((pair) -> {
			InsightComponent<?, ?> component = this.get(pair.getFirst());
			return component == null ? DataResult.error("Unknown component registry key: " + pair.getFirst()) : DataResult.success(Pair.of(component, pair.getSecond()));
		});
	}

	@Override
	public <T> DataResult<T> encode(InsightComponent<?, ?> input, DynamicOps<T> ops, T prefix) {
		Identifier id = this.getId(input);
		if (id == null) {
			return DataResult.error("Unknown component registry element " + input);
		} else {
			return ops.mergeToPrimitive(prefix, ops.createString(id.toString()));
		}
	}
}
