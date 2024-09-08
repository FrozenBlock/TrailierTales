package net.frozenblock.trailiertales.block.impl;

import net.frozenblock.trailiertales.block.entity.coffin.CoffinSpawnerState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class TTBlockStateProperties {
	public static final EnumProperty<CoffinPart> COFFIN_PART = EnumProperty.create("part", CoffinPart.class);
	public static final EnumProperty<CoffinSpawnerState> COFFIN_STATE = EnumProperty.create("state", CoffinSpawnerState.class);
	public static final BooleanProperty CAN_PLACE_ITEM = BooleanProperty.create("can_place_item");
}
