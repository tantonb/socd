package net.tantonb.socd.world.dimz.layer.traits;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.tantonb.socd.world.dimz.layer.AreaRng;

/**
 * Transform each point in an area based on diagonal neighbor values.
 *
 * Axis alignment with cardinal directions:
 *
 *      North -> negative z
 *      West -> negative x
 *      South -> positive z
 *      East -> positive x
 */
public interface BishopTransformer extends AreaTransformer {

    int transform(AreaRng rng, int southwest, int southeast, int northeast, int northwest, int center);

    default int transform(AreaRng rng, IArea area, int x, int z) {
        return transform(
                rng,
                southwest(area, x, z),
                southeast(area, x, z),
                northeast(area, x, z),
                northwest(area, x, z),
                value(area, x, z)
        );
    }
}
