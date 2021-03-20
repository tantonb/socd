package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.tantonb.socd.world.dimz.layer.traits.BiomeType;
import net.tantonb.socd.world.dimz.layer.traits.CastleTransformer;

public enum BiomeEdgeTransformer implements CastleTransformer {

    INSTANCE;

    public int transform(AreaRng rng, int north, int west, int south, int east, int center) {

        // edge wooded badlands plateau and badlands plateau with badlands
        if (BiomeType.BADLANDS_PLATEAU.includes(center)) {
            if (!BiomeType.BADLANDS_PLATEAU.includes(north, west, east, south)) {
                return BADLANDS;
            }
            return center;
        }

        // edge giant tree taiga with taiga
        if (center == GIANT_TREE_TAIGA) {
            if (!BiomeType.TAIGA.includes(north, west, east, south)) {
                return TAIGA;
            }
            return center;
        }

        // edge swamp with plains/jungle edge if next to certain biomes
        if (center == SWAMP) {
            if (matchesAny(DESERT, north, west, south, east) ||
                    matchesAny(SNOWY_TAIGA, north, west, south, east) ||
                    matchesAny(SNOWY_TUNDRA, north, west, south, east))
            {
                return PLAINS;
            }

            if (matchesAny(JUNGLE, north, west, south, east) ||
                    matchesAny(BAMBOO_JUNGLE, north, west, south, east))
            {
                return JUNGLE_EDGE;
            }
        }

        // edge desert with wooded mountains if next to snowy tundra
        if (center == DESERT && matchesAny(SNOWY_TUNDRA, north, west, south, east)) {
            return WOODED_MOUNTAINS;
        }

        // no edge condition met, return unchanged
        return center;
    }
}
