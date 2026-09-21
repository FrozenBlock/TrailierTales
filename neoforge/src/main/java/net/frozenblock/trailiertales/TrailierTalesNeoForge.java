/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.trailiertales;

import net.frozenblock.lib.networking.api.platform.NetworkingHelperImpl;
import net.frozenblock.lib.platform.ModLoader;
import net.frozenblock.trailiertales.config.TTEntityConfig;
import net.frozenblock.trailiertales.entity.Apparition;
import net.frozenblock.trailiertales.networking.TTClientNetworking;
import net.frozenblock.trailiertales.networking.TTNetworking;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityMobGriefingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(TTPreLoadConstants.MOD_ID)
public final class TrailierTalesNeoForge {

	public TrailierTalesNeoForge(IEventBus modBus) {
		TrailierTales.init(TTConstants.MOD_ID);

		modBus.addListener(RegisterPayloadHandlersEvent.class, event -> {
			TTNetworking.setup();

			if (ModLoader.isClient()) TTClientNetworking.setup();

			final PayloadRegistrar registrar = event.registrar(TTPreLoadConstants.MOD_ID);
			NetworkingHelperImpl.flush(registrar);
		});

		// AFTER register event
		modBus.addListener(FMLCommonSetupEvent.class, event -> {
			TrailierTales.setup();
		});

		NeoForge.EVENT_BUS.addListener(
			EntityMobGriefingEvent.class,
			(event) -> {
				if (event.getEntity() instanceof Apparition && TTEntityConfig.APPARITION_IGNORES_MOB_GRIEFING.get()) event.setCanGrief(true);
			}
		);
	}
}
