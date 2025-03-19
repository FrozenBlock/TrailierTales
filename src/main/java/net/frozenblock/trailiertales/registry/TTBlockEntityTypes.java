package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityType;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.block.entity.SurveyorBlockEntity;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

public final class TTBlockEntityTypes {

	public static final BlockEntityType<CoffinBlockEntity> COFFIN = register("coffin", CoffinBlockEntity::new, TTBlocks.COFFIN);
	public static final BlockEntityType<SurveyorBlockEntity> SURVEYOR = register("surveyor", SurveyorBlockEntity::new, TTBlocks.SURVEYOR);

	public static void register() {
		TTConstants.log("Registering BlockEntities for Trailier Tales.", TTConstants.UNSTABLE_LOGGING);

		FabricBlockEntityType fabricBrushableBlock = (FabricBlockEntityType) BlockEntityType.BRUSHABLE_BLOCK;
		fabricBrushableBlock.addSupportedBlock(TTBlocks.SUSPICIOUS_DIRT);
		fabricBrushableBlock.addSupportedBlock(TTBlocks.SUSPICIOUS_CLAY);
		fabricBrushableBlock.addSupportedBlock(TTBlocks.SUSPICIOUS_RED_SAND);
	}

	@NotNull
	private static <T extends BlockEntity> BlockEntityType<T> register(@NotNull String path, @NotNull BlockEntityType.BlockEntitySupplier<T> factory, @NotNull Block... blocks) {
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, TTConstants.id(path), BlockEntityType.Builder.of(factory, blocks).build(null));
	}
}
