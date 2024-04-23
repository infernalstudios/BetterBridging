package org.infernalstudios.betterbridging.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.infernalstudios.betterbridging.BetterBridgingConfig;
import org.infernalstudios.betterbridging.enchantments.EnchantmentsInit;
import org.infernalstudios.betterbridging.items.ItemsInit;
import org.infernalstudios.betterbridging.network.DirectionMap;

import java.awt.*;
import java.util.UUID;

public class OutlineRenderer {

    private static BlockPos lookPos = null;
    public static Direction getDirection(UUID playerId) {
        return DirectionMap.getDirection(playerId);
    }

    public static void grabNewLookPos(Player player) {
        if (player.getOffhandItem().is(ItemsInit.BRIDGE_BRACE.get()) && player.getMainHandItem().getItem() instanceof BlockItem) {

            float blockRange = 10;
            int offset = 1;

            Level level = player.level;

            Vec3 start = player.position().add(0, player.getEyeHeight(), 0);
            Vec3 range = player.getLookAngle().scale(blockRange);
            BlockHitResult raytrace = level
                    .clip(new ClipContext(start, start.add(range), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
            if (raytrace.getType() == HitResult.Type.BLOCK && start.distanceToSqr(raytrace.getLocation()) <= Mth.square(Minecraft.getInstance().gameMode.getPickRange())) {
                if (player.level.getBlockState(raytrace.getBlockPos()).canBeReplaced()) {
                    offset = 0;
                }
                lookPos = raytrace.getBlockPos().relative(raytrace.getDirection(), offset);
            } else lookPos = null;
        } else lookPos = null;
    }

    public static void renderBlockOutline(PoseStack matrixStack, Camera camera, Minecraft mc) {
        if (lookPos != null) {

            Player player = mc.player;
            Level level = player.level;
            level.getProfiler().popPush("outline");
            UUID playerId = player.getUUID();
            Direction nextDirection = getDirection(playerId);
            BlockPos pos = lookPos;

            if (level.getWorldBorder().isWithinBounds(pos)) {
                VertexConsumer builder = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.lines());

                Vec3 vector3d = camera.getPosition();
                double pX = vector3d.x();
                double pY = vector3d.y();
                double pZ = vector3d.z();

                int color = Color.BLACK.getRGB();
                //config is setup to use RGBA
                float r = FastColor.ARGB32.red(color) / 255f;
                float g = FastColor.ARGB32.green(color) / 255f;
                float b = FastColor.ARGB32.blue(color) / 255f;
                float a = FastColor.ARGB32.alpha(color) / 255f;

                VoxelShape shape = Blocks.DIRT.defaultBlockState().getShape(level, pos);
                renderVoxelShape(matrixStack, builder, shape, pos.getX() - pX, pos.getY() - pY, pos.getZ() - pZ, r, g, b, a/3);

                int width = BetterBridgingConfig.General.defaultWidth;
                if (BetterBridgingConfig.Enchantment.enableWidening) {
                    int enchLevel = player.getOffhandItem().getEnchantmentLevel(EnchantmentsInit.WIDENING.get());
                    if (enchLevel > 0) width = width + (enchLevel * 2);
                }
                int placedWidth = 1;
                int placedDistance = 0;
                while (placedWidth < width && (placedWidth < player.getMainHandItem().getCount() || player.isCreative())) {
                    BlockPos additionalPos = pos.relative(nextDirection, 1 + placedDistance / 2);
                    renderVoxelShape(matrixStack, builder, shape, additionalPos.getX() - pX, additionalPos.getY() - pY, additionalPos.getZ() - pZ, r, g, b, a/3);
                    nextDirection = nextDirection.getOpposite();
                    ++placedWidth;
                    placedDistance++;
                }
            }
        }
    }

    private static void renderVoxelShape(PoseStack pMatrixStack, VertexConsumer pBuffer, VoxelShape pShape,
                                         double pX, double pY, double pZ, float pRed, float pGreen, float pBlue, float pAlpha) {
        PoseStack.Pose last = pMatrixStack.last();
        pShape.forAllEdges((e1, e2, e3, e4, e5, e6) -> {
            float f = (float) (e4 - e1);
            float f1 = (float) (e5 - e2);
            float f2 = (float) (e6 - e3);
            float f3 = Mth.sqrt(f * f + f1 * f1 + f2 * f2);
            f = f / f3;
            f1 = f1 / f3;
            f2 = f2 / f3;
            pBuffer.vertex(last.pose(), (float) (e1 + pX), (float) (e2 + pY), (float) (e3 + pZ)).color(pRed, pGreen, pBlue, pAlpha).normal(last.normal(), f, f1, f2).endVertex();
            pBuffer.vertex(last.pose(), (float) (e4 + pX), (float) (e5 + pY), (float) (e6 + pZ)).color(pRed, pGreen, pBlue, pAlpha).normal(last.normal(), f, f1, f2).endVertex();
        });
    }
}
