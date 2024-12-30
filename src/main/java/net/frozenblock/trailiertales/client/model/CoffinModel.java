package net.frozenblock.trailiertales.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class CoffinModel extends Model {
	private static final String BASE = "base";
	private static final String LID = "lid";
	private final ModelPart base;
	private final ModelPart lid;

	public CoffinModel(ModelPart root) {
		super(root, RenderType::entitySolid);
		this.base = root.getChild(BASE);
		this.lid = root.getChild(LID);
	}

	public static @NotNull LayerDefinition createLayerDefinition() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		modelPartData.addOrReplaceChild(BASE, CubeListBuilder.create().texOffs(0, 18).addBox(0F, 0F, 0F, 16F, 12F, 16F), PartPose.ZERO);
		modelPartData.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 0).addBox(0F, 0F, 0F, 16F, 2F, 16F), PartPose.offset(0F, 12F, 0F));
		return LayerDefinition.create(modelData, 64, 64);
	}


	public void setupAnim(float liftProgress) {
		this.lid.xRot = -(liftProgress * Mth.HALF_PI);
	}
}
