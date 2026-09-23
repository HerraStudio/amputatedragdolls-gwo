package com.YMhmD.amputatedragdolls;

import com.YMhmD.amputatedragdolls.gwo.GwoAmputationHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;

@Mod("amputatedragdolls")
public class AmputationRagdollCompat {
    public AmputationRagdollCompat(IEventBus modBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, AmputatedRagdollsConfig.SPEC);
        if (ModList.get().isLoaded("gwo")) {
            NeoForge.EVENT_BUS.register(GwoAmputationHandler.class);
        }
    }
}
