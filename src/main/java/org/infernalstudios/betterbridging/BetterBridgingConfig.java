package org.infernalstudios.betterbridging;

import org.infernalstudios.config.Config;
import org.infernalstudios.config.annotation.Category;
import org.infernalstudios.config.annotation.Configurable;
import org.infernalstudios.config.annotation.IntegerRange;

@Category("Config")
public class BetterBridgingConfig {
    // Defined in mod constructor
    public static Config INSTANCE;

    @Category("General")
    public static class General {
        @Configurable(description = "Set the default bridging width when using the Bridge Brace")
        @IntegerRange(min = 1, max = 999)
        public static int defaultWidth = 3;
    }

    @Category("Enchantment")
    public static class Enchantment {
        @Configurable(description = "Enable the Widening enchantment")
        public static boolean enableWidening = true;

        @Configurable(description = "Enable getting the Widening enchantment from enchantment tables")
        public static boolean WideningFromEnchantmentTable = true;

        @Configurable(description = "Enable villagers to trade Widening books")
        public static boolean WideningCanBeTraded = true;

        @Configurable(description = "Enable Widening books generating in loot chests")
        public static boolean WideningFromLootChests = true;
    }
}