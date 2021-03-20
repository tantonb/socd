package net.tantonb.socd.world.dimz.layer;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.util.Util;
import net.minecraft.world.gen.area.IArea;
import net.tantonb.socd.world.dimz.layer.traits.AreaMixer;
import net.tantonb.socd.world.dimz.layer.traits.BiomeType;
import net.tantonb.socd.world.dimz.layer.traits.Oceans;

public enum VariantMixer implements Oceans, AreaMixer {

    INSTANCE;

    private static final Int2IntMap variantMap = Util.make(new Int2IntOpenHashMap(), (map) -> {
        map.put(PLAINS, SUNFLOWER_PLAINS);
        map.put(DESERT, DESERT_LAKES);
        map.put(MOUNTAINS, GRAVELLY_MOUNTAINS);
        map.put(FOREST, FLOWER_FOREST);
        map.put(TAIGA, TAIGA_MOUNTAINS);
        map.put(SWAMP, SWAMP_HILLS);
        map.put(SNOWY_TUNDRA, ICE_SPIKES);
        map.put(JUNGLE, MODIFIED_JUNGLE);
        map.put(JUNGLE_EDGE, MODIFIED_JUNGLE_EDGE);
        map.put(BIRCH_FOREST, TALL_BIRCH_FOREST);
        map.put(BIRCH_FOREST_HILLS, TALL_BIRCH_HILLS);
        map.put(DARK_FOREST, DARK_FOREST_HILLS);
        map.put(SNOWY_TAIGA, SNOWY_TAIGA_MOUNTAINS);
        map.put(GIANT_TREE_TAIGA, GIANT_SPRUCE_TAIGA);
        map.put(GIANT_TREE_TAIGA_HILLS, GIANT_SPRUCE_TAIGA_HILLS);
        map.put(WOODED_MOUNTAINS, MODIFIED_GRAVELLY_MOUNTAINS);
        map.put(SAVANNA, SHATTERED_SAVANNA);
        map.put(SAVANNA_PLATEAU, SHATTERED_SAVANNA_PLATEAU);
        map.put(BADLANDS, ERODED_BADLANDS);
        map.put(WOODED_BADLANDS_PLATEAU, MODIFIED_WOODED_BADLANDS_PLATEAU);
        map.put(BADLANDS_PLATEAU, MODIFIED_BADLANDS_PLATEAU);
    });

    public int mix(AreaRng rng, IArea biomes, IArea noise, int x, int z) {

        int biomeId = value(biomes, x, z);
        int noiseVal = value(noise, x, z);
        int moddedNoise = (noiseVal - 2) % 29;

        if (isShallowOcean(biomeId) && noiseVal >= 2 && moddedNoise == 1) {
            return variantMap.getOrDefault(biomeId, biomeId);
        }

        if (rng.random(3) == 0 || moddedNoise == 0) {
            int hillishId = biomeId;
            if (biomeId == DESERT) {
                hillishId = DESERT_HILLS;
            } else if (biomeId == FOREST) {
                hillishId = WOODED_HILLS;
            } else if (biomeId == BIRCH_FOREST) {
                hillishId = BIRCH_FOREST_HILLS;
            } else if (biomeId == DARK_FOREST) {
                hillishId = PLAINS;
            } else if (biomeId == TAIGA) {
                hillishId = TAIGA_HILLS;
            } else if (biomeId == GIANT_TREE_TAIGA) {
                hillishId = GIANT_TREE_TAIGA_HILLS;
            } else if (biomeId == SNOWY_TAIGA) {
                hillishId = SNOWY_TAIGA_HILLS;
            } else if (biomeId == PLAINS) {
                hillishId = rng.random(3) == 0 ? WOODED_HILLS : FOREST;
            } else if (biomeId == SNOWY_TUNDRA) {
                hillishId = SNOWY_MOUNTAINS;
            } else if (biomeId == JUNGLE) {
                hillishId = JUNGLE_HILLS;
            } else if (biomeId == BAMBOO_JUNGLE) {
                hillishId = BAMBOO_JUNGLE_HILLS;
            } else if (biomeId == OCEAN) {
                hillishId = DEEP_OCEAN;
            } else if (biomeId == LUKEWARM_OCEAN) {
                hillishId = DEEP_LUKEWARM_OCEAN;
            } else if (biomeId == COLD_OCEAN) {
                hillishId = DEEP_COLD_OCEAN;
            } else if (biomeId == FROZEN_OCEAN) {
                hillishId = DEEP_FROZEN_OCEAN;
            } else if (biomeId == MOUNTAINS) {
                hillishId = WOODED_MOUNTAINS;
            } else if (biomeId == SAVANNA) {
                hillishId = SAVANNA_PLATEAU;
            } else if (BiomeType.similar(biomeId, WOODED_BADLANDS_PLATEAU)) {
                hillishId = BADLANDS;
            } else if (matchesAny(biomeId, DEEP_OCEAN, DEEP_LUKEWARM_OCEAN, DEEP_COLD_OCEAN, DEEP_FROZEN_OCEAN) &&
                    rng.random(3) == 0)
            {
                hillishId = rng.random(2) == 0 ? PLAINS : FOREST;
            }

            if (moddedNoise == 0 && hillishId != biomeId) {
                hillishId = variantMap.getOrDefault(hillishId, biomeId);
            }

            if (hillishId != biomeId) {
                int similarCount = 0;
                if (BiomeType.similar(biomeId, north(biomes, x, z))) ++similarCount;
                if (BiomeType.similar(biomeId, east(biomes, x, z))) ++similarCount;
                if (BiomeType.similar(biomeId, south(biomes, x, z))) ++similarCount;
                if (BiomeType.similar(biomeId, west(biomes, x, z))) ++similarCount;
                if (similarCount >= 3) {
                    return hillishId;
                }
            }
        }

        return biomeId;
    }
}
