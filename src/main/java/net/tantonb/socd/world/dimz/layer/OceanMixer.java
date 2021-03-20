package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.area.IArea;
import net.tantonb.socd.world.dimz.layer.traits.AreaMixer;
import net.tantonb.socd.world.dimz.layer.traits.Oceans;
import net.tantonb.socd.world.dimz.layer.traits.Offsets;

public enum OceanMixer implements AreaMixer, Oceans, Offsets {

    INSTANCE;

    public int mix(AreaRng rng, IArea biomes, IArea ocean, int x, int z) {

        int biomeId = value(biomes, x, z);
        if (!isOcean(biomeId)) {
            return biomeId;
        }

        int oceanId = value(ocean, x, z);
        for(int xOff = -8; xOff <= 8; xOff += 4) {
            for(int zOff = -8; zOff <= 8; zOff += 4) {
                int nearbyBiomeId = value(biomes, x + xOff, z + zOff);
                if (!isOcean(nearbyBiomeId)) {
                    if (oceanId == WARM_OCEAN) return LUKEWARM_OCEAN;
                    if (oceanId == FROZEN_OCEAN) return COLD_OCEAN;
                }
            }
        }

        if (biomeId == DEEP_OCEAN) {
            if (oceanId == LUKEWARM_OCEAN) return DEEP_LUKEWARM_OCEAN;
            if (oceanId == OCEAN) return DEEP_OCEAN;
            if (oceanId == COLD_OCEAN) return DEEP_COLD_OCEAN;
            if (oceanId == FROZEN_OCEAN) return DEEP_FROZEN_OCEAN;
        }

        return oceanId;
    }
}
