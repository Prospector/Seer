package com.terraformersmc.insight.api;

import com.terraformersmc.insight.api.component.context.BlockComponentContext;
import com.terraformersmc.insight.api.component.factory.ConfiguredComponentFactory;
import com.terraformersmc.insight.api.component.registry.ConfiguredInsightComponentFactoryRegistry;
import com.terraformersmc.insight.api.component.registry.ConfiguredInsightComponentRegistry;
import com.terraformersmc.insight.api.component.registry.InsightComponentRegistry;
import net.minecraft.block.Block;

public class InsightRegistries {
	public static final InsightComponentRegistry COMPONENTS = new InsightComponentRegistry();
	public static final ConfiguredInsightComponentRegistry CONFIGURED_COMPONENTS = new ConfiguredInsightComponentRegistry();
	public static final ConfiguredInsightComponentFactoryRegistry<BlockComponentContext, Block, ConfiguredComponentFactory<BlockComponentContext>> BLOCK_CONFIGURED_COMPONENTS = new ConfiguredInsightComponentFactoryRegistry<>();
}
