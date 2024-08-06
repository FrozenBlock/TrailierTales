package net.frozenblock.trailiertales;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.entrypoint.api.FrozenModInitializer;
import net.frozenblock.lib.feature_flag.api.FrozenFeatureFlags;
import net.frozenblock.trailiertales.datafix.trailiertales.TrailierDataFixer;
import net.frozenblock.trailiertales.mod_compat.TrailierModIntegrations;
import net.frozenblock.trailiertales.registry.RegisterBlockEntities;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterEnchantments;
import net.frozenblock.trailiertales.registry.RegisterEntities;
import net.frozenblock.trailiertales.registry.RegisterFeatures;
import net.frozenblock.trailiertales.registry.RegisterItems;
import net.frozenblock.trailiertales.registry.RegisterJukeboxSongs;
import net.frozenblock.trailiertales.registry.RegisterLootTables;
import net.frozenblock.trailiertales.registry.RegisterMemoryModuleTypes;
import net.frozenblock.trailiertales.registry.RegisterMobEffects;
import net.frozenblock.trailiertales.registry.RegisterParticles;
import net.frozenblock.trailiertales.registry.RegisterRecipies;
import net.frozenblock.trailiertales.registry.RegisterSensorTypes;
import net.frozenblock.trailiertales.registry.RegisterSounds;
import net.frozenblock.trailiertales.registry.RegisterStructurePieceTypes;
import net.frozenblock.trailiertales.registry.RegisterStructureTypes;
import net.frozenblock.trailiertales.registry.RegsiterRuleBlockEntityModifiers;
import net.frozenblock.trailiertales.worldgen.TrailierBiomeModifications;
import net.frozenblock.trailiertales.worldgen.structure.piece.RuinsPieces;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlags;
import org.jetbrains.annotations.NotNull;

public class TrailierTales extends FrozenModInitializer {
	public static final FeatureFlag FEATURE_FLAG = TrailierDatagenConstants.IS_DATAGEN ? TrailierFeatureFlags.TRAILIER_TALES : FeatureFlags.VANILLA;

	public TrailierTales() {
		super(TrailierConstants.MOD_ID);
	}

	@Override
	public void onInitialize(String modId, ModContainer container) {
		TrailierConstants.startMeasuring(this);

		if (TrailierDatagenConstants.IS_DATAGEN) {
			TrailierFeatureFlags.init();
			FrozenFeatureFlags.rebuild();
		}

		TrailierDataFixer.applyDataFixes(container);
		RegisterStructureTypes.init();
		RegisterStructurePieceTypes.init();
		RegisterBlocks.init();
		RegisterBlockEntities.register();
		RegisterItems.init();
		RegisterEntities.init();
		RegisterMemoryModuleTypes.register();
		RegisterSensorTypes.register();
		RegisterRecipies.init();
		RegisterFeatures.init();
		TrailierBiomeModifications.init();
		RegisterLootTables.init();
		RegisterSounds.init();
		RegisterParticles.init();
		RegisterEnchantments.init();
		RegisterMobEffects.init();
		RegisterJukeboxSongs.init();
		RegsiterRuleBlockEntityModifiers.init();

		TrailierModIntegrations.init();

		ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public ResourceLocation getFabricId() {
				return TrailierConstants.id("ruins_structure_piece_loader");
			}

			@Override
			public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
				RuinsPieces.reloadPiecesFromDirectories(resourceManager);
			}
		});

		TrailierConstants.stopMeasuring(this);
	}
}
