package org.infernalstudios.betterbridging.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.infernalstudios.betterbridging.client.OutlineRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(
            method = "renderLevel",
            at = @At(value = "FIELD",
                    target = "Lnet/minecraft/client/Minecraft;hitResult:Lnet/minecraft/world/phys/HitResult;",
                    ordinal = 1
            )
    )
    private void experimentation$renderPreview(PoseStack matrixStack, float partialTicks, long finishNanoTime, boolean blockOutlines, Camera camera, GameRenderer renderer, LightTexture lightTexture, Matrix4f matrix4f, CallbackInfo ci) {
        if (blockOutlines) OutlineRenderer.renderBlockOutline(matrixStack, camera, this.minecraft);
    }
}
