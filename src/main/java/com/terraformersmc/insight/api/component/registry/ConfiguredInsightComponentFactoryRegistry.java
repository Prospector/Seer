package com.terraformersmc.insight.api.component.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.terraformersmc.insight.api.component.context.ComponentContext;
import com.terraformersmc.insight.api.component.factory.ConfiguredComponentFactory;
import com.terraformersmc.insight.util.ListMap;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConfiguredInsightComponentFactoryRegistry<X extends ComponentContext, T, F extends ConfiguredComponentFactory<X>> implements Codec<F> {
	private final BiMap<Identifier, F> factories = HashBiMap.create();
	private final ListMap<Identifier, Class<T>> classes = new ListMap<>();
	private final ListMap<Class<T>, Identifier> classIds = new ListMap<>();
	private final ListMap<Identifier, T> types = new ListMap<>();
	private final ListMap<T, Identifier> typeIds = new ListMap<>();

	public void register(Identifier id, F factory, T... types) {
		this.factories.put(id, factory);
		this.types.putIfAbsent(id, new ArrayList<>());
		if (types.length > 0) {
			registerFor(id, types);
		}
	}

	public void register(Identifier id, F factory, Class<T>... typeClasses) {
		this.factories.put(id, factory);
		this.classes.putIfAbsent(id, new ArrayList<>());
		if (typeClasses.length > 0) {
			registerFor(id, typeClasses);
		}
	}

	public void registerFor(Identifier id, T... types) {
		for (T type : types) {
			this.types.get(id).add(type);
			this.typeIds.putIfAbsent(type, new ArrayList<>());
			this.typeIds.get(type).add(id);
		}
	}

	public void registerFor(Identifier id, Class<T>... typeClasses) {
		for (Class<T> typeClass : typeClasses) {
			this.classes.get(id).add(typeClass);
			this.classIds.get(typeClass).add(id);
			this.classIds.putIfAbsent(typeClass, new ArrayList<>());
		}
	}

	public F getFactory(Identifier id) {
		return factories.get(id);
	}

	public Identifier getId(F factory) {
		return factories.inverse().get(factory);
	}

	public List<F> getFactoriesFor(T type) {
		return typeIds.get(type).stream().map(this::getFactory).collect(Collectors.toList());
	}

	public List<F> getFactoriesFor(Class<T> typeClass) {
		return classIds.get(typeClass).stream().map(this::getFactory).collect(Collectors.toList());
	}

	public List<Identifier> getIdsFor(T type) {
		return typeIds.get(type);
	}

	public List<Identifier> getIdsFor(Class<T> typeClass) {
		return classIds.get(typeClass);
	}

	public List<Class<T>> getClassesFor(Identifier id) {
		return classes.get(id);
	}

	public List<T> getTypesFor(Identifier id) {
		return types.get(id);
	}

	@Override
	public <U> DataResult<Pair<F, U>> decode(DynamicOps<U> ops, U input) {
		return Identifier.CODEC.decode(ops, input).flatMap((pair) -> {
			F factory = this.getFactory(pair.getFirst());
			return factory == null ? DataResult.error("Unknown dynamic component factory registry key: " + pair.getFirst()) : DataResult.success(Pair.of(factory, pair.getSecond()));
		});
	}

	@Override
	public <U> DataResult<U> encode(F input, DynamicOps<U> ops, U prefix) {
		Identifier id = this.getId(input);
		if (id == null) {
			return DataResult.error("Unknown dynamic component factory registry element " + input);
		} else {
			return ops.mergeToPrimitive(prefix, ops.createString(id.toString()));
		}
	}
}
