package net.frozenblock.trailiertales.mixin;

import java.util.List;
import java.util.Set;
import net.frozenblock.trailiertales.config.MixinsConfig;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public final class TTMixinPlugin implements IMixinConfigPlugin {
	@Override
	public void onLoad(String mixinPackage) {}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		MixinsConfig config = MixinsConfig.get();

		if (mixinClassName.contains("brush")) return config.brush;
		if (mixinClassName.contains("brushable_block")) return config.brushable_block;
		if (mixinClassName.contains("coffin")) return config.coffin;
		if (mixinClassName.contains("decorated_pot")) return config.decorated_pot;
		if (mixinClassName.contains("haunt")) return config.haunt;
		if (mixinClassName.contains("surveyor")) return config.surveyor;

		return true;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

	@Override
	public List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
