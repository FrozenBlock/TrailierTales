/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.trailiertales.worldgen.structure;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import net.frozenblock.trailiertales.registry.TTStructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
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
import org.jetbrains.annotations.Nullable;

public class RuinsPieces {

	public static void addPieces(
		StructureTemplateManager structureTemplateManager,
		LevelHeightAccessor heightAccessor,
		BlockPos pos,
		Rotation rotation,
		RandomSource random,
		RuinsStructure feature,
		List<RuinsPieces.RuinPiece> pieces,
		BoundingBox box
	) {
		final RuinPiece ruinPiece = addStarterPiece(structureTemplateManager, pos, rotation, pieces, random, feature);
		if (random.nextFloat() > feature.clusterProbability) return;
		addClusterRuins(structureTemplateManager, ruinPiece.getBoundingBox(), random, pos, feature, pieces, box);
	}

	private static void addClusterRuins(
		StructureTemplateManager structureTemplateManager,
		BoundingBox boundingBox,
		RandomSource random,
		BlockPos pos,
		RuinsStructure feature,
		List<RuinsPieces.RuinPiece> pieces,
		BoundingBox box
	) {
		final ObjectArrayList<BoundingBox> boundingBoxes = new ObjectArrayList<>();
		boundingBoxes.add(boundingBox);
		int totalPieces = feature.clusterPieces.sample(random);
		for (int pieceNumber = 0; pieceNumber < totalPieces; pieceNumber++) {
			final Rotation newRotation = Rotation.getRandom(random);
			addPiece(structureTemplateManager, boundingBoxes, pos, newRotation, pieces, random, feature, box, 7);
		}
	}

	private static @Nullable RuinPiece addPiece(
		StructureTemplateManager structureTemplateManager,
		ObjectArrayList<BoundingBox> boundingBoxes,
		BlockPos pos,
		Rotation rotation,
		List<RuinsPieces.RuinPiece> pieces,
		RandomSource random,
		RuinsStructure structure,
		BoundingBox box,
		int maxAttempts
	) {
		RuinPiece ruinPiece = null;
		maxAttempts += 1;

		RuinPiece potentialPiece;
		final Identifier pieceId = structure.ruinsType.getPieceHandler().getRandomPiece(random);
		final BlockPos.MutableBlockPos mutable = pos.mutable();

		for (int i = 0; i < maxAttempts; i++) {
			potentialPiece = new RuinPiece(
				structureTemplateManager,
				pieceId,
				mutable,
				rotation,
				structure.ruinsType,
				structure.heightmap,
				structure.providedHeight
			);

			final BoundingBox currentBox = potentialPiece.getBoundingBox();
			final AtomicReference<BoundingBox> inflatedBox = new AtomicReference<>(currentBox.inflatedBy(2));
			final List<BoundingBox> shuffledBoxes = Util.shuffledCopy(boundingBoxes, random);
			final AtomicBoolean intersected = new AtomicBoolean(false);

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
					final BlockPos center = boundingBox.getCenter();
					final boolean useXSpan = random.nextBoolean();
					final boolean useZSpan = !useXSpan || random.nextBoolean();
					final int xDirection = random.nextBoolean() ? 1 : -1;
					final int zDirection = random.nextBoolean() ? 1 : -1;
					final double xSpanScale = useXSpan ? 0.5D : 0.1D;
					final double zSpanScale = useZSpan ? 0.5D : 0.1D;
					final int xStep = (int) ((boundingBox.getXSpan() * xSpanScale) + (currentBox.getXSpan() * xSpanScale) + random.nextInt(0, 6));
					final int zStep = (int) ((boundingBox.getZSpan() * zSpanScale) + (currentBox.getZSpan() * zSpanScale) + random.nextInt(0, 6));
					mutable.set(center).move(xStep * xDirection, 0, zStep * zDirection);
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

	private static RuinPiece addStarterPiece(
		StructureTemplateManager structureTemplateManager,
		BlockPos pos,
		Rotation rotation,
		List<RuinsPieces.RuinPiece> pieces,
		RandomSource random,
		RuinsStructure structure
	) {
		final Identifier structureId = structure.ruinsType.getPieceHandler().getRandomPiece(random);
		final RuinPiece piece = new RuinPiece(
			structureTemplateManager,
			structureId,
			pos,
			rotation,
			structure.ruinsType,
			structure.heightmap,
			structure.providedHeight
		);
		pieces.add(piece);
		return piece;
	}

	public static class RuinPiece extends TemplateStructurePiece {
		private final RuinsStructure.Type ruinsType;
		public final Optional<Heightmap.Types> heightmap;
		private Optional<Integer> providedHeight;
		private boolean adjustedHeight;

		public RuinPiece(
			StructureTemplateManager structureTemplateManager,
			Identifier structureId,
			BlockPos pos,
			Rotation rotation,
			RuinsStructure.Type biomeType, Optional<Heightmap.Types> heightmap,
			Optional<Integer> providedHeight
		) {
			super(TTStructurePieceTypes.RUIN, 0, structureTemplateManager, structureId, structureId.toString(), makeSettings(rotation, biomeType), pos);
			this.ruinsType = biomeType;
			this.heightmap = heightmap;
			this.providedHeight = providedHeight;
		}

		public RuinPiece(
			StructureTemplateManager structureTemplateManager,
			Identifier structureId,
			BlockPos pos,
			Rotation rotation,
			RuinsStructure.Type ruinsType,
			Optional<Heightmap.Types> heightmap,
			Optional<Integer> providedHeight,
			boolean adjustedHeight
		) {
			super(TTStructurePieceTypes.RUIN, 0, structureTemplateManager, structureId, structureId.toString(), makeSettings(rotation, ruinsType), pos);
			this.ruinsType = ruinsType;
			this.heightmap = heightmap;
			this.providedHeight = providedHeight;
			this.adjustedHeight = adjustedHeight;
		}

		private RuinPiece(
			StructureTemplateManager templateManager,
			CompoundTag nbt, Rotation rotation,
			RuinsStructure.Type ruinsType,
			Optional<Heightmap.Types> heightmap,
			Optional<Integer> providedHeight
		) {
			super(TTStructurePieceTypes.RUIN, nbt, templateManager, id -> makeSettings(rotation, ruinsType));
			this.ruinsType = ruinsType;
			this.heightmap = heightmap;
			this.providedHeight = providedHeight;
		}

		private RuinPiece(
			StructureTemplateManager templateManager,
			CompoundTag nbt, Rotation rotation,
			RuinsStructure.Type ruinsType,
			Optional<Heightmap.Types> heightmap,
			Optional<Integer> providedHeight,
			boolean adjustedHeight
		) {
			super(TTStructurePieceTypes.RUIN, nbt, templateManager, id -> makeSettings(rotation, ruinsType));
			this.ruinsType = ruinsType;
			this.heightmap = heightmap;
			this.providedHeight = providedHeight;
			this.adjustedHeight = adjustedHeight;
		}

		private static StructurePlaceSettings makeSettings(Rotation rotation, RuinsStructure.Type ruinsType) {
			StructurePlaceSettings placeSettings = new StructurePlaceSettings()
				.setRotation(rotation)
				.setMirror(Mirror.NONE)
				.addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);

			ruinsType.getProcessors().list().forEach(placeSettings::addProcessor);
			return placeSettings;
		}

		public static RuinPiece create(StructureTemplateManager templateManager, CompoundTag tag) {
			final Rotation rotation = Rotation.valueOf(tag.getStringOr("Rot", ""));

			RuinsStructure.Type type = RuinsStructure.Type.GENERIC;
			if (tag.contains("RuinsType")) {
				type = RuinsStructure.Type.valueOf(tag.getStringOr("RuinsType", ""));
			} else if (tag.contains("BiomeType")) {
				type = RuinsStructure.Type.valueOf(tag.getStringOr("BiomeType", ""));
			}

			Heightmap.Types heightmap = null;
			if (tag.contains("HeightmapType")) heightmap = Heightmap.Types.valueOf(tag.getStringOr("HeightmapType", ""));

			Integer providedHeight = null;
			if (tag.contains("ProvidedHeight")) providedHeight = tag.getIntOr("ProvidedHeight", 0);

			boolean adjustedHeight = false;
			if (tag.contains("AdjustedHeight")) adjustedHeight = tag.getBooleanOr("AdjustedHeight", false);

			return new RuinPiece(templateManager, tag, rotation, type, Optional.ofNullable(heightmap), Optional.ofNullable(providedHeight), adjustedHeight);
		}

		@Override
		protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
			super.addAdditionalSaveData(context, tag);
			tag.putString("Rot", this.placeSettings.getRotation().name());
			tag.putString("RuinsType", this.ruinsType.toString());
			this.heightmap.ifPresent(types -> tag.putString("HeightmapType", types.toString()));
			this.providedHeight.ifPresent(height -> tag.putInt("ProvidedHeight", height));
			tag.putBoolean("AdjustedHeight", this.adjustedHeight);
		}

		@Override
		protected void handleDataMarker(String metadata, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox boundingBox) {
		}

		@Override
		public void postProcess(
			WorldGenLevel level,
			StructureManager structureManager,
			ChunkGenerator chunkGenerator,
			RandomSource random,
			BoundingBox boundingBox,
			ChunkPos chunkPos,
			BlockPos pos
		) {
			if (!this.adjustedHeight) {
				this.adjustedHeight = true;
				final int startHeight = this.templatePosition.getY();

				int offset = -2;
				final OptionalInt optionalOffset  = RuinsPieceHandler.getPieceOffset(this);
				if (optionalOffset.isPresent()) offset = -this.getBoundingBox().getYSpan() + optionalOffset.getAsInt();

				if (!this.ruinsType.isUnderground()) {
					final int genHeight = this.getGenHeight(level, this.templatePosition, random, 5);

					this.templatePosition = new BlockPos(this.templatePosition.getX(), genHeight, this.templatePosition.getZ());

					final BlockPos endPos = StructureTemplate.transform(
						new BlockPos(this.template.getSize().getX() - 1, 0, this.template.getSize().getZ() - 1),
						Mirror.NONE,
						this.placeSettings.getRotation(),
						BlockPos.ZERO
					).offset(this.templatePosition);

					this.templatePosition = new BlockPos(
						this.templatePosition.getX(),
						this.getFinalHeight(this.templatePosition, level, endPos),
						this.templatePosition.getZ()
					);
				}

				this.templatePosition = this.templatePosition.relative(Direction.Axis.Y, offset);
				int yDifference = startHeight - this.templatePosition.getY();
				this.boundingBox.move(0, yDifference, 0);
			}

			if (this.boundingBox.minY() <= level.getMinY()) return;

			super.postProcess(level, structureManager, chunkGenerator, random, boundingBox, chunkPos, pos);
		}

		public int getGenHeight(WorldGenLevel level, BlockPos pos, RandomSource random, int providerOffset) {
			final Heightmap.Types heightmapType = this.heightmap.orElseGet(() -> this.providedHeight.isEmpty() ? Heightmap.Types.OCEAN_FLOOR_WG : null);
			if (heightmapType != null) return level.getHeight(heightmapType, pos.getX(), pos.getZ());
			return this.providedHeight.get() + (random.nextInt(providerOffset) * (random.nextBoolean() ? 1 : -1));
		}

		private int getFinalHeight(BlockPos start, BlockGetter level, BlockPos end) {
			int i = start.getY();
			int j = 512;
			int k = i - 1;
			int l = 0;

			for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
				final int x = pos.getX();
				final int z = pos.getZ();
				int y = start.getY() - 1;
				BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(x, y, z);
				BlockState blockState = level.getBlockState(mutableBlockPos);

				for (FluidState fluidState = level.getFluidState(mutableBlockPos);
					 (blockState.isAir() || fluidState.is(FluidTags.WATER) || blockState.is(BlockTags.ICE)) && y > level.getMinY() + 1;
					 fluidState = level.getFluidState(mutableBlockPos)
				) {
					mutableBlockPos.set(x, --y, z);
					blockState = level.getBlockState(mutableBlockPos);
				}

				j = Math.min(j, y);
				if (y < k - 2) l++;
			}

			final int xDifference = Math.abs(start.getX() - end.getX());
			if (k - j > 2 && l > xDifference - 2) i = j + 1;

			return i;
		}
	}
}
