package org.infernalstudios.betterbridging.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.infernalstudios.betterbridging.BetterBridging;

public class NetworkInit {
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerPackets() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(BetterBridging.MOD_ID, "packets"), () -> "1.0", s -> true, s -> true);

        INSTANCE.registerMessage(nextID(), RequestDirectionPacket.class, RequestDirectionPacket::encode, RequestDirectionPacket::new, RequestDirectionPacket::handle);
        INSTANCE.registerMessage(nextID(), DirectionResponsePacket.class, DirectionResponsePacket::encode, DirectionResponsePacket::new, DirectionResponsePacket::handle);
    }
}
