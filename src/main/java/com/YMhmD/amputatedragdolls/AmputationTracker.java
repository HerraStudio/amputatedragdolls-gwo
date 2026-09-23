package com.YMhmD.amputatedragdolls;

import java.util.concurrent.ConcurrentHashMap;

public final class AmputationTracker {
    public static final ConcurrentHashMap<Integer, Integer> MISSING_LIMBS = new ConcurrentHashMap<>();

    private AmputationTracker() {}
}
