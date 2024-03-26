package org.infernalstudios.betterbridging.items;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.infernalstudios.betterbridging.enchantments.EnchantmentsInit;

public class BridgeBraceItem extends TieredItem {
    private Tier tier;

    public BridgeBraceItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return super.getMaxDamage(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        return enchantment == Enchantments.MENDING || enchantment == Enchantments.UNBREAKING || enchantment == EnchantmentsInit.WIDENING.get();
    }
}
