package org.infernalstudios.betterbridging.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.MendingEnchantment;
import org.infernalstudios.betterbridging.Config;
import org.infernalstudios.betterbridging.items.ItemsInit;

public class WideningEnchantment extends Enchantment {
    public WideningEnchantment(Enchantment.Rarity rarity, EquipmentSlot... slots) {super(rarity, EnchantmentCategory.BREAKABLE, slots);}

    @Override
    public int getMinCost(int enchantmentLevel) {return enchantmentLevel * 25;}

    @Override
    public int getMaxCost(int enchantmentLevel) {return this.getMinCost(enchantmentLevel) + 50;}

    @Override
    public int getMaxLevel() {return 2;}

    @Override
    public boolean checkCompatibility(Enchantment ench) {return !(ench instanceof MendingEnchantment) && super.checkCompatibility(ench);}

    @Override
    public boolean isTreasureOnly() {return Config.ENABLE_WIDENING.get() && !Config.getEnchantmentSource(Config.ENABLE_TABLE);}

    @Override
    public boolean isTradeable() {return Config.ENABLE_WIDENING.get() && Config.getEnchantmentSource(Config.ENABLE_TRADE);}

    @Override
    public boolean isDiscoverable() {return Config.ENABLE_WIDENING.get() && Config.getEnchantmentSource(Config.ENABLE_LOOT);}

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return Config.ENABLE_WIDENING.get()
                && Config.getEnchantmentSource(Config.ENABLE_TABLE)
                && (stack.getItem() == ItemsInit.BRIDGE_BRACE.get() || stack.getItem() == Items.BOOK);
    }

    @Override
    public boolean canEnchant(ItemStack stack) {return (stack.getItem() == ItemsInit.BRIDGE_BRACE.get() || stack.getItem() == Items.BOOK) && Config.ENABLE_WIDENING.get();}

    @Override
    public boolean isAllowedOnBooks() {return Config.ENABLE_WIDENING.get();}
}