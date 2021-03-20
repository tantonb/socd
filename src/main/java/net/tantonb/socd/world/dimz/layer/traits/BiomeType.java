package net.tantonb.socd.world.dimz.layer.traits;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

public enum BiomeType {
    NONE,
    TAIGA,
    MOUNTAINS,
    JUNGLE,
    MESA,
    BADLANDS_PLATEAU,
    PLAINS,
    SAVANNA,
    ICY,
    BEACH,
    FOREST,
    DEEP_OCEAN,
    SHALLOW_OCEAN,
    DESERT,
    RIVER,
    SWAMP,
    MUSHROOM;

    private static Int2IntOpenHashMap idToType = new Int2IntOpenHashMap();

    private static void mapIds2Type(BiomeType t, int ... biomeIds) {
        for (int biomeId : biomeIds) idToType.put(biomeId, t.ordinal());
    }

    static {
        mapIds2Type(
                NONE,
                BiomeIds.STONE_SHORE
        );

        mapIds2Type(
                TAIGA,
                BiomeIds.TAIGA,
                BiomeIds.TAIGA_HILLS,
                BiomeIds.SNOWY_TAIGA,
                BiomeIds.SNOWY_TAIGA_HILLS,
                BiomeIds.GIANT_TREE_TAIGA,
                BiomeIds.GIANT_TREE_TAIGA_HILLS,
                BiomeIds.TAIGA_MOUNTAINS,
                BiomeIds.SNOWY_TAIGA_MOUNTAINS,
                BiomeIds.GIANT_SPRUCE_TAIGA,
                BiomeIds.GIANT_SPRUCE_TAIGA_HILLS
        );

        mapIds2Type(
                MOUNTAINS,
                BiomeIds.MOUNTAINS,
                BiomeIds.MOUNTAIN_EDGE,
                BiomeIds.WOODED_MOUNTAINS,
                BiomeIds.GRAVELLY_MOUNTAINS,
                BiomeIds.MODIFIED_GRAVELLY_MOUNTAINS
        );

        mapIds2Type(
                JUNGLE,
                BiomeIds.JUNGLE,
                BiomeIds.JUNGLE_HILLS,
                BiomeIds.JUNGLE_EDGE,
                BiomeIds.MODIFIED_JUNGLE,
                BiomeIds.MODIFIED_JUNGLE_EDGE,
                BiomeIds.BAMBOO_JUNGLE,
                BiomeIds.BAMBOO_JUNGLE_HILLS
        );

        mapIds2Type(
                MESA,
                BiomeIds.BADLANDS,
                BiomeIds.ERODED_BADLANDS,
                BiomeIds.MODIFIED_WOODED_BADLANDS_PLATEAU,
                BiomeIds.MODIFIED_BADLANDS_PLATEAU
        );

        mapIds2Type(
                BADLANDS_PLATEAU,
                BiomeIds.BADLANDS_PLATEAU,
                BiomeIds.WOODED_BADLANDS_PLATEAU
        );

        mapIds2Type(
                PLAINS,
                BiomeIds.PLAINS,
                BiomeIds.SUNFLOWER_PLAINS
        );

        mapIds2Type(
                SAVANNA,
                BiomeIds.SAVANNA,
                BiomeIds.SAVANNA_PLATEAU,
                BiomeIds.SHATTERED_SAVANNA,
                BiomeIds.SHATTERED_SAVANNA_PLATEAU
        );

        mapIds2Type(
                ICY,
                BiomeIds.SNOWY_TUNDRA,
                BiomeIds.SNOWY_MOUNTAINS,
                BiomeIds.ICE_SPIKES
        );

        mapIds2Type(
                BEACH,
                BiomeIds.BEACH,
                BiomeIds.SNOWY_BEACH
        );

        mapIds2Type(
                FOREST,
                BiomeIds.FOREST,
                BiomeIds.WOODED_HILLS,
                BiomeIds.BIRCH_FOREST,
                BiomeIds.BIRCH_FOREST_HILLS,
                BiomeIds.DARK_FOREST,
                BiomeIds.FLOWER_FOREST,
                BiomeIds.TALL_BIRCH_FOREST,
                BiomeIds.TALL_BIRCH_HILLS,
                BiomeIds.DARK_FOREST_HILLS
        );

        mapIds2Type(
                DEEP_OCEAN,
                BiomeIds.DEEP_OCEAN,
                BiomeIds.DEEP_WARM_OCEAN,
                BiomeIds.DEEP_LUKEWARM_OCEAN,
                BiomeIds.DEEP_COLD_OCEAN,
                BiomeIds.DEEP_FROZEN_OCEAN
        );

        mapIds2Type(
                SHALLOW_OCEAN,
                BiomeIds.OCEAN,
                BiomeIds.FROZEN_OCEAN,
                BiomeIds.WARM_OCEAN,
                BiomeIds.LUKEWARM_OCEAN,
                BiomeIds.COLD_OCEAN
        );

        mapIds2Type(
                DESERT,
                BiomeIds.DESERT,
                BiomeIds.DESERT_HILLS,
                BiomeIds.DESERT_LAKES
        );

        mapIds2Type(
                RIVER,
                BiomeIds.RIVER,
                BiomeIds.FROZEN_RIVER
        );

        mapIds2Type(
                SWAMP,
                BiomeIds.SWAMP,
                BiomeIds.SWAMP_HILLS
        );

        mapIds2Type(
                MUSHROOM,
                BiomeIds.MUSHROOM_FIELDS,
                BiomeIds.MUSHROOM_FIELD_SHORE
        );
    }

    /**
     * Returns true if all biome id's types match.
     */
    public static boolean similar(int firstId, int ... biomeIds) {
        int firstType = idToType.get(firstId);
        for (int id : biomeIds) if (firstType != idToType.get(id)) return false;
        return true;
    }

    /**
     * Return true if biome type 1 matches biome type 2.
     */
    public static boolean similar(int biomeId1, int biomeId2) {
        return idToType.get(biomeId1) == idToType.get(biomeId2);
    }

    /**
     * Returns true if biome id's type matches this.
     */
    public boolean includes(int biomeId) {
        return idToType.get(biomeId) == this.ordinal();
    }

    public boolean includes(int ... biomeIds) {
        for (int biomeId : biomeIds) if (!includes(biomeId)) return false;
        return true;
    }
}
