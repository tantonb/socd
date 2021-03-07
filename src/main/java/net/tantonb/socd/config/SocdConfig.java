package net.tantonb.socd.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class SocdConfig {

    private static final Logger LOGGER = LogManager.getLogger();

    private static SocdConfig instance;

    private ForgeConfigSpec serverSpec;
    private ForgeConfigSpec clientSpec;

    public final ServerConfig server;
    public final ClientConfig client;

    private SocdConfig() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        server = new ServerConfig(builder);
        serverSpec = builder.build();

        builder = new ForgeConfigSpec.Builder();
        client = new ClientConfig(builder);
        clientSpec = builder.build();
    }

    /**
     * Called during mod loading to generate a config instance containing config data.
     *♠
     * @return the generated config singleton instance
     */
    public static SocdConfig init() {
        if (instance == null) {
            LOGGER.info("Initializing configuration...");
            instance = new SocdConfig();
            ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, instance.serverSpec, "socd.toml");
            ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, instance.clientSpec, "socd-client.toml");
        }
        else {
            LOGGER.warn("Attempt to reinitialize SocdConfig ignored...");
        }
        return instance;
    }

    public static SocdConfig get() {
        return Objects.requireNonNull(instance,
                "SocdConfig instance is null, has init() been called yet?");
    }
}
