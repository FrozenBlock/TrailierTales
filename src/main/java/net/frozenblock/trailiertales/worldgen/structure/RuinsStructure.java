package net.frozenblock.trailiertales.worldgen.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.function.Consumer;
import net.frozenblock.trailiertales.registry.RegisterStructureTypes;
import net.frozenblock.trailiertales.worldgen.structure.datagen.BadlandsRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.DesertRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.JungleRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.RuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.SavannaRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.piece.RuinsPieces;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

public class RuinsStructure extends Structure {
	public static final MapCodec<RuinsStructure> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
				settingsCodec(instance),
				RuinsStructure.Type.CODEC.fieldOf("biome_type").forGetter(feature -> feature.biomeType),
				Codec.floatRange(0F, 1F).fieldOf("cluster_probability").forGetter(feature -> feature.clusterProbability),
				UniformInt.CODEC.fieldOf("cluster_pieces").forGetter(feature -> feature.clusterPieces),
				Heightmap.Types.CODEC.lenientOptionalFieldOf("heightmap").forGetter(feature -> feature.heightmap),
				HeightProvider.CODEC.lenientOptionalFieldOf("height_provider").forGetter(feature -> feature.heightProvider)
			)
			.apply(instance, RuinsStructure::new)
	);
	public final RuinsStructure.Type biomeType;
	public final float clusterProbability;
	public final UniformInt clusterPieces;
	public final Optional<Heightmap.Types> heightmap;
	public final Optional<HeightProvider> heightProvider;
	public Optional<Integer> providedHeight = Optional.empty();

	private RuinsStructure(
		Structure.StructureSettings settings,
		RuinsStructure.Type biomeType,
		float clusterProbability,
		UniformInt clusterPieces,
		Optional<Heightmap.Types> heightmap,
		Optional<HeightProvider> heightProvider
	) {
		super(settings);
		this.biomeType = biomeType;
		this.clusterProbability = clusterProbability;
		this.clusterPieces = clusterPieces;
		this.heightmap = heightmap;
		this.heightProvider = heightProvider;
	}

	public RuinsStructure(
		Structure.StructureSettings settings,
		RuinsStructure.Type biomeType,
		float clusterProbability,
		UniformInt clusterPieces,
		Heightmap.Types heightmap
	) {
		this(settings, biomeType, clusterProbability, clusterPieces, Optional.of(heightmap), Optional.empty());
	}

	public RuinsStructure(
		Structure.StructureSettings settings,
		RuinsStructure.Type biomeType,
		float clusterProbability,
		UniformInt clusterPieces,
		HeightProvider heightProvider
	) {
		this(settings, biomeType, clusterProbability, clusterPieces, Optional.empty(), Optional.of(heightProvider));
	}

	public int getHeight(
		@NotNull ChunkGenerator chunkGenerator,
		BlockPos pos,
		LevelHeightAccessor heightAccessor,
		RandomState randomState,
		WorldgenRandom random
	) {
		Heightmap.Types heightmapType = this.heightmap.orElseGet(() -> this.heightProvider.isEmpty() ? Heightmap.Types.OCEAN_FLOOR_WG : null);
		if (heightmapType != null) {
			return chunkGenerator.getFirstOccupiedHeight(pos.getX(), pos.getZ(), heightmapType, heightAccessor, randomState);
		}
		WorldGenerationContext worldGenerationContext = new WorldGenerationContext(chunkGenerator, heightAccessor);
		int y = this.heightProvider.get().sample(random.forkPositional().at(pos), worldGenerationContext);
		this.providedHeight = Optional.of(y);
		return y;
	}

	@Override
	public @NotNull Optional<GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
		return getStub(context, collector -> this.generatePieces(collector, context));
	}

	private @NotNull Optional<Structure.GenerationStub> getStub(
		Structure.@NotNull GenerationContext context, Consumer<StructurePiecesBuilder> generator
	) {
		ChunkPos chunkPos = context.chunkPos();
		int x = chunkPos.getMiddleBlockX();
		int z = chunkPos.getMiddleBlockZ();
		int y = this.getHeight(context.chunkGenerator(), new BlockPos(x, 0, z), context.heightAccessor(), context.randomState(), context.random());
		return Optional.of(new Structure.GenerationStub(new BlockPos(x, y, z), generator));
	}

	private void generatePieces(StructurePiecesBuilder collector, Structure.@NotNull GenerationContext context) {
		BlockPos blockPos = new BlockPos(context.chunkPos().getMinBlockX(), 90, context.chunkPos().getMinBlockZ());
		Rotation rotation = Rotation.getRandom(context.random());
		RuinsPieces.addPieces(context.structureTemplateManager(), blockPos, rotation, collector, context.random(), this);
	}

	@Override
	public @NotNull StructureType<?> type() {
		return RegisterStructureTypes.RUINS;
	}

	public enum Type implements StringRepresentable {
		GENERIC("generic", RuinsGenerator.PROCESSORS),
		BADLANDS("badlands", BadlandsRuinsGenerator.PROCESSORS),
		DESERT("desert", DesertRuinsGenerator.PROCESSORS),
		JUNGLE("jungle", JungleRuinsGenerator.PROCESSORS),
		SAVANNA("savanna", SavannaRuinsGenerator.PROCESSORS),
		DEEPSLATE("deepslate", BadlandsRuinsGenerator.PROCESSORS),;

		public static final Codec<RuinsStructure.Type> CODEC = StringRepresentable.fromEnum(RuinsStructure.Type::values);
		private final String name;
		private final StructureProcessorList processors;

		Type(final String name, StructureProcessorList processors) {
			this.name = name;
			this.processors = processors;
		}

		public String getName() {
			return this.name;
		}

		public StructureProcessorList getProcessors() {
			return this.processors;
		}

		@Override
		public @NotNull String getSerializedName() {
			return this.name;
		}
	}
}
