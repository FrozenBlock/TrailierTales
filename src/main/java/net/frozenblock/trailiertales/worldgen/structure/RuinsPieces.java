package net.frozenblock.trailiertales.worldgen.structure;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.registry.TTStructurePieceTypes;
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
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
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
	// JUNGLE
	private static final ArrayList<ResourceLocation> JUNGLE_SURFACE_PIECES = Lists.newArrayList();
	private static final ArrayList<ResourceLocation> JUNGLE_MOSTLY_BURIED_PIECES = Lists.newArrayList();
	private static final ArrayList<ResourceLocation> JUNGLE_BURIED_PIECES = Lists.newArrayList();
	private static final ArrayList<ResourceLocation> JUNGLE_SEVEN_FROM_TOP_PIECES = Lists.newArrayList();
	// DESERT
	private static final ArrayList<ResourceLocation> DESERT_SURFACE_PIECES = Lists.newArrayList();
	private static final ArrayList<ResourceLocation> DESERT_MOSTLY_BURIED_PIECES = Lists.newArrayList();
	private static final ArrayList<ResourceLocation> DESERT_BURIED_PIECES = Lists.newArrayList();
	private static final ArrayList<ResourceLocation> DESERT_FOUR_FROM_TOP_PIECES = Lists.newArrayList();
	private static final ArrayList<ResourceLocation> DESERT_SIX_FROM_TOP_PIECES = Lists.newArrayList();
	// BADLANDS
	private static final ArrayList<ResourceLocation> BADLANDS_SURFACE_PIECES = Lists.newArrayList();
	private static final ArrayList<ResourceLocation> BADLANDS_MOSTLY_BURIED_PIECES = Lists.newArrayList();
	private static final ArrayList<ResourceLocation> BADLANDS_BURIED_PIECES = Lists.newArrayList();
	private static final ArrayList<ResourceLocation> BADLANDS_FIVE_FROM_TOP_PIECES = Lists.newArrayList();
	// DEEPSLATE
	private static final ArrayList<ResourceLocation> DEEPSLATE_PIECES = Lists.newArrayList();

	public static void reloadPiecesFromDirectories(@NotNull ResourceManager resourceManager) {
		clearPieceLists();

		GENERIC_SURFACE_PIECES.addAll(getLoadedPieces(resourceManager, TTConstants.MOD_ID, createGenericRuinPath("surface")));
		GENERIC_MOSTLY_BURIED_PIECES.addAll(getLoadedPieces(resourceManager, TTConstants.MOD_ID, createGenericRuinPath("mostly_buried")));
		GENERIC_BURIED_PIECES.addAll(getLoadedPieces(resourceManager, TTConstants.MOD_ID, createGenericRuinPath("buried")));
		GENERIC_FIVE_FROM_TOP_PIECES.addAll(getLoadedPieces(resourceManager, TTConstants.MOD_ID, createGenericRuinPath("five_from_top")));

		SAVANNA_SURFACE_PIECES.addAll(getLoadedPieces(resourceManager, TTConstants.MOD_ID, createSavannaRuinPath("surface")));
		SAVANNA_MOSTLY_BURIED_PIECES.addAll(getLoadedPieces(resourceManager, TTConstants.MOD_ID, createSavannaRuinPath("mostly_buried")));
		SAVANNA_BURIED_PIECES.addAll(getLoadedPieces(resourceManager, TTConstants.MOD_ID, createSavannaRuinPath("buried")));

		JUNGLE_SURFACE_PIECES.addAll(getLoadedPieces(resourceManager, TTConstants.MOD_ID, createJungleRuinPath("surface")));
		JUNGLE_MOSTLY_BURIED_PIECES.addAll(getLoadedPieces(resourceManager, TTConstants.MOD_ID, createJungleRuinPath("mostly_buried")));
		JUNGLE_BURIED_PIECES.addAll(getLoadedPieces(resourceManager, TTConstants.MOD_ID, createJungleRuinPath("buried")));
		JUNGLE_SEVEN_FROM_TOP_PIECES.addAll(getLoadedPieces(resourceManager, TTConstants.MOD_ID, createJungleRuinPath("seven_from_top")));

		DESERT_SURFACE_PIECES.addAll(getLoadedPieces(resourceManager, TTConstants.MOD_ID, createDesertRuinPath("surface")));
		DESERT_MOSTLY_BURIED_PIECES.addAll(getLoadedPieces(resourceManager, TTConstants.MOD_ID, createDesertRuinPath("mostly_buried")));
		DESERT_BURIED_PIECES.addAll(getLoadedPieces(resourceManager, TTConstants.MOD_ID, createDesertRuinPath("buried")));
		DESERT_FOUR_FROM_TOP_PIECES.addAll(getLoadedPieces(resourceManager, TTConstants.MOD_ID, createDesertRuinPath("four_from_top")));
		DESERT_SIX_FROM_TOP_PIECES.addAll(getLoadedPieces(resourceManager, TTConstants.MOD_ID, createDesertRuinPath("six_from_top")));

		BADLANDS_SURFACE_PIECES.addAll(getLoadedPieces(resourceManager, TTConstants.MOD_ID, createBadlandsRuinPath("surface")));
		BADLANDS_MOSTLY_BURIED_PIECES.addAll(getLoadedPieces(resourceManager, TTConstants.MOD_ID, createBadlandsRuinPath("mostly_buried")));
		BADLANDS_BURIED_PIECES.addAll(getLoadedPieces(resourceManager, TTConstants.MOD_ID, createBadlandsRuinPath("buried")));
		BADLANDS_FIVE_FROM_TOP_PIECES.addAll(getLoadedPieces(resourceManager, TTConstants.MOD_ID, createBadlandsRuinPath("five_from_top")));

		DEEPSLATE_PIECES.addAll(getLoadedPieces(resourceManager, TTConstants.MOD_ID, "ruins/deepslate"));

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
		JUNGLE_SURFACE_PIECES.clear();
		JUNGLE_MOSTLY_BURIED_PIECES.clear();
		JUNGLE_BURIED_PIECES.clear();
		JUNGLE_SEVEN_FROM_TOP_PIECES.clear();
		DESERT_SURFACE_PIECES.clear();
		DESERT_MOSTLY_BURIED_PIECES.clear();
		DESERT_BURIED_PIECES.clear();
		DESERT_FOUR_FROM_TOP_PIECES.clear();
		DESERT_SIX_FROM_TOP_PIECES.clear();
		BADLANDS_SURFACE_PIECES.clear();
		BADLANDS_MOSTLY_BURIED_PIECES.clear();
		BADLANDS_BURIED_PIECES.clear();
		BADLANDS_FIVE_FROM_TOP_PIECES.clear();
		DEEPSLATE_PIECES.clear();
	}

	private static void fillInPieceOffsets() {
		PIECE_OFFSETS.clear();
		GENERIC_MOSTLY_BURIED_PIECES.forEach(resourceLocation -> PIECE_OFFSETS.put(resourceLocation, 3));
		GENERIC_BURIED_PIECES.forEach(resourceLocation -> PIECE_OFFSETS.put(resourceLocation, 0));
		GENERIC_FIVE_FROM_TOP_PIECES.forEach(resourceLocation -> PIECE_OFFSETS.put(resourceLocation, 5));
		SAVANNA_MOSTLY_BURIED_PIECES.forEach(resourceLocation -> PIECE_OFFSETS.put(resourceLocation, 3));
		SAVANNA_BURIED_PIECES.forEach(resourceLocation -> PIECE_OFFSETS.put(resourceLocation, 0));
		JUNGLE_MOSTLY_BURIED_PIECES.forEach(resourceLocation -> PIECE_OFFSETS.put(resourceLocation, 3));
		JUNGLE_BURIED_PIECES.forEach(resourceLocation -> PIECE_OFFSETS.put(resourceLocation, 0));
		JUNGLE_SEVEN_FROM_TOP_PIECES.forEach(resourceLocation -> PIECE_OFFSETS.put(resourceLocation, 7));
		DESERT_MOSTLY_BURIED_PIECES.forEach(resourceLocation -> PIECE_OFFSETS.put(resourceLocation, 3));
		DESERT_BURIED_PIECES.forEach(resourceLocation -> PIECE_OFFSETS.put(resourceLocation, 0));
		DESERT_FOUR_FROM_TOP_PIECES.forEach(resourceLocation -> PIECE_OFFSETS.put(resourceLocation, 4));
		DESERT_SIX_FROM_TOP_PIECES.forEach(resourceLocation -> PIECE_OFFSETS.put(resourceLocation, 6));
		BADLANDS_MOSTLY_BURIED_PIECES.forEach(resourceLocation -> PIECE_OFFSETS.put(resourceLocation, 3));
		BADLANDS_BURIED_PIECES.forEach(resourceLocation -> PIECE_OFFSETS.put(resourceLocation, 0));
		BADLANDS_FIVE_FROM_TOP_PIECES.forEach(resourceLocation -> PIECE_OFFSETS.put(resourceLocation, 5));
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

	@Contract("_ -> new")
	private static @NotNull String createJungleRuinPath(String path) {
		return "ruins/jungle/" + path;
	}

	@Contract("_ -> new")
	private static @NotNull String createDesertRuinPath(String path) {
		return "ruins/desert/" + path;
	}

	@Contract("_ -> new")
	private static @NotNull String createBadlandsRuinPath(String path) {
		return "ruins/badlands/" + path;
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

	private static @NotNull ResourceLocation getRandomJungleRuin(@NotNull RandomSource random) {
		if (random.nextFloat() <= 0.75F) {
			return Util.getRandom(JUNGLE_MOSTLY_BURIED_PIECES, random);
		}
		if (random.nextFloat() <= 0.05F) {
			return Util.getRandom(JUNGLE_SEVEN_FROM_TOP_PIECES, random);
		}
		if (random.nextFloat() <= 0.75F) {
			return Util.getRandom(JUNGLE_SURFACE_PIECES, random);
		}
		return Util.getRandom(JUNGLE_BURIED_PIECES, random);
	}

	private static @NotNull ResourceLocation getRandomDesertRuin(@NotNull RandomSource random) {
		if (random.nextFloat() <= 0.65F) {
			return Util.getRandom(DESERT_SURFACE_PIECES, random);
		}
		if (random.nextFloat() <= 0.35F) {
			return random.nextBoolean() ? Util.getRandom(DESERT_SIX_FROM_TOP_PIECES, random) : Util.getRandom(DESERT_MOSTLY_BURIED_PIECES, random);
		}
		if (random.nextFloat() <= 0.75F) {
			return Util.getRandom(DESERT_MOSTLY_BURIED_PIECES, random);
		}
		return Util.getRandom(DESERT_BURIED_PIECES, random);
	}

	private static @NotNull ResourceLocation getRandomBadlandsRuin(@NotNull RandomSource random) {
		if (random.nextFloat() <= 0.8F) {
			return Util.getRandom(BADLANDS_MOSTLY_BURIED_PIECES, random);
		}
		if (random.nextFloat() <= 0.3F) {
			return Util.getRandom(BADLANDS_SURFACE_PIECES, random);
		}
		if (random.nextFloat() <= 0.075F) {
			return Util.getRandom(BADLANDS_FIVE_FROM_TOP_PIECES, random);
		}
		return Util.getRandom(BADLANDS_BURIED_PIECES, random);
	}

	private static @NotNull ResourceLocation getRandomDeepslateRuin(@NotNull RandomSource random) {
		return Util.getRandom(DEEPSLATE_PIECES, random);
	}

	public static void addPieces(
		StructureTemplateManager structureTemplateManager,
		LevelHeightAccessor heightAccessor,
		BlockPos pos,
		Rotation rotation,
		@NotNull RandomSource random,
		@NotNull RuinsStructure feature,
		List<RuinsPieces.RuinPiece> pieces,
		BoundingBox box
	) {
		RuinPiece ruinPiece = addStarterPiece(structureTemplateManager, pos, rotation, pieces, random, feature);
		if (random.nextFloat() <= feature.clusterProbability) {
			addClusterRuins(structureTemplateManager, ruinPiece.getBoundingBox(), random, pos, feature, pieces, box);
		}
	}

	private static void addClusterRuins(
		StructureTemplateManager structureTemplateManager,
		BoundingBox boundingBox,
		RandomSource random,
		@NotNull BlockPos pos,
		@NotNull RuinsStructure feature,
		@NotNull List<RuinsPieces.RuinPiece> pieces,
		BoundingBox box
	) {
		ObjectArrayList<BoundingBox> boundingBoxes = new ObjectArrayList<>();
		boundingBoxes.add(boundingBox);
		int totalPieces = feature.clusterPieces.sample(random);
		for (int pieceNumber = 0; pieceNumber < totalPieces; pieceNumber++) {
			Rotation newRotation = Rotation.getRandom(random);
			addPiece(structureTemplateManager, boundingBoxes, pos, newRotation, pieces, random, feature, box, 7);
		}
	}

	private static @Nullable RuinPiece addPiece(
		StructureTemplateManager structureTemplateManager,
		ObjectArrayList<BoundingBox> boundingBoxes,
		@NotNull BlockPos pos,
		Rotation rotation,
		@NotNull List<RuinsPieces.RuinPiece> pieces,
		RandomSource random,
		@NotNull RuinsStructure feature,
		BoundingBox box,
		int maxAttempts
	) {
		RuinPiece ruinPiece = null;
		maxAttempts += 1;

		RuinPiece potentialPiece;
		ResourceLocation structureId = getPieceForType(feature.biomeType, random);
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();

		for (int i = 0; i < maxAttempts; i++) {
			potentialPiece = new RuinPiece(
				structureTemplateManager,
				structureId,
				mutableBlockPos,
				rotation,
				feature.biomeType,
				feature.heightmap,
				feature.providedHeight
			);
			BoundingBox currentBox = potentialPiece.getBoundingBox();
			AtomicReference<BoundingBox> inflatedBox = new AtomicReference<>(currentBox.inflatedBy(2));
			List<BoundingBox> shuffledBoxes = Util.shuffledCopy(boundingBoxes, random);
			AtomicBoolean intersected = new AtomicBoolean(false);

			shuffledBoxes.forEach(boundingBox -> {
				AtomicBoolean withinBox = new AtomicBoolean(true);
				inflatedBox.get().inflatedBy(-2).forAllCorners(
					cornerPos -> {
						if (!box.isInside(cornerPos)) {
							withinBox.set(false);
						}
					}
				);

				if (boundingBox.intersects(inflatedBox.get()) || !withinBox.get()) {
					intersected.set(true);
					BlockPos center = boundingBox.getCenter();
					boolean useXSpan = random.nextBoolean();
					boolean useZSpan = !useXSpan || random.nextBoolean();
					int xDirection = random.nextBoolean() ? 1 : -1;
					int zDirection = random.nextBoolean() ? 1 : -1;
					double xSpanScale = useXSpan ? 0.5D : 0.1D;
					double zSpanScale = useZSpan ? 0.5D : 0.1D;
					int xStep = (int) ((boundingBox.getXSpan() * xSpanScale) + (currentBox.getXSpan() * xSpanScale) + random.nextInt(0, 6));
					int zStep = (int) ((boundingBox.getZSpan() * zSpanScale) + (currentBox.getZSpan() * zSpanScale) + random.nextInt(0, 6));
					mutableBlockPos.set(center).move(xStep * xDirection, 0, zStep * zDirection);
					inflatedBox.set(inflatedBox.get().moved(xStep * xDirection, 0, zStep * zDirection));
				}
			});

			if (!intersected.get()) {
				ruinPiece = potentialPiece;
				pieces.add(ruinPiece);
				boundingBoxes.add(ruinPiece.getBoundingBox());
				break;
			}
		}

		return ruinPiece;
	}

	private static @NotNull RuinPiece addStarterPiece(
		StructureTemplateManager structureTemplateManager,
		@NotNull BlockPos pos,
		Rotation rotation,
		@NotNull List<RuinsPieces.RuinPiece> pieces,
		RandomSource random,
		@NotNull RuinsStructure feature
	) {
		ResourceLocation structureId = getPieceForType(feature.biomeType, random);
		RuinPiece piece = new RuinPiece(
			structureTemplateManager,
			structureId,
			pos,
			rotation,
			feature.biomeType,
			feature.heightmap,
			feature.providedHeight
		);
		pieces.add(piece);
		return piece;
	}

	private static ResourceLocation getPieceForType(RuinsStructure.Type type, RandomSource random) {
		if (type == RuinsStructure.Type.SAVANNA) {
			return getRandomSavannaRuin(random);
		}
		if (type == RuinsStructure.Type.JUNGLE) {
			return getRandomJungleRuin(random);
		}
		if (type == RuinsStructure.Type.DESERT) {
			return getRandomDesertRuin(random);
		}
		if (type == RuinsStructure.Type.BADLANDS) {
			return getRandomBadlandsRuin(random);
		}
		if (type == RuinsStructure.Type.DEEPSLATE) {
			return getRandomDeepslateRuin(random);
		}
		return getRandomGenericRuin(random);
	}

	public static class RuinPiece extends TemplateStructurePiece {
		private final RuinsStructure.Type biomeType;
		public final Optional<Heightmap.Types> heightmap;
		private Optional<Integer> providedHeight;
		private boolean adjustedHeight;

		public RuinPiece(
			StructureTemplateManager structureTemplateManager,
			ResourceLocation structureId,
			BlockPos pos,
			Rotation rotation,
			RuinsStructure.Type biomeType, Optional<Heightmap.Types> heightmap,
			Optional<Integer> providedHeight
		) {
			super(TTStructurePieceTypes.RUIN, 0, structureTemplateManager, structureId, structureId.toString(), makeSettings(rotation, biomeType), pos);
			this.biomeType = biomeType;
			this.heightmap = heightmap;
			this.providedHeight = providedHeight;
		}

		public RuinPiece(
			StructureTemplateManager structureTemplateManager,
			ResourceLocation structureId,
			BlockPos pos,
			Rotation rotation,
			RuinsStructure.Type biomeType, Optional<Heightmap.Types> heightmap,
			Optional<Integer> providedHeight,
			boolean adjustedHeight
		) {
			super(TTStructurePieceTypes.RUIN, 0, structureTemplateManager, structureId, structureId.toString(), makeSettings(rotation, biomeType), pos);
			this.biomeType = biomeType;
			this.heightmap = heightmap;
			this.providedHeight = providedHeight;
			this.adjustedHeight = adjustedHeight;
		}

		private RuinPiece(
			StructureTemplateManager templateManager,
			CompoundTag nbt, Rotation rotation,
			RuinsStructure.Type biomeType,
			Optional<Heightmap.Types> heightmap,
			Optional<Integer> providedHeight
		) {
			super(TTStructurePieceTypes.RUIN, nbt, templateManager, id -> makeSettings(rotation, biomeType));
			this.biomeType = biomeType;
			this.heightmap = heightmap;
			this.providedHeight = providedHeight;
		}

		private RuinPiece(
			StructureTemplateManager templateManager,
			CompoundTag nbt, Rotation rotation,
			RuinsStructure.Type biomeType,
			Optional<Heightmap.Types> heightmap,
			Optional<Integer> providedHeight,
			boolean adjustedHeight
		) {
			super(TTStructurePieceTypes.RUIN, nbt, templateManager, id -> makeSettings(rotation, biomeType));
			this.biomeType = biomeType;
			this.heightmap = heightmap;
			this.providedHeight = providedHeight;
			this.adjustedHeight = adjustedHeight;
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
			Heightmap.Types heightmap = null;
			if (nbt.contains("HeightmapType")) {
				heightmap = Heightmap.Types.valueOf(nbt.getString("HeightmapType"));
			}
			Integer providedHeight = null;
			if (nbt.contains("ProvidedHeight")) {
				providedHeight = nbt.getInt("ProvidedHeight");
			}
			boolean adjustedHeight = false;
			if (nbt.contains("AdjustedHeight")) {
				adjustedHeight = nbt.getBoolean("AdjustedHeight");
			}
			return new RuinPiece(templateManager, nbt, rotation, type, Optional.ofNullable(heightmap), Optional.ofNullable(providedHeight), adjustedHeight);
		}

		@Override
		protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag nbt) {
			super.addAdditionalSaveData(context, nbt);
			nbt.putString("Rot", this.placeSettings.getRotation().name());
			nbt.putString("BiomeType", this.biomeType.toString());
			this.heightmap.ifPresent(types -> nbt.putString("HeightmapType", types.toString()));
			this.providedHeight.ifPresent(height -> nbt.putInt("ProvidedHeight", height));
			nbt.putBoolean("AdjustedHeight", this.adjustedHeight);
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
			if (!this.adjustedHeight) {
				this.adjustedHeight = true;
				int startHeight = this.templatePosition.getY();

				ResourceLocation pieceLocation = this.makeTemplateLocation();
				Integer offset = PIECE_OFFSETS.computeIfPresent(pieceLocation, (resourceLocation, integer) -> -this.getBoundingBox().getYSpan() + integer);
				if (offset == null) {
					offset = -2;
				}

				if (!DEEPSLATE_PIECES.contains(pieceLocation)) {
					int i = this.getGenHeight(world, this.templatePosition, random, 5);
					this.templatePosition = new BlockPos(this.templatePosition.getX(), i, this.templatePosition.getZ());
					BlockPos endPos = StructureTemplate.transform(
							new BlockPos(this.template.getSize().getX() - 1, 0, this.template.getSize().getZ() - 1), Mirror.NONE, this.placeSettings.getRotation(), BlockPos.ZERO
						)
						.offset(this.templatePosition);
					this.templatePosition = new BlockPos(this.templatePosition.getX(), this.getFinalHeight(this.templatePosition, world, endPos), this.templatePosition.getZ());
				}

				this.templatePosition = this.templatePosition.relative(Direction.Axis.Y, offset);
				int yDifference = startHeight - this.templatePosition.getY();
				this.boundingBox.move(0, yDifference, 0);
			}
			if (this.boundingBox.minY() <= world.getMinY()) {
				return;
			}
			super.postProcess(world, structureManager, chunkGenerator, random, boundingBox, chunkPos, pos);
		}

		public int getGenHeight(@NotNull WorldGenLevel world, BlockPos pos, RandomSource random, int providerOffset) {
			Heightmap.Types heightmapType = this.heightmap.orElseGet(() -> this.providedHeight.isEmpty() ? Heightmap.Types.OCEAN_FLOOR_WG : null);
			if (heightmapType != null) {
				return world.getHeight(heightmapType, pos.getX(), pos.getZ());
			}
			return this.providedHeight.get() + (random.nextInt(providerOffset) * (random.nextBoolean() ? 1 : -1));
		}

		private int getFinalHeight(@NotNull BlockPos start, BlockGetter world, BlockPos end) {
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
					 (blockState.isAir() || fluidState.is(FluidTags.WATER) || blockState.is(BlockTags.ICE)) && o > world.getMinY() + 1;
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
