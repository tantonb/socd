package net.tantonb.socd.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
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
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.tantonb.socd.util.SeedStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

/**
 * Base class for mod custom dimension generation.
 */
abstract public class DimensionGenerator {

    protected static final Logger LOGGER = LogManager.getLogger();

    protected ResourceLocation dimId;
    protected ResourceLocation dimChunkGenId;
    protected ResourceLocation dimBiomeProviderId;
    protected RegistryKey<DimensionSettings> dimensionSettingsKey;
    protected RegistryKey<Dimension> dimensionKey;

    public DimensionGenerator(
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

    abstract protected DimensionSettingsGenerator getDimensionSettingsGenerator();

    abstract protected Codec<? extends BiomeProvider> getBiomeProviderCodec();

    abstract protected Codec<? extends ChunkGenerator> getChunkGeneratorCodec();

    /**
     * Called during mod loading phase, server has not been started,
     * things like the world seed are not available...
     *
     * Registrations for noise settings, chunk generation and biome providers
     * can be performed here.
     */
    public void onModLoad() {
        LOGGER.info("Registering dimension {} settings", dimId.getPath());
        DimensionSettings settings = getDimensionSettingsGenerator().getDimensionSettings();
        WorldGenRegistries.register(WorldGenRegistries.NOISE_SETTINGS, dimensionSettingsKey.getLocation(), settings);
        Registry.register(Registry.CHUNK_GENERATOR_CODEC, dimChunkGenId, getChunkGeneratorCodec());
        Registry.register(Registry.BIOME_PROVIDER_CODEC, dimBiomeProviderId, getBiomeProviderCodec());
    }

    abstract protected ChunkGenerator getChunkGenerator(
            long seed,
            MutableRegistry<Biome> biomeRegistry,
            MutableRegistry<DimensionSettings> dimensionSettingsRegistry
    );

    abstract protected DimensionType getDimensionType();

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

        ChunkGenerator chunkGenerator = getChunkGenerator(seed - 100, biomeRegistry, dimensionSettingsRegistry);
        Supplier<DimensionType> dimTypeSupplier = () -> getDimensionType();
        Dimension dimension = new Dimension(dimTypeSupplier, chunkGenerator);
        dimensionRegistry.register(dimensionKey, dimension, Lifecycle.stable());
    }
}