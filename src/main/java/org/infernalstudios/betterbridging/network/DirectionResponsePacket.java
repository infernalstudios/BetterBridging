package org.infernalstudios.betterbridging.network;

import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.infernalstudios.betterbridging.events.BridgingEvents;

import java.util.UUID;
import java.util.function.Supplier;

public class DirectionResponsePacket {
    private Direction direction;
    private UUID playerId;

    public DirectionResponsePacket(FriendlyByteBuf buf) {
        this.direction = buf.readEnum(Direction.class);
        this.playerId = buf.readUUID();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeEnum(direction);
        buf.writeUUID(playerId);
    }

    public DirectionResponsePacket(Direction direction, UUID playerId) {
        this.direction = direction;
        this.playerId = playerId;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(this::handle);
        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    public void handle() {
        BridgingEvents.setClientDirection(playerId, direction);
    }
}
