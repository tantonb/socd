package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.tantonb.socd.world.dimz.layer.traits.BishopTransformer;
import net.tantonb.socd.world.dimz.layer.traits.Oceans;

/**
 * Transforms land to sea and vice versa...hard to understand
 */
public enum IslandTransformer implements BishopTransformer, Oceans {

    INSTANCE;

    public int transform(INoiseRandom areaRng, int southwest, int southeast, int northeast, int northwest, int center) {

        boolean centerOcean = isShallowOcean(center);
        boolean nwOcean = isShallowOcean(northwest);
        boolean neOcean = isShallowOcean(northeast);
        boolean swOcean = isShallowOcean(southwest);
        boolean seOcean = isShallowOcean(southeast);

        if (!centerOcean || (nwOcean && neOcean && swOcean && seOcean)) {
            if (center != 4 && !centerOcean &&
                (nwOcean || swOcean || neOcean || seOcean) &&
                areaRng.random(5) == 0)
            {
                if (nwOcean) return northwest;
                if (swOcean) return southwest;
                if (neOcean) return northeast;
                if (seOcean) return southeast;
            }
            return center;
        }

        int retVal = 1;
        int rarity = 1;
        if (!nwOcean && areaRng.random(rarity++) == 0) retVal = northwest;
        if (!neOcean && areaRng.random(rarity++) == 0) retVal = northeast;
        if (!swOcean && areaRng.random(rarity++) == 0) retVal = southwest;
        if (!seOcean && areaRng.random(rarity) == 0) retVal = southeast;
        if (areaRng.random(3) == 0) return retVal;

        return retVal == 4 ? 4 : center;
    }
}
