package net.frozenblock.trailiertales.mixin.common.brush;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrushItem.class)
public class BrushParticleMixin {

	@Unique
	private BlockHitResult trailierTales$blockHitResult;

	@ModifyVariable(method = "onUseTick", at = @At("STORE"), ordinal = 0)
	public BlockHitResult trailierTales$captureBlockHitResult(BlockHitResult blockHitResult) {
		this.trailierTales$blockHitResult = blockHitResult;
		return blockHitResult;
	}

	@Inject(method = "onUseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/BrushItem;getUseDuration(Lnet/minecraft/world/item/ItemStack;)I", shift = At.Shift.AFTER))
	public void trailierTales$onUseTick(Level level, LivingEntity livingEntity2, ItemStack itemStack, int i, CallbackInfo info) {
		this.trailierTales$halfBrush(level, livingEntity2, itemStack, i);
	}

	@Unique
	public void trailierTales$halfBrush(Level level, LivingEntity livingEntity2, ItemStack itemStack, int i) {
		int j = BrushItem.class.cast(this).getUseDuration(itemStack) - i + 1;
		if (j % 5 == 0 && j % 10 != 5 && this.trailierTales$blockHitResult != null && livingEntity2 instanceof Player player) {
			BlockPos blockPos = trailierTales$blockHitResult.getBlockPos();
			this.trailierTales$spawnOppositeDustParticles(level, this.trailierTales$blockHitResult, level.getBlockState(blockPos), livingEntity2.getViewVector(0.0f), livingEntity2.getUsedItemHand() == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite());
			level.playSound(player, blockPos, SoundEvents.BRUSH_GENERIC, SoundSource.PLAYERS, 0.3F, 0.85F);
		}
	}

	@Unique
	public void trailierTales$spawnOppositeDustParticles(@NotNull Level level, @NotNull BlockHitResult blockHitResult, @NotNull BlockState blockState, @NotNull Vec3 vec3, @NotNull HumanoidArm humanoidArm) {
		int i = humanoidArm == HumanoidArm.RIGHT ? -1 : 1;
		int j = level.getRandom().nextInt(2, 6);
		BlockParticleOption blockParticleOption = new BlockParticleOption(ParticleTypes.BLOCK, blockState);
		Direction direction = blockHitResult.getDirection();
		BrushItem.DustParticlesDelta dustParticlesDelta = BrushItem.DustParticlesDelta.fromDirection(vec3, direction);
		Vec3 vec32 = blockHitResult.getLocation();
		for (int k = 0; k < j; ++k) {
			level.addParticle(blockParticleOption, vec32.x - (double) (direction == Direction.WEST ? 1.0E-6f : 0.0f), vec32.y, vec32.z - (double) (direction == Direction.NORTH ? 1.0E-6f : 0.0f), dustParticlesDelta.xd() * (double) i * 3.0 * level.getRandom().nextDouble(), 0.0, dustParticlesDelta.zd() * (double) i * 3.0 * level.getRandom().nextDouble());
		}
	}

}
