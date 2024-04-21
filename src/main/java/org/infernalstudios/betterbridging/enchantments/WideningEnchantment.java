package org.infernalstudios.betterbridging.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.MendingEnchantment;
import org.infernalstudios.betterbridging.BetterBridgingConfig;
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
    public boolean isTreasureOnly() {return BetterBridgingConfig.Enchantment.enableWidening && !BetterBridgingConfig.Enchantment.WideningFromEnchantmentTable;}

    @Override
    public boolean isTradeable() {return BetterBridgingConfig.Enchantment.enableWidening && BetterBridgingConfig.Enchantment.WideningCanBeTraded;}

    @Override
    public boolean isDiscoverable() {return BetterBridgingConfig.Enchantment.enableWidening && BetterBridgingConfig.Enchantment.WideningFromLootChests;}

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return BetterBridgingConfig.Enchantment.enableWidening
                && BetterBridgingConfig.Enchantment.WideningFromEnchantmentTable
                && (stack.getItem() == ItemsInit.BRIDGE_BRACE.get() || stack.getItem() == Items.BOOK);
    }

    @Override
    public boolean canEnchant(ItemStack stack) {return (stack.getItem() == ItemsInit.BRIDGE_BRACE.get() || stack.getItem() == Items.BOOK) && BetterBridgingConfig.Enchantment.enableWidening;}

    @Override
    public boolean isAllowedOnBooks() {return BetterBridgingConfig.Enchantment.enableWidening;}
}