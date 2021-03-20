package net.tantonb.socd.world.dimz.layer;

import net.tantonb.socd.world.dimz.layer.traits.CastleTransformer;

public enum RiverFilterTransformer implements CastleTransformer {

    INSTANCE;

    public int transform(AreaRng rng, int north, int west, int south, int east, int center) {
        int i = riverFilter(center);
        return i == riverFilter(east) && i == riverFilter(north) && i == riverFilter(west) && i == riverFilter(south) ? -1 : 7;
    }

    private static int riverFilter(int value) {
        return value >= 2 ? 2 + (value & 1) : value;
    }
}
