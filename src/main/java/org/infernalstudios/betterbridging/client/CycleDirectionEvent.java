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
import org.infernalstudios.betterbridging.network.DirectionMap;
import org.infernalstudios.betterbridging.network.NetworkInit;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = BetterBridging.MOD_ID, value = Dist.CLIENT)
public class CycleDirectionEvent {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyMappings.CYCLE_KEY.consumeClick()) {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                UUID id = player.getUUID();
                int old = DirectionMap.DIRECTION_MAP.get(id);
                int updated = old+1;
                if (updated > 3) updated = 0;
                DirectionMap.DIRECTION_MAP.put(id, updated);
                NetworkInit.INSTANCE.send(PacketDistributor.SERVER.noArg(), new CycleEnum(id, updated));
            }
        }
    }
}
