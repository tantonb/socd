package net.tantonb.socd.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.tantonb.socd.util.Teleportation;
import net.tantonb.socd.world.SocdDimensions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TeleportCommands {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Teleport the player to another dimension.
     *
     * @param player the server player entity to teleport
     * @param remoteKey the remote world's registry key
     * @return 1 if successful, else 0
     */
    public static int teleport(ServerPlayerEntity player, RegistryKey<World> remoteKey) {

        // make sure player is not already in the destination world
        RegistryKey<World> localKey = player.world.getDimensionKey();
        if (localKey.equals(remoteKey)) {
            player.sendStatusMessage(new StringTextComponent("Already in desired dimension!"), false);
            return 0;
        }

        // get the remote world
        ServerWorld remoteWorld = player.server.getWorld(remoteKey);
        if (remoteWorld == null) {
            LOGGER.error("Could not find destination world '{}'", remoteKey);
            return 0;
        }

        // send the player to the remote world
        if (Teleportation.teleportPlayer(player, remoteWorld)) {
            return 1;
        }

        return 0;
    }

    /**
     * Command to teleport player to overworld.
     *
     * @param player
     * @return
     */
    public static int home(ServerPlayerEntity player) {
        return teleport(player, World.OVERWORLD);
    }

    /**
     * Command to teleport player to dimension X.
     *
     * @param player
     * @return
     */
    public static int dimx(ServerPlayerEntity player) {
        return teleport(player, SocdDimensions.RK_WORLD_DIMX);
    }

    /**
     * Command to teleport player to dimension Y.
     *
     * @param player
     * @return
     */
    public static int dimy(ServerPlayerEntity player) {
        return teleport(player, SocdDimensions.RK_WORLD_DIMY);
    }

    /**
     * Register teleportation commands with server.
     *
     * @param dispatcher
     */
    public static void register(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("dimx")
                .executes(context -> dimx(context.getSource().asPlayer()))
        );

        dispatcher.register(Commands.literal("dimy")
                .executes(context -> dimy(context.getSource().asPlayer()))
        );

        dispatcher.register(Commands.literal("home")
                .executes(context -> home(context.getSource().asPlayer()))
        );
    }
}
