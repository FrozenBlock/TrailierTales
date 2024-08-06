package net.frozenblock.trailiertales.config;

import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.shadow.blue.endless.jankson.Comment;
import static net.frozenblock.trailiertales.TrailierConstants.MOD_ID;
import static net.frozenblock.trailiertales.TrailierConstants.configPath;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class MixinsConfig {

	public static final Config<MixinsConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			MOD_ID,
			MixinsConfig.class,
			configPath("mixins", true),
			JsonType.JSON5,
			false,
			null,
			null
		)
	);

	public boolean brush = true;

	public boolean brushable_block = true;

	public boolean coffin = true;

	public boolean decorated_pot = true;

	@Comment("Client only")
	public boolean haunt = true;

	public boolean surveyor = true;

	public static MixinsConfig get() {
		return INSTANCE.config();
	}
}
