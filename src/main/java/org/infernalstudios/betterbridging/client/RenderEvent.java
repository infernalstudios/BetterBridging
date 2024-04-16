package org.infernalstudios.betterbridging.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.infernalstudios.betterbridging.BetterBridging;
import org.infernalstudios.betterbridging.items.ItemsInit;

@Mod.EventBusSubscriber(modid = BetterBridging.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RenderEvent {
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            OutlineRenderer.grabNewLookPos(player);
        }
    }

    @SubscribeEvent
    public void cancelDefaultOutline(RenderHighlightEvent event) {
        Player player = Minecraft.getInstance().player;
        if (player != null && player.getMainHandItem().getItem() instanceof BlockItem) {
            if (player.getOffhandItem().is(ItemsInit.BRIDGE_BRACE.get())) {
                event.setCanceled(true);
            }
        }
    }
}
