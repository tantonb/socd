package net.tantonb.socd.world.dimy;

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
import net.tantonb.socd.world.VanillaDimensionGenerator;
import net.tantonb.socd.world.DimensionSettingsGenerator;
import net.tantonb.socd.world.dimx.DimxChunkGenerator;

import java.util.OptionalLong;
import java.util.function.Supplier;

public class DimyGenerator extends VanillaDimensionGenerator {

    public static long SEED_OFFSET = 1002L;

    public DimyGenerator(
            ResourceLocation dimensionId,
            ResourceLocation dimChunkGenId,
            ResourceLocation dimBiomeProviderId,
            RegistryKey<DimensionSettings> dimensionSettingsKey,
            RegistryKey<Dimension> dimensionKey)
    {
        super(dimensionId, dimChunkGenId, dimBiomeProviderId, dimensionSettingsKey, dimensionKey);
        seedModifier = SEED_OFFSET;
    }

    protected DimensionSettingsGenerator getDimensionSettingsGenerator() {
        return new DimySettingsGenerator();
    }

    protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
        return DimyBiomeProvider.CODEC;
    }

    protected BiomeProvider getBiomeProvider(long seed, Registry<Biome> biomeRegistry) {
        return new DimyBiomeProvider(seed, biomeRegistry);
    }

    protected Codec<? extends ChunkGenerator> getChunkGeneratorCodec() {
        return DimyChunkGenerator.CODEC;
    }

    protected ChunkGenerator createChunkGenerator(BiomeProvider bp, long seed, Supplier<DimensionSettings> dss) {
        return new DimyChunkGenerator(bp, seed, dss);
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
