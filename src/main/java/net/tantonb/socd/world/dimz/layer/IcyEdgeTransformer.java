package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.tantonb.socd.world.dimz.layer.traits.CastleTransformer;
import net.tantonb.socd.world.dimz.layer.traits.Temperatures;

/**
 * Generates cool edge around icy areas next to hot and warm areas.
 */
public enum IcyEdgeTransformer implements CastleTransformer, Temperatures {

    INSTANCE;

    public int transform(INoiseRandom areaRng, int north, int west, int south, int east, int center) {
        if (center == ICY) {
            if (matchesAny(HOT, north, west, east, south) || matchesAny(WARM, north, west, east, south)) {
                return COOL;
            }
        }
        return center;
    }
}
