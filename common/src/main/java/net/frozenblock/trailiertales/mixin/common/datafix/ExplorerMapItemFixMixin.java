/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.trailiertales.mixin.common.datafix;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.datafix.fixes.ExplorerMapItemFix;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExplorerMapItemFix.class)
public class ExplorerMapItemFixMixin {

	@Mutable
	@Shadow
	@Final
	private static Map<String, String> DECORATION_TYPE_TO_ITEM_ID;

	@Mutable
	@Shadow
	@Final
	private static Map<String, String> DECORATION_TYPE_TO_LEGACY_NAME_KEY;

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void trailierTales$patchForCatacombsExplorerMap(CallbackInfo info) {
		final Map<String, String> newDecorationTypeToItemIdMap = new HashMap<>();
		newDecorationTypeToItemIdMap.putAll(DECORATION_TYPE_TO_ITEM_ID);
		newDecorationTypeToItemIdMap.put("trailiertales:catacombs", "trailiertales:buried_catacombs_map");
		DECORATION_TYPE_TO_ITEM_ID = Map.copyOf(newDecorationTypeToItemIdMap);

		final Map<String, String> newDecorationTypeToLegacyNameKey = new HashMap<>();
		newDecorationTypeToLegacyNameKey.putAll(DECORATION_TYPE_TO_LEGACY_NAME_KEY);
		newDecorationTypeToLegacyNameKey.put("trailiertales:catacombs", "filled_map.trailiertales.catacombs");
		DECORATION_TYPE_TO_LEGACY_NAME_KEY = Map.copyOf(newDecorationTypeToLegacyNameKey);
	}
}
