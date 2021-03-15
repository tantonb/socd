package net.tantonb.socd.world.dimz;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.util.Util;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.*;
import net.tantonb.socd.world.dimz.layer.AddIslandTransformer;
import net.tantonb.socd.world.dimz.layer.ReduceOceanTransformer;
import net.tantonb.socd.world.dimz.layer.LandSeaInitializer;
import net.tantonb.socd.world.dimz.layer.ZoomTransformer;
import net.tantonb.socd.world.dimz.layer.transform.AreaTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.LongFunction;

public class DimzLayerUtil {

    private static final Logger LOGGER = LogManager.getLogger();

    enum BiomeType {
        NONE,
        TAIGA,
        EXTREME_HILLS,
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
    }

    private static final Int2IntMap biomeTypes = Util.make(new Int2IntOpenHashMap(), (map) -> {
        setBiomeType(map, BiomeType.BEACH, 16);
        setBiomeType(map, BiomeType.BEACH, 26);
        setBiomeType(map, BiomeType.DESERT, 2);
        setBiomeType(map, BiomeType.DESERT, 17);
        setBiomeType(map, BiomeType.DESERT, 130);
        setBiomeType(map, BiomeType.EXTREME_HILLS, 131);
        setBiomeType(map, BiomeType.EXTREME_HILLS, 162);
        setBiomeType(map, BiomeType.EXTREME_HILLS, 20);
        setBiomeType(map, BiomeType.EXTREME_HILLS, 3);
        setBiomeType(map, BiomeType.EXTREME_HILLS, 34);
        setBiomeType(map, BiomeType.FOREST, 27);
        setBiomeType(map, BiomeType.FOREST, 28);
        setBiomeType(map, BiomeType.FOREST, 29);
        setBiomeType(map, BiomeType.FOREST, 157);
        setBiomeType(map, BiomeType.FOREST, 132);
        setBiomeType(map, BiomeType.FOREST, 4);
        setBiomeType(map, BiomeType.FOREST, 155);
        setBiomeType(map, BiomeType.FOREST, 156);
        setBiomeType(map, BiomeType.FOREST, 18);
        setBiomeType(map, BiomeType.ICY, 140);
        setBiomeType(map, BiomeType.ICY, 13);
        setBiomeType(map, BiomeType.ICY, 12);
        setBiomeType(map, BiomeType.JUNGLE, 168);
        setBiomeType(map, BiomeType.JUNGLE, 169);
        setBiomeType(map, BiomeType.JUNGLE, 21);
        setBiomeType(map, BiomeType.JUNGLE, 23);
        setBiomeType(map, BiomeType.JUNGLE, 22);
        setBiomeType(map, BiomeType.JUNGLE, 149);
        setBiomeType(map, BiomeType.JUNGLE, 151);
        setBiomeType(map, BiomeType.MESA, 37);
        setBiomeType(map, BiomeType.MESA, 165);
        setBiomeType(map, BiomeType.MESA, 167);
        setBiomeType(map, BiomeType.MESA, 166);
        setBiomeType(map, BiomeType.BADLANDS_PLATEAU, 39);
        setBiomeType(map, BiomeType.BADLANDS_PLATEAU, 38);
        setBiomeType(map, BiomeType.MUSHROOM, 14);
        setBiomeType(map, BiomeType.MUSHROOM, 15);
        setBiomeType(map, BiomeType.NONE, 25);
        setBiomeType(map, BiomeType.OCEAN, 46);
        setBiomeType(map, BiomeType.OCEAN, 49);
        setBiomeType(map, BiomeType.OCEAN, 50);
        setBiomeType(map, BiomeType.OCEAN, 48);
        setBiomeType(map, BiomeType.OCEAN, 24);
        setBiomeType(map, BiomeType.OCEAN, 47);
        setBiomeType(map, BiomeType.OCEAN, 10);
        setBiomeType(map, BiomeType.OCEAN, 45);
        setBiomeType(map, BiomeType.OCEAN, 0);
        setBiomeType(map, BiomeType.OCEAN, 44);
        setBiomeType(map, BiomeType.PLAINS, 1);
        setBiomeType(map, BiomeType.PLAINS, 129);
        setBiomeType(map, BiomeType.RIVER, 11);
        setBiomeType(map, BiomeType.RIVER, 7);
        setBiomeType(map, BiomeType.SAVANNA, 35);
        setBiomeType(map, BiomeType.SAVANNA, 36);
        setBiomeType(map, BiomeType.SAVANNA, 163);
        setBiomeType(map, BiomeType.SAVANNA, 164);
        setBiomeType(map, BiomeType.SWAMP, 6);
        setBiomeType(map, BiomeType.SWAMP, 134);
        setBiomeType(map, BiomeType.TAIGA, 160);
        setBiomeType(map, BiomeType.TAIGA, 161);
        setBiomeType(map, BiomeType.TAIGA, 32);
        setBiomeType(map, BiomeType.TAIGA, 33);
        setBiomeType(map, BiomeType.TAIGA, 30);
        setBiomeType(map, BiomeType.TAIGA, 31);
        setBiomeType(map, BiomeType.TAIGA, 158);
        setBiomeType(map, BiomeType.TAIGA, 5);
        setBiomeType(map, BiomeType.TAIGA, 19);
        setBiomeType(map, BiomeType.TAIGA, 133);
    });

    private static void setBiomeType(Int2IntOpenHashMap map, BiomeType biomeType, int biomeId) {
        map.put(biomeId, biomeType.ordinal());
    }

    public static boolean sameBiomeType(int biomeId1, int biomeId2) {
        if (biomeId1 == biomeId2) {
            return true;
        }
        return biomeTypes.get(biomeId1) == biomeTypes.get(biomeId2);
    }

    protected static boolean isShallowOcean(int biomeId) {
        return biomeId == 44 || biomeId == 45 || biomeId == 0 || biomeId == 46 || biomeId == 10;
    }

    protected static boolean isOcean(int biomeId) {
        return biomeId == 44 || biomeId == 45 || biomeId == 0 || biomeId == 46 || biomeId == 10 ||
                biomeId == 47 || biomeId == 48 || biomeId == 24 || biomeId == 49 || biomeId == 50;
    }

    public static <AreaType extends IArea, Lalc extends IExtendedNoiseRandom<AreaType>> IAreaFactory<AreaType> repeatTransform(
            long seedModifier,
            AreaTransformer transformer,
            IAreaFactory<AreaType> start,
            int count,
            LongFunction<Lalc> areaRng)
    {
        IAreaFactory<AreaType> result = start;

        for(int i = 0; i < count; ++i) {
            result = transformer.apply(areaRng.apply(seedModifier + (long)i), result);
        }

        return result;
    }

    private static
            <AreaType extends IArea, Lalc extends IExtendedNoiseRandom<AreaType>>
        IAreaFactory<AreaType> createVanillaAreaFactory(
            boolean legacyBiomeFlag,
            int biomeSizing,
            int riverZooming,
            LongFunction<Lalc> areaRng)
    {
        LOGGER.info("Creating dimension z biome layer area factory...");

        AddIslandTransformer addIsland = new AddIslandTransformer(DimzLayerUtil::isShallowOcean);
        ReduceOceanTransformer reduceOcean = new ReduceOceanTransformer(DimzLayerUtil::isShallowOcean);

        // generate base layer of ocean, sprinkle in some islands of land and
        // zoom in to grow the initial islands into larger bodies of land,
        // remove excess ocean.
        // Note: base area values do not yet represent actual biome IDs...
        // 0 = ocean, land = 1
        IAreaFactory<AreaType> base = LandSeaInitializer.INSTANCE.initialize(areaRng.apply(1L));
        base = ZoomTransformer.FUZZY.apply(areaRng.apply(2000L), base);
        //base = AddIslandLayer.INSTANCE.apply(areaRng.apply(1L), base);
        base = addIsland.apply(areaRng.apply(1L), base);
        base = ZoomTransformer.NORMAL.apply(areaRng.apply(2001L), base);
        base = addIsland.apply(areaRng.apply(2L), base);
        base = addIsland.apply(areaRng.apply(50L), base);
        base = addIsland.apply(areaRng.apply(70L), base);
        base = reduceOcean.apply(areaRng.apply(2L), base);

        // generate ocean variation layer
        // generates coordinate values with various shallow ocean biome ids
        IAreaFactory<AreaType> ocean = OceanLayer.INSTANCE.apply(areaRng.apply(2L));
        ocean = repeatTransform(2001L, ZoomTransformer.NORMAL, ocean, 6, areaRng);

        // refine base layer, add in various edge transitions, deep ocean, cold
        // land values may be abstract at this point, representing temperatures?
        // 0 = ocean, 1 = land, 3, 4 = snow-ish
        base = AddSnowLayer.INSTANCE.apply(areaRng.apply(2L), base);
        base = addIsland.apply(areaRng.apply(3L), base);
        // 2 =  of 3, 4 (ice/snow?)
        base = EdgeLayer.CoolWarm.INSTANCE.apply(areaRng.apply(2L), base);
        // 3 = edge between
        base = EdgeLayer.HeatIce.INSTANCE.apply(areaRng.apply(2L), base);
        base = EdgeLayer.Special.INSTANCE.apply(areaRng.apply(3L), base);
        base = ZoomTransformer.NORMAL.apply(areaRng.apply(2002L), base);
        base = ZoomTransformer.NORMAL.apply(areaRng.apply(2003L), base);
        base = addIsland.apply(areaRng.apply(4L), base);
        base = AddMushroomIslandLayer.INSTANCE.apply(areaRng.apply(5L), base);
        base = DeepOceanLayer.INSTANCE.apply(areaRng.apply(4L), base);
        base = repeatTransform(1000L, ZoomTransformer.NORMAL, base, 0, areaRng);

        // generate river layer from base
        IAreaFactory<AreaType> rivers = repeatTransform(1000L, ZoomTransformer.NORMAL, base, 0, areaRng);
        // turns non-ocean values into large randomized values...?
        rivers = StartRiverLayer.INSTANCE.apply(areaRng.apply(100L), rivers);

        // convert base layer to biome specific layer
        IAreaFactory<AreaType> biomes = (new BiomeLayer(legacyBiomeFlag)).apply(areaRng.apply(200L), base);
        biomes = AddBambooForestLayer.INSTANCE.apply(areaRng.apply(1001L), biomes);
        biomes = repeatTransform(1000L, ZoomTransformer.NORMAL, biomes, 2, areaRng);
        biomes = EdgeBiomeLayer.INSTANCE.apply(areaRng.apply(1000L), biomes);

        IAreaFactory<AreaType> afRiversZoom2 = repeatTransform(1000L, ZoomTransformer.NORMAL, rivers, 2, areaRng);
        biomes = HillsLayer.INSTANCE.apply(areaRng.apply(1000L), biomes, afRiversZoom2);

        // finish up river layer
        rivers = repeatTransform(1000L, ZoomTransformer.NORMAL, rivers, 2, areaRng);
        rivers = repeatTransform(1000L, ZoomTransformer.NORMAL, rivers, riverZooming, areaRng);
        rivers = RiverLayer.INSTANCE.apply(areaRng.apply(1L), rivers);
        rivers = SmoothLayer.INSTANCE.apply(areaRng.apply(1000L), rivers);

        // sprinkle in some rare biome(s)
        biomes = RareBiomeLayer.INSTANCE.apply(areaRng.apply(1001L), biomes);

        // add shores, smooth
        for(int i = 0; i < biomeSizing; ++i) {
            biomes = ZoomTransformer.NORMAL.apply(areaRng.apply((long)(1000 + i)), biomes);
            if (i == 0) {
                biomes = addIsland.apply(areaRng.apply(3L), biomes);
            }

            if (i == 1 || biomeSizing == 1) {
                biomes = ShoreLayer.INSTANCE.apply(areaRng.apply(1000L), biomes);
            }
        }
        biomes = SmoothLayer.INSTANCE.apply(areaRng.apply(1000L), biomes);

        // merge in river layer
        biomes = MixRiverLayer.INSTANCE.apply(areaRng.apply(100L), biomes, rivers);

        // merge in oceans
        biomes = MixOceansLayer.INSTANCE.apply(areaRng.apply(100L), biomes, ocean);

        return biomes;
    }

    public static DimzBiomeLayer getBiomeLayer(long seed, boolean legacyBiomeFlag, int biomeSizing, int riverZooming) {
        int memCacheSize = 25;
        LongFunction<LazyAreaLayerContext> areaRng = (seedModifier) -> {
            return new LazyAreaLayerContext(memCacheSize, seed, seedModifier);
        };
        IAreaFactory<LazyArea> areaFactory = createVanillaAreaFactory(legacyBiomeFlag, biomeSizing, riverZooming, areaRng);
        return new DimzBiomeLayer(areaFactory);
    }

}
