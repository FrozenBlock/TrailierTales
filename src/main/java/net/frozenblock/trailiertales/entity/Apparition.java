package net.frozenblock.trailiertales.entity;

import com.mojang.serialization.Dynamic;
import net.frozenblock.trailiertales.entity.ai.ApparitionAi;
import net.frozenblock.trailiertales.registry.RegisterEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
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
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.lighting.LightEngine;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Apparition extends Monster {
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
		this.moveControl = new FlyingMoveControl(this, 20, true);
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
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(ITEM_STACK, new ItemStack(Items.DIAMOND_SWORD));
	}

	public ItemStack getVisibleItem() {
		return this.entityData.get(ITEM_STACK);
	}

	public void setVisibleItem(ItemStack itemStack) {
		this.getEntityData().set(ITEM_STACK, itemStack);
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

	private static final Vector3f SOUL_PARTICLE_COLOR = new Vector3f(96F / 255F, 245F / 255F, 250F / 255F);
	private static final Vector3f WHITE = new Vector3f(1F, 1F, 1F);
	private static final DustColorTransitionOptions SOUL_TO_WHITE = new DustColorTransitionOptions(
		SOUL_PARTICLE_COLOR, WHITE, 1.0F
	);

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
	}

	private float transparency;
	private float prevTransparency;
	private boolean detectedProjectile;
	private int detectedProjectileCooldownTicks;

	@Override
	public boolean canBeHitByProjectile() {
		return super.canBeHitByProjectile() && this.transparency > 0F;
	}

	public void scanForProjectiles() {
		List<Projectile> projectiles = this.level()
			.getEntitiesOfClass(
				Projectile.class, this.getBoundingBox().inflate(2.5D, 2.5D, 2.5D),
				projectile ->
					projectile.getType() != RegisterEntities.APPARITION_PROJECTILE
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
		this.level().getProfiler().push("ghostBrain");
		this.getBrain().tick((ServerLevel) this.level(), this);
		this.level().getProfiler().pop();
		this.level().getProfiler().push("ghostActivityUpdate");
		ApparitionAi.updateActivity(this);
		this.level().getProfiler().pop();
		super.customServerAiStep();
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
}
