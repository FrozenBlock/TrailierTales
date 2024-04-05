package net.frozenblock.trailiertales;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.nio.file.Path;
import java.util.Map;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrailierTalesSharedConstants {
	public static final String MOD_ID = "trailiertales";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ModContainer MOD_CONTAINER = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow();
	/**
	 * Used for datafixers.
	 * <p>
	 * Is not necessary for a normal mod, but can be useful in some cases.
	 */
	public static final int DATA_VERSION = 0;
	// MEASURING
	public static final Map<Object, Long> INSTANT_MAP = new Object2ObjectOpenHashMap<>();
	public static boolean DEV_LOGGING = false;
	/**
	 * Used for features that may be unstable and crash in public builds.
	 * <p>
	 * It's smart to use this for at least registries.
	 */
	public static boolean UNSTABLE_LOGGING = FabricLoader.getInstance().isDevelopmentEnvironment();
	public static boolean areConfigsInit = false;

	// LOGGING
	public static void log(String string, boolean shouldLog) {
		if (shouldLog) {
			LOGGER.info(string);
		}
	}

	public static void log(Entity entity, String string, boolean shouldLog) {
		if (shouldLog) {
			LOGGER.info(entity.toString() + " : " + string + " : " + entity.position());
		}
	}

	public static void log(Block block, String string, boolean shouldLog) {
		if (shouldLog) {
			LOGGER.info(block.toString() + " : " + string + " : ");
		}
	}

	public static void log(Block block, BlockPos pos, String string, boolean shouldLog) {
		if (shouldLog) {
			LOGGER.info(block.toString() + " : " + string + " : " + pos);
		}
	}

	public static void logMod(String string, boolean shouldLog) {
		if (shouldLog) {
			LOGGER.info(string + " " + MOD_ID);
		}
	}

	public static void startMeasuring(Object object) {
		long started = System.nanoTime();
		String name = object.getClass().getName();
		LOGGER.info("Started measuring {}", name.substring(name.lastIndexOf(".") + 1));
		INSTANT_MAP.put(object, started);
	}

	public static void stopMeasuring(Object object) {
		if (INSTANT_MAP.containsKey(object)) {
			String name = object.getClass().getName();
			LOGGER.info("{} took {} nanoseconds", name.substring(name.lastIndexOf(".") + 1), System.nanoTime() - INSTANT_MAP.get(object));
			INSTANT_MAP.remove(object);
		}
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	public static ResourceLocation vanillaId(String path) {
		return new ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, path);
	}

	@NotNull
	public static String string(@NotNull String path) {
		return TrailierTalesSharedConstants.id(path).toString();
	}

	public static String safeString(String path) {
		return MOD_ID + "_" + path;
	}

	/**
	 * @return A text component for use in a Config GUI
	 */
	public static Component text(String key) {
		return Component.translatable("option." + MOD_ID + "." + key);
	}

	/**
	 * @return A tooltip component for use in a Config GUI
	 */
	public static Component tooltip(String key) {
		return Component.translatable("tooltip." + MOD_ID + "." + key);
	}

	public static Path configPath(String name, boolean json5) {
		return Path.of("./config/" + MOD_ID + "/" + name + "." + (json5 ? "json5" : "json"));
	}
}
