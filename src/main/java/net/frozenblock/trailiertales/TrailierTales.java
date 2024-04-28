package net.frozenblock.trailiertales;

import java.util.List;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.advancement.api.AdvancementAPI;
import net.frozenblock.lib.advancement.api.AdvancementEvents;
import net.frozenblock.lib.block.api.tick.BlockScheduledTicks;
import net.frozenblock.lib.entrypoint.api.FrozenModInitializer;
import net.frozenblock.trailiertales.registry.RegisterBlockEntities;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterFeatures;
import net.frozenblock.trailiertales.registry.RegisterItems;
import net.frozenblock.trailiertales.registry.RegisterLootTables;
import net.frozenblock.trailiertales.registry.RegisterRecipies;
import net.frozenblock.trailiertales.worldgen.TrailierBiomeModifications;
import net.frozenblock.trailiertales.worldgen.impl.suspicious_handler.SuspiciousData;
import net.frozenblock.trailiertales.worldgen.impl.suspicious_handler.SuspiciousDataStorage;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class TrailierTales extends FrozenModInitializer {

	public TrailierTales() {
		super(TrailierTalesSharedConstants.MOD_ID);
	}

	@Override
	public void onInitialize(String modId, ModContainer container) {
		TrailierTalesSharedConstants.startMeasuring(this);

		RegisterBlocks.init();
		RegisterBlockEntities.register();
		RegisterItems.init();
		RegisterRecipies.init();
		RegisterFeatures.init();
		TrailierBiomeModifications.init();
		RegisterLootTables.init();

		ServerWorldEvents.LOAD.register((server, level) -> {
			DimensionDataStorage dimensionDataStorage = level.getDataStorage();
			SuspiciousData suspiciousData = SuspiciousData.getSuspiciousData(level);
			dimensionDataStorage.computeIfAbsent(suspiciousData.createData(), SuspiciousDataStorage.SUSPICIOUS_FILE_ID);
		});

		BlockScheduledTicks.TICKS.put(
			Blocks.SUSPICIOUS_GRAVEL, (state, world, pos, random) -> SuspiciousData.addLootTableToBrushableBlock(world, pos)
		);

		BlockScheduledTicks.TICKS.put(
			Blocks.SUSPICIOUS_SAND, (state, world, pos, random) -> SuspiciousData.addLootTableToBrushableBlock(world, pos)
		);

		BlockScheduledTicks.TICKS.put(
			RegisterBlocks.SUSPICIOUS_CLAY, (state, world, pos, random) -> SuspiciousData.addLootTableToBrushableBlock(world, pos)
		);

		BlockScheduledTicks.TICKS.put(
			RegisterBlocks.SUSPICIOUS_DIRT, (state, world, pos, random) -> SuspiciousData.addLootTableToBrushableBlock(world, pos)
		);

		BlockScheduledTicks.TICKS.put(
			RegisterBlocks.SUSPICIOUS_RED_SAND, (state, world, pos, random) -> SuspiciousData.addLootTableToBrushableBlock(world, pos)
		);

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

		TrailierTalesSharedConstants.stopMeasuring(this);
	}
}
