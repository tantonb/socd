package net.tantonb.socd.commands;

import net.minecraftforge.event.RegisterCommandsEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SocdCommands {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void onRegisterCommands(RegisterCommandsEvent event) {
        TeleportCommands.register(event.getDispatcher());
    }
}
