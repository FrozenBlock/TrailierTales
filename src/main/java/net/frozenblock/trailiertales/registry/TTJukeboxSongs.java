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

import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import org.jetbrains.annotations.NotNull;
import net.minecraft.Util;

public final class TTJukeboxSongs {
	public static final ResourceKey<JukeboxSong> FAUSSE_VIE = create("fausse_vie");
	public static final ResourceKey<JukeboxSong> STASIS = create("stasis");
	public static final ResourceKey<JukeboxSong> OSSUAIRE = create("ossuaire");

	public static void init() {
	}

	private static @NotNull ResourceKey<JukeboxSong> create(String path) {
		return ResourceKey.create(Registries.JUKEBOX_SONG, TTConstants.id(path));
	}

	private static void register(
		@NotNull BootstrapContext<JukeboxSong> context,
		ResourceKey<JukeboxSong> registryKey,
		Holder.Reference<SoundEvent> soundEvent,
		int lengthInSeconds,
		int comparatorOutput
	) {
		context.register(
			registryKey,
			new JukeboxSong(soundEvent, Component.translatable(Util.makeDescriptionId("jukebox_song", registryKey.location())), (float)lengthInSeconds, comparatorOutput)
		);
	}

	public static void bootstrap(BootstrapContext<JukeboxSong> context) {
		register(context, FAUSSE_VIE, TTSounds.MUSIC_DISC_FAUSSE_VIE, 246, 10); // 11 is Creator (Music Box)
		register(context, STASIS, TTSounds.MUSIC_DISC_STASIS, 148, 9);
		register(context, OSSUAIRE, TTSounds.MUSIC_DISC_OSSUAIRE, 176, 8);
	}
}
