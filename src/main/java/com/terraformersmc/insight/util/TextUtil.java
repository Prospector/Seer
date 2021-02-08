package com.terraformersmc.insight.util;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import net.minecraft.text.Text;

public class TextUtil {
	public static final Codec<Text> CODEC = new Codec<Text>() {
		@Override
		public <T> DataResult<Pair<Text, T>> decode(DynamicOps<T> ops, T input) {
			try {
				JsonElement json = Dynamic.convert(ops, JsonOps.INSTANCE, input);
				Text text = Text.Serializer.fromJson(json);
				return DataResult.success(Pair.of(text, ops.empty()));
			} catch (Exception e) {
				return DataResult.error(e.getClass().getName() + ": " + e.getMessage());
			}
		}

		@Override
		public <T> DataResult<T> encode(Text input, DynamicOps<T> ops, T prefix) {
			try {
				return ops.mergeToPrimitive(prefix, Dynamic.convert(JsonOps.INSTANCE, ops, Text.Serializer.toJsonTree(input)));
			} catch (Exception e) {
				return DataResult.error(e.getClass().getName() + ": " + e.getMessage());
			}
		}

		@Override
		public String toString() {
			return "Text";
		}
	};
}
