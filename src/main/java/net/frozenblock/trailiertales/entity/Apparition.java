package net.frozenblock.trailiertales.entity;

import com.mojang.serialization.Dynamic;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawner;
import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinInterface;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionAi;
import net.frozenblock.trailiertales.particle.options.GlowingDustColorTransitionOptions;
import net.frozenblock.trailiertales.registry.RegisterMemoryModuleTypes;
import net.frozenblock.trailiertales.registry.RegisterSounds;
import net.frozenblock.trailiertales.tag.TrailierEntityTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class Apparition extends Monster implements InventoryCarrier, RangedAttackMob {
	private static final Vector3f PARTICLE_COLOR = new Vector3f(162F / 255F, 181F/ 255F, 217F / 255F);
	private static final Vector3f WHITE = new Vector3f(1F, 1F, 1F);
	private static final GlowingDustColorTransitionOptions APPARITION_TO_WHITE = new GlowingDustColorTransitionOptions(
		PARTICLE_COLOR, WHITE, 1F
	);
	public static final ResourceLocation ATTRIBUTE_APPARITION_FOLLOW_RANGE = TrailierConstants.id("apparition_follow_range");
	private static final EntityDataAccessor<ItemStack> ITEM_STACK = SynchedEntityData.defineId(Apparition.class, EntityDataSerializers.ITEM_STACK);
	private static final EntityDataAccessor<Float> TRANSPARENCY = SynchedEntityData.defineId(Apparition.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> OUTER_TRANSPARENCY = SynchedEntityData.defineId(Apparition.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> AID_ANIM_PROGRESS = SynchedEntityData.defineId(Apparition.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> POLTERGEIST_ANIM_PROGRESS = SynchedEntityData.defineId(Apparition.class, EntityDataSerializers.FLOAT);

	private final SimpleContainer inventory = new SimpleContainer(1);
	private float transparency;
	private float outerTransparency;
	public int hiddenTicks;

	//CLIENT VARIABLES
	private float prevTransparency;
	private float prevOuterTransparency;
	private float aidAnimProgress;
	private float prevAidAnimProgress;
	private float poltergeistAnimProgress;
	private float prevPoltergeistAnimProgress;
	private float flicker;
	private float prevFlicker;

	public Apparition(EntityType<? extends Apparition> entityType, Level world) {
		super(entityType, world);
		this.xpReward = 3;
		Arrays.stream(PathType.values()).forEach(pathType -> this.setPathfindingMalus(pathType, 0F));
		this.setPathfindingMalus(PathType.BLOCKED, 8F);
		this.moveControl = new FlyingMoveControl(this, 20, true);
		this.setCanPickUpLoot(this.canPickUpLoot());
		this.blocksBuilding = false;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(ITEM_STACK, ItemStack.EMPTY);
		builder.define(TRANSPARENCY, 0F);
		builder.define(OUTER_TRANSPARENCY, 0F);
		builder.define(AID_ANIM_PROGRESS, 0F);
		builder.define(POLTERGEIST_ANIM_PROGRESS, 0F);
	}

	@NotNull
	public static AttributeSupplier.Builder createApparitionAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, 15D)
			.add(Attributes.FLYING_SPEED, 0.5D)
			.add(Attributes.MOVEMENT_SPEED, 0.5D)
			.add(Attributes.ATTACK_DAMAGE, 3D)
			.add(Attributes.FOLLOW_RANGE, 24D)
			.add(Attributes.KNOCKBACK_RESISTANCE, 0.35D);
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
		this.getBrain().setMemoryWithExpiry(RegisterMemoryModuleTypes.AID_COOLDOWN, Unit.INSTANCE, 100L);
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
	public float getWalkTargetValue(BlockPos pos) {
		Level level = this.level();
		boolean isPosSafe = !level.getBlockState(pos).isCollisionShapeFullBlock(level, pos);
		float successValue = 20F - level.getRawBrightness(pos, 0);
		float punishmentValue = -1F;

		if (this instanceof EntityCoffinInterface entityCoffinInterface) {
			if (entityCoffinInterface.trailierTales$getCoffinData() != null && level instanceof ServerLevel serverLevel) {
				boolean withinCatacombs = CoffinSpawner.isInCatacombsBounds(pos, serverLevel.structureManager());
				if (withinCatacombs) {
					punishmentValue = 0F;
				}
				isPosSafe = isPosSafe && withinCatacombs;
				successValue *= 2F;
			}
		}

		return isPosSafe ? successValue : punishmentValue;
	}

	@Override
	public float getWalkTargetValue(BlockPos pos, @NotNull LevelReader world) {
		return !world.getBlockState(pos).isCollisionShapeFullBlock(world, pos) ? 15F : -0.75F;
	}

	public boolean shouldReturnToHome() {
		if (this instanceof EntityCoffinInterface coffinInterface) {
			if (coffinInterface.trailierTales$getCoffinData() != null) {
				Optional<CoffinSpawner> optional = coffinInterface.trailierTales$getCoffinData().getSpawner(this.level());
				if (optional.isPresent()) {
					return this.position().distanceTo(Vec3.atCenterOf(coffinInterface.trailierTales$getCoffinData().getPos())) >= 58D;
				}
			}
		}
		return false;
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

	public boolean wantsToPickUp(@NotNull ItemEntity itemEntity) {
		return this.wantsToPickUp(itemEntity.getItem()) && this.getTarget() != null;
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
	protected void dropEquipment() {
		super.dropEquipment();
		this.inventory.removeAllItems().forEach(this::spawnAtLocation);
	}

	public float getInnerTransparency() {
		return this.entityData.get(TRANSPARENCY);
	}

	public void setTransparency(float transparency) {
		this.entityData.set(TRANSPARENCY, transparency);
	}

	public float getOuterTransparency() {
		return this.entityData.get(OUTER_TRANSPARENCY);
	}

	public void setOuterTransparency(float transparency) {
		this.entityData.set(OUTER_TRANSPARENCY, transparency);
	}

	public float getAidAnimProgress() {
		return this.entityData.get(AID_ANIM_PROGRESS);
	}

	public void setAidAnimProgress(float progress) {
		this.entityData.set(AID_ANIM_PROGRESS, progress);
	}

	public float getPoltergeistAnimProgress() {
		return this.entityData.get(POLTERGEIST_ANIM_PROGRESS);
	}

	public void setPoltergeistAnimProgress(float progress) {
		this.entityData.set(POLTERGEIST_ANIM_PROGRESS, progress);
	}

	public float getItemYRot(float partialTick) {
		return Mth.cos((this.tickCount + partialTick) / 8F) * 0.35F;
	}

	public float getItemZRot(float partialTick) {
		return Mth.sin((this.tickCount + partialTick) / 8F) * 0.35F;
	}

	public float getFlicker(float partialTick) {
		return 1F - Mth.lerp(partialTick, this.prevFlicker, this.flicker);
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return RegisterSounds.APPARITION_IDLE;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return RegisterSounds.APPARITION_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return RegisterSounds.APPARITION_DEATH;
	}

	@Override
	protected float getSoundVolume() {
		return 0.75F;
	}

	@Override
	public void tick() {
		this.noPhysics = true;
		super.tick();
		this.noPhysics = false;
		this.setNoGravity(true);
		if (!this.level().isClientSide) {
			this.tickTransparency();
			if (this.hiddenTicks <= 0) {
				this.spawnParticles(this.random.nextInt(0, 3), APPARITION_TO_WHITE);
			}
			this.hiddenTicks = (Math.max(0, this.hiddenTicks - 1));
			this.setVisibleItem(this.inventory.getItems().getFirst().copy());
		} else {
			this.prevTransparency = this.transparency;
			this.prevOuterTransparency = this.outerTransparency;
			this.transparency = this.getInnerTransparency();
			this.outerTransparency = this.getOuterTransparency();
			this.prevFlicker = this.flicker;
			this.flicker += ((this.random.nextFloat() * 0.175F) - this.flicker) * 0.5F;
			this.prevAidAnimProgress = this.aidAnimProgress;
			this.aidAnimProgress += (this.getAidAnimProgress() - this.aidAnimProgress) * 0.3F;
			this.prevPoltergeistAnimProgress = this.poltergeistAnimProgress;
			this.poltergeistAnimProgress += (this.getPoltergeistAnimProgress() - this.poltergeistAnimProgress) * 0.3F;
		}
	}

	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || !this.getVisibleItem().isEmpty();
	}

	@Override
	public boolean canBeHitByProjectile() {
		return super.canBeHitByProjectile() && this.getInnerTransparency() > 0F;
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
			this.dropItem();
			this.inventory.setItem(0, itemStack);
		}
	}

	public void dropItem() {
		ItemStack currentStack = this.inventory.getItems().getFirst().copyAndClear();
		if (!currentStack.isEmpty()) {
			this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), currentStack));
		}
	}

	@Override
	public @NotNull AABB getAttackBoundingBox() { // Extends access for outside-package classes
		return super.getAttackBoundingBox();
	}

	public void tickTransparency() {
		AtomicReference<Float> transparency = new AtomicReference<>(0F);
		AtomicReference<Float> outerTransparency = new AtomicReference<>(0F);
		BlockPos pos = this.blockPosition();
		if (this.isAiding()) {
			transparency.set(1F);
			outerTransparency.set(1.5F);
		} else {
			if (this.hiddenTicks > 0) {
				transparency.set(0F);
				outerTransparency.set(0F);
			} else {
				BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1)).forEach(blockPos -> {
						if (transparency.get() < 1) {
							transparency.set(Math.max(transparency.get(), this.level().getRawBrightness(blockPos, 0) / (float) LightEngine.MAX_LEVEL));
							outerTransparency.set(transparency.get() * 0.5F);
						}
					}
				);
			}
		}
		this.transparency += (transparency.get() - this.transparency) * (this.hiddenTicks > 0 ? 0.9F : 0.3F);
		if (this.transparency < 0.025F && this.transparency != 0F && transparency.get() == 0F) {
			this.transparency = 0F;
			if (this.hiddenTicks > 0) {
				this.spawnParticles(this.random.nextInt(3, 7), ParticleTypes.POOF);
			}
		} else if (this.transparency > 0.975F && transparency.get() == 1F) {
			this.transparency = 1F;
		}
		this.setTransparency(this.transparency);

		this.outerTransparency += (outerTransparency.get() - this.outerTransparency) * (this.hiddenTicks > 0 ? 0.9F : 0.3F);
		if (this.outerTransparency < 0.025F && this.outerTransparency != 0F && outerTransparency.get() == 0F) {
			this.outerTransparency = 0F;
		} else if (this.outerTransparency > 1.975F && outerTransparency.get() == 2F) {
			this.outerTransparency = 2F;
		}
		this.setOuterTransparency(this.outerTransparency);
	}

	public float getInnerTransparency(float partialTick) {
		return Mth.lerp(partialTick, this.prevTransparency, this.transparency) * 0.8F * (1F - this.getOtherAnimProgress(partialTick));
	}

	public float getOutlineTransparency(float partialTick) {
		return this.getInnerTransparency(partialTick) * 0.7F;
	}

	public float getOuterTransparency(float partialTick) {
		return Mth.lerp(partialTick, this.prevOuterTransparency, this.outerTransparency) * 0.75F * (1F - this.getOtherAnimProgress(partialTick));
	}

	private float getOtherAnimProgress(float partialTick) {
		return Math.max(this.getAidAnimProgress(partialTick), this.getPoltergeistAnimProgress(partialTick));
	}

	public float getAidAnimProgress(float partialTick) {
		return Mth.lerp(partialTick, this.prevAidAnimProgress, this.aidAnimProgress) * 0.85F;
	}

	public float getPoltergeistAnimProgress(float partialTick) {
		return Mth.lerp(partialTick, this.prevPoltergeistAnimProgress, this.poltergeistAnimProgress) * 0.85F;
	}

	@Nullable
	@Override
	public LivingEntity getTarget() {
		return this.getTargetFromBrain();
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt) {
		super.readAdditionalSaveData(nbt);
		this.readInventoryFromTag(nbt, this.registryAccess());
		this.setTransparency(nbt.getFloat("Transparency"));
		this.setOuterTransparency(nbt.getFloat("OuterTransparency"));
		this.setAidAnimProgress(nbt.getFloat("AidAnimProgress"));
		this.setPoltergeistAnimProgress(nbt.getFloat("PoltergeistAnimProgress"));
		this.setVisibleItem(this.inventory.getItems().getFirst().copy());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt) {
		super.addAdditionalSaveData(nbt);
		this.writeInventoryToTag(nbt, this.registryAccess());
		nbt.putFloat("Transparency", this.getInnerTransparency());
		nbt.putFloat("OuterTransparency", this.getOuterTransparency());
		nbt.putFloat("AidAnimProgress", this.getAidAnimProgress());
		nbt.putFloat("PoltergeistAnimProgress", this.getPoltergeistAnimProgress());
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

	public boolean isAiding() {
		return this.getBrain().hasMemoryValue(RegisterMemoryModuleTypes.AIDING_TIME);
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
			&& entity.getType().is(TrailierEntityTags.APPARITION_TARGETABLE)
			&& !livingEntity.isInvulnerable()
			&& !livingEntity.isDeadOrDying()
			&& !livingEntity.isRemoved()
			&& this.level().getWorldBorder().isWithinBounds(livingEntity.getBoundingBox());
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
			ItemStack singleItem = itemStack.copyAndClear();
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
		}
	}
}
