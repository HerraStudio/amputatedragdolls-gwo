package com.YMhmD.amputatedragdolls.gwo;

import net.minecraft.world.entity.Entity;

public final class GwoShotTracker {
    private static final ThreadLocal<Hit> HIT = new ThreadLocal<>();

    public static void record(Entity bullet, Entity target, boolean headshot) {
        HIT.set(new Hit(bullet, target, headshot));
    }

    public static Boolean consume(Entity bullet, Entity target) {
        Hit hit = HIT.get();
        if (hit == null || hit.bullet != bullet || hit.target != target) return null;
        HIT.remove();
        return hit.headshot;
    }

    public static void clear() {
        HIT.remove();
    }

    private record Hit(Entity bullet, Entity target, boolean headshot) {}

    private GwoShotTracker() {}
}
