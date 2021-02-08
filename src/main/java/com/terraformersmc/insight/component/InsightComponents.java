package com.terraformersmc.insight.component;

import com.terraformersmc.insight.Insight;
import com.terraformersmc.insight.api.InsightRegistries;
import com.terraformersmc.insight.api.component.*;
import com.terraformersmc.insight.api.component.config.*;
import net.minecraft.util.Identifier;

public class InsightComponents {
	public static final RootInsightComponent ROOT = register("root", new RootInsightComponent(RootComponentConfig.CODEC));
	public static final SelectLastInsightComponent SELECT_LAST = register("select_last", new SelectLastInsightComponent(ChildrenComponentConfig.CODEC));
	public static final ColumnInsightComponent COLUMN = register("column", new ColumnInsightComponent(ChildrenComponentConfig.CODEC));
	public static final TextInsightComponent TEXT = register("text", new TextInsightComponent(TextComponentConfig.CODEC));
	public static final ItemStackInsightComponent ITEM_STACK = register("item_stack", new ItemStackInsightComponent(ItemStackComponentConfig.CODEC));
	public static final BlockDynamicInsightComponent BLOCK_DYNAMIC = register("block_dynamic", new BlockDynamicInsightComponent(BlockDynamicComponentConfig.CODEC));

	private static <T extends InsightComponent<?, ?>> T register(String id, T component) {
		InsightRegistries.COMPONENTS.register(new Identifier(Insight.MOD_ID, id), component);
		return component;
	}

	public static void register() {
	}
}
