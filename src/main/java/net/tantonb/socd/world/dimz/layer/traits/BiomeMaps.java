package net.tantonb.socd.world.dimz.layer.traits;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

import java.util.Map;

public class BiomeMaps {

    public static Object2IntOpenHashMap<RegistryKey<Biome>> keyToId = new Object2IntOpenHashMap<>();
    public static Int2ObjectOpenHashMap<RegistryKey<Biome>> idToKey = new Int2ObjectOpenHashMap<>();

    static {
        for (Map.Entry<RegistryKey<Biome>, Biome> e : ForgeRegistries.BIOMES.getEntries()) {
            RegistryKey<Biome> key = e.getKey();
            int biomeId = ((ForgeRegistry<Biome>) ForgeRegistries.BIOMES).getID(e.getValue());
            keyToId.put(key, biomeId);
            idToKey.put(biomeId, key);
        }
    }

    public static int getId(RegistryKey<Biome> key) {
        return keyToId.getInt(key);
    }

    public static RegistryKey<Biome> getKey(int id) {
        return idToKey.get(id);
    }
}
