package com.terraformersmc.insight.api.component.config;

import com.mojang.serialization.Codec;

public class DefaultComponentConfig implements InsightComponentConfig {
	public static final DefaultComponentConfig INSTANCE = new DefaultComponentConfig();

	public static final Codec<DefaultComponentConfig> CODEC = Codec.unit(() -> INSTANCE);
}
