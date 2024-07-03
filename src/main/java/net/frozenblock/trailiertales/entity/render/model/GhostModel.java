package net.frozenblock.trailiertales.entity.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.entity.api.rendering.FrozenRenderType;
import net.frozenblock.trailiertales.entity.Ghost;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.FastColor;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class GhostModel<T extends Ghost> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart body;

	public GhostModel(@NotNull ModelPart root) {
		super(FrozenRenderType::entityTranslucentEmissiveFixed);
		this.root = root;
		ModelPart bone = root.getChild("bone");
		this.body = bone.getChild("body");
	}

	@NotNull
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		PartDefinition bone = partDefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0F, 14F, 0F));
		bone.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2F, -4F, 8F, 5F, 8F)
			.texOffs(4, 13).addBox(-3F, -1F, -3F, 6F, 3F, 6F), PartPose.ZERO);
		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, int colorBad) {
		poseStack.pushPose();
		this.body.render(poseStack, buffer, packedLight, packedOverlay, colorBad);
		poseStack.popPose();
	}

	@Override
	@NotNull
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}
}
