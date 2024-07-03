package net.frozenblock.trailiertales.entity.render.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.TrailierTalesClient;
import net.frozenblock.trailiertales.entity.Ghost;
import net.frozenblock.trailiertales.entity.render.model.GhostModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class GhostRenderer extends MobRenderer<Ghost, GhostModel<Ghost>> {

	private static final ResourceLocation TEXTURE = TrailierConstants.vanillaId("textures/entity/warden/warden.png");

	public GhostRenderer(EntityRendererProvider.Context context) {
		super(context, new GhostModel<>(context.bakeLayer(TrailierTalesClient.GHOST)), 0.3F);
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(Ghost entity) {
		return TEXTURE;
	}
}
