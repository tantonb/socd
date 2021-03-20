package net.tantonb.socd.world.dimz.layer.traits;

import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.tantonb.socd.world.dimz.layer.AreaRng;


/**
 * Makes a new area factory with an initial state determined by a transform
 * function.  Use initializers to start a new area factory.
 *
 * based on net.minecraft.world.gen.layer.traits.IAreaTransformer0
 */
public interface AreaInitializer extends BiomeIds {

    default IAreaFactory<LazyArea> initialize(AreaRng rng) {
        return () -> {
            return rng.makeArea((x, z) -> {
                rng.setPosition((long) x, (long) z);
                return this.initialize(rng, x, z);
            });
        };
    }

    int initialize(AreaRng rng, int x, int z);
}
