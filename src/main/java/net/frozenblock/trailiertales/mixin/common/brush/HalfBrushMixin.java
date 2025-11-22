/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.trailiertales.mixin.common.brush;

import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.trailiertales.config.TTItemConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrushItem.class)
public class HalfBrushMixin {

	@Inject(
		method = "onUseTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/item/BrushItem;getUseDuration(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)I",
			shift = At.Shift.AFTER
		)
	)
	public void trailierTales$onUseTick(
		Level level, LivingEntity entity, ItemStack stack, int i, CallbackInfo info,
		@Local(ordinal = 0) BlockHitResult hitResult
	) {
		if (TTItemConfig.EXTRA_BRUSH_PARTICLES) this.trailierTales$halfBrush(level, entity, stack, hitResult, i);
	}

	@Unique
	public void trailierTales$halfBrush(Level level, LivingEntity entity, ItemStack stack, BlockHitResult hitResult, int i) {
		final int brushTimer = BrushItem.class.cast(this).getUseDuration(stack, entity) - i + 1;
		if (brushTimer % 5 == 0 && brushTimer % 10 != 5 && hitResult != null && entity instanceof Player player) {
			final BlockPos pos = hitResult.getBlockPos();
			this.trailierTales$spawnOppositeDustParticles(
				level, hitResult,
				level.getBlockState(pos),
				entity.getViewVector(0F),
				entity.getUsedItemHand() == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite()
			);
			level.playSound(player, pos, SoundEvents.BRUSH_GENERIC, SoundSource.PLAYERS, 0.3F, 0.85F);
		}
	}

	@Unique
	public void trailierTales$spawnOppositeDustParticles(Level level, BlockHitResult hitResult, BlockState state, Vec3 vec3, HumanoidArm arm) {
		final RandomSource random = level.getRandom();
		final int particleCount = random.nextInt(2, 6);
		final BlockParticleOption blockParticleOption = new BlockParticleOption(ParticleTypes.BLOCK, state);

		final Direction direction = hitResult.getDirection();
		final BrushItem.DustParticlesDelta dustParticlesDelta = BrushItem.DustParticlesDelta.fromDirection(vec3, direction);
		final Vec3 hitLocation = hitResult.getLocation();

		final double xPos = hitLocation.x - (direction == Direction.WEST ? 1.0E-6D : 0D);
		final double zPos = hitLocation.z - (direction == Direction.NORTH ? 1.0E-6D : 0D);

		final double armDirection = arm == HumanoidArm.RIGHT ? -3D : 3;
		final double xDelta = dustParticlesDelta.xd() * armDirection;
		final double zDelta = dustParticlesDelta.zd() * armDirection;
		for (int i = 0; i < particleCount; ++i) {
			level.addParticle(
				blockParticleOption,
				xPos, hitLocation.y, zPos,
				xDelta * random.nextDouble(), 0D, zDelta * random.nextDouble()
			);
		}
	}

}
