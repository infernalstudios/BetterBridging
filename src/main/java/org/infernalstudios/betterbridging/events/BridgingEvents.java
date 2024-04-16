package org.infernalstudios.betterbridging.events;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;
import org.infernalstudios.betterbridging.Config;
import org.infernalstudios.betterbridging.enchantments.EnchantmentsInit;
import org.infernalstudios.betterbridging.items.ItemsInit;
import org.infernalstudios.betterbridging.network.DirectionMap;
import org.infernalstudios.betterbridging.network.NetworkInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class BridgingEvents {

    public static Direction getDirection(UUID playerId) {
        return DirectionMap.getDirection(playerId);
    }

    @SubscribeEvent
    public void onBLockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && player.getOffhandItem().is(ItemsInit.BRIDGE_BRACE.get())) {

            UUID playerId = player.getUUID();
            Direction nextDirection = getDirection(playerId);

            int width = Config.DEFAULT_WIDTH.get();
            if (Config.ENABLE_WIDENING.get()) {
                int enchLevel = player.getOffhandItem().getEnchantmentLevel(EnchantmentsInit.WIDENING.get());
                if (enchLevel > 0) width = width + (enchLevel * 2);
            }

            int placedWidth = 1;
            int placedDistance = 0;
            LevelAccessor level = event.getLevel();
            while (placedWidth < width && player.getMainHandItem().getItem() instanceof BlockItem blockItem && blockItem.getBlock().defaultBlockState() == event.getPlacedBlock() && (player.getMainHandItem().getCount() > 1 || player.isCreative())) {
                BlockPos additionalPos = event.getPos().relative(nextDirection, 1 + placedDistance / 2);
                if (event.getLevel().getBlockState(additionalPos).canBeReplaced()) {
                    level.setBlock(additionalPos, event.getPlacedBlock(), 3);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, additionalPos, GameEvent.Context.of(player, level.getBlockState(additionalPos)));
                    if (!player.isCreative()) player.setItemInHand(InteractionHand.MAIN_HAND, player.getMainHandItem().copyWithCount(player.getMainHandItem().getCount()-1));
                    player.getOffhandItem().hurtAndBreak(1, player, (pl) -> {
                        pl.broadcastBreakEvent(EquipmentSlot.OFFHAND);
                    });
                    SoundType newSound = event.getPlacedBlock().getSoundType();
                    level.playSound(player, additionalPos, newSound.getPlaceSound(), SoundSource.BLOCKS, newSound.getVolume(), newSound.getPitch());
                    player.swing(InteractionHand.OFF_HAND);
                }

                nextDirection = nextDirection.getOpposite();
                ++placedWidth;
                placedDistance++;
            }
            if (!player.isCreative()) player.setItemInHand(InteractionHand.MAIN_HAND, player.getMainHandItem().copyWithCount(player.getMainHandItem().getCount()-1));
        }
    }
}