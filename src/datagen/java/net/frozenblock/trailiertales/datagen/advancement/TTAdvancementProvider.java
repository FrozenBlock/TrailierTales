package net.frozenblock.trailiertales.datagen.advancement;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.frozenblock.trailiertales.TrailierConstants;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;

public class TTAdvancementProvider extends FabricAdvancementProvider {
	public TTAdvancementProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	public void generateAdvancement(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
		AdvancementHolder adventure = Advancement.Builder.advancement().build(TrailierConstants.vanillaId("adventure/root"));
		AdvancementHolder husbandry = Advancement.Builder.advancement().build(TrailierConstants.vanillaId("husbandry/root"));
	}
}
