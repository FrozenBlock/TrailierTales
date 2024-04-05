package net.frozenblock.trailiertales.structure;

import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

public class RegisterStructureProcessors {
	public static final float SUSPICIOUS_BLOCK_TO_NORMAL_085_CHANCE = 0.85F;
	public static final ResourceKey<StructureProcessorList> SUSPICIOUS_BLOCK_TO_NORMAL_085 = createKey("suspicious_block_to_normal_085");

	public static void init() {
	}

	@NotNull
	private static ResourceKey<StructureProcessorList> createKey(@NotNull String string) {
		return ResourceKey.create(Registries.PROCESSOR_LIST, TrailierTalesSharedConstants.id(string));
	}
}
