package net.frozenblock.trailiertales.worldgen.structure;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.frozenblock.trailiertales.config.TTWorldgenConfig;
import net.frozenblock.trailiertales.registry.TTStructureTypes;
import net.frozenblock.trailiertales.worldgen.structure.datagen.BadlandsRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.DeepslateRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.DesertRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.GenericRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.JungleRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.SavannaRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.SmallTrailRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.SnowyRuinsGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class RuinsStructure extends Structure {
	public static final MapCodec<RuinsStructure> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
				settingsCodec(instance),
				RuinsStructure.Type.CODEC.optionalFieldOf("ruins_type", Type.GENERIC).forGetter(feature -> feature.ruinsType),
				Codec.floatRange(0F, 1F).fieldOf("cluster_probability").forGetter(feature -> feature.clusterProbability),
				UniformInt.CODEC.fieldOf("cluster_pieces").forGetter(feature -> feature.clusterPieces),
				Heightmap.Types.CODEC.lenientOptionalFieldOf("heightmap").forGetter(feature -> feature.heightmap),
				HeightProvider.CODEC.lenientOptionalFieldOf("height_provider").forGetter(feature -> feature.heightProvider)
			)
			.apply(instance, RuinsStructure::new)
	);
	public final RuinsStructure.Type ruinsType;
	public final float clusterProbability;
	public final UniformInt clusterPieces;
	public final Optional<Heightmap.Types> heightmap;
	public final Optional<HeightProvider> heightProvider;
	public Optional<Integer> providedHeight = Optional.empty();

	private RuinsStructure(
		Structure.StructureSettings settings,
		RuinsStructure.Type ruinsType,
		float clusterProbability,
		UniformInt clusterPieces,
		Optional<Heightmap.Types> heightmap,
		Optional<HeightProvider> heightProvider
	) {
		super(settings);
		this.ruinsType = ruinsType;
		this.clusterProbability = clusterProbability;
		this.clusterPieces = clusterPieces;
		this.heightmap = heightmap;
		this.heightProvider = heightProvider;
	}

	public RuinsStructure(
		Structure.StructureSettings settings,
		RuinsStructure.Type ruinsType,
		float clusterProbability,
		UniformInt clusterPieces,
		Heightmap.Types heightmap
	) {
		this(settings, ruinsType, clusterProbability, clusterPieces, Optional.of(heightmap), Optional.empty());
	}

	public RuinsStructure(
		Structure.StructureSettings settings,
		RuinsStructure.Type ruinsType,
		float clusterProbability,
		UniformInt clusterPieces,
		HeightProvider heightProvider
	) {
		this(settings, ruinsType, clusterProbability, clusterPieces, Optional.empty(), Optional.of(heightProvider));
	}

	public int getHeight(
		BlockPos pos,
		Structure.@NotNull GenerationContext context
	) {
		LevelHeightAccessor heightAccessor = context.heightAccessor();
		ChunkGenerator chunkGenerator = context.chunkGenerator();
		Heightmap.Types heightmapType = this.heightmap.orElseGet(() -> this.heightProvider.isEmpty() ? Heightmap.Types.OCEAN_FLOOR_WG : null);
		if (heightmapType != null) {
			return chunkGenerator.getFirstOccupiedHeight(pos.getX(), pos.getZ(), heightmapType, heightAccessor, context.randomState());
		}
		WorldGenerationContext worldGenerationContext = new WorldGenerationContext(chunkGenerator, heightAccessor);
		int y = this.heightProvider.get().sample(context.random().forkPositional().at(pos), worldGenerationContext);
		this.providedHeight = Optional.of(y);
		return y;
	}

	@Override
	public @NotNull Optional<GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
		if (this.ruinsType == Type.GENERIC && !TTWorldgenConfig.GENERATE_GENERIC_RUINS) return Optional.empty();
		if (this.ruinsType == Type.SNOWY && !TTWorldgenConfig.GENERATE_SNOWY_RUINS) return Optional.empty();
		if (this.ruinsType == Type.JUNGLE && !TTWorldgenConfig.GENERATE_JUNGLE_RUINS) return Optional.empty();
		if (this.ruinsType == Type.SAVANNA && !TTWorldgenConfig.GENERATE_SAVANNA_RUINS) return Optional.empty();
		if (this.ruinsType == Type.DESERT && !TTWorldgenConfig.GENERATE_DESERT_RUINS) return Optional.empty();
		if (this.ruinsType == Type.BADLANDS && !TTWorldgenConfig.GENERATE_BADLANDS_RUINS) return Optional.empty();
		if (this.ruinsType == Type.DEEPSLATE && !TTWorldgenConfig.GENERATE_DEEPSLATE_RUINS) return Optional.empty();

		ChunkPos chunkPos = context.chunkPos();
		int x = chunkPos.getMiddleBlockX();
		int z = chunkPos.getMiddleBlockZ();
		int y = this.getHeight(new BlockPos(x, 0, z), context);
		BlockPos startPos = new BlockPos(x, y, z);
		return Optional.of(new Structure.GenerationStub(startPos, this.generatePieces(startPos, context)));
	}

	@Contract(pure = true)
	private @NotNull Consumer<StructurePiecesBuilder> generatePieces(BlockPos startPos, Structure.@NotNull GenerationContext context) {
		return structurePiecesBuilder -> {
			List<RuinsPieces.RuinPiece> list = Lists.newArrayList();
			int x = startPos.getX();
			int y = startPos.getY();
			int z = startPos.getZ();
			LevelHeightAccessor heightAccessor = context.heightAccessor();
				BoundingBox box = new BoundingBox(
					x - 128,
					Math.max(y - 128, heightAccessor.getMinBuildHeight() + 7),
					z - 128,
					x + 128 + 1,
					Math.min(y + 128 + 1, heightAccessor.getMaxBuildHeight()),
					z + 128 + 1
				);
				RuinsPieces.addPieces(
					context.structureTemplateManager(),
					heightAccessor,
					startPos,
					Rotation.getRandom(context.random()),
					context.random(),
					this,
					list,
					box
				);
				list.forEach(structurePiecesBuilder::addPiece);
		};
	}

	@Override
	public @NotNull StructureType<?> type() {
		return TTStructureTypes.RUINS;
	}

	public static void onServerDataReload(@NotNull ResourceManager resourceManager) {
		Arrays.stream(Type.values()).toList().forEach(type -> type.getPieceHandler().onDataReload(resourceManager));
	}

	public enum Type implements StringRepresentable {
		GENERIC("generic", GenericRuinsGenerator.PROCESSORS, false),
		TRAIL("trail", SmallTrailRuinsGenerator.PROCESSORS, false),
		SNOWY("snowy", SnowyRuinsGenerator.PROCESSORS, false),
		BADLANDS("badlands", BadlandsRuinsGenerator.PROCESSORS, false),
		DESERT("desert", DesertRuinsGenerator.PROCESSORS, false),
		JUNGLE("jungle", JungleRuinsGenerator.PROCESSORS, false),
		SAVANNA("savanna", SavannaRuinsGenerator.PROCESSORS, false),
		DEEPSLATE("deepslate", DeepslateRuinsGenerator.PROCESSORS, true);

		public static final Codec<RuinsStructure.Type> CODEC = StringRepresentable.fromEnum(RuinsStructure.Type::values);
		private final String name;
		private final StructureProcessorList processors;
		private final boolean underground;
		private final RuinsPieceHandler pieceHandler;

		Type(final String name, StructureProcessorList processors, boolean underground) {
			this.name = name;
			this.processors = processors;
			this.underground = underground;
			this.pieceHandler = new RuinsPieceHandler(this);
		}

		public String getName() {
			return this.name;
		}

		public StructureProcessorList getProcessors() {
			return this.processors;
		}

		public boolean isUnderground() {
			return this.underground;
		}

		public RuinsPieceHandler getPieceHandler() {
			return this.pieceHandler;
		}

		@Override
		public @NotNull String getSerializedName() {
			return this.name;
		}
	}
}
