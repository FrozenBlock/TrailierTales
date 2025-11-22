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

package net.frozenblock.trailiertales.config;

import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.shadow.blue.endless.jankson.Comment;
import static net.frozenblock.trailiertales.TTConstants.MOD_ID;
import net.frozenblock.trailiertales.TTPreLoadConstants;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class TTMixinsConfig {
	public static final Config<TTMixinsConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			MOD_ID,
			TTMixinsConfig.class,
			TTPreLoadConstants.configPath("mixins", true),
			JsonType.JSON5,
			false
		)
	);

	@Comment("Required for Apparitions to interact with Ectoplasm Blocks properly")
	public boolean apparition = true;

	@Comment("Gives Armor Stands arms by default")
	public boolean armor_stand = true;

	@Comment("Required for Banner Boats")
	public boolean boat = true;

	@Comment("Makes Brush animations smoother")
	public boolean brush = true;

	@Comment("Required for the Rebrush enchantment and smooth animations to work")
	public boolean brushable_block = true;

	@Comment("Required to let the Camel spawn naturally")
	public boolean camel = true;

	@Comment("Required for Coffin-specific mob mechanics to work")
	public boolean coffin = true;

	@Comment("Required to allow Trailier Tales' content to be datafixed alongside vanilla content")
	public boolean datafix = true;

	@Comment("Required to let Dawntrails be sheared by Dispensers")
	public boolean dawntrail = true;

	@Comment("Mirror's the Decorated Pots wobble animation on each consecutive use")
	public boolean decorated_pot = true;

	@Comment("Required to let the player's cursor ignore Ectoplasm Blocks while inside them")
	public boolean ectoplasm_block = true;

	@Comment("Required for Apparitions to not collide with all blocks when Lithium is installed")
	public boolean lithium = true;

	@Comment("Required for a structure gen bugfix related to rail rotation")
	public boolean rail = true;

	@Comment("Required to let Redstone Wire connect to Surveyors")
	public boolean surveyor = true;

	@Comment("Client only")
	public boolean haunt = true;

	public static TTMixinsConfig get() {
		return INSTANCE.config();
	}
}
