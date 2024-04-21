package org.infernalstudios.betterbridging.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.SimpleChannel;
import org.infernalstudios.betterbridging.BetterBridging;

public class NetworkInit {
    public static SimpleChannel INSTANCE;

    public static void registerPackets() {
        INSTANCE = ChannelBuilder.named(new ResourceLocation(BetterBridging.MOD_ID, "packets"))
                .optional()
                .acceptedVersions((status, version) -> true)
                .networkProtocolVersion(1)
                .simpleChannel();

        INSTANCE.messageBuilder(CycleEnum.class, NetworkDirection.PLAY_TO_SERVER).encoder(CycleEnum::encode).decoder(CycleEnum::new).consumerNetworkThread(CycleEnum::handle).add();
        INSTANCE.messageBuilder(ResetEnum.class, NetworkDirection.PLAY_TO_CLIENT).encoder(ResetEnum::encode).decoder(ResetEnum::new).consumerNetworkThread(ResetEnum::handle).add();
        MinecraftForge.EVENT_BUS.register(new NetworkInit());
    }
}
