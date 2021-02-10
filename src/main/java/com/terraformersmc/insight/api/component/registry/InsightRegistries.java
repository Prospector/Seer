package com.terraformersmc.insight.api.component.registry;

import com.mojang.serialization.Codec;
import com.terraformersmc.insight.api.component.InsightComponent;
import com.terraformersmc.insight.api.component.InsightComponentType;
import com.terraformersmc.insight.api.component.context.BlockComponentContext;

import net.minecraft.util.Identifier;

public class InsightRegistries {
	public static final InsightRegistry<BlockComponentContext> BLOCK = new InsightRegistry<>();
	public static final Codec<InsightComponent<BlockComponentContext>> BLOCK_CODEC = BLOCK.dispatch(InsightComponent::getType, InsightComponentType::getCodec);

	public static <C extends InsightComponent<BlockComponentContext>> InsightComponentType<C> registerBlock(Identifier id, Codec<C> codec) {
		InsightComponentType<C> type = new InsightComponentType<>(codec);
		BLOCK.register(id, type);
		return type;
	}
}
