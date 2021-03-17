package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.tantonb.socd.world.dimz.layer.traits.AreaTransformer;
import net.tantonb.socd.world.dimz.layer.traits.Oceans;

public enum StartRiverTransormer implements AreaTransformer, Oceans {

    INSTANCE;

    public int transform(IExtendedNoiseRandom areaRng, IArea area, int x, int z) {
        int val = value(area, x, z);
        if (isShallowOcean(val)) return val;
        return areaRng.random(299999) + 2;
    }
}
