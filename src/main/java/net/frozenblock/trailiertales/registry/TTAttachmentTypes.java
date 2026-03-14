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

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.frozenblock.trailiertales.TTConstants;
import net.minecraft.world.item.ItemStack;

public final class TTAttachmentTypes {
	public static final AttachmentType<ItemStack> BOAT_BANNER = AttachmentRegistry.create(
		TTConstants.id("boat_banner"),
		builder -> {
			builder.initializer(() -> ItemStack.EMPTY);
			builder.syncWith(ItemStack.STREAM_CODEC, AttachmentSyncPredicate.all());
			builder.persistent(ItemStack.CODEC);
		}
	);

	public static void init() {}

	private TTAttachmentTypes() {
		throw new UnsupportedOperationException("TTAttachmentTypes contains only static declarations.");
	}
}
