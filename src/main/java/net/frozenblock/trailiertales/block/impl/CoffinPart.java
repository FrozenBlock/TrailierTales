package net.frozenblock.trailiertales.block.impl;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum CoffinPart implements StringRepresentable {
	HEAD("head"),
	FOOT("foot");

	private final String name;

	CoffinPart(final String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

	@Override
	public @NotNull String getSerializedName() {
		return this.name;
	}
}

