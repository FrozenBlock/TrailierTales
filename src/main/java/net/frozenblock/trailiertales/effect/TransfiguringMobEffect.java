package net.frozenblock.trailiertales.effect;

import com.google.common.annotations.VisibleForTesting;
import java.util.ArrayList;
import java.util.List;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.entity.ai.apparition.ApparitionAi;
import net.frozenblock.trailiertales.registry.TTEntityTypes;
import net.frozenblock.trailiertales.registry.TTParticleTypes;
import net.frozenblock.trailiertales.registry.TTSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameRules;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TransfiguringMobEffect extends MobEffect {
	public static final EntityType<Apparition> SPAWNED_ENTITY_TYPE = TTEntityTypes.APPARITION;

	public TransfiguringMobEffect(MobEffectCategory type, int color) {
		super(type, color, TTParticleTypes.TRANSFIGURING);
	}

	@VisibleForTesting
	protected static int numberOfApparitionsToSpawn(int maxEntityCramming, NearbyApparitions counter) {
		return maxEntityCramming < 1 ? 1 : Mth.clamp(0, maxEntityCramming - counter.count(maxEntityCramming), 1);
	}

	@Override
	public void onEffectAdded(LivingEntity entity, int amplifier) {
		super.onEffectAdded(entity, amplifier);
		if (entity instanceof Apparition apparition) {
			entity.level().broadcastEntityEvent(entity, EntityEvent.POOF);
			entity.level().playSound(
				null,
				entity.getX(),
				entity.getEyeY(),
				entity.getZ(),
				TTSounds.APPARITION_VANISH,
				SoundSource.HOSTILE,
				0.6F,
				0.9F + (entity.level().random.nextFloat() * 0.2F)
			);
			apparition.dropItem();
			entity.remove(Entity.RemovalReason.DISCARDED);
		}
	}

	@Override
	public void onMobRemoved(ServerLevel level, LivingEntity entity, int amplifier, Entity.RemovalReason reason) {
		if (reason == Entity.RemovalReason.KILLED && entity.getType() != SPAWNED_ENTITY_TYPE) {
			int j = level.getGameRules().getInt(GameRules.RULE_MAX_ENTITY_CRAMMING);
			int k = numberOfApparitionsToSpawn(j, NearbyApparitions.closeTo(entity));

			for (int l = 0; l < k; l++) {
				this.spawnApparitionOffspring(level, entity.getX(), entity.getY() + 0.5D, entity.getZ());
			}
		}
	}

	private void spawnApparitionOffspring(ServerLevel level, double x, double y, double z) {
		Apparition apparition = SPAWNED_ENTITY_TYPE.create(level, EntitySpawnReason.TRIAL_SPAWNER);
		if (apparition != null) {
			apparition.moveTo(x, y, z, level.getRandom().nextFloat() * 360F, 0F);
			ApparitionAi.rememberHome(apparition, level, BlockPos.containing(x, y, z));
			level.addFreshEntity(apparition);
		}
	}

	@FunctionalInterface
	protected interface NearbyApparitions {
		int count(int i);

		@Contract(pure = true)
		static @NotNull NearbyApparitions closeTo(LivingEntity entity) {
			return i -> {
				List<Apparition> list = new ArrayList<>();
				entity.level().getEntities(TTEntityTypes.APPARITION, entity.getBoundingBox().inflate(3D), apparition -> apparition != entity, list, i);
				return list.size();
			};
		}
	}
}
