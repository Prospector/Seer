package com.terraformersmc.insight.component;

import com.terraformersmc.insight.Insight;
import com.terraformersmc.insight.api.InsightRegistries;
import com.terraformersmc.insight.api.component.config.ItemStackComponentConfig;
import com.terraformersmc.insight.api.component.config.TextComponentConfig;
import com.terraformersmc.insight.api.component.context.BlockComponentContext;
import com.terraformersmc.insight.api.component.factory.ConfiguredComponentFactory;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class InsightConfiguredComponentFactories {
	public static final ConfiguredComponentFactory<BlockComponentContext> BLOCK_ICON = register("block_icon", (context) -> InsightComponents.ITEM_STACK.configure(new ItemStackComponentConfig(context.getState().getBlock().asItem().getDefaultStack())), Block.class);
	public static final ConfiguredComponentFactory<BlockComponentContext> BLOCK_NAME = register("block_name", (context) -> InsightComponents.TEXT.configure(new TextComponentConfig(context.getState().getBlock().getName())), Block.class);
	public static final ConfiguredComponentFactory<BlockComponentContext> DIRT_TEXT = register("dirt_text", (context) -> InsightComponents.TEXT.configure(new TextComponentConfig(new LiteralText("this is dirt").formatted(Formatting.GRAY))), Blocks.DIRT);

	private static <T extends ConfiguredComponentFactory<BlockComponentContext>> T register(String id, T factory, Block... blocks) {
		InsightRegistries.BLOCK_CONFIGURED_COMPONENTS.register(new Identifier(Insight.MOD_ID, id), factory, blocks);
		return factory;
	}

	private static <T extends ConfiguredComponentFactory<BlockComponentContext>> T register(String id, T factory, Class<Block>... blockClass) {
		InsightRegistries.BLOCK_CONFIGURED_COMPONENTS.register(new Identifier(Insight.MOD_ID, id), factory, blockClass);
		return factory;
	}

	public static void register() {
	}
}
