package org.infernalstudios.betterbridging;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final String CATEGORY_CONFIG = "Config";
    public static final String CATEGORY_GENERAL = "General";
    public static final String CATEGORY_ENCHANTMENT = "Enchantment";

    public static ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.IntValue DEFAULT_WIDTH;
    public static ForgeConfigSpec.BooleanValue ENABLE_WIDENING;
    public static ForgeConfigSpec.BooleanValue ENABLE_TABLE;
    public static ForgeConfigSpec.BooleanValue ENABLE_TRADE;
    public static ForgeConfigSpec.BooleanValue ENABLE_LOOT;

    public static Boolean getEnchantmentSource(ForgeConfigSpec.BooleanValue source) {
        return source.get();
    }

    static {

        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

        SERVER_BUILDER.push(CATEGORY_CONFIG);

        setupGeneral(SERVER_BUILDER);
        setupEnchant(SERVER_BUILDER);

        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();

    }

    private static void setupGeneral(ForgeConfigSpec.Builder SERVER_BUILDER) {

        SERVER_BUILDER.push(CATEGORY_GENERAL);

        SERVER_BUILDER.comment("Set the default bridging width when using the Bridge Brace", "default = 3");
        DEFAULT_WIDTH = SERVER_BUILDER.defineInRange("defaultWidth", 3, 1, 999);

        SERVER_BUILDER.pop();
    }

    private static void setupEnchant(ForgeConfigSpec.Builder SERVER_BUILDER) {

        SERVER_BUILDER.push(CATEGORY_ENCHANTMENT);

        SERVER_BUILDER.comment("Enable the Widening enchantment", "default = true");
        ENABLE_WIDENING = SERVER_BUILDER.define("enableWidening", true);

        SERVER_BUILDER.comment("\nEnable getting the Widening enchantment from enchantment tables", "default = true");
        ENABLE_TABLE = SERVER_BUILDER.define("WideningFromEnchantmentTable", true);

        SERVER_BUILDER.comment("\nEnable villagers to trade Widening books", "default = true");
        ENABLE_TRADE = SERVER_BUILDER.define("WideningCanBeTraded", true);

        SERVER_BUILDER.comment("\nEnable Widening books generating in loot chests", "default = true");
        ENABLE_LOOT = SERVER_BUILDER.define("WideningFromLootChests", true);

        SERVER_BUILDER.pop();
    }
}