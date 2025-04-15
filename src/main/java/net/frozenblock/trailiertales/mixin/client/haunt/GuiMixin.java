/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.mixin.client.haunt;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.registry.TTMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
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
	private static final ResourceLocation TRAILIER_TALES$HEART_HAUNT = TTConstants.id("hud/heart/haunt");
	@Unique
	private static final ResourceLocation TRAILIER_TALES$ARMOR_HAUNT = TTConstants.id("hud/armor_full_haunt");
	@Unique
	private static final ResourceLocation TRAILIER_TALES$ARMOR_HALF_HAUNT = TTConstants.id("hud/armor_half_haunt");
	@Unique
	private static final ResourceLocation TRAILIER_TALES$FOOD_HAUNT = TTConstants.id("hud/food_haunt");
	@Unique
	private static final ResourceLocation TRAILIER_TALES$AIR_HAUNT = TTConstants.id("hud/air_haunt");

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
		trailierTales$isHaunted = player.hasEffect(TTMobEffects.HAUNT);
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
		@Share("trailierTales$maxHealthAttribute") LocalDoubleRef maxHealthAttribute
	) {
		maxHealthAttribute.set(original);
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
		int original, @Share("trailierTales$maxHealthAttribute") LocalDoubleRef maxHealthAttribute
	) {
		return (int) Mth.lerp(trailierTales$getHauntProgress(), original, maxHealthAttribute.get());
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

	@ModifyExpressionValue(
		method = "renderHearts",
		at = @At(
			value = "CONSTANT",
			ordinal = 0,
			args = "intValue=4"
		)
	)
	private int trailierTales$shakeHearts(int original) {
		return trailierTales$isHaunted ? Integer.MAX_VALUE : original;
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
			this.trailierTales$renderHauntedHeart(graphics, x, y);
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
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V"
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V",
				ordinal = 0,
				shift = At.Shift.AFTER
			)
		)
	)
	private boolean trailierTales$removeExtraHunger(GuiGraphics instance, RenderPipeline renderPipeline, ResourceLocation texture, int x, int y, int width, int height) {
		return !trailierTales$isHaunted;
	}

	@ModifyExpressionValue(
		method = "renderFood",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/food/FoodData;getSaturationLevel()F",
			ordinal = 0
		)
	)
	private float trailierTales$shakeHungerA(float original) {
		return trailierTales$isHaunted ? 0F : original;
	}

	@ModifyExpressionValue(
		method = "renderFood",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/food/FoodData;getFoodLevel()I",
			ordinal = 0
		)
	)
	private int trailierTales$shakeHungerB(int original) {
		return trailierTales$isHaunted ? 0 : original;
	}

	@WrapOperation(
		method = "renderFood",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V",
			ordinal = 0
		)
	)
	private void trailierTales$hauntedHunger(GuiGraphics instance, RenderPipeline renderPipeline, ResourceLocation texture, int x, int y, int width, int height, Operation<Void> original) {
		original.call(instance, renderPipeline, texture, x, y, width, height);
		if (trailierTales$isHaunted) {
			original.call(instance, renderPipeline, TRAILIER_TALES$FOOD_HAUNT, x, y, width, height);
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
		method = "renderAirBubbles",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Math;clamp(JII)I"
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/world/entity/player/Player;getMaxAirSupply()I"
			)
		)
	)
	private int trailierTales$hideAirSupply(long airSupply, int noSupply, int maxAirSupply, Operation<Integer> original) {
		int finalSupply = original.call(airSupply, noSupply, maxAirSupply);
		if (trailierTales$isHaunted) {
			if (finalSupply != maxAirSupply) {
				return (int) (finalSupply * (1F -trailierTales$getHauntProgress()));
			}
		}
		return finalSupply;
	}

	@WrapOperation(
		method = "renderAirBubbles",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V"
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/client/gui/Gui;AIR_SPRITE:Lnet/minecraft/resources/ResourceLocation;"
			)
		)
	)
	private void trailierTales$hauntedAirSupply(GuiGraphics instance, RenderPipeline renderPipeline, ResourceLocation texture, int x, int y, int width, int height, Operation<Void> original) {
		texture = trailierTales$isHaunted ? TRAILIER_TALES$AIR_HAUNT : texture;
		original.call(instance, renderPipeline, texture, x, y, width, height);
	}

	@Unique
	private static float trailierTales$getHauntProgress() {
		return trailierTales$hauntTicks / 40F;
	}

	@Unique
	private void trailierTales$renderHauntedHeart(@NotNull GuiGraphics graphics, int x, int y) {
		graphics.blitSprite(RenderPipelines.GUI_TEXTURED, TRAILIER_TALES$HEART_HAUNT, x, y, 9, 9);
	}
}
