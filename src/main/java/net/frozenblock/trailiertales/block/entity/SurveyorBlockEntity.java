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

package net.frozenblock.trailiertales.block.entity;

import java.util.List;
import java.util.Optional;
import net.frozenblock.trailiertales.TTPreLoadConstants;
import net.frozenblock.trailiertales.block.SurveyorBlock;
import net.frozenblock.trailiertales.block.impl.TTClipContextShapeGetters;
import net.frozenblock.trailiertales.registry.TTBlockEntityTypes;
import net.frozenblock.trailiertales.tag.TTBlockTags;
import net.frozenblock.trailiertales.tag.TTEntityTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SurveyorBlockEntity extends BlockEntity {
	private int detectionCooldown;
	private int lastDetectionPower;

	public SurveyorBlockEntity(BlockPos pos, BlockState state) {
		super(TTBlockEntityTypes.SURVEYOR, pos, state);
	}

	private static Vec3 chooseClosestPos(@NotNull Vec3 origin, Vec3 posA, Vec3 posB) {
		if (origin.distanceTo(posA) > origin.distanceTo(posB)) return posB;
		return posA;
	}

	public static @NotNull Vec3 getEyePosition(@NotNull BlockPos pos, @NotNull BlockState state) {
		return getEyePosition(pos, state, 0.55D);
	}

	public static @NotNull Vec3 getViewEndPosition(@NotNull BlockPos pos, @NotNull BlockState state) {
		return getEyePosition(pos, state, 15.5D);
	}

	public static @NotNull Vec3 getEyePosition(@NotNull BlockPos pos, @NotNull BlockState state, double distanceFromCenter) {
		Direction direction = state.getValue(SurveyorBlock.FACING);
		return pos.getCenter()
			.add(
				distanceFromCenter * (double) direction.getStepX(),
				distanceFromCenter * (double) direction.getStepY(),
				distanceFromCenter * (double) direction.getStepZ()
			);
	}

	@Override
	protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
		super.loadAdditional(nbt, lookupProvider);
		this.lastDetectionPower = nbt.getInt("last_detection_power");
		this.detectionCooldown = nbt.getInt("detection_cooldown");
	}

	@Override
	protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
		super.saveAdditional(nbt, lookupProvider);
		nbt.putInt("last_detection_power", this.lastDetectionPower);
		nbt.putInt("detection_cooldown", this.detectionCooldown);
	}

	public void tickServer(ServerLevel serverLevel, BlockPos pos, BlockState state) {
		if (TTPreLoadConstants.STRUCTURE_BUILDING_MODE) return;

		if (this.detectionCooldown <= 0) {
			this.detectionCooldown = 2;

			int closestDetection = 16;

			Direction facing = state.getValue(SurveyorBlock.FACING);
			BlockPos inFrontPos = pos.relative(facing);
			BlockState inFrontState = serverLevel.getBlockState(pos.relative(facing));
			boolean isBlocked = !canSeeThroughBlock(this.level, inFrontState, inFrontPos);

			if (!isBlocked) {
				Vec3 surveyorCenterPos = Vec3.atCenterOf(pos);
				Vec3 startClipPos = getEyePosition(pos, state);
				Vec3 endClipPos = getViewEndPosition(pos, state);
				BlockPos startBlockPos = BlockPos.containing(startClipPos);
				BlockPos endBlockPos = BlockPos.containing(endClipPos);

				AABB detectionBox = AABB.encapsulatingFullBlocks(startBlockPos, endBlockPos);
				List<Player> players = serverLevel.getEntities(
					EntityTypeTest.forClass(Player.class),
					detectionBox,
					EntitySelector.NO_SPECTATORS
				);

				for (Player player : players) {
					Vec3 closestPoint = closestPointTo(player.getBoundingBox(), startClipPos);
					Optional<Vec3> headPoint = Optional.empty();
					Optional<Vec3> footPoint = Optional.empty();
					if (player.isInvisible()) {
						if (player.getInventory().armor.get(1).isEmpty() && player.getInventory().armor.get(2).isEmpty()) {
							if (!player.getInventory().armor.get(0).isEmpty() && detectionBox.contains(player.position())) {
								footPoint = Optional.of(player.position());
							}
							if (!player.getInventory().armor.get(3).isEmpty() && detectionBox.contains(player.getEyePosition())) {
								headPoint = Optional.of(player.getEyePosition());
							}

							if (headPoint.isPresent() && footPoint.isPresent()) {
								closestPoint = chooseClosestPos(closestPoint, headPoint.get(), footPoint.get());
							} else if (headPoint.isPresent()) {
								closestPoint = headPoint.get();
							} else if (footPoint.isPresent()) {
								closestPoint = footPoint.get();
							} else {
								continue;
							}
						}
					}
					int distance = (int) closestPoint.distanceTo(startClipPos);
					if (distance < closestDetection) {
						HitResult hitResult = ProjectileUtil.getHitResult(
							closestPoint,
							player,
							EntitySelector.NO_SPECTATORS.and(entity -> !entity.isInvisible() && !entity.getType().is(TTEntityTags.SURVEYOR_IGNORES)),
							surveyorCenterPos.subtract(closestPoint),
							serverLevel,
							0F,
							TTClipContextShapeGetters.SURVEYOR_SIGHT
						);
						if (hitResult.getType() == HitResult.Type.BLOCK) {
							BlockHitResult blockHitResult = (BlockHitResult) hitResult;
							if (blockHitResult.getBlockPos().equals(pos)) {
								closestDetection = distance;
							}
						}
					}
				}
			}
			int previousDetection = this.lastDetectionPower;
			this.lastDetectionPower = Math.clamp(15 - closestDetection, 0, 15);
			boolean updateNeighbors = previousDetection != this.lastDetectionPower;
			SurveyorBlock.updatePower(serverLevel, pos, state, this.lastDetectionPower, updateNeighbors);
		} else {
			this.detectionCooldown -= 1;
		}
	}

	public static boolean canSeeThroughBlock(BlockGetter level, @NotNull BlockState blockState, BlockPos pos) {
		if (blockState.is(TTBlockTags.SURVEYOR_CAN_SEE_THROUGH) && !blockState.is(TTBlockTags.SURVEYOR_CANNOT_SEE_THROUGH)) return true;
		return !blockState.isCollisionShapeFullBlock(level, pos);
	}

	private Vec3 closestPointTo(@NotNull AABB aabb, @NotNull Vec3 point) {
		Vec3[] vec3s = new Vec3[1];
		double d = Mth.clamp(point.x(), aabb.minX, aabb.maxX);
		double e = Mth.clamp(point.y(), aabb.minY, aabb.maxY);
		double f = Mth.clamp(point.z(), aabb.minZ, aabb.maxZ);
		if (vec3s[0] == null || point.distanceToSqr(d, e, f) < point.distanceToSqr(vec3s[0])) {
			vec3s[0] = new Vec3(d, e, f);
		}
		return vec3s[0];
	}

	public int getLastDetectionPower() {
		return this.lastDetectionPower;
	}

	public void setLastDetectionPower(int lastDetectionPower) {
		this.lastDetectionPower = lastDetectionPower;
	}
}
