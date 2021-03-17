package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.tantonb.socd.world.dimz.layer.traits.AreaTransformer;

public enum ZoomTransformer implements AreaTransformer {
    NORMAL,
    FUZZY {
        /**
         * Randomly pick from four values.
         */
        @Override
        protected int pickZoom(IExtendedNoiseRandom<?> areaRng, int val1, int val2, int val3, int val4) {
            return areaRng.pickRandom(val1, val2, val3, val4);
        }
    };

    /**
     * Pick the most common of four values, or randomly chooses if all are different.
     */
    protected int pickZoom(IExtendedNoiseRandom<?> areaRng, int val1, int val2, int val3, int val4) {
        if (val2 == val3 && val3 == val4) {
            return val2;
        } else if (val1 == val2 && val1 == val3) {
            return val1;
        } else if (val1 == val2 && val1 == val4) {
            return val1;
        } else if (val1 == val3 && val1 == val4) {
            return val1;
        } else if (val1 == val2 && val3 != val4) {
            return val1;
        } else if (val1 == val3 && val2 != val4) {
            return val1;
        } else if (val1 == val4 && val2 != val3) {
            return val1;
        } else if (val2 == val3 && val1 != val4) {
            return val2;
        } else if (val2 == val4 && val1 != val3) {
            return val2;
        } else if (val3 == val4 && val1 != val2) {
            return val3;
        } else {
            return areaRng.pickRandom(val1, val2, val3, val4);
        }
    }

    /**
     * Bitshift x and z right 1 when fetching values from original area, this
     * will allow each x, z coordinate to be expanded from one coordinate to
     * four coordinates in 2 x 2 with original x, z in 0, 0 position.
     */
    public int getOffsetX(int x) {
        return x >> 1;
    }

    public int getOffsetZ(int z) {
        return z >> 1;
    }

    /**
     * Generates zoomed area values.
     *
     * Transforms from n by n:
     *
     *   a b
     *   c d
     *
     * to n * 2 by n * 2, filling in values based on adjacent abcd values:
     *
     *   a p b .
     *   q r . .
     *   c . d .
     *   . . . .
     * p = a or b
     * q = a or c
     * r = a or b or c or d
     *
     * r values are normally picked by most common value within abcd, or randomly picked
     * in the case of FUZZY zoom.
     *
     */

    public int transform(IExtendedNoiseRandom<?> areaRng, IArea area, int x, int z) {

        boolean xEven = (x & 1) == 0;
        boolean zEven = (z & 1) == 0;

        // both even
        int aVal = area.getValue(getOffsetX(x), getOffsetZ(z));
        if (xEven && zEven) {
            return aVal;
        }

        // randomness will be needed, set rng to pos a
        areaRng.setPosition((long)(x >> 1 << 1), (long)(z >> 1 << 1));

        // x odd, z even
        int bVal = area.getValue(this.getOffsetX(x + 1), this.getOffsetZ(z));
        if (zEven) {
            return areaRng.pickRandom(aVal, bVal);
        }

        // x even, z odd
        int cVal = area.getValue(this.getOffsetX(x), this.getOffsetZ(z + 1));
        if (xEven) {
            return areaRng.pickRandom(aVal, cVal);
        }

        // both odd
        int dVal = area.getValue(this.getOffsetX(x + 1), this.getOffsetZ(z + 1));
        return pickZoom(areaRng, aVal, bVal, cVal, dVal);
    }
}
