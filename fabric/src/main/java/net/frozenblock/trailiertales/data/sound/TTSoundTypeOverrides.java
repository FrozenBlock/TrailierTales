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

package net.frozenblock.trailiertales.data.sound;

import java.util.Optional;
import net.frozenblock.lib.block.api.sound.SoundTypeOverrides;
import net.frozenblock.lib.block.impl.sound.SoundTypeOverride;
import net.frozenblock.lib.config.v2.entry.ConfigEntry;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.config.TTBlockConfig;
import net.frozenblock.trailiertales.registry.TTConfigPredicates;
import net.frozenblock.trailiertales.registry.TTSoundTypes;
import net.frozenblock.trailiertales.tag.TTBlockTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public final class TTSoundTypeOverrides {

	public static void bootstrap(BootstrapContext<SoundTypeOverride> context) {
		final HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER);

		register(context, "unpolished_bricks", TTBlockTags.SOUND_UNPOLISHED_BRICKS, TTSoundTypes.BRICKS, TTBlockConfig.UNPOLISHED_BRICKS_SOUNDS);
		register(context, "polished_bricks", TTBlockTags.SOUND_POLISHED_BRICKS, TTSoundTypes.POLISHED_BRICKS, TTBlockConfig.POLISHED_BRICKS_SOUNDS);
		register(context, "polished_calcite", TTBlockTags.SOUND_POLISHED_CALCITE, TTSoundTypes.POLISHED_CALCITE, TTBlockConfig.POLISHED_CALCITE_SOUNDS);
		register(context, "calcite_bricks", TTBlockTags.SOUND_CALCITE_BRICKS, TTSoundTypes.CALCITE_BRICKS_ALT, TTBlockConfig.CALCITE_BRICKS_SOUNDS);
		register(context, "polished", TTBlockTags.SOUND_POLISHED, TTSoundTypes.POLISHED, TTBlockConfig.POLISHED_SOUNDS);
		register(context, "polished_deepslate", TTBlockTags.SOUND_POLISHED_DEEPSLATE, TTSoundTypes.POLISHED_DEEPSLATE, TTBlockConfig.POLISHED_DEEPSLATE_SOUNDS);
		register(context, "polished_tuff", TTBlockTags.SOUND_POLISHED_TUFF, TTSoundTypes.POLISHED_TUFF, TTBlockConfig.POLISHED_TUFF_SOUNDS);
		register(context, "polished_basalt", TTBlockTags.SOUND_POLISHED_BASALT, TTSoundTypes.POLISHED_BASALT, TTBlockConfig.POLISHED_BASALT_SOUNDS);
		register(context, "polished_resin", TTBlockTags.SOUND_POLISHED_RESIN, TTSoundTypes.POLISHED_RESIN, TTBlockConfig.POLISHED_SOUNDS);

		register(
			context,
			"suspicious_clay",
			TTBlockTags.SOUND_SUSPICIOUS_CLAY,
			TTSoundTypes.SUSPICIOUS_CLAY_WW,
			configPredicates.getOrThrow(TTConfigPredicates.WILDER_WILD_CLAY_SOUNDS)
		);

		register(
			context,
			"suspicious_gravel",
			TTBlockTags.SOUND_SUSPICIOUS_GRAVEL,
			TTSoundTypes.SUSPICIOUS_GRAVEL_WW,
			configPredicates.getOrThrow(TTConfigPredicates.WILDER_WILD_GRAVEL_SOUNDS)
		);
	}

	private static void register(
		BootstrapContext<SoundTypeOverride> context,
		String name,
		TagKey<Block> tagKey,
		SoundType soundType
	) {
		SoundTypeOverrides.register(context, key(name), context.lookup(Registries.BLOCK).getOrThrow(tagKey), soundType, Optional.empty());
	}

	private static void register(
		BootstrapContext<SoundTypeOverride> context,
		String name,
		TagKey<Block> tagKey,
		SoundType soundType,
		ConfigEntry<Boolean> configEntry
	) {
		register(context, name, tagKey, soundType, ConfigPredicate.equalTo(configEntry, true));
	}

	private static void register(
		BootstrapContext<SoundTypeOverride> context,
		String name,
		TagKey<Block> tagKey,
		SoundType soundType,
		ConfigPredicate configPredicate
	) {
		register(context, name, tagKey, soundType, configPredicate.asHolder());
	}

	private static void register(
		BootstrapContext<SoundTypeOverride> context,
		String name,
		TagKey<Block> tagKey,
		SoundType soundType,
		Holder<ConfigPredicate> configPredicate
	) {
		SoundTypeOverrides.register(context, key(name), context.lookup(Registries.BLOCK).getOrThrow(tagKey), soundType, configPredicate);
	}

	private static ResourceKey<SoundTypeOverride> key(String name) {
		return SoundTypeOverrides.createKey(TTConstants.id(name));
	}

	private TTSoundTypeOverrides() {}
}
