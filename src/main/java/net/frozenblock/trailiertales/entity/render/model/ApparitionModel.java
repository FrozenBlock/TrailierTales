package net.frozenblock.trailiertales.entity.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.entity.Apparition;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.FastColor;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ApparitionModel<T extends Apparition> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart bone;

	private float transparency;

	public ApparitionModel(@NotNull ModelPart root) {
		super(RenderType::entityTranslucentEmissive);
		this.root = root;
		this.bone = root.getChild("bone");
	}

	@NotNull
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		meshdefinition.getRoot().addOrReplaceChild("bone", CubeListBuilder.create()
			.texOffs(0, 28).addBox(-6F, 6F, -6F, 12F, 12F, 12F)
			.texOffs(0, 0).addBox(-7F, 7F, -7F, 14F, 14F, 14F), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	@NotNull
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void prepareMobModel(@NotNull T entity, float limbAngle, float limbDistance, float tickDelta) {
		this.transparency = entity.getTransparency(tickDelta);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, int colorBad) {
		poseStack.pushPose();
		int color = FastColor.ARGB32.colorFromFloat(this.transparency, 1F, 1F, 1F);
		super.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, color);
		poseStack.popPose();
	}
}
