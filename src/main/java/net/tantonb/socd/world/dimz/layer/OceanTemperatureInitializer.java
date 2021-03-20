package net.tantonb.socd.world.dimz.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.ImprovedNoiseGenerator;
import net.tantonb.socd.world.dimz.layer.traits.AreaInitializer;
import net.tantonb.socd.world.dimz.layer.traits.Oceans;

public enum OceanTemperatureInitializer implements AreaInitializer, Oceans {

    INSTANCE;

    public int initialize(AreaRng rng, int x, int z) {
        ImprovedNoiseGenerator noiseGen = rng.getNoiseGenerator();
        double noise = noiseGen.func_215456_a((double)x / 8.0D, (double)z / 8.0D, 0.0D, 0.0D, 0.0D);
        if (noise > 0.4D) {
            return WARM_OCEAN;
        }
        if (noise > 0.2D) {
            return LUKEWARM_OCEAN;
        }
        if (noise < -0.4D) {
            return FROZEN_OCEAN;
        }
        if (noise < -0.2D) {
            return COLD_OCEAN;
        }
        return OCEAN;
    }
}
