
package net.frozenblock.trailiertales;

import net.fabricmc.loader.api.FabricLoader;
import java.util.Arrays;

public class TTPreLoadConstants {
	public static final boolean IS_DATAGEN = isDatagen();

	private static boolean isDatagen() {
		return Arrays.stream(
			FabricLoader.getInstance().getLaunchArguments(true)
		).toList().stream().anyMatch(string -> string.contains("datagen"));
	}
}
