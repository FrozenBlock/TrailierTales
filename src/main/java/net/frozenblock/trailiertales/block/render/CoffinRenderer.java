package net.frozenblock.trailiertales.block.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.TrailierTalesClient;
import net.frozenblock.trailiertales.TrailierTalesSharedConstants;
import net.frozenblock.trailiertales.block.CoffinBlock;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinBlockEntity;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawnerState;
import net.frozenblock.trailiertales.block.impl.CoffinPart;
import net.frozenblock.trailiertales.registry.RegisterBlockEntities;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class CoffinRenderer implements BlockEntityRenderer<CoffinBlockEntity> {
	public static final ResourceLocation COFFIN_HEAD = TrailierTalesSharedConstants.id("textures/entity/coffin/coffin_head.png");
	public static final ResourceLocation COFFIN_FOOT = TrailierTalesSharedConstants.id("textures/entity/coffin/coffin_foot.png");
	private static final String BASE = "base";
	private static final String LID = "lid";
	private final ModelPart headRoot;
	private final ModelPart headBase;
	private final ModelPart headLid;
	private final ModelPart footRoot;
	private final ModelPart footBase;
	private final ModelPart footLid;

	public CoffinRenderer(@NotNull Context context) {
		this.headRoot = context.bakeLayer(TrailierTalesClient.COFFIN_HEAD); //left
		this.headBase = headRoot.getChild(BASE);
		this.headLid = headRoot.getChild(LID);

		this.footRoot = context.bakeLayer(TrailierTalesClient.COFFIN_FOOT); //right
		this.footBase = footRoot.getChild(BASE);
		this.footLid = footRoot.getChild(LID);
	}

	public static LayerDefinition createHeadLayer() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		modelPartData.addOrReplaceChild(BASE, CubeListBuilder.create().texOffs(0, 18).addBox(0F, 0F, 0F, 16F, 12F, 16F), PartPose.ZERO);
		modelPartData.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 0).addBox(0F, 0F, 0F, 16F, 2F, 16F), PartPose.offset(0F, 12F, 0F));
		return LayerDefinition.create(modelData, 64, 64);
	}

	public static LayerDefinition createFootLayer() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		modelPartData.addOrReplaceChild(BASE, CubeListBuilder.create().texOffs(0, 18).addBox(0F, 0F, 0F, 16F, 12F, 16F), PartPose.ZERO);
		modelPartData.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 0).addBox(0F, 0F, 0F, 16F, 2F, 16F), PartPose.offset(0F, 12F, 0F));
		return LayerDefinition.create(modelData, 64, 64);
	}

	@NotNull
	public static ResourceLocation getCoffinTexture(@NotNull CoffinPart part, CoffinSpawnerState state, boolean ominous) {
		if (part == CoffinPart.HEAD) {
			return switch (state) {
				case null, default -> COFFIN_HEAD;
			};
		}
		return switch (state) {
			case null, default -> COFFIN_FOOT;
		};
	}


	@Override
	public void render(@NotNull CoffinBlockEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
		Level level = blockEntity.getLevel();
		poseStack.pushPose();
		if (level != null) {
			float openProg = blockEntity.getOpenProgress(partialTick);
			openProg = 1F - openProg;
			openProg = 1F - openProg * openProg * openProg;

			BlockState blockState = blockEntity.getBlockState();
			DoubleBlockCombiner.NeighborCombineResult<? extends CoffinBlockEntity> neighborCombineResult = DoubleBlockCombiner.combineWithNeigbour(
				RegisterBlockEntities.COFFIN,
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
			poseStack.translate(0.5, 0.5, 0.5);
			poseStack.mulPose(Axis.YP.rotationDegrees(-f));
			poseStack.translate(-0.5, -0.5, -0.5);
			this.renderPiece(
				poseStack,
				buffer,
				part == CoffinPart.HEAD ? this.headRoot : this.footRoot,
				part == CoffinPart.HEAD ? this.headLid : this.footLid,
				getCoffinTexture(part, coffinSpawnerState, false),
				null,
				openProg,
				i,
				packedOverlay,
				false
			);
		} else {
			float f = Direction.SOUTH.toYRot();
			poseStack.translate(0.5, 0.5, 0.5);
			poseStack.mulPose(Axis.YP.rotationDegrees(-f));
			poseStack.translate(-0.5, -0.5, -0.5);
			this.renderPiece(poseStack, buffer, this.headRoot, this.headLid, COFFIN_HEAD, null, 0F, packedLight, packedOverlay, false);
			this.renderPiece(poseStack, buffer, this.footRoot, this.footLid, COFFIN_FOOT, null, 0F, packedLight, packedOverlay, true);
		}
		poseStack.popPose();
	}

	private void renderPiece(
		@NotNull PoseStack poseStack,
		MultiBufferSource bufferSource,
		@NotNull ModelPart modelPart,
		@NotNull ModelPart lid,
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
		lid.zRot = (openProgress * 1.5707964F);
		VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(texture));
		modelPart.render(poseStack, vertexConsumer, packedLight, packedOverlay);
		if (glowingTexture != null) {
			VertexConsumer glowingConsumer = bufferSource.getBuffer(RenderType.eyes(glowingTexture));
			modelPart.render(poseStack, glowingConsumer, packedLight, packedOverlay);
		}
		if (foot) {
			poseStack.popPose();
		}
	}
}
