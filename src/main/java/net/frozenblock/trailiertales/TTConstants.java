/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.trailiertales;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Contract;
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

	// DEBUG
	public static final boolean DEBUG_COFFINS = SharedConstants.debugFlag("TRAILIERTALES_COFFINS");

	// LOGGING
	public static void log(String string, boolean shouldLog) {
		if (shouldLog) LOGGER.info(string);
	}

	@Contract("_ -> new")
	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static Identifier idOrDefault(String path, String fallback) {
		if (Identifier.isValidPath(path)) return id(path);
		return id(fallback);
	}

	@Contract("_ -> new")
	public static Identifier vanillaId(String path) {
		return Identifier.withDefaultNamespace(path);
	}

	public static String string(String path) {
		return TTConstants.id(path).toString();
	}

	@Contract(pure = true)
	public static String safeString(String path) {
		return MOD_ID + "_" + path;
	}

	/**
	 * @return A text component for use in a Config GUI
	 */
	@Contract(value = "_ -> new", pure = true)
	public static Component text(String key) {
		return Component.translatable("option." + MOD_ID + "." + key);
	}

	/**
	 * @return A tooltip component for use in a Config GUI
	 */
	@Contract(value = "_ -> new", pure = true)
	public static Component tooltip(String key) {
		return Component.translatable("tooltip." + MOD_ID + "." + key);
	}
}
