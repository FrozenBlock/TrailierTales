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

package net.frozenblock.trailiertales.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.Set;
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinBlockEntity;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawnerState;
import net.frozenblock.trailiertales.block.impl.CoffinPart;
import net.frozenblock.trailiertales.client.TTModelLayers;
import net.frozenblock.trailiertales.client.model.CoffinModel;
import net.frozenblock.trailiertales.client.renderer.blockentity.state.CoffinRenderState;
import net.frozenblock.trailiertales.registry.TTBlockEntityTypes;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3fc;

@Environment(EnvType.CLIENT)
public class CoffinRenderer implements BlockEntityRenderer<CoffinBlockEntity, CoffinRenderState> {
	private final CoffinModel headModel;
	private final CoffinModel footModel;

	public CoffinRenderer(@NotNull Context context) {
		this(context.entityModelSet());
	}

	public CoffinRenderer(@NotNull EntityModelSet entityModelSet) {
		this.headModel = new CoffinModel(entityModelSet.bakeLayer(TTModelLayers.COFFIN_HEAD));
		this.footModel = new CoffinModel(entityModelSet.bakeLayer(TTModelLayers.COFFIN_FOOT));
	}

	@NotNull
	public static Identifier getCoffinTexture(@NotNull CoffinPart part, CoffinSpawnerState state) {
		return part == CoffinPart.HEAD ? state.getHeadTexture() : state.getFootTexture();
	}

	@Override
	public void submit(
		@NotNull CoffinRenderState renderState,
		@NotNull PoseStack poseStack,
		@NotNull SubmitNodeCollector submitNodeCollector,
		@NotNull CameraRenderState cameraRenderState
	) {
		float openProg = renderState.openProgress;
		openProg = 1F - openProg;
		openProg = 1F - openProg * openProg * openProg;

		this.submitPiece(
			poseStack,
			submitNodeCollector,
			renderState.part == CoffinPart.HEAD ? this.headModel : this.footModel,
			getCoffinTexture(renderState.part, renderState.spawnerState),
			null,
			openProg,
			renderState.wobbleProgress,
			renderState.lightCoords,
			OverlayTexture.NO_OVERLAY,
			renderState.breakProgress,
			0,
			false,
			renderState.direction
		);
	}

	public void renderInHand(
		@NotNull PoseStack poseStack,
		SubmitNodeCollector collector,
		int packedLight,
		int packedOverlay,
		Identifier headTexture,
		Identifier footTexture,
		float openness,
		int outlineColor
	) {
		poseStack.translate(0F, -0.1F, 0F);
		this.submitPiece(poseStack, collector, this.headModel, headTexture, null, openness, 0F, packedLight, packedOverlay, null, outlineColor, false, Direction.SOUTH);
		this.submitPiece(poseStack, collector, this.footModel, footTexture, null, openness, 0F, packedLight, packedOverlay, null, outlineColor, true, Direction.SOUTH);
	}

	private void submitPiece(
		@NotNull PoseStack poseStack,
		@NotNull SubmitNodeCollector collector,
		@NotNull CoffinModel model,
		@NotNull Identifier texture,
		@Nullable Identifier glowingTexture,
		float openProgress,
		float wobbleProgress,
		int packedLight,
		int packedOverlay,
		@Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress,
		int outlineColor,
		boolean renderAsOffsetFoot,
		Direction direction
	) {
		poseStack.pushPose();

		openProgress = setupPoseStackAndCalculateOpenProgress(poseStack, renderAsOffsetFoot, direction, openProgress, wobbleProgress);

		collector.submitModel(
			model,
			openProgress,
			poseStack,
			RenderTypes.entityCutout(texture),
			packedLight,
			packedOverlay,
			outlineColor,
			breakProgress
		);
		if (glowingTexture != null) {
			collector.submitModel(
				model,
				openProgress,
				poseStack,
				RenderTypes.eyes(glowingTexture),
				packedLight,
				packedOverlay,
				outlineColor,
				null
			);
		}

		poseStack.popPose();
	}

	private static float setupPoseStackAndCalculateOpenProgress(
		@NotNull PoseStack poseStack,
		boolean renderAsOffsetFoot,
		@NotNull Direction direction,
		float openProgress,
		float wobbleProgress
	) {
		poseStack.translate(0.5F, 0.5F, 0.5F);
		poseStack.mulPose(Axis.YP.rotationDegrees(-direction.toYRot()));
		poseStack.translate(-0.5F, -0.5F, -0.5F);

		if (wobbleProgress >= 0F && wobbleProgress <= 1F) {
			float coffinWobble = wobbleProgress * Mth.PI * 2.5F;
			float wobbleDampen = 1F - wobbleProgress;

			float wobble = -3F * Mth.cos(coffinWobble) * Mth.sin(coffinWobble) * wobbleDampen;
			poseStack.rotateAround(Axis.ZP.rotation(wobble * 0.015625F), 0.5F, 0F, 0.5F);

			float lidWobble = (wobbleProgress + (2.5F / CoffinBlockEntity.WOBBLE_DURATION)) * Mth.PI * 2.5F;
			openProgress += Math.max(0F, (Mth.cos(lidWobble) * -0.25F) * (Mth.sin(lidWobble) * -0.25F)) * wobbleDampen;
		}

		if (renderAsOffsetFoot) poseStack.translate(0F, 0F, -1F);

		return openProgress * Mth.HALF_PI;
	}

	public void getExtents(Consumer<Vector3fc> set) {
		PoseStack poseStack = new PoseStack();
		setupPoseStackAndCalculateOpenProgress(poseStack, false, Direction.SOUTH, 0F, 0F);
		this.headModel.root().getExtentsForGui(poseStack, set);
		poseStack.setIdentity();
		setupPoseStackAndCalculateOpenProgress(poseStack, true, Direction.SOUTH, 0F, 0F);
		this.footModel.root().getExtentsForGui(poseStack, set);
	}

	@Override
	public @NotNull CoffinRenderState createRenderState() {
		return new CoffinRenderState();
	}

	@Override
	public void extractRenderState(
		@NotNull CoffinBlockEntity coffin,
		@NotNull CoffinRenderState renderState,
		float partialTick,
		Vec3 cameraPos,
		@Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay
	) {
		BlockEntityRenderer.super.extractRenderState(coffin, renderState, partialTick, cameraPos, crumblingOverlay);

		final Level level = coffin.getLevel();
		renderState.part = renderState.blockState.getValue(CoffinBlock.PART);
		renderState.spawnerState = renderState.blockState.getValue(CoffinBlock.STATE);
		renderState.ominous = coffin.getCoffinSpawner().isOminous();
		renderState.direction = renderState.blockState.getValue(CoffinBlock.FACING);
		renderState.openProgress = coffin.getOpenProgress(partialTick);
		renderState.wobbleProgress = ((float)(level.getGameTime() - coffin.wobbleStartedAtTick) + partialTick) / CoffinBlockEntity.WOBBLE_DURATION;

		DoubleBlockCombiner.NeighborCombineResult<? extends CoffinBlockEntity> neighborCombineResult = DoubleBlockCombiner.combineWithNeigbour(
			TTBlockEntityTypes.COFFIN,
			CoffinBlock::getBlockType,
			CoffinBlock::getConnectedDirection,
			CoffinBlock.FACING,
			renderState.blockState,
			level,
			renderState.blockPos,
			(world, pos) -> false
		);
		renderState.lightCoords = neighborCombineResult.apply(new BrightnessCombiner<>()).get(renderState.lightCoords);
	}
}
