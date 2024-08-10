package net.frozenblock.trailiertales.effect;

import com.google.common.annotations.VisibleForTesting;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.registry.RegisterEntities;
import net.frozenblock.trailiertales.registry.RegisterParticles;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class TransfiguringMobEffect extends MobEffect {

	public TransfiguringMobEffect(MobEffectCategory type, int color) {
		super(type, color, RegisterParticles.COFFIN_SOUL);
	}

	@VisibleForTesting
	protected static int numberOfApparitionsToSpawn(int maxEntityCramming, NearbyApparitions counter) {
		return maxEntityCramming < 1 ? 1 : Mth.clamp(0, maxEntityCramming - counter.count(maxEntityCramming), 1);
	}

	@Override
	public void onMobRemoved(LivingEntity entity, int amplifier, Entity.RemovalReason reason) {
		if (reason == Entity.RemovalReason.KILLED) {
			Level level = entity.level();
			int j = level.getGameRules().getInt(GameRules.RULE_MAX_ENTITY_CRAMMING);
			int k = numberOfApparitionsToSpawn(j, NearbyApparitions.closeTo(entity));

			for (int l = 0; l < k; l++) {
				this.spawnApparitionOffspring(entity.level(), entity.getX(), entity.getY() + 0.5D, entity.getZ());
			}
		}
	}

	private void spawnApparitionOffspring(Level world, double x, double y, double z) {
		Apparition apparition = RegisterEntities.APPARITION.create(world);
		if (apparition != null) {
			apparition.moveTo(x, y, z, world.getRandom().nextFloat() * 360F, 0F);
			world.addFreshEntity(apparition);
		}
	}

	@FunctionalInterface
	protected interface NearbyApparitions {
		int count(int i);

		@Contract(pure = true)
		static @NotNull NearbyApparitions closeTo(LivingEntity entity) {
			return i -> {
				List<Apparition> list = new ArrayList<>();
				entity.level().getEntities(RegisterEntities.APPARITION, entity.getBoundingBox().inflate(3D), apparition -> apparition != entity, list, i);
				return list.size();
			};
		}
	}
}
