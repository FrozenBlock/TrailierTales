package net.frozenblock.trailiertales.mixin.common.coffin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public class ItemStackMixin {

	@ModifyExpressionValue(
		method = "addDetailsToTooltip",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z",
			ordinal = 0
		)
	)
	private boolean addCoffinDetails(boolean original) {
		return original || ItemStack.class.cast(this).is(TTBlocks.COFFIN.asItem());
	}
}
