package net.frozenblock.trailiertales.registry;

import net.frozenblock.trailiertales.TrailierConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import org.jetbrains.annotations.NotNull;

public class RegisterMapDecorationTypes {
	public static final Holder<MapDecorationType> CATACOMBS = register(
		"catacombs",
		"catacombs",
		true,
		10663385,
		false,
		true
	);

	public static void init() {
	}

	private static @NotNull Holder<MapDecorationType> register(String string, String string2, boolean showOnItemFrame, boolean trackCount) {
		return register(string, string2, showOnItemFrame, -1, trackCount, false);
	}

	private static @NotNull Holder<MapDecorationType> register(
		String string, String string2, boolean showOnItemFrame, int mapColor, boolean trackCount, boolean explorationMapElement
	) {
		ResourceKey<MapDecorationType> resourceKey = ResourceKey.create(Registries.MAP_DECORATION_TYPE, TrailierConstants.id(string));
		MapDecorationType mapDecorationType = new MapDecorationType(
			TrailierConstants.id(string2), showOnItemFrame, mapColor, explorationMapElement, trackCount
		);
		return Registry.registerForHolder(BuiltInRegistries.MAP_DECORATION_TYPE, resourceKey, mapDecorationType);
	}

}
