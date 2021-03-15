package net.tantonb.socd.world;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.tantonb.socd.util.SeedStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Base class for mod custom dimension generation.
 *
 * Custom dimensions are generated in two stages:
 *
 *      onModLoad() called during mod initialization, will register
 *          noise settings, codecs, etc.
 *
 *      onServerStart() called during server startup, allows access
 *          to world seeds and other world-specific settings
 */
abstract public class DimensionGenerator {

    protected static final Logger LOGGER = LogManager.getLogger();

    private MinecraftServer server;
    private DimensionGeneratorSettings dgs;
    private long seed = 0;

    protected ResourceLocation dimId;

    public DimensionGenerator(ResourceLocation dimId) {
        this.dimId = dimId;
    }

    public ResourceLocation getId() {
        return dimId;
    }

    public String getName() {
        return dimId.getPath();
    }

    // TODO: this just a glorified supplier, need to refactor
    abstract protected DimensionSettingsGenerator getDimensionSettingsGenerator();

    /**
     * Called during mod loading phase, server has not been started, things
     * like the world seed are not available...here we can register noise
     * settings and codecs for data serialization.
     */
    abstract protected void setupDimension();

    /**
     * Called during mod loading phase, server has not been started, things like
     * the world seed are not available...
     */
    public void onModLoad() {

        LOGGER.info("Setting up dimension {} settings", getName());

        setupDimension();
    }

    abstract protected DimensionType getDimensionType();

    /**
     * Save some server data to use for starting up custom dimensions. Currently
     * grabs MinecraftServer, DimensionGeneratorSettings and extracts the world
     * seed from the settings.
     *
     * @param server minecraft server instance
     */
    private void setServerData(MinecraftServer server) {

        this.server = server;
        dgs = server.getServerConfiguration().getDimensionGeneratorSettings();
        seed = dgs.getSeed();

        SeedStore.setSeed(seed); // TODO: move this to SeedStore server startup (or remove SeedStore)
    }

    protected MinecraftServer getServer() {
        return server;
    }

    protected long getSeed() {
        return seed;
    }

    protected DimensionGeneratorSettings getDgs() {
        return dgs;
    }

    abstract protected void startDimension();

    /**
     * Called during server startup.
     *
     * @param server
     */
    public void onServerStart(MinecraftServer server) {

        LOGGER.info("Starting dimension {} on server...", getName());

        setServerData(server);
        startDimension();
    }
}