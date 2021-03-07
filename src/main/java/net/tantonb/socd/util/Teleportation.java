package net.tantonb.socd.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Function;

public class Teleportation {

    private static final Logger LOGGER = LogManager.getLogger();

    /*
    private static final Logger LOGGER = LogManager.getLogger();

    private static boolean isValidPlayerPos(ServerPlayerEntity player, ServerWorld world, BlockPos pos) {

        // don't teleport player into solid or liquid blocks
        if (!world.isAirBlock(pos) || !world.isAirBlock(pos.up())) {
            return false;
        }

        // make sure player arrives on a solid block or is flying
        BlockState bs = world.getBlockState(pos.down());
        if (!bs.isSolid() && !player.abilities.isFlying) {
            return false;
        }

        return true;
    }

    @Nullable
    private static BlockPos findValidPosVertically(ServerPlayerEntity player, ServerWorld world, BlockPos startPos) {

        // see if they can arrive at same position in new dimension
        if (isValidPlayerPos(player, world, startPos)) {
            return startPos;
        }

        // search up and down y axis for valid player pos
        for (int i = 1; i < 256; ++i) {
            if (isValidPlayerPos(player, world, startPos.up(i))) {
                return startPos.up(i);
            }
            if (isValidPlayerPos(player, world, startPos.down(i))) {
                return startPos.down(i);
            }
        }
        return null;
    }

    static final int MAX_POS_SEARCH_RANGE = 20;

    @Nullable
    private static BlockPos findValidNearbyPos(ServerPlayerEntity player, ServerWorld world, BlockPos startPos) {

        // look near starting position for safe destination position
        BlockPos validPos = findValidPosVertically(player, world, startPos);
        if (validPos == null) {
            // search in expanding x and z directions for valid position
            for (int i = 1; validPos == null && i <= MAX_POS_SEARCH_RANGE; ++i) {
                validPos = findValidPosVertically(player, world, startPos.north(i));
                if (validPos == null) {
                    validPos = findValidPosVertically(player, world, startPos.east(i));
                    if (validPos == null) {
                        validPos = findValidPosVertically(player, world, startPos.south(i));
                        if (validPos == null) {
                            validPos = findValidPosVertically(player, world, startPos.west(i));
                        }
                    }
                }
            }
        }
        return validPos;
    }

    public static boolean teleportNearDimPos(ServerPlayerEntity player, DimensionType dim, BlockPos pos) {
        BlockPos currentPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
        ServerWorld world = player.server.getWorld(dim);
        LOGGER.info("Searching for valid position near player location {}, flying is {}", currentPos, player.isAirBorne);
        BlockPos validPos = findValidNearbyPos(player, world, currentPos);
        if (validPos == null) {
            return false;
        }
        player.changeDimension(dim, new ITeleporter() {
            @Override
            public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                entity = repositionEntity.apply(false);
                entity.setPositionAndUpdate(validPos.getX(), validPos.getY(), validPos.getZ());
                return entity;
            }
        });
        return true;
    }

     */

    /**
     * Teleports the player to the specified position in a destination world.
     *
     * @param player
     * @param destWorld
     * @param destPos
     * @return
     */
    public static boolean teleportPlayer(ServerPlayerEntity player, ServerWorld destWorld, BlockPos destPos) {
        LOGGER.info(
                "Teleporting player to pos {} in destination world {}",
                destPos,
                destWorld.getDimensionKey()
        );

        player.changeDimension(destWorld, new ITeleporter() {
            @Override
            public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                entity = repositionEntity.apply(false);
                entity.setPositionAndUpdate(destPos.getX(), destPos.getY(), destPos.getZ());
                return entity;
            }
        });

        return true;
    }

    public static int HORIZONTAL_SEARCH_RANGE = 200;
    public static int VERTICAL_SEARCH_RANGE = 256;

    /**
     * Determines if a position is a safe location for the player.
     *
     * @param player
     * @param world
     * @param pos
     * @return
     */
    public static boolean isSafePlayerPos(ServerPlayerEntity player, ServerWorld world, BlockPos pos) {

        // don't place the player in solid or liquid blocks
        if (!world.isAirBlock(pos) || !world.isAirBlock(pos.up())) {
            return false;
        }

        // block below player should be solid unless they are flying
        BlockState bs = world.getBlockState(pos.down());
        if (!bs.isSolid() && !player.abilities.isFlying) {
            return false;
        }

        return true;
    }

    /**
     * Looks above and below the given start position for a safe position to place the player at.
     *
     * @param player
     * @param world
     * @param startPos
     * @return
     */
    public static BlockPos getNearestSafeVerticalPos(ServerPlayerEntity player, ServerWorld world, BlockPos startPos) {

        // check start pos itself
        if (isSafePlayerPos(player, world, startPos)) {
            return startPos;
        }

        // look above and below start pos
        for (int i = 1; i < VERTICAL_SEARCH_RANGE; ++i) {
            if (isSafePlayerPos(player, world, startPos.down(i))) {
                return startPos.down(i);
            } else if (isSafePlayerPos(player, world, startPos.up(i))) {
                return startPos.up(i);
            }
        }

        return null;
    }

    /**
     * Attempts to find a safe arrival position in a world based on player's current position.  Begins
     * search at player's current world position in the destination world. Safe is defined as placing the player
     * on a solid block with two air blocks above.
     *
     * @param player
     * @param destWorld
     * @return
     */
    public static BlockPos getNearestSafePos(ServerPlayerEntity player, ServerWorld destWorld) {

        // check player's current position, it may work
        BlockPos startPos = player.getPosition();
        BlockPos destPos = getNearestSafeVerticalPos(player, destWorld, startPos);
        if (destPos != null) {
            return destPos;
        }

        // scan outward in the cardinal directions for safe positions
        for (int i = 1; i < HORIZONTAL_SEARCH_RANGE; ++i) {
            destPos = getNearestSafeVerticalPos(player, destWorld, startPos.north(i));
            if (destPos == null) {
                destPos = getNearestSafeVerticalPos(player, destWorld, startPos.east(i));
                if (destPos == null) {
                    destPos = getNearestSafeVerticalPos(player, destWorld, startPos.south(i));
                    if (destPos == null) {
                        destPos = getNearestSafeVerticalPos(player, destWorld, startPos.west(i));
                    }
                }
            }
            if (destPos != null) {
                return destPos;
            }
        }

        return null;
    }

    /**
     * Attempts to teleport player to a destination world.  First a safe arrival point must be located.
     *
     * @param player
     * @param destWorld
     * @return true if player successfully teleported, else false
     */
    public static boolean teleportPlayer(ServerPlayerEntity player, ServerWorld destWorld) {
        BlockPos destPos = getNearestSafePos(player, destWorld);
        if (destPos == null) {
            player.sendStatusMessage(new StringTextComponent("Failed to locate safe arrival position!"), false);
            return false;
        }
        return teleportPlayer(player, destWorld, destPos);
    }
}