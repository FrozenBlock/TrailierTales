package net.lunade.onetwenty.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrushItem.class)
public class BrushParticleMixin {

	@Unique
	BlockHitResult luna120$blockHitResult;

	@ModifyVariable(method = "onUseTick", at = @At("STORE"), ordinal = 0)
	public BlockHitResult luna120$captureBlockHitResult(BlockHitResult blockHitResult) {
		this.luna120$blockHitResult = blockHitResult;
		return blockHitResult;
	}

	@Inject(method = "onUseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/BrushItem;getUseDuration(Lnet/minecraft/world/item/ItemStack;)I", shift = At.Shift.AFTER))
	public void luna120$onUseTick(Level level, LivingEntity livingEntity2, ItemStack itemStack, int i, CallbackInfo info) {
		int j = BrushItem.class.cast(this).getUseDuration(itemStack) - i + 1;
		if (j % 5 == 0 && j % 10 != 0 && this.luna120$blockHitResult != null && livingEntity2 instanceof Player player) {
			BlockPos blockPos = luna120$blockHitResult.getBlockPos();
			this.luna120$spawnOppositeDustParticles(level, this.luna120$blockHitResult, level.getBlockState(blockPos), livingEntity2.getViewVector(0.0f));
			level.playSound(player, blockPos, SoundEvents.BRUSH_BRUSHING, SoundSource.PLAYERS, 0.4F, 0.9F);
		}
	}

	@Unique
	public void luna120$spawnOppositeDustParticles(Level level, BlockHitResult blockHitResult, BlockState blockState, Vec3 vec3) {
		int i = level.getRandom().nextInt(2, 6);
		BlockParticleOption blockParticleOption = new BlockParticleOption(ParticleTypes.BLOCK, blockState);
		Direction direction = blockHitResult.getDirection().getOpposite();
		BrushItem.DustParticlesDelta dustParticlesDelta = BrushItem.DustParticlesDelta.fromDirection(vec3, direction);
		Vec3 vec32 = blockHitResult.getLocation();

		for(int j = 0; j < i; ++j) {
			level.addParticle(blockParticleOption, vec32.x - (double)(direction == Direction.WEST ? 1.0E-6F : 0.0F), vec32.y, vec32.z - (double)(direction == Direction.NORTH ? 1.0E-6F : 0.0F), dustParticlesDelta.xd() * 3.0D * level.getRandom().nextDouble(), 0.0D, dustParticlesDelta.zd() * 3.0D * level.getRandom().nextDouble());
		}
	}
}
