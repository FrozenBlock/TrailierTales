package net.frozenblock.trailiertales.mixin.common.datafix;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import java.util.function.BiFunction;
import net.frozenblock.trailiertales.TrailierConstants;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.AddNewChoices;
import net.minecraft.util.datafix.fixes.References;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(DataFixers.class)
public class DataFixersMixin {

	@WrapOperation(
		method = "addFixers",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/datafixers/DataFixerBuilder;addSchema(ILjava/util/function/BiFunction;)Lcom/mojang/datafixers/schemas/Schema;",
			remap = false,
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "CONSTANT",
				args = "intValue=3689"
			)
		)
	)
	private static Schema trailierTales$addFixers3689(DataFixerBuilder builder, int version, BiFunction<Integer, Schema, Schema> factory, Operation<Schema> original) {
		Schema schema = original.call(builder, version, factory);
		builder.addFixer(new AddNewChoices(schema, TrailierConstants.string("coffin"), References.BLOCK_ENTITY));
		builder.addFixer(new AddNewChoices(schema, TrailierConstants.string("surveyor"), References.BLOCK_ENTITY));
		builder.addFixer(new AddNewChoices(schema, TrailierConstants.string("apparition"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schema, TrailierConstants.string("thrown_item"), References.ENTITY));
		return schema;
	}

}
