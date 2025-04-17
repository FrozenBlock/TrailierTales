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

package net.frozenblock.trailiertales.datafix.trailiertales;

import com.mojang.datafixers.schemas.Schema;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixerBuilder;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixes;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.SimpleFixes;

public class TTDataFixer {
	public static final int DATA_VERSION = 3;

	private TTDataFixer() {
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
			TTConstants.id("chiseled_polished_granite"),
			TTConstants.id("chiseled_granite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_granite_bricks to granite_bricks",
			TTConstants.id("polished_granite_bricks"),
			TTConstants.id("granite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename cracked_polished_granite_bricks to cracked_granite_bricks",
			TTConstants.id("cracked_polished_granite_bricks"),
			TTConstants.id("cracked_granite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_granite_brick_stairs to granite_brick_stairs",
			TTConstants.id("polished_granite_brick_stairs"),
			TTConstants.id("granite_brick_stairs"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_granite_brick_slab to granite_brick_slab",
			TTConstants.id("polished_granite_brick_slab"),
			TTConstants.id("granite_brick_slab"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_granite_brick_wall to granite_brick_wall",
			TTConstants.id("polished_granite_brick_wall"),
			TTConstants.id("granite_brick_wall"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_granite_bricks to mossy_granite_bricks",
			TTConstants.id("mossy_polished_granite_bricks"),
			TTConstants.id("mossy_granite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_granite_brick_stairs to mossy_granite_brick_stairs",
			TTConstants.id("mossy_polished_granite_brick_stairs"),
			TTConstants.id("mossy_granite_brick_stairs"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_granite_brick_slab to mossy_granite_brick_slab",
			TTConstants.id("mossy_polished_granite_brick_slab"),
			TTConstants.id("mossy_granite_brick_slab"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_granite_brick_wall to mossy_granite_brick_wall",
			TTConstants.id("mossy_polished_granite_brick_wall"),
			TTConstants.id("mossy_granite_brick_wall"),
			schemaV1
		);

		// DIORITE
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename chiseled_polished_diorite to chiseled_diorite_bricks",
			TTConstants.id("chiseled_polished_diorite"),
			TTConstants.id("chiseled_diorite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_diorite_bricks to diorite_bricks",
			TTConstants.id("polished_diorite_bricks"),
			TTConstants.id("diorite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename cracked_polished_diorite_bricks to cracked_diorite_bricks",
			TTConstants.id("cracked_polished_diorite_bricks"),
			TTConstants.id("cracked_diorite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_diorite_brick_stairs to diorite_brick_stairs",
			TTConstants.id("polished_diorite_brick_stairs"),
			TTConstants.id("diorite_brick_stairs"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_diorite_brick_slab to diorite_brick_slab",
			TTConstants.id("polished_diorite_brick_slab"),
			TTConstants.id("diorite_brick_slab"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_diorite_brick_wall to diorite_brick_wall",
			TTConstants.id("polished_diorite_brick_wall"),
			TTConstants.id("diorite_brick_wall"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_diorite_bricks to mossy_diorite_bricks",
			TTConstants.id("mossy_polished_diorite_bricks"),
			TTConstants.id("mossy_diorite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_diorite_brick_stairs to mossy_diorite_brick_stairs",
			TTConstants.id("mossy_polished_diorite_brick_stairs"),
			TTConstants.id("mossy_diorite_brick_stairs"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_diorite_brick_slab to mossy_diorite_brick_slab",
			TTConstants.id("mossy_polished_diorite_brick_slab"),
			TTConstants.id("mossy_diorite_brick_slab"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_diorite_brick_wall to mossy_diorite_brick_wall",
			TTConstants.id("mossy_polished_diorite_brick_wall"),
			TTConstants.id("mossy_diorite_brick_wall"),
			schemaV1
		);

		// ANDESITE
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename chiseled_polished_andesite to chiseled_andesite_bricks",
			TTConstants.id("chiseled_polished_andesite"),
			TTConstants.id("chiseled_andesite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_andesite_bricks to andesite_bricks",
			TTConstants.id("polished_andesite_bricks"),
			TTConstants.id("andesite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename cracked_polished_andesite_bricks to cracked_andesite_bricks",
			TTConstants.id("cracked_polished_andesite_bricks"),
			TTConstants.id("cracked_andesite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_andesite_brick_stairs to andesite_brick_stairs",
			TTConstants.id("polished_andesite_brick_stairs"),
			TTConstants.id("andesite_brick_stairs"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_andesite_brick_slab to andesite_brick_slab",
			TTConstants.id("polished_andesite_brick_slab"),
			TTConstants.id("andesite_brick_slab"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename polished_andesite_brick_wall to andesite_brick_wall",
			TTConstants.id("polished_andesite_brick_wall"),
			TTConstants.id("andesite_brick_wall"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_andesite_bricks to mossy_andesite_bricks",
			TTConstants.id("mossy_polished_andesite_bricks"),
			TTConstants.id("mossy_andesite_bricks"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_andesite_brick_stairs to mossy_andesite_brick_stairs",
			TTConstants.id("mossy_polished_andesite_brick_stairs"),
			TTConstants.id("mossy_andesite_brick_stairs"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_andesite_brick_slab to mossy_andesite_brick_slab",
			TTConstants.id("mossy_polished_andesite_brick_slab"),
			TTConstants.id("mossy_andesite_brick_slab"),
			schemaV1
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename mossy_polished_andesite_brick_wall to mossy_andesite_brick_wall",
			TTConstants.id("mossy_polished_andesite_brick_wall"),
			TTConstants.id("mossy_andesite_brick_wall"),
			schemaV1
		);

		Schema schemaV3 = builder.addSchema(3, NamespacedSchema::new);
		SimpleFixes.addEntityRenameFix(
			builder,
			"Rename damaging_throwable_item_projectile to thrown_item",
			TTConstants.id("damaging_throwable_item_projectile"),
			TTConstants.id("thrown_item"),
			schemaV3
		);

		QuiltDataFixes.buildAndRegisterFixer(mod, builder);
	}

}
