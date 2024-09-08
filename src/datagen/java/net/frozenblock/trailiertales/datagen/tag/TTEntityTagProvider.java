package net.frozenblock.trailiertales.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.frozenblock.trailiertales.registry.TTEntities;
import net.frozenblock.trailiertales.tag.TTEntityTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public final class TTEntityTagProvider extends FabricTagProvider.EntityTypeTagProvider {

	public TTEntityTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.getOrCreateTagBuilder(TTEntityTags.APPARITION_TARGETABLE)
			.add(EntityType.PLAYER);

		this.getOrCreateTagBuilder(TTEntityTags.SURVEYOR_IGNORES)
			.add(TTEntities.APPARITION);

		this.getOrCreateTagBuilder(EntityTypeTags.CAN_BREATHE_UNDER_WATER)
			.add(TTEntities.APPARITION);

		this.getOrCreateTagBuilder(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
			.add(TTEntities.APPARITION);

		this.getOrCreateTagBuilder(EntityTypeTags.WITHER_FRIENDS)
			.add(TTEntities.APPARITION);
	}
}
