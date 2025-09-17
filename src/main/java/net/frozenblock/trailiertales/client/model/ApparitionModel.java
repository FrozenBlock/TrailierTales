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

package net.frozenblock.trailiertales.client.model;

import java.util.function.Function;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.entity.impl.client.rendering.ModelPartInvertInterface;
import net.frozenblock.lib.render.FrozenLibRenderTypes;
import net.frozenblock.trailiertales.client.renderer.entity.state.ApparitionRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ApparitionModel extends EntityModel<ApparitionRenderState> {
	private final ModelPart root;
	public final ModelPart core;
	public final ModelPart inner;
	public final ModelPart outline;
	public final ModelPart outer;

	public ApparitionModel(@NotNull ModelPart root) {
		this(FrozenLibRenderTypes::apparitionOuter, root);
	}

	public ApparitionModel(Function<ResourceLocation, RenderType> function, @NotNull ModelPart root) {
		super(root, function);
		this.root = root;
		this.core = root.getChild("core");
		this.inner = this.core.getChild("inner");
		this.outline = this.core.getChild("outline");
		this.outer = root.getChild("outer");
	}

	@NotNull
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();

		PartDefinition core = meshdefinition.getRoot().addOrReplaceChild("core", CubeListBuilder.create(), PartPose.ZERO);

		core.addOrReplaceChild("inner", CubeListBuilder.create()
				.texOffs(0, 28)
				.addBox(-5F, -5F, -5F, 10F, 10F, 10F),
			PartPose.offset(0F, 17F, 0F)
		);
		PartDefinition outline = core.addOrReplaceChild("outline", CubeListBuilder.create()
				.texOffs(0, 48)
				.addBox(-5.5F, -5.5F, -5.5F, 11F, 11F, 11F)
				.mirror(),
			PartPose.offset(0F, 17F, 0F)
		);
		ModelPartInvertInterface.class.cast(outline).frozenLib$setInverted();

		meshdefinition.getRoot().addOrReplaceChild("outer", CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-7F, -7F, -7F, 14F, 14F, 14F),
			PartPose.offset(0F, 17F, 0F)
		);

		return LayerDefinition.create(meshdefinition, 80, 80);
	}

	@Override
	public void setupAnim(@NotNull ApparitionRenderState renderState) {
		float limbAngle = renderState.walkAnimationPos;
		float limbDistance = renderState.walkAnimationSpeed;
		float headYaw = renderState.yRot;
		float headPitch = renderState.xRot;
		this.outer.yRot = renderState.itemYRot;
		this.outer.zRot = renderState.itemZRot;

		float animationProgress = renderState.ageInTicks + (limbAngle * 3.5F);
		this.core.yRot = headYaw * Mth.DEG_TO_RAD;
		this.core.xRot = headPitch * Mth.DEG_TO_RAD;

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

	public interface AlphaFunction<T extends ApparitionRenderState> {
		float apply(T apparition);
	}
}
