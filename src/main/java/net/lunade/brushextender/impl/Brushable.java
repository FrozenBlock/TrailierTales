package net.lunade.brushextender.impl;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface Brushable {

	boolean brush(long l, Level level, Player player, Direction direction, BlockPos pos, BlockState state);

}
