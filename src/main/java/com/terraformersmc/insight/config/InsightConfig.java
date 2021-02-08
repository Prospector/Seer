package com.terraformersmc.insight.config;

import net.minecraft.util.math.MathHelper;

public class InsightConfig {
	public static final int DEFAULT_RELATIVE_X = 4;
	public static final int DEFAULT_RELATIVE_Y = 4;
	public static int relativeX = DEFAULT_RELATIVE_X;
	public static int relativeY = DEFAULT_RELATIVE_Y;
	public static int outerPadding = 4;
	public static int innerPadding = 2;
	public static XAnchor anchorX = XAnchor.LEFT;
	public static YAnchor anchorY = YAnchor.TOP;

	private enum Anchor {
		LOW(
				(relativePos, screenSize) -> relativePos,
				(absolutePos, size) -> absolutePos,
				(pos, size, screenSize, sizeDiff) -> MathHelper.clamp(pos, 0, sizeDiff)
		),
		MIDDLE(
				(relativePos, screenSize) -> screenSize / 2 - relativePos,
				(absolutePos, size) -> absolutePos - size / 2,
				(pos, size, screenSize, sizeDiff) -> -MathHelper.clamp(pos - screenSize / 2.0, -sizeDiff / 2.0, sizeDiff / 2.0)
		),
		HIGH(
				(relativePos, screenSize) -> screenSize - relativePos,
				(absolutePos, size) -> absolutePos - size,
				(pos, size, screenSize, sizeDiff) -> screenSize - MathHelper.clamp(pos, size, screenSize)
		);

		AbsoluteFunction absolute;
		OriginFunction origin;
		ClampFunction clamp;

		Anchor(AbsoluteFunction absolute, OriginFunction origin, ClampFunction clamp) {
			this.absolute = absolute;
			this.origin = origin;
			this.clamp = clamp;
		}

		public int absoluteOf(int relativePos, int screenSize) {
			return absolute.apply(relativePos, screenSize);
		}

		public int originOf(int relativePos, int size, int screenSize) {
			int absolute = absoluteOf(relativePos, screenSize);
			return originOf(absolute, size);
		}

		public int originOf(int absolutePos, int size) {
			return origin.apply(absolutePos, size);
		}

		public double clamp(double pos, int size, int screenSize) {
			int sizeDiff = screenSize - size;
			return clamp.apply(pos, size, screenSize, sizeDiff);
		}

		private interface AbsoluteFunction {
			int apply(int relativePos, int screenSize);
		}

		private interface OriginFunction {
			int apply(int absolutePos, int size);
		}

		private interface ClampFunction {
			double apply(double pos, int size, int screenSize, int sizeDiff);
		}
	}

	public enum XAnchor {
		LEFT(Anchor.LOW),
		CENTER(Anchor.MIDDLE),
		RIGHT(Anchor.HIGH);

		private final Anchor anchor;

		XAnchor(Anchor anchor) {
			this.anchor = anchor;
		}

		public int absoluteOf(int relativeX, int screenWidth) {
			return anchor.absoluteOf(relativeX, screenWidth);
		}

		public int originOf(int relativeX, int width, int screenWidth) {
			return anchor.originOf(relativeX, width, screenWidth);
		}

		public int originOf(int absoluteX, int width) {
			return anchor.originOf(absoluteX, width);
		}

		public double clamp(double x, int width, int screenWidth) {
			return anchor.clamp(x, width, screenWidth);
		}
	}

	public enum YAnchor {
		TOP(Anchor.LOW),
		CENTER(Anchor.MIDDLE),
		BOTTOM(Anchor.HIGH);

		private final Anchor anchor;

		YAnchor(Anchor anchor) {
			this.anchor = anchor;
		}

		public int absoluteOf(int relativeY, int screenHeight) {
			return anchor.absoluteOf(relativeY, screenHeight);
		}

		public int originOf(int relativeY, int height, int screenHeight) {
			return anchor.originOf(relativeY, height, screenHeight);
		}

		public int originOf(int absoluteY, int height) {
			return anchor.originOf(absoluteY, height);
		}

		public double clamp(double y, int height, int screenHeight) {
			return anchor.clamp(y, height, screenHeight);
		}
	}
}
