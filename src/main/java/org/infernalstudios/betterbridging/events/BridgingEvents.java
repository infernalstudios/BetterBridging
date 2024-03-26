package org.infernalstudios.betterbridging.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.infernalstudios.betterbridging.BetterBridging;
import org.infernalstudios.betterbridging.Config;
import org.infernalstudios.betterbridging.access.LivingEntityAccess;
import org.infernalstudios.betterbridging.enchantments.EnchantmentsInit;
import org.infernalstudios.betterbridging.items.ItemsInit;
import org.infernalstudios.betterbridging.network.DirectionResponsePacket;
import org.infernalstudios.betterbridging.network.NetworkInit;
import org.infernalstudios.betterbridging.network.RequestDirectionPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = BetterBridging.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BridgingEvents {

    //store and read player direction data
    public static Map<UUID, Direction> playerDirectionMap = new HashMap<>();

    public static Direction getClientDirection(UUID playerId) {
        return playerDirectionMap.get(playerId);
    }

    public static void setClientDirection(UUID playerId, Direction direction) {
        playerDirectionMap.put(playerId, direction);
    }

    @SubscribeEvent
    public void onBLockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && player.getOffhandItem().is(ItemsInit.BRIDGE_BRACE.get())) {

            UUID playerId = player.getUUID();
            if (!player.level().isClientSide())
                NetworkInit.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new RequestDirectionPacket());

            Direction nextDirection = Direction.SOUTH;
            if (getClientDirection(playerId) != null) {
                nextDirection = getClientDirection(playerId);
            }

            int width = Config.DEFAULT_WIDTH.get();
//            if (Config.getWideningEnabled()) {
//                int enchLevel = player.getOffhandItem().getEnchantmentLevel(EnchantmentsInit.WIDENING.get());
//                if (enchLevel > 0) width = width + (enchLevel * 2);
//            }

            int placedWidth = 0;
            int placedDistance = 0;
            LevelAccessor level = event.getLevel();
            while (placedWidth < width-1 && player.getMainHandItem().getItem() instanceof BlockItem blockItem && blockItem.getBlock().defaultBlockState() == event.getPlacedBlock() && player.getMainHandItem().getCount() > 0) {
                BlockPos additionalPos = event.getPos().relative(nextDirection, 1 + placedDistance / 2);
                if (event.getLevel().getBlockState(additionalPos).canBeReplaced()) {
                    Map<UUID, Direction> testMap = playerDirectionMap;
                    level.setBlock(additionalPos, event.getPlacedBlock(), 11);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, additionalPos, GameEvent.Context.of(player, level.getBlockState(additionalPos)));
                    if (!player.isCreative()) player.getMainHandItem().shrink(1);
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
        }
    }
}