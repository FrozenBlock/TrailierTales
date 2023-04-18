package net.lunade.onetwenty;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.lunade.onetwenty.data.Luna120BiomeTags;
import net.lunade.onetwenty.util.Luna120SharedConstants;
import net.lunade.onetwenty.worldgen.Luna120FeatureBootstrap;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.Nullable;

public class Luna120 implements ModInitializer {

	@Override
	public void onInitialize() {
		Luna120SharedConstants.startMeasuring(this);

		/*
		BiomeModifications.addFeature(BiomeSelectors.tag(Luna120BiomeTags.HAS_TORCHFLOWER),
				GenerationStep.Decoration.VEGETAL_DECORATION, Luna120FeatureBootstrap.TORCHFLOWER_PLACED);

		BiomeModifications.addSpawn(BiomeSelectors.tag(Luna120BiomeTags.HAS_SNIFFER),
				MobCategory.CREATURE, EntityType.SNIFFER, 2, 1, 1);

		SpawnPlacements.register(EntityType.SNIFFER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
		 */

		Luna120SharedConstants.stopMeasuring(this);
	}

	@SuppressWarnings("unchecked")
	@Nullable
	public static <T> T getRandomEntry(RandomSource random, TagKey<T> tag) {
		Optional<? extends Registry<?>> maybeRegistry = BuiltInRegistries.REGISTRY.getOptional(tag.registry().location());
		Objects.requireNonNull(random);
		Objects.requireNonNull(tag);

		if (maybeRegistry.isPresent()) {
			Registry<T> registry = (Registry<T>) maybeRegistry.get();
			if (tag.isFor(registry.key())) {
				ArrayList<T> entries = new ArrayList<>();
				for (Holder<T> entry : registry.getTagOrEmpty(tag)) {
					var optionalKey = entry.unwrapKey();
					if (optionalKey.isPresent()) {
						var key = optionalKey.get();
						registry.getOptional(key).ifPresent(entries::add);
					}
				}
				if (!entries.isEmpty()) {
					return entries.get(random.nextInt(entries.size()));
				}
			}
		}
		return null;
	}
}
