package com.terraformersmc.insight.api.component;

import com.mojang.serialization.Codec;

public class InsightComponentType<C extends InsightComponent<?>> {
	private final Codec<C> codec;

	public InsightComponentType(Codec<C> codec) {
		this.codec = codec;
	}

	public Codec<C> getCodec() {
		return this.codec;
	}
}
