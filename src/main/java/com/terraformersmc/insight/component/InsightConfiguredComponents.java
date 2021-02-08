package com.terraformersmc.insight.component;

import com.google.common.collect.Lists;
import com.terraformersmc.insight.Insight;
import com.terraformersmc.insight.api.InsightRegistries;
import com.terraformersmc.insight.api.component.ConfiguredInsightComponent;
import com.terraformersmc.insight.api.component.config.BlockDynamicComponentConfig;
import com.terraformersmc.insight.api.component.config.ChildrenComponentConfig;
import com.terraformersmc.insight.api.component.config.RootComponentConfig;
import com.terraformersmc.insight.api.component.config.TextComponentConfig;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

public class InsightConfiguredComponents {
	public static final ConfiguredInsightComponent<?, ?, ?> BLOCK_ROOT = register("block_root",
			InsightComponents.ROOT.configure(
					RootComponentConfig.createTypeless(
							InsightComponents.SELECT_LAST.configure(
									new ChildrenComponentConfig(
											Lists.newArrayList(InsightComponents.BLOCK_DYNAMIC.configure(new BlockDynamicComponentConfig(InsightConfiguredComponentFactories.BLOCK_ICON)))
									)
							),
							InsightComponents.COLUMN.configure(
									new ChildrenComponentConfig(
											Lists.newArrayList(InsightComponents.BLOCK_DYNAMIC.configure(new BlockDynamicComponentConfig(InsightConfiguredComponentFactories.BLOCK_NAME)))
									)
							),
							InsightComponents.COLUMN.configure(
									new ChildrenComponentConfig(
											Lists.newArrayList(InsightComponents.BLOCK_DYNAMIC.configure(new BlockDynamicComponentConfig(InsightConfiguredComponentFactories.DIRT_TEXT)))
									)
							),
							InsightComponents.COLUMN.configure(
									new ChildrenComponentConfig(
											Lists.newArrayList(InsightComponents.TEXT.configure(new TextComponentConfig(new LiteralText("Footer"))))
									)
							)
					)
			)
	);
	public static final ConfiguredInsightComponent<?, ?, ?> DUMMY_ROOT = register("dummy_root",
			InsightComponents.ROOT.configure(
					RootComponentConfig.createTypeless(
							InsightComponents.SELECT_LAST.configure(
									new ChildrenComponentConfig(
											Lists.newArrayList()
									)
							),
							InsightComponents.COLUMN.configure(
									new ChildrenComponentConfig(
											Lists.newArrayList(InsightComponents.TEXT.configure(new TextComponentConfig(new LiteralText("Title of something here"))))
									)
							),
							InsightComponents.COLUMN.configure(
									new ChildrenComponentConfig(
											Lists.newArrayList(InsightComponents.TEXT.configure(new TextComponentConfig(new LiteralText("Another text here"))))
									)
							),
							InsightComponents.COLUMN.configure(
									new ChildrenComponentConfig(
											Lists.newArrayList()
									)
							)
					)
			)
	);

	private static <T extends ConfiguredInsightComponent<?, ?, ?>> T register(String id, T component) {
		InsightRegistries.CONFIGURED_COMPONENTS.register(new Identifier(Insight.MOD_ID, id), component);
		return component;
	}

	public static void register() {
	}
}
