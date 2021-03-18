package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.tantonb.socd.world.dimz.layer.traits.BiomeType;
import net.tantonb.socd.world.dimz.layer.traits.CastleTransformer;

public enum BiomeEdgeTransformer implements CastleTransformer {

    INSTANCE;

    /**
     * If biome ID is mountain type set idRef to biome ID and return true,
     * else return false.
     */
    private boolean handleMountainBiome(int[] idRef, int center) {
        if (!BiomeType.MOUNTAINS.has(center)) return false;
        idRef[0] = center;
        return true;
    }

    /**
     * Creates a border around a biome.
     */
    private boolean handleBiome(int[] idRef, int north, int west, int south, int east, int center, int matchId, int edgeId) {

        // if center biome id doesn't match biome target return false
        if (center != matchId) return false;

        if (BiomeType.similar(matchId, north, west, east, south)) {
            // center is surrounded by same biome type, don't replace with new value
            idRef[0] = center;
        } else {
            // some neighbors are different biome type, replace center with edge id
            idRef[0] = edgeId;
        }

        // biome type matched, handled, return true
        return true;
    }

    public int transform(INoiseRandom areaRng, int north, int west, int south, int east, int center) {

        // TODO: refactor, get rid of hard coded int biome ids, get rid of idRef, it's a terrible hack

        // change certain center biomes to edge biome if next to certain neighbor biome types
        int[] idRef = new int[1];
        if (handleMountainBiome(idRef, center) ||
                handleBiome(idRef, north, west, south, east, center, 38, 37) || // 38 = wooded badlands plateau, 37 = badlands
                handleBiome(idRef, north, west, south, east, center, 39, 37) || // 39 = badlands plateau, 37 = badlands
                handleBiome(idRef, north, west, south, east, center, 32, 5)) // 32 = giant tree taiga, 5 = taiga
        {
            return idRef[0];
        }

        if (center != 2 || north != 12 && west != 12 && east != 12 && south != 12) {
            // if center is swamp
            if (center == 6) {
                // if next to 2 desert, 30 snowy taiga, 12 snowy tundra, return 1 plains (plains edge between swamp and these other biomes)
                if (matchesAny(2, north, west, south, east) || matchesAny(30, north, west, south, east) || matchesAny(12, north, west, south, east)) return 1;
                // if next to 21 jungle, 168 bamboo jungle, return 23 jungle edge (jungle edge between swamp and jungle, bamboo)
                if (matchesAny(21, north, west, south, east) || matchesAny(168, north, west, south, east)) return 23;
            }

            // center is not 2 desert OR not next to 12 snowy tundra, return unchanged center
            return center;
        }

        // center is 2 desert or next to one or more 12 snowy tundra, return 34 wooded mountains
        return 34;
    }
}
