package net.frozenblock.trailiertales.mixin.common.datafix;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.frozenblock.trailiertales.TrailierConstants;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.V3689;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(V3689.class)
public class V3689Mixin {

	@WrapOperation(
		method = "registerBlockEntities",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/datafix/schemas/NamespacedSchema;registerBlockEntities(Lcom/mojang/datafixers/schemas/Schema;)Ljava/util/Map;",
			ordinal = 0
		)
	)
	public Map<String, Supplier<TypeTemplate>> trailierTales$registerBlockEntities(V3689 instance, Schema schema, Operation<Map<String, Supplier<TypeTemplate>>> original) {
		Map<String, Supplier<TypeTemplate>> map = original.call(instance, schema);
		schema.register(
			map,
			TrailierConstants.string("coffin"),
			() -> DSL.optionalFields(
				"spawn_potentials",
				DSL.list(DSL.fields("data", DSL.fields("entity", References.ENTITY_TREE.in(schema)))),
				"spawn_data",
				DSL.fields("entity", References.ENTITY_TREE.in(schema))
			)
		);
		schema.register(
			map,
			TrailierConstants.string("surveyor"),
			DSL::remainder
		);
		return map;
	}

	@WrapOperation(
		method = "registerEntities",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/datafix/schemas/NamespacedSchema;registerEntities(Lcom/mojang/datafixers/schemas/Schema;)Ljava/util/Map;",
			ordinal = 0
		)
	)
	public Map<String, Supplier<TypeTemplate>> trailierTales$registerEntities(V3689 instance, Schema schema, Operation<Map<String, Supplier<TypeTemplate>>> original) {
		Map<String, Supplier<TypeTemplate>> map = original.call(instance, schema);
		schema.register(
			map,
			TrailierConstants.string("apparition"),
			(string) -> DSL.optionalFields("Inventory", References.ITEM_STACK.in(schema))
		);
		schema.register(
			map,
			TrailierConstants.string("thrown_item"),
			(string) -> DSL.optionalFields("Item", References.ITEM_STACK.in(schema))
		);
		return map;
	}
}
