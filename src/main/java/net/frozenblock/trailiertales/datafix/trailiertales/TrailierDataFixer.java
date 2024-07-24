package net.frozenblock.trailiertales.datafix.trailiertales;

import com.mojang.datafixers.schemas.Schema;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.trailiertales.TrailierConstants;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixerBuilder;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixes;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.SimpleFixes;

public class TrailierDataFixer {
	public static final int DATA_VERSION = 1;

	private TrailierDataFixer() {
		throw new UnsupportedOperationException("TrailierDataFixer contains only static declarations.");
	}

	public static void applyDataFixes(final @NotNull ModContainer mod) {
		var builder = new QuiltDataFixerBuilder(DATA_VERSION);
		builder.addSchema(0, QuiltDataFixes.BASE_SCHEMA);

		Schema schemaV1 = builder.addSchema(1, NamespacedSchema::new);

		// GRANITE
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename chiseled_polished_granite to chiseled_granite_bricks",
			TrailierConstants.id("chiseled_polished_granite"),
			TrailierConstants.id("chiseled_granite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_granite_bricks to granite_bricks",
			TrailierConstants.id("polished_granite_bricks"),
			TrailierConstants.id("granite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename cracked_polished_granite_bricks to cracked_granite_bricks",
			TrailierConstants.id("cracked_polished_granite_bricks"),
			TrailierConstants.id("cracked_granite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_granite_brick_stairs to granite_brick_stairs",
			TrailierConstants.id("polished_granite_brick_stairs"),
			TrailierConstants.id("granite_brick_stairs"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_granite_brick_slab to granite_brick_slab",
			TrailierConstants.id("polished_granite_brick_slab"),
			TrailierConstants.id("granite_brick_slab"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_granite_brick_wall to granite_brick_wall",
			TrailierConstants.id("polished_granite_brick_wall"),
			TrailierConstants.id("granite_brick_wall"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_granite_bricks to mossy_granite_bricks",
			TrailierConstants.id("mossy_polished_granite_bricks"),
			TrailierConstants.id("mossy_granite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_granite_brick_stairs to mossy_granite_brick_stairs",
			TrailierConstants.id("mossy_polished_granite_brick_stairs"),
			TrailierConstants.id("mossy_granite_brick_stairs"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_granite_brick_slab to mossy_granite_brick_slab",
			TrailierConstants.id("mossy_polished_granite_brick_slab"),
			TrailierConstants.id("mossy_granite_brick_slab"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_granite_brick_wall to mossy_granite_brick_wall",
			TrailierConstants.id("mossy_polished_granite_brick_wall"),
			TrailierConstants.id("mossy_granite_brick_wall"),
			schemaV1
		);

		// DIORITE
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename chiseled_polished_diorite to chiseled_diorite_bricks",
			TrailierConstants.id("chiseled_polished_diorite"),
			TrailierConstants.id("chiseled_diorite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_diorite_bricks to diorite_bricks",
			TrailierConstants.id("polished_diorite_bricks"),
			TrailierConstants.id("diorite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename cracked_polished_diorite_bricks to cracked_diorite_bricks",
			TrailierConstants.id("cracked_polished_diorite_bricks"),
			TrailierConstants.id("cracked_diorite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_diorite_brick_stairs to diorite_brick_stairs",
			TrailierConstants.id("polished_diorite_brick_stairs"),
			TrailierConstants.id("diorite_brick_stairs"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_diorite_brick_slab to diorite_brick_slab",
			TrailierConstants.id("polished_diorite_brick_slab"),
			TrailierConstants.id("diorite_brick_slab"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_diorite_brick_wall to diorite_brick_wall",
			TrailierConstants.id("polished_diorite_brick_wall"),
			TrailierConstants.id("diorite_brick_wall"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_diorite_bricks to mossy_diorite_bricks",
			TrailierConstants.id("mossy_polished_diorite_bricks"),
			TrailierConstants.id("mossy_diorite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_diorite_brick_stairs to mossy_diorite_brick_stairs",
			TrailierConstants.id("mossy_polished_diorite_brick_stairs"),
			TrailierConstants.id("mossy_diorite_brick_stairs"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_diorite_brick_slab to mossy_diorite_brick_slab",
			TrailierConstants.id("mossy_polished_diorite_brick_slab"),
			TrailierConstants.id("mossy_diorite_brick_slab"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_diorite_brick_wall to mossy_diorite_brick_wall",
			TrailierConstants.id("mossy_polished_diorite_brick_wall"),
			TrailierConstants.id("mossy_diorite_brick_wall"),
			schemaV1
		);

		// ANDESITE
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename chiseled_polished_andesite to chiseled_andesite_bricks",
			TrailierConstants.id("chiseled_polished_andesite"),
			TrailierConstants.id("chiseled_andesite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_andesite_bricks to andesite_bricks",
			TrailierConstants.id("polished_andesite_bricks"),
			TrailierConstants.id("andesite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename cracked_polished_andesite_bricks to cracked_andesite_bricks",
			TrailierConstants.id("cracked_polished_andesite_bricks"),
			TrailierConstants.id("cracked_andesite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_andesite_brick_stairs to andesite_brick_stairs",
			TrailierConstants.id("polished_andesite_brick_stairs"),
			TrailierConstants.id("andesite_brick_stairs"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_andesite_brick_slab to andesite_brick_slab",
			TrailierConstants.id("polished_andesite_brick_slab"),
			TrailierConstants.id("andesite_brick_slab"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_andesite_brick_wall to andesite_brick_wall",
			TrailierConstants.id("polished_andesite_brick_wall"),
			TrailierConstants.id("andesite_brick_wall"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_andesite_bricks to mossy_andesite_bricks",
			TrailierConstants.id("mossy_polished_andesite_bricks"),
			TrailierConstants.id("mossy_andesite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_andesite_brick_stairs to mossy_andesite_brick_stairs",
			TrailierConstants.id("mossy_polished_andesite_brick_stairs"),
			TrailierConstants.id("mossy_andesite_brick_stairs"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_andesite_brick_slab to mossy_andesite_brick_slab",
			TrailierConstants.id("mossy_polished_andesite_brick_slab"),
			TrailierConstants.id("mossy_andesite_brick_slab"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_andesite_brick_wall to mossy_andesite_brick_wall",
			TrailierConstants.id("mossy_polished_andesite_brick_wall"),
			TrailierConstants.id("mossy_andesite_brick_wall"),
			schemaV1
		);
		QuiltDataFixes.buildAndRegisterFixer(mod, builder);
	}

}
