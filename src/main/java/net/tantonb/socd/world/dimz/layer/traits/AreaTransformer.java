package net.tantonb.socd.world.dimz.layer.traits;

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
public interface AreaTransformer extends Offsets {

    default boolean matchesAny(int value, int ...candidates) {
        for (int c : candidates) if (c == value) return true;
        return false;
    }

    default <AreaType extends IArea> IAreaFactory<AreaType> transform(
            IExtendedNoiseRandom<AreaType> areaRng,
            IAreaFactory<AreaType> areaFactory)
    {
        return () -> {
            AreaType area = areaFactory.make();
            return areaRng.makeArea((x, z) -> {
                areaRng.setPosition(x, z);
                return this.transform(areaRng, area, x, z);
            }, area);
        };
    }

    int transform(IExtendedNoiseRandom<?> context, IArea area, int x, int z);
}
