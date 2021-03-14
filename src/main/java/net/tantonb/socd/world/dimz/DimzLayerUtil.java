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
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;

import java.util.function.LongFunction;

public class DimzLayerUtil {
    private static final Int2IntMap field_242937_a = Util.make(new Int2IntOpenHashMap(), (p_242938_0_) -> {
        func_242939_a(p_242938_0_, Type.BEACH, 16);
        func_242939_a(p_242938_0_, Type.BEACH, 26);
        func_242939_a(p_242938_0_, Type.DESERT, 2);
        func_242939_a(p_242938_0_, Type.DESERT, 17);
        func_242939_a(p_242938_0_, Type.DESERT, 130);
        func_242939_a(p_242938_0_, Type.EXTREME_HILLS, 131);
        func_242939_a(p_242938_0_, Type.EXTREME_HILLS, 162);
        func_242939_a(p_242938_0_, Type.EXTREME_HILLS, 20);
        func_242939_a(p_242938_0_, Type.EXTREME_HILLS, 3);
        func_242939_a(p_242938_0_, Type.EXTREME_HILLS, 34);
        func_242939_a(p_242938_0_, Type.FOREST, 27);
        func_242939_a(p_242938_0_, Type.FOREST, 28);
        func_242939_a(p_242938_0_, Type.FOREST, 29);
        func_242939_a(p_242938_0_, Type.FOREST, 157);
        func_242939_a(p_242938_0_, Type.FOREST, 132);
        func_242939_a(p_242938_0_, Type.FOREST, 4);
        func_242939_a(p_242938_0_, Type.FOREST, 155);
        func_242939_a(p_242938_0_, Type.FOREST, 156);
        func_242939_a(p_242938_0_, Type.FOREST, 18);
        func_242939_a(p_242938_0_, Type.ICY, 140);
        func_242939_a(p_242938_0_, Type.ICY, 13);
        func_242939_a(p_242938_0_, Type.ICY, 12);
        func_242939_a(p_242938_0_, Type.JUNGLE, 168);
        func_242939_a(p_242938_0_, Type.JUNGLE, 169);
        func_242939_a(p_242938_0_, Type.JUNGLE, 21);
        func_242939_a(p_242938_0_, Type.JUNGLE, 23);
        func_242939_a(p_242938_0_, Type.JUNGLE, 22);
        func_242939_a(p_242938_0_, Type.JUNGLE, 149);
        func_242939_a(p_242938_0_, Type.JUNGLE, 151);
        func_242939_a(p_242938_0_, Type.MESA, 37);
        func_242939_a(p_242938_0_, Type.MESA, 165);
        func_242939_a(p_242938_0_, Type.MESA, 167);
        func_242939_a(p_242938_0_, Type.MESA, 166);
        func_242939_a(p_242938_0_, Type.BADLANDS_PLATEAU, 39);
        func_242939_a(p_242938_0_, Type.BADLANDS_PLATEAU, 38);
        func_242939_a(p_242938_0_, Type.MUSHROOM, 14);
        func_242939_a(p_242938_0_, Type.MUSHROOM, 15);
        func_242939_a(p_242938_0_, Type.NONE, 25);
        func_242939_a(p_242938_0_, Type.OCEAN, 46);
        func_242939_a(p_242938_0_, Type.OCEAN, 49);
        func_242939_a(p_242938_0_, Type.OCEAN, 50);
        func_242939_a(p_242938_0_, Type.OCEAN, 48);
        func_242939_a(p_242938_0_, Type.OCEAN, 24);
        func_242939_a(p_242938_0_, Type.OCEAN, 47);
        func_242939_a(p_242938_0_, Type.OCEAN, 10);
        func_242939_a(p_242938_0_, Type.OCEAN, 45);
        func_242939_a(p_242938_0_, Type.OCEAN, 0);
        func_242939_a(p_242938_0_, Type.OCEAN, 44);
        func_242939_a(p_242938_0_, Type.PLAINS, 1);
        func_242939_a(p_242938_0_, Type.PLAINS, 129);
        func_242939_a(p_242938_0_, Type.RIVER, 11);
        func_242939_a(p_242938_0_, Type.RIVER, 7);
        func_242939_a(p_242938_0_, Type.SAVANNA, 35);
        func_242939_a(p_242938_0_, Type.SAVANNA, 36);
        func_242939_a(p_242938_0_, Type.SAVANNA, 163);
        func_242939_a(p_242938_0_, Type.SAVANNA, 164);
        func_242939_a(p_242938_0_, Type.SWAMP, 6);
        func_242939_a(p_242938_0_, Type.SWAMP, 134);
        func_242939_a(p_242938_0_, Type.TAIGA, 160);
        func_242939_a(p_242938_0_, Type.TAIGA, 161);
        func_242939_a(p_242938_0_, Type.TAIGA, 32);
        func_242939_a(p_242938_0_, Type.TAIGA, 33);
        func_242939_a(p_242938_0_, Type.TAIGA, 30);
        func_242939_a(p_242938_0_, Type.TAIGA, 31);
        func_242939_a(p_242938_0_, Type.TAIGA, 158);
        func_242939_a(p_242938_0_, Type.TAIGA, 5);
        func_242939_a(p_242938_0_, Type.TAIGA, 19);
        func_242939_a(p_242938_0_, Type.TAIGA, 133);
    });

    public static <AreaType extends IArea, Lalc extends IExtendedNoiseRandom<AreaType>> IAreaFactory<AreaType> repeat(
            long seed,
            IAreaTransformer1 transformer,
            IAreaFactory<AreaType> afStart,
            int count,
            LongFunction<Lalc> contextFactory)
    {
        IAreaFactory<AreaType> afModified = afStart;

        for(int i = 0; i < count; ++i) {
            afModified = transformer.apply(contextFactory.apply(seed + (long)i), afModified);
        }

        return afModified;
    }

    private static
            <AreaType extends IArea, Lalc extends IExtendedNoiseRandom<AreaType>>
        IAreaFactory<AreaType> createVanillaAreaFactory(
            boolean legacyBiomeFlag,
            int biomeSizing,
            int riverZooming,
            LongFunction<Lalc> areaRng)
    {
        // generate base layer, seed in some islands of land, remove excess ocean
        // at this point its not clear if coordinate values represent actual biome ids...
        // 0 = ocean, land = 1
        IAreaFactory<AreaType> afBase = IslandLayer.INSTANCE.apply(areaRng.apply(1L));
        afBase = ZoomLayer.FUZZY.apply(areaRng.apply(2000L), afBase);
        afBase = AddIslandLayer.INSTANCE.apply(areaRng.apply(1L), afBase);
        afBase = ZoomLayer.NORMAL.apply(areaRng.apply(2001L), afBase);
        afBase = AddIslandLayer.INSTANCE.apply(areaRng.apply(2L), afBase);
        afBase = AddIslandLayer.INSTANCE.apply(areaRng.apply(50L), afBase);
        afBase = AddIslandLayer.INSTANCE.apply(areaRng.apply(70L), afBase);
        afBase = RemoveTooMuchOceanLayer.INSTANCE.apply(areaRng.apply(2L), afBase);

        // generate ocean variation layer
        // generates coordinate values with various shallow ocean biome ids
        IAreaFactory<AreaType> afOcean = OceanLayer.INSTANCE.apply(areaRng.apply(2L));
        afOcean = repeat(2001L, ZoomLayer.NORMAL, afOcean, 6, areaRng);

        // refine base layer, add in various edge transitions, deep ocean, cold
        // land values may be abstract at this point, representing temperatures?
        // 0 = ocean, 1 = land, 3, 4 = snow-ish
        afBase = AddSnowLayer.INSTANCE.apply(areaRng.apply(2L), afBase);
        afBase = AddIslandLayer.INSTANCE.apply(areaRng.apply(3L), afBase);
        // 2 =  of 3, 4 (ice/snow?)
        afBase = EdgeLayer.CoolWarm.INSTANCE.apply(areaRng.apply(2L), afBase);
        // 3 = edge between
        afBase = EdgeLayer.HeatIce.INSTANCE.apply(areaRng.apply(2L), afBase);
        afBase = EdgeLayer.Special.INSTANCE.apply(areaRng.apply(3L), afBase);
        afBase = ZoomLayer.NORMAL.apply(areaRng.apply(2002L), afBase);
        afBase = ZoomLayer.NORMAL.apply(areaRng.apply(2003L), afBase);
        afBase = AddIslandLayer.INSTANCE.apply(areaRng.apply(4L), afBase);
        afBase = AddMushroomIslandLayer.INSTANCE.apply(areaRng.apply(5L), afBase);
        afBase = DeepOceanLayer.INSTANCE.apply(areaRng.apply(4L), afBase);
        afBase = repeat(1000L, ZoomLayer.NORMAL, afBase, 0, areaRng);

        // generate river layer from base
        IAreaFactory<AreaType> afRivers = repeat(1000L, ZoomLayer.NORMAL, afBase, 0, areaRng);
        // turns non-ocean values into large randomized values...?
        afRivers = StartRiverLayer.INSTANCE.apply(areaRng.apply(100L), afRivers);

        // convert base layer to biome specific layer
        IAreaFactory<AreaType> afBiomes = (new BiomeLayer(legacyBiomeFlag)).apply(areaRng.apply(200L), afBase);
        afBiomes = AddBambooForestLayer.INSTANCE.apply(areaRng.apply(1001L), afBiomes);
        afBiomes = repeat(1000L, ZoomLayer.NORMAL, afBiomes, 2, areaRng);
        afBiomes = EdgeBiomeLayer.INSTANCE.apply(areaRng.apply(1000L), afBiomes);

        IAreaFactory<AreaType> afRiversZoom2 = repeat(1000L, ZoomLayer.NORMAL, afRivers, 2, areaRng);
        afBiomes = HillsLayer.INSTANCE.apply(areaRng.apply(1000L), afBiomes, afRiversZoom2);

        // finish up river layer
        afRivers = repeat(1000L, ZoomLayer.NORMAL, afRivers, 2, areaRng);
        afRivers = repeat(1000L, ZoomLayer.NORMAL, afRivers, riverZooming, areaRng);
        afRivers = RiverLayer.INSTANCE.apply(areaRng.apply(1L), afRivers);
        afRivers = SmoothLayer.INSTANCE.apply(areaRng.apply(1000L), afRivers);

        // sprinkle in some rare biome(s)
        afBiomes = RareBiomeLayer.INSTANCE.apply(areaRng.apply(1001L), afBiomes);

        // add shores, smooth
        for(int i = 0; i < biomeSizing; ++i) {
            afBiomes = ZoomLayer.NORMAL.apply(areaRng.apply((long)(1000 + i)), afBiomes);
            if (i == 0) {
                afBiomes = AddIslandLayer.INSTANCE.apply(areaRng.apply(3L), afBiomes);
            }

            if (i == 1 || biomeSizing == 1) {
                afBiomes = ShoreLayer.INSTANCE.apply(areaRng.apply(1000L), afBiomes);
            }
        }
        afBiomes = SmoothLayer.INSTANCE.apply(areaRng.apply(1000L), afBiomes);

        // merge in river layer
        afBiomes = MixRiverLayer.INSTANCE.apply(areaRng.apply(100L), afBiomes, afRivers);

        // merge in oceans
        afBiomes = MixOceansLayer.INSTANCE.apply(areaRng.apply(100L), afBiomes, afOcean);

        return afBiomes;
    }

    public static DimzBiomeLayer getBiomeLayer(long seed, boolean legacyBiomeFlag, int biomeSizing, int riverZooming) {
        int memCacheSize = 25;
        LongFunction<LazyAreaLayerContext> areaRng = (seedModifier) -> {
            return new LazyAreaLayerContext(memCacheSize, seed, seedModifier);
        };
        IAreaFactory<LazyArea> areaFactory = createVanillaAreaFactory(legacyBiomeFlag, biomeSizing, riverZooming, areaRng);
        return new DimzBiomeLayer(areaFactory);
    }

    public static boolean areBiomesSimilar(int p_202826_0_, int p_202826_1_) {
        if (p_202826_0_ == p_202826_1_) {
            return true;
        } else {
            return field_242937_a.get(p_202826_0_) == field_242937_a.get(p_202826_1_);
        }
    }

    private static void func_242939_a(Int2IntOpenHashMap p_242939_0_, Type p_242939_1_, int p_242939_2_) {
        p_242939_0_.put(p_242939_2_, p_242939_1_.ordinal());
    }

    protected static boolean isOcean(int biomeIn) {
        return biomeIn == 44 || biomeIn == 45 || biomeIn == 0 || biomeIn == 46 || biomeIn == 10 || biomeIn == 47 || biomeIn == 48 || biomeIn == 24 || biomeIn == 49 || biomeIn == 50;
    }

    protected static boolean isShallowOcean(int biomeIn) {
        return biomeIn == 44 || biomeIn == 45 || biomeIn == 0 || biomeIn == 46 || biomeIn == 10;
    }

    static enum Type {
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
}
