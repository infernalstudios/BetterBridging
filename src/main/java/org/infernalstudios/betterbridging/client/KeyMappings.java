package org.infernalstudios.betterbridging.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.infernalstudios.betterbridging.BetterBridging;
import org.lwjgl.glfw.GLFW;

public class KeyMappings {
    public static final String KEY_CYCLE = "key." + BetterBridging.MOD_ID + ".cycle";

    public static final KeyMapping CYCLE_KEY = new KeyMapping(KEY_CYCLE, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KeyMapping.CATEGORY_GAMEPLAY);
}
