package net.tantonb.socd.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

/**
 * Base class for mod custom dimension generation.
 */
abstract public class VanillaDimensionGenerator extends DimensionGenerator {

    protected static final Logger LOGGER = LogManager.getLogger();

    protected ResourceLocation dimChunkGenId;
    protected ResourceLocation dimBiomeProviderId;
    protected RegistryKey<DimensionSettings> dimSettingsKey;
    protected RegistryKey<Dimension> dimKey;
    protected long seedModifier = 0;

    public VanillaDimensionGenerator(
            ResourceLocation dimId,
            ResourceLocation dimChunkGenId,
            ResourceLocation dimBiomeProviderId,
            RegistryKey<DimensionSettings> dimSettingsKey,
            RegistryKey<Dimension> dimKey) {
        super(dimId);
        this.dimChunkGenId = dimChunkGenId;
        this.dimBiomeProviderId = dimBiomeProviderId;
        this.dimSettingsKey = dimSettingsKey;
        this.dimKey = dimKey;
    }

    abstract protected Codec<? extends BiomeProvider> getBiomeProviderCodec();

    abstract protected Codec<? extends ChunkGenerator> getChunkGeneratorCodec();

    /**
     * Vanilla dimensions use NoiseChunkGenerators that will require
     * registration of noise settings and chunk generator and biome provider
     * codecs.
     */
    protected void setupDimension() {
        DimensionSettings settings = getDimensionSettingsGenerator().getDimensionSettings();
        WorldGenRegistries.register(WorldGenRegistries.NOISE_SETTINGS, dimSettingsKey.getLocation(), settings);
        Registry.register(Registry.CHUNK_GENERATOR_CODEC, dimChunkGenId, getChunkGeneratorCodec());
        Registry.register(Registry.BIOME_PROVIDER_CODEC, dimBiomeProviderId, getBiomeProviderCodec());
    }

    protected long modifySeed(long seed) {
        return seed + seedModifier;
    }

    @Override
    protected long getSeed() {
        return modifySeed(super.getSeed());
    }

    abstract protected BiomeProvider getBiomeProvider(long seed, Registry<Biome> biomeRegistry);

    abstract protected ChunkGenerator createChunkGenerator(BiomeProvider bp, long seed, Supplier<DimensionSettings> dss);

    protected ChunkGenerator getChunkGenerator(
            long seed,
            MutableRegistry<Biome> biomeRegistry,
            MutableRegistry<DimensionSettings> dimensionSettingsRegistry)
    {
        BiomeProvider biomeProvider = getBiomeProvider(seed, biomeRegistry);
        Supplier<DimensionSettings> dimSettingsSupplier =
                () -> dimensionSettingsRegistry.getOrThrow(dimSettingsKey);
        return createChunkGenerator(biomeProvider, seed, dimSettingsSupplier);
    }

    abstract protected DimensionType getDimensionType();

    /**
     * Do dimension setup needed during server startup.
     *
     * Vanilla dimensions need a Dimension registered.  Dimensions need a DimensionType
     * containing various dimension wide settings (e.g. things differentiating overworld
     * from the nether, such as whether there's a sky), and a NoiseChunkGenerator.  The
     * chunk generators need a BiomeProvider which generates per coordinate biomes.  Vanilla
     * dimension biome providers and chunk generators need the world seed or a modified
     * version of it to procedurally generate noise-based randomness.
     */
    protected void startDimension() {

        MutableRegistry<Biome> biomeRegistry = getServer().field_240767_f_.getRegistry(Registry.BIOME_KEY);
        MutableRegistry<DimensionSettings> dimensionSettingsRegistry =
                getServer().field_240767_f_.getRegistry(Registry.NOISE_SETTINGS_KEY);
        SimpleRegistry<Dimension> dimensionRegistry = getDgs().func_236224_e_();

        ChunkGenerator chunkGenerator = getChunkGenerator(getSeed(), biomeRegistry, dimensionSettingsRegistry);
        Supplier<DimensionType> dimTypeSupplier = this::getDimensionType;
        Dimension dimension = new Dimension(dimTypeSupplier, chunkGenerator);
        dimensionRegistry.register(dimKey, dimension, Lifecycle.stable());
    }
}