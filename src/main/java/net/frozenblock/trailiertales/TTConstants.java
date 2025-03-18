package net.frozenblock.trailiertales;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TTConstants {
	public static final String MOD_ID = TTPreLoadConstants.MOD_ID;
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	/**
	 * Used for features that may be unstable and crash in public builds.
	 * <p>
	 * It's smart to use this for at least registries.
	 */
	public static boolean UNSTABLE_LOGGING = FabricLoader.getInstance().isDevelopmentEnvironment();

	// LOGGING
	public static void log(String string, boolean shouldLog) {
		if (shouldLog) {
			LOGGER.info(string);
		}
	}

	@Contract("_ -> new")
	public static @NotNull ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	public static @NotNull ResourceLocation idOrDefault(String path, String fallback) {
		if (ResourceLocation.isValidPath(path)) return id(path);
		return id(fallback);
	}

	@Contract("_ -> new")
	public static @NotNull ResourceLocation vanillaId(String path) {
		return ResourceLocation.withDefaultNamespace(path);
	}

	@NotNull
	public static String string(@NotNull String path) {
		return TTConstants.id(path).toString();
	}

	@Contract(pure = true)
	public static @NotNull String safeString(String path) {
		return MOD_ID + "_" + path;
	}

	/**
	 * @return A text component for use in a Config GUI
	 */
	@Contract(value = "_ -> new", pure = true)
	public static @NotNull Component text(String key) {
		return Component.translatable("option." + MOD_ID + "." + key);
	}

	/**
	 * @return A tooltip component for use in a Config GUI
	 */
	@Contract(value = "_ -> new", pure = true)
	public static @NotNull Component tooltip(String key) {
		return Component.translatable("tooltip." + MOD_ID + "." + key);
	}
}
