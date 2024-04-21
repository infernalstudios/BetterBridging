package org.infernalstudios.betterbridging.items;

import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.infernalstudios.betterbridging.BetterBridging;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemsInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BetterBridging.MOD_ID);

    public static final RegistryObject<Item> BRIDGE_BRACE = ITEMS.register("bridge_brace", () -> new BridgeBraceItem(new Tier() {
        @Override
        public int getUses() {
            return 375;
        }

        @Override
        public float getSpeed() {
            return 5;
        }

        @Override
        public float getAttackDamageBonus() {
            return 0;
        }

        @Override
        public int getLevel() {
            return 0;
        }

        @Override
        public int getEnchantmentValue() {
            return 10;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of();
        }
    }, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
}