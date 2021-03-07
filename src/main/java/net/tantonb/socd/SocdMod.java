package net.tantonb.socd;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.tantonb.socd.commands.SocdCommands;
import net.tantonb.socd.config.ClientConfig;
import net.tantonb.socd.config.ServerConfig;
import net.tantonb.socd.config.SocdConfig;
import net.tantonb.socd.world.SocdBiomes;
import net.tantonb.socd.world.SocdDimensions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml.original file
@Mod(SocdMod.MOD_ID)
public class SocdMod
{
    public static final String MOD_ID = "socd";

    private static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static ServerConfig SERVER_CONFIG;
    public static ClientConfig CLIENT_CONFIG;

    // used for ids, generate resource location using modid for namespace
    public static final ResourceLocation resLoc(String name) {
        return new ResourceLocation(SocdMod.MOD_ID, name);
    }

    public SocdMod() {
        LOGGER.info("SocdMod instantiation...");

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        // mod loading
        modEventBus.addListener(this::setup);

        SocdBiomes.deferRegistration(modEventBus);
        SocdDimensions.onModLoad();
        //ModItems.register(modEventBus);
        //ModBlocks.register(modEventBus);
        //ModTileEntities.register(modEventBus);

        // load mod configuration
        SocdConfig config = SocdConfig.init();
        SERVER_CONFIG = config.server;
        CLIENT_CONFIG = config.client;

        LOGGER.info("client config show custom world warning? {}", CLIENT_CONFIG.showCustomWorldWarning);
        forgeBus.addListener(SocdDimensions::onServerStartup);
        forgeBus.addListener(SocdCommands::onRegisterCommands);
    }

    private void setup(FMLCommonSetupEvent event) {
        LOGGER.debug("Handling FMLCommonSetupEvent setup event: {}", event);

        event.enqueueWork(() -> {
        });
    }
}
