package net.frozenblock.trailiertales.block.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

@Environment(EnvType.CLIENT)
public class CoffinRenderer implements BlockEntityRenderer<BedBlockEntity> {
	private final ModelPart headRoot;
	private final ModelPart footRoot;

	public CoffinRenderer(Context context) {
		this.headRoot = context.bakeLayer(ModelLayers.BED_HEAD);
		this.footRoot = context.bakeLayer(ModelLayers.BED_FOOT);
	}

	public static LayerDefinition createHeadLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		partDefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F), PartPose.ZERO);
		partDefinition.addOrReplaceChild(
			"left_leg",
			CubeListBuilder.create().texOffs(50, 6).addBox(0.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F),
			PartPose.rotation((float) (Math.PI / 2), 0.0F, (float) (Math.PI / 2))
		);
		partDefinition.addOrReplaceChild(
			"right_leg",
			CubeListBuilder.create().texOffs(50, 18).addBox(-16.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F),
			PartPose.rotation((float) (Math.PI / 2), 0.0F, (float) Math.PI)
		);
		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	public static LayerDefinition createFootLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		partDefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 22).addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F), PartPose.ZERO);
		partDefinition.addOrReplaceChild(
			"left_leg", CubeListBuilder.create().texOffs(50, 0).addBox(0.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F), PartPose.rotation((float) (Math.PI / 2), 0.0F, 0.0F)
		);
		partDefinition.addOrReplaceChild(
			"right_leg",
			CubeListBuilder.create().texOffs(50, 12).addBox(-16.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F),
			PartPose.rotation((float) (Math.PI / 2), 0.0F, (float) (Math.PI * 3.0 / 2.0))
		);
		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	public void render(BedBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
		Material material = Sheets.BED_TEXTURES[blockEntity.getColor().getId()];
		Level level = blockEntity.getLevel();
		if (level != null) {
			BlockState blockState = blockEntity.getBlockState();
			DoubleBlockCombiner.NeighborCombineResult<? extends BedBlockEntity> neighborCombineResult = DoubleBlockCombiner.combineWithNeigbour(
				BlockEntityType.BED,
				BedBlock::getBlockType,
				BedBlock::getConnectedDirection,
				ChestBlock.FACING,
				blockState,
				level,
				blockEntity.getBlockPos(),
				(world, pos) -> false
			);
			int i = neighborCombineResult.<Int2IntFunction>apply(new BrightnessCombiner()).get(packedLight);
			this.renderPiece(
				poseStack,
				buffer,
				blockState.getValue(BedBlock.PART) == BedPart.HEAD ? this.headRoot : this.footRoot,
				(Direction)blockState.getValue(BedBlock.FACING),
				material,
				i,
				packedOverlay,
				false
			);
		} else {
			this.renderPiece(poseStack, buffer, this.headRoot, Direction.SOUTH, material, packedLight, packedOverlay, false);
			this.renderPiece(poseStack, buffer, this.footRoot, Direction.SOUTH, material, packedLight, packedOverlay, true);
		}
	}

	/**
	 * @param foot {@code true} if piece to render is the foot of the bed, {@code false} otherwise or if being rendered by a {@link net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer}
	 */
	private void renderPiece(
		PoseStack poseStack,
		MultiBufferSource bufferSource,
		ModelPart modelPart,
		Direction direction,
		Material material,
		int packedLight,
		int packedOverlay,
		boolean foot
	) {
		poseStack.pushPose();
		poseStack.translate(0.0F, 0.5625F, foot ? -1.0F : 0.0F);
		poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
		poseStack.translate(0.5F, 0.5F, 0.5F);
		poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F + direction.toYRot()));
		poseStack.translate(-0.5F, -0.5F, -0.5F);
		VertexConsumer vertexConsumer = material.buffer(bufferSource, RenderType::entitySolid);
		modelPart.render(poseStack, vertexConsumer, packedLight, packedOverlay);
		poseStack.popPose();
	}
}
