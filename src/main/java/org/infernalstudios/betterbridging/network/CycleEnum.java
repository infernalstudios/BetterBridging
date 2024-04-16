package org.infernalstudios.betterbridging.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class CycleEnum {
    private static final int MAX = 32767 * 2;
    UUID id;

    public CycleEnum(FriendlyByteBuf buf) {
        this.id = buf.readUUID();
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeUUID(id);
    }

    public CycleEnum(UUID id){
        this.id = id;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(this::handle);
        ctx.get().setPacketHandled(true);
    }

    private void handle() {
        int old = DirectionMap.DIRECTION_MAP.get(id);
        int updated = old+1;
        if (updated > 3) updated = 0;
        DirectionMap.DIRECTION_MAP.put(id, updated);
    }
}
