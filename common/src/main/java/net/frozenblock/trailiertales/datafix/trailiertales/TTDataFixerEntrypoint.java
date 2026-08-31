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
