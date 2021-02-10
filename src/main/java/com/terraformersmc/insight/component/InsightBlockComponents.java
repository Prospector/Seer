package com.terraformersmc.insight.component;

import com.mojang.serialization.Codec;
import com.terraformersmc.insight.Insight;
import com.terraformersmc.insight.api.component.InsightComponent;
import com.terraformersmc.insight.api.component.InsightComponentType;
import com.terraformersmc.insight.api.component.context.BlockComponentContext;
import com.terraformersmc.insight.api.component.registry.InsightRegistries;

import net.minecraft.util.Identifier;

public class InsightBlockComponents {
	public static final InsightComponentType<BlockNameComponent> BLOCK_NAME = register("block_name", BlockNameComponent.CODEC);
	public static final InsightComponentType<BlockStackComponent> BLOCK_STACK = register("block_stack", BlockStackComponent.CODEC);
	public static final InsightComponentType<BoxComponent> BOX = register("box", BoxComponent.CODEC);
	public static final InsightComponentType<ItemStackComponent> ITEM_STACK = register("item_stack", ItemStackComponent.CODEC);
	public static final InsightComponentType<RootComponent> ROOT = register("root", RootComponent.CODEC);
	public static final InsightComponentType<RowComponent> ROW = register("row", RowComponent.CODEC);
	public static final InsightComponentType<TextComponent> TEXT = register("text", TextComponent.CODEC);
	/*public static final SelectLastInsightComponent SELECT_LAST = register("select_last", new SelectLastInsightComponent(ChildrenComponentConfig.CODEC));
	public static final ColumnInsightComponent COLUMN = register("column", new ColumnInsightComponent(ChildrenComponentConfig.CODEC));
	public static final TextInsightComponent TEXT = register("text", new TextInsightComponent(TextComponentConfig.CODEC));
	public static final ItemStackInsightComponent ITEM_STACK = register("item_stack", new ItemStackInsightComponent(ItemStackComponentConfig.CODEC));
	public static final BlockDynamicInsightComponent BLOCK_DYNAMIC = register("block_dynamic", new BlockDynamicInsightComponent(BlockDynamicComponentConfig.CODEC));*/

	private static <C extends InsightComponent<BlockComponentContext>> InsightComponentType<C> register(String id, Codec<C> codec) {
		return InsightRegistries.registerBlock(new Identifier(Insight.MOD_ID, id), codec);
	}

	public static void register() {
	}
}
