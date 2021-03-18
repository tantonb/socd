package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.tantonb.socd.world.dimz.layer.traits.AreaTransformer;

public enum BambooForestTransformer implements AreaTransformer {

    INSTANCE;

    public int transform(IExtendedNoiseRandom areaRng, IArea area, int x, int z) {
        int value = value(area, x, z);
        if (value == 21 && areaRng.random(10) == 0) {
            return 168;
        }
        return value;
    }
}
