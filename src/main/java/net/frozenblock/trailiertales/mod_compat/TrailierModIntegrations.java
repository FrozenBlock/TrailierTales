package net.frozenblock.trailiertales.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.integration.api.ModIntegrationSupplier;
import net.frozenblock.lib.integration.api.ModIntegrations;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.mod_compat.wilderwild.AbstractWWIntegration;
import net.frozenblock.trailiertales.mod_compat.wilderwild.NoOpWWIntegration;
import net.frozenblock.trailiertales.mod_compat.wilderwild.WWIntegration;
import java.util.function.Supplier;

public final class TrailierModIntegrations {
	public static final ModIntegrationSupplier<AbstractWWIntegration> SIMPLE_COPPER_PIPES_INTEGRATION = register(
		WWIntegration::new,
		NoOpWWIntegration::new,
		"wilderwild"
	);

	private TrailierModIntegrations() {
		throw new UnsupportedOperationException("TrailierModIntegrations contains only static declarations.");
	}

	public static void init() {
	}

	public static ModIntegrationSupplier<? extends ModIntegration> register(Supplier<? extends ModIntegration> integration, String modID) {
		return ModIntegrations.register(integration, TrailierConstants.MOD_ID, modID);
	}

	public static <T extends ModIntegration> ModIntegrationSupplier<T> register(Supplier<T> integration, Supplier<T> unloadedIntegration, String modID) {
		return ModIntegrations.register(integration, unloadedIntegration, TrailierConstants.MOD_ID, modID);
	}

	public static <T extends ModIntegration> ModIntegration registerAndGet(Supplier<T> integration, String modID) {
		return ModIntegrations.register(integration, TrailierConstants.MOD_ID, modID).getIntegration();
	}
}
