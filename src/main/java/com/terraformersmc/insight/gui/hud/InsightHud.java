package com.terraformersmc.insight.gui.hud;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.terraformersmc.insight.api.component.InsightComponent;
import com.terraformersmc.insight.api.component.context.BlockComponentContext;
import com.terraformersmc.insight.api.component.registry.InsightRegistries;
import com.terraformersmc.insight.component.BlockNameComponent;
import com.terraformersmc.insight.component.BlockStackComponent;
import com.terraformersmc.insight.component.BoxComponent;
import com.terraformersmc.insight.component.RowComponent;
import com.terraformersmc.insight.config.InsightConfig;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

public class InsightHud extends DrawableHelper {
	public static InsightHud instance;

	private final MinecraftClient client;

	private int width = 0;
	private int height = 0;
	private HitResult lastTarget = null;
	private InsightComponent<BlockComponentContext> root = null;
	private BlockComponentContext context = null;

	public InsightHud(MinecraftClient client) {
		this.client = client;
	}

	public void render(HitResult crosshairTarget, MatrixStack matrixStack, float tickDelta) {
		ClientWorld world = client.world;
		if (world != null) {
			if (crosshairTarget != lastTarget) {
				lastTarget = crosshairTarget;
				root = null;
				if (crosshairTarget.getType() == HitResult.Type.BLOCK) {
					root = new BoxComponent(new RowComponent(Lists.newArrayList(
						new BlockStackComponent(),
						new BlockNameComponent()
					)), BoxComponent.Padding.all(8));
					context = new BlockComponentContext(client, (BlockHitResult) crosshairTarget);
				}
			}
		}

		if (root != null) {
			int outerPadding = InsightConfig.outerPadding;

			this.width = root.getWidth(context);
			this.height = root.getHeight(context);

			if (this.width > 0 && this.height > 0) {
				this.width += outerPadding * 2;
				this.height += outerPadding * 2;
			}

			int x = getOriginX();
			int y = getOriginY();
			fill(matrixStack, x, y, x + getWidth(), y + getHeight(), 0x80000000);

			root.render(context, matrixStack, tickDelta, x + outerPadding, y + outerPadding);
			DataResult<JsonElement> result = InsightRegistries.BLOCK_CODEC.encodeStart(JsonOps.INSTANCE, root);
			result.get().left().ifPresent(json -> {
				System.out.println("===START======================================================================================");
				System.out.println(json);
				System.out.println("===END======================================================================================");
			});
		}
	}

	public int getOriginX() {
		return InsightConfig.anchorX.originOf(InsightConfig.relativeX, getWidth(), client.getWindow().getScaledWidth());
	}

	public int getOriginY() {
		return InsightConfig.anchorY.originOf(InsightConfig.relativeY, getHeight(), client.getWindow().getScaledHeight());
	}

	public int getAnchorX() {
		return InsightConfig.anchorX.absoluteOf(InsightConfig.relativeX, client.getWindow().getScaledWidth());
	}

	public int getAnchorY() {
		return InsightConfig.anchorY.absoluteOf(InsightConfig.relativeY, client.getWindow().getScaledHeight());
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
