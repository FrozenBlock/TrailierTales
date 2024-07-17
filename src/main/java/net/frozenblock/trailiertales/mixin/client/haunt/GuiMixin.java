package net.frozenblock.trailiertales.mixin.client.haunt;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import com.mojang.blaze3d.systems.RenderSystem;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.registry.RegisterMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
	@Unique
	private static final ResourceLocation TRAILIER_TALES$HEART_HAUNT = TrailierConstants.id("hud/heart/haunt");

	@Unique
	private boolean trailierTales$isHaunted;
	@Unique
	private int trailierTales$hauntTicks;

	@Shadow
	@Final
	private Minecraft minecraft;

	@Inject(
		method = "Lnet/minecraft/client/gui/Gui;tick()V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/player/LocalPlayer;getInventory()Lnet/minecraft/world/entity/player/Inventory;",
			ordinal = 0,
			shift = At.Shift.AFTER
		)
	)
	private void trailierTales$setHauntedInfo(CallbackInfo info) {
		Player player = this.minecraft.player;
		this.trailierTales$isHaunted = player.hasEffect(RegisterMobEffects.HAUNT);
		if (this.trailierTales$isHaunted) {
			this.trailierTales$hauntTicks = Math.min(50, this.trailierTales$hauntTicks + 1);
		} else {
			this.trailierTales$hauntTicks = Math.max(0, this.trailierTales$hauntTicks - 1);
		}
	}

	@ModifyExpressionValue(
		method = "renderPlayerHealth",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/player/Player;getAttributeValue(Lnet/minecraft/core/Holder;)D"
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/world/entity/ai/attributes/Attributes;MAX_HEALTH:Lnet/minecraft/core/Holder;"
			)
		)
	)
	private double trailierTales$captureMaxHealthAttribute(
		double original,
		@Share("trailierTales$maxHealthAttribute") LocalDoubleRef doubleRef
	) {
		doubleRef.set(original);
		return original;
	}

	@ModifyExpressionValue(
		method = "renderPlayerHealth",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Math;max(II)I",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/world/entity/ai/attributes/Attributes;MAX_HEALTH:Lnet/minecraft/core/Holder;"
			)
		)
	)
	private int trailierTales$lerpBackHealth(
		int original, @Share("trailierTales$maxHealthAttribute") LocalDoubleRef doubleRef
	) {
		return (int) Mth.lerp(this.trailierTales$getHauntProgress(), original, doubleRef.get());
	}

	@ModifyExpressionValue(
		method = "renderPlayerHealth",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/Mth;ceil(F)I",
			ordinal = 1
		)
	)
	private int trailierTales$hauntAbsorption(int absorptionAmount) {
		return (int) (absorptionAmount * (1F - this.trailierTales$getHauntProgress()));
	}

	@ModifyVariable(method = "renderHearts", at = @At("HEAD"), argsOnly = true, ordinal = 3)
	private int trailierTales$hideRegeneration(int regeneratingHeartIndex) {
		return this.trailierTales$isHaunted ? Integer.MAX_VALUE : regeneratingHeartIndex;
	}

	@ModifyVariable(method = "renderHearts", at = @At("HEAD"), argsOnly = true, ordinal = 4)
	private int trailierTales$renderFullBarA(
		int lastHealth,
		GuiGraphics graphics,
		Player player,
		int x,
		int y,
		int lines,
		int regeneratingHeartIndex,
		float maxHealth
	) {
		return this.trailierTales$isHaunted ? (int) maxHealth : lastHealth;
	}

	@ModifyVariable(method = "renderHearts", at = @At("HEAD"), argsOnly = true, ordinal = 5)
	private int trailierTales$renderFullBarB(
		int health,
		GuiGraphics graphics,
		Player player,
		int x,
		int y,
		int lines,
		int regeneratingHeartIndex,
		float maxHealth
	) {
		return this.trailierTales$isHaunted ? (int) maxHealth : health;
	}

	@WrapOperation(
		method = "renderHearts",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIZZZ)V"
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIZZZ)V",
				shift = At.Shift.AFTER
			)
		)
	)
	private void trailierTales$renderHauntedHeart(
		Gui instance, GuiGraphics graphics, Gui.HeartType type, int x, int y, boolean hardcore, boolean blinking, boolean half, Operation<Void> original
	) {
		if (this.trailierTales$isHaunted) {
			this.trailierTales$forceRenderHeart(graphics, TRAILIER_TALES$HEART_HAUNT, x, y);
		} else {
			original.call(instance, graphics, type, x, y, hardcore, blinking, half);
		}
	}

	@Unique
	private float trailierTales$getHauntProgress() {
		return this.trailierTales$hauntTicks / 50F;
	}

	@Unique
	private void trailierTales$forceRenderHeart(GuiGraphics graphics, ResourceLocation texture, int x, int y) {
		RenderSystem.enableBlend();
		graphics.blitSprite(texture, x, y, 9, 9);
		RenderSystem.disableBlend();
	}
}
