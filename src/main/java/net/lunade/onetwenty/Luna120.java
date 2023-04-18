package net.lunade.onetwenty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.lunade.onetwenty.data.Luna120BiomeTags;
import net.lunade.onetwenty.util.Luna120SharedConstants;
import net.lunade.onetwenty.worldgen.Luna120FeatureBootstrap;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

public class Luna120 implements ModInitializer {

	public static final Block SUSPICIOUS_RED_SAND = new BrushableBlock(Blocks.SAND, BlockBehaviour.Properties.of(Material.SAND, MaterialColor.COLOR_ORANGE).strength(0.25f).sound(SoundType.SUSPICIOUS_SAND).pushReaction(PushReaction.DESTROY), SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED);
	public static final BlockItem SUSPICIOUS_RED_SAND_ITEM = new BlockItem(SUSPICIOUS_RED_SAND, new Item.Properties());
	@Override
	public void onInitialize() {
		Luna120SharedConstants.startMeasuring(this);

		Registry.register(BuiltInRegistries.BLOCK, Luna120SharedConstants.id("suspicious_red_sand"), SUSPICIOUS_RED_SAND);

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries ->
			entries.addAfter(Items.SUSPICIOUS_SAND,  List.of(new ItemStack(SUSPICIOUS_RED_SAND_ITEM)), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));

		/*
		BiomeModifications.addFeature(BiomeSelectors.tag(Luna120BiomeTags.HAS_TORCHFLOWER),
				GenerationStep.Decoration.VEGETAL_DECORATION, Luna120FeatureBootstrap.TORCHFLOWER_PLACED);

		BiomeModifications.addSpawn(BiomeSelectors.tag(Luna120BiomeTags.HAS_SNIFFER),
				MobCategory.CREATURE, EntityType.SNIFFER, 2, 1, 1);

		SpawnPlacements.register(EntityType.SNIFFER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
		 */

		Luna120SharedConstants.stopMeasuring(this);
	}

	@SuppressWarnings("unchecked")
	@Nullable
	public static <T> T getRandomEntry(RandomSource random, TagKey<T> tag) {
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
}
