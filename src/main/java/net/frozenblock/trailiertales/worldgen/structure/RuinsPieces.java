/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
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
import net.minecraft.resources.ResourceLocation;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.minecraft.Util;

public class RuinsPieces {

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
		@NotNull RuinsStructure structure,
		BoundingBox box,
		int maxAttempts
	) {
		RuinPiece ruinPiece = null;
		maxAttempts += 1;

		RuinPiece potentialPiece;
		ResourceLocation pieceId = structure.ruinsType.getPieceHandler().getRandomPiece(random);
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();

		for (int i = 0; i < maxAttempts; i++) {
			potentialPiece = new RuinPiece(
				structureTemplateManager,
				pieceId,
				mutableBlockPos,
				rotation,
				structure.ruinsType,
				structure.heightmap,
				structure.providedHeight
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
		@NotNull RuinsStructure structure
	) {
		ResourceLocation structureId = structure.ruinsType.getPieceHandler().getRandomPiece(random);
		RuinPiece piece = new RuinPiece(
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
			ResourceLocation structureId,
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
			ResourceLocation structureId,
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

		private static @NotNull StructurePlaceSettings makeSettings(Rotation rotation, RuinsStructure.@NotNull Type ruinsType) {
			StructurePlaceSettings placeSettings = new StructurePlaceSettings()
				.setRotation(rotation)
				.setMirror(Mirror.NONE)
				.addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);

			ruinsType.getProcessors().list().forEach(placeSettings::addProcessor);
			return placeSettings;
		}

		public static @NotNull RuinPiece create(StructureTemplateManager templateManager, @NotNull CompoundTag nbt) {
			Rotation rotation = Rotation.valueOf(nbt.getString("Rot"));

			RuinsStructure.Type type = RuinsStructure.Type.GENERIC;
			if (nbt.contains("RuinsType")) {
				type = RuinsStructure.Type.valueOf(nbt.getString("RuinsType"));
			} else if (nbt.contains("BiomeType")) {
				type = RuinsStructure.Type.valueOf(nbt.getString("BiomeType"));
			}

			Heightmap.Types heightmap = null;
			if (nbt.contains("HeightmapType")) heightmap = Heightmap.Types.valueOf(nbt.getString("HeightmapType"));

			Integer providedHeight = null;
			if (nbt.contains("ProvidedHeight")) providedHeight = nbt.getInt("ProvidedHeight");

			boolean adjustedHeight = false;
			if (nbt.contains("AdjustedHeight")) adjustedHeight = nbt.getBoolean("AdjustedHeight");

			return new RuinPiece(templateManager, nbt, rotation, type, Optional.ofNullable(heightmap), Optional.ofNullable(providedHeight), adjustedHeight);
		}

		@Override
		protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag nbt) {
			super.addAdditionalSaveData(context, nbt);
			nbt.putString("Rot", this.placeSettings.getRotation().name());
			nbt.putString("RuinsType", this.ruinsType.toString());
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

				int offset = -2;
				OptionalInt optionalOffset  = RuinsPieceHandler.getPieceOffset(this);
				if (optionalOffset.isPresent()) {
					offset = -this.getBoundingBox().getYSpan() + optionalOffset.getAsInt();
				}

				if (!this.ruinsType.isUnderground()) {
					int i = this.getGenHeight(world, this.templatePosition, random, 5);

					this.templatePosition = new BlockPos(this.templatePosition.getX(), i, this.templatePosition.getZ());

					BlockPos endPos = StructureTemplate.transform(
						new BlockPos(this.template.getSize().getX() - 1, 0, this.template.getSize().getZ() - 1),
						Mirror.NONE,
						this.placeSettings.getRotation(),
						BlockPos.ZERO
					).offset(this.templatePosition);

					this.templatePosition = new BlockPos(
						this.templatePosition.getX(),
						this.getFinalHeight(this.templatePosition, world, endPos),
						this.templatePosition.getZ()
					);
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
