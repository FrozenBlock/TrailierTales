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

import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.lib.platform.api.registry.DeferredSoundEvent;
import net.frozenblock.trailiertales.TTConstants;

public final class TTSounds {
	private static final DeferredRegister.SoundEvents REGISTER = DeferredRegister.createSoundEvents(TTConstants.MOD_ID);

	public static final DeferredSoundEvent MUSIC_DISC_FAUSSE_VIE = REGISTER.register("music_disc.fausse_vie");
	public static final DeferredSoundEvent MUSIC_DISC_STASIS = REGISTER.register("music_disc.stasis");
	public static final DeferredSoundEvent MUSIC_DISC_OSSUAIRE = REGISTER.register("music_disc.ossuaire");

	public static final DeferredSoundEvent APPLY_EFFECT_SIEGE_OMEN = REGISTER.register("event.mob_effect.siege_omen");
	public static final DeferredSoundEvent DAWNTRAIL_PICK = REGISTER.register("block.dawntrail.pick");

	public static final DeferredSoundEvent CALCITE_BRICKS_BREAK = REGISTER.register("block.calcite_bricks.break");
	public static final DeferredSoundEvent CALCITE_BRICKS_STEP = REGISTER.register("block.calcite_bricks.step");
	public static final DeferredSoundEvent CALCITE_BRICKS_PLACE = REGISTER.register("block.calcite_bricks.place");
	public static final DeferredSoundEvent CALCITE_BRICKS_HIT = REGISTER.register("block.calcite_bricks.hit");
	public static final DeferredSoundEvent CALCITE_BRICKS_FALL = REGISTER.register("block.calcite_bricks.fall");

	public static final DeferredSoundEvent CALCITE_BRICKS_ALT_BREAK = REGISTER.register("block.calcite_bricks_alt.break");
	public static final DeferredSoundEvent CALCITE_BRICKS_ALT_STEP = REGISTER.register("block.calcite_bricks_alt.step");
	public static final DeferredSoundEvent CALCITE_BRICKS_ALT_PLACE = REGISTER.register("block.calcite_bricks_alt.place");
	public static final DeferredSoundEvent CALCITE_BRICKS_ALT_HIT = REGISTER.register("block.calcite_bricks_alt.hit");
	public static final DeferredSoundEvent CALCITE_BRICKS_ALT_FALL = REGISTER.register("block.calcite_bricks_alt.fall");

	public static final DeferredSoundEvent BRICKS_BREAK = REGISTER.register("block.bricks.break");
	public static final DeferredSoundEvent BRICKS_STEP = REGISTER.register("block.bricks.step");
	public static final DeferredSoundEvent BRICKS_PLACE = REGISTER.register("block.bricks.place");
	public static final DeferredSoundEvent BRICKS_HIT = REGISTER.register("block.bricks.hit");
	public static final DeferredSoundEvent BRICKS_FALL = REGISTER.register("block.bricks.fall");

	public static final DeferredSoundEvent POLISHED_BREAK = REGISTER.register("block.polished.break");
	public static final DeferredSoundEvent POLISHED_STEP = REGISTER.register("block.polished.step");
	public static final DeferredSoundEvent POLISHED_PLACE = REGISTER.register("block.polished.place");
	public static final DeferredSoundEvent POLISHED_HIT = REGISTER.register("block.polished.hit");
	public static final DeferredSoundEvent POLISHED_FALL = REGISTER.register("block.polished.fall");

	public static final DeferredSoundEvent POLISHED_BRICKS_BREAK = REGISTER.register("block.polished_bricks.break");
	public static final DeferredSoundEvent POLISHED_BRICKS_STEP = REGISTER.register("block.polished_bricks.step");
	public static final DeferredSoundEvent POLISHED_BRICKS_PLACE = REGISTER.register("block.polished_bricks.place");
	public static final DeferredSoundEvent POLISHED_BRICKS_HIT = REGISTER.register("block.polished_bricks.hit");
	public static final DeferredSoundEvent POLISHED_BRICKS_FALL = REGISTER.register("block.polished_bricks.fall");

	public static final DeferredSoundEvent POLISHED_RESIN_BREAK = REGISTER.register("block.polished_resin.break");
	public static final DeferredSoundEvent POLISHED_RESIN_STEP = REGISTER.register("block.polished_resin.step");
	public static final DeferredSoundEvent POLISHED_RESIN_PLACE = REGISTER.register("block.polished_resin.place");
	public static final DeferredSoundEvent POLISHED_RESIN_HIT = REGISTER.register("block.polished_resin.hit");
	public static final DeferredSoundEvent POLISHED_RESIN_FALL = REGISTER.register("block.polished_resin.fall");

	public static final DeferredSoundEvent POLISHED_CALCITE_BREAK = REGISTER.register("block.polished_calcite.break");
	public static final DeferredSoundEvent POLISHED_CALCITE_STEP = REGISTER.register("block.polished_calcite.step");
	public static final DeferredSoundEvent POLISHED_CALCITE_PLACE = REGISTER.register("block.polished_calcite.place");
	public static final DeferredSoundEvent POLISHED_CALCITE_HIT = REGISTER.register("block.polished_calcite.hit");
	public static final DeferredSoundEvent POLISHED_CALCITE_FALL = REGISTER.register("block.polished_calcite.fall");

	public static final DeferredSoundEvent POLISHED_TUFF_BREAK = REGISTER.register("block.polished_tuff.break");
	public static final DeferredSoundEvent POLISHED_TUFF_STEP = REGISTER.register("block.polished_tuff.step");
	public static final DeferredSoundEvent POLISHED_TUFF_PLACE = REGISTER.register("block.polished_tuff.place");
	public static final DeferredSoundEvent POLISHED_TUFF_HIT = REGISTER.register("block.polished_tuff.hit");
	public static final DeferredSoundEvent POLISHED_TUFF_FALL = REGISTER.register("block.polished_tuff.fall");

	public static final DeferredSoundEvent POLISHED_BASALT_BREAK = REGISTER.register("block.polished_basalt.break");
	public static final DeferredSoundEvent POLISHED_BASALT_STEP = REGISTER.register("block.polished_basalt.step");
	public static final DeferredSoundEvent POLISHED_BASALT_PLACE = REGISTER.register("block.polished_basalt.place");
	public static final DeferredSoundEvent POLISHED_BASALT_HIT = REGISTER.register("block.polished_basalt.hit");
	public static final DeferredSoundEvent POLISHED_BASALT_FALL = REGISTER.register("block.polished_basalt.fall");

	public static final DeferredSoundEvent POLISHED_DEEPSLATE_BREAK = REGISTER.register("block.polished_deepslate.break");
	public static final DeferredSoundEvent POLISHED_DEEPSLATE_STEP = REGISTER.register("block.polished_deepslate.step");
	public static final DeferredSoundEvent POLISHED_DEEPSLATE_PLACE = REGISTER.register("block.polished_deepslate.place");
	public static final DeferredSoundEvent POLISHED_DEEPSLATE_HIT = REGISTER.register("block.polished_deepslate.hit");
	public static final DeferredSoundEvent POLISHED_DEEPSLATE_FALL = REGISTER.register("block.polished_deepslate.fall");

	public static final DeferredSoundEvent COFFIN_BREAK = REGISTER.register("block.coffin.break");
	public static final DeferredSoundEvent COFFIN_STEP = REGISTER.register("block.coffin.step");
	public static final DeferredSoundEvent COFFIN_PLACE = REGISTER.register("block.coffin.place");
	public static final DeferredSoundEvent COFFIN_HIT = REGISTER.register("block.coffin.hit");
	public static final DeferredSoundEvent COFFIN_FALL = REGISTER.register("block.coffin.fall");
	public static final DeferredSoundEvent COFFIN_SPAWN_MOB = REGISTER.register("block.coffin.spawn_mob");
	public static final DeferredSoundEvent COFFIN_VANISH_MOB = REGISTER.register("block.coffin.vanish_mob");
	public static final DeferredSoundEvent COFFIN_WOBBLE = REGISTER.register("block.coffin.wobble");
	public static final DeferredSoundEvent COFFIN_AMBIENT = REGISTER.register("block.coffin.ambient");
	public static final DeferredSoundEvent COFFIN_DETECT_PLAYER = REGISTER.register("block.coffin.detect_player");
	public static final DeferredSoundEvent COFFIN_INCREASE_POWER = REGISTER.register("block.coffin.increase_power");

	public static final DeferredSoundEvent ECTOPLASM_BREAK = REGISTER.register("block.ectoplasm.break");
	public static final DeferredSoundEvent ECTOPLASM_STEP = REGISTER.register("block.ectoplasm.step");
	public static final DeferredSoundEvent ECTOPLASM_PLACE = REGISTER.register("block.ectoplasm.place");
	public static final DeferredSoundEvent ECTOPLASM_HIT = REGISTER.register("block.ectoplasm.hit");
	public static final DeferredSoundEvent ECTOPLASM_FALL = REGISTER.register("block.ectoplasm.fall");

	public static final DeferredSoundEvent APPARITION_IDLE = REGISTER.register("entity.apparition.idle");
	public static final DeferredSoundEvent APPARITION_HURT = REGISTER.register("entity.apparition.hurt");
	public static final DeferredSoundEvent APPARITION_DEATH = REGISTER.register("entity.apparition.death");
	public static final DeferredSoundEvent APPARITION_AID = REGISTER.register("entity.apparition.aid");
	public static final DeferredSoundEvent APPARITION_HOLDING_ITEM = REGISTER.register("entity.apparition.holding_item");
	public static final DeferredSoundEvent APPARITION_HAUNT = REGISTER.register("entity.apparition.haunt");
	public static final DeferredSoundEvent APPARITION_THROW = REGISTER.register("entity.apparition.throw");
	public static final DeferredSoundEvent APPARITION_VANISH = REGISTER.register("entity.apparition.vanish");

	public static final DeferredSoundEvent SUSPICIOUS_DIRT_BREAK = REGISTER.register("block.suspicious_dirt.break");
	public static final DeferredSoundEvent SUSPICIOUS_DIRT_STEP = REGISTER.register("block.suspicious_dirt.step");
	public static final DeferredSoundEvent SUSPICIOUS_DIRT_PLACE = REGISTER.register("block.suspicious_dirt.place");
	public static final DeferredSoundEvent SUSPICIOUS_DIRT_HIT = REGISTER.register("block.suspicious_dirt.hit");
	public static final DeferredSoundEvent SUSPICIOUS_DIRT_FALL = REGISTER.register("block.suspicious_dirt.fall");
	public static final DeferredSoundEvent BRUSH_DIRT = REGISTER.register("item.brush.brushing.dirt");
	public static final DeferredSoundEvent BRUSH_DIRT_COMPLETED = REGISTER.register("item.brush.brushing.dirt.complete");

	public static final DeferredSoundEvent SUSPICIOUS_CLAY_BREAK = REGISTER.register("block.suspicious_clay.break");
	public static final DeferredSoundEvent SUSPICIOUS_CLAY_STEP = REGISTER.register("block.suspicious_clay.step");
	public static final DeferredSoundEvent SUSPICIOUS_CLAY_PLACE = REGISTER.register("block.suspicious_clay.place");
	public static final DeferredSoundEvent SUSPICIOUS_CLAY_HIT = REGISTER.register("block.suspicious_clay.hit");
	public static final DeferredSoundEvent SUSPICIOUS_CLAY_FALL = REGISTER.register("block.suspicious_clay.fall");
	public static final DeferredSoundEvent BRUSH_CLAY = REGISTER.register("item.brush.brushing.clay");
	public static final DeferredSoundEvent BRUSH_CLAY_COMPLETED = REGISTER.register("item.brush.brushing.clay.complete");

	public static final DeferredSoundEvent SUSPICIOUS_CLAY_WW_BREAK = REGISTER.register("block.suspicious_clay_ww.break");
	public static final DeferredSoundEvent SUSPICIOUS_CLAY_WW_STEP = REGISTER.register("block.suspicious_clay_ww.step");
	public static final DeferredSoundEvent SUSPICIOUS_CLAY_WW_PLACE = REGISTER.register("block.suspicious_clay_ww.place");
	public static final DeferredSoundEvent SUSPICIOUS_CLAY_WW_HIT = REGISTER.register("block.suspicious_clay_ww.hit");
	public static final DeferredSoundEvent SUSPICIOUS_CLAY_WW_FALL = REGISTER.register("block.suspicious_clay_ww.fall");
	public static final DeferredSoundEvent BRUSH_CLAY_WW = REGISTER.register("item.brush.brushing.clay_ww");
	public static final DeferredSoundEvent BRUSH_CLAY_WW_COMPLETED = REGISTER.register("item.brush.brushing.clay_ww.complete");

	public static final DeferredSoundEvent SUSPICIOUS_GRAVEL_WW_BREAK = REGISTER.register("block.suspicious_gravel_ww.break");
	public static final DeferredSoundEvent SUSPICIOUS_GRAVEL_WW_STEP = REGISTER.register("block.suspicious_gravel_ww.step");
	public static final DeferredSoundEvent SUSPICIOUS_GRAVEL_WW_PLACE = REGISTER.register("block.suspicious_gravel_ww.place");
	public static final DeferredSoundEvent SUSPICIOUS_GRAVEL_WW_HIT = REGISTER.register("block.suspicious_gravel_ww.hit");
	public static final DeferredSoundEvent SUSPICIOUS_GRAVEL_WW_FALL = REGISTER.register("block.suspicious_gravel_ww.fall");
	public static final DeferredSoundEvent BRUSH_GRAVEL_WW = REGISTER.register("item.brush.brushing.gravel_ww");
	public static final DeferredSoundEvent BRUSH_GRAVEL_WW_COMPLETED = REGISTER.register("item.brush.brushing.gravel_ww.complete");

	static {
		REGISTER.register();
	}

	public static void init() {}

	private TTSounds() {}
}
