package net.frozenblock.trailiertales.mod_compat.wilderwild;

import net.frozenblock.lib.integration.api.ModIntegration;

public abstract class AbstractWWIntegration extends ModIntegration {

	public AbstractWWIntegration() {
		super("wilderwild");
	}

	@Override
	public void init() {
	}

	abstract public boolean newClaySounds();

	abstract public boolean newGravelSounds();
}
