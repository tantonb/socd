package net.tantonb.socd.world.dimz.layer;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraftforge.common.BiomeManager;
import net.tantonb.socd.world.dimz.layer.traits.AreaTransformer;
import net.tantonb.socd.world.dimz.layer.traits.Oceans;
import net.tantonb.socd.world.dimz.layer.traits.Temperatures;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts base area values to biome IDs.
 */
public class BiomeTransformer implements AreaTransformer, Oceans, Temperatures {

    private List<BiomeManager.BiomeEntry>[] biomes = new ArrayList[BiomeManager.BiomeType.values().length];

    public BiomeTransformer() {
        for (BiomeManager.BiomeType type : BiomeManager.BiomeType.values())
            biomes[type.ordinal()] = new ArrayList<>(BiomeManager.getBiomes(type));
    }

    protected RegistryKey<Biome> getBiome(BiomeManager.BiomeType type, INoiseRandom areaRng) {
        List<BiomeManager.BiomeEntry> biomeList = biomes[type.ordinal()];
        int totalWeight = WeightedRandom.getTotalWeight(biomeList);
        int weight = BiomeManager.isTypeListModded(type) ? areaRng.random(totalWeight) : areaRng.random(totalWeight / 10) * 10;
        return WeightedRandom.getRandomItem(biomeList, weight).getKey();
    }

    private int getBiomeId(BiomeManager.BiomeType type, INoiseRandom areaRng) {
        return WorldGenRegistries.BIOME.getId(WorldGenRegistries.BIOME.getValueForKey(getBiome(type, areaRng)));
    }

    public int transform(IExtendedNoiseRandom<?> areaRng, IArea area, int x, int z) {

        // retrieve "composite" value, extract base value
        int compValue = value(area, x, z);
        int value = compValue & -3841; // mask out any special tag

        // leave ocean or mushroom island value as is
        if (isOcean(value) || value == 14) return value;

        // convert land temperature value to randomly selected biome or special
        // biome variant if special biome tag is present
        int special = (compValue & 3840) >> 8; // extract special biome tag
        switch(value) {
            case HOT:
                if (special > 0) {
                    return areaRng.random(3) == 0 ? 39 : 38; // badlands
                }
                return getBiomeId(BiomeManager.BiomeType.DESERT, areaRng);

            case WARM:
                if (special > 0) {
                    return 21; // jungle
                }
                return getBiomeId(BiomeManager.BiomeType.WARM, areaRng);

            case COOL:
                if (special > 0) {
                    return 32; // giant tree taiga
                }
                return getBiomeId(BiomeManager.BiomeType.COOL, areaRng);

            case ICY:
                return getBiomeId(BiomeManager.BiomeType.ICY, areaRng);

            default:
                return 14; // mushroom fields (shouldn't get here...)
        }
    }
}
