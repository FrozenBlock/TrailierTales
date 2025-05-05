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

package net.frozenblock.trailiertales.mixin.client.brushable_block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.block.NonFallingBrushableBlock;
import net.frozenblock.trailiertales.config.TTBlockConfig;
import net.frozenblock.trailiertales.registry.TTParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BrushableBlock.class)
public class BrushableBlockMixin {

	@Inject(method = "animateTick", at = @At("HEAD"), cancellable = true)
	public void trailierTales$animateTick(BlockState state, Level world, BlockPos pos, RandomSource random, CallbackInfo info) {
		if (TTBlockConfig.SUSPICIOUS_BLOCK_PARTICLES) trailierTales$emitConnectionParticlesForPlayer(world, pos, random);
		if (BrushableBlock.class.cast(this) instanceof NonFallingBrushableBlock) info.cancel();
	}

	@Unique
	private static void trailierTales$emitConnectionParticlesForPlayer(@NotNull Level world, BlockPos pos, RandomSource random) {
		Player player = Minecraft.getInstance().player;
		if (player != null && player.isHolding(Items.BRUSH)) {
			Vec3 vec3 = Vec3.atCenterOf(pos).add(0D, 0.2D, 0D);
			double playerDistance = vec3.distanceTo(player.position());
			double distanceThreshold = random.nextDouble() * player.blockInteractionRange() * 1.5D;
			if (playerDistance < distanceThreshold) {
				boolean canCreateParticle = false;
				BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
				for (Direction direction : Direction.values()) {
					BlockState state = world.getBlockState(mutableBlockPos.setWithOffset(pos, direction));
					if (!(state.isAir() || !state.isFaceSturdy(world, mutableBlockPos, direction.getOpposite()))) continue;
					canCreateParticle = true;
				}

				if (canCreateParticle) {
					Vec3 halfPlayerHeight = vec3.vectorTo(player.position().add(0D, player.getBbHeight() / 2D, 0D));
					Vec3 startPos = halfPlayerHeight.offsetRandom(random, 1F);
					world.addParticle(TTParticleTypes.SUSPICIOUS_CONNECTION, vec3.x(), vec3.y(), vec3.z(), startPos.x(), startPos.y(), startPos.z());
				}
			}
		}
	}

}
