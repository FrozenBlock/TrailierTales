package net.frozenblock.trailiertales.mixin.common.boat;

import net.frozenblock.trailiertales.impl.BoatBannerInterface;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.WalkAnimationState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Boat.class, priority = 100)
public abstract class BoatMixin extends VehicleEntity implements BoatBannerInterface {
	@Unique
	private static final EntityDataAccessor<ItemStack> TRAILIER_TALES$BANNER = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.ITEM_STACK);
	@Unique
	private final WalkAnimationState trailierTales$walkAnimation = new WalkAnimationState();

	public BoatMixin(EntityType<?> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(method = "defineSynchedData", at = @At("TAIL"))
	public void trailierTales$defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo info) {
		builder.define(TRAILIER_TALES$BANNER, ItemStack.EMPTY);
	}

	@Unique
	@Override
	public ItemStack trailierTales$getBanner() {
		return this.entityData.get(TRAILIER_TALES$BANNER);
	}

	@Unique
	@Override
	public void trailierTales$setBanner(ItemStack stack) {
		this.entityData.set(TRAILIER_TALES$BANNER, stack);
	}

	@Unique
	@Override
	public WalkAnimationState trailierTales$getWalkAnimationState() {
		return this.trailierTales$walkAnimation;
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void trailierTales$addAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		if (!this.trailierTales$getBanner().isEmpty()) {
			nbt.put("TrailierTalesBanner", this.trailierTales$getBanner().save(this.registryAccess()).copy());
		}
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void trailierTales$readAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		if (nbt.contains("TrailierTalesBanner", 10)) {
			this.trailierTales$setBanner(ItemStack.parse(this.registryAccess(), nbt.getCompound("TrailierTalesBanner")).orElse(ItemStack.EMPTY));
		}
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void trailierTales$tick(CallbackInfo info) {
		this.trailierTales$calculateEntityAnimation(true);
	}

	@Unique
	public void trailierTales$calculateEntityAnimation(boolean flutter) {
		float f = (float) Mth.length(this.getX() - this.xo, flutter ? this.getY() - this.yo : 0.0, this.getZ() - this.zo);
		this.trailierTales$updateWalkAnimation(f);
	}

	@Unique
	protected void trailierTales$updateWalkAnimation(float limbDistance) {
		float f = Math.min(limbDistance, 1F);
		this.trailierTales$walkAnimation.update(f, 0.1F);
	}

	@Inject(
		method = "interact",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/vehicle/VehicleEntity;interact(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;",
			shift = At.Shift.BEFORE
		),
		cancellable = true
	)
	public void trailierTales$interact(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> info) {
		if (player.isSecondaryUseActive()) {
			if (this.trailierTales$getBanner().isEmpty()) {
				ItemStack itemStack = player.getItemInHand(hand);
				if (itemStack.is(ItemTags.BANNERS)) {
					if (!this.level().isClientSide()) {
						this.spawnAtLocation(this.trailierTales$getBanner(), 0.6F);
						this.trailierTales$setBanner(itemStack.split(1));
						this.gameEvent(GameEvent.ENTITY_INTERACT, player);
					}
					info.setReturnValue(InteractionResult.sidedSuccess(this.level().isClientSide));
				}
			} else {
				this.spawnAtLocation(this.trailierTales$getBanner(), 0.6F);
				this.trailierTales$setBanner(ItemStack.EMPTY);
				this.gameEvent(GameEvent.ENTITY_INTERACT, player);
				info.setReturnValue(InteractionResult.sidedSuccess(this.level().isClientSide));
			}
		}
	}
}
