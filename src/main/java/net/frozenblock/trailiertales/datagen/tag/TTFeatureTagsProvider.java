/*
 * Copyright 2025-2026 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.trailiertales.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.frozenblock.trailiertales.worldgen.TTFeatureBootstrap;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.FeatureTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public final class TTFeatureTagsProvider extends FabricTagsProvider<ConfiguredFeature<?, ?>> {

	public TTFeatureTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, Registries.CONFIGURED_FEATURE, registries);
	}

	@Override
	public void addTags(HolderLookup.Provider arg) {
		this.builder(FeatureTags.CAN_SPAWN_FROM_BONE_MEAL)
			.add(TTFeatureBootstrap.TORCHFLOWER)
			.add(TTFeatureBootstrap.PITCHER)
			.add(TTFeatureBootstrap.CYAN_ROSE)
			.add(TTFeatureBootstrap.MANEDROP)
			.add(TTFeatureBootstrap.GUZMANIA)
			.add(TTFeatureBootstrap.LITHOPS);
	}
}
