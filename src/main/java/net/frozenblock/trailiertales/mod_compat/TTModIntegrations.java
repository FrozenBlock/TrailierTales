package net.frozenblock.trailiertales.mod_compat;

import java.util.function.Supplier;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.integration.api.ModIntegrationSupplier;
import net.frozenblock.lib.integration.api.ModIntegrations;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.mod_compat.wilderwild.AbstractWWIntegration;
import net.frozenblock.trailiertales.mod_compat.wilderwild.NoOpWWIntegration;
import net.frozenblock.trailiertales.mod_compat.wilderwild.WWIntegration;

public final class TTModIntegrations {
	public static final ModIntegration FROZENLIB_INTEGRATION = registerAndGet(FrozenLibIntegration::new, "frozenlib");
	public static final ModIntegrationSupplier<AbstractWWIntegration> WILDER_WILD_INTEGRATION = register(
		() -> new WWIntegration(),
		NoOpWWIntegration::new,
		"wilderwild"
	);

	private TTModIntegrations() {
		throw new UnsupportedOperationException("TrailierModIntegrations contains only static declarations.");
	}

	public static void init() {
	}

	public static ModIntegrationSupplier<? extends ModIntegration> register(Supplier<? extends ModIntegration> integration, String modID) {
		return ModIntegrations.register(integration, TTConstants.MOD_ID, modID);
	}

	public static <T extends ModIntegration> ModIntegrationSupplier<T> register(Supplier<T> integration, Supplier<T> unloadedIntegration, String modID) {
		return ModIntegrations.register(integration, unloadedIntegration, TTConstants.MOD_ID, modID);
	}

	public static <T extends ModIntegration> ModIntegration registerAndGet(Supplier<T> integration, String modID) {
		return register(integration, modID).getIntegration();
	}
}
