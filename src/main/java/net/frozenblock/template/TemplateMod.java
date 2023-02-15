package net.frozenblock.template;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.template.mod_compat.TemplateModIntegrations;
import net.frozenblock.template.util.TemplateSharedConstants;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixerBuilder;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateMod implements ModInitializer {

	@Override
	public void onInitialize() {
		TemplateSharedConstants.startMeasuring(this);
		applyDataFixes(TemplateSharedConstants.MOD_CONTAINER);

		TemplateModIntegrations.init();

		TemplateSharedConstants.stopMeasuring(this);
	}

	private static void applyDataFixes(final @NotNull ModContainer mod) {
		TemplateSharedConstants.log("Applying DataFixes for FrozenBlock Template Mod with Data Version " + TemplateSharedConstants.DATA_VERSION, true);

		var builder = new QuiltDataFixerBuilder(TemplateSharedConstants.DATA_VERSION);
		builder.addSchema(0, QuiltDataFixes.BASE_SCHEMA);

		QuiltDataFixes.buildAndRegisterFixer(mod, builder);
		TemplateSharedConstants.log("DataFixes for FrozenBlock Template Mod have been applied", true);
	}
}
