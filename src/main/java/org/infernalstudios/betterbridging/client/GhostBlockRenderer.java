package org.infernalstudios.betterbridging.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.infernalstudios.betterbridging.BetterBridging;

@Mod.EventBusSubscriber(modid = BetterBridging.MOD_ID, value = Dist.CLIENT)
public class GhostBlockRenderer {
//    @SubscribeEvent
//    public static void onRenderWorldLast(RenderLevelStageEvent event) {
//        Minecraft mc = Minecraft.getInstance();
//        Level level = mc.level;
//        if (level != null) {
//            assert mc.hitResult != null;
//            BlockPos targetPos = BlockPos.containing(mc.hitResult.getLocation());
//            if (mc.hitResult instanceof BlockHitResult blockHitResult) {
//                Direction side = blockHitResult.getDirection();
//                ItemStack stack = mc.player.getMainHandItem();
//                if (!stack.isEmpty() && stack.getItem() instanceof BlockItem blockItem) {
//                    if (targetPos != null) {
//                        renderBlockAt(targetPos.relative(side), event.getPoseStack(), mc.getPartialTick(), mc.renderBuffers().bufferSource(), mc.level, mc.player, stack);
//                    }
//                }
//            }
//        }
//    }
//
//    private static void renderBlockAt(BlockPos pos, PoseStack matrixStack, float partialTicks, MultiBufferSource buffer, Level world, Player player, ItemStack heldStack) {
//        BlockState blockState = ((BlockItem) heldStack.getItem()).getBlock().defaultBlockState();
//        BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
//        matrixStack.pushPose();
//
//
//        Display.BlockDisplay blockDisplay = new Display.BlockDisplay()
//
//
//
//        matrixStack.translate(pos.getX(), pos.getY(), pos.getZ());
//        //matrixStack.translate(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
//        VertexConsumer builder = buffer.getBuffer(RenderType.solid());
//        //blockRenderer.getModelRenderer().renderModel(matrixStack.last(), builder, blockState, blockRenderer.getBlockModel(blockState), 0.5f, 0.5f, 0.5f, 1, 1);
//        blockRenderer.renderSingleBlock(blockState, matrixStack, buffer, 1, 1);
//        matrixStack.popPose();
//    }
}
