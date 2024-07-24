package net.frozenblock.trailiertales.block.entity;

import java.util.List;
import java.util.Optional;
import net.frozenblock.trailiertales.block.SurveyorBlock;
import net.frozenblock.trailiertales.registry.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
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
		super(RegisterBlockEntities.SURVEYOR, pos, state);
	}

	private static Vec3 chooseClosestPos(@NotNull Vec3 origin, Vec3 posA, Vec3 posB) {
		if (origin.distanceTo(posA) > origin.distanceTo(posB)) {
			return posB;
		}
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
		//this.lastDetectionPower = nbt.getInt("last_detection_power");
		//this.detectionCooldown = nbt.getInt("detection_cooldown");
	}

	@Override
	protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
		super.saveAdditional(nbt, lookupProvider);
		//nbt.putInt("last_detection_power", this.lastDetectionPower);
		//nbt.putInt("detection_cooldown", this.detectionCooldown);
	}

	public void tickServer(ServerLevel serverLevel, BlockPos pos, BlockState state) {
		/*
		if (this.detectionCooldown <= 0) {
			this.detectionCooldown = 2;

			int closestDetection = 16;

			BlockPos inFrontPos = pos.relative(state.getValue(SurveyorBlock.FACING));
			BlockState inFrontState = serverLevel.getBlockState(pos.relative(state.getValue(SurveyorBlock.FACING)));
			boolean isBlocked = inFrontState.isCollisionShapeFullBlock(level, inFrontPos);

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
							EntitySelector.NO_SPECTATORS.and(entity -> !entity.isInvisible()),
							surveyorCenterPos.subtract(closestPoint),
							serverLevel,
							0F,
							ClipContext.Block.COLLIDER
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
			this.lastDetectionPower = Math.clamp(15 - closestDetection, 0, 15);
			SurveyorBlock.updatePower(serverLevel, pos, state, this.lastDetectionPower);
		} else {
			this.detectionCooldown -= 1;
		}
		 */
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
