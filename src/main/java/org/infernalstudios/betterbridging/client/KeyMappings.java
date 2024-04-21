package org.infernalstudios.betterbridging.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.common.Mod;
import org.infernalstudios.betterbridging.BetterBridging;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeyMappings {
    public static final String KEY_CYCLE = "key." + BetterBridging.MOD_ID + ".cycle";
    public static KeyMapping CYCLE_KEY;

    public static void registerKeys() {
        CYCLE_KEY = registerKeybinding(new KeyMapping(KEY_CYCLE, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KeyMapping.CATEGORY_GAMEPLAY));
    }

    private static KeyMapping registerKeybinding(KeyMapping key) {
        ClientRegistry.registerKeyBinding(key);
        return key;
    }
}
