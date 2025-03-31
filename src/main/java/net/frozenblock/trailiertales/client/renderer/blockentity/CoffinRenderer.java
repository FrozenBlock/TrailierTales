/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinBlockEntity;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawnerState;
import net.frozenblock.trailiertales.block.impl.CoffinPart;
import net.frozenblock.trailiertales.client.TTModelLayers;
import net.frozenblock.trailiertales.client.model.CoffinModel;
import net.frozenblock.trailiertales.registry.TTBlockEntityTypes;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class CoffinRenderer implements BlockEntityRenderer<CoffinBlockEntity> {
	private final CoffinModel headModel;
	private final CoffinModel footModel;

	public CoffinRenderer(@NotNull Context context) {
		this(context.getModelSet());
	}

	public CoffinRenderer(@NotNull EntityModelSet entityModelSet) {
		this.headModel = new CoffinModel(entityModelSet.bakeLayer(TTModelLayers.COFFIN_HEAD));
		this.footModel = new CoffinModel(entityModelSet.bakeLayer(TTModelLayers.COFFIN_FOOT));
	}

	@NotNull
	public static ResourceLocation getCoffinTexture(@NotNull CoffinPart part, CoffinSpawnerState state, boolean ominous) {
		return part == CoffinPart.HEAD ? state.getHeadTexture() : state.getFootTexture();
	}

	@Override
	public void render(
		@NotNull CoffinBlockEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, Vec3 cameraPos
	) {
		Level level = blockEntity.getLevel();
		if (level != null) {
			float openProg = blockEntity.getOpenProgress(partialTick);
			openProg = 1F - openProg;
			openProg = 1F - openProg * openProg * openProg;

			BlockState blockState = blockEntity.getBlockState();
			DoubleBlockCombiner.NeighborCombineResult<? extends CoffinBlockEntity> neighborCombineResult = DoubleBlockCombiner.combineWithNeigbour(
				TTBlockEntityTypes.COFFIN,
				CoffinBlock::getBlockType,
				CoffinBlock::getConnectedDirection,
				CoffinBlock.FACING,
				blockState,
				level,
				blockEntity.getBlockPos(),
				(world, pos) -> false
			);
			int i = neighborCombineResult.apply(new BrightnessCombiner<>()).get(packedLight);
			CoffinPart part = blockState.getValue(CoffinBlock.PART);
			CoffinSpawnerState coffinSpawnerState = blockState.getValue(CoffinBlock.STATE);
			float f = blockState.getValue(CoffinBlock.FACING).toYRot();
			poseStack.translate(0.5D, 0.5D, 0.5D);
			poseStack.mulPose(Axis.YP.rotationDegrees(-f));
			poseStack.translate(-0.5D, -0.5D, -0.5D);

			float wobbleProgress = ((float)(level.getGameTime() - blockEntity.wobbleStartedAtTick) + partialTick) / CoffinBlockEntity.WOBBLE_DURATION;
			if (wobbleProgress >= 0F && wobbleProgress <= 1F) {
				float coffinWobble = wobbleProgress * Mth.PI * 2.5F;
				float wobbleDampen = 1F - wobbleProgress;

				float wobble = -3F * Mth.cos(coffinWobble) * Mth.sin(coffinWobble) * wobbleDampen;
				poseStack.rotateAround(Axis.ZP.rotation(wobble * 0.015625F), 0.5F, 0F, 0.5F);

				float lidWobble = (wobbleProgress + (2.5F / CoffinBlockEntity.WOBBLE_DURATION)) * Mth.PI * 2.5F;
				openProg += Math.max(0F, (Mth.cos(lidWobble) * -0.25F) * (Mth.sin(lidWobble) * -0.25F)) * wobbleDampen;
			}

			this.renderPiece(
				poseStack,
				buffer,
				part == CoffinPart.HEAD ? this.headModel : this.footModel,
				getCoffinTexture(part, coffinSpawnerState, false),
				null,
				openProg,
				i,
				packedOverlay,
				false
			);
		}
	}

	public void renderInHand(
		@NotNull PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, ResourceLocation headTexture, ResourceLocation footTexture, float openness
	) {
		this.headModel.setupAnim(openness);
		this.footModel.setupAnim(openness);
		poseStack.translate(0.5D, 0.5D, 0.5D);
		poseStack.mulPose(Axis.YP.rotationDegrees(-Direction.SOUTH.toYRot()));
		poseStack.translate(-0.5D, -0.5D, -0.5D);
		this.renderPiece(poseStack, buffer, this.headModel, headTexture, null, 0F, packedLight, packedOverlay, false);
		this.renderPiece(poseStack, buffer, this.footModel, footTexture, null, 0F, packedLight, packedOverlay, true);
	}

	private void renderPiece(
		@NotNull PoseStack poseStack,
		MultiBufferSource bufferSource,
		@NotNull CoffinModel model,
		@NotNull ResourceLocation texture,
		@Nullable ResourceLocation glowingTexture,
		float openProgress,
		int packedLight,
		int packedOverlay,
		boolean foot
	) {
		if (foot) {
			poseStack.pushPose();
			poseStack.translate(0F, 0F, -1F);
		}
		model.setupAnim(openProgress * 1.5707964F);
		VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(texture));
		model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
		if (glowingTexture != null) {
			VertexConsumer glowingConsumer = bufferSource.getBuffer(RenderType.eyes(glowingTexture));
			model.renderToBuffer(poseStack, glowingConsumer, packedLight, packedOverlay);
		}
		if (foot) {
			poseStack.popPose();
		}
	}
}
