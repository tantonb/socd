package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.tantonb.socd.world.dimz.layer.transform.CastleTransformer;

public class ReduceOceanTransformer implements CastleTransformer {

    private IntValueTest oceanTest;

    public ReduceOceanTransformer(IntValueTest oceanTest) {
        this.oceanTest = oceanTest;
    }

    private boolean isOcean(int val) {
        return oceanTest.test(val);
    }

    public int transform(INoiseRandom areaRng, int north, int west, int south, int east, int center) {
        return isOcean(center) && isOcean(north) && isOcean(west) && isOcean(east) && isOcean(south) && areaRng.random(2) == 0 ? 1 : center;
    }
}
