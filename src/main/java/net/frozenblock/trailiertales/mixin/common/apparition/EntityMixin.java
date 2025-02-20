package net.frozenblock.trailiertales.mixin.common.apparition;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import java.util.List;
import net.frozenblock.trailiertales.entity.Apparition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public class EntityMixin {

	@ModifyExpressionValue(
		method = "collide",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;getEntityCollisions(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;"
		)
	)
	private List<VoxelShape> trailierTales$noApparitionCollisions(List<VoxelShape> original) {
		if (Entity.class.cast(this) instanceof Apparition) return List.of();
		return original;
	}

}
