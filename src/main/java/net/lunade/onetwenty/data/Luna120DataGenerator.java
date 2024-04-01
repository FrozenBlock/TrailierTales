package net.lunade.onetwenty.data;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.lunade.onetwenty.registry.RegisterBlocksAndItems;
import net.lunade.onetwenty.registry.RegisterStructures;
import net.lunade.onetwenty.worldgen.Luna120FeatureBootstrap;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public class Luna120DataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(@NotNull FabricDataGenerator dataGenerator) {
		final FabricDataGenerator.Pack pack = dataGenerator.createPack();
		pack.addProvider(Luna120ItemTagProvider::new);
		pack.addProvider(Luna120BlockTagProvider::new);
		pack.addProvider(Luna120BiomeTagProvider::new);
		pack.addProvider(Luna120WorldgenProvider::new);
	}

	@Override
	public void buildRegistry(@NotNull RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.CONFIGURED_FEATURE, Luna120FeatureBootstrap::bootstrapConfigured);
		registryBuilder.add(Registries.PLACED_FEATURE, Luna120FeatureBootstrap::bootstrapPlaced);
		registryBuilder.add(Registries.PROCESSOR_LIST, RegisterStructures::bootstrapProcessor);
		registryBuilder.add(Registries.TEMPLATE_POOL, RegisterStructures::bootstrapTemplatePool);
		registryBuilder.add(Registries.STRUCTURE, RegisterStructures::bootstrap);
		registryBuilder.add(Registries.STRUCTURE_SET, RegisterStructures::bootstrapStructureSet);
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

	private static class Luna120BlockTagProvider extends FabricTagProvider.BlockTagProvider {

		public Luna120BlockTagProvider(FabricDataOutput output, CompletableFuture registriesFuture) {
			super(output, registriesFuture);
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_SHOVEL)
				.add(RegisterBlocksAndItems.SUSPICIOUS_RED_SAND);
		}
	}

	private static class Luna120ItemTagProvider extends FabricTagProvider<Item> {

		public Luna120ItemTagProvider(FabricDataOutput output, CompletableFuture registriesFuture) {
			super(output, Registries.ITEM, registriesFuture);
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
			this.getOrCreateTagBuilder(Luna120ItemTags.COPYABLE_SHERDS)
				.add(Items.ANGLER_POTTERY_SHERD)
				.add(Items.SHEAF_POTTERY_SHERD)
				.add(Items.SHELTER_POTTERY_SHERD)
				.add(Items.ARMS_UP_POTTERY_SHERD)
				.add(Items.ARCHER_POTTERY_SHERD)
				.add(Items.SKULL_POTTERY_SHERD)
				.add(Items.BLADE_POTTERY_SHERD)
				.add(Items.BREWER_POTTERY_SHERD)
				.add(Items.BURN_POTTERY_SHERD)
				.add(Items.DANGER_POTTERY_SHERD)
				.add(Items.SNORT_POTTERY_SHERD)
				.add(Items.EXPLORER_POTTERY_SHERD)
				.add(Items.PRIZE_POTTERY_SHERD)
				.add(Items.PLENTY_POTTERY_SHERD)
				.add(Items.MOURNER_POTTERY_SHERD)
				.add(Items.MINER_POTTERY_SHERD)
				.add(Items.HOWL_POTTERY_SHERD)
				.add(Items.HEARTBREAK_POTTERY_SHERD)
				.add(Items.HEART_POTTERY_SHERD)
				.add(Items.FRIEND_POTTERY_SHERD);

			this.getOrCreateTagBuilder(Luna120ItemTags.POT_BASES)
				.add(Items.BRICK);
		}
	}

	private static class Luna120BiomeTagProvider extends FabricTagProvider<Biome> {

		public Luna120BiomeTagProvider(FabricDataOutput output, CompletableFuture registriesFuture) {
			super(output, Registries.BIOME, registriesFuture);
		}

		@Override
		protected void addTags(HolderLookup.Provider arg) {
		}
	}
}
