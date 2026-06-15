package net.frozenblock.trailiertales.data.sound;

import net.frozenblock.lib.block.api.sound.SoundTypeOverrides;
import net.frozenblock.lib.block.impl.sound.SoundTypeOverride;
import net.frozenblock.lib.config.v2.entry.ConfigEntry;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.config.TTBlockConfig;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTSounds;
import net.frozenblock.trailiertales.tag.TTBlockTags;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import java.util.Optional;

public final class TTSoundTypeOverrides {

	public static void bootstrap(BootstrapContext<SoundTypeOverride> context) {
		final HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

		register(context, "unpolished_bricks", TTBlockTags.SOUND_UNPOLISHED_BRICKS, TTSounds.BRICKS, TTBlockConfig.UNPOLISHED_BRICKS_SOUNDS);
		register(context, "polished_bricks", TTBlockTags.SOUND_POLISHED_BRICKS, TTSounds.POLISHED_BRICKS, TTBlockConfig.POLISHED_BRICKS_SOUNDS);
		register(context, "polished_calcite", TTBlockTags.SOUND_POLISHED_CALCITE, TTSounds.POLISHED_CALCITE, TTBlockConfig.POLISHED_CALCITE_SOUNDS);
		register(context, "calcite_bricks", TTBlockTags.SOUND_CALCITE_BRICKS, TTSounds.CALCITE_BRICKS_ALT, TTBlockConfig.CALCITE_BRICKS_SOUNDS);
		register(context, "polished", TTBlockTags.SOUND_POLISHED, TTSounds.POLISHED, TTBlockConfig.POLISHED_SOUNDS);
		register(context, "polished_deepslate", TTBlockTags.SOUND_POLISHED_DEEPSLATE, TTSounds.POLISHED_DEEPSLATE, TTBlockConfig.POLISHED_DEEPSLATE_SOUNDS);
		register(context, "polished_tuff", TTBlockTags.SOUND_POLISHED_TUFF, TTSounds.POLISHED_TUFF, TTBlockConfig.POLISHED_TUFF_SOUNDS);
		register(context, "polished_basalt", TTBlockTags.SOUND_POLISHED_BASALT, TTSounds.POLISHED_BASALT, TTBlockConfig.POLISHED_BASALT_SOUNDS);
		register(context, "polished_resin", TTBlockTags.SOUND_POLISHED_RESIN, TTSounds.POLISHED_RESIN, TTBlockConfig.POLISHED_SOUNDS);

		register(context, "suspicious_clay", TTBlockTags.SOUND_SUSPICIOUS_CLAY, TTSounds.SUSPICIOUS_CLAY_WW, WWBlockConfig.CLAY_SOUNDS);
		register(context, "suspicious_gravel", TTBlockTags.SOUND_SUSPICIOUS_GRAVEL, TTSounds.SUSPICIOUS_GRAVEL_WW, WWBlockConfig.GRAVEL_SOUNDS);
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
		SoundTypeOverrides.register(context, key(name), context.lookup(Registries.BLOCK).getOrThrow(tagKey), soundType, configPredicate);
	}

	private static ResourceKey<SoundTypeOverride> key(String name) {
		return SoundTypeOverrides.createKey(TTConstants.id(name));
	}
}
