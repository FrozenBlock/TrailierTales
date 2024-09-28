package net.frozenblock.trailiertales.mod_compat.wilderwild;

public class NoOpWWIntegration extends AbstractWWIntegration {
	public NoOpWWIntegration() {
		super();
	}

	@Override
	public boolean newClaySounds() {
		return false;
	}

	@Override
	public boolean newGravelSounds() {
		return false;
	}

	@Override
	public void disableSnowloggingInDatagen() {
	}
}
