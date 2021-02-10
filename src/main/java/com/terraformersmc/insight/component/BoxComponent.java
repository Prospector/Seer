package com.terraformersmc.insight.component;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.terraformersmc.insight.api.component.InsightComponent;
import com.terraformersmc.insight.api.component.InsightComponentType;
import com.terraformersmc.insight.api.component.context.BlockComponentContext;
import com.terraformersmc.insight.api.component.registry.InsightRegistries;

import net.minecraft.client.util.math.MatrixStack;

public class BoxComponent extends InsightComponent<BlockComponentContext> {
	public static final Codec<BoxComponent> CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
			InsightRegistries.BLOCK_CODEC.fieldOf("child").forGetter(box -> box.child),
			Padding.CODEC.fieldOf("padding").orElseGet(Padding::none).forGetter(box -> box.padding)
		).apply(instance, BoxComponent::new);
	});

	private final InsightComponent<BlockComponentContext> child;
	private final Padding padding;

	public BoxComponent(InsightComponent<BlockComponentContext> child, Padding padding) {
		this.child = child;
		this.padding = padding;
	}

	@Override
	public InsightComponentType<BoxComponent> getType() {
		return InsightBlockComponents.BOX;
	}

	@Override
	public int getWidth(BlockComponentContext context) {
		return this.padding.getX(this.child.getWidth(context));
	}

	@Override
	public int getHeight(BlockComponentContext context) {
		return this.padding.getY(this.child.getHeight(context));
	}

	@Override
	public void render(BlockComponentContext context, MatrixStack matrices, float tickDelta, int x, int y) {
		this.child.render(context, matrices, tickDelta, x + this.padding.left, y + this.padding.top);
	}

	public static class Padding {
		private static final Codec<Padding> RECORD_CODEC = RecordCodecBuilder.create(instance -> {
			return instance.group(
				Codec.INT.optionalFieldOf("top", 0).forGetter(padding -> padding.top),
				Codec.INT.optionalFieldOf("left", 0).forGetter(padding -> padding.left),
				Codec.INT.optionalFieldOf("bottom", 0).forGetter(padding -> padding.bottom),
				Codec.INT.optionalFieldOf("right", 0).forGetter(padding -> padding.right)
			).apply(instance, Padding::new);
		});
		private static final Codec<Padding> CODEC = Codec.either(Codec.INT, RECORD_CODEC).xmap(either -> {
			if (either.left().isPresent()) {
				return Padding.all(either.left().get());
			}
			return either.right().get();
		}, Either::right);

		private final int top;
		private final int left;
		private final int bottom;
		private final int right;

		public Padding(int top, int left, int bottom, int right) {
			this.top = top;
			this.left = left;
			this.bottom = bottom;
			this.right = right;
		}

		public int getX(int x) {
			return this.left + x + this.right;
		}

		public int getY(int y) {
			return this.top + y + this.bottom;
		}

		public static Padding all(int padding) {
			return new Padding(padding, padding, padding, padding);
		}

		public static Padding none() {
			return Padding.all(0);
		}
	}
}
