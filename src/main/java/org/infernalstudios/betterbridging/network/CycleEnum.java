package org.infernalstudios.betterbridging.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

import java.util.UUID;

public class CycleEnum {
    private static final int MAX = 32767 * 2;
    UUID id;
    int dir;

    public CycleEnum(FriendlyByteBuf buf) {
        this.id = buf.readUUID();
        this.dir = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeUUID(id);
        buf.writeInt(dir);
    }

    public CycleEnum(UUID id, int dir){
        this.id = id;
        this.dir = dir;
    }

    public void handle(CustomPayloadEvent.Context ctx){
        ctx.enqueueWork(this::handle);
        ctx.setPacketHandled(true);
    }

    private void handle() {
        DirectionMap.DIRECTION_MAP.put(id, dir);
    }
}
