package net.frozenblock.trailiertales.mixin.common.boat;

import java.util.function.Supplier;
import net.frozenblock.trailiertales.impl.BoatBannerInterface;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractBoat;
import net.minecraft.world.entity.vehicle.AbstractChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractChestBoat.class)
public abstract class AbstractChestBoatMixin extends AbstractBoat {

	public AbstractChestBoatMixin(EntityType<? extends AbstractBoat> entityType, Level level, Supplier<Item> supplier) {
		super(entityType, level, supplier);
	}

	@Inject(method = "interact", at = @At("HEAD"), cancellable = true)
	public void trailierTales$interact(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> info) {
		if (player.isSecondaryUseActive() && AbstractChestBoat.class.cast(this) instanceof BoatBannerInterface bannerInterface) {
			if (bannerInterface.trailierTales$getBanner().isEmpty()) {
				ItemStack itemStack = player.getItemInHand(hand);
				if (itemStack.is(ItemTags.BANNERS)) {
					if (this.level() instanceof ServerLevel serverLevel) {
						this.spawnAtLocation(serverLevel, bannerInterface.trailierTales$getBanner(), 0.6F);
						bannerInterface.trailierTales$setBanner(itemStack.split(1));
						this.gameEvent(GameEvent.ENTITY_INTERACT, player);
					}
					info.setReturnValue(InteractionResult.SUCCESS);
				}
			} else {
				if (this.level() instanceof ServerLevel serverLevel) {
					this.spawnAtLocation(serverLevel, bannerInterface.trailierTales$getBanner(), 0.6F);
				}
				bannerInterface.trailierTales$setBanner(ItemStack.EMPTY);
				this.gameEvent(GameEvent.ENTITY_INTERACT, player);
				info.setReturnValue(InteractionResult.SUCCESS);
			}
		}
	}
}
