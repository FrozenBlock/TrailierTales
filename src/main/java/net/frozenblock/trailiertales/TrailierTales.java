package net.frozenblock.trailiertales;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import net.fabricmc.api.ModInitializer;
import net.frozenblock.trailiertales.registry.RegisterBlocksAndItems;
import net.frozenblock.trailiertales.data.recipe.SherdCopyRecipe;
import net.frozenblock.trailiertales.registry.RegisterStructures;
import net.frozenblock.trailiertales.util.TrailierTalesSharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TrailierTales implements ModInitializer {

	public static final BooleanProperty CAN_PLACE_ITEM = BooleanProperty.create("can_place_item");

	public static final RecipeSerializer<SherdCopyRecipe> SHERD_COPY_RECIPE = Registry.register(
		BuiltInRegistries.RECIPE_SERIALIZER,
		TrailierTalesSharedConstants.id("crafting_sherd_copy"),
		new SimpleCraftingRecipeSerializer<>(SherdCopyRecipe::new)
	);

	@SuppressWarnings("unchecked")
	@Nullable
	public static <T> T getRandomEntry(@NotNull RandomSource random, @NotNull TagKey<T> tag) {
		Optional<? extends Registry<?>> maybeRegistry = BuiltInRegistries.REGISTRY.getOptional(tag.registry().location());
		Objects.requireNonNull(random);
		Objects.requireNonNull(tag);

		if (maybeRegistry.isPresent()) {
			Registry<T> registry = (Registry<T>) maybeRegistry.get();
			if (tag.isFor(registry.key())) {
				ArrayList<T> entries = new ArrayList<>();
				for (Holder<T> entry : registry.getTagOrEmpty(tag)) {
					var optionalKey = entry.unwrapKey();
					if (optionalKey.isPresent()) {
						var key = optionalKey.get();
						registry.getOptional(key).ifPresent(entries::add);
					}
				}
				if (!entries.isEmpty()) {
					return entries.get(random.nextInt(entries.size()));
				}
			}
		}
		return null;
	}

	@Override
	public void onInitialize() {
		TrailierTalesSharedConstants.startMeasuring(this);
		RegisterBlocksAndItems.init();
		RegisterStructures.init();
		TrailierTalesSharedConstants.stopMeasuring(this);
	}
}
