package net.lunade.onetwenty;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import net.fabricmc.api.ModInitializer;
import net.lunade.onetwenty.data.recipe.SherdCopyRecipe;
import net.lunade.onetwenty.registry.RegisterBlocksAndItems;
import net.lunade.onetwenty.registry.RegisterStructures;
import net.lunade.onetwenty.util.Luna120SharedConstants;
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

public class Luna120 implements ModInitializer {

	public static final BooleanProperty CAN_PLACE_ITEM = BooleanProperty.create("can_place_item");

	public static final RecipeSerializer<SherdCopyRecipe> SHERD_COPY_RECIPE = Registry.register(
		BuiltInRegistries.RECIPE_SERIALIZER,
		Luna120SharedConstants.id("crafting_sherd_copy"),
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
		Luna120SharedConstants.startMeasuring(this);
		RegisterBlocksAndItems.init();
		RegisterStructures.init();
		Luna120SharedConstants.stopMeasuring(this);
	}
}
