/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.mixin.common.datafix;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import java.util.function.BiFunction;
import net.frozenblock.trailiertales.TTConstants;
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
		builder.addFixer(new AddNewChoices(schema, TTConstants.string("coffin"), References.BLOCK_ENTITY));
		builder.addFixer(new AddNewChoices(schema, TTConstants.string("surveyor"), References.BLOCK_ENTITY));
		builder.addFixer(new AddNewChoices(schema, TTConstants.string("apparition"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schema, TTConstants.string("thrown_item"), References.ENTITY));
		return schema;
	}

}
