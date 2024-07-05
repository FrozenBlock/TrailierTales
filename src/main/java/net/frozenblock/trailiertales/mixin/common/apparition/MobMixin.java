package net.frozenblock.trailiertales.mixin.common.apparition;

import net.frozenblock.trailiertales.entity.ai.apparition.impl.EntityPossessionData;
import net.frozenblock.trailiertales.entity.ai.apparition.impl.EntityPossessionInterface;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public class MobMixin implements EntityPossessionInterface {

	@Unique
	private final EntityPossessionData trailierTales$entityPossessionData = new EntityPossessionData(Mob.class.cast(this));

	@Unique
	@Override
	public EntityPossessionData trailierTales$getPossessionData() {
		return this.trailierTales$entityPossessionData;
	}


	@Inject(
		method = "baseTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;getProfiler()Lnet/minecraft/util/profiling/ProfilerFiller;",
			ordinal = 0,
			shift = At.Shift.AFTER
		)
	)
	public void trailierTales$baseTick(CallbackInfo info) {
		Mob mob = Mob.class.cast(this);
		this.trailierTales$entityPossessionData.tick(mob, mob.level());
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void trailierTales$addAdditionalSaveData(CompoundTag tag, CallbackInfo info) {
		if (this.trailierTales$entityPossessionData != null) {
			this.trailierTales$entityPossessionData.saveCompoundTag(tag);
		}
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void trailierTales$readAdditionalSaveData(CompoundTag tag, CallbackInfo info) {
		this.trailierTales$entityPossessionData.loadCompoundTag(tag);
	}
}
