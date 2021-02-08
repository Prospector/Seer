package com.terraformersmc.insight.gui.hud;

import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.terraformersmc.insight.api.component.ConfiguredInsightComponent;
import com.terraformersmc.insight.api.component.config.RootComponentConfig;
import com.terraformersmc.insight.api.component.context.BlockComponentContext;
import com.terraformersmc.insight.api.component.context.ComponentContext;
import com.terraformersmc.insight.component.InsightConfiguredComponents;
import com.terraformersmc.insight.config.InsightConfig;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import java.util.function.Function;

public class InsightHud extends DrawableHelper {
	public static InsightHud instance;

	private final MinecraftClient client;
	private final boolean realData;

	private int width = 0;
	private int height = 0;
	private HitResult lastTarget = null;
	private ConfiguredInsightComponent<?, ?, ?> root = null;
	private ComponentContext context = null;

	public InsightHud(MinecraftClient client, boolean realData) {
		this.client = client;
		this.realData = realData;
	}

	public void render(HitResult crosshairTarget, MatrixStack matrixStack, float tickDelta) {
		ClientWorld world = client.world;
		if (world != null) {
			if (crosshairTarget != lastTarget) {
				lastTarget = crosshairTarget;
				root = null;
				if (crosshairTarget.getType() == HitResult.Type.BLOCK) {
					BlockHitResult blockHit = (BlockHitResult) crosshairTarget;
					BlockPos pos = blockHit.getBlockPos();
					BlockState state = world.getBlockState(pos);
					root = InsightConfiguredComponents.BLOCK_ROOT;
					context = new BlockComponentContext(state, pos, world, client.options.advancedItemTooltips);
				}
			}
		}
		if (!realData && root == null) {
			root = InsightConfiguredComponents.DUMMY_ROOT;
		}

		if (root != null) {
			int outerPadding = InsightConfig.outerPadding;

			this.width = InsightConfiguredComponents.BLOCK_ROOT.getWidthTypeless(context);
			this.height = InsightConfiguredComponents.BLOCK_ROOT.getHeightTypeless(context);

			if (this.width > 0 && this.height > 0) {
				this.width += outerPadding * 2;
				this.height += outerPadding * 2;
			}

			int x = getOriginX();
			int y = getOriginY();
			fill(matrixStack, x, y, x + getWidth(), y + getHeight(), 0x80000000);

			root.renderTypeless(context, matrixStack, tickDelta, x + outerPadding, y + outerPadding, client);
			Function<RootComponentConfig, DataResult<JsonElement>> function = JsonOps.INSTANCE.withEncoder(RootComponentConfig.CODEC);
			DataResult<JsonElement> result = function.apply((RootComponentConfig) root.getConfig());
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
