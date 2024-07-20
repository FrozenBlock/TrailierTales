package net.frozenblock.trailiertales.registry;

import com.mojang.serialization.MapCodec;
import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.worldgen.processor.CoffinProcessor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifierType;

public class RegsiterRuleBlockEntityModifiers {
	public static final RuleBlockEntityModifierType<CoffinProcessor> COFFIN_PROCESSOR = register("coffin", CoffinProcessor.CODEC);

	public static void init() {
	}

	private static <P extends RuleBlockEntityModifier> RuleBlockEntityModifierType<P> register(String name, MapCodec<P> codec) {
		return Registry.register(BuiltInRegistries.RULE_BLOCK_ENTITY_MODIFIER, TrailierConstants.id(name), () -> codec);
	}
}
