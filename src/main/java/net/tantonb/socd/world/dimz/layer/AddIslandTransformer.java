package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.tantonb.socd.world.dimz.layer.transform.BishopTransformer;

public class AddIslandTransformer implements BishopTransformer {

    private IntValueTest oceanTest;
    
    public AddIslandTransformer(IntValueTest oceanTest) {
        this.oceanTest = oceanTest;
    }
    
    private boolean isOcean(int val) {
        return oceanTest.test(val);
    }
    
    public int transform(INoiseRandom areaRng, int southwest, int southeast, int northeast, int northwest, int center) {

        boolean centerOcean = isOcean(center);
        boolean nwOcean = isOcean(northwest);
        boolean neOcean = isOcean(northeast);
        boolean swOcean = isOcean(southwest);
        boolean seOcean = isOcean(southeast);
        
        if (!centerOcean || nwOcean && neOcean && swOcean && seOcean) {
            if (!centerOcean && (nwOcean || swOcean || neOcean || seOcean) && areaRng.random(5) == 0) {
                if (nwOcean) {
                    return center == 4 ? 4 : northwest;
                }

                if (swOcean) {
                    return center == 4 ? 4 : southwest;
                }

                if (neOcean) {
                    return center == 4 ? 4 : northeast;
                }

                if (seOcean) {
                    return center == 4 ? 4 : southeast;
                }
            }

            return center;
        }

        int retVal = 1;
        int rarity = 1;
        if (!nwOcean && areaRng.random(rarity++) == 0) {
            retVal = northwest;
        }

        if (!neOcean && areaRng.random(rarity++) == 0) {
            retVal = northeast;
        }

        if (!swOcean && areaRng.random(rarity++) == 0) {
            retVal = southwest;
        }

        if (!seOcean && areaRng.random(rarity) == 0) {
            retVal = southeast;
        }

        if (areaRng.random(3) == 0) {
            return retVal;
        }

        return retVal == 4 ? 4 : center;
    }
}
