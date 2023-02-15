package net.frozenblock.template.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.template.util.TemplateSharedConstants;

public class FrozenLibIntegration extends ModIntegration {
	public FrozenLibIntegration() {
		super("frozenlib");
	}

	@Override
	public void init() {
		TemplateSharedConstants.log("FrozenLib integration ran!", TemplateSharedConstants.UNSTABLE_LOGGING);
	}
}
