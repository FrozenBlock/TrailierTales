package world.features.structure;

import net.lunade.onetwenty.util.Luna120SharedConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

public class RegisterStructureProcessors {

	public static final ResourceKey<StructureProcessorList> ABANDONED_BADLANDS_HOUSE = createKey("abandoned_badlands_house");

	public static void init() {
	}

	@NotNull
	private static ResourceKey<StructureProcessorList> createKey(@NotNull String string) {
		return ResourceKey.create(Registries.PROCESSOR_LIST, Luna120SharedConstants.id(string));
	}
}
