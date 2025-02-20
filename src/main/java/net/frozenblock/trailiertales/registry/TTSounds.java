package net.frozenblock.trailiertales.registry;

import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import org.jetbrains.annotations.NotNull;

public class TTSounds {
	public static final Holder.Reference<SoundEvent> MUSIC_DISC_FAUSSE_VIE = registerForHolder("music_disc.fausse_vie");
	public static final Holder.Reference<SoundEvent> MUSIC_DISC_STASIS = registerForHolder("music_disc.stasis");
	public static final Holder.Reference<SoundEvent> MUSIC_DISC_OSSUAIRE = registerForHolder("music_disc.ossuaire");

	public static final SoundEvent APPLY_EFFECT_SIEGE_OMEN = register("event.mob_effect.siege_omen");
	public static final SoundEvent DAWNTRAIL_PICK = register("block.dawntrail.pick");

	public static final SoundEvent CALCITE_BRICKS_BREAK = register("block.calcite_bricks.break");
	public static final SoundEvent CALCITE_BRICKS_STEP = register("block.calcite_bricks.step");
	public static final SoundEvent CALCITE_BRICKS_PLACE = register("block.calcite_bricks.place");
	public static final SoundEvent CALCITE_BRICKS_HIT = register("block.calcite_bricks.hit");
	public static final SoundEvent CALCITE_BRICKS_FALL = register("block.calcite_bricks.fall");
	public static final SoundType CALCITE_BRICKS = new SoundType(
		1F,
		1F,
		CALCITE_BRICKS_BREAK,
		CALCITE_BRICKS_STEP,
		CALCITE_BRICKS_PLACE,
		CALCITE_BRICKS_HIT,
		CALCITE_BRICKS_FALL
	);

	public static final SoundEvent CALCITE_BRICKS_ALT_BREAK = register("block.calcite_bricks_alt.break");
	public static final SoundEvent CALCITE_BRICKS_ALT_STEP = register("block.calcite_bricks_alt.step");
	public static final SoundEvent CALCITE_BRICKS_ALT_PLACE = register("block.calcite_bricks_alt.place");
	public static final SoundEvent CALCITE_BRICKS_ALT_HIT = register("block.calcite_bricks_alt.hit");
	public static final SoundEvent CALCITE_BRICKS_ALT_FALL = register("block.calcite_bricks_alt.fall");
	public static final SoundType CALCITE_BRICKS_ALT = new SoundType(
		1F,
		1F,
		CALCITE_BRICKS_ALT_BREAK,
		CALCITE_BRICKS_ALT_STEP,
		CALCITE_BRICKS_ALT_PLACE,
		CALCITE_BRICKS_ALT_HIT,
		CALCITE_BRICKS_ALT_FALL
	);

	public static final SoundEvent BRICKS_BREAK = register("block.bricks.break");
	public static final SoundEvent BRICKS_STEP = register("block.bricks.step");
	public static final SoundEvent BRICKS_PLACE = register("block.bricks.place");
	public static final SoundEvent BRICKS_HIT = register("block.bricks.hit");
	public static final SoundEvent BRICKS_FALL = register("block.bricks.fall");
	public static final SoundType BRICKS = new SoundType(
		1F,
		1.05F,
		BRICKS_BREAK,
		BRICKS_STEP,
		BRICKS_PLACE,
		BRICKS_HIT,
		BRICKS_FALL
	);

	public static final SoundEvent POLISHED_BREAK = register("block.polished.break");
	public static final SoundEvent POLISHED_STEP = register("block.polished.step");
	public static final SoundEvent POLISHED_PLACE = register("block.polished.place");
	public static final SoundEvent POLISHED_HIT = register("block.polished.hit");
	public static final SoundEvent POLISHED_FALL = register("block.polished.fall");
	public static final SoundType POLISHED = new SoundType(
		1F,
		1F,
		POLISHED_BREAK,
		POLISHED_STEP,
		POLISHED_PLACE,
		POLISHED_HIT,
		POLISHED_FALL
	);

	public static final SoundEvent POLISHED_BRICKS_BREAK = register("block.polished_bricks.break");
	public static final SoundEvent POLISHED_BRICKS_STEP = register("block.polished_bricks.step");
	public static final SoundEvent POLISHED_BRICKS_PLACE = register("block.polished_bricks.place");
	public static final SoundEvent POLISHED_BRICKS_HIT = register("block.polished_bricks.hit");
	public static final SoundEvent POLISHED_BRICKS_FALL = register("block.polished_bricks.fall");
	public static final SoundType POLISHED_BRICKS = new SoundType(
		1F,
		1F,
		POLISHED_BRICKS_BREAK,
		POLISHED_BRICKS_STEP,
		POLISHED_BRICKS_PLACE,
		POLISHED_BRICKS_HIT,
		POLISHED_BRICKS_FALL
	);

	public static final SoundEvent POLISHED_CALCITE_BREAK = register("block.polished_calcite.break");
	public static final SoundEvent POLISHED_CALCITE_STEP = register("block.polished_calcite.step");
	public static final SoundEvent POLISHED_CALCITE_PLACE = register("block.polished_calcite.place");
	public static final SoundEvent POLISHED_CALCITE_HIT = register("block.polished_calcite.hit");
	public static final SoundEvent POLISHED_CALCITE_FALL = register("block.polished_calcite.fall");
	public static final SoundType POLISHED_CALCITE = new SoundType(
		1F,
		1F,
		POLISHED_CALCITE_BREAK,
		POLISHED_CALCITE_STEP,
		POLISHED_CALCITE_PLACE,
		POLISHED_CALCITE_HIT,
		POLISHED_CALCITE_FALL
	);

	public static final SoundEvent POLISHED_TUFF_BREAK = register("block.polished_tuff.break");
	public static final SoundEvent POLISHED_TUFF_STEP = register("block.polished_tuff.step");
	public static final SoundEvent POLISHED_TUFF_PLACE = register("block.polished_tuff.place");
	public static final SoundEvent POLISHED_TUFF_HIT = register("block.polished_tuff.hit");
	public static final SoundEvent POLISHED_TUFF_FALL = register("block.polished_tuff.fall");
	public static final SoundType POLISHED_TUFF = new SoundType(
		1F,
		1F,
		POLISHED_TUFF_BREAK,
		POLISHED_TUFF_STEP,
		POLISHED_TUFF_PLACE,
		POLISHED_TUFF_HIT,
		POLISHED_TUFF_FALL
	);

	public static final SoundEvent POLISHED_BASALT_BREAK = register("block.polished_basalt.break");
	public static final SoundEvent POLISHED_BASALT_STEP = register("block.polished_basalt.step");
	public static final SoundEvent POLISHED_BASALT_PLACE = register("block.polished_basalt.place");
	public static final SoundEvent POLISHED_BASALT_HIT = register("block.polished_basalt.hit");
	public static final SoundEvent POLISHED_BASALT_FALL = register("block.polished_basalt.fall");
	public static final SoundType POLISHED_BASALT = new SoundType(
		1F,
		1F,
		POLISHED_BASALT_BREAK,
		POLISHED_BASALT_STEP,
		POLISHED_BASALT_PLACE,
		POLISHED_BASALT_HIT,
		POLISHED_BASALT_FALL
	);

	public static final SoundEvent POLISHED_DEEPSLATE_BREAK = register("block.polished_deepslate.break");
	public static final SoundEvent POLISHED_DEEPSLATE_STEP = register("block.polished_deepslate.step");
	public static final SoundEvent POLISHED_DEEPSLATE_PLACE = register("block.polished_deepslate.place");
	public static final SoundEvent POLISHED_DEEPSLATE_HIT = register("block.polished_deepslate.hit");
	public static final SoundEvent POLISHED_DEEPSLATE_FALL = register("block.polished_deepslate.fall");
	public static final SoundType POLISHED_DEEPSLATE = new SoundType(
		1F,
		1F,
		POLISHED_DEEPSLATE_BREAK,
		POLISHED_DEEPSLATE_STEP,
		POLISHED_DEEPSLATE_PLACE,
		POLISHED_DEEPSLATE_HIT,
		POLISHED_DEEPSLATE_FALL
	);

	public static final SoundEvent COFFIN_BREAK = register("block.coffin.break");
	public static final SoundEvent COFFIN_STEP = register("block.coffin.step");
	public static final SoundEvent COFFIN_PLACE = register("block.coffin.place");
	public static final SoundEvent COFFIN_HIT = register("block.coffin.hit");
	public static final SoundEvent COFFIN_FALL = register("block.coffin.fall");
	public static final SoundEvent COFFIN_SPAWN_MOB = register("block.coffin.spawn_mob");
	public static final SoundEvent COFFIN_VANISH_MOB = register("block.coffin.vanish_mob");
	public static final SoundEvent COFFIN_WOBBLE = register("block.coffin.wobble");
	public static final SoundEvent COFFIN_HAUNT = register("block.coffin.haunt");
	public static final SoundEvent COFFIN_AMBIENT = register("block.coffin.ambient");
	public static final SoundEvent COFFIN_DETECT_PLAYER = register("block.coffin.detect_player");
	public static final SoundEvent COFFIN_INCREASE_POWER = register("block.coffin.increase_power");
	public static final SoundType COFFIN = new SoundType(
		1F,
		1F,
		COFFIN_BREAK,
		COFFIN_STEP,
		COFFIN_PLACE,
		COFFIN_HIT,
		COFFIN_FALL
	);

	public static final SoundEvent ECTOPLASM_BREAK = register("block.ectoplasm.break");
	public static final SoundEvent ECTOPLASM_STEP = register("block.ectoplasm.step");
	public static final SoundEvent ECTOPLASM_PLACE = register("block.ectoplasm.place");
	public static final SoundEvent ECTOPLASM_HIT = register("block.ectoplasm.hit");
	public static final SoundEvent ECTOPLASM_FALL = register("block.ectoplasm.fall");
	public static final SoundType ECTOPLASM = new SoundType(
		0.075F,
		1.3F,
		ECTOPLASM_BREAK,
		ECTOPLASM_STEP,
		ECTOPLASM_PLACE,
		ECTOPLASM_HIT,
		ECTOPLASM_FALL
	);

	public static final SoundEvent APPARITION_IDLE = register("entity.apparition.idle");
	public static final SoundEvent APPARITION_HURT = register("entity.apparition.hurt");
	public static final SoundEvent APPARITION_DEATH = register("entity.apparition.death");
	public static final SoundEvent APPARITION_AID = register("entity.apparition.aid");
	public static final SoundEvent APPARITION_HOLDING_ITEM = register("entity.apparition.holding_item");
	public static final SoundEvent APPARITION_HAUNT = register("entity.apparition.haunt");
	public static final SoundEvent APPARITION_THROW = register("entity.apparition.throw");
	public static final SoundEvent APPARITION_VANISH = register("entity.apparition.vanish");

	public static final SoundEvent SUSPICIOUS_DIRT_BREAK = register("block.suspicious_dirt.break");
	public static final SoundEvent SUSPICIOUS_DIRT_STEP = register("block.suspicious_dirt.step");
	public static final SoundEvent SUSPICIOUS_DIRT_PLACE = register("block.suspicious_dirt.place");
	public static final SoundEvent SUSPICIOUS_DIRT_HIT = register("block.suspicious_dirt.hit");
	public static final SoundEvent SUSPICIOUS_DIRT_FALL = register("block.suspicious_dirt.fall");
	public static final SoundEvent BRUSH_DIRT = register("item.brush.brushing.dirt");
	public static final SoundEvent BRUSH_DIRT_COMPLETED = register("item.brush.brushing.dirt.complete");
	public static final SoundType SUSPICIOUS_DIRT = new SoundType(
		1F,
		1F,
		SUSPICIOUS_DIRT_BREAK,
		SUSPICIOUS_DIRT_STEP,
		SUSPICIOUS_DIRT_PLACE,
		SUSPICIOUS_DIRT_HIT,
		SUSPICIOUS_DIRT_FALL
	);

	public static final SoundEvent SUSPICIOUS_CLAY_BREAK = register("block.suspicious_clay.break");
	public static final SoundEvent SUSPICIOUS_CLAY_STEP = register("block.suspicious_clay.step");
	public static final SoundEvent SUSPICIOUS_CLAY_PLACE = register("block.suspicious_clay.place");
	public static final SoundEvent SUSPICIOUS_CLAY_HIT = register("block.suspicious_clay.hit");
	public static final SoundEvent SUSPICIOUS_CLAY_FALL = register("block.suspicious_clay.fall");
	public static final SoundEvent BRUSH_CLAY = register("item.brush.brushing.clay");
	public static final SoundEvent BRUSH_CLAY_COMPLETED = register("item.brush.brushing.clay.complete");
	public static final SoundType SUSPICIOUS_CLAY = new SoundType(
		1F,
		1F,
		SUSPICIOUS_CLAY_BREAK,
		SUSPICIOUS_CLAY_STEP,
		SUSPICIOUS_CLAY_PLACE,
		SUSPICIOUS_CLAY_HIT,
		SUSPICIOUS_CLAY_FALL
	);

	public static final SoundEvent SUSPICIOUS_CLAY_WW_BREAK = register("block.suspicious_clay_ww.break");
	public static final SoundEvent SUSPICIOUS_CLAY_WW_STEP = register("block.suspicious_clay_ww.step");
	public static final SoundEvent SUSPICIOUS_CLAY_WW_PLACE = register("block.suspicious_clay_ww.place");
	public static final SoundEvent SUSPICIOUS_CLAY_WW_HIT = register("block.suspicious_clay_ww.hit");
	public static final SoundEvent SUSPICIOUS_CLAY_WW_FALL = register("block.suspicious_clay_ww.fall");
	public static final SoundEvent BRUSH_CLAY_WW = register("item.brush.brushing.clay_ww");
	public static final SoundEvent BRUSH_CLAY_WW_COMPLETED = register("item.brush.brushing.clay_ww.complete");
	public static final SoundType SUSPICIOUS_CLAY_WW = new SoundType(
		0.9F,
		1F,
		SUSPICIOUS_CLAY_WW_BREAK,
		SUSPICIOUS_CLAY_WW_STEP,
		SUSPICIOUS_CLAY_WW_PLACE,
		SUSPICIOUS_CLAY_WW_HIT,
		SUSPICIOUS_CLAY_WW_FALL
	);

	public static final SoundEvent SUSPICIOUS_GRAVEL_WW_BREAK = register("block.suspicious_gravel_ww.break");
	public static final SoundEvent SUSPICIOUS_GRAVEL_WW_STEP = register("block.suspicious_gravel_ww.step");
	public static final SoundEvent SUSPICIOUS_GRAVEL_WW_PLACE = register("block.suspicious_gravel_ww.place");
	public static final SoundEvent SUSPICIOUS_GRAVEL_WW_HIT = register("block.suspicious_gravel_ww.hit");
	public static final SoundEvent SUSPICIOUS_GRAVEL_WW_FALL = register("block.suspicious_gravel_ww.fall");
	public static final SoundEvent BRUSH_GRAVEL_WW = register("item.brush.brushing.gravel_ww");
	public static final SoundEvent BRUSH_GRAVEL_WW_COMPLETED = register("item.brush.brushing.gravel_ww.complete");
	public static final SoundType SUSPICIOUS_GRAVEL_WW = new SoundType(
		0.8F,
		1F,
		SUSPICIOUS_GRAVEL_WW_BREAK,
		SUSPICIOUS_GRAVEL_WW_STEP,
		SUSPICIOUS_GRAVEL_WW_PLACE,
		SUSPICIOUS_GRAVEL_WW_HIT,
		SUSPICIOUS_GRAVEL_WW_FALL
	);

	@NotNull
	private static SoundEvent register(@NotNull String string) {
		ResourceLocation resourceLocation = TTConstants.id(string);
		return Registry.register(BuiltInRegistries.SOUND_EVENT, resourceLocation, SoundEvent.createVariableRangeEvent(resourceLocation));
	}

	private static Holder.@NotNull Reference<SoundEvent> registerForHolder(String id) {
		return registerForHolder(TTConstants.id(id));
	}

	private static Holder.@NotNull Reference<SoundEvent> registerForHolder(ResourceLocation id) {
		return registerForHolder(id, id);
	}

	private static Holder.@NotNull Reference<SoundEvent> registerForHolder(ResourceLocation id, ResourceLocation soundId) {
		return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(soundId));
	}

	public static void init() {}
}
