package net.tantonb.socd.world.dimz.layer.traits;

import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.tantonb.socd.world.dimz.layer.AreaRng;

/**
 * Takes an existing area factory and modifies coordinate values using a transform
 * function.
 *
 * Extends OffsetTransformer which provides methods to access coordinate values
 * in the provided area.
 *
 * based on net.minecraft.world.gen.layer.traits.IAreaTransformer1
 */
public interface AreaTransformer extends BiomeIds, Matches, Offsets {

    default IAreaFactory<LazyArea> transform(
            AreaRng rng,
            IAreaFactory<LazyArea> areaFactory)
    {
        return () -> {
            LazyArea area = areaFactory.make();
            return rng.makeArea((x, z) -> {
                rng.setPosition(x, z);
                return this.transform(rng, area, x, z);
            }, area);
        };
    }

    int transform(AreaRng rng, IArea area, int x, int z);
}
