package org.infernalstudios.betterbridging.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.infernalstudios.betterbridging.BetterBridging;
import org.infernalstudios.betterbridging.network.CycleEnum;
import org.infernalstudios.betterbridging.network.NetworkInit;

@Mod.EventBusSubscriber(modid = BetterBridging.MOD_ID, value = Dist.CLIENT)
public class CycleDirectionEvent {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyMappings.CYCLE_KEY.consumeClick()) {
            Player player = Minecraft.getInstance().player;
            if (player != null) NetworkInit.INSTANCE.send(PacketDistributor.SERVER.noArg(), new CycleEnum(player.getUUID()));
        }
    }
}
