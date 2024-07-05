package net.frozenblock.trailiertales.entity.ai.apparition.impl;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.RegisterEntities;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityPossessionData {
	private final TargetingConditions targetingConditions = TargetingConditions.DEFAULT.copy()
		.range(64D)
				.ignoreLineOfSight()
				.ignoreInvisibilityTesting()
				.selector(this::canTargetEntity);

	private final Mob mob;
	private Optional<Possessor> optionalPossessor = Optional.empty();

	public EntityPossessionData(Mob mob) {
		this.mob = mob;
	}

	public void tick(Mob mob, @NotNull Level level) {
		if (level.isClientSide()) {
			return;
		}
		if (this.optionalPossessor.isPresent()) {
			Possessor possessor = this.optionalPossessor.get();
			List<Player> players = level.getNearbyPlayers(
				this.targetingConditions,
				mob,
				this.mob.getBoundingBox().inflate(64D)
			);
			players.sort(Comparator.comparingDouble(mob::distanceToSqr));

			Optional<Player> optional = players.stream()
				.filter(itemEntity -> itemEntity.closerThan(mob, 64D))
				.findFirst();

			optional.ifPresent(this.mob::setTarget);
			possessor.ticksPossessing += 1;
			if (possessor.ticksPossessing++ > possessor.minTicksPossessing) {
				this.unpossess(level, mob);
			}
		}
	}

	public boolean canTargetEntity(@Nullable Entity entity) {
		return entity instanceof LivingEntity livingEntity
			&& this.mob.level() == livingEntity.level()
			&& !this.mob.level().getDifficulty().equals(Difficulty.PEACEFUL)
			&& EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingEntity)
			&& !this.mob.isAlliedTo(livingEntity)
			&& livingEntity.getType() != EntityType.ARMOR_STAND
			&& livingEntity.getType() != RegisterEntities.APPARITION
			&& !livingEntity.isInvulnerable()
			&& !livingEntity.isDeadOrDying()
			&& !livingEntity.isRemoved()
			&& this.mob.level().getWorldBorder().isWithinBounds(livingEntity.getBoundingBox());
	}

	public void onDeath(LivingEntity entity) {
		this.unpossess(entity.level(), entity);
	}

	public void setPossessor(Apparition possessor) {
		if (possessor == null) {
			optionalPossessor = Optional.empty();
		} else {
			this.optionalPossessor = Optional.of(Possessor.of(possessor));
		}
	}

	private boolean unpossess(Level level, LivingEntity host) {
		if (this.optionalPossessor.isPresent()) {
			Entity entity = this.optionalPossessor.get().createEntity(level);
			this.setPossessor(null);
			if (entity != null) {
				entity.moveTo(host.getX(), host.getEyeY(), host.getZ(), host.getYRot(), host.getXRot());
				level.playSound(null, entity.blockPosition(), SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1F, 1F);
				AttributeInstance followRange = host.getAttribute(Attributes.FOLLOW_RANGE);
				if (followRange != null) {
					followRange.removeModifier(Apparition.ATTRIBUTE_APPARITION_FOLLOW_RANGE);
				}
				return level.addFreshEntity(entity);
			} else {
				return false;
			}
		}
		return true;
	}

	public boolean isPossessed() {
		return this.optionalPossessor.isPresent();
	}

	public void saveCompoundTag(@NotNull CompoundTag tag) {
		CompoundTag possessionDataTag = new CompoundTag();
		if (this.optionalPossessor.isPresent()) {
			RegistryOps<Tag> registryOps = this.mob.registryAccess().createSerializationContext(NbtOps.INSTANCE);
			Possessor.CODEC
				.encodeStart(registryOps, this.optionalPossessor.get())
				.resultOrPartial()
				.ifPresent(possessor -> possessionDataTag.put("Possessor", possessor));
		}
		tag.put("TrailierTales_PossessionData", possessionDataTag);
	}

	public void loadCompoundTag(@NotNull CompoundTag tag) {
		if (tag.contains("TrailierTales_PossessionData", 10)) {
			CompoundTag possessionDataTag = tag.getCompound("TrailierTales_PossessionData");
			RegistryOps<Tag> registryOps = this.mob.registryAccess().createSerializationContext(NbtOps.INSTANCE);
			if (possessionDataTag.contains("Possessor", 10)) {
				Possessor.CODEC
					.parse(registryOps, possessionDataTag.getCompound("Possessor"))
					.resultOrPartial()
					.ifPresent(possessor -> this.optionalPossessor = Optional.of(possessor));
			}
		}
	}

	public static class Possessor {
		static final List<String> IGNORED_APPARITION_TAGS = Arrays.asList(
			"Air",
			"ArmorDropChances",
			"ArmorItems",
			"Brain",
			"CanPickUpLoot",
			"DeathTime",
			"FallDistance",
			"FallFlying",
			"Fire",
			"HandDropChances",
			"HandItems",
			"HurtByTimestamp",
			"HurtTime",
			"LeftHanded",
			"Motion",
			"NoGravity",
			"OnGround",
			"PortalCooldown",
			"Pos",
			"Rotation",
			"SleepingX",
			"SleepingY",
			"SleepingZ",
			"Passengers",
			"leash",
			"UUID",
			"TargetItemXRotScale",
			"TargetItemYRotScale",
			"TargetItemZRotScale",
			"ItemXRotScale",
			"ItemYRotScale",
			"ItemZRotScale",
			"Transparency",
			"ShootProgress"
		);

		protected final CustomData entityData;
		protected int ticksPossessing;
		protected int minTicksPossessing;

		public static final Codec<Possessor> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					CustomData.CODEC.optionalFieldOf("entity_data", CustomData.EMPTY).forGetter(Possessor::entityData),
					Codec.INT.fieldOf("ticks_possessing").forGetter(Possessor::ticksPossessing),
					Codec.INT.fieldOf("min_ticks_possessing").forGetter(Possessor::minTicksPossessing)
				)
				.apply(instance, Possessor::new)
		);

		public Possessor(CustomData entityData, int ticksPossessing, int i) {
			this.entityData = entityData;
			this.ticksPossessing = ticksPossessing;
			this.minTicksPossessing = i;
		}

		@Contract("_ -> new")
		public static @NotNull Possessor of(@NotNull Apparition entity) {
			CompoundTag compoundTag = new CompoundTag();
			entity.save(compoundTag);
			IGNORED_APPARITION_TAGS.forEach(compoundTag::remove);
			return new Possessor(CustomData.of(compoundTag), 0, 400);
		}

		@Contract("_ -> new")
		public static @NotNull Possessor create(int ticksPossessing) {
			CompoundTag compoundTag = new CompoundTag();
			compoundTag.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(RegisterEntities.APPARITION).toString());
			return new Possessor(CustomData.of(compoundTag), ticksPossessing, 400);
		}

		@Nullable
		public Entity createEntity(Level world) {
			CompoundTag compoundTag = this.entityData.copyTag();
			IGNORED_APPARITION_TAGS.forEach(compoundTag::remove);
			return EntityType.loadEntityRecursive(compoundTag, world, entityx -> entityx);
		}

		public CustomData entityData() {
			return this.entityData;
		}

		public int ticksPossessing() {
			return this.ticksPossessing;
		}

		public int minTicksPossessing() {
			return this.minTicksPossessing;
		}
	}
}
