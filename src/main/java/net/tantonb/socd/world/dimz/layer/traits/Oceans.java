package net.tantonb.socd.world.dimz.layer.traits;

public interface Oceans {

    // shallow ocean
    int OCEAN = 0;
    int FROZEN_OCEAN = 10;
    int WARM_OCEAN = 44;
    int LUKEWARM_OCEAN = 45;
    int COLD_OCEAN = 46;

    // deep ocean
    int DEEP_OCEAN = 24;
    int DEEP_WARM_OCEAN = 47;
    int DEEP_LUKEWARM_OCEAN = 48;
    int DEEP_COLD_OCEAN = 49;
    int DEEP_FROZEN_OCEAN = 50;

    int[] SHALLOW_OCEANS = { OCEAN, FROZEN_OCEAN, WARM_OCEAN, LUKEWARM_OCEAN, COLD_OCEAN };
    int[] DEEP_OCEANS = { DEEP_OCEAN, DEEP_WARM_OCEAN, DEEP_LUKEWARM_OCEAN, DEEP_COLD_OCEAN, DEEP_FROZEN_OCEAN };

    default boolean isShallowOcean(int value) {
        for (int ocean : SHALLOW_OCEANS) if (value == ocean) return true;
        return false;
    }

    default boolean isShallowOcean(int ...values) {
        for (int value : values) if (!isShallowOcean(value)) return false;
        return true;
    }

    default boolean isDeepOcean(int value) {
        for (int ocean : DEEP_OCEANS) if (value == ocean) return true;
        return false;
    }

    default boolean isDeepOcean(int ...values) {
        for (int value : values) if (!isDeepOcean(value)) return false;
        return true;
    }

    default boolean isOcean(int value) {
        return isShallowOcean(value) || isDeepOcean(value);
    }

    default boolean isOcean(int ...values) {
        for (int value : values) if (!isOcean(value)) return false;
        return true;
    }
}
