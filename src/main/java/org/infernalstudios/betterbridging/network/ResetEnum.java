package org.infernalstudios.betterbridging.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ResetEnum {
    UUID id;

    public ResetEnum(FriendlyByteBuf buf) {
        this.id = buf.readUUID();
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeUUID(id);
    }

    public ResetEnum(UUID id){
        this.id = id;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(this::handle);
        ctx.get().setPacketHandled(true);
    }

    private void handle() {
        DirectionMap.DIRECTION_MAP.put(id, 0);
    }
}
