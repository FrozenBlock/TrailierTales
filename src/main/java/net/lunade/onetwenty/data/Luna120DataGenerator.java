package net.lunade.onetwenty.data;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.lunade.onetwenty.worldgen.Luna120FeatureBootstrap;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;

public class Luna120DataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
		final FabricDataGenerator.Pack pack = dataGenerator.createPack();
		pack.addProvider(Luna120ItemTagProvider::new);
		pack.addProvider(Luna120BiomeTagProvider::new);
		pack.addProvider(Luna120WorldgenProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.CONFIGURED_FEATURE, Luna120FeatureBootstrap::bootstrapConfigured);
		registryBuilder.add(Registries.PLACED_FEATURE, Luna120FeatureBootstrap::bootstrapPlaced);
	}

	private static class Luna120WorldgenProvider extends FabricDynamicRegistryProvider {

		public Luna120WorldgenProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		protected void configure(HolderLookup.Provider registries, Entries entries) {
			Luna120FeatureBootstrap.bootstrap(entries);
		}

		@Override
		public String getName() {
			return "Luna 1.20 Dynamic Registries";
		}
	}

	private static class Luna120ItemTagProvider extends FabricTagProvider<Item> {

		public Luna120ItemTagProvider(FabricDataOutput output, CompletableFuture registriesFuture) {
			super(output, Registries.ITEM, registriesFuture);
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			this.getOrCreateTagBuilder(Luna120ItemTags.COMMON_SNIFFER_LOOT)
					.add(Items.DANDELION)
					.add(Items.POPPY)
					.add(Items.BLUE_ORCHID)
					.add(Items.ALLIUM)
					.add(Items.AZURE_BLUET)
					.add(Items.ORANGE_TULIP)
					.add(Items.PINK_TULIP)
					.add(Items.RED_TULIP)
					.add(Items.WHITE_TULIP)
					.add(Items.OXEYE_DAISY)
					.add(Items.CORNFLOWER)
					.add(Items.LILY_OF_THE_VALLEY)
					.add(Items.LILAC)
					.add(Items.ROSE_BUSH)
					.add(Items.PEONY)
					.add(Items.SUNFLOWER);

			this.getOrCreateTagBuilder(Luna120ItemTags.UNCOMMON_SNIFFER_LOOT)
					.add(Items.WHEAT_SEEDS)
					.add(Items.HANGING_ROOTS)
					.add(Items.FLINT)
					.add(Items.GRASS)
					.add(Items.FERN)
					.add(Items.STICK)
					.add(Items.STRING)
					.add(Items.TORCHFLOWER_SEEDS);

			this.getOrCreateTagBuilder(Luna120ItemTags.RARE_SNIFFER_LOOT)
					.add(Items.CARROT)
					.add(Items.POTATO)
					.add(Items.BEETROOT_SEEDS)
					.add(Items.BONE)
					.add(Items.COAL)
					.add(Items.FEATHER);
		}
	}

	private static class Luna120BiomeTagProvider extends FabricTagProvider<Biome> {

		public Luna120BiomeTagProvider(FabricDataOutput output, CompletableFuture registriesFuture) {
			super(output, Registries.BIOME, registriesFuture);
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			this.getOrCreateTagBuilder(Luna120BiomeTags.HAS_TORCHFLOWER)
					.addOptionalTag(BiomeTags.IS_JUNGLE);
			this.getOrCreateTagBuilder(Luna120BiomeTags.HAS_SNIFFER)
					.addOptionalTag(BiomeTags.IS_JUNGLE);
		}
	}
}
