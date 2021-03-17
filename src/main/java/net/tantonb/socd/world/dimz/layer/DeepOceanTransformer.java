package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.tantonb.socd.world.dimz.layer.traits.CastleTransformer;
import net.tantonb.socd.world.dimz.layer.traits.Oceans;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum DeepOceanTransformer implements CastleTransformer, Oceans {

    INSTANCE;

    private static final Logger LOGGER = LogManager.getLogger();

    public int transform(INoiseRandom areaRng, int north, int west, int south, int east, int center) {
        //LOGGER.info("DeepOceanTransformer - center {}, n {}, w {}, s {}, e {}", center, north, west, south, east);
        if (isShallowOcean(center, north, west, east, south)) {
            LOGGER.info("Creating deep ocean...");
            if (center == WARM_OCEAN) return DEEP_WARM_OCEAN;
            if (center == LUKEWARM_OCEAN) return DEEP_LUKEWARM_OCEAN;
            if (center == COLD_OCEAN) return DEEP_COLD_OCEAN;
            if (center == FROZEN_OCEAN) return DEEP_FROZEN_OCEAN;
            return DEEP_OCEAN;
        }
        return center;
    }
}
