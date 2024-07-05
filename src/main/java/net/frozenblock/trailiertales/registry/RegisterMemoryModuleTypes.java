package net.frozenblock.trailiertales.registry;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.entity.Apparition;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import org.jetbrains.annotations.NotNull;

public final class RegisterMemoryModuleTypes {
	private RegisterMemoryModuleTypes() {
		throw new UnsupportedOperationException("RegisterMemoryModuleTypes contains only static declarations.");
	}

	public static void register() {
		TrailierConstants.log("Registering MemoryModuleTypes for Trailier Tales", TrailierConstants.UNSTABLE_LOGGING);
	}

	public static final MemoryModuleType<List<Apparition>> NEARBY_APPARITIONS = register("nearby_apparitions");
	public static final MemoryModuleType<Unit> POSSESSION_COOLDOWN = register("possession_cooldown");
	public static final MemoryModuleType<LivingEntity> NEAREST_POSSESSABLE = register("nearest_possessable");

	@NotNull
	private static <U> MemoryModuleType<U> register(String identifier, Codec<U> codec) {
		return Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, TrailierConstants.id(identifier), new MemoryModuleType<>(Optional.of(codec)));
	}

	@NotNull
	private static <U> MemoryModuleType<U> register(String identifier) {
		return Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, TrailierConstants.id(identifier), new MemoryModuleType<>(Optional.empty()));
	}

}
