package net.frozenblock.trailiertales.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageType;
import org.jetbrains.annotations.NotNull;

public final class TTDamageTypeTagProvider extends FabricTagProvider<DamageType> {

	public TTDamageTypeTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registries) {
		super(output, Registries.DAMAGE_TYPE, registries);
	}

	@Override
	public void addTags(@NotNull HolderLookup.Provider arg) {

	}
}
