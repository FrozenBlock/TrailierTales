package net.frozenblock.trailiertales.worldgen.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.frozenblock.trailiertales.registry.RegisterStructureTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import org.jetbrains.annotations.NotNull;

public class RuinsStructure extends Structure {
	public static final MapCodec<RuinsStructure> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
				settingsCodec(instance),
				RuinsStructure.Type.CODEC.fieldOf("biome_type").forGetter(feature -> feature.biomeType),
				Codec.floatRange(0F, 1F).fieldOf("cluster_probability").forGetter(feature -> feature.clusterProbability)
			)
			.apply(instance, RuinsStructure::new)
	);
	public final RuinsStructure.Type biomeType;
	public final float clusterProbability;

	public RuinsStructure(Structure.StructureSettings settings, RuinsStructure.Type biomeType, float clusterProbability) {
		super(settings);
		this.biomeType = biomeType;
		this.clusterProbability = clusterProbability;
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
		GENERIC("generic"),
		BADLANDS("badlands"),
		DESERT("desert"),
		JUNGLE("jungle"),
		SAVANNA("savanna");

		public static final Codec<RuinsStructure.Type> CODEC = StringRepresentable.fromEnum(RuinsStructure.Type::values);
		private final String name;

		Type(final String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		@Override
		public @NotNull String getSerializedName() {
			return this.name;
		}
	}
}
