package net.tantonb.socd.world.dimz.layer.traits;

import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.tantonb.socd.world.dimz.layer.AreaRng;

public interface AreaMixer extends BiomeIds, Matches, Offsets {

    default IAreaFactory<LazyArea> mix(
            AreaRng areaRng,
            IAreaFactory<LazyArea> areaFactory1,
            IAreaFactory<LazyArea> areaFactory2)
    {
        return () -> {
            LazyArea area1 = areaFactory1.make();
            LazyArea area2 = areaFactory2.make();
            return areaRng.makeArea((x, z) -> {
                areaRng.setPosition((long)x, (long)z);
                return this.mix(areaRng, area1, area2, x, z);
            }, area1, area2);
        };
    }

    int mix(AreaRng rng, IArea area1, IArea area2, int x, int z);
}
