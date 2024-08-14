package net.frozenblock.trailiertales.registry;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class RegisterProperties {
	public static final BooleanProperty CAN_PLACE_ITEM = BooleanProperty.create("can_place_item");
	public static final int MAX_SPREAD_AGE = 3;
	public static final IntegerProperty SPREAD_AGE = IntegerProperty.create("spread_age", 0, MAX_SPREAD_AGE);
}
