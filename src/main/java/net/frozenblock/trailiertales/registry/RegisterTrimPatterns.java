package net.frozenblock.trailiertales.registry;

import net.frozenblock.trailiertales.TrailierConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.item.armortrim.TrimPatterns;
import org.jetbrains.annotations.NotNull;

public class RegisterTrimPatterns {
	public static final ResourceKey<TrimPattern> DESOLATION = create("desolation");
	public static final ResourceKey<TrimPattern> UNDEAD = create("undead");
	public static final ResourceKey<TrimPattern> MATRIX = create("matrix");
	public static final ResourceKey<TrimPattern> GEODE = create("geode");

	public static void init() {
	}

	private static @NotNull ResourceKey<TrimPattern> create(String path) {
		return ResourceKey.create(Registries.TRIM_PATTERN, TrailierConstants.id(path));
	}

	public static void bootstrap(BootstrapContext<TrimPattern> context) {
		TrimPatterns.register(context, RegisterItems.DESOLATION_ARMOR_TRIM_SMITHING_TEMPLATE, DESOLATION);
		TrimPatterns.register(context, RegisterItems.UNDEAD_ARMOR_TRIM_SMITHING_TEMPLATE, UNDEAD);
		TrimPatterns.register(context, RegisterItems.MATRIX_ARMOR_TRIM_SMITHING_TEMPLATE, MATRIX);
		TrimPatterns.register(context, RegisterItems.GEODE_ARMOR_TRIM_SMITHING_TEMPLATE, GEODE);
	}
}
