package com.terraformersmc.insight.component;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.terraformersmc.insight.api.component.InsightComponent;
import com.terraformersmc.insight.api.component.InsightComponentType;
import com.terraformersmc.insight.api.component.context.BlockComponentContext;
import com.terraformersmc.insight.api.component.registry.InsightRegistries;

import net.minecraft.client.util.math.MatrixStack;

public class RowComponent extends InsightComponent<BlockComponentContext> {
	public static final Codec<RowComponent> CODEC = RecordCodecBuilder.create(instance -> {
		return instance.group(
			InsightRegistries.BLOCK_CODEC.listOf().fieldOf("children").forGetter(row -> row.children)
		).apply(instance, RowComponent::new);
	});

	private final List<InsightComponent<BlockComponentContext>> children;

	public RowComponent(List<InsightComponent<BlockComponentContext>> children) {
		this.children = children;
	}

	@Override
	public InsightComponentType<RowComponent> getType() {
		return InsightBlockComponents.ROW;
	}

	@Override
	public int getWidth(BlockComponentContext context) {
		int width = 0;
		for (InsightComponent<BlockComponentContext> child : this.children) {
			width += child.getWidth(context);
		}
		return width;
	}

	@Override
	public int getHeight(BlockComponentContext context) {
		int height = 0;
		for (InsightComponent<BlockComponentContext> child : this.children) {
			height = Math.max(height, child.getHeight(context));
		}
		return height;
	}

	@Override
	public void render(BlockComponentContext context, MatrixStack matrices, float tickDelta, int x, int y) {
		for (InsightComponent<BlockComponentContext> child : this.children) {
			child.render(context, matrices, tickDelta, x, y);
			x += child.getWidth(context);
		}
	}
	
}
