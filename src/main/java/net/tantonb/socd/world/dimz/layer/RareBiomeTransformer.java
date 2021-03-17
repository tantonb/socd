package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.tantonb.socd.world.dimz.layer.traits.AreaTransformer;
import net.tantonb.socd.world.dimz.layer.traits.Oceans;

public enum RareBiomeTransformer implements AreaTransformer, Oceans {

    INSTANCE;

    public int transform(IExtendedNoiseRandom areaRng, IArea area, int x, int z ) {
        int value = value(area, x, z);
        if (!isShallowOcean(value) && areaRng.random(13) == 0) {
            value |= 1 + areaRng.random(15) << 8 & 3840;
        }
        return value;
    }
}
