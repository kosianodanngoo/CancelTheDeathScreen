package com.cancelthedeathscreen;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue IS_ENABLED = BUILDER.comment("Enable The Cancelling Death Screen").define("isEnabled", true);
    public static final ForgeConfigSpec.BooleanValue HEALTH_SPOOF = BUILDER.comment("Enable Health Spoof").define("healthSpoof", false);
    public static final ForgeConfigSpec.DoubleValue HEALTH_SPOOF_VALUE = BUILDER.comment("The value of LivingEntity.getHealth() when enabled Health Spoof").defineInRange("healthSpoofValue", 20.0D, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

    static final ForgeConfigSpec SPEC = BUILDER.build();

}
