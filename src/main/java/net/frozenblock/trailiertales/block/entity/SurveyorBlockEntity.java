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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SurveyorBlockEntity extends BlockEntity {
	private int detectionCooldown;
	private int lastDetectionPower;

	public SurveyorBlockEntity(BlockPos pos, BlockState state) {
		super(TTBlockEntityTypes.SURVEYOR, pos, state);
	}

	private static Vec3 chooseClosestPos(Vec3 origin, Vec3 posA, Vec3 posB) {
		if (origin.distanceTo(posA) > origin.distanceTo(posB)) return posB;
		return posA;
	}

	public static Vec3 getEyePosition(BlockPos pos, BlockState state) {
		return getEyePosition(pos, state, 0.55D);
	}

	public static Vec3 getViewEndPosition(BlockPos pos, BlockState state) {
		return getEyePosition(pos, state, 15.5D);
	}

	public static Vec3 getEyePosition(BlockPos pos, BlockState state, double distanceFromCenter) {
		final Direction direction = state.getValue(SurveyorBlock.FACING);
		return pos.getCenter()
			.add(
				distanceFromCenter * (double) direction.getStepX(),
				distanceFromCenter * (double) direction.getStepY(),
				distanceFromCenter * (double) direction.getStepZ()
			);
	}

	@Override
	protected void loadAdditional(ValueInput valueInput) {
		super.loadAdditional(valueInput);
		this.lastDetectionPower = valueInput.getIntOr("last_detection_power", 0);
		this.detectionCooldown = valueInput.getIntOr("detection_cooldown", 0);
	}

	@Override
	protected void saveAdditional(ValueOutput valueOutput) {
		super.saveAdditional(valueOutput);
		valueOutput.putInt("last_detection_power", this.lastDetectionPower);
		valueOutput.putInt("detection_cooldown", this.detectionCooldown);
	}

	public void tickServer(ServerLevel level, BlockPos pos, BlockState state) {
		if (TTPreLoadConstants.STRUCTURE_BUILDING_MODE) return;

		if (this.detectionCooldown > 0) {
			this.detectionCooldown -= 1;
			return;
		}

		this.detectionCooldown = 2;
		int closestDetection = 16;

		final Direction facing = state.getValue(SurveyorBlock.FACING);
		final BlockPos inFrontPos = pos.relative(facing);
		final BlockState inFrontState = level.getBlockState(pos.relative(facing));
		final boolean isBlocked = !canSeeThroughBlock(level, inFrontState, inFrontPos);

		if (!isBlocked) {
			final Vec3 surveyorCenterPos = Vec3.atCenterOf(pos);
			final Vec3 startClipPos = getEyePosition(pos, state);
			final Vec3 endClipPos = getViewEndPosition(pos, state);
			final BlockPos startBlockPos = BlockPos.containing(startClipPos);
			final BlockPos endBlockPos = BlockPos.containing(endClipPos);

			final AABB detectionBox = AABB.encapsulatingFullBlocks(startBlockPos, endBlockPos);
			List<Player> players = level.getEntities(
				EntityTypeTest.forClass(Player.class),
				detectionBox,
				EntitySelector.NO_SPECTATORS
			);

			for (Player player : players) {
				Vec3 closestPoint = closestPointTo(player.getBoundingBox(), startClipPos);
				Optional<Vec3> headPoint = Optional.empty();
				Optional<Vec3> footPoint = Optional.empty();

				tryMakingInvisibilityPoints: {
					if (!player.isInvisible()) break tryMakingInvisibilityPoints;
					if (!player.getItemBySlot(EquipmentSlot.LEGS).isEmpty() || !player.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) break tryMakingInvisibilityPoints;

					if (!player.getItemBySlot(EquipmentSlot.FEET).isEmpty() && detectionBox.contains(player.position())) footPoint = Optional.of(player.position());
					if (!player.getItemBySlot(EquipmentSlot.HEAD).isEmpty() && detectionBox.contains(player.getEyePosition())) headPoint = Optional.of(player.getEyePosition());

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

				final int distance = (int) closestPoint.distanceTo(startClipPos);
				if (distance < closestDetection) {
					final HitResult hitResult = ProjectileUtil.getHitResult(
						closestPoint,
						player,
						EntitySelector.NO_SPECTATORS.and(entity -> !entity.isInvisible() && !entity.getType().is(TTEntityTags.SURVEYOR_IGNORES)),
						surveyorCenterPos.subtract(closestPoint),
						level,
						0F,
						TTClipContextShapeGetters.SURVEYOR_SIGHT
					);
					if (hitResult.getType() == HitResult.Type.BLOCK) {
						final BlockHitResult blockHitResult = (BlockHitResult) hitResult;
						if (blockHitResult.getBlockPos().equals(pos)) closestDetection = distance;
					}
				}
			}
		}

		final int previousDetection = this.lastDetectionPower;
		this.lastDetectionPower = Math.clamp(15 - closestDetection, 0, 15);
		final boolean updateNeighbors = previousDetection != this.lastDetectionPower;
		SurveyorBlock.updatePower(level, pos, state, this.lastDetectionPower, updateNeighbors);
	}

	public static boolean canSeeThroughBlock(BlockGetter level, BlockState state, BlockPos pos) {
		if (state.is(TTBlockTags.SURVEYOR_CAN_SEE_THROUGH) && !state.is(TTBlockTags.SURVEYOR_CANNOT_SEE_THROUGH)) return true;
		return !state.isCollisionShapeFullBlock(level, pos);
	}

	private Vec3 closestPointTo(AABB aabb, Vec3 point) {
		final Vec3[] vec3s = new Vec3[1];
		final double clampedX = Mth.clamp(point.x(), aabb.minX, aabb.maxX);
		final double clampedY = Mth.clamp(point.y(), aabb.minY, aabb.maxY);
		final double clampedZ = Mth.clamp(point.z(), aabb.minZ, aabb.maxZ);
		if (vec3s[0] == null || point.distanceToSqr(clampedX, clampedY, clampedZ) < point.distanceToSqr(vec3s[0])) {
			vec3s[0] = new Vec3(clampedX, clampedY, clampedZ);
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
