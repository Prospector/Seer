package com.terraformersmc.insight.api.component.context;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;

public class BlockComponentContext extends ComponentContext {
	private final BlockHitResult hitResult;
	private final BlockState state;

	public BlockComponentContext(MinecraftClient client, BlockHitResult hitResult) {
		super(client);

		this.hitResult = hitResult;
		this.state = client.world.getBlockState(this.hitResult.getBlockPos());
	}

	public BlockHitResult getHitResult() {
		return this.hitResult;
	}

	public BlockState getState() {
		return this.state;
	}

	@Override
	public String toString() {
		return "BlockComponentContext{hitResult=" + this.hitResult + ", state=" + this.state + ", client=" + this.getClient() + "}";
	}
}
