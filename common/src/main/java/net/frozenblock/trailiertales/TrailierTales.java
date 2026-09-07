package net.frozenblock.trailiertales;

import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.feature_flag.api.FeatureFlagApi;
import net.frozenblock.lib.gravity.api.GravityAPI;
import net.frozenblock.trailiertales.block.EctoplasmBlock;
import net.frozenblock.trailiertales.config.TTBlockConfig;
import net.frozenblock.trailiertales.config.TTEntityConfig;
import net.frozenblock.trailiertales.config.TTItemConfig;
import net.frozenblock.trailiertales.config.TTMiscConfig;
import net.frozenblock.trailiertales.config.TTWorldgenConfig;
import net.frozenblock.trailiertales.datafix.trailiertales.TTDataFixer;
import net.frozenblock.trailiertales.levelgen.biome.modification.TTBiomeModifications;
import net.frozenblock.trailiertales.levelgen.structure.modification.TTStructureModifications;
import net.frozenblock.trailiertales.registry.TTAttachmentTypes;
import net.frozenblock.trailiertales.registry.TTBlockEntityTypes;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.registry.TTCreativeInventorySorting;
import net.frozenblock.trailiertales.registry.TTDebugSubscriptions;
import net.frozenblock.trailiertales.registry.TTEntityTypes;
import net.frozenblock.trailiertales.registry.TTItems;
import net.frozenblock.trailiertales.registry.TTLootTables;
import net.frozenblock.trailiertales.registry.TTMapDecorationTypes;
import net.frozenblock.trailiertales.registry.TTMemoryModuleTypes;
import net.frozenblock.trailiertales.registry.TTMobEffects;
import net.frozenblock.trailiertales.registry.TTParticleTypes;
import net.frozenblock.trailiertales.registry.TTPotions;
import net.frozenblock.trailiertales.registry.TTRecipeTypes;
import net.frozenblock.trailiertales.registry.TTResources;
import net.frozenblock.trailiertales.registry.TTRuleBlockEntityModifiers;
import net.frozenblock.trailiertales.registry.TTSensorTypes;
import net.frozenblock.trailiertales.registry.TTSoundTypes;
import net.frozenblock.trailiertales.registry.TTSounds;
import net.frozenblock.trailiertales.registry.TTStructurePieceTypes;
import net.frozenblock.trailiertales.registry.TTStructureTypes;
import net.frozenblock.trailiertales.registry.TTWindDisturbances;

public final class TrailierTales {

	public static void init(String modId) {
		if (FrozenBools.IS_DATAGEN) {
			TTFeatureFlags.init();
			FeatureFlagApi.rebuild();
		}
		TTDataFixer.applyDataFixes(modId);
		TTResources.init(modId);

		TTStructureTypes.init();
		TTStructurePieceTypes.init();
		TTMapDecorationTypes.init();
		TTAttachmentTypes.init();
		TTSounds.init();
		TTBlocks.init();
		TTBlockEntityTypes.init();
		TTItems.init();
		TTEntityTypes.init();
		TTMemoryModuleTypes.init();
		TTSensorTypes.init();
		TTRecipeTypes.init();
		TTLootTables.init();
		TTParticleTypes.init();
		TTMobEffects.init();
		TTPotions.init();
		TTRuleBlockEntityModifiers.init();
		TTDebugSubscriptions.init();
		TTWindDisturbances.init();

		GravityAPI.MODIFICATIONS.register(gravityContext -> {
			if (gravityContext.entity == null) return;
			if (!(gravityContext.state.getBlock() instanceof EctoplasmBlock)) return;
			gravityContext.gravity = gravityContext.gravity.scale(EctoplasmBlock.GRAVITY_SLOWDOWN);
		});

		TTBlockConfig.CONFIG.load(true);
		TTEntityConfig.CONFIG.load(true);
		TTItemConfig.CONFIG.load(true);
		TTMiscConfig.CONFIG.load(true);
		TTWorldgenConfig.CONFIG.load(true);
	}

	public static void setup() {
		TTSoundTypes.setup();
		TTBlocks.registerBlockProperties();
		TTBlockEntityTypes.registerValidBlocks();
		TTCreativeInventorySorting.setup();
		TTBiomeModifications.setup();
		TTStructureModifications.setup();
	}
}
