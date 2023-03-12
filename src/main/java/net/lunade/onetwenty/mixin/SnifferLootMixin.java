package net.lunade.onetwenty.mixin;

import net.lunade.onetwenty.Luna120;
import net.lunade.onetwenty.data.Luna120ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Sniffer.class)
public class SnifferLootMixin {

	@ModifyVariable(method = "dropSeed", at = @At("STORE"), ordinal = 0)
	private ItemStack luna120$dropSeed(ItemStack original) {
		return luna120$getNewSnifferItem(Sniffer.class.cast(this).getRandom());
	}

	@Unique
	private static ItemStack luna120$getNewSnifferItem(RandomSource randomSource) {
		ItemStack itemStack = new ItemStack(Luna120.getRandomEntry(randomSource, Luna120ItemTags.COMMON_SNIFFER_LOOT));
		if (randomSource.nextFloat() < 0.3) {
			if (randomSource.nextFloat() < 0.3) {
				itemStack = new ItemStack(Luna120.getRandomEntry(randomSource, Luna120ItemTags.RARE_SNIFFER_LOOT));
			} else {
				itemStack = new ItemStack(Luna120.getRandomEntry(randomSource, Luna120ItemTags.UNCOMMON_SNIFFER_LOOT));
			}
		}
		return itemStack;
	}

}
