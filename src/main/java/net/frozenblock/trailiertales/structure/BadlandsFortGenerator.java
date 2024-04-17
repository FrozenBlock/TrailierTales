package net.frozenblock.trailiertales.structure;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Function;
import net.frozenblock.trailiertales.TrailierEnumValues;
import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.frozenblock.trailiertales.registry.RegisterStructureProcessors;
import net.frozenblock.trailiertales.registry.RegisterStructures;
import net.frozenblock.trailiertales.tag.TrailierBiomeTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

/**
 * Contains the StructureTemplatePool for Abandoned Cabins
 */
public class BadlandsFortGenerator {
	public static final ResourceKey<StructureSet> BADLANDS_FORTS_KEY =  RegisterStructures.ofSet("badlands_fort");
	private static final ResourceKey<Structure> BADLANDS_FORT_KEY = RegisterStructures.createKey("badlands_fort");
	public static final ResourceKey<StructureTemplatePool> BADLANDS_FORT = createKey("badlands_fort");

	/**
	 * @param id The id for the {@link SinglePoolElement}'s {@link ResourceLocation}
	 * @param processorListEntry The processor list for the {@link SinglePoolElement}
	 * @return A {@link SinglePoolElement} of the parameters given.
	 */
	@NotNull
	public static Function<StructureTemplatePool.Projection, SinglePoolElement> ofProcessedSingle(@NotNull String id, @NotNull Holder<StructureProcessorList> processorListEntry) {
		return projection -> new SinglePoolElement(Either.left(TrailierTalesSharedConstants.id(id)), processorListEntry, projection);
	}

	public static void bootstrapTemplatePool(@NotNull BootstrapContext<StructureTemplatePool> context) {
		HolderGetter<StructureProcessorList> processor = context.lookup(Registries.PROCESSOR_LIST);
		HolderGetter<StructureTemplatePool> holderGetter2 = context.lookup(Registries.TEMPLATE_POOL);
		Holder<StructureTemplatePool> holder2 = holderGetter2.getOrThrow(Pools.EMPTY);

		context.register(
			BADLANDS_FORT,
			new StructureTemplatePool(
				holder2,
				List.of(
					Pair.of(ofProcessedSingle("badlands_fort/fort_1", processor.getOrThrow(RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085)), 1),
					Pair.of(ofProcessedSingle("badlands_fort/fort_2", processor.getOrThrow(RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085)), 1),
					Pair.of(ofProcessedSingle("badlands_fort/fort_tower_1", processor.getOrThrow(RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085)), 1),
					Pair.of(ofProcessedSingle("badlands_fort/fort_tower_2", processor.getOrThrow(RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085)), 1)
				),
				StructureTemplatePool.Projection.RIGID
			)
		);
	}

	public static void bootstrap(@NotNull BootstrapContext<Structure> context) {
		HolderGetter<Biome> holderGetter = context.lookup(Registries.BIOME);
		HolderGetter<StructureTemplatePool> templatePool = context.lookup(Registries.TEMPLATE_POOL);

		context.register(
			BADLANDS_FORT_KEY,
			new JigsawStructure(
				RegisterStructures.structure(
					holderGetter.getOrThrow(TrailierBiomeTags.HAS_BADLANDS_FORT),
					GenerationStep.Decoration.SURFACE_STRUCTURES,
					TrailierEnumValues.SMALL_PLATFORM
				),
				templatePool.getOrThrow(BadlandsFortGenerator.BADLANDS_FORT),
				1,
				ConstantHeight.of(VerticalAnchor.absolute(-2)),
				false,
				Heightmap.Types.WORLD_SURFACE_WG
			)
		);
	}

	public static void bootstrapStructureSet(@NotNull BootstrapContext<StructureSet> context) {
		HolderGetter<Structure> structure = context.lookup(Registries.STRUCTURE);

		context.register(
			BADLANDS_FORTS_KEY,
			new StructureSet(
				structure.getOrThrow(BADLANDS_FORT_KEY),
				new RandomSpreadStructurePlacement(20, 15, RandomSpreadType.LINEAR, 21338252) // ancient city salt is 20083232
			)
		);
	}

	@NotNull
	public static ResourceKey<StructureTemplatePool> createKey(@NotNull String string) {
		return ResourceKey.create(Registries.TEMPLATE_POOL, TrailierTalesSharedConstants.id(string));
	}
}
