package net.frozenblock.trailiertales.entity.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.entity.api.rendering.FrozenRenderType;
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
	private final ModelPart core;
	private final ModelPart outer;

	private float transparency;
	private float outerTransparency;
	private float flicker;

	public ApparitionModel(@NotNull ModelPart root) {
		super(FrozenRenderType::entityTranslucentEmissiveAlwaysRender);
		this.root = root;
		this.core = root.getChild("core");
		this.outer = root.getChild("outer");
	}

	@NotNull
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();

		meshdefinition.getRoot().addOrReplaceChild("core", CubeListBuilder.create()
			.texOffs(0, 28).addBox(-5F, -5F, -5F, 10F, 10F, 10F), PartPose.offset(0F, 17F, 0F));

		meshdefinition.getRoot().addOrReplaceChild("outer", CubeListBuilder.create()
			.texOffs(0, 0).addBox(-7F, -7F, -7F, 14F, 14F, 14F),  PartPose.offset(0F, 17F, 0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	@NotNull
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		animationProgress = animationProgress + (limbAngle * 3.5F);
		this.core.yRot = headYaw * ((float) Math.PI / 180F);
		this.core.xRot = headPitch * ((float) Math.PI / 180F);

		float tighten = 1F - limbDistance * 0.75F;
		this.outer.yRot *= tighten;
		this.outer.zRot *= tighten;

		//SQUASH & STRETCH
		float sinIdle = (float) (Math.sin(animationProgress * 0.3F) * 0.1F) * tighten;
		float squash = sinIdle + 1F;

		this.outer.xScale = squash;
		this.outer.zScale = squash;
		this.outer.yScale = -sinIdle + 1;
	}

	@Override
	public void prepareMobModel(@NotNull T entity, float limbAngle, float limbDistance, float tickDelta) {
		this.transparency = 1F;
		this.outerTransparency = 0.5F;
		this.flicker = entity.getFlicker(tickDelta);
		this.outer.yRot = entity.getItemYRot(tickDelta);
		this.outer.zRot = entity.getItemZRot(tickDelta);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, int colorBad) {
		poseStack.pushPose();
		int coreTransparency = FastColor.ARGB32.colorFromFloat(this.transparency * this.flicker, 1F, 1F, 1F);
		this.core.render(poseStack, buffer, packedLight, packedOverlay, coreTransparency);
		int outerTransparency = FastColor.ARGB32.colorFromFloat(this.outerTransparency * this.flicker, 1F, 1F, 1F);
		this.outer.render(poseStack, buffer, packedLight, packedOverlay, outerTransparency);
		poseStack.popPose();
	}
}
