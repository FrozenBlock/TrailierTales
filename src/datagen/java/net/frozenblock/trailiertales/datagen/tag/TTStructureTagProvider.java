package net.frozenblock.trailiertales.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.frozenblock.trailiertales.tag.TrailierStructureTags;
import net.frozenblock.trailiertales.worldgen.structure.datagen.CatacombsGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.StructureTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public final class TTStructureTagProvider extends StructureTagsProvider {

	public TTStructureTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.tag(TrailierStructureTags.ON_CATACOMBS_EXPLORER_MAPS)
			.add(CatacombsGenerator.CATACOMBS_KEY);

	}

}
