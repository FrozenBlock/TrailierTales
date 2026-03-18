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

package net.frozenblock.trailiertales.client.renderer;

import java.util.function.Function;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.trailiertales.block.impl.CoffinPart;

@Environment(EnvType.CLIENT)
public record MultiblockCoffinResources<T>(T head, T foot) {

	public T select(final CoffinPart part) {
		return switch (part) {
			case HEAD -> this.head;
			case FOOT -> this.foot;
		};
	}

	public <S> MultiblockCoffinResources<S> map(Function<T, S> mapper) {
		return new MultiblockCoffinResources<>(mapper.apply(this.head), mapper.apply(this.foot));
	}
}
