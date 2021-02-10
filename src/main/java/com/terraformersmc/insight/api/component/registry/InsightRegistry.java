package com.terraformersmc.insight.api.component.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.terraformersmc.insight.api.component.InsightComponent;
import com.terraformersmc.insight.api.component.InsightComponentType;
import com.terraformersmc.insight.api.component.context.ComponentContext;

import net.minecraft.util.Identifier;

public class InsightRegistry<C extends ComponentContext> implements Codec<InsightComponentType<? extends InsightComponent<C>>> {
	private final BiMap<Identifier, InsightComponentType<? extends InsightComponent<C>>> components = HashBiMap.create();

	protected void register(Identifier id, InsightComponentType<? extends InsightComponent<C>> component) {
		components.put(id, component);
	}

	public InsightComponentType<? extends InsightComponent<C>> get(Identifier id) {
		return components.get(id);
	}

	public Identifier getId(InsightComponentType<? extends InsightComponent<C>> component) {
		return components.inverse().get(component);
	}

	@Override
	public <T> DataResult<Pair<InsightComponentType<? extends InsightComponent<C>>, T>> decode(DynamicOps<T> ops, T input) {
		return Identifier.CODEC.decode(ops, input).flatMap((pair) -> {
			InsightComponentType<? extends InsightComponent<C>> component = this.get(pair.getFirst());
			return component == null ? DataResult.error("Unknown component registry key: " + pair.getFirst()) : DataResult.success(Pair.of(component, pair.getSecond()));
		});
	}

	@Override
	public <T> DataResult<T> encode(InsightComponentType<? extends InsightComponent<C>> input, DynamicOps<T> ops, T prefix) {
		Identifier id = this.getId(input);
		if (id == null) {
			return DataResult.error("Unknown component registry element " + input);
		} else {
			return ops.mergeToPrimitive(prefix, ops.createString(id.toString()));
		}
	}
}
