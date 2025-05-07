/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.worldgen.structure.api.StructureGenerationConditionApi;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.worldgen.structure.RuinsStructure;
import net.frozenblock.trailiertales.worldgen.structure.datagen.CatacombsGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

public final class TTResources {

	public static boolean HAS_STRONGHOLD_OVERRIDE_PACK = false;

	public static void init(ModContainer container) {
		ResourceManagerHelper.registerBuiltinResourcePack(
			TTConstants.id("stronghold_catacombs"),
			container,
			Component.translatable("pack.trailiertales.strongholds_to_catacombs"),
			ResourcePackActivationType.NORMAL
		);

		ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public ResourceLocation getFabricId() {
				return TTConstants.id("server_resource_listener");
			}

			@Override
			public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
				RuinsStructure.onServerDataReload(resourceManager);
				HAS_STRONGHOLD_OVERRIDE_PACK = resourceManager.listPacks().anyMatch(packResources -> {
					if (packResources.knownPackInfo().isPresent()) {
						return packResources.knownPackInfo().get().id().equals(TTConstants.string("stronghold_catacombs"));
					}
					return false;
				});
				TTConstants.log(HAS_STRONGHOLD_OVERRIDE_PACK ? "Has stronghold override pack!" : "Does not have stronghold override pack!", TTConstants.UNSTABLE_LOGGING);
			}
		});

		StructureGenerationConditionApi.addGenerationCondition(CatacombsGenerator.CATACOMBS_STRUCTURE_SET_KEY.location(), () -> !HAS_STRONGHOLD_OVERRIDE_PACK);
	}

}
