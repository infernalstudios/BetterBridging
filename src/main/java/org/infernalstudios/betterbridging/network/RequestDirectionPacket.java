package org.infernalstudios.betterbridging.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import org.infernalstudios.betterbridging.access.LivingEntityAccess;

import java.util.function.Supplier;

public class RequestDirectionPacket {
    public RequestDirectionPacket(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buf){
    }

    public RequestDirectionPacket() {
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(this::handle);
        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private void handle() {
        LocalPlayer player = Minecraft.getInstance().player;
        Direction currentDirection = LivingEntityAccess.get(player).getDirection();
        DirectionResponsePacket responsePacket = new DirectionResponsePacket(currentDirection, player.getUUID());
        NetworkInit.INSTANCE.send(PacketDistributor.SERVER.noArg(), responsePacket);
    }
}
