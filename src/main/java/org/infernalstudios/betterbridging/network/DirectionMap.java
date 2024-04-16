package org.infernalstudios.betterbridging.network;

import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.infernalstudios.betterbridging.BetterBridging;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = BetterBridging.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DirectionMap {

    public static Map<UUID, Integer> DIRECTION_MAP = new HashMap<>();

    @SubscribeEvent
    public static void resetEnumOnJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        DIRECTION_MAP.put(player.getUUID(), 0);
        NetworkInit.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ResetEnum(player.getUUID()));
    }

    @SubscribeEvent
    public static void deleteEnumOnLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        DIRECTION_MAP.remove(event.getEntity().getUUID());
    }

    public static Direction getDirection(UUID uuid) {
        int dir = DIRECTION_MAP.get(uuid);
        return switch (dir) {
            case 0 -> Direction.NORTH;
            case 1 -> Direction.EAST;
            case 2 -> Direction.SOUTH;
            default -> Direction.WEST;
        };
    }
}
