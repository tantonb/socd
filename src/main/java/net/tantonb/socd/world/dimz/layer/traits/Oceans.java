package net.tantonb.socd.world.dimz.layer.traits;

public interface Oceans {

    default boolean isShallowOcean(int value) {
        return BiomeType.SHALLOW_OCEAN.includes(value);
    }

    default boolean isShallowOcean(int ...values) {
        for (int value : values) if (!isShallowOcean(value)) return false;
        return true;
    }

    default boolean isDeepOcean(int value) {
        return BiomeType.DEEP_OCEAN.includes(value);
    }

    default boolean isDeepOcean(int ...values) {
        for (int value: values) if (!isDeepOcean(value)) return false;
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
