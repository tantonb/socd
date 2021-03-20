package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.tantonb.socd.world.dimz.layer.traits.AreaTransformer;
import net.tantonb.socd.world.dimz.layer.traits.Oceans;
import net.tantonb.socd.world.dimz.layer.traits.Temperatures;

/**
 * Transform land temperature. 1/6th of land is set to ICY, 1/6th to
 * COOL.  The rest are set to HOT (should actually already be HOT).
 */
public enum LandTemperatureTransformer implements AreaTransformer, Temperatures, Oceans {

    INSTANCE;

    public int transform(AreaRng rng, IArea area, int x, int z) {
        int val = value(area, x, z);
        if (isOcean(val)) return val;
        int roll = rng.roll(6);
        if (roll == 1) return ICY;
        if (roll == 2) return COOL;
        return HOT;
    }
}
