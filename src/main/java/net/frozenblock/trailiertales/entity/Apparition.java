package net.frozenblock.trailiertales.entity;

import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionAi;
import net.frozenblock.trailiertales.entity.ai.apparition.impl.EntityPossessionInterface;
import net.frozenblock.trailiertales.registry.RegisterEntities;
import net.frozenblock.trailiertales.registry.RegisterMemoryModuleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class Apparition extends Monster implements InventoryCarrier, RangedAttackMob {
	private static final Vector3f SOUL_PARTICLE_COLOR = new Vector3f(96F / 255F, 245F / 255F, 250F / 255F);
	private static final Vector3f WHITE = new Vector3f(1F, 1F, 1F);
	private static final DustColorTransitionOptions SOUL_TO_WHITE = new DustColorTransitionOptions(
		SOUL_PARTICLE_COLOR, WHITE, 1.0F
	);
	public static final ResourceLocation ATTRIBUTE_APPARITION_FOLLOW_RANGE = TrailierConstants.id("apparition_follow_range");
	private static final EntityDataAccessor<ItemStack> ITEM_STACK = SynchedEntityData.defineId(Apparition.class, EntityDataSerializers.ITEM_STACK);
	private static final EntityDataAccessor<Float> ITEM_X_ROT_SCALE = SynchedEntityData.defineId(Apparition.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> TARGET_ITEM_X_ROT_SCALE = SynchedEntityData.defineId(Apparition.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> ITEM_Y_ROT_SCALE = SynchedEntityData.defineId(Apparition.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> TARGET_ITEM_Y_ROT_SCALE = SynchedEntityData.defineId(Apparition.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> ITEM_Z_ROT_SCALE = SynchedEntityData.defineId(Apparition.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> TARGET_ITEM_Z_ROT_SCALE = SynchedEntityData.defineId(Apparition.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> SHOOT_PROGRESS = SynchedEntityData.defineId(Apparition.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> TRANSPARENCY = SynchedEntityData.defineId(Apparition.class, EntityDataSerializers.FLOAT);

	private final SimpleContainer inventory = new SimpleContainer(1);
	private float transparency;
	private boolean detectedProjectile;
	private int detectedProjectileCooldownTicks;

	//CLIENT VARIABLES
	private float itemXRotScale;
	private float prevItemXRotScale;
	private float itemYRotScale;
	private float prevItemYRotScale;
	private float itemZRotScale;
	private float prevItemZRotScale;
	private float prevTransparency;

	public Apparition(EntityType<? extends Apparition> entityType, Level world) {
		super(entityType, world);
		this.xpReward = 3;
		this.setPathfindingMalus(PathType.BLOCKED, 0.0F);
		this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0.0F);
		this.setPathfindingMalus(PathType.LAVA, 0.0F);
		this.setPathfindingMalus(PathType.DAMAGE_FIRE, 0.0F);
		this.setPathfindingMalus(PathType.DANGER_FIRE, 0.0F);
		this.setPathfindingMalus(PathType.POWDER_SNOW, 0.0F);
		this.setPathfindingMalus(PathType.FENCE, 0.0F);
		this.setPathfindingMalus(PathType.WATER, 0.0F);
		this.setPathfindingMalus(PathType.WATER_BORDER, 0.0F);
		this.setPathfindingMalus(PathType.DANGER_OTHER, 0.0F);
		this.setPathfindingMalus(PathType.DAMAGE_OTHER, 0.0F);
		this.setPathfindingMalus(PathType.DOOR_WOOD_CLOSED, 0.0F);
		this.setPathfindingMalus(PathType.DOOR_IRON_CLOSED, 0.0F);
		this.setPathfindingMalus(PathType.BREACH, 0.0F);
		this.setPathfindingMalus(PathType.LEAVES, 0.0F);
		this.setPathfindingMalus(PathType.STICKY_HONEY, 0.0F);
		this.moveControl = new FlyingMoveControl(this, 20, true);
		this.setCanPickUpLoot(this.canPickUpLoot());
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(ITEM_STACK, ItemStack.EMPTY);
		builder.define(ITEM_X_ROT_SCALE, (float) this.random.triangle(0D, 0.75D));
		builder.define(TARGET_ITEM_X_ROT_SCALE, (float) this.random.triangle(0D, 0.75D));
		builder.define(ITEM_Y_ROT_SCALE, (float) this.random.triangle(0D, 0.75D));
		builder.define(TARGET_ITEM_Y_ROT_SCALE, (float) this.random.triangle(0D, 0.75D));
		builder.define(ITEM_Z_ROT_SCALE, (float) this.random.triangle(0D, 0.75D));
		builder.define(TARGET_ITEM_Z_ROT_SCALE, (float) this.random.triangle(0D, 0.75D));
		builder.define(SHOOT_PROGRESS, 0F);
		builder.define(TRANSPARENCY, 0F);
	}

	@NotNull
	public static AttributeSupplier.Builder createApparitionAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, 15D)
			.add(Attributes.FLYING_SPEED, 0.5D)
			.add(Attributes.MOVEMENT_SPEED, 0.5D)
			.add(Attributes.ATTACK_DAMAGE, 3D);
	}

	@Override
	@NotNull
	protected PathNavigation createNavigation(Level level) {
		FlyingPathNavigation flyingPathNavigation = new FlyingPathNavigation(this, level);
		flyingPathNavigation.setCanFloat(false);
		flyingPathNavigation.setCanOpenDoors(false);
		flyingPathNavigation.setCanPassDoors(true);
		return flyingPathNavigation;
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
		this.getBrain().setMemoryWithExpiry(RegisterMemoryModuleTypes.POSSESSION_COOLDOWN, Unit.INSTANCE, 200L);
		return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public boolean isInLiquid() {
		return false;
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	protected boolean isAffectedByFluids() {
		return false;
	}

	@Override
	public boolean isInWaterOrBubble() {
		return false;
	}

	@Override
	public boolean isInWaterOrRain() {
		return false;
	}

	@Override
	public boolean isInWaterRainOrBubble() {
		return false;
	}

	@Override
	public boolean isInLava() {
		return false;
	}

	@Override
	public boolean isInWater() {
		return false;
	}

	@Override
	public float getWalkTargetValue(BlockPos pos, LevelReader world) {
		return 0F;
	}

	@Override
	protected boolean canRide(Entity entity) {
		return false;
	}

	@Override
	public boolean dampensVibrations() {
		return true;
	}

	@Override
	public @NotNull SimpleContainer getInventory() {
		return this.inventory;
	}

	public ItemStack getVisibleItem() {
		return this.entityData.get(ITEM_STACK);
	}

	public void setVisibleItem(@NotNull ItemStack itemStack) {
		this.getEntityData().set(ITEM_STACK, itemStack);
	}

	@Override
	public boolean canPickUpLoot() {
		return !this.isOnPickupCooldown();
	}

	private boolean isOnPickupCooldown() {
		return this.getBrain().checkMemory(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, MemoryStatus.VALUE_PRESENT);
	}

	@Override
	public boolean wantsToPickUp(ItemStack stack) {
		return this.inventory.getItems().getFirst().isEmpty();
	}

	@Override
	protected void pickUpItem(@NotNull ItemEntity item) {
		ItemEntity newItemEntity = new ItemEntity(this.level(), item.getX(), item.getY(), item.getZ(), item.getItem().split(1));
		this.level().addFreshEntity(newItemEntity);
		InventoryCarrier.pickUpItem(this, this, newItemEntity);
	}

	@Override
	public void onItemPickup(ItemEntity item) {
		if (!this.level().isClientSide) {
			this.setVisibleItem(item.getItem());
		}
		super.onItemPickup(item);
	}

	@Override
	protected void dropEquipment() {
		super.dropEquipment();
		this.inventory.removeAllItems().forEach(this::spawnAtLocation);
	}

	public float getItemRotX(float partialTick) {
		return (this.tickCount + partialTick) * Mth.lerp(partialTick, this.prevItemXRotScale, this.itemXRotScale) * Mth.PI;
	}

	public float getItemRotY(float partialTick) {
		return (this.tickCount + partialTick) * Mth.lerp(partialTick, this.prevItemYRotScale, this.itemYRotScale) * Mth.PI;
	}

	public float getItemRotZ(float partialTick) {
		return (this.tickCount + partialTick) * Mth.lerp(partialTick, this.prevItemZRotScale, this.itemZRotScale) * Mth.PI;
	}

	public float getItemXRotScale() {
		return this.entityData.get(ITEM_X_ROT_SCALE);
	}

	public void setItemXRotScale(float itemXRotScale) {
		this.entityData.set(ITEM_X_ROT_SCALE, itemXRotScale);
	}

	public float getTargetItemXRotScale() {
		return this.entityData.get(TARGET_ITEM_X_ROT_SCALE);
	}

	public void setTargetItemXRotScale(float targetItemXRotScale) {
		this.entityData.set(TARGET_ITEM_X_ROT_SCALE, targetItemXRotScale);
	}

	public float getItemYRotScale() {
		return this.entityData.get(ITEM_Y_ROT_SCALE);
	}

	public void setItemYRotScale(float itemYRotScale) {
		this.entityData.set(ITEM_Y_ROT_SCALE, itemYRotScale);
	}

	public float getTargetItemYRotScale() {
		return this.entityData.get(TARGET_ITEM_Y_ROT_SCALE);
	}

	public void setTargetItemYRotScale(float targetItemYRotScale) {
		this.entityData.set(TARGET_ITEM_Y_ROT_SCALE, targetItemYRotScale);
	}

	public float getItemZRotScale() {
		return this.entityData.get(ITEM_Z_ROT_SCALE);
	}

	public void setItemZRotScale(float itemZRotScale) {
		this.entityData.set(ITEM_Z_ROT_SCALE, itemZRotScale);
	}

	public float getTargetItemZRotScale() {
		return this.entityData.get(TARGET_ITEM_Z_ROT_SCALE);
	}

	public void setTargetItemZRotScale(float targetItemZRotScale) {
		this.entityData.set(TARGET_ITEM_Z_ROT_SCALE, targetItemZRotScale);
	}

	public float getShootProgress() {
		return this.entityData.get(SHOOT_PROGRESS);
	}

	public void setShootProgress(float progress) {
		this.entityData.set(SHOOT_PROGRESS, progress);
	}

	public float getTransparency() {
		return this.entityData.get(TRANSPARENCY);
	}

	public void setTransparency(float transparency) {
		this.entityData.set(TRANSPARENCY, transparency);
	}

	@Override
	public void tick() {
		this.noPhysics = true;
		super.tick();
		this.noPhysics = false;
		this.setNoGravity(true);
		if (!this.level().isClientSide) {
			this.scanForProjectiles();
			this.tickTransparency();
			this.tickItemRotation(this.random);
			if (this.detectedProjectileCooldownTicks <= 0) {
				this.spawnParticles(this.random.nextInt(0, 5), SOUL_TO_WHITE);
			}
		} else {
			this.prevItemXRotScale = this.itemXRotScale;
			this.prevItemYRotScale = this.itemYRotScale;
			this.prevItemZRotScale = this.itemZRotScale;
			this.itemXRotScale = this.getItemXRotScale();
			this.itemYRotScale = this.getItemYRotScale();
			this.itemZRotScale = this.getItemZRotScale();

			this.prevTransparency = this.transparency;
			this.transparency = this.getTransparency();
		}
	}

	@Override
	public boolean canBeHitByProjectile() {
		return super.canBeHitByProjectile() && this.getTransparency() > 0F;
	}

	@Override
	public boolean hurt(@NotNull DamageSource source, float amount) {
		if (source.is(DamageTypeTags.IS_PROJECTILE)) {
			if (source.getDirectEntity() instanceof Projectile projectile) {
				if (projectile instanceof AbstractArrow abstractArrow) {
					this.swapItem(abstractArrow.getPickupItemStackOrigin());
					abstractArrow.discard();
				} else if (projectile instanceof ItemSupplier itemSupplier) {
					this.swapItem(itemSupplier.getItem());
					projectile.discard();
				}
			}
		}
		boolean bl = super.hurt(source, amount);
		if (this.level().isClientSide) {
			return false;
		}
		if (bl) {
			if (source.getEntity() instanceof LivingEntity livingEntity) {
				ApparitionAi.wasHurtBy(this, livingEntity);
			}
		}
		return bl;
	}

	public void swapItem(@NotNull ItemStack itemStack) {
		if (!itemStack.isEmpty() && !this.level().isClientSide) {
			ItemStack currentStack = this.inventory.getItems().getFirst().copyAndClear();
			if (!currentStack.isEmpty()) {
				this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), currentStack));
			}
			this.inventory.setItem(0, itemStack);
			this.setVisibleItem(itemStack);
		}
	}

	public void scanForProjectiles() {
		List<Projectile> projectiles = this.level()
			.getEntitiesOfClass(
				Projectile.class, this.getBoundingBox().inflate(2.5D, 2.5D, 2.5D),
				projectile ->
					projectile.getType() != RegisterEntities.DAMAGING_THROWABLE_ITEM_PROJECTILE
						&& (projectile.getOwner() == null || projectile.getOwner().getType() != RegisterEntities.APPARITION)
						&& !projectile.onGround()
						&& (!(projectile instanceof AbstractArrow arrow) || !arrow.inGround)
			);
		this.detectedProjectile = !projectiles.isEmpty();
		this.detectedProjectileCooldownTicks = this.detectedProjectile ? 20 : Math.max(0, this.detectedProjectileCooldownTicks - 1);
	}

	public void tickTransparency() {
		AtomicReference<Float> transparency = new AtomicReference<>(0F);
		BlockPos pos = this.blockPosition();
		if (this.detectedProjectileCooldownTicks > 0) {
			transparency.set(0F);
		} else {
			BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1)).forEach(blockPos ->
				transparency.set(Math.max(transparency.get(), this.level().getRawBrightness(blockPos, 0)) / (float) LightEngine.MAX_LEVEL)
			);
		}
		this.transparency += (transparency.get() - this.transparency) * (this.detectedProjectileCooldownTicks > 0 ? 0.9F : 0.3F);
		if (this.transparency < 0.1F && this.transparency != 0F && transparency.get() == 0F) {
			this.transparency = 0F;
			this.blocksBuilding = false;

			if (this.detectedProjectileCooldownTicks > 0) {
				this.spawnParticles(this.random.nextInt(3, 7), ParticleTypes.POOF);
			}
		} else if (this.transparency > 0.9F && transparency.get() == 1F) {
			this.transparency = 1F;
		}

		this.setTransparency(this.transparency);
	}

	public float getTransparency(float partialTick) {
		return Mth.lerp(partialTick, this.prevTransparency, this.transparency);
	}

	public void tickItemRotation(@NotNull RandomSource random) {
		if (random.nextFloat() < 0.05F) {
			this.setTargetItemXRotScale((float) Math.clamp(this.getTargetItemXRotScale() + random.triangle(0D, 0.15D), -1F, 1F));
		}

		if (random.nextFloat() < 0.05F) {
			this.setTargetItemYRotScale((float) Math.clamp(this.getTargetItemYRotScale() + random.triangle(0D, 0.15D), -1F, 1F));
		}

		if (random.nextFloat() < 0.05F) {
			this.setTargetItemZRotScale((float) Math.clamp(this.getTargetItemZRotScale() + random.triangle(0D, 0.15D), -1F, 1F));
		}

		this.setItemXRotScale(this.getItemXRotScale() + (this.getTargetItemXRotScale() - this.getItemXRotScale()) * 0.05F);
		this.setItemXRotScale(this.getItemYRotScale() + (this.getTargetItemYRotScale() - this.getItemYRotScale()) * 0.05F);
		this.setItemXRotScale(this.getItemZRotScale() + (this.getTargetItemZRotScale() - this.getItemZRotScale()) * 0.05F);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt) {
		super.readAdditionalSaveData(nbt);
		this.readInventoryFromTag(nbt, this.registryAccess());
		this.setTargetItemXRotScale(nbt.getFloat("TargetItemXRotScale"));
		this.setTargetItemYRotScale(nbt.getFloat("TargetItemYRotScale"));
		this.setTargetItemZRotScale(nbt.getFloat("TargetItemZRotScale"));
		this.setItemXRotScale(nbt.getFloat("ItemXRotScale"));
		this.setItemYRotScale(nbt.getFloat("ItemYRotScale"));
		this.setItemZRotScale(nbt.getFloat("ItemZRotScale"));
		this.setTransparency(nbt.getFloat("Transparency"));
		this.setShootProgress(nbt.getFloat("ShootProgress"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt) {
		super.addAdditionalSaveData(nbt);
		this.writeInventoryToTag(nbt, this.registryAccess());
		nbt.putFloat("TargetItemXRotScale", this.getTargetItemXRotScale());
		nbt.putFloat("TargetItemYRotScale", this.getTargetItemYRotScale());
		nbt.putFloat("TargetItemZRotScale", this.getTargetItemZRotScale());
		nbt.putFloat("ItemXRotScale", this.getItemXRotScale());
		nbt.putFloat("ItemYRotScale", this.getItemYRotScale());
		nbt.putFloat("ItemZRotScale", this.getItemZRotScale());
		nbt.putFloat("Transparency", this.getTransparency());
		nbt.putFloat("ShootProgress", this.getShootProgress());
	}

	@Override
	@NotNull
	protected Brain.Provider<Apparition> brainProvider() {
		return Brain.provider(ApparitionAi.MEMORY_TYPES, ApparitionAi.SENSOR_TYPES);
	}

	@Override
	@NotNull
	protected Brain<Apparition> makeBrain(Dynamic<?> dynamic) {
		return ApparitionAi.makeBrain(this, this.brainProvider().makeBrain(dynamic));
	}

	@SuppressWarnings("unchecked")
	@Override
	@NotNull
	public Brain<Apparition> getBrain() {
		return (Brain<Apparition>) super.getBrain();
	}

	@Override
	protected void customServerAiStep() {
		this.level().getProfiler().push("apparitionBrain");
		this.getBrain().tick((ServerLevel) this.level(), this);
		this.level().getProfiler().pop();
		this.level().getProfiler().push("apparitionActivityUpdate");
		ApparitionAi.updateActivity(this);
		this.level().getProfiler().pop();
		super.customServerAiStep();
	}

	@Override
	protected void sendDebugPackets() {
		super.sendDebugPackets();
		DebugPackets.sendEntityBrain(this);
	}

	@Contract("null->false")
	public boolean canTargetEntity(@Nullable Entity entity) {
		return entity instanceof LivingEntity livingEntity
			&& this.level() == livingEntity.level()
			&& !this.level().getDifficulty().equals(Difficulty.PEACEFUL)
			&& EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingEntity)
			&& !this.isAlliedTo(livingEntity)
			&& livingEntity.getType() != EntityType.ARMOR_STAND
			&& livingEntity.getType() != RegisterEntities.APPARITION
			&& !livingEntity.isInvulnerable()
			&& !livingEntity.isDeadOrDying()
			&& !livingEntity.isRemoved()
			&& this.level().getWorldBorder().isWithinBounds(livingEntity.getBoundingBox())
			&& !this.inventory.getItems().getFirst().isEmpty();
	}

	public boolean canPossessEntity(Entity entity) {
		return entity instanceof EntityPossessionInterface entityPossessable
			&& !entityPossessable.trailierTales$getPossessionData().isPossessed()
			&& entity.level() == this.level()
			&& entity.isAlive()
			&& EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity)
			&& entity.getType() != RegisterEntities.APPARITION;
	}

	public void possessEntity(@NotNull Mob mob) {
		if (mob instanceof EntityPossessionInterface entityPossessable) {
			entityPossessable.trailierTales$getPossessionData().setPossessor(this);
			mob.getAttributes().getInstance(Attributes.FOLLOW_RANGE)
				.addPermanentModifier(new AttributeModifier(ATTRIBUTE_APPARITION_FOLLOW_RANGE, Math.max(48D * (this.getMaxHealth() / this.getHealth()), 16D), AttributeModifier.Operation.ADD_VALUE));
			this.discard();
		}
	}

	@Override
	protected void doPush(Entity entity) {
	}

	@Override
	protected void pushEntities() {
	}

	public void spawnParticles(int count, ParticleOptions particleOptions) {
		if (this.level() instanceof ServerLevel level) {
			level.sendParticles(
				particleOptions,
				this.getX(),
				this.getY(0.6666666666666666D),
				this.getZ(),
				count,
				this.getBbWidth() / 4F,
				this.getBbHeight() / 4F,
				this.getBbWidth() / 4F,
				0.05D
			);
		}
	}

	@Override
	public void performRangedAttack(LivingEntity target, float pullProgress) {
		ItemStack itemStack = this.inventory.getItems().getFirst();
		if (!itemStack.isEmpty()) {
			Projectile projectile;
			ItemStack singleItem = itemStack.split(1);
			if (singleItem.getItem() instanceof ProjectileItem projectileItem) {
				projectile = projectileItem.asProjectile(this.level(), this.getEyePosition(), singleItem, this.getDirection());
			} else {
				DamagingThrowableItemProjectile thrownItem = new DamagingThrowableItemProjectile(this.level(), this);
				thrownItem.setItem(singleItem);
				projectile = thrownItem;
			}
			projectile.setOwner(this);

			double targetY = target.getEyeY() - 1.1F;
			double xDifference = target.getX() - this.getX();
			double yDifference = targetY - projectile.getY();
			double zDifference = target.getZ() - this.getZ();
			double yAdjustment = Math.sqrt(xDifference * xDifference + zDifference * zDifference) * 0.2F;
			projectile.shoot(xDifference, yDifference + yAdjustment, zDifference, Math.max(0.5F, pullProgress), (float)(14 - this.level().getDifficulty().getId() * 4));
			this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
			this.level().addFreshEntity(projectile);
			this.setVisibleItem(itemStack);
		}
	}
}
