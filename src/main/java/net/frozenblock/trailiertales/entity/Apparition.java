package net.frozenblock.trailiertales.entity;

import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionAi;
import net.frozenblock.trailiertales.registry.RegisterEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
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

	private final SimpleContainer inventory = new SimpleContainer(1);
	private float itemXRotScale = (float) this.random.triangle(0D, 0.75D);
	private float prevItemXRotScale = this.itemXRotScale;
	private float itemYRotScale = (float) this.random.triangle(0D, 0.75D);
	private float targetItemXRotScale = (float) this.random.triangle(0D, 0.75D);
	private float prevItemYRotScale = this.itemYRotScale;
	private float targetItemYRotScale = (float) this.random.triangle(0D, 0.75D);
	private float itemZRotScale = (float) this.random.triangle(0D, 0.75D);
	private float prevItemZRotScale = this.itemZRotScale;
	private float targetItemZRotScale = (float) this.random.triangle(0D, 0.75D);

	private static final EntityDataAccessor<ItemStack> ITEM_STACK = SynchedEntityData.defineId(Apparition.class, EntityDataSerializers.ITEM_STACK);

	public Apparition(EntityType<? extends Apparition> entityType, Level world) {
		super(entityType, world);
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

	@NotNull
	public static AttributeSupplier.Builder createApparitionAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, 10.0)
			.add(Attributes.FLYING_SPEED, 0.5F)
			.add(Attributes.MOVEMENT_SPEED, 0.5F)
			.add(Attributes.ATTACK_DAMAGE, 3.0);
	}

	@Override
	@NotNull
	protected PathNavigation createNavigation(Level level) {
		FlyingPathNavigation flyingPathNavigation = new FlyingPathNavigation(this, level);
		flyingPathNavigation.setCanFloat(true);
		flyingPathNavigation.setCanOpenDoors(false);
		flyingPathNavigation.setCanPassDoors(true);
		return flyingPathNavigation;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public float getWalkTargetValue(BlockPos pos, LevelReader world) {
		return 0.0F;
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
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(ITEM_STACK, new ItemStack(Items.DIAMOND_SWORD));
	}

	@Override
	public @NotNull SimpleContainer getInventory() {
		return this.inventory;
	}

	public ItemStack getVisibleItem() {
		return this.entityData.get(ITEM_STACK);
	}

	public void setVisibleItem(@NotNull ItemStack itemStack) {
		ItemStack visibleItem = this.getVisibleItem();
		if (itemStack.isEmpty() && !visibleItem.isEmpty()) {
			this.getEntityData().set(ITEM_STACK, ItemStack.EMPTY);
		} else if (itemStack.getItem() != this.getVisibleItem().getItem()) {
			this.getEntityData().set(ITEM_STACK, itemStack);
		}
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

	@Override
	public void tick() {
		this.noPhysics = true;
		super.tick();
		this.noPhysics = false;
		this.setNoGravity(true);
		this.scanForProjectiles();
		this.tickTransparency();
		this.tickItemRotation(this.random);
		if (this.detectedProjectileCooldownTicks <= 0) {
			this.spawnParticles(this.random.nextInt(0, 5), SOUL_TO_WHITE);
		}
		if (!this.level().isClientSide) {
			this.setVisibleItem(this.inventory.getItems().getFirst());
		}
	}

	private float transparency;
	private float prevTransparency;
	private boolean detectedProjectile;
	private int detectedProjectileCooldownTicks;

	@Override
	public boolean canBeHitByProjectile() {
		return super.canBeHitByProjectile() && this.transparency > 0F;
	}

	@Override
	public boolean hurt(@NotNull DamageSource source, float amount) {
		if (source.is(DamageTypeTags.IS_PROJECTILE)) {
			if (source.getDirectEntity() instanceof Projectile projectile) {
				if (projectile instanceof AbstractArrow abstractArrow) {
					this.grabItem(abstractArrow.getPickupItemStackOrigin());
					abstractArrow.discard();
				} else if (projectile instanceof ItemSupplier itemSupplier) {
					this.grabItem(itemSupplier.getItem());
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

	public void grabItem(@NotNull ItemStack itemStack) {
		if (!itemStack.isEmpty()) {
			this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), this.inventory.getItems().getFirst().copyAndClear()));
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
				transparency.set(Math.max(transparency.get(), this.level().getBrightness(LightLayer.BLOCK, blockPos)) / (float) LightEngine.MAX_LEVEL)
			);
		}
		this.prevTransparency = this.transparency;
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
	}

	public float getTransparency(float partialTick) {
		return Mth.lerp(partialTick, this.prevTransparency, this.transparency);
	}

	public void tickItemRotation(@NotNull RandomSource random) {
		this.prevItemXRotScale = this.itemXRotScale;
		this.prevItemYRotScale = this.itemYRotScale;
		this.prevItemZRotScale = this.itemZRotScale;

		if (random.nextFloat() < 0.05F) {
			this.targetItemXRotScale = (float) Math.clamp(this.targetItemXRotScale + random.triangle(0D, 0.15D), -1F, 1F);
		}

		if (random.nextFloat() < 0.05F) {
			this.targetItemYRotScale = (float) Math.clamp(this.targetItemYRotScale + random.triangle(0D, 0.15D), -1F, 1F);
		}

		if (random.nextFloat() < 0.05F) {
			this.targetItemZRotScale = (float) Math.clamp(this.targetItemZRotScale + random.triangle(0D, 0.15D), -1F, 1F);
		}

		this.itemXRotScale += (this.targetItemXRotScale - this.itemXRotScale) * 0.05F;
		this.itemYRotScale += (this.targetItemYRotScale - this.itemYRotScale) * 0.05F;
		this.itemZRotScale += (this.targetItemZRotScale - this.itemZRotScale) * 0.05F;
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt) {
		super.readAdditionalSaveData(nbt);
		this.readInventoryFromTag(nbt, this.registryAccess());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt) {
		super.addAdditionalSaveData(nbt);
		this.writeInventoryToTag(nbt, this.registryAccess());
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
		}
	}
}
