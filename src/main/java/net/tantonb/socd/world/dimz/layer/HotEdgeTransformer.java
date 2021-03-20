package net.tantonb.socd.world.dimz.layer;

import net.tantonb.socd.world.dimz.layer.traits.CastleTransformer;
import net.tantonb.socd.world.dimz.layer.traits.Temperatures;

/**
 * Generates warm edge around hot areas next to cool and icy areas.
 */
public enum HotEdgeTransformer implements CastleTransformer, Temperatures {

    INSTANCE;

    public int transform(AreaRng rng, int north, int west, int south, int east, int center) {
        if (center == HOT) {
            if (matchesAny(COOL, north, west, east, south) || matchesAny(ICY, north, west, east, south)) {
                return WARM;
            }
        }
        return center;
    }
}
