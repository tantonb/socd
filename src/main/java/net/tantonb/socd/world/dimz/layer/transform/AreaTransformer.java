package net.tantonb.socd.world.dimz.layer.transform;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;

/**
 * Takes an existing area factory and modifies coordinate values using a transform
 * function.
 *
 * Extends OffsetTransformer which provides methods to access coordinate values
 * in the provided area.
 *
 * based on net.minecraft.world.gen.layer.traits.IAreaTransformer1
 */
public interface AreaTransformer extends OffsetTransformer {

    default <AreaType extends IArea> IAreaFactory<AreaType> apply(
            IExtendedNoiseRandom<AreaType> areaRng,
            IAreaFactory<AreaType> areaFactory)
    {
        return () -> {
            AreaType area = areaFactory.make();
            return areaRng.makeArea((x, z) -> {
                areaRng.setPosition((long)x, (long)z);
                return this.transform(areaRng, area, x, z);
            }, area);
        };
    }

    int transform(IExtendedNoiseRandom<?> context, IArea area, int x, int z);
}
