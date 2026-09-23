package com.YMhmD.amputatedragdolls;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class AmputatedRagdollsConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.BooleanValue ENABLE = BUILDER.define("enableGwoAmputation", true);
    public static final ModConfigSpec.DoubleValue HEADSHOT_CHANCE = BUILDER.defineInRange("gwoHeadshotChance", 0.75, 0.0, 1.0);
    public static final ModConfigSpec.DoubleValue LIMB_CHANCE = BUILDER.defineInRange("gwoLimbChance", 0.2, 0.0, 1.0);
    public static final ModConfigSpec SPEC = BUILDER.build();

    private AmputatedRagdollsConfig() {}
}
