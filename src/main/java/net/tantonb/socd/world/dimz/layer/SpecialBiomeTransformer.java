package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.area.IArea;
import net.tantonb.socd.world.dimz.layer.traits.AreaTransformer;
import net.tantonb.socd.world.dimz.layer.traits.Oceans;

/**
 * Applies a value to land biome IDs used by BiomesTransformer to trigger
 * alternate biomes.  Value is bit shifted up two bytes and or-ed into
 * the land value.
 */
public enum SpecialBiomeTransformer implements AreaTransformer, Oceans {

    INSTANCE;

    public int transform(AreaRng rng, IArea area, int x, int z ) {
        int value = value(area, x, z);
        if (!isShallowOcean(value) && rng.random(13) == 0) {
            value |= 1 + rng.random(15) << 8 & 3840;
        }
        return value;
    }
}
