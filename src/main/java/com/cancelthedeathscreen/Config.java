package com.cancelthedeathscreen;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue IS_ENABLED = BUILDER.comment("Enable The Cancelling Death Screen").define("isEnabled", true);

    static final ForgeConfigSpec SPEC = BUILDER.build();

}
