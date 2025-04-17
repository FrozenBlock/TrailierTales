/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.trailiertales.registry;

import com.mojang.serialization.MapCodec;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.worldgen.processor.CoffinProcessor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifierType;

public final class TTRuleBlockEntityModifiers {
	public static final RuleBlockEntityModifierType<CoffinProcessor> COFFIN_PROCESSOR = register("coffin", CoffinProcessor.CODEC);

	public static void init() {
	}

	private static <P extends RuleBlockEntityModifier> RuleBlockEntityModifierType<P> register(String name, MapCodec<P> codec) {
		return Registry.register(BuiltInRegistries.RULE_BLOCK_ENTITY_MODIFIER, TTConstants.id(name), () -> codec);
	}
}
