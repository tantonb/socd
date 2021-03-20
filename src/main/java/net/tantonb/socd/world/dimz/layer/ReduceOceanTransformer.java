package net.tantonb.socd.world.dimz.layer;

import net.tantonb.socd.world.dimz.layer.traits.CastleTransformer;
import net.tantonb.socd.world.dimz.layer.traits.Oceans;

public enum ReduceOceanTransformer implements Oceans, CastleTransformer {

    INSTANCE;

    public int transform(AreaRng rng, int north, int west, int south, int east, int center) {
        return isOcean(center, north, west, east, south) && rng.random(2) == 0 ? PLAINS : center;
    }
}
