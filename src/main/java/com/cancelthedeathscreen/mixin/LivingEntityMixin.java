package com.cancelthedeathscreen.mixin;

import com.cancelthedeathscreen.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntity.class, priority = 2147483647)
public class LivingEntityMixin {
    @Inject(method = "isDeadOrDying", at = @At("HEAD"), cancellable = true)
    public void isDeadOrDyingMixin(CallbackInfoReturnable<Boolean> ci) {
        if(!Config.IS_ENABLED.get()) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (this.equals(mc.player)) {
            ci.setReturnValue(false);
        }
    }

    @Inject(method = "isAlive", at = @At("HEAD"), cancellable = true)
    public void isAliveMixin(CallbackInfoReturnable<Boolean> ci) {
        if(!Config.IS_ENABLED.get()) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (this.equals(mc.player)) {
            ci.setReturnValue(true);
        }
    }

    @Inject(method = "getHealth", at = @At("HEAD"), cancellable = true)
    public void getHealthMixin(CallbackInfoReturnable<Float> cir) {
        if(!Config.IS_ENABLED.get() || !Config.HEALTH_SPOOF.get()) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if(this.equals(mc.player)) {
            cir.setReturnValue(Config.HEALTH_SPOOF_VALUE.get().floatValue());
        }
    }
}
