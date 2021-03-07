package net.tantonb.socd.world;

import com.mojang.serialization.Lifecycle;
import net.minecraft.block.BlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.NoiseSettings;
import net.tantonb.socd.util.SeedStore;
import net.tantonb.socd.world.dimx.DimxBiomeProvider;
import net.tantonb.socd.world.dimx.DimxChunkGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

/**
 * Base class for mod custom dimension generation.
 */
abstract public class SocdDimGenerator {

    protected static final Logger LOGGER = LogManager.getLogger();

    protected ResourceLocation dimId;
    protected ResourceLocation dimChunkGenId;
    protected ResourceLocation dimBiomeProviderId;
    protected RegistryKey<DimensionSettings> dimensionSettingsKey;
    protected RegistryKey<Dimension> dimensionKey;

    public SocdDimGenerator(
            ResourceLocation dimId,
            ResourceLocation dimChunkGenId,
            ResourceLocation dimBiomeProviderId,
            RegistryKey<DimensionSettings> dimSettingsKey,
            RegistryKey<Dimension> dimKey) {
        this.dimId = dimId;
        this.dimChunkGenId = dimChunkGenId;
        this.dimBiomeProviderId = dimBiomeProviderId;
        this.dimensionSettingsKey = dimSettingsKey;
        this.dimensionKey = dimKey;
    }

    protected DimensionStructuresSettings createStructuresSettings() {
        return new DimensionStructuresSettings(false);
    }

    abstract protected NoiseSettings createNoiseSettings();

    abstract protected BlockState getFiller();

    abstract protected BlockState getFluid();

    abstract protected int getBedrockFloor();

    abstract protected int getBedrockCeiling();

    abstract protected int getSeaLevel();

    abstract protected boolean getDisableMobGenFlag();

    protected DimensionSettings createDimensionSettings() {
        return new DimensionSettings(
                createStructuresSettings(),
                createNoiseSettings(),
                getFiller(),
                getFluid(),
                getBedrockFloor(),
                getBedrockCeiling(),
                getSeaLevel(),
                getDisableMobGenFlag()
        );
    }

    /**
     * Called during mod loading phase, server has not been started,
     * things like the world seed are not available...
     *
     * Registrations for noise settings, chunk generation and biome providers
     * can be performed here.
     */
    public void onModLoad() {
        LOGGER.info("Registering dimension {} settings", dimId.getPath());
        WorldGenRegistries.register(
                WorldGenRegistries.NOISE_SETTINGS,
                dimensionSettingsKey.getLocation(),
                createDimensionSettings()
        );
        Registry.register(Registry.CHUNK_GENERATOR_CODEC, dimChunkGenId, DimxChunkGenerator.CODEC);
        Registry.register(Registry.BIOME_PROVIDER_CODEC, dimBiomeProviderId, DimxBiomeProvider.CODEC);
    }

    abstract protected ChunkGenerator createChunkGenerator(
            long seed,
            MutableRegistry<Biome> biomeRegistry,
            MutableRegistry<DimensionSettings> dimensionSettingsRegistry
    );

    abstract protected DimensionType createDimensionType();

    /**
     * Called during server startup.
     *
     * @param server
     */
    public void onServerStartup(MinecraftServer server) {

        LOGGER.info("Registering dimension {}", dimId.getPath());

        DimensionGeneratorSettings dgs = server.getServerConfiguration().getDimensionGeneratorSettings();
        long seed = dgs.getSeed();
        MutableRegistry<Biome> biomeRegistry = server.field_240767_f_.getRegistry(Registry.BIOME_KEY);
        MutableRegistry<DimensionSettings> dimensionSettingsRegistry = server.field_240767_f_.getRegistry(Registry.NOISE_SETTINGS_KEY);
        SimpleRegistry<Dimension> dimensionRegistry = dgs.func_236224_e_();

        SeedStore.setSeed(seed);
        LOGGER.info("onServerStartup(), seed = {}", seed);

        ChunkGenerator chunkGenerator = createChunkGenerator(seed - 100, biomeRegistry, dimensionSettingsRegistry);
        Supplier<DimensionType> dimTypeSupplier = () -> createDimensionType();
        Dimension dimension = new Dimension(dimTypeSupplier, chunkGenerator);
        dimensionRegistry.register(dimensionKey, dimension, Lifecycle.stable());
    }
}