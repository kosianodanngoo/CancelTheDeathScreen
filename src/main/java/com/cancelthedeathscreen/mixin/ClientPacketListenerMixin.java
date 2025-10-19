package com.cancelthedeathscreen.mixin;

import com.cancelthedeathscreen.Config;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientPacketListener.class, priority = 2147483647)
public class ClientPacketListenerMixin {
    @Inject(method = "handlePlayerCombatKill", at = @At("HEAD"), cancellable = true)
    public void handlePlayerCombatKillMixin(CallbackInfo ci) {
        if(!Config.IS_ENABLED.get()) {
            return;
        }
        ci.cancel();
    }
}
