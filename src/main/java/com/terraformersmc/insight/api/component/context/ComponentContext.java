package com.terraformersmc.insight.api.component.context;

import net.minecraft.client.MinecraftClient;

public class ComponentContext {
	private final MinecraftClient client;

	public ComponentContext(MinecraftClient client) {
		this.client = client;
	}

	public MinecraftClient getClient() {
		return this.client;
	}

	@Override
	public String toString() {
		return "ComponentContext{client=" + this.client + "}";
	}
}
