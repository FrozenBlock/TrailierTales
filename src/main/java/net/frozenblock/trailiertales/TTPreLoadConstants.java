
package net.frozenblock.trailiertales;

public class TTPreLoadConstants {
	public static final boolean IS_DATAGEN = isDatagen();

	private static boolean isDatagen() {
		boolean isDatagen = false;
		try {
			Class.forName("net.frozenblock.trailiertales.datagen.TTDataGenerator", false, TTPreLoadConstants.class.getClassLoader());
			isDatagen = true;
		} catch (ClassNotFoundException ignored) {}

		return isDatagen;
	}
}
