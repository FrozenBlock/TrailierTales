package net.frozenblock.trailiertales.mixin.client.brush;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.config.TTItemConfig;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(RangeSelectItemModel.class)
public class RangeSelectItemModelMixin {

	@ModifyExpressionValue(
		method = "update",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Float;isNaN(F)Z"
		)
	)
	private boolean trailierTales$useSmoothBrushingAnim(
		boolean original,
		ItemStackRenderState itemStackRenderState,
		ItemStack itemStack
	) {
		if (TTItemConfig.SMOOTH_BRUSH_ANIMATION && itemStack.is(Items.BRUSH)) {
			return true;
		}
		return original;
	}
}
