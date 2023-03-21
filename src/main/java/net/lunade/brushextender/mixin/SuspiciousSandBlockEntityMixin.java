package net.lunade.brushextender.mixin;

import net.lunade.brushextender.impl.Brushable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SuspiciousSandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SuspiciousSandBlockEntity.class)
public class SuspiciousSandBlockEntityMixin implements Brushable {

	@Shadow
	public boolean brush(long l, Player player, Direction direction) {
		throw new AssertionError("Mixin injection failed - Brush Extender SuspiciousSandBlockEntityMixin.");
	}

	@Unique
	@Override
	public boolean brush(long l, Level level, Player player, Direction direction, BlockPos pos, BlockState state) {
		return this.brush(l, player, direction);
	}
}
