package net.frozenblock.trailiertales.mixin;

import java.util.List;
import java.util.Set;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.trailiertales.TTPreLoadConstants;
import net.frozenblock.trailiertales.config.TTMixinsConfig;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public final class TTMixinPlugin implements IMixinConfigPlugin {
	private TTMixinsConfig mixinsConfig;

	@Override
	public void onLoad(String mixinPackage) {
		this.mixinsConfig = TTMixinsConfig.get();
	}

	@Contract(pure = true)
	@Override
	public @Nullable String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, @NotNull String mixinClassName) {
		if (mixinClassName.contains("apparition.")) return this.mixinsConfig.apparition;
		if (mixinClassName.contains("armor_stand.")) return this.mixinsConfig.armor_stand;
		if (mixinClassName.contains("boat.")) return this.mixinsConfig.boat;
		if (mixinClassName.contains("brush.")) return this.mixinsConfig.brush;
		if (mixinClassName.contains("brushable_block.")) return this.mixinsConfig.brushable_block;
		if (mixinClassName.contains("camel.")) return this.mixinsConfig.camel;
		if (mixinClassName.contains("coffin.")) return this.mixinsConfig.coffin;
		if (mixinClassName.contains("datafix.")) return this.mixinsConfig.datafix;
		if (mixinClassName.contains("dawntrail.")) return this.mixinsConfig.dawntrail;
		if (mixinClassName.contains("decorated_pot.")) return this.mixinsConfig.decorated_pot;
		if (mixinClassName.contains("ectoplasm_block.")) return this.mixinsConfig.ectoplasm_block;
		if (mixinClassName.contains("haunt.")) return this.mixinsConfig.haunt;
		if (mixinClassName.contains("surveyor.")) return this.mixinsConfig.surveyor;
		if (mixinClassName.contains("rail.")) return this.mixinsConfig.rail;

		if (mixinClassName.contains("datagen.")) return FrozenBools.IS_DATAGEN;
		if (mixinClassName.contains("structure_building.")) return TTPreLoadConstants.STRUCTURE_BUILDING_MODE;
		return true;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

	@Contract(pure = true)
	@Override
	public @Nullable List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
