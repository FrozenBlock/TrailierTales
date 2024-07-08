package net.frozenblock.trailiertales.registry;

import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.block.entity.SurveyorBlockEntity;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

public class RegisterBlockEntities {

	public static final BlockEntityType<CoffinBlockEntity> COFFIN = register("coffin", CoffinBlockEntity::new, RegisterBlocks.COFFIN);
	public static final BlockEntityType<SurveyorBlockEntity> SURVEYOR = register("surveyor", SurveyorBlockEntity::new, RegisterBlocks.SURVEYOR);

	public static void register() {
		TrailierConstants.log("Registering BlockEntities for Trailier Tales.", TrailierConstants.UNSTABLE_LOGGING);
	}

	@NotNull
	private static <T extends BlockEntity> BlockEntityType<T> register(@NotNull String path, @NotNull BlockEntityType.BlockEntitySupplier<T> factory, @NotNull Block... blocks) {
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, TrailierConstants.id(path), BlockEntityType.Builder.of(factory, blocks).build(null));
	}
}
