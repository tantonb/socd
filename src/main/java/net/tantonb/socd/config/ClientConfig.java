package net.tantonb.socd.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientConfig {

    private static final Logger LOGGER = LogManager.getLogger();

    public final ForgeConfigSpec.BooleanValue showCustomWorldWarning;

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        LOGGER.info("Loading client configuration...");
        // TODO: remove show custom world warning option...
        builder.comment("Should game show custom world warning?");
        showCustomWorldWarning = builder.define("show_custom_world_warning", false);
    }
}
