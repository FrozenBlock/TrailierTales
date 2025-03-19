/*
 * Copyright 2025 FrozenBlock
 * This file is part of Trailier Tales.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.trailiertales.registry;

import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.tag.TTItemTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.MultiplyValue;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public final class TTEnchantments {
	public static final ResourceKey<Enchantment> REBRUSH = key("rebrush");
	public static final ResourceKey<Enchantment> REAPING = key("reaping");

	public static void bootstrap(@NotNull BootstrapContext<Enchantment> context) {
		HolderGetter<DamageType> damageTypeHolder = context.lookup(Registries.DAMAGE_TYPE);
		HolderGetter<Enchantment> enchantmentHolder = context.lookup(Registries.ENCHANTMENT);
		HolderGetter<Item> itemHolder = context.lookup(Registries.ITEM);
		HolderGetter<Block> blockHolder = context.lookup(Registries.BLOCK);

		register(
			context,
			REBRUSH,
			Enchantment.enchantment(
					Enchantment.definition(
						itemHolder.getOrThrow(TTItemTags.BRUSH_ENCHANTABLE),
						2,
						3,
						Enchantment.dynamicCost(25, 25),
						Enchantment.dynamicCost(75, 25),
						4,
						EquipmentSlotGroup.HAND
					)
				)
		);

		register(
			context,
			REAPING,
			Enchantment.enchantment(
				Enchantment.definition(
					itemHolder.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
					2,
					3,
					Enchantment.dynamicCost(25, 25),
					Enchantment.dynamicCost(75, 25),
					4,
					EquipmentSlotGroup.HAND
				)
			).withEffect(
				EnchantmentEffectComponents.MOB_EXPERIENCE,
				new MultiplyValue(LevelBasedValue.perLevel(1.2F, 0.3F))
			)
		);
	}

	public static void init() {
	}

	private static void register(@NotNull BootstrapContext<Enchantment> context, ResourceKey<Enchantment> registryKey, Enchantment.@NotNull Builder builder) {
		context.register(registryKey, builder.build(registryKey.location()));
	}

	private static @NotNull ResourceKey<Enchantment> key(String path) {
		return ResourceKey.create(Registries.ENCHANTMENT, TTConstants.id(path));
	}
}
