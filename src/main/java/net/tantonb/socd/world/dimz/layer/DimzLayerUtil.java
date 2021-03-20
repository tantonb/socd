package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.tantonb.socd.world.dimz.DimzBiomeLayer;
import net.tantonb.socd.world.dimz.layer.traits.AreaTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DimzLayerUtil {

    private static final Logger LOGGER = LogManager.getLogger();

    public static IAreaFactory<LazyArea> repeatTransform(
            long seedModifier,
            AreaTransformer transformer,
            IAreaFactory<LazyArea> start,
            int count,
            AreaRng areaRng)
    {
        IAreaFactory<LazyArea> result = start;

        for(int i = 0; i < count; ++i) {
            result = transformer.transform(areaRng.modified(seedModifier + (long)i), result);
        }

        return result;
    }

    private static IAreaFactory<LazyArea>
        createBiomeAreaFactory(int biomeSizing, int riverZooming, AreaRng rng)
    {
        /*
         * generate base layer of ocean, sprinkle in some islands of land and
         * zoom in to grow the initial islands into larger bodies of land,
         * remove excess ocean.
         *
         * 0 = ocean, land = 1
         *
         * Note: base area values do not yet represent actual biome IDs...
         *       land initially set to HOT (1), eventually will be
         *       transformed to more temperatures
         */
        IAreaFactory<LazyArea> base = IslandInitializer.INSTANCE.initialize(rng.modified(1L));
        base = ZoomTransformer.FUZZY.transform(rng.modified(2000L), base);
        base = IslandTransformer.INSTANCE.transform(rng.modified(1L), base);
        base = ZoomTransformer.NORMAL.transform(rng.modified(2001L), base);
        base = IslandTransformer.INSTANCE.transform(rng.modified(2L), base);
        base = IslandTransformer.INSTANCE.transform(rng.modified(50L), base);
        base = IslandTransformer.INSTANCE.transform(rng.modified(70L), base);
        base = ReduceOceanTransformer.INSTANCE.transform(rng.modified(2L), base);

        // refine base layer, add in various edge transitions, deep ocean,
        // temperature variation
        // 0 = ocean, 1 = hot, 2 = warm, 3 = cold, 4 = icy
        base = LandTemperatureTransformer.INSTANCE.transform(rng.modified(2L), base);
        base = IslandTransformer.INSTANCE.transform(rng.modified(3L), base);
        base = HotEdgeTransformer.INSTANCE.transform(rng.modified(2L), base);
        base = IcyEdgeTransformer.INSTANCE.transform(rng.modified(2L), base);
        base = SpecialBiomeTransformer.INSTANCE.transform(rng.modified(3L), base);
        base = ZoomTransformer.NORMAL.transform(rng.modified(2002L), base);
        base = ZoomTransformer.NORMAL.transform(rng.modified(2003L), base);
        base = IslandTransformer.INSTANCE.transform(rng.modified(4L), base);
        base = MushroomIslandTransformer.INSTANCE.transform(rng.modified(5L), base);
        base = DeepOceanTransformer.INSTANCE.transform(rng.modified(4L), base);

        // convert base layer to biome specific layer
        IAreaFactory<LazyArea> biomes = (new BiomeTransformer()).transform(rng.modified(200L), base);
        biomes = BambooForestTransformer.INSTANCE.transform(rng.modified(1001L), biomes);
        biomes = repeatTransform(1000L, ZoomTransformer.NORMAL, biomes, 2, rng);
        biomes = BiomeEdgeTransformer.INSTANCE.transform(rng.modified(1000L), biomes);

        // generate variant land noise from base layer
        // turns non-ocean values into large randomized values to be used for
        // adding biome variants and generating rivers
        IAreaFactory<LazyArea> noiseBase = LandNoiseTransformer.INSTANCE.transform(rng.modified(100L), base);

        // add a lot of biome variants using the noise area
        IAreaFactory<LazyArea> variantNoise =
                repeatTransform(1000L, ZoomTransformer.NORMAL, noiseBase, 2, rng);
        biomes = VariantMixer.INSTANCE.mix(rng.modified(1000L), biomes, variantNoise);

        // change some plains to sunflower plains (1 in 57)
        biomes = SunflowerTransformer.INSTANCE.transform(rng.modified(1001L), biomes);

        // final zooming, adding more islands and shores
        for(int i = 0; i < biomeSizing; ++i) {
            biomes = ZoomTransformer.NORMAL.transform(rng.modified((long)(1000 + i)), biomes);
            if (i == 0) {
                biomes = IslandTransformer.INSTANCE.transform(rng.modified(3L), biomes);
            }

            if (i == 1 || biomeSizing == 1) {
                biomes = ShoreTransformer.INSTANCE.transform(rng.modified(1000L), biomes);
            }
        }
        biomes = SmoothTransformer.INSTANCE.transform(rng.modified(1000L), biomes);

        // create rivers using the noise area
        IAreaFactory<LazyArea> riverNoise =
                repeatTransform(1000L, ZoomTransformer.NORMAL, noiseBase, riverZooming + 2, rng);
        riverNoise = RiverFilterTransformer.INSTANCE.transform(rng.modified(1L), riverNoise);
        riverNoise = SmoothTransformer.INSTANCE.transform(rng.modified(1000L), riverNoise);
        biomes = RiverMixer.INSTANCE.mix(rng.modified(100L), biomes, riverNoise);

        // create shallow ocean temperature variations
        //  0 = ocean, 10 = frozen ocean, 44 = warm ocean,
        //  45 = lukewarm ocean, 46 = cold ocean
        IAreaFactory<LazyArea> ocean = OceanTemperatureInitializer.INSTANCE.initialize(rng.modified(2L));
        ocean = repeatTransform(2001L, ZoomTransformer.NORMAL, ocean, 6, rng);
        biomes = OceanMixer.INSTANCE.mix(rng.modified(100L), biomes, ocean);

        return biomes;
    }

    public static DimzBiomeLayer getBiomeLayer(long seed, int biomeSizing, int riverZooming) {
        AreaRng areaRng = new AreaRng(25, seed);
        IAreaFactory<LazyArea> areaFactory = createBiomeAreaFactory(biomeSizing, riverZooming, areaRng);
        return new DimzBiomeLayer(areaFactory);
    }

}
