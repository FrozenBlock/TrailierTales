package net.frozenblock.trailiertales.entity.render.renderer.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class ApparitionRenderState extends LivingEntityRenderState {

	public int hurtTime;
	public float itemYRot;
	public float itemZRot;
	public float totalTransparency;
	public float innerTransparency;
	public float outlineTransparency;
	public float outerTransparency;
	public float flicker;

	public ItemStack visibleItem;
	public float aidAnimProgress;
	public float poltergeistAnimProgress;
}
