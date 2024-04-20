package net.frozenblock.trailiertales.block.impl;

import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawnerState;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class TrailierBlockStateProperties {
	public static final EnumProperty<CoffinPart> COFFIN_PART = EnumProperty.create("part", CoffinPart.class);
	public static final EnumProperty<CoffinSpawnerState> COFFIN_STATE = EnumProperty.create("state", CoffinSpawnerState.class);
}
