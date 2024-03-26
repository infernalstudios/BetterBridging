package org.infernalstudios.betterbridging.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.infernalstudios.betterbridging.BetterBridging;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnchantmentsInit {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, BetterBridging.MOD_ID);

    public static final RegistryObject<Enchantment> WIDENING = ENCHANTMENTS.register("widening", () -> new WideningEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));
}
