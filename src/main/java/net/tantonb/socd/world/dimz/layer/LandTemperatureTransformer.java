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

    public int transform(IExtendedNoiseRandom areaRng, IArea area, int x, int z) {
        int val = value(area, x, z);
        if (isOcean(val)) return val;
        int roll = areaRng.random(6);
        if (roll == 0) return ICY;
        if (roll == 1) return COOL;
        return HOT;
    }
}
