package com.cancelthedeathscreen.mixin;

import com.cancelthedeathscreen.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow @Nullable public LocalPlayer player;

    @Shadow @Nullable public Screen screen;

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    public void setScreenMixin(Screen pScreen ,CallbackInfo ci) {
        if(!Config.IS_ENABLED.get()) {
            return;
        }
        if (this.player == null || this.screen != null) {
            return;
        }
        if ((this.player.isDeadOrDying() && pScreen == null) || pScreen instanceof DeathScreen) {
            ci.cancel();
        }
    }
}
