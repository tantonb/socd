package net.tantonb.socd.world.dimz.layer;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.tantonb.socd.world.dimz.layer.traits.CastleTransformer;
import net.tantonb.socd.world.dimz.layer.traits.Oceans;

public enum ShoreTransformer implements CastleTransformer, Oceans {
    
    INSTANCE;

    private static final IntSet ICY_BIOMES = new IntOpenHashSet(new int[] {
            SNOWY_BEACH,
            FROZEN_RIVER,
            SNOWY_TUNDRA,
            SNOWY_MOUNTAINS,
            ICE_SPIKES,
            SNOWY_TAIGA,
            SNOWY_TAIGA_HILLS,
            SNOWY_TAIGA_MOUNTAINS,
            FROZEN_OCEAN
    });

    private static final IntSet JUNGLE_BIOMES = new IntOpenHashSet(new int[] {
            BAMBOO_JUNGLE,
            BAMBOO_JUNGLE_HILLS,
            JUNGLE,
            JUNGLE_HILLS,
            JUNGLE_EDGE,
            MODIFIED_JUNGLE,
            MODIFIED_JUNGLE_EDGE
    });

    public int transform(AreaRng rng, int north, int west, int south, int east, int center) {
        if (center == MUSHROOM_FIELDS) {
            if (isShallowOcean(north) || isShallowOcean(west) || isShallowOcean(south) || isShallowOcean(east)) {
                return MUSHROOM_FIELD_SHORE;
            }
        } else if (JUNGLE_BIOMES.contains(center)) {
            if (!isJungleCompatible(north) || !isJungleCompatible(west) || !isJungleCompatible(south) || !isJungleCompatible(east)) {
                return JUNGLE_EDGE;
            }

            if (isOcean(north) || isOcean(west) || isOcean(south) || isOcean(east)) {
                return BEACH;
            }
        } else if (center != MOUNTAINS && center != WOODED_MOUNTAINS && center != MOUNTAIN_EDGE) {
            if (ICY_BIOMES.contains(center)) {
                if (!isOcean(center) && (isOcean(north) || isOcean(west) || isOcean(south) || isOcean(east))) {
                    return SNOWY_BEACH;
                }
            } else if (center != BADLANDS && center != WOODED_BADLANDS_PLATEAU) {
                if (!isOcean(center) && center != RIVER && center != SWAMP && (isOcean(north) || isOcean(west) || isOcean(south) || isOcean(east))) {
                    return BEACH;
                }
            } else if (!isOcean(north) && !isOcean(west) && !isOcean(south) && !isOcean(east) && (!isMesa(north) || !isMesa(west) || !isMesa(south) || !isMesa(east))) {
                return DESERT;
            }
        } else if (!isOcean(center) && (isOcean(north) || isOcean(west) || isOcean(south) || isOcean(east))) {
            return STONE_SHORE;
        }

        return center;
    }

    private boolean isJungleCompatible(int value) {
        return JUNGLE_BIOMES.contains(value) || value == FOREST || value == TAIGA || isOcean(value);
    }

    private boolean isMesa(int value) {
        return value == BADLANDS ||
                value == WOODED_BADLANDS_PLATEAU ||
                value == BADLANDS_PLATEAU ||
                value == ERODED_BADLANDS ||
                value == MODIFIED_WOODED_BADLANDS_PLATEAU ||
                value == MODIFIED_BADLANDS_PLATEAU;
    }
}
