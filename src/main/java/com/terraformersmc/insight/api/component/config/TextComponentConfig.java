package com.terraformersmc.insight.api.component.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.terraformersmc.insight.util.TextUtil;
import net.minecraft.text.Text;

public class TextComponentConfig implements InsightComponentConfig {
	public static final Codec<TextComponentConfig> CODEC = RecordCodecBuilder.create(
			(instance) -> instance.group(
					TextUtil.CODEC.fieldOf("text").forGetter(TextComponentConfig::getText)
			).apply(instance, TextComponentConfig::new));

	private final Text text;

	public TextComponentConfig(Text text) {
		this.text = text;
	}

	public Text getText() {
		return this.text;
	}
}
