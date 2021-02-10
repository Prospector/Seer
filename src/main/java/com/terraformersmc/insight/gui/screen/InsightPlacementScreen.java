package com.terraformersmc.insight.gui.screen;

import com.terraformersmc.insight.config.InsightConfig;
import com.terraformersmc.insight.config.InsightConfigManager;
import com.terraformersmc.insight.gui.hud.InsightHud;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import org.lwjgl.glfw.GLFW;

public class InsightPlacementScreen extends Screen {

	private final Screen previous;

	private InsightHud insightHud;
	private boolean clickingInsight = false;
	private double clickOffsetX = 0;
	private double clickOffsetY = 0;
	private int oldXPosition = 0;
	private int oldYPosition = 0;
	private int xBefore = InsightConfig.relativeX;
	private int yBefore = InsightConfig.relativeY;

	public InsightPlacementScreen(Screen previous) {
		super(new TranslatableText("insight.configure.placement"));
		this.previous = previous;
	}

	@Override
	protected void init() {
		super.init();
		insightHud = new InsightHud(client);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float tickDelta) {
		this.renderBackground(matrixStack);
		insightHud.render(null, matrixStack, tickDelta);
		boolean debug = true;
		if (debug) {
			int centerX = width / 2;
			int centerY = height / 2;
			fill(matrixStack, centerX - 1, centerY - 1, centerX + 1, centerY + 1, 0xFF0000FF);
			int anchorX = insightHud.getAnchorX();
			int anchorY = insightHud.getAnchorY();
			fill(matrixStack, anchorX - 1, anchorY - 1, anchorX + 1, anchorY + 1, 0xFFFF0000);
			int screenAnchorX = InsightConfig.anchorX.absoluteOf(0, width);
			int screenAnchorY = InsightConfig.anchorY.absoluteOf(0, height);
			fill(matrixStack, screenAnchorX - 1, screenAnchorY - 1, screenAnchorX + 1, screenAnchorY + 1, 0xFF00FF00);
		}
		super.render(matrixStack, mouseX, mouseY, tickDelta);
	}

	@Override
	public void removed() {
		InsightConfigManager.save();
		super.removed();
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		if (hasControlDown()) {
			if (keyCode == GLFW.GLFW_KEY_C) {
				InsightConfig.anchorX = InsightConfig.XAnchor.values()[(InsightConfig.anchorX.ordinal() + 1) % 3];
				InsightConfig.relativeX = 0;
			} else if (keyCode == GLFW.GLFW_KEY_V) {
				InsightConfig.anchorY = InsightConfig.YAnchor.values()[(InsightConfig.anchorY.ordinal() + 1) % 3];
				InsightConfig.relativeY = 0;
			}
		}
		if (keyCode == GLFW.GLFW_KEY_R) {
			InsightConfig.relativeX = 0;
			InsightConfig.relativeY = 0;
		} else {
			int anchorX = insightHud.getAnchorX();
			int anchorY = insightHud.getAnchorY();
			if (!hasShiftDown()) {
				if (keyCode == GLFW.GLFW_KEY_UP) {
					anchorY = anchorY - 1;
				} else if (keyCode == GLFW.GLFW_KEY_DOWN) {
					anchorY = anchorY + 1;
				} else if (keyCode == GLFW.GLFW_KEY_LEFT) {
					anchorX = anchorX - 1;
				} else if (keyCode == GLFW.GLFW_KEY_RIGHT) {
					anchorX = anchorX + 1;
				}
			} else {
				if (keyCode == GLFW.GLFW_KEY_UP) {
					anchorY = anchorY - 5;
				} else if (keyCode == GLFW.GLFW_KEY_DOWN) {
					anchorY = anchorY + 5;
				} else if (keyCode == GLFW.GLFW_KEY_LEFT) {
					anchorX = anchorX - 5;
				} else if (keyCode == GLFW.GLFW_KEY_RIGHT) {
					anchorX = anchorX + 5;
				}
			}
			setPosition(anchorX, anchorY);
		}
		return false;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (mouseX >= insightHud.getOriginX() && mouseX <= insightHud.getOriginX() + insightHud.getWidth() && mouseY >= insightHud.getOriginY() && mouseY <= insightHud.getOriginY() + insightHud.getHeight()) {
			clickingInsight = true;
			clickOffsetX = mouseX - insightHud.getAnchorX();
			clickOffsetY = mouseY - insightHud.getAnchorY();
			oldXPosition = InsightConfig.relativeX;
			oldYPosition = InsightConfig.relativeY;
		}
		return false;
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		if (clickingInsight) {
			double xPosition = mouseX - clickOffsetX;
			double yPosition = mouseY - clickOffsetY;

			if (hasShiftDown()) {
				double xDiff = Math.abs(oldXPosition - xPosition);
				double yDiff = Math.abs(oldYPosition - yPosition);
				if (xDiff > yDiff) {
					setPosition(xPosition, oldYPosition);
				} else {
					setPosition(oldXPosition, yPosition);
				}
			} else {
				setPosition(xPosition, yPosition);
			}
			return true;
		}
		return false;
	}

	public void setPosition(double x, double y) {
		x = InsightConfig.anchorX.clamp(x, insightHud.getWidth(), width);
		y = InsightConfig.anchorY.clamp(y, insightHud.getHeight(), height);
		InsightConfig.relativeX = (int) x;
		InsightConfig.relativeY = (int) y;
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		clickingInsight = false;
		return false;
	}

	@Override
	public void mouseMoved(double mouseX, double mouseY) {

	}
}
