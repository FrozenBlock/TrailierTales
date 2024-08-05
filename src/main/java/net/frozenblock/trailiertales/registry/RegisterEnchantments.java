package net.frozenblock.trailiertales.registry;

import net.frozenblock.trailiertales.TrailierConstants;
import net.frozenblock.trailiertales.tag.TrailierItemTags;
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
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.MultiplyValue;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class RegisterEnchantments {
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
						itemHolder.getOrThrow(TrailierItemTags.BRUSH_ENCHANTABLE),
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
				new MultiplyValue(LevelBasedValue.perLevel(1.3F, 0.225F))
			)
		);
	}

	public static void init() {
	}

	private static void register(@NotNull BootstrapContext<Enchantment> context, ResourceKey<Enchantment> registryKey, Enchantment.@NotNull Builder builder) {
		context.register(registryKey, builder.build(registryKey.location()));
	}

	private static @NotNull ResourceKey<Enchantment> key(String path) {
		return ResourceKey.create(Registries.ENCHANTMENT, TrailierConstants.id(path));
	}
}
