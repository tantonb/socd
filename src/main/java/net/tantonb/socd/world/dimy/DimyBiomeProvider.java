package net.tantonb.socd.world.dimy;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class DimyBiomeProvider extends BiomeProvider {

    private static long seedModifier = 1002L;

    public static final Codec<DimyBiomeProvider> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(Codec.LONG.fieldOf("seed").stable().forGetter((provider) -> {
            return provider.seed;
        }), RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter((provider) -> {
            return provider.lookupRegistry;
        })).apply(builder, builder.stable(DimyBiomeProvider::new));
    });
    private static final List<RegistryKey<Biome>> biomes = ImmutableList.of(
            Biomes.OCEAN,
            Biomes.PLAINS,
            Biomes.RIVER,
            Biomes.BEACH,
            Biomes.DEEP_OCEAN,
            Biomes.STONE_SHORE,
            Biomes.SNOWY_BEACH,
            Biomes.WARM_OCEAN,
            Biomes.LUKEWARM_OCEAN,
            Biomes.COLD_OCEAN,
            Biomes.DEEP_WARM_OCEAN,
            Biomes.DEEP_LUKEWARM_OCEAN,
            Biomes.DEEP_COLD_OCEAN,
            Biomes.DEEP_FROZEN_OCEAN
    );
    private final long seed;
    private final Registry<Biome> lookupRegistry;
    private final DimyBiomeLayer biomeLayer;

    public DimyBiomeProvider(long seed, Registry<Biome> lookupRegistry) {
        super(biomes.stream().map((key) -> {
            return () -> {
                return lookupRegistry.getOrThrow(key);
            };
        }));
        // modify seed to not duplicate vanilla overworld
        this.seed = seed + seedModifier;
        this.lookupRegistry = lookupRegistry;
        this.biomeLayer = DimyLayerUtil.getBiomeLayer(this.seed,false,4,4);
    }

    protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
        return CODEC;
    }

    @OnlyIn(Dist.CLIENT)
    public BiomeProvider getBiomeProvider(long seed) {
        return new DimyBiomeProvider(seed, this.lookupRegistry);
    }

    public Biome getNoiseBiome(int x, int y, int z) {
        return this.biomeLayer.getBiome(this.lookupRegistry, x, z);
    }
}
