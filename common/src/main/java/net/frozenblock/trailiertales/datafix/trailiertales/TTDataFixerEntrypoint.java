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

package net.frozenblock.trailiertales.datafix.trailiertales;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import net.fabricmc.frozenblock.datafixer.api.DataFixerEntrypoint;
import net.fabricmc.frozenblock.datafixer.api.SchemaRegistry;
import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.util.datafix.fixes.References;

public final class TTDataFixerEntrypoint implements DataFixerEntrypoint {

	@Override
	public void onRegisterBlockEntities(SchemaRegistry registry, Schema schema) {
		registry.register(
			TTConstants.id("coffin"),
			() -> DSL.optionalFields(
				"spawn_potentials",
				DSL.list(DSL.fields("data", DSL.fields("entity", References.ENTITY_TREE.in(schema)))),
				"spawn_data",
				DSL.fields("entity", References.ENTITY_TREE.in(schema))
			)
		);
		registry.register(TTConstants.id("surveyor"), DSL::remainder);
	}

	@Override
	public void onRegisterEntities(SchemaRegistry registry, Schema schema) {
		registry.register(TTConstants.id("apparition"), () -> DSL.optionalFields("Inventory", References.ITEM_STACK.in(schema)));
		registry.register(TTConstants.id("thrown_item"), () -> DSL.optionalFields("Item", References.ITEM_STACK.in(schema)));
	}
}
