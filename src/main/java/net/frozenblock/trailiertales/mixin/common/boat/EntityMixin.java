package net.frozenblock.trailiertales.mixin.common.boat;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.trailiertales.impl.BoatBannerInterface;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public class EntityMixin {

	@ModifyExpressionValue(
		method = "getBoundingBoxForCulling",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/Entity;getBoundingBox()Lnet/minecraft/world/phys/AABB;"
		)
	)
	public AABB trailierTales$extendBannerBoatRenderBox(AABB original) {
		if (Entity.class.cast(this) instanceof BoatBannerInterface bannerInterface) {
			if (!bannerInterface.trailierTales$getBanner().isEmpty()) {
				return original.inflate(1D, 0D, 1D).expandTowards(0D, 2.5D, 0D);
			}
		}
		return original;
	}

}
