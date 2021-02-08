package com.terraformersmc.insight.config;

import com.google.common.base.CaseFormat;
import com.google.gson.*;
import com.terraformersmc.modmenu.ModMenu;
import com.terraformersmc.insight.Insight;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class InsightConfigManager {
	public static final Gson GSON = new GsonBuilder()
			.setPrettyPrinting()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.create();
	private static File file;
	private static final Logger LOGGER = LogManager.getLogger();

	private static void prepareConfigFile() {
		if (file != null) {
			return;
		}
		file = new File(FabricLoader.getInstance().getConfigDir().toFile(), Insight.MOD_ID + ".json");
	}

	public static void initializeConfig() {
		load();
	}

	private static void load() {
		prepareConfigFile();

		try {
			if (!file.exists()) {
				save();
			}
			if (file.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(file));
				JsonObject json = new JsonParser().parse(br).getAsJsonObject();

				for (Field field : InsightConfig.class.getDeclaredFields()) {
					if (Modifier.isStatic(field.getModifiers())) {
						Object value = GSON.fromJson(json.get(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName())), field.getType());
						if (value != null) {
							field.set(null, value);
						}
					}
				}
			}
		} catch (FileNotFoundException | IllegalAccessException e) {
			LOGGER.error("Couldn't load Insight configuration file; reverting to defaults", e);
		}
	}

	public static void save() {
		ModMenu.clearModCountCache();
		prepareConfigFile();

		JsonObject config = new JsonObject();

		try {
			for (Field field : InsightConfig.class.getDeclaredFields()) {
				if (Modifier.isStatic(field.getModifiers())) {
					Object object = field.get(null);
					config.add(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName()), GSON.toJsonTree(object));
				}
			}
		} catch (IllegalAccessException e) {
			LOGGER.error("Could not access field in Insight config", e);
		}

		String jsonString = ModMenu.GSON.toJson(config);

		try (FileWriter fileWriter = new FileWriter(file)) {
			fileWriter.write(jsonString);
		} catch (IOException e) {
			LOGGER.error("Couldn't save Insight configuration file", e);
		}
	}
}
