package net.frozenblock.trailiertales.worldgen.impl;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

public class SuspiciousBlockConfiguration implements FeatureConfiguration {
	public static final Codec<SuspiciousBlockConfiguration> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
				Codec.list(OreConfiguration.TargetBlockState.CODEC).fieldOf("targets").forGetter(config -> config.targetStates),
				Codec.intRange(0, 64).fieldOf("size").forGetter(config -> config.size),
				Codec.floatRange(0F, 1F).fieldOf("discard_chance_on_air_exposure").forGetter(config -> config.discardChanceOnAirExposure),
				Codec.floatRange(0F, 1F).fieldOf("place_chance_per_block").forGetter(config -> config.placeChancePerBlock),
				ResourceLocation.CODEC.listOf().fieldOf("loot_tables").forGetter(config -> config.lootTables)
			)
			.apply(instance, SuspiciousBlockConfiguration::new)
	);
	public final List<OreConfiguration.TargetBlockState> targetStates;
	public final int size;
	public final float discardChanceOnAirExposure;
	public final float placeChancePerBlock;
	public final List<ResourceLocation> lootTables;

	public SuspiciousBlockConfiguration(List<OreConfiguration.TargetBlockState> targetStates, int size, float discardChanceOnAirExposure, float placeChancePerBlock, List<ResourceLocation> lootTables) {
		this.size = size;
		this.targetStates = targetStates;
		this.discardChanceOnAirExposure = discardChanceOnAirExposure;
		this.placeChancePerBlock = placeChancePerBlock;
		this.lootTables = lootTables;
	}

	public SuspiciousBlockConfiguration(RuleTest target, BlockState state, int size, float discardChanceOnAirExposure, float placeChancePerBlock, List<ResourceLocation> lootTables) {
		this(ImmutableList.of(OreConfiguration.target(target, state)), size, discardChanceOnAirExposure, placeChancePerBlock, lootTables);
	}
}
