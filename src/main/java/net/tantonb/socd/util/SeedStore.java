package net.tantonb.socd.util;

public class SeedStore {

    private static long seed = 0;

    public static void setSeed(long seed) {
        SeedStore.seed = seed;
    }

    public static long getSeed() {
        return seed;
    }
}