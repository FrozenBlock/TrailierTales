package net.frozenblock.trailiertales.worldgen.structure.piece;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.registry.RegisterStructurePieceTypes;
import net.frozenblock.trailiertales.worldgen.structure.RuinsStructure;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RuinsPieces {
	private static final Map<ResourceLocation, Integer> PIECE_OFFSETS = new Object2IntLinkedOpenHashMap<>();
	// GENERIC
	private static final ArrayList<ResourceLocation> GENERIC_SURFACE_PIECES = Lists.newArrayList();
	private static final ArrayList<ResourceLocation> GENERIC_MOSTLY_BURIED_PIECES = Lists.newArrayList();
	private static final ArrayList<ResourceLocation> GENERIC_BURIED_PIECES = Lists.newArrayList();
	private static final ArrayList<ResourceLocation> GENERIC_FIVE_FROM_TOP_PIECES = Lists.newArrayList();
	// SAVANNA
	private static final ArrayList<ResourceLocation> SAVANNA_SURFACE_PIECES = Lists.newArrayList();
	private static final ArrayList<ResourceLocation> SAVANNA_MOSTLY_BURIED_PIECES = Lists.newArrayList();
	private static final ArrayList<ResourceLocation> SAVANNA_BURIED_PIECES = Lists.newArrayList();

	public static void reloadPiecesFromDirectories(@NotNull ResourceManager resourceManager) {
		clearPieceLists();

		GENERIC_SURFACE_PIECES.addAll(getLoadedPieces(resourceManager, TrailierConstants.MOD_ID, createGenericRuinPath("surface")));
		GENERIC_MOSTLY_BURIED_PIECES.addAll(getLoadedPieces(resourceManager, TrailierConstants.MOD_ID, createGenericRuinPath("mostly_buried")));
		GENERIC_BURIED_PIECES .addAll(getLoadedPieces(resourceManager, TrailierConstants.MOD_ID, createGenericRuinPath("buried")));
		GENERIC_FIVE_FROM_TOP_PIECES.addAll(getLoadedPieces(resourceManager, TrailierConstants.MOD_ID, createGenericRuinPath("five_from_top")));

		SAVANNA_SURFACE_PIECES.addAll(getLoadedPieces(resourceManager, TrailierConstants.MOD_ID, createSavannaRuinPath("surface")));
		SAVANNA_MOSTLY_BURIED_PIECES.addAll(getLoadedPieces(resourceManager, TrailierConstants.MOD_ID, createSavannaRuinPath("mostly_buried")));
		SAVANNA_BURIED_PIECES.addAll(getLoadedPieces(resourceManager, TrailierConstants.MOD_ID, createSavannaRuinPath("buried")));
		
		fillInPieceOffsets();
	}

	private static void clearPieceLists() {
		GENERIC_SURFACE_PIECES.clear();
		GENERIC_MOSTLY_BURIED_PIECES.clear();
		GENERIC_BURIED_PIECES.clear();
		GENERIC_FIVE_FROM_TOP_PIECES.clear();
		SAVANNA_SURFACE_PIECES.clear();
		SAVANNA_MOSTLY_BURIED_PIECES.clear();
		SAVANNA_BURIED_PIECES.clear();
	}

	private static void fillInPieceOffsets() {
		PIECE_OFFSETS.clear();
		GENERIC_MOSTLY_BURIED_PIECES.forEach(resourceLocation -> PIECE_OFFSETS.put(resourceLocation, 3));
		GENERIC_BURIED_PIECES.forEach(resourceLocation -> PIECE_OFFSETS.put(resourceLocation, -2));
		GENERIC_FIVE_FROM_TOP_PIECES.forEach(resourceLocation -> PIECE_OFFSETS.put(resourceLocation, 5));
		SAVANNA_MOSTLY_BURIED_PIECES.forEach(resourceLocation -> PIECE_OFFSETS.put(resourceLocation, 3));
		SAVANNA_BURIED_PIECES.forEach(resourceLocation -> PIECE_OFFSETS.put(resourceLocation, -2));
	}

	private static @NotNull List<ResourceLocation> getLoadedPieces(@NotNull ResourceManager resourceManager, String namespace, String path) {
		Set<ResourceLocation> foundPieces = resourceManager.listResources(
			"structure/" + path,
			resourceLocation -> resourceLocation.getPath().endsWith(".nbt") && resourceLocation.getNamespace().equals(namespace)
		).keySet();
		ArrayList<ResourceLocation> convertedLocations = new ArrayList<>();
		foundPieces.forEach(resourceLocation -> {
			String newPath = resourceLocation.getPath();
			newPath = newPath.replace(".nbt", "");
			newPath = newPath.replace("structure/", "");
			convertedLocations.add(ResourceLocation.tryBuild(resourceLocation.getNamespace(), newPath));
		});
		return convertedLocations;
	}

	@Contract("_ -> new")
	private static @NotNull String createGenericRuinPath(String path) {
		return "ruins/generic/" + path;
	}

	@Contract("_ -> new")
	private static @NotNull String createSavannaRuinPath(String path) {
		return "ruins/savanna/" + path;
	}

	private static @NotNull ResourceLocation getRandomGenericRuin(@NotNull RandomSource random) {
		if (random.nextFloat() <= 0.75F) {
			return Util.getRandom(GENERIC_MOSTLY_BURIED_PIECES, random);
		}
		if (random.nextFloat() <= 0.05F) {
			return Util.getRandom(GENERIC_FIVE_FROM_TOP_PIECES, random);
		}
		return random.nextBoolean() ? Util.getRandom(GENERIC_SURFACE_PIECES, random) : Util.getRandom(GENERIC_BURIED_PIECES, random);
	}

	private static @NotNull ResourceLocation getRandomSavannaRuin(@NotNull RandomSource random) {
		if (random.nextFloat() <= 0.75F) {
			return Util.getRandom(SAVANNA_MOSTLY_BURIED_PIECES, random);
		}
		if (random.nextFloat() <= 0.175F) {
			return Util.getRandom(SAVANNA_SURFACE_PIECES, random);
		}
		return Util.getRandom(SAVANNA_BURIED_PIECES, random);
	}

	public static void addPieces(
		StructureTemplateManager structureTemplateManager,
		BlockPos pos,
		Rotation rotation,
		StructurePieceAccessor pieces,
		@NotNull RandomSource random,
		@NotNull RuinsStructure feature
	) {
		RuinPiece ruinPiece = addStarterPiece(structureTemplateManager, pos, rotation, pieces, random, feature);
		if (random.nextFloat() <= feature.clusterProbability) {
			addClusterRuins(structureTemplateManager, ruinPiece.getBoundingBox(), random, pos, feature, pieces);
		}
	}

	private static void addClusterRuins(
		StructureTemplateManager structureTemplateManager,
		BoundingBox boundingBox,
		RandomSource random,
		@NotNull BlockPos pos,
		@NotNull RuinsStructure feature,
		StructurePieceAccessor pieces
	) {
		List<BoundingBox> boundingBoxes = new ArrayList<>();
		boundingBoxes.add(boundingBox);
		int totalPieces = feature.clusterPieces.sample(random);
		for (int pieceNumber = 0; pieceNumber < totalPieces; pieceNumber++) {
			Rotation newRotation = Rotation.getRandom(random);
			addPiece(structureTemplateManager, boundingBoxes, pos, newRotation, pieces, random, feature, 2);
		}
	}

	private static @Nullable RuinPiece addPiece(
		StructureTemplateManager structureTemplateManager,
		List<BoundingBox> boundingBoxes,
		BlockPos pos,
		Rotation rotation,
		StructurePieceAccessor pieces,
		RandomSource random,
		@NotNull RuinsStructure feature,
		int maxAttempts
	) {
		RuinPiece ruinPiece = null;
		maxAttempts += 1;

		RuinPiece potentialPiece;
		ResourceLocation structureId;
		if (feature.biomeType == RuinsStructure.Type.GENERIC) {
			structureId = getRandomGenericRuin(random);
		} else if (feature.biomeType == RuinsStructure.Type.SAVANNA) {
			structureId = getRandomSavannaRuin(random);
		} else {
			structureId = getRandomGenericRuin(random);
		}
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();

		for (int i = 0; i < maxAttempts; i++) {
			potentialPiece = new RuinPiece(structureTemplateManager, structureId, mutableBlockPos, rotation, feature.biomeType);
			BoundingBox currentBox = potentialPiece.getBoundingBox();
			AtomicReference<BoundingBox> inflatedBox = new AtomicReference<>(currentBox.inflatedBy(2));

			AtomicBoolean intersected = new AtomicBoolean(false);
			boundingBoxes.forEach(boundingBox -> {
				if (boundingBox.intersects(inflatedBox.get())) {
					intersected.set(true);
					BlockPos center = boundingBox.getCenter();
					int xDirection = random.nextBoolean() ? 1 : -1;
					int zDirection = random.nextBoolean() ? 1 : -1;
					int xStep = (int) ((boundingBox.getXSpan() * 0.5D) + (currentBox.getXSpan() * 0.5D) + random.nextInt(2, 16));
					int zStep = (int) ((boundingBox.getZSpan() * 0.5D) + (currentBox.getZSpan() * 0.5D) + random.nextInt(2, 16));
					mutableBlockPos.set(center).move(xStep * xDirection, 0, zStep * zDirection);
					inflatedBox.set(inflatedBox.get().moved(xStep * xDirection, 0, zStep * zDirection));
				}
			});

			if (!intersected.get()) {
				ruinPiece = potentialPiece;
				pieces.addPiece(ruinPiece);
				break;
			}
		}

		return ruinPiece;
	}

	private static @NotNull RuinPiece addStarterPiece(
		StructureTemplateManager structureTemplateManager,
		BlockPos pos,
		Rotation rotation,
		StructurePieceAccessor pieces,
		RandomSource random,
		@NotNull RuinsStructure feature
	) {
		ResourceLocation structureId;
		if (feature.biomeType == RuinsStructure.Type.GENERIC) {
			structureId = getRandomGenericRuin(random);
		} else {
			structureId = getRandomGenericRuin(random);
		}
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
		RuinPiece piece = new RuinPiece(structureTemplateManager, structureId, mutableBlockPos, rotation, feature.biomeType);
		pieces.addPiece(piece);
		return piece;
	}

	public static class RuinPiece extends TemplateStructurePiece {
		private final RuinsStructure.Type biomeType;

		public RuinPiece(
			StructureTemplateManager structureTemplateManager,
			ResourceLocation structureId,
			BlockPos pos,
			Rotation rotation,
			RuinsStructure.Type biomeType
		) {
			super(RegisterStructurePieceTypes.RUIN, 0, structureTemplateManager, structureId, structureId.toString(), makeSettings(rotation, biomeType), pos);
			this.biomeType = biomeType;
		}

		private RuinPiece(
			StructureTemplateManager templateManager, CompoundTag nbt, Rotation rotation, RuinsStructure.Type biomeType
		) {
			super(RegisterStructurePieceTypes.RUIN, nbt, templateManager, id -> makeSettings(rotation, biomeType));
			this.biomeType = biomeType;
		}

		private static @NotNull StructurePlaceSettings makeSettings(Rotation rotation, RuinsStructure.@NotNull Type type) {
			StructurePlaceSettings placeSettings = new StructurePlaceSettings()
				.setRotation(rotation)
				.setMirror(Mirror.NONE)
				.addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);

			type.getProcessors().list().forEach(placeSettings::addProcessor);
			return placeSettings;
		}

		public static @NotNull RuinPiece create(StructureTemplateManager templateManager, @NotNull CompoundTag nbt) {
			Rotation rotation = Rotation.valueOf(nbt.getString("Rot"));
			RuinsStructure.Type type = RuinsStructure.Type.valueOf(nbt.getString("BiomeType"));
			return new RuinPiece(templateManager, nbt, rotation, type);
		}

		@Override
		protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag nbt) {
			super.addAdditionalSaveData(context, nbt);
			nbt.putString("Rot", this.placeSettings.getRotation().name());
			nbt.putString("BiomeType", this.biomeType.toString());
		}

		@Override
		protected void handleDataMarker(String metadata, BlockPos pos, ServerLevelAccessor world, RandomSource random, BoundingBox boundingBox) {
		}

		@Override
		public void postProcess(
			@NotNull WorldGenLevel world,
			StructureManager structureManager,
			ChunkGenerator chunkGenerator,
			RandomSource random,
			BoundingBox boundingBox,
			ChunkPos chunkPos,
			BlockPos pos
		) {
			int i = world.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, this.templatePosition.getX(), this.templatePosition.getZ());
			this.templatePosition = new BlockPos(this.templatePosition.getX(), i, this.templatePosition.getZ());
			BlockPos blockPos = StructureTemplate.transform(
					new BlockPos(this.template.getSize().getX() - 1, 0, this.template.getSize().getZ() - 1), Mirror.NONE, this.placeSettings.getRotation(), BlockPos.ZERO
				)
				.offset(this.templatePosition);
			this.templatePosition = new BlockPos(this.templatePosition.getX(), this.getHeight(this.templatePosition, world, blockPos), this.templatePosition.getZ());
			Integer offset = PIECE_OFFSETS.computeIfPresent(this.makeTemplateLocation(), (resourceLocation, integer) -> -this.getBoundingBox().getYSpan() + integer);
			if (offset == null) {
				offset = -1;
			}

			this.templatePosition = this.templatePosition.relative(Direction.Axis.Y, offset);
			super.postProcess(world, structureManager, chunkGenerator, random, boundingBox, chunkPos, pos);
		}

		private int getHeight(@NotNull BlockPos start, BlockGetter world, BlockPos end) {
			int i = start.getY();
			int j = 512;
			int k = i - 1;
			int l = 0;

			for (BlockPos blockPos : BlockPos.betweenClosed(start, end)) {
				int m = blockPos.getX();
				int n = blockPos.getZ();
				int o = start.getY() - 1;
				BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(m, o, n);
				BlockState blockState = world.getBlockState(mutableBlockPos);

				for (FluidState fluidState = world.getFluidState(mutableBlockPos);
					 (blockState.isAir() || fluidState.is(FluidTags.WATER) || blockState.is(BlockTags.ICE)) && o > world.getMinBuildHeight() + 1;
					 fluidState = world.getFluidState(mutableBlockPos)
				) {
					mutableBlockPos.set(m, --o, n);
					blockState = world.getBlockState(mutableBlockPos);
				}

				j = Math.min(j, o);
				if (o < k - 2) {
					l++;
				}
			}

			int p = Math.abs(start.getX() - end.getX());
			if (k - j > 2 && l > p - 2) {
				i = j + 1;
			}

			return i;
		}
	}
}
