package com.terraformersmc.insight.util;

import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class DefaultedHashMap<K, V> extends HashMap<K, V> {

	private final Supplier<V> defaultValue;

	public DefaultedHashMap(Supplier<V> defaultValue) {
		this.defaultValue = defaultValue;
	}

	public V getDefaultValue() {
		return defaultValue.get();
	}

	@Override
	public V get(Object key) {
		putIfAbsent((K) key, defaultValue.get());
		return super.get(key);
	}

	@Override
	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		putIfAbsent(key, defaultValue.get());
		return super.compute(key, remappingFunction);
	}
}
