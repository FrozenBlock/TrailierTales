package net.frozenblock.trailiertales.registry;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.frozenblock.trailiertales.structure.BadlandsFortGenerator;
import net.frozenblock.trailiertales.structure.RegisterStructureProcessors;
import net.frozenblock.trailiertales.tag.TrailierBiomeTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

public final class RegisterStructures {
	public static final ResourceKey<StructureSet> BADLANDS_FORTS_KEY = ofSet("badlands_fort");
	private static final ResourceKey<Structure> BADLANDS_FORT_KEY = createKey("badlands_fort");

	private RegisterStructures() {
		throw new UnsupportedOperationException("RegisterStructures contains only static declarations.");
	}

	@NotNull
	private static ResourceKey<StructureSet> ofSet(@NotNull String id) {
		return ResourceKey.create(Registries.STRUCTURE_SET, TrailierTalesSharedConstants.id(id));
	}

	public static void init() {
		RegisterStructureProcessors.init();
		BadlandsFortGenerator.init();
	}

	@NotNull
	private static ResourceKey<Structure> createKey(@NotNull String id) {
		return ResourceKey.create(Registries.STRUCTURE, TrailierTalesSharedConstants.id(id));
	}

	@NotNull
	private static Structure.StructureSettings structure(@NotNull HolderSet<Biome> holderSet, @NotNull Map<MobCategory, StructureSpawnOverride> spawns, @NotNull GenerationStep.Decoration featureStep, @NotNull TerrainAdjustment terrainAdaptation) {
		return new Structure.StructureSettings(holderSet, spawns, featureStep, terrainAdaptation);
	}

	@NotNull
	private static Structure.StructureSettings structure(@NotNull HolderSet<Biome> holderSet, @NotNull GenerationStep.Decoration featureStep, @NotNull TerrainAdjustment terrainAdaptation) {
		return structure(holderSet, Map.of(), featureStep, terrainAdaptation);
	}

	public static void bootstrapProcessor(@NotNull BootstrapContext<StructureProcessorList> context) {
		register(
			context,
			RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085,
			List.of(
				new RuleProcessor(
					List.of(
						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.SUSPICIOUS_GRAVEL, RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085_CHANCE),
							AlwaysTrueTest.INSTANCE, Blocks.GRAVEL.defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockMatchTest(Blocks.SUSPICIOUS_SAND, RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085_CHANCE),
							AlwaysTrueTest.INSTANCE, Blocks.SAND.defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockMatchTest(RegisterBlocks.SUSPICIOUS_RED_SAND, RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085_CHANCE),
							AlwaysTrueTest.INSTANCE, Blocks.RED_SAND.defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockMatchTest(RegisterBlocks.SUSPICIOUS_CLAY, RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085_CHANCE),
							AlwaysTrueTest.INSTANCE, Blocks.CLAY.defaultBlockState()
						),
						new ProcessorRule(
							new RandomBlockMatchTest(RegisterBlocks.SUSPICIOUS_DIRT, RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085_CHANCE),
							AlwaysTrueTest.INSTANCE, Blocks.DIRT.defaultBlockState()
						)
					)
				),
				new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)
			)
		);
	}

	public static void bootstrapTemplatePool(@NotNull BootstrapContext<StructureTemplatePool> context) {
		HolderGetter<StructureProcessorList> processor = context.lookup(Registries.PROCESSOR_LIST);
		HolderGetter<StructureTemplatePool> holderGetter2 = context.lookup(Registries.TEMPLATE_POOL);
		Holder<StructureTemplatePool> holder2 = holderGetter2.getOrThrow(Pools.EMPTY);

		context.register(
			BadlandsFortGenerator.BADLANDS_FORT,
			new StructureTemplatePool(
				holder2,
				List.of(
					Pair.of(BadlandsFortGenerator.ofProcessedSingle("badlands_fort/fort_1", processor.getOrThrow(RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085)), 1),
					Pair.of(BadlandsFortGenerator.ofProcessedSingle("badlands_fort/fort_2", processor.getOrThrow(RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085)), 1),
					Pair.of(BadlandsFortGenerator.ofProcessedSingle("badlands_fort/fort_3", processor.getOrThrow(RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085)), 1),
					Pair.of(BadlandsFortGenerator.ofProcessedSingle("badlands_fort/fort_4", processor.getOrThrow(RegisterStructureProcessors.SUSPICIOUS_BLOCK_TO_NORMAL_085)), 1)
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
				structure(
					holderGetter.getOrThrow(TrailierBiomeTags.HAS_BADLANDS_FORT),
					GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
					TerrainAdjustment.NONE
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
	private static Holder<StructureProcessorList> register(
		@NotNull BootstrapContext<StructureProcessorList> entries, @NotNull ResourceKey<StructureProcessorList> key, @NotNull List<StructureProcessor> list
	) {
		return entries.register(key, new StructureProcessorList(list));
	}
}
