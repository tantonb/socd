package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.area.IArea;
import net.tantonb.socd.world.dimz.layer.traits.AreaMixer;
import net.tantonb.socd.world.dimz.layer.traits.Oceans;
import net.tantonb.socd.world.dimz.layer.traits.Offsets;

/**
 * Places river from the variant noise area into the main biome area.
 */
public enum RiverMixer implements AreaMixer, Oceans, Offsets {

    INSTANCE;

    public int mix(AreaRng rng, IArea biomes, IArea noise, int x, int z) {
        int biomeId = value(biomes, x, z);
        if (isOcean(biomeId)) return biomeId;
        int noiseId = value(noise, x, z);
        if (noiseId == RIVER) {
            if (biomeId == SNOWY_TUNDRA) {
                return FROZEN_RIVER;
            }
            return biomeId != MUSHROOM_FIELDS && biomeId != MUSHROOM_FIELD_SHORE ?
                    noiseId & 255 : MUSHROOM_FIELD_SHORE;
        }
        return biomeId;
    }
}
