
package net.frozenblock.trailiertales;

import java.nio.file.Path;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TTPreLoadConstants {
	public static final String MOD_ID = "trailiertales";

	@Contract(pure = true)
	public static @NotNull Path configPath(String name, boolean json5) {
		return Path.of("./config/" + MOD_ID + "/" + name + "." + (json5 ? "json5" : "json"));
	}
}
