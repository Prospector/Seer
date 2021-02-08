package com.terraformersmc.insight.api.component.context;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockComponentContext extends ComponentContext {
	private final BlockState state;
	private final BlockPos pos;

	public BlockComponentContext(BlockState state, BlockPos pos, World world, boolean advanced) {
		super(world, advanced);
		this.state = state;
		this.pos = pos;
	}

	public BlockState getState() {
		return state;
	}

	public BlockPos getPos() {
		return pos;
	}
}
