package net.frozenblock.trailiertales.mixin.client.haunt;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
@Mixin(Gui.class)
public class GuiMixin {
	@Unique
	private static final ResourceLocation TRAILIER_TALES$HEART_HAUNT = TrailierConstants.id("hud/heart/haunt");
	@Unique
	private static final ResourceLocation TRAILIER_TALES$ARMOR_HAUNT = TrailierConstants.id("hud/armor_full_haunt");
	@Unique
	private static final ResourceLocation TRAILIER_TALES$ARMOR_HALF_HAUNT = TrailierConstants.id("hud/armor_half_haunt");
	@Unique
	private static final ResourceLocation TRAILIER_TALES$FOOD_HAUNT = TrailierConstants.id("hud/food_haunt");
	@Unique
	private static final ResourceLocation TRAILIER_TALES$AIR_HAUNT = TrailierConstants.id("hud/air_haunt");

	@Unique
	private static boolean trailierTales$isHaunted;
	@Unique
	private static int trailierTales$hauntTicks;

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
		trailierTales$isHaunted = player.hasEffect(RegisterMobEffects.HAUNT);
		if (trailierTales$isHaunted) {
			trailierTales$hauntTicks = Math.min(40, trailierTales$hauntTicks + 1);
		} else {
			trailierTales$hauntTicks = Math.max(0, trailierTales$hauntTicks - 1);
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
		return (int) Mth.lerp(trailierTales$getHauntProgress(), original, doubleRef.get());
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
		return (int) (absorptionAmount * (1F - trailierTales$getHauntProgress()));
	}

	@ModifyVariable(method = "renderHearts", at = @At("HEAD"), argsOnly = true, ordinal = 3)
	private int trailierTales$hideRegeneration(int regeneratingHeartIndex) {
		return trailierTales$isHaunted ? Integer.MAX_VALUE : regeneratingHeartIndex;
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
		return trailierTales$isHaunted ? (int) maxHealth : lastHealth;
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
		return trailierTales$isHaunted ? (int) maxHealth : health;
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
		if (trailierTales$isHaunted) {
			this.trailierTales$forceRenderHeart(graphics, TRAILIER_TALES$HEART_HAUNT, x, y);
		} else {
			original.call(instance, graphics, type, x, y, hardcore, blinking, half);
		}
	}

	@ModifyExpressionValue(
		method = "renderArmor",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/player/Player;getArmorValue()I"
		)
	)
	private static int trailierTales$hideArmor(int armorValue) {
		return (int) (armorValue * (1F - trailierTales$getHauntProgress()));
	}

	@ModifyExpressionValue(
		method = "renderArmor",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/gui/Gui;ARMOR_FULL_SPRITE:Lnet/minecraft/resources/ResourceLocation;"
		)
	)
	private static ResourceLocation trailierTales$hauntedFullArmor(ResourceLocation original) {
		return trailierTales$isHaunted ? TRAILIER_TALES$ARMOR_HAUNT : original;
	}

	@ModifyExpressionValue(
		method = "renderArmor",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/gui/Gui;ARMOR_HALF_SPRITE:Lnet/minecraft/resources/ResourceLocation;"
		)
	)
	private static ResourceLocation trailierTales$hauntedHalfArmor(ResourceLocation original) {
		return trailierTales$isHaunted ? TRAILIER_TALES$ARMOR_HALF_HAUNT : original;
	}

	@WrapWithCondition(
		method = "renderFood",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V",
				ordinal = 0,
				shift = At.Shift.AFTER
			)
		)
	)
	private boolean trailierTales$removeExtraHunger(GuiGraphics instance, ResourceLocation texture, int x, int y, int width, int height) {
		return !trailierTales$isHaunted;
	}

	@WrapOperation(
		method = "renderFood",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 0
		)
	)
	private void trailierTales$hauntedHunger(GuiGraphics instance, ResourceLocation texture, int x, int y, int width, int height, Operation<Void> original) {
		original.call(instance, texture, x, y, width, height);
		if (trailierTales$isHaunted) {
			original.call(instance, TRAILIER_TALES$FOOD_HAUNT, x, y, width, height);
		}
	}

	@ModifyExpressionValue(
		method = "renderFood",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/food/FoodData;getSaturationLevel()F"
		)
	)
	private float trailierTales$hideHungerChange(float saturationLevel) {
		return trailierTales$isHaunted ? 1F : saturationLevel;
	}

	@WrapOperation(
		method = "renderPlayerHealth",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Math;min(II)I"
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/world/entity/player/Player;getMaxAirSupply()I"
			)
		)
	)
	private int trailierTales$hideAirSupply(int airSupply, int maxAirSupply, Operation<Integer> original) {
		int finalSupply = original.call(airSupply, maxAirSupply);
		if (trailierTales$isHaunted) {
			if (finalSupply != maxAirSupply) {
				return (int) (finalSupply * (1F -trailierTales$getHauntProgress()));
			}
		}
		return finalSupply;
	}

	@WrapOperation(
		method = "renderPlayerHealth",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/client/gui/Gui;AIR_SPRITE:Lnet/minecraft/resources/ResourceLocation;"
			)
		)
	)
	private void trailierTales$hauntedAirSupply(GuiGraphics instance, ResourceLocation texture, int x, int y, int width, int height, Operation<Void> original) {
		texture = trailierTales$isHaunted ? TRAILIER_TALES$AIR_HAUNT : texture;
		original.call(instance, texture, x, y, width, height);
	}

	@Unique
	private static float trailierTales$getHauntProgress() {
		return trailierTales$hauntTicks / 40F;
	}

	@Unique
	private void trailierTales$forceRenderHeart(GuiGraphics graphics, ResourceLocation texture, int x, int y) {
		RenderSystem.enableBlend();
		graphics.blitSprite(texture, x, y, 9, 9);
		RenderSystem.disableBlend();
	}
}
