package net.frozenblock.trailiertales.entity.render.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.entity.api.rendering.FrozenRenderType;
import net.frozenblock.trailiertales.entity.Apparition;
import net.minecraft.client.model.geom.ModelPart;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ApparitionAidModel<T extends Apparition> extends ApparitionModel<T> {

	public ApparitionAidModel(@NotNull ModelPart root) {
		super(FrozenRenderType::apparitionOuter, root);
	}

	@Override
	public float getOuterTransparency(@NotNull Apparition entity, float tickDelta) {
		return entity.getAidAnimProgress(tickDelta);
	}

	@Override
	public float getTransparency(@NotNull Apparition entity, float tickDelta) {
		return entity.getAidAnimProgress(tickDelta);
	}
}
