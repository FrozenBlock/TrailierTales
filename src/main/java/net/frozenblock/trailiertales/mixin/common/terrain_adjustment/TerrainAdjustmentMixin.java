package net.frozenblock.trailiertales.mixin.common.terrain_adjustment;

import java.util.ArrayList;
import java.util.Arrays;
import net.frozenblock.trailiertales.worldgen.TrailierTerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TerrainAdjustment.class)
public class TerrainAdjustmentMixin {

	@SuppressWarnings("ShadowTarget")
	@Final
	@Shadow
	@Mutable
	private static TerrainAdjustment[] $VALUES;

	@SuppressWarnings("InvokerTarget")
	@Invoker("<init>")
	private static TerrainAdjustment trailierTales$newType(String internalName, int internalId, String name) {
		throw new AssertionError("Mixin injection failed - Trailier Tales TerrainAdjustmentMixin.");
	}

	@Inject(method = "<clinit>",
		at = @At(
			value = "FIELD",
			opcode = Opcodes.PUTSTATIC,
			target = "Lnet/minecraft/world/level/levelgen/structure/TerrainAdjustment;$VALUES:[Lnet/minecraft/world/level/levelgen/structure/TerrainAdjustment;",
			shift = At.Shift.AFTER
		)
	)
	private static void trailierTales$addCustomTerrainAdjustment(CallbackInfo info) {
		var types = new ArrayList<>(Arrays.asList($VALUES));
		var last = types.getLast();

		var smallPlatform = trailierTales$newType("TRAILIER_TALES_SMALL_PLATFORM", last.ordinal() + 1, "trailier_tales_small_platform");
		TrailierTerrainAdjustment.SMALL_PLATFORM = smallPlatform;
		types.add(smallPlatform);

		$VALUES = types.toArray(new TerrainAdjustment[0]);
	}

}
