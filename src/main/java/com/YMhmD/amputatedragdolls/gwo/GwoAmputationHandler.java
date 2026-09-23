package com.YMhmD.amputatedragdolls.gwo;

import com.YMhmD.amputatedragdolls.AmputatedRagdollsConfig;
import com.astryxion.mobamputation.common.Limb;
import com.sgr792.gwo.entity.projectile.BulletEntity;
import java.lang.reflect.Method;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GwoAmputationHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger("amputatedragdolls");
    private static final Limb[] BODY_LIMBS = {Limb.LEFT_ARM, Limb.RIGHT_ARM, Limb.LEFT_LEG, Limb.RIGHT_LEG};
    private static Method detach;
    private static Method supported;
    private static Method enabled;
    private static boolean initialized;

    @SubscribeEvent
    public static void onGwoDamage(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getDirectEntity() instanceof BulletEntity bullet)) return;
        LivingEntity victim = event.getEntity();
        Boolean headshot = GwoShotTracker.consume(bullet, victim);
        if (headshot == null || !AmputatedRagdollsConfig.ENABLE.get() || !victim.isAlive()
                || victim.isBaby() || event.getNewDamage() <= 0) return;
        if (!initialized) initialize();
        if (detach == null) return;
        try {
            if (!(boolean) supported.invoke(null, victim)) return;
            Limb limb = headshot ? Limb.HEAD : BODY_LIMBS[ThreadLocalRandom.current().nextInt(BODY_LIMBS.length)];
            double chance = headshot ? AmputatedRagdollsConfig.HEADSHOT_CHANCE.get() : AmputatedRagdollsConfig.LIMB_CHANCE.get();
            if (ThreadLocalRandom.current().nextDouble() < chance && (boolean) enabled.invoke(null, victim, limb)) {
                detach.invoke(null, victim, limb);
            }
        } catch (ReflectiveOperationException exception) {
            LOGGER.warn("GWO amputation invocation failed", exception);
        }
    }

    private static void initialize() {
        initialized = true;
        try {
            Class<?> handler = Class.forName("com.astryxion.mobamputation.common.core.EventHandlerServer");
            detach = handler.getDeclaredMethod("detachLimb", LivingEntity.class, Limb.class);
            supported = handler.getDeclaredMethod("isSupported", LivingEntity.class);
            enabled = handler.getDeclaredMethod("isLimbEnabled", LivingEntity.class, Limb.class);
            detach.setAccessible(true);
            supported.setAccessible(true);
            enabled.setAccessible(true);
        } catch (ReflectiveOperationException exception) {
            LOGGER.warn("Mob Amputation integration unavailable", exception);
        }
    }
}
