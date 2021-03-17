package net.tantonb.socd.world.dimz.layer.traits;

import net.minecraft.world.gen.area.IArea;

/**
 * A transformer that provides offset calculations along the x and z axis.
 * Provides convenience methods for fetching values offset in various
 * directions.
 *
 *  Axis alignment with cardinal directions:
 *
 *      North -> negative z
 *      West -> negative x
 *      South -> positive z
 *      East -> positive x
 */
public interface Offsets {

    default int getOffsetX(int x) { return x; }

    default int getOffsetZ(int z) { return z; }

    default int north(IArea area, int x, int z) {
        return area.getValue(getOffsetX(x), getOffsetZ(z - 1));
    }

    default int west(IArea area, int x, int z) {
        return area.getValue(getOffsetX(x - 1), getOffsetZ(z));
    }

    default int south(IArea area, int x, int z) {
        return area.getValue(getOffsetX(x), getOffsetZ(z + 1));
    }

    default int east(IArea area, int x, int z) {
        return area.getValue(getOffsetX(x + 1), getOffsetZ(z));
    }

    default int northwest(IArea area, int x, int z) {
        return area.getValue(getOffsetX(x - 1), getOffsetZ(z - 1));
    }

    default int southwest(IArea area, int x, int z) {
        return area.getValue(getOffsetX(x - 1), getOffsetZ(z + 1));
    }

    default int southeast(IArea area, int x, int z) {
        return area.getValue(getOffsetX(x + 1), getOffsetZ(z + 1));
    }

    default int northeast(IArea area, int x, int z) {
        return area.getValue(getOffsetX(x + 1), getOffsetZ(z - 1));
    }

    default int value(IArea area, int x, int z) {
        return area.getValue(getOffsetX(x), getOffsetZ(z));
    }
}
