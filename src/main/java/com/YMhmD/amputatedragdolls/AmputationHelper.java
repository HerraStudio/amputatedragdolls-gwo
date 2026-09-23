package com.YMhmD.amputatedragdolls;

import com.raiiiden.ragdollified.RagdollPart;

public final class AmputationHelper {
    public static boolean isLimbAmputated(int mask, RagdollPart part) {
        int bit = switch (part) {
            case HEAD -> 1;
            case LEFT_ARM -> 2;
            case RIGHT_ARM -> 4;
            case LEFT_LEG -> 8;
            case RIGHT_LEG -> 16;
            default -> 0;
        };
        return bit != 0 && (mask & bit) != 0;
    }

    private AmputationHelper() {}
}
