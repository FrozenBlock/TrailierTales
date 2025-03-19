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

package net.frozenblock.trailiertales.mixin.common.surveyor;

import java.util.ArrayList;
import java.util.Arrays;
import net.frozenblock.trailiertales.block.impl.TTClipContextShapeGetters;
import net.frozenblock.trailiertales.tag.TTBlockTags;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.shapes.Shapes;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClipContext.Block.class)
public class ClipContextBlockMixin {

	//CREDIT TO nyuppo/fabric-boat-example ON GITHUB

	@SuppressWarnings("ShadowTarget")
	@Final
	@Shadow
	@Mutable
	private static ClipContext.Block[] $VALUES;

	@Final
	@Shadow
	public static ClipContext.Block COLLIDER;

	@SuppressWarnings("InvokerTarget")
	@Invoker("<init>")
	private static ClipContext.Block trailierTales$newBlock(String internalName, int internalId, ClipContext.ShapeGetter provider) {
		throw new AssertionError("Mixin injection failed - Trailier Tales ClipContextBlockMixin.");
	}

	@Inject(
		method = "<clinit>",
		at = @At(value = "FIELD",
			opcode = Opcodes.PUTSTATIC,
			target = "Lnet/minecraft/world/level/ClipContext$Block;$VALUES:[Lnet/minecraft/world/level/ClipContext$Block;",
			shift = At.Shift.AFTER
		)
	)
	private static void trailierTales$addCustomBlock(CallbackInfo info) {
		var blocks = new ArrayList<>(Arrays.asList($VALUES));
		var last = blocks.get(blocks.size() - 1);

		ClipContext.ShapeGetter surveyorShapeGetter = (blockState, blockGetter, blockPos, collisionContext) -> {
			if (blockState.is(TTBlockTags.SURVEYOR_CAN_SEE_THROUGH) && !blockState.is(TTBlockTags.SURVEYOR_CANNOT_SEE_THROUGH)) return Shapes.empty();
			return COLLIDER.get(blockState, blockGetter, blockPos, collisionContext);
		};
		var surveyorSight = trailierTales$newBlock(
			"TRAILIERTALESSURVEYORSIGHT",
			last.ordinal() + 1,
			surveyorShapeGetter
		);
		TTClipContextShapeGetters.SURVEYOR_SIGHT = surveyorSight;
		blocks.add(surveyorSight);

		$VALUES = blocks.toArray(new ClipContext.Block[0]);
	}
}
