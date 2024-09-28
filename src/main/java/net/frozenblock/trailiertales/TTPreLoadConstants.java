
package net.frozenblock.trailiertales;

import net.frozenblock.trailiertales.mod_compat.TTModIntegrations;

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
		if (IS_DATAGEN) {
			TTModIntegrations.WILDER_WILD_INTEGRATION.getIntegration().disableSnowloggingInDatagen();
		}
	}
}
