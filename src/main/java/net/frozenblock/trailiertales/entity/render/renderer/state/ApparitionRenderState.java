package net.frozenblock.trailiertales.entity.render.renderer.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

@Environment(EnvType.CLIENT)
public class ApparitionRenderState extends LivingEntityRenderState {

	public int hurtTime;
	public float itemYRot;
	public float itemZRot;
	public float totalTransparency;
	public float flicker;
}
