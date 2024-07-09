package net.frozenblock.trailiertales.entity.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Function;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.entity.api.rendering.FrozenRenderType;
import net.frozenblock.lib.entity.impl.client.rendering.ModelPartInvertInterface;
import net.frozenblock.trailiertales.entity.Apparition;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ApparitionModel<T extends Apparition> extends HierarchicalModel<T> {
	private final ModelPart root;
	public final ModelPart core;
	public final ModelPart outer;

	private float transparency;
	private float outerTransparency;
	private float flicker;

	public ApparitionModel(@NotNull ModelPart root) {
		this(FrozenRenderType::entityTranslucentEmissiveAlwaysRenderCull, root);
	}

	public ApparitionModel(Function<ResourceLocation, RenderType> function, @NotNull ModelPart root) {
		super(function);
		this.root = root;
		this.core = root.getChild("core");
		this.outer = root.getChild("outer");

		ModelPartInvertInterface.class.cast(this.outer).frozenLib$setInverted(true);
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
		this.transparency = this.getTransparency(entity, tickDelta);
		this.outerTransparency = this.getOuterTransparency(entity, tickDelta);
		this.flicker = entity.getFlicker(tickDelta);
		this.outer.yRot = entity.getItemYRot(tickDelta);
		this.outer.zRot = entity.getItemZRot(tickDelta);
	}

	public float getTransparency(@NotNull Apparition entity, float tickDelta) {
		return entity.getTransparency(tickDelta);
	}

	public float getOuterTransparency(@NotNull Apparition entity, float tickDelta) {
		return entity.getOuterTransparency(tickDelta);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, int colorBad) {
		poseStack.pushPose();
		int coreTransparency = FastColor.ARGB32.colorFromFloat(this.transparency * this.flicker, 1F, 1F, 1F);
		if (coreTransparency != 0) {
		this.core.render(poseStack, buffer, 15728640, packedOverlay, coreTransparency);
		}
		int outerTransparency = FastColor.ARGB32.colorFromFloat(this.outerTransparency * this.flicker, 1F, 1F, 1F);
		if (outerTransparency != 0) {
			this.outer.render(poseStack, buffer, 15728640, packedOverlay, outerTransparency);
		}
		poseStack.popPose();
	}
}
