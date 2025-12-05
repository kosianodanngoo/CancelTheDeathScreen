package com.cancelthedeathscreen.mixin;

import com.cancelthedeathscreen.Config;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LocalPlayer.class, priority = Integer.MAX_VALUE)
public class LocalPlayerMixin {
    @Inject(method = "tickDeath", at = @At("HEAD"), cancellable = true)
    public void tickDeathMixin(CallbackInfo ci){
        if(!Config.IS_ENABLED.get()) {
            return;
        }
        ci.cancel();
    }
}
