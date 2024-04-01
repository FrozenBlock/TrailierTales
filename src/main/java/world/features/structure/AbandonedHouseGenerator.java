package world.features.structure;

import com.mojang.datafixers.util.Either;
import java.util.function.Function;
import net.lunade.onetwenty.util.Luna120SharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

/**
 * Contains the StructureTemplatePool for Abandoned Cabins
 */
public class AbandonedHouseGenerator {

	public static final ResourceKey<StructureTemplatePool> ABANDONED_BADLANDS_HOUSE = createKey("abandoned_house/badlands");

	/**
	 * @param id                 The id for the {@link SinglePoolElement}'s {@link ResourceLocation}
	 * @param processorListEntry The processor list for the {@link SinglePoolElement}
	 * @return A {@link SinglePoolElement} of the parameters given.
	 */
	@NotNull
	public static Function<StructureTemplatePool.Projection, SinglePoolElement> ofProcessedSingle(@NotNull String id, @NotNull Holder<StructureProcessorList> processorListEntry) {
		return projection -> new SinglePoolElement(Either.left(Luna120SharedConstants.id(id)), processorListEntry, projection);
	}

	/**
	 * Initializes this class to register the {@link StructureTemplatePool}s
	 */
	public static void init() {
	}

	@NotNull
	public static ResourceKey<StructureTemplatePool> createKey(@NotNull String string) {
		return ResourceKey.create(Registries.TEMPLATE_POOL, Luna120SharedConstants.id(string));
	}
}
