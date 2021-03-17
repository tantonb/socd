package net.tantonb.socd.world.dimz.layer.traits;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;


/**
 * Makes a new area factory with an initial state determined by a transform
 * function.  Use initializers to start a new area factory.
 *
 * based on net.minecraft.world.gen.layer.traits.IAreaTransformer0
 */
public interface AreaInitializer {
    default <R extends IArea> IAreaFactory<R> initialize(IExtendedNoiseRandom<R> areaRng) {
        return () -> {
            return areaRng.makeArea((x, z) -> {
                areaRng.setPosition((long)x, (long)z);
                return this.initialize(areaRng, x, z);
            });
        };
    }

    int initialize(INoiseRandom areaRng, int x, int z);
}
