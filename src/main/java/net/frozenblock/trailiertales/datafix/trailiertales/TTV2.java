package net.frozenblock.trailiertales.datafix.trailiertales;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import net.frozenblock.trailiertales.TrailierConstants;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.util.datafix.schemas.V100;
import java.util.Map;
import java.util.function.Supplier;

public class TTV2 extends NamespacedSchema {

	public TTV2(int versionKey, Schema parent) {
		super(versionKey, parent);
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map = super.registerEntities(schema);

		schema.register(
			map,
			TrailierConstants.string("apparition"),
			() -> DSL.optionalFields(
				"Inventory",
				DSL.list(References.ITEM_STACK.in(schema)),
				V100.equipment(schema)
			)
		);
		schema.registerSimple(map, TrailierConstants.string("damaging_throwable_item_projectile"));

		return map;
	}
}
