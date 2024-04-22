package net.frozenblock.trailiertales.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinBlockEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

public class RegisterBlockEntities {

	public static final BlockEntityType<CoffinBlockEntity> COFFIN = register("coffin", CoffinBlockEntity::new, RegisterBlocks.COFFIN);

	public static void register() {
		TrailierTalesSharedConstants.log("Registering BlockEntities for Trailier Tales.", TrailierTalesSharedConstants.UNSTABLE_LOGGING);
	}

	@NotNull
	private static <T extends BlockEntity> BlockEntityType<T> register(@NotNull String path, @NotNull FabricBlockEntityTypeBuilder.Factory<T> blockEntity, @NotNull Block... blocks) {
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, TrailierTalesSharedConstants.id(path), FabricBlockEntityTypeBuilder.create(blockEntity, blocks).build(null));
	}
}
