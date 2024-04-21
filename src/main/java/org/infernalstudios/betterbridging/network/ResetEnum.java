package org.infernalstudios.betterbridging.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

import java.util.UUID;

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

    public void handle(CustomPayloadEvent.Context ctx){
        ctx.enqueueWork(this::handle);
        ctx.setPacketHandled(true);
    }

    private void handle() {
        DirectionMap.DIRECTION_MAP.put(id, 0);
    }
}
