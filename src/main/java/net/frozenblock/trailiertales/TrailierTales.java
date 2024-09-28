package net.frozenblock.trailiertales;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.entrypoint.api.FrozenModInitializer;
import net.frozenblock.lib.feature_flag.api.FrozenFeatureFlags;
import net.frozenblock.trailiertales.config.TTMiscConfig;
import net.frozenblock.trailiertales.datafix.trailiertales.TTDataFixer;
import net.frozenblock.trailiertales.mod_compat.TTModIntegrations;
import net.frozenblock.trailiertales.networking.TrailierNetworking;
import net.frozenblock.trailiertales.registry.TTBlockEntityTypes;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTEnchantments;
import net.frozenblock.trailiertales.registry.TTEntityTypes;
import net.frozenblock.trailiertales.registry.TTItems;
import net.frozenblock.trailiertales.registry.TTJukeboxSongs;
import net.frozenblock.trailiertales.registry.TTLootTables;
import net.frozenblock.trailiertales.registry.TTMapDecorationTypes;
import net.frozenblock.trailiertales.registry.TTMemoryModuleTypes;
import net.frozenblock.trailiertales.registry.TTMobEffects;
import net.frozenblock.trailiertales.registry.TTParticleTypes;
import net.frozenblock.trailiertales.registry.TTPotions;
import net.frozenblock.trailiertales.registry.TTRecipeTypes;
import net.frozenblock.trailiertales.registry.TTRuleBlockEntityModifiers;
import net.frozenblock.trailiertales.registry.TTSensorTypes;
import net.frozenblock.trailiertales.registry.TTSounds;
import net.frozenblock.trailiertales.registry.TTStructurePieceTypes;
import net.frozenblock.trailiertales.registry.TTStructureTypes;
import net.frozenblock.trailiertales.registry.TTTrimPatterns;
import net.frozenblock.trailiertales.registry.TTVillagerTrades;
import net.frozenblock.trailiertales.worldgen.TTBiomeModifications;
import net.frozenblock.trailiertales.worldgen.structure.RuinsPieces;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

public class TrailierTales extends FrozenModInitializer {

	public TrailierTales() {
		super(TTConstants.MOD_ID);
	}

	@Override
	public void onInitialize(String modId, ModContainer container) {
		TTConstants.startMeasuring(this);

		if (TTPreLoadConstants.IS_DATAGEN) {
			TTFeatureFlags.init();
			FrozenFeatureFlags.rebuild();
		}

		TTDataFixer.applyDataFixes(container);
		TTStructureTypes.init();
		TTStructurePieceTypes.init();
		TTMapDecorationTypes.init();
		TTBlocks.init();
		TTBlockEntityTypes.register();
		TTItems.init();
		TTTrimPatterns.init();
		TTEntityTypes.init();
		TTMemoryModuleTypes.register();
		TTSensorTypes.register();
		TTRecipeTypes.init();
		TTBiomeModifications.init();
		TTLootTables.init();
		TTSounds.init();
		TTParticleTypes.init();
		TTEnchantments.init();
		TTMobEffects.init();
		TTPotions.init();
		TTJukeboxSongs.init();
		TTRuleBlockEntityModifiers.init();
		TTVillagerTrades.init();
		TrailierNetworking.init();

		TTModIntegrations.init();

		ResourceManagerHelper.registerBuiltinResourcePack(
			TTConstants.id("trailier_main_menu"),
			container, Component.literal("Trailier Main Menu"),
			TTMiscConfig.get().titleResourcePackEnabled ?
				ResourcePackActivationType.DEFAULT_ENABLED : ResourcePackActivationType.NORMAL
		);

		ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public ResourceLocation getFabricId() {
				return TTConstants.id("ruins_structure_piece_loader");
			}

			@Override
			public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
				RuinsPieces.reloadPiecesFromDirectories(resourceManager);
			}
		});

		TTConstants.stopMeasuring(this);
	}
}
