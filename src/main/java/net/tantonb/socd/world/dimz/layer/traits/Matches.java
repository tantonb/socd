package net.tantonb.socd.world.dimz.layer.traits;

public interface Matches {

    default boolean matchesAny(int value, int ...candidates) {
        for (int c : candidates) if (c == value) return true;
        return false;
    }

    default boolean matchesAll(int value, int ...candidates) {
        for (int c : candidates) if (c != value) return false;
        return true;
    }

}
