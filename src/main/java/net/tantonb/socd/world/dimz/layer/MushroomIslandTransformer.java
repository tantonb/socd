package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.tantonb.socd.world.dimz.layer.traits.BishopTransformer;
import net.tantonb.socd.world.dimz.layer.traits.Oceans;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 1 in 100 chance to transform ocean to mushroom island.
 */
public enum MushroomIslandTransformer implements BishopTransformer, Oceans {

    INSTANCE;

    private static final Logger LOGGER = LogManager.getLogger();

    public int transform(INoiseRandom areaRng, int sw, int se, int ne, int nw, int center) {
        boolean isAllOcean = isShallowOcean(center, nw, sw, ne, se);
        int roll = areaRng.random(2);
        int retVal = isAllOcean && roll == 0 ? 14 : center;
        LOGGER.info("retVal {}, isAllOcean {}, roll {}, sw {}, se {}, ne {}, nw {}, center {}", retVal, isAllOcean, roll, sw, se, ne, nw, center);
        return retVal;
    }
}
