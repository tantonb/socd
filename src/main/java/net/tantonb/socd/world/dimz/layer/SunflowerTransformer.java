package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.tantonb.socd.world.dimz.layer.traits.AreaTransformer;

public enum SunflowerTransformer implements AreaTransformer {

    INSTANCE;

    public int transform(AreaRng rng, IArea area, int x, int z) {
        int biomeId = value(area, x, z);
        if (biomeId == PLAINS && rng.rollUpTo(57, 1)) return SUNFLOWER_PLAINS;
        return biomeId;
    }
}
