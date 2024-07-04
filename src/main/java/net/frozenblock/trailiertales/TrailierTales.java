package net.frozenblock.trailiertales;

import java.util.List;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.advancement.api.AdvancementAPI;
import net.frozenblock.lib.advancement.api.AdvancementEvents;
import net.frozenblock.lib.entrypoint.api.FrozenModInitializer;
import net.frozenblock.trailiertales.mod_compat.TrailierModIntegrations;
import net.frozenblock.trailiertales.registry.RegisterBlockEntities;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterEntities;
import net.frozenblock.trailiertales.registry.RegisterFeatures;
import net.frozenblock.trailiertales.registry.RegisterItems;
import net.frozenblock.trailiertales.registry.RegisterLootTables;
import net.frozenblock.trailiertales.registry.RegisterMemoryModuleTypes;
import net.frozenblock.trailiertales.registry.RegisterParticles;
import net.frozenblock.trailiertales.registry.RegisterRecipies;
import net.frozenblock.trailiertales.registry.RegisterSensorTypes;
import net.frozenblock.trailiertales.registry.RegisterSounds;
import net.frozenblock.trailiertales.worldgen.TrailierBiomeModifications;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger;

public class TrailierTales extends FrozenModInitializer {

	public TrailierTales() {
		super(TrailierConstants.MOD_ID);
	}

	@Override
	public void onInitialize(String modId, ModContainer container) {
		TrailierConstants.startMeasuring(this);

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

		AdvancementEvents.INIT.register((holder, registries) -> {
			Advancement advancement = holder.value();
			//if (AmbienceAndMiscConfig.get().modifyAdvancements) {

				switch (holder.id().toString()) {
					case "minecraft:adventure/plant_any_sniffer_seed" -> {
						AdvancementAPI.addCriteria(advancement, "trailiertales:cyan_rose", CriteriaTriggers.PLACED_BLOCK.createCriterion(
							ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(RegisterBlocks.CYAN_ROSE_CROP).triggerInstance())
						);
						AdvancementAPI.addRequirementsToList(advancement,
							List.of(
								"trailiertales:cyan_rose"
							)
						);
					}
					default -> {
					}
				}

			//}
		});

		TrailierModIntegrations.init();

		TrailierConstants.stopMeasuring(this);
	}
}
