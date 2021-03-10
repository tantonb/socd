package net.tantonb.socd.world.dimx;

import com.mojang.serialization.Codec;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.FuzzedBiomeMagnifier;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.tantonb.socd.world.DimensionGenerator;
import net.tantonb.socd.world.DimensionSettingsGenerator;

import java.util.OptionalLong;
import java.util.function.Supplier;

public class DimxGenerator extends DimensionGenerator {

    public DimxGenerator(
            ResourceLocation dimensionId,
            ResourceLocation dimChunkGenId,
            ResourceLocation dimBiomeProviderId,
            RegistryKey<DimensionSettings> dimensionSettingsKey,
            RegistryKey<Dimension> dimensionKey)
    {
        super(dimensionId, dimChunkGenId, dimBiomeProviderId, dimensionSettingsKey, dimensionKey);
    }

    protected DimensionSettingsGenerator getDimensionSettingsGenerator() {
        return new DimxSettingsGenerator();
    }

    protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
        return DimxBiomeProvider.CODEC;
    }

    protected BiomeProvider getBiomeProvider(long seed, Registry<Biome> biomeRegistry) {
        return new DimxBiomeProvider(seed, biomeRegistry);
    }

    protected Codec<? extends ChunkGenerator> getChunkGeneratorCodec() {
        return DimxChunkGenerator.CODEC;
    }

    protected ChunkGenerator getChunkGenerator(
            long seed,
            MutableRegistry<Biome> biomeRegistry,
            MutableRegistry<DimensionSettings> dimensionSettingsRegistry)
    {
        BiomeProvider biomeProvider = getBiomeProvider(seed, biomeRegistry);
        Supplier<DimensionSettings> dimSettingsSupplier =
                () -> dimensionSettingsRegistry.getOrThrow(dimensionSettingsKey);
        return new NoiseChunkGenerator(biomeProvider, seed, dimSettingsSupplier);
    }

    protected DimensionType getDimensionType() {
        return new DimensionType(
                OptionalLong.of(6000L), true, false, false, true,
                1, false, false, true,
                false, false, 256,
                FuzzedBiomeMagnifier.INSTANCE, BlockTags.INFINIBURN_OVERWORLD.getName(),
                dimId, 0.0F)
        {
        };
    }
}
