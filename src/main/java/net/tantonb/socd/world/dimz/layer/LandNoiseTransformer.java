package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.tantonb.socd.world.dimz.layer.traits.AreaTransformer;
import net.tantonb.socd.world.dimz.layer.traits.Oceans;

/**
 * Generates random values for land (from 2 - 300000), ocean remains as is.
 */
public enum LandNoiseTransformer implements AreaTransformer, Oceans {

    INSTANCE;

    public int transform(AreaRng rng, IArea area, int x, int z) {
        int val = value(area, x, z);
        if (isShallowOcean(val)) return val;
        return rng.random(299999) + 2;
    }
}
