package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.tantonb.socd.world.dimz.layer.transform.AreaInitializer;

/**
 * Initialize an area to ocean with islands scattered randomly throughout.
 * Land is guaranteed at 0, 0 and has a 1 in 10 chance of appearing at other
 * coordinates.
 */
public enum LandSeaInitializer implements AreaInitializer {

    INSTANCE;

    public int transform(INoiseRandom areaRng, int x, int z) {
        if (x == 0 && z == 0) {
            return 1;
        } else {
            return areaRng.random(10) == 0 ? 1 : 0;
        }
    }
}
