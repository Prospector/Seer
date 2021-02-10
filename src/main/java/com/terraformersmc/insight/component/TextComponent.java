package com.terraformersmc.insight.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.terraformersmc.insight.api.component.InsightComponentType;
import com.terraformersmc.insight.api.component.context.BlockComponentContext;
import com.terraformersmc.insight.util.TextUtil;

import net.minecraft.text.Text;

public class TextComponent extends AbstractTextComponent<BlockComponentContext> {
	public static final Codec<TextComponent> CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
			TextUtil.CODEC.fieldOf("text").forGetter(text -> text.text)
		).apply(instance, TextComponent::new);
	});

	private final Text text;

	public TextComponent(Text text) {
		this.text = text;
	}

	@Override
	public InsightComponentType<TextComponent> getType() {
		return InsightBlockComponents.TEXT;
	}

	@Override
	protected Text getText(BlockComponentContext context) {
		return this.text;
	}
}
