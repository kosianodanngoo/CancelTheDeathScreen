package com.cancelthedeathscreen;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CancelTheDeathScreen.MODID)
public class CancelTheDeathScreen {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "cancel_the_death_screen";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public CancelTheDeathScreen() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
    }

    @SubscribeEvent
    public void omCommandRegister(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("cancel_the_death_screen").then(
                        Commands.literal("respawn").executes(ctx -> {
                            Minecraft mc = Minecraft.getInstance();
                            if (mc.player != null) {
                                mc.player.respawn();
                                ctx.getSource().sendSuccess(() -> Component.translatable("commands.cancel_the_death_screen.respawn.success"), false);
                            }
                            return 0;
                        })
                ).then(
                        Commands.literal("toggle").executes(ctx -> {
                            boolean isEnabled = Config.IS_ENABLED.get();
                            Config.IS_ENABLED.set(!isEnabled);
                            ctx.getSource().sendSuccess(() -> Component.translatable("commands.cancel_the_death_screen.toggle.success", !isEnabled ? Component.translatable("commands.cancel_the_death_screen.enabled") : Component.translatable("commands.cancel_the_death_screen.disabled")), false);
                            return 0;
                        })
                ).then(
                        Commands.literal("health_spoof").then(
                                Commands.literal("toggle").executes(ctx -> {
                                    boolean isEnabled = Config.HEALTH_SPOOF.get();
                                    Config.HEALTH_SPOOF.set(!isEnabled);
                                    ctx.getSource().sendSuccess(() -> Component.translatable("commands.cancel_the_death_screen.health_spoof.toggle.success", !isEnabled ? Component.translatable("commands.cancel_the_death_screen.enabled") : Component.translatable("commands.cancel_the_death_screen.disabled")), false);
                                    return 0;
                                })
                        ).then(
                                Commands.literal("set").then(
                                        Commands.argument("value", DoubleArgumentType.doubleArg()).executes(ctx -> {
                                            double value = DoubleArgumentType.getDouble(ctx, "value");
                                            Config.HEALTH_SPOOF_VALUE.set(value);
                                            ctx.getSource().sendSuccess(() -> Component.translatable("commands.cancel_the_death_screen.health_spoof.set.success", value), false);
                                            return 0;
                                        })
                                )
                        )
                )
        );
    }
}
