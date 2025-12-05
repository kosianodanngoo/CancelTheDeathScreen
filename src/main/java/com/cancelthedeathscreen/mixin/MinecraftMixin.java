package com.cancelthedeathscreen.mixin;

import com.cancelthedeathscreen.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(value = Minecraft.class, priority = Integer.MAX_VALUE)
public class MinecraftMixin {
    @Shadow @Nullable public LocalPlayer player;

    @Shadow @Nullable public Screen screen;

    @Shadow @Final public MouseHandler mouseHandler;

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    public void setScreenMixin(Screen pScreen ,CallbackInfo ci) {
        if(!Config.IS_ENABLED.get()) {
            return;
        }
        if (this.player == null) {
            return;
        }
        if ((this.player.isDeadOrDying() && pScreen == null && this.screen == null) || pScreen instanceof DeathScreen) {
            ci.cancel();
        }
    }

    @Inject(method = "setScreen", at = @At("RETURN"), cancellable = true)
    public void setScreenMixinReturn(Screen p_91153_, CallbackInfo ci) {
        if(!Config.IS_ENABLED.get()) {
            return;
        }
        if(this.screen instanceof DeathScreen) {
            this.screen = null;
            this.mouseHandler.grabMouse();
        }
    }
}
