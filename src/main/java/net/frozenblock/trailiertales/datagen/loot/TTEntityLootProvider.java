/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.datagen.loot;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.frozenblock.trailiertales.registry.TTEntityTypes;
import net.frozenblock.trailiertales.registry.TTItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

public class TTEntityLootProvider extends SimpleFabricLootTableProvider {
	private final CompletableFuture<HolderLookup.Provider> registries;

	public TTEntityLootProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, LootContextParamSets.ENTITY);
		this.registries = registries;
	}

	@Override
	public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
		HolderLookup.Provider registryLookup = this.registries.join();

		output.accept(
			TTEntityTypes.APPARITION.getDefaultLootTable().orElseThrow(),
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(TTItems.ECTOPLASM)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(-1F, 1F)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(registryLookup, UniformGenerator.between(0F, 1F)))
						)
				)
		);
	}
}
