/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.trailiertales.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.math.Transformation;
import java.util.Map;
import java.util.function.Consumer;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinBlockEntity;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawnerState;
import net.frozenblock.trailiertales.block.impl.CoffinPart;
import net.frozenblock.trailiertales.client.TTModelLayers;
import net.frozenblock.trailiertales.client.model.object.coffin.CoffinModel;
import net.frozenblock.trailiertales.client.renderer.MultiblockCoffinResources;
import net.frozenblock.trailiertales.client.renderer.blockentity.state.CoffinRenderState;
import net.frozenblock.trailiertales.registry.TTBlockEntityTypes;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3fc;

@ClientOnly
public class CoffinRenderer implements BlockEntityRenderer<CoffinBlockEntity, CoffinRenderState> {
	private static final Map<Direction, Transformation> TRANSFORMATIONS = Util.makeEnumMap(Direction.class, CoffinRenderer::createModelTransform);
	public static final MultiblockCoffinResources<ModelLayerLocation> LAYERS = new MultiblockCoffinResources<>(TTModelLayers.COFFIN_HEAD, TTModelLayers.COFFIN_FOOT);
	private final MultiblockCoffinResources<CoffinModel> models;

	public CoffinRenderer(Context context) {
		this(context.entityModelSet());
	}

	public CoffinRenderer(EntityModelSet entityModelSet) {
		this.models = LAYERS.map(layer -> new CoffinModel(entityModelSet.bakeLayer(layer)));
	}

	public static Identifier getCoffinTexture(CoffinPart part, CoffinSpawnerState state) {
		return part == CoffinPart.HEAD ? state.getHeadTexture() : state.getFootTexture();
	}

	@Override
	public void submit(
		CoffinRenderState state,
		PoseStack poseStack,
		SubmitNodeCollector submitNodeCollector,
		CameraRenderState camera
	) {
		float openProg = state.openProgress;
		openProg = 1F - openProg;
		openProg = 1F - openProg * openProg * openProg;

		this.submitPiece(
			poseStack,
			submitNodeCollector,
			this.models.select(state.part),
			getCoffinTexture(state.part, state.spawnerState),
			null,
			openProg,
			state.wobbleProgress,
			state.lightCoords,
			OverlayTexture.NO_OVERLAY,
			state.breakProgress,
			0,
			state.direction
		);
	}

	public void renderInHand(
		PoseStack poseStack,
		SubmitNodeCollector submitNodeCollector,
		int packedLight,
		int packedOverlay,
		Identifier texture,
		CoffinPart part,
		float openness,
		int outlineColor
	) {
		final CoffinModel model = this.models.select(part);
		poseStack.translate(0F, -0.1F, 0F);
		this.submitPiece(
			poseStack,
			submitNodeCollector,
			model,
			texture,
			null,
			openness,
			0F,
			packedLight,
			packedOverlay,
			null,
			outlineColor,
			Direction.SOUTH
		);
	}

	private void submitPiece(
		PoseStack poseStack,
		SubmitNodeCollector submitNodeCollector,
		CoffinModel model,
		Identifier texture,
		@Nullable Identifier emissiveTexture,
		float open,
		float wobble,
		int lightCoords,
		int overlayCoords,
		@Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress,
		int outlineColor,
		Direction direction
	) {
		poseStack.pushPose();

		open = setupPoseStackAndCalculateOpenProgress(poseStack, direction, open, wobble);

		submitNodeCollector.submitModel(
			model,
			open,
			poseStack,
			RenderTypes.entityCutoutCull(texture),
			lightCoords,
			overlayCoords,
			outlineColor
		);
		if (emissiveTexture != null) {
			submitNodeCollector.submitModel(
				model,
				open,
				poseStack,
				RenderTypes.eyes(emissiveTexture),
				lightCoords,
				overlayCoords,
				outlineColor
			);
		}

		if (breakProgress != null) {
			submitNodeCollector.order(1)
				.submitCrumblingOverlay(model, open, poseStack, RenderTypes.entityCutoutCull(texture), lightCoords, OverlayTexture.NO_OVERLAY, -1, breakProgress);
		}

		poseStack.popPose();
	}

	private static float setupPoseStackAndCalculateOpenProgress(
		PoseStack poseStack,
		Direction direction,
		float openProgress,
		float wobbleProgress
	) {
		poseStack.translate(0.5F, 0.5F, 0.5F);
		poseStack.rotate(Axis.YP.rotationDegrees(-direction.toYRot()));
		poseStack.translate(-0.5F, -0.5F, -0.5F);

		if (wobbleProgress >= 0F && wobbleProgress <= 1F) {
			final float coffinWobble = wobbleProgress * Mth.PI * 2.5F;
			final float wobbleDampen = 1F - wobbleProgress;

			final float wobble = -3F * Mth.cos(coffinWobble) * Mth.sin(coffinWobble) * wobbleDampen;
			poseStack.rotateAround(Axis.ZP.rotation(wobble * 0.015625F), 0.5F, 0F, 0.5F);

			final float lidWobble = (wobbleProgress + (2.5F / CoffinBlockEntity.WOBBLE_DURATION)) * Mth.PI * 2.5F;
			openProgress += Math.max(0F, (Mth.cos(lidWobble) * -0.25F) * (Mth.sin(lidWobble) * -0.25F)) * wobbleDampen;
		}

		return openProgress * Mth.HALF_PI;
	}

	private static Transformation createModelTransform(Direction direction) {
		final PoseStack poseStack = new PoseStack();
		setupPoseStackAndCalculateOpenProgress(poseStack, direction, 0F, 0F);
		return new Transformation(poseStack.last().pose());
	}

	public static Transformation modelTransform(Direction direction) {
		return TRANSFORMATIONS.get(direction);
	}

	public void getExtents(CoffinPart part, Consumer<Vector3fc> set) {
		final PoseStack poseStack = new PoseStack();
		this.models.select(part).root().getExtentsForGui(poseStack, set);
	}

	@Override
	public CoffinRenderState createRenderState() {
		return new CoffinRenderState();
	}

	@Override
	public void extractRenderState(
		CoffinBlockEntity blockEntity,
		CoffinRenderState state,
		float partialTicks,
		Vec3 cameraPosition,
		@Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress
	) {
		BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);

		final Level level = blockEntity.getLevel();
		state.part = state.blockState.getValue(CoffinBlock.PART);
		state.spawnerState = state.blockState.getValue(CoffinBlock.STATE);
		state.ominous = blockEntity.getCoffinSpawner().isOminous();
		state.direction = state.blockState.getValue(CoffinBlock.FACING);
		state.openProgress = blockEntity.getOpenProgress(partialTicks);
		state.wobbleProgress = ((float)(level.getGameTime() - blockEntity.wobbleStartedAtTick) + partialTicks) / CoffinBlockEntity.WOBBLE_DURATION;

		final DoubleBlockCombiner.NeighborCombineResult<? extends CoffinBlockEntity> neighborCombineResult = DoubleBlockCombiner.combineWithNeigbour(
			TTBlockEntityTypes.COFFIN.get(),
			CoffinBlock::getBlockType,
			CoffinBlock::getConnectedDirection,
			CoffinBlock.FACING,
			state.blockState,
			level,
			state.blockPos,
			(levelx, pos) -> false
		);
		state.lightCoords = neighborCombineResult.apply(new BrightnessCombiner<>()).get(state.lightCoords);
	}
}
