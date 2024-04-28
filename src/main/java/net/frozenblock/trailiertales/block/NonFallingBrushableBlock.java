package net.frozenblock.trailiertales.block;

import net.frozenblock.trailiertales.worldgen.impl.suspicious_handler.SuspiciousData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class NonFallingBrushableBlock extends BrushableBlock {

	public NonFallingBrushableBlock(Block block, SoundEvent soundEvent, SoundEvent soundEvent2, BlockBehaviour.Properties properties) {
		super(block, soundEvent, soundEvent2, properties);
	}

	@Override
	public void tick(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
		BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);
		if (blockEntity instanceof BrushableBlockEntity brushableBlockEntity) {
			SuspiciousData.addLootTableToBrushableBlock(serverLevel, blockPos);
			brushableBlockEntity.checkReset();
		}
	}

	@Override
	public void animateTick(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {

	}

}
