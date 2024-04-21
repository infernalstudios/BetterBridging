/**
 * Copyright 2022 Infernal Studios
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.infernalstudios.betterbridging;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;

import org.infernalstudios.betterbridging.client.RenderEvent;
import org.infernalstudios.betterbridging.enchantments.EnchantmentsInit;
import org.infernalstudios.betterbridging.events.CreativeTabEvents;
import org.infernalstudios.betterbridging.items.*;
import org.infernalstudios.betterbridging.events.BridgingEvents;
import org.infernalstudios.betterbridging.network.DirectionMap;
import org.infernalstudios.betterbridging.network.NetworkInit;
import org.infernalstudios.config.Config;

import com.electronwill.nightconfig.core.io.ParsingException;

@Mod("betterbridging")
public class BetterBridging {
    public static final String NAME = "Better Bridging";
    public static final String MOD_ID = "betterbridging";

    public static BetterBridging instance;

    public BetterBridging() {
        instance = this;

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemsInit.ITEMS.register(modBus);
        EnchantmentsInit.ENCHANTMENTS.register(modBus);

         try {
            BetterBridgingConfig.INSTANCE = Config
                .builder(FMLPaths.CONFIGDIR.get().resolve("BetterBridging-common.toml"))
                .loadClass(BetterBridgingConfig.class, true)
                .build();
        } catch (IllegalStateException | IllegalArgumentException | IOException | ParsingException e) {
            throw new RuntimeException(
                "Failed to load BetterBridging config" +
                (e instanceof ParsingException ? ", try fixing/deleting your config file" : ""), e);
        }

        modBus.addListener(this::clientSetup);
        modBus.addListener(this::commonSetup);
        modBus.addListener(CreativeTabEvents::registerCreativeTab);

        MinecraftForge.EVENT_BUS.register(new DirectionMap());
        MinecraftForge.EVENT_BUS.register(new BridgingEvents());
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new RenderEvent());
    }

    @SubscribeEvent
    public void commonSetup(final FMLCommonSetupEvent event){
        NetworkInit.registerPackets();
    }
}