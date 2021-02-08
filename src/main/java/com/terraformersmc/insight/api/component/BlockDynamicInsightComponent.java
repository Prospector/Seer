package com.terraformersmc.insight.api.component;

import com.mojang.serialization.Codec;
import com.terraformersmc.insight.api.InsightRegistries;
import com.terraformersmc.insight.api.component.config.BlockDynamicComponentConfig;
import com.terraformersmc.insight.api.component.context.BlockComponentContext;
import com.terraformersmc.insight.api.component.factory.ConfiguredComponentFactory;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.List;

public class BlockDynamicInsightComponent extends InsightComponent<BlockDynamicComponentConfig, BlockComponentContext> {
	public BlockDynamicInsightComponent(Codec<BlockDynamicComponentConfig> codec) {
		super(codec);
	}

	@Override
	public int getWidth(BlockDynamicComponentConfig config, BlockComponentContext context) {
		if (matchesType(config, context)) {

			return config.getFactory().create(context).getWidthTypeless(context);
		}
		return 0;
	}

	@Override
	public int getHeight(BlockDynamicComponentConfig config, BlockComponentContext context) {
		if (matchesType(config, context)) {
			return config.getFactory().create(context).getHeightTypeless(context);
		}
		return 0;
	}

	@Override
	public void render(BlockDynamicComponentConfig config, BlockComponentContext context, MatrixStack matrixStack, float tickDelta, int x, int y, MinecraftClient client) {
		if (matchesType(config, context)) {
			config.getFactory().create(context).renderTypeless(context, matrixStack, tickDelta, x, y, client);
		}
	}

	private boolean matchesType(BlockDynamicComponentConfig config, BlockComponentContext context) {
		ConfiguredComponentFactory<BlockComponentContext> factory = config.getFactory();
		Identifier id = InsightRegistries.BLOCK_CONFIGURED_COMPONENTS.getId(factory);
		Block block = context.getState().getBlock();
		List<Block> blocks = InsightRegistries.BLOCK_CONFIGURED_COMPONENTS.getTypesFor(id);
		List<Class<Block>> blockClasses = InsightRegistries.BLOCK_CONFIGURED_COMPONENTS.getClassesFor(id);
		boolean matchingBlockClass = false;
		for (Class<Block> blockClass : blockClasses) {
			if (blockClass.isAssignableFrom(block.getClass())) {
				matchingBlockClass = true;
				break;
			}
		}
		return blocks.contains(block) || matchingBlockClass;
	}
}
