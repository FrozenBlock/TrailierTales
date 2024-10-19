package net.frozenblock.trailiertales.mixin.common.boat;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.trailiertales.impl.BoatBannerInterface;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {

	@ModifyExpressionValue(
		method = "getBoundingBoxForCulling",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/Entity;getBoundingBox()Lnet/minecraft/world/phys/AABB;"
		)
	)
	public AABB trailierTales$extendBannerBoatRenderBox(AABB original, @Local(argsOnly = true) T entity) {
		if (entity instanceof BoatBannerInterface bannerInterface) {
			if (!bannerInterface.trailierTales$getBanner().isEmpty()) {
				return original.inflate(1D, 0D, 1D).expandTowards(0D, 2D, 0D);
			}
		}
		return original;
	}

}
