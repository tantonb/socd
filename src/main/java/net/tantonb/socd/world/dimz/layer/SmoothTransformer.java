package net.tantonb.socd.world.dimz.layer;

import net.tantonb.socd.world.dimz.layer.traits.CastleTransformer;

public enum SmoothTransformer implements CastleTransformer {

    INSTANCE;

    public int transform(AreaRng rng, int north, int west, int south, int east, int center) {
        boolean ewMatch = west == east;
        boolean nsMatch = north == south;
        if (ewMatch == nsMatch) {
            if (ewMatch) {
                return rng.random(2) == 0 ? east : north;
            } else {
                return center;
            }
        } else {
            return ewMatch ? east : north;
        }
    }
}
