package net.tantonb.socd.world;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tantonb.socd.SocdMod;

import java.util.function.Supplier;

public class SocdBiomes {

    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, SocdMod.MOD_ID);

    public static final RegistryKey<Biome> RK_BIOME_DIMX_PLAINS = register("dimx_plains", () -> BiomeMaker.makePlainsBiome(false));

    public static RegistryKey<Biome> register(String name, Supplier<Biome> biomeSup) {
        BIOMES.register(name, biomeSup);
        return RegistryKey.getOrCreateKey(Registry.BIOME_KEY, SocdMod.resLoc(name));
    }

    public static void deferRegistration(IEventBus bus) {
        BIOMES.register(bus);
    }
}
