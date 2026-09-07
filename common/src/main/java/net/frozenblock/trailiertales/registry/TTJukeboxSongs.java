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

package net.frozenblock.trailiertales.registry;

import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.world.item.JukeboxSong;

public final class TTJukeboxSongs {
	public static final ResourceKey<JukeboxSong> FAUSSE_VIE = createKey("fausse_vie");
	public static final ResourceKey<JukeboxSong> STASIS = createKey("stasis");
	public static final ResourceKey<JukeboxSong> OSSUAIRE = createKey("ossuaire");

	private static ResourceKey<JukeboxSong> createKey(String path) {
		return ResourceKey.create(Registries.JUKEBOX_SONG, TTConstants.id(path));
	}

	private static void register(
		BootstrapContext<JukeboxSong> context,
		ResourceKey<JukeboxSong> registryKey,
		Holder<SoundEvent> soundEvent,
		int lengthInSeconds,
		int comparatorOutput
	) {
		context.register(
			registryKey,
			new JukeboxSong(soundEvent, Component.translatable(Util.makeDescriptionId("jukebox_song", registryKey.identifier())), (float)lengthInSeconds, comparatorOutput)
		);
	}

	public static void bootstrap(BootstrapContext<JukeboxSong> context) {
		register(context, FAUSSE_VIE, TTSounds.MUSIC_DISC_FAUSSE_VIE.asHolder(), 246, 10); // 11 is Creator (Music Box)
		register(context, STASIS, TTSounds.MUSIC_DISC_STASIS.asHolder(), 148, 9);
		register(context, OSSUAIRE, TTSounds.MUSIC_DISC_OSSUAIRE.asHolder(), 176, 8);
	}

	private TTJukeboxSongs() {}
}
