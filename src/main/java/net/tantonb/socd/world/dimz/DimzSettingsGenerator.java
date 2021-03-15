package net.tantonb.socd.world.dimz;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.NoiseSettings;
import net.minecraft.world.gen.settings.ScalingSettings;
import net.minecraft.world.gen.settings.SlideSettings;
import net.tantonb.socd.world.DimensionSettingsGenerator;

public class DimzSettingsGenerator extends DimensionSettingsGenerator {

    // dimension settings
    public static final BlockState filler = Blocks.STONE.getDefaultState();
    public static final BlockState fluid = Blocks.WATER.getDefaultState();
    public static final int bedrockFloor = -10;
    public static final int bedrockCeiling = 0;
    public static final int seaLevel = 63;
    public static final boolean disableMobGenFlag = false;

    // noise settings
    public static final int noiseHeight = 256;
    public static final double noiseScaleXz = 0.9999999814507745D;
    public static final double noiseScaleY = 0.9999999814507745D;
    public static final double noiseFactorXz = 80.0D;
    public static final double noiseFactorY = 160.0D;

    public static final int noiseTopTarget = -10;
    public static final int noiseTopSize = 3;
    public static final int noiseTopOffset = 0;

    public static final int noiseBottomTarget = -30;
    public static final int noiseBottomSize = 0;
    public static final int noiseBottomOffset = 0;

    public static final int noiseSizeHorizontal = 1; // 1 - 4
    public static final int noiseSizeVertical = 2; // 1 - 4
    public static final double noiseDensityFactor = 1.0D;
    public static final double noiseDensityOffset = -0.46875D;

    public static final boolean noiseSimplexSurfaceNoise = true;
    public static final boolean noiseRandomDensityOffset = true;
    public static final boolean noiseIslandNoiseOverride = false;
    public static final boolean noiseAmplified = false;

    protected BlockState getFiller() { return filler; }

    protected BlockState getFluid() { return fluid; }

    protected int getBedrockFloor() { return bedrockFloor; }

    protected int getBedrockCeiling() { return bedrockCeiling; }

    protected int getSeaLevel() { return seaLevel; }

    protected boolean getDisableMobGenFlag() { return disableMobGenFlag; }

    protected DimensionStructuresSettings createStructuresSettings() {
        return new DimensionStructuresSettings(false);
    }

    private ScalingSettings createNoiseScalingSettings() {
        return new ScalingSettings(noiseScaleXz, noiseScaleY, noiseFactorXz, noiseFactorY);
    }

    private SlideSettings createTopSlideSettings() {
        return new SlideSettings(noiseTopTarget, noiseTopSize, noiseTopOffset);
    }

    private SlideSettings createBottomSlideSettings() {
        return new SlideSettings(noiseBottomTarget, noiseBottomSize, noiseBottomOffset);
    }

    protected NoiseSettings createNoiseSettings() {
        return new NoiseSettings(
                noiseHeight,
                createNoiseScalingSettings(),
                createTopSlideSettings(),
                createBottomSlideSettings(),
                noiseSizeHorizontal,
                noiseSizeVertical,
                noiseDensityFactor,
                noiseDensityOffset,
                noiseSimplexSurfaceNoise,
                noiseRandomDensityOffset,
                noiseIslandNoiseOverride,
                noiseAmplified
        );
    }

    public DimensionSettings getDimensionSettings() {
        return new DimensionSettings(
                createStructuresSettings(),
                createNoiseSettings(),
                getFiller(),
                getFluid(),
                getBedrockFloor(),
                getBedrockCeiling(),
                getSeaLevel(),
                getDisableMobGenFlag()
        );
    }
}
