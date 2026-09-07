/*
 * Copyright 2025-2026 FrozenBlock
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

import com.mojang.serialization.Codec;
import net.frozenblock.lib.platform.api.attachment.DataAttachmentSyncPredicate;
import net.frozenblock.lib.platform.api.attachment.DataAttachmentType;
import net.frozenblock.trailiertales.TTConstants;
import net.frozenblock.trailiertales.block.entity.coffin.impl.EntityCoffinData;
import net.frozenblock.trailiertales.block.impl.BrushableBlockAnimationState;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTable;

public final class TTAttachmentTypes {
	public static final DataAttachmentType<ItemStack> BOAT_BANNER = DataAttachmentType.create(
		TTConstants.id("boat_banner"),
		builder -> {
			builder.persistent(ItemStack.CODEC);
			builder.syncWith(ItemStack.STREAM_CODEC, DataAttachmentSyncPredicate.all());
			builder.initializer(() -> ItemStack.EMPTY);
		}
	);
	public static final DataAttachmentType<EntityCoffinData> ENTITY_COFFIN_DATA = DataAttachmentType.create(
		TTConstants.id("entity_coffin_data"),
		builder -> builder.persistent(EntityCoffinData.CODEC)
	);

	// BRUSHABLE BLOCK
	public static final DataAttachmentType<BrushableBlockAnimationState> BRUSHABLE_BLOCK_ANIMATION_STATE = DataAttachmentType.create(
		TTConstants.id("brushable_block_animation_state"),
		builder -> builder.syncWith(BrushableBlockAnimationState.STREAM_CODEC, DataAttachmentSyncPredicate.all())
	);
	public static final DataAttachmentType<Boolean> BRUSHABLE_BLOCK_REBRUSHED = DataAttachmentType.create(
		TTConstants.id("brushable_block_rebrushed"),
		builder -> builder.persistent(Codec.BOOL)
	);
	public static final DataAttachmentType<ResourceKey<LootTable>> BRUSHABLE_BLOCK_STORED_LOOT_TABLE = DataAttachmentType.create(
		TTConstants.id("brushable_block_stored_loot_table"),
		builder -> builder.persistent(LootTable.KEY_CODEC)
	);

	public static void init() {}

	private TTAttachmentTypes() {}
}
