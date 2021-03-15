package net.tantonb.socd.world;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Dimension;
import net.minecraft.world.World;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.tantonb.socd.SocdMod;
import net.tantonb.socd.world.dimx.DimxGenerator;
import net.tantonb.socd.world.dimy.DimyGenerator;
import net.tantonb.socd.world.dimz.DimzGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class SocdDimensions {

    private static final Logger LOGGER = LogManager.getLogger();

    // dimension ids
    public static final ResourceLocation ID_DIMX = SocdMod.resLoc("dimx");
    public static final ResourceLocation ID_DIMY = SocdMod.resLoc("dimy");
    public static final ResourceLocation ID_DIMZ = SocdMod.resLoc("dimz");

    // chunk generator codec ids
    public static final ResourceLocation ID_CG_DIMX = SocdMod.resLoc("dimx_chunk_gen");
    public static final ResourceLocation ID_CG_DIMY = SocdMod.resLoc("dimy_chunk_gen");
    public static final ResourceLocation ID_CG_DIMZ = SocdMod.resLoc("dimz_chunk_gen");

    // biome provider codec ids
    public static final ResourceLocation ID_BP_DIMX = SocdMod.resLoc("dimx_biome_provider");
    public static final ResourceLocation ID_BP_DIMY = SocdMod.resLoc("dimy_biome_provider");
    public static final ResourceLocation ID_BP_DIMZ = SocdMod.resLoc("dimz_biome_provider");

    // world keys, see teleport commands
    public static final RegistryKey<World> RK_WORLD_DIMX = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, ID_DIMX);
    public static final RegistryKey<World> RK_WORLD_DIMY = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, ID_DIMY);
    public static final RegistryKey<World> RK_WORLD_DIMZ = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, ID_DIMZ);

    // dimension registry keys
    public static final RegistryKey<Dimension> RK_DIM_DIMX = RegistryKey.getOrCreateKey(Registry.DIMENSION_KEY, ID_DIMX);
    public static final RegistryKey<Dimension> RK_DIM_DIMY = RegistryKey.getOrCreateKey(Registry.DIMENSION_KEY, ID_DIMY);
    public static final RegistryKey<Dimension> RK_DIM_DIMZ = RegistryKey.getOrCreateKey(Registry.DIMENSION_KEY, ID_DIMZ);

    // TODO: haven't found where these are used or referenced...runs fine without these? maybe because not serializing/data loading dimension types?
    //public static final RegistryKey<DimensionType> RK_DIM_TYPE_DIMX = RegistryKey.getOrCreateKey(Registry.DIMENSION_TYPE_KEY, ID_DIMX);
    //public static final RegistryKey<DimensionType> RK_DIM_TYPE_DIMY = RegistryKey.getOrCreateKey(Registry.DIMENSION_TYPE_KEY, ID_DIMY);

    // dimension settings registry keys, used during load phase to register noise settings
    public static final RegistryKey<DimensionSettings> RK_DIM_SET_DIMX = RegistryKey.getOrCreateKey(Registry.NOISE_SETTINGS_KEY, ID_DIMX);
    public static final RegistryKey<DimensionSettings> RK_DIM_SET_DIMY = RegistryKey.getOrCreateKey(Registry.NOISE_SETTINGS_KEY, ID_DIMY);
    public static final RegistryKey<DimensionSettings> RK_DIM_SET_DIMZ = RegistryKey.getOrCreateKey(Registry.NOISE_SETTINGS_KEY, ID_DIMZ);

    // custom dimension generators
    public static final DimensionGenerator DG_DIMX = new DimxGenerator(ID_DIMX, ID_CG_DIMX, ID_BP_DIMX, RK_DIM_SET_DIMX, RK_DIM_DIMX);
    public static final DimensionGenerator DG_DIMY = new DimyGenerator(ID_DIMY, ID_CG_DIMY, ID_BP_DIMY, RK_DIM_SET_DIMY, RK_DIM_DIMY);
    public static final DimensionGenerator DG_DIMZ = new DimzGenerator(ID_DIMZ, ID_CG_DIMZ, ID_BP_DIMZ, RK_DIM_SET_DIMZ, RK_DIM_DIMZ);

    public static List<DimensionGenerator> DIMENSION_GENERATORS = Arrays.asList(DG_DIMX, DG_DIMY, DG_DIMZ);

    /**
     * Called during mod loading, should perform registrations, etc. that do not rely
     * on server specific data, see also onServerStart()...
     */
    public static void onModLoad() {
        for (DimensionGenerator dg : DIMENSION_GENERATORS) {
            dg.onModLoad();
        }
    }

    /**
     * Called during server startup.  This is where per server things like world seeds
     * are available, but also where dynamic/procedurally generated data would need to be
     * produced.  This is also where we load data that minecraft now expects to come from
     * json data files, so we are supplanting such data programmatically...
     */
    public static void onServerStart(FMLServerAboutToStartEvent event) {
        for (DimensionGenerator dg : DIMENSION_GENERATORS) {
            dg.onServerStart(event.getServer());
        }
    }
}
