package net.frozenblock.trailiertales.mixin.common.brushable_block;

import net.frozenblock.trailiertales.worldgen.impl.suspicious_handler.SuspiciousData;
import net.frozenblock.trailiertales.worldgen.impl.suspicious_handler.SuspiciousDataInterface;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerLevel.class)
public class ServerLevelMixin implements SuspiciousDataInterface {

	@Unique
	private final SuspiciousData trailierTales$suspiciousData = new SuspiciousData();

	@Unique
	@Override
	public SuspiciousData trailierTales$getSuspiciousData() {
		return this.trailierTales$suspiciousData;
	}
}
