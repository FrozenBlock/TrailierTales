package net.frozenblock.trailiertales.registry;

import com.mojang.serialization.MapCodec;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.worldgen.structure.RuinsStructure;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class RegisterStructureTypes {
	public static final StructureType<RuinsStructure> RUINS = register("ruins", RuinsStructure.CODEC);

	public static void init() {
	}

	private static <S extends Structure> StructureType<S> register(String id, MapCodec<S> codec) {
		return Registry.register(BuiltInRegistries.STRUCTURE_TYPE, TrailierConstants.id(id), () -> codec);
	}
}
