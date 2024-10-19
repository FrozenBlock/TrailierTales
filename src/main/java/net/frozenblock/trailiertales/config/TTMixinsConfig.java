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
			false,
			null,
			null
		)
	);

	public boolean armor_stand = true;

	public boolean boat = true;

	public boolean brush = true;

	public boolean brushable_block = true;

	public boolean camel = true;

	public boolean coffin = true;

	public boolean datafix = true;

	public boolean dawntrail = true;

	public boolean decorated_pot = true;

	@Comment("Client only")
	public boolean haunt = true;

	public boolean surveyor = true;

	public static TTMixinsConfig get() {
		return INSTANCE.config();
	}
}
