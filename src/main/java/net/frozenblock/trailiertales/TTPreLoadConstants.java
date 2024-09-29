
package net.frozenblock.trailiertales;

import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.lib.config.api.instance.ConfigModification;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.wilderwild.config.WWBlockConfig;

public class TTPreLoadConstants {
	public static final boolean IS_DATAGEN;

	private static boolean isDatagen() {
		boolean isDatagen = false;
		try {
			Class.forName("net.frozenblock.trailiertales.datagen.TTDatagenModule", false, TTPreLoadConstants.class.getClassLoader());
			isDatagen = true;
		} catch (ClassNotFoundException ignored) {}

		return isDatagen;
	}

	static {
		IS_DATAGEN = isDatagen();
		if (IS_DATAGEN && FabricLoader.getInstance().isModLoaded("wilderwild")) {
			ConfigRegistry.register(WWBlockConfig.INSTANCE, new ConfigModification<>(config -> config.snowlogging.snowlogging = false));
		}
	}
}
