package net.frozenblock.trailiertales;

import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.entrypoint.api.FrozenModInitializer;
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
import net.frozenblock.trailiertales.registry.RegsiterRuleBlockEntityModifiers;
import net.frozenblock.trailiertales.worldgen.TrailierBiomeModifications;

public class TrailierTales extends FrozenModInitializer {

	public TrailierTales() {
		super(TrailierConstants.MOD_ID);
	}

	@Override
	public void onInitialize(String modId, ModContainer container) {
		TrailierConstants.startMeasuring(this);

		TrailierDataFixer.applyDataFixes(container);
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

		TrailierConstants.stopMeasuring(this);
	}
}
