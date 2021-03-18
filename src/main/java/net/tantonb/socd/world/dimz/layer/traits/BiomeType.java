package net.tantonb.socd.world.dimz.layer.traits;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

import java.util.Map;

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
    OCEAN,
    DESERT,
    RIVER,
    SWAMP,
    MUSHROOM;

    private static Int2IntOpenHashMap idToType = new Int2IntOpenHashMap();

    private static Object2IntArrayMap<RegistryKey<Biome>> keyToId = new Object2IntArrayMap<>();

    private static void mapIds2Type(BiomeType t, RegistryKey<Biome> ... keys) {
        for (RegistryKey<Biome> key : keys) idToType.put(keyToId.getInt(key), t.ordinal());
    }

    static {
        for (Map.Entry<RegistryKey<Biome>, Biome> e : ForgeRegistries.BIOMES.getEntries()) {
            RegistryKey<Biome> key = e.getKey();
            int biomeId = ((ForgeRegistry<Biome>) ForgeRegistries.BIOMES).getID(e.getValue());
            keyToId.put(key, biomeId);
        }

        mapIds2Type(
                NONE,
                Biomes.STONE_SHORE
        );

        mapIds2Type(
                TAIGA,
                Biomes.TAIGA,
                Biomes.TAIGA_HILLS,
                Biomes.SNOWY_TAIGA,
                Biomes.SNOWY_TAIGA_HILLS,
                Biomes.GIANT_TREE_TAIGA,
                Biomes.GIANT_TREE_TAIGA_HILLS,
                Biomes.TAIGA_MOUNTAINS,
                Biomes.SNOWY_TAIGA_MOUNTAINS,
                Biomes.GIANT_SPRUCE_TAIGA,
                Biomes.GIANT_SPRUCE_TAIGA_HILLS
        );

        mapIds2Type(
                MOUNTAINS,
                Biomes.MOUNTAINS,
                Biomes.MOUNTAIN_EDGE,
                Biomes.WOODED_MOUNTAINS,
                Biomes.GRAVELLY_MOUNTAINS,
                Biomes.MODIFIED_GRAVELLY_MOUNTAINS
        );

        mapIds2Type(
                JUNGLE,
                Biomes.JUNGLE,
                Biomes.JUNGLE_HILLS,
                Biomes.JUNGLE_EDGE,
                Biomes.MODIFIED_JUNGLE,
                Biomes.MODIFIED_JUNGLE_EDGE,
                Biomes.BAMBOO_JUNGLE,
                Biomes.BAMBOO_JUNGLE_HILLS
        );

        mapIds2Type(
                MESA,
                Biomes.BADLANDS,
                Biomes.ERODED_BADLANDS,
                Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU,
                Biomes.MODIFIED_BADLANDS_PLATEAU
        );

        mapIds2Type(
                BADLANDS_PLATEAU,
                Biomes.BADLANDS_PLATEAU,
                Biomes.WOODED_BADLANDS_PLATEAU
        );

        mapIds2Type(
                PLAINS,
                Biomes.PLAINS,
                Biomes.SUNFLOWER_PLAINS
        );

        mapIds2Type(
                SAVANNA,
                Biomes.SAVANNA,
                Biomes.SAVANNA_PLATEAU,
                Biomes.SHATTERED_SAVANNA,
                Biomes.SHATTERED_SAVANNA_PLATEAU
        );

        mapIds2Type(
                ICY,
                Biomes.SNOWY_TUNDRA,
                Biomes.SNOWY_MOUNTAINS,
                Biomes.ICE_SPIKES
        );

        mapIds2Type(
                BEACH,
                Biomes.BEACH,
                Biomes.SNOWY_BEACH
        );

        mapIds2Type(
                FOREST,
                Biomes.FOREST,
                Biomes.WOODED_HILLS,
                Biomes.BIRCH_FOREST,
                Biomes.BIRCH_FOREST_HILLS,
                Biomes.DARK_FOREST,
                Biomes.FLOWER_FOREST,
                Biomes.TALL_BIRCH_FOREST,
                Biomes.TALL_BIRCH_HILLS,
                Biomes.DARK_FOREST_HILLS
        );

        mapIds2Type(
                OCEAN,
                Biomes.OCEAN,
                Biomes.FROZEN_OCEAN,
                Biomes.DEEP_OCEAN,
                Biomes.WARM_OCEAN,
                Biomes.LUKEWARM_OCEAN,
                Biomes.COLD_OCEAN,
                Biomes.DEEP_WARM_OCEAN,
                Biomes.DEEP_LUKEWARM_OCEAN,
                Biomes.DEEP_COLD_OCEAN,
                Biomes.DEEP_FROZEN_OCEAN
        );

        mapIds2Type(
                DESERT,
                Biomes.DESERT,
                Biomes.DESERT_HILLS,
                Biomes.DESERT_LAKES
        );

        mapIds2Type(
                RIVER,
                Biomes.RIVER,
                Biomes.FROZEN_RIVER
        );

        mapIds2Type(
                SWAMP,
                Biomes.SWAMP,
                Biomes.SWAMP_HILLS
        );

        mapIds2Type(
                MUSHROOM,
                Biomes.MUSHROOM_FIELDS,
                Biomes.MUSHROOM_FIELD_SHORE
        );
    }

    public static boolean similar(int id1, int ... ids) {
        int idType = idToType.get(id1);
        for (int id : ids) if (idType != idToType.get(id)) return false;
        return true;
    }

    public static boolean similar(int id1, int id2) {
        return idToType.get(id1) == idToType.get(id2);
    }

    public boolean has(int id) {
        return idToType.get(id) == this.ordinal();
    }
}
