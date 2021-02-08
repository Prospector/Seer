package com.terraformersmc.insight.util;

import java.util.ArrayList;
import java.util.List;

public class ListMap<K, V> extends DefaultedHashMap<K, List<V>> {
	public ListMap() {
		super(ArrayList::new);
	}
}
