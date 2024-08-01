package net.frozenblock.trailiertales.worldgen.structure;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import net.frozenblock.lib.worldgen.structure.api.AppendSherds;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingProcessorRule;
import net.frozenblock.lib.worldgen.structure.api.BlockStateRespectingRuleProcessor;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.registry.RegisterBlocks;
import net.frozenblock.trailiertales.registry.RegisterLootTables;
import net.frozenblock.trailiertales.registry.RegisterStructurePieceTypes;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosAlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.AppendLoot;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RuinsPieces {
	private static final List<StructureProcessor> GENERIC_PROCESSORS = ImmutableList.of(
		new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.DIRT.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.GRAVEL, 0.1F), AlwaysTrueTest.INSTANCE, Blocks.COARSE_DIRT.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.75F), AlwaysTrueTest.INSTANCE, Blocks.COBBLESTONE.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.COBBLESTONE, 0.25F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.25F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_STONE_BRICKS.defaultBlockState()),
				new ProcessorRule(new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.2F), AlwaysTrueTest.INSTANCE, Blocks.CRACKED_STONE_BRICKS.defaultBlockState())
			)
		),
		new BlockStateRespectingRuleProcessor(
			ImmutableList.of(
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.COBBLESTONE_SLAB.defaultBlockState(), 0.25F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE_SLAB
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.COBBLESTONE_WALL.defaultBlockState(), 0.25F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE_WALL
				),
				new BlockStateRespectingProcessorRule(
					new RandomBlockStateMatchTest(Blocks.COBBLESTONE_STAIRS.defaultBlockState(), 0.25F), AlwaysTrueTest.INSTANCE, Blocks.MOSSY_COBBLESTONE_STAIRS
				)
			)
		),
		archyLootProcessor(Blocks.GRAVEL, Blocks.SUSPICIOUS_GRAVEL, RegisterLootTables.RUINS_ARCHAEOLOGY, 0.275F),
		archyLootProcessor(Blocks.DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.RUINS_ARCHAEOLOGY, 0.2F),
		archyLootProcessor(Blocks.COARSE_DIRT, RegisterBlocks.SUSPICIOUS_DIRT, RegisterLootTables.RUINS_ARCHAEOLOGY, 0.2F),
		archyLootProcessor(Blocks.CLAY, RegisterBlocks.SUSPICIOUS_CLAY, RegisterLootTables.RUINS_ARCHAEOLOGY, 0.4F),
		decoratedPotSherdProcessor(
			1F,
			false,
			Items.MINER_POTTERY_SHERD,
			Items.ARCHER_POTTERY_SHERD,
			Items.BREWER_POTTERY_SHERD,
			Items.FRIEND_POTTERY_SHERD
		)
	);

	private static final ImmutableList<ResourceLocation> GENERIC_SURFACE_PIECES = ImmutableList.of(
		genericRuinId("archway3"),
		genericRuinId("shrine1"),
		genericRuinId("pillar1"),
		genericRuinId("pillars1"));
	private static final ImmutableList<ResourceLocation> GENERIC_MOSTLY_BURIED_PIECES = ImmutableList.of(
		genericRuinId("archway2"),
		genericRuinId("archway3"),
		genericRuinId("fort1"),
		genericRuinId("fort2"),
		genericRuinId("fort3"),
		genericRuinId("house1"),
		genericRuinId("house2"),
		genericRuinId("houses1"),
		genericRuinId("shrine3"),
		genericRuinId("shrine4"),
		genericRuinId("shrine5"),
		genericRuinId("shrine6"),
		genericRuinId("shrine7"),
		genericRuinId("shrine8"),
		genericRuinId("town1"),
		genericRuinId("twin_houses1"),
		genericRuinId("twin_houses2"));
	private static final ImmutableList<ResourceLocation> GENERIC_BURIED_PIECES = ImmutableList.of(
		genericRuinId("house1"),
		genericRuinId("library1"),
		genericRuinId("shrine2"));
	private static final ImmutableList<ResourceLocation> GENERIC_FIVE_FROM_TOP_PIECES = ImmutableList.of(
		genericRuinId("well1"));

	@Contract("_ -> new")
	private static @NotNull ResourceLocation genericRuinId(String path) {
		return TrailierConstants.id("ruins/generic/" + path);
	}

	@Contract("_, _, _, _ -> new")
	private static @NotNull RuleProcessor archyLootProcessor(Block original, @NotNull Block suspicious, ResourceKey<LootTable> registryKey, float chance) {
		return new RuleProcessor(
			ImmutableList.of(
				new ProcessorRule(
					new RandomBlockMatchTest(original, chance),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					suspicious.defaultBlockState(),
					new AppendLoot(registryKey)
				)
			)
		);
	}

	@Contract("_, _, _ -> new")
	private static @NotNull BlockStateRespectingRuleProcessor decoratedPotSherdProcessor(float chance, boolean defaultToBricks, Item... sherds) {
		return new BlockStateRespectingRuleProcessor(
			ImmutableList.of(
				new BlockStateRespectingProcessorRule(
					new RandomBlockMatchTest(Blocks.DECORATED_POT, chance),
					AlwaysTrueTest.INSTANCE,
					PosAlwaysTrueTest.INSTANCE,
					Blocks.DECORATED_POT,
					new AppendSherds(chance, defaultToBricks, sherds)
				)
			)
		);
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
		RuinsStructure feature,
		StructurePieceAccessor pieces
	) {
		List<BoundingBox> boundingBoxes = new ArrayList<>();
		boundingBoxes.add(boundingBox);
		int totalPieces = Mth.nextInt(random, 2, 6);
		for (int pieceNumber = 0; pieceNumber < totalPieces; pieceNumber++) {
			Rotation newRotation = Rotation.getRandom(random);
			addPiece(structureTemplateManager, boundingBoxes, pos, newRotation, pieces, random, feature, 7);
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

		private static @NotNull StructurePlaceSettings makeSettings(Rotation rotation, RuinsStructure.Type type) {
			StructurePlaceSettings placeSettings = new StructurePlaceSettings()
				.setRotation(rotation)
				.setMirror(Mirror.NONE)
				.addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);

			GENERIC_PROCESSORS.forEach(placeSettings::addProcessor);
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
			ResourceLocation pieceName = this.makeTemplateLocation();
			int i = world.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, this.templatePosition.getX(), this.templatePosition.getZ());
			this.templatePosition = new BlockPos(this.templatePosition.getX(), i, this.templatePosition.getZ());
			BlockPos blockPos = StructureTemplate.transform(
					new BlockPos(this.template.getSize().getX() - 1, 0, this.template.getSize().getZ() - 1), Mirror.NONE, this.placeSettings.getRotation(), BlockPos.ZERO
				)
				.offset(this.templatePosition);
			this.templatePosition = new BlockPos(this.templatePosition.getX(), this.getHeight(this.templatePosition, world, blockPos), this.templatePosition.getZ());

			int offset;
			if (GENERIC_MOSTLY_BURIED_PIECES.contains(pieceName)) {
				offset = -this.getBoundingBox().getYSpan() + 3;
			} else if (GENERIC_BURIED_PIECES.contains(pieceName)) {
				offset = -this.getBoundingBox().getYSpan() - random.nextInt(1, 3);
			} else if (GENERIC_FIVE_FROM_TOP_PIECES.contains(pieceName)) {
				offset = -this.getBoundingBox().getYSpan() + 5;
			} else {
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
