package net.frozenblock.trailiertales.datagen.advancement;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.registry.TTBlocks;
import net.frozenblock.trailiertales.worldgen.structure.datagen.CatacombsGenerator;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class TTAdvancementProvider extends FabricAdvancementProvider {
	public TTAdvancementProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	public void generateAdvancement(HolderLookup.@NotNull Provider registries, Consumer<AdvancementHolder> writer) {
		AdvancementHolder adventure = Advancement.Builder.advancement().build(TTConstants.vanillaId("adventure/root"));

		AdvancementHolder enterCatacombs = Advancement.Builder.advancement()
			.parent(adventure)
			.display(
				Blocks.DEEPSLATE_BRICKS,
				Component.translatable("advancements.adventure.tomb_raider.title"),
				Component.translatable("advancements.adventure.tomb_raider.description"),
				null,
				AdvancementType.TASK,
				true,
				true,
				false
			)
			.addCriterion(
				"tomb_raider",
				PlayerTrigger.TriggerInstance.located(
					LocationPredicate.Builder.inStructure(registries.lookupOrThrow(Registries.STRUCTURE).getOrThrow(CatacombsGenerator.CATACOMBS_KEY))
				)
			)
			.save(writer, TTConstants.string("adventure/tomb_raider"));

		Advancement.Builder.advancement()
			.parent(enterCatacombs)
			.display(
				TTBlocks.ECTOPLASM_BLOCK,
				Component.translatable("advancements.adventure.walk_in_ectoplasm_block.title"),
				Component.translatable("advancements.adventure.walk_in_ectoplasm_block.description"),
				null,
				AdvancementType.TASK,
				true,
				true,
				false
			)
			.addCriterion("walk_in_ectoplasm_block", PlayerTrigger.TriggerInstance.located(
				EntityPredicate.Builder.entity()
					.steppingOn(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(TTBlocks.ECTOPLASM_BLOCK)))
				)
			)
			.save(writer, TTConstants.string("adventure/walk_in_ectoplasm_block"));
	}
}
