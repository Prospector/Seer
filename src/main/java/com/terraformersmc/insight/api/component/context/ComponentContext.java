package com.terraformersmc.insight.api.component.context;

import net.minecraft.world.World;

public class ComponentContext {
	private final World world;
	private final boolean advanced;

	public ComponentContext(World world, boolean advanced) {
		this.world = world;
		this.advanced = advanced;
	}

	public World getWorld() {
		return world;
	}

	public boolean isAdvanced() {
		return advanced;
	}
}
