package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.tantonb.socd.world.dimz.layer.traits.AreaInitializer;

/**
 * Initialize an area to ocean with islands scattered randomly throughout.
 * Land is guaranteed at 0, 0 and has a 1 in 10 chance of appearing at other
 * coordinates.  Land is initially set to 1 (HOT/desert) and eventually will
 * have temperature variety applied.  Ocean is set to 0 (OCEAN) and will
 * later be varied to more ocean depths and temperatures.
 */
public enum IslandInitializer implements AreaInitializer {

    INSTANCE;

    public int initialize(AreaRng rng, int x, int z) {
        if ((x == 0 && z == 0) || rng.random(10) == 0) return PLAINS;
        return OCEAN;
    }
}
