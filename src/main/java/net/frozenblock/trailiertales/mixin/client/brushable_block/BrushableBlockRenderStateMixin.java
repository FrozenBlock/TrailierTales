package net.frozenblock.trailiertales.mixin.client.brushable_block;

import net.frozenblock.trailiertales.impl.client.BrushableBlockRenderStateInterface;
import net.minecraft.client.renderer.blockentity.state.BrushableBlockRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BrushableBlockRenderState.class)
public class BrushableBlockRenderStateMixin implements BrushableBlockRenderStateInterface {
	@Unique
	private float xOffset;
	@Unique
	private float yOffset;
	@Unique
	private float zOffset;
	@Unique
	private float rotation;
	@Unique
	private float itemScale;

	@Override
	public float trailierTales$getXOffset() {
		return this.xOffset;
	}

	@Override
	public void trailierTales$setXOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	@Override
	public float trailierTales$getYOffset() {
		return this.yOffset;
	}

	@Override
	public void trailierTales$setYOffset(float yOffset) {
		this.yOffset = yOffset;
	}

	@Override
	public float trailierTales$getZOffset() {
		return this.zOffset;
	}

	@Override
	public void trailierTales$setZOffset(float zOffset) {
		this.zOffset = zOffset;
	}

	@Override
	public float trailierTales$getRotation() {
		return this.rotation;
	}

	@Override
	public void trailierTales$setRotation(float rotation) {
		this.rotation = rotation;
	}

	@Override
	public float trailierTales$getItemScale() {
		return this.itemScale;
	}

	@Override
	public void trailierTales$setItemScale(float itemScale) {
		this.itemScale = itemScale;
	}
}
