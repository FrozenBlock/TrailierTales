package net.frozenblock.trailiertales.mixin.common.boat;

import net.frozenblock.trailiertales.impl.BoatBannerInterface;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBoat.class)
public abstract class ChestBoatMixin extends Boat {

	public ChestBoatMixin(EntityType<? extends Boat> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(method = "interact", at = @At("HEAD"), cancellable = true)
	public void trailierTales$interact(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> info) {
		if (player.isSecondaryUseActive() && ChestBoat.class.cast(this) instanceof BoatBannerInterface bannerInterface) {
			ItemStack itemStack = player.getItemInHand(hand);
			if (!itemStack.is(ItemTags.BANNERS) && !bannerInterface.trailierTales$getBanner().isEmpty()) {
				this.spawnAtLocation(bannerInterface.trailierTales$getBanner());
				bannerInterface.trailierTales$setBanner(ItemStack.EMPTY);
				info.setReturnValue(InteractionResult.sidedSuccess(this.level().isClientSide));
			}
		}
	}
}
