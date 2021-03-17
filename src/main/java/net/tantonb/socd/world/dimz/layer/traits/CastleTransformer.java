package net.tantonb.socd.world.dimz.layer.traits;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;

/**
 * Transform each point in an area based on adjacent neighbor values.
 *
 * Axis alignment with cardinal directions:
 *
 *      North -> negative z
 *      West -> negative x
 *      South -> positive z
 *      East -> positive x
 */
public interface CastleTransformer extends AreaTransformer {

    int transform(INoiseRandom areaRng, int north, int west, int south, int east, int center);

    default int transform(IExtendedNoiseRandom<?> areaRng, IArea area, int x, int z) {
        return transform(
                areaRng,
                north(area, x, z),
                west(area, x, z),
                south(area, x, z),
                east(area, x, z),
                value(area, x, z)
        );
    }
}
