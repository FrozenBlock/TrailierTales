package net.frozenblock.trailiertales.registry;

import com.mojang.serialization.Codec;
import net.frozenblock.trailiertales.TrailierConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class RegisterMemoryModuleTypes {
	private RegisterMemoryModuleTypes() {
		throw new UnsupportedOperationException("RegisterMemoryModuleTypes contains only static declarations.");
	}

	public static void register() {
		TrailierConstants.log("Registering MemoryModuleTypes for Trailier Tales", TrailierConstants.UNSTABLE_LOGGING);
	}

	public static final MemoryModuleType<Boolean> IS_UNDERGROUND = register("is_underground", Codec.BOOL);
	public static final MemoryModuleType<Integer> HEAL_COOLDOWN_TICKS = register("heal_cooldown_ticks", Codec.INT);
	public static final MemoryModuleType<Boolean> IS_PLAYER_NEARBY = register("is_player_nearby", Codec.BOOL);
	public static final MemoryModuleType<Boolean> CAN_DIG = register("can_dig", Codec.BOOL);
	public static final MemoryModuleType<Unit> FIRST_BRAIN_TICK = register("first_brain_tick");

	@NotNull
	private static <U> MemoryModuleType<U> register(String identifier, Codec<U> codec) {
		return Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, TrailierConstants.id(identifier), new MemoryModuleType<>(Optional.of(codec)));
	}

	@NotNull
	private static <U> MemoryModuleType<U> register(String identifier) {
		return Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, TrailierConstants.id(identifier), new MemoryModuleType<>(Optional.empty()));
	}

}
