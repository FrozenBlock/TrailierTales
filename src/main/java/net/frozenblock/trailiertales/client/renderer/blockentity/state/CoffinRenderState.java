package net.frozenblock.trailiertales.client.renderer.blockentity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawnerState;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;

@Environment(EnvType.CLIENT)
public class CoffinRenderState extends BlockEntityRenderState {

	public float openProgress;
	public CoffinSpawnerState spawnerState;
	public boolean ominous;
}
