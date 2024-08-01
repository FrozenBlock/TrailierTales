package net.frozenblock.trailiertales.worldgen.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.frozenblock.trailiertales.registry.RegisterStructureTypes;
import net.frozenblock.trailiertales.worldgen.structure.datagen.RuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.datagen.SavannaRuinsGenerator;
import net.frozenblock.trailiertales.worldgen.structure.piece.RuinsPieces;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
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
				UniformInt.CODEC.fieldOf("cluster_pieces").forGetter(feature -> feature.clusterPieces)
			)
			.apply(instance, RuinsStructure::new)
	);
	public final RuinsStructure.Type biomeType;
	public final float clusterProbability;
	public final UniformInt clusterPieces;

	public RuinsStructure(Structure.StructureSettings settings, RuinsStructure.Type biomeType, float clusterProbability, UniformInt clusterPieces) {
		super(settings);
		this.biomeType = biomeType;
		this.clusterProbability = clusterProbability;
		this.clusterPieces = clusterPieces;
	}

	@Override
	public @NotNull Optional<GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
		return onTopOfChunkCenter(context, Heightmap.Types.OCEAN_FLOOR_WG, collector -> this.generatePieces(collector, context));
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
		BADLANDS("badlands", RuinsGenerator.PROCESSORS),
		DESERT("desert", RuinsGenerator.PROCESSORS),
		JUNGLE("jungle", RuinsGenerator.PROCESSORS),
		SAVANNA("savanna", SavannaRuinsGenerator.PROCESSORS);

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
